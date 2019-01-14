package io.yfjz.service.report.impl;

import io.yfjz.dao.statistics.SixToTwoStatisticsDao;
import io.yfjz.service.jwxplat.ServiceChild;
import io.yfjz.service.jwxplat.ServiceChildServiceLocator;
import io.yfjz.service.report.SixToTwoStatisticsService;
import io.yfjz.utils.*;
import org.dom4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.EntityResolver;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by tianjinhai on 2018/10/16 16:07
 */
@Service
public class SixToTwoStatisticsServiceImpl implements SixToTwoStatisticsService {
    private static ServiceChildServiceLocator serviceChildServiceLocator = new ServiceChildServiceLocator();
    @Autowired
    private SixToTwoStatisticsDao sixToTwoStatisticsDao;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> queryMap) {

        queryMap.put("orgId", Constant.GLOBAL_ORG_ID);
        List<Map<String, Object>> vacs=sixToTwoStatisticsDao.queryAllVaccine();
        List<Map<String, Object>> maps = sixToTwoStatisticsDao.queryList(queryMap);
        for (Map<String, Object> vac : vacs) {
            vac.put("number","0");
            for (Map<String, Object> map : maps) {
                if (map.get("classCode").equals(vac.get("classCode"))) {
                    vac.put("number",Integer.valueOf(vac.get("number").toString())+Integer.valueOf(map.get("number").toString()));
//                    break;
                }else if (vac.get("classCode").equals(map.get("classCode").toString().substring(0,2))){
                    vac.put("number",Integer.valueOf(vac.get("number").toString())+Integer.valueOf(map.get("number").toString()));
//                    break;
                }
            }
        }
        Map<String, Object> temp = new HashMap<>();
        vacs.add(temp);
        List<Map<String, Object>> results = new ArrayList<>();
        for (int i = 0; i < vacs.size(); i++) {
            if (i<vacs.size()/2){
                results.add(vacs.get(i));
            }else{
                Map<String, Object> map = vacs.get(i);
                Map<String, Object> tmp = results.get(i-vacs.size()/2);
                if(map.get("className")!=null){
                    tmp.put("classNamePlus",map.get("className"));
                    tmp.put("numberPlus",map.get("number"));
                    tmp.put("classCodePlus",map.get("classCode"));
                }

            }
        }
        return  results;
    }

    @Override
    public R uploadPlatform(Map<String, Object> queryMap) {
        if (queryMap.get("endDate") == null || "".equals(queryMap.get("endDate").toString().trim())){
            return R.error(400,"请先选择查询结束日期");
        }
        if (Constant.GLOBAL_ORG_ID ==null){
            return R.error(400,"登录超时，请退出重新登录在试");
        }
        queryMap.put("nationcode",Constant.GLOBAL_ORG_ID.substring(6));
        List<Map<String, Object>> vacs = sixToTwoStatisticsDao.queryUploadList(queryMap);
        if (vacs == null || vacs.isEmpty()){
            return R.error(400,"没有查询到信息");
        }
        String nation_code = PropertiesUtils.getProperty("mongodb.properties", "nnation.code");
        if (nation_code == null || "".equals(nation_code.trim())){
            return R.error(400,"报告单位编码没有配置正确");
        }

        String yearMonth = ""+queryMap.get("endDate").toString().substring(0,4)+queryMap.get("endDate").toString().substring(5,7);//
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("secondImmuReport");
        Element immuh = root.addElement("secondImmuh");
        Element yearxml = immuh.addElement("seir_statdate");
        yearxml.setText(yearMonth);
        Element depa = immuh.addElement("seir_depa_id");
        depa.setText(nation_code);
        Element nationcode = immuh.addElement("seir_nationcode");
        nationcode.setText(Constant.GLOBAL_ORG_ID);
        Element infe = immuh.addElement("seir_infe_id");
        infe.setText("1");
        //统计信息
        Element immubs = root.addElement("secondImmubs");

        for (Map<String, Object> map : vacs){

            Element immub = immubs.addElement("secondImmub");
            //户籍属性
            Element residence = immub.addElement("seir_residence");
            residence.setText("1");
            //疫苗编码
            Element bactcode = immub.addElement("seir_bact_code");
            bactcode.setText(""+map.get("classCode"));
            //接种剂次
            Element inoculated = immub.addElement("seir_inoculated");
            inoculated.setText(""+map.get("inoc_time"));
        }

        try{
            String xmlString = XmlUtils.formatXml(doc);
            System.out.println(xmlString);

            byte[] zippedBytes = XmlUtils.zipBytes(xmlString, "SecondImmuReport.xml");
            ServiceChild serviceChild = serviceChildServiceLocator.getChildService();
            String result = serviceChild.uploadSecondImmuReport(Constant.GLOBAL_ORG_ID, Constant.GLOBAL_ORG_ID, zippedBytes);
            if("1".equals(result)){
                return R.error(1,"上传成功");
            }if("2".equals(result)){
                return R.error(400,"您没有权限上报数据");
            }if("3".equals(result)){
                return R.error(400,"单位编码或密码错误");
            }if("4".equals(result)){
                return R.error(400,"数据操作失败，可能数据格式不对");
            }if("5".equals(result)){
                return R.error(400,"上级单位已经上报数据，上传失败");
            }else{
                return R.error(400,"上传失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return R.ok();
    }
}
