package io.yfjz.controller.print;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.yfjz.entity.print.ChildInfoBean;
import io.yfjz.entity.print.InoculateDetailBean;
import io.yfjz.service.print.XmlFileService;
import io.yfjz.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by rsp on 2018-03-18.
 */

@Controller
@RequestMapping("/SetPrintForm")
public class PrintSetController {
    @Autowired
    private XmlFileService xmlFileService;

    /**
     * 保存接种证设置坐标
     * @author 饶士培
     * @date 2018-09-01
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ChildInoculatePrintSet", method = RequestMethod.POST)
    @ResponseBody
    public R inoculatePrintSet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String datas = (String) map.get("datass");
        String printType = (String) map.get("printType");
        int allX = 0;
        int allY = 0;
        if(map.get("allX")!=null){
            allX = Integer.valueOf(map.get("allX").toString());
        }
        if(map.get("allY")!=null){
            allY = Integer.valueOf(map.get("allY").toString());
        }
        List<InoculateDetailBean> all = new ArrayList<InoculateDetailBean>(18);
        JSONObject json = new JSONObject();
        JSONArray jsonArray = JSONArray.parseArray(datas);
        List<Map<String, Object>> listma = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            InoculateDetailBean bean = new InoculateDetailBean();
            JSONArray arr = (JSONArray) jsonArray.get(i);
            String name =null;
            for (int j=0; j<arr.size();j++) {
                JSONObject jobj = (JSONObject)arr.get(j);
                if(j%13==0){
                    name = String.valueOf(jobj.get("name"));
                }
                String fullname=String.valueOf(jobj.get("name"));
                String nametrim =fullname.trim().substring(name.length());
                switch (nametrim){
                    case "": { bean.setBacterinClassName(String.valueOf(jobj.get("value")));break;}
                    case "DateX": { bean.setInoculate_dateX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX);break;}
                    case "DateY": { bean.setInoculate_dateY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY);break;}
                    case "SiteX": { bean.setTextX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX);break;}
                    case "SiteY": {bean.setTextY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "NumX": {bean.setBatchnumX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX); break;}
                    case "NumY": {bean.setBatchnumY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "FacX": { bean.setFactory_nameX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX);break;}
                    case "FacY": {bean.setFactory_nameY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "DeptX": {bean.setDepartnameX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX); break;}
                    case "DeptY": {bean.setDepartnameY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "DocX": {bean.setDoctorX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX); break;}
                    case "DocY": {bean.setDoctorY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "ExpX": {bean.setExpiration_dateX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX); break;}
                    case "ExpY": {bean.setExpiration_dateY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                }
            }
            all.add(bean);
        }
        int result = 0;
        //int result = xmlFileService.modifyXml(path + "WEB-INF/classes/jaserreport/selfdefineprintInoculateNew.xml", all);
        if("1".equals(printType)){
             result = xmlFileService.modifyXml(path + "WEB-INF/classes/jaserreport/selfdefineprintInoculate.xml", all);
        }
        if("2".equals(printType)){
             result = xmlFileService.modifyXml(path + "WEB-INF/classes/jaserreport/selfdefineprintCard.xml", all);
        }

        if (result == 0) {
            json.put("message", "true");
        } else {
            json.put("message", "false");
        }
        return R.ok(json);
    }

    /**
     * 保存儿童信息坐标方法
     * @author 饶士培
     * @date 2018-09-04
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ChildInfoPrintSet", method = RequestMethod.POST)
    @ResponseBody
    public R childInfoPrintSet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String datas = (String) map.get("datass");
        String printType = (String) map.get("printType");
        int allX = 0;
        int allY = 0;
        if(map.get("allX")!=null){
            allX = Integer.valueOf(map.get("allX").toString());
        }
        if(map.get("allY")!=null){
            allY = Integer.valueOf(map.get("allY").toString());
        }
        List<ChildInfoBean> all = new ArrayList<ChildInfoBean>(18);
        JSONObject json = new JSONObject();
        try{
            JSONArray jsonArray = JSONArray.parseArray(datas);
            List<Map<String, Object>> listma = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {

                ChildInfoBean bean = new ChildInfoBean();
                JSONArray arr = (JSONArray) jsonArray.get(i);
                String name =null;
                for (int j=0; j<arr.size();j++) {
                    JSONObject jobj = (JSONObject)arr.get(j);
                    if(j%3==0){
                        name = String.valueOf(jobj.get("name"));
                    }
                    String fullname=String.valueOf(jobj.get("name"));
                    String nametrim =fullname.substring(name.length());
                    switch (nametrim){
                        case "": { bean.setProperty(String.valueOf(jobj.get("value")));break;}
                        case "_X": { bean.setProperty_X(Integer.valueOf(jobj.get("value").toString())+allX);break;}
                        case "_Y": { bean.setProperty_Y(Integer.valueOf(String.valueOf(jobj.get("value")))+allY);break;}
                    }
                }
                all.add(bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String path = request.getSession().getServletContext().getRealPath("/");
        int result = 0;
        if("1".equals(printType)){//接种证
            result = xmlFileService.modifyJrxml(path + "reports/selfdefineprintInfo.jrxml", all);
        }
        if("2".equals(printType)){//接种卡
            result = xmlFileService.modifyJrxml(path + "reports/printinfoforcard.jrxml", all);
        }

        if (result == 0) {
            json.put("message", "true");
        } else {
            json.put("message", "false");
        }
       return R.ok(json);

    }



    @RequestMapping(value = "/ChildInoculatePrintSetIntoTable", method = RequestMethod.POST)
    @ResponseBody
    public R ChildInoculatePrintSetIntoTable(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String printType = (String) map.get("printType");

        JSONObject json = new JSONObject();

        int result = 0;
        if("1".equals(printType)){
            result = xmlFileService.xmlElements(response,path + "WEB-INF/classes/jaserreport/selfdefineprintInoculate.xml", printType);
        }
        if("2".equals(printType)){
            result = xmlFileService.xmlElements(response,path + "WEB-INF/classes/jaserreport/selfdefineprintCard.xml", printType);
        }
        if (result == 0) {
            json.put("message", "true");
        } else {
            json.put("message", "false");
        }
        return R.ok(json);
    }


    @RequestMapping("/selectInoculatePrintSet")
    @ResponseBody
    public R selectInoculatePrintSet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String printType = (String) map.get("printType");

        JSONObject json = new JSONObject();

        List<Map<String,Object>> listCoordinate = xmlFileService.queryInoculateCoordinate(response,printType);
        PageUtils pageUtil = null;
        if(listCoordinate!=null){
             pageUtil = new PageUtils(listCoordinate, listCoordinate.size(), listCoordinate.size(), 1);
        }
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping(value = "/ChildInfoSetIntoTable", method = RequestMethod.POST)
   //@RequestMapping("/ChildInfoSetIntoTable")
    @ResponseBody
    public R ChildInfoSetIntoTable(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String printType = (String) map.get("printType");

        JSONObject json = new JSONObject();
        int result = 0;
           // result = xmlFileService.xmlElements(response,path + "WEB-INF/classes/jaserreport/selfdefineprintInoculateNew.xml", printType);
           if("1".equals(printType)){
               result = xmlFileService.jrxmlElements(response,path + "reports/selfdefineprintInfo.jrxml",printType);
           }
        if("2".equals(printType)){
            result = xmlFileService.jrxmlElements(response,path + "reports/printinfoforcard.jrxml",printType);
        }


        //int result = xmlFileService.xmlElements(response,path + "WEB-INF/classes/jaserreport/selfdefineprintInoculateNew.xml",printType);
        //int result = xmlFileService.xmlElements(path + "WEB-INF/classes/jaserreport/selfdefineprintInoculateNew.xml");
        if (result == 0) {
            json.put("message", "true");
        } else {
            json.put("message", "false");
        }
        return R.ok(json);
    }

    @RequestMapping(value = "/selectChildInfoPrintSet", method = RequestMethod.POST)
    @ResponseBody
    public R selectChildInfoPrintSet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String printType = (String) map.get("printType");
       R r =  xmlFileService.queryChildInfoCoordinate(response,printType);

       return r;

    }

    /**
     * @author 饶士培
     * @time  2018-07-11 11:22:34
     * 接种证打印儿童接种记录模板另存为
     * @param request
     * @param response
     */
    @RequestMapping("/ChildInoculatePrintSetSaveAs")
    @ResponseBody
    public R ChildInoculatePrintSetSaveAs(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String datas = (String) map.get("datass");
        String printType = (String) map.get("printType");
        String xml_name = (String) map.get("xml_name");
        int allX = 0;
        int allY = 0;
        if(map.get("allX")!=null){
            allX = Integer.valueOf(map.get("allX").toString());
        }
        if(map.get("allY")!=null){
            allY = Integer.valueOf(map.get("allY").toString());
        }

        List<InoculateDetailBean> all = new ArrayList<InoculateDetailBean>(18);
        JSONObject json = new JSONObject();
        JSONArray jsonArray = JSONArray.parseArray(datas);
        List<Map<String, Object>> listma = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            InoculateDetailBean bean = new InoculateDetailBean();
            JSONArray arr = (JSONArray) jsonArray.get(i);
            String name =null;
            for (int j=0; j<arr.size();j++) {
                JSONObject jobj = (JSONObject)arr.get(j);
                if(j%13==0){
                    name = String.valueOf(jobj.get("name"));
                }
                String fullname=String.valueOf(jobj.get("name"));
                String nametrim =fullname.trim().substring(name.length());
                switch (nametrim){
                    case "": { bean.setBacterinClassName(String.valueOf(jobj.get("value")));break;}
                    case "DateX": { bean.setInoculate_dateX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX);break;}
                    case "DateY": { bean.setInoculate_dateY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY);break;}
                    case "SiteX": { bean.setTextX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX);break;}
                    case "SiteY": {bean.setTextY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "NumX": {bean.setBatchnumX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX); break;}
                    case "NumY": {bean.setBatchnumY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "FacX": { bean.setFactory_nameX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX);break;}
                    case "FacY": {bean.setFactory_nameY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "DeptX": {bean.setDepartnameX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX); break;}
                    case "DeptY": {bean.setDepartnameY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "DocX": {bean.setDoctorX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX); break;}
                    case "DocY": {bean.setDoctorY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                    case "ExpX": {bean.setExpiration_dateX(Integer.valueOf(String.valueOf(jobj.get("value")))+allX); break;}
                    case "ExpY": {bean.setExpiration_dateY(Integer.valueOf(String.valueOf(jobj.get("value")))+allY); break;}
                }
            }
            all.add(bean);
        }
        String path = request.getSession().getServletContext().getRealPath("/");
        int result = 0;
        if("1".equals(printType)){
            result = xmlFileService.createXml(path,path + "WEB-INF/classes/jaserreport/selfdefineprintInoculate.xml",xml_name, all);
        }
        if("2".equals(printType)){
            result = xmlFileService.createXml(path,path + "WEB-INF/classes/jaserreport/selfdefineprintCard.xml",xml_name, all);
        }

        if (result == 0) {
            json.put("message", "true");
        } else {
            json.put("message", "false");
        }
        return R.ok(json);
    }

    /**
     * @author 饶士培
     * @time 2018-07-11 09:32:34
     * @description 将模板名保存到数据库
     * @param request
     * @param response
     */
    @RequestMapping("/ChildInoculatePrintSetModelIntoTable")
    @ResponseBody
    public R ChildInoculatePrintSetModelIntoTable(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String orgid = ShiroUtils.getUserEntity().getOrgId();
        String username = ShiroUtils.getUserEntity().getRealName();
        map.put("orgid",orgid);
        map.put("username",username);

        JSONObject json = new JSONObject();
        int result = 0;
        result = xmlFileService.saveModelIntoTable(map);
        if (result == 1) {
            json.put("message", "true");
        } else {
            json.put("message", "false");
        }
       return R.ok(json);
    }

    /**
     *@time 2018-07-11 09:42:34
     * @author 饶士培
     * 模板查询
     * @param request
     * @param response
     */
    @RequestMapping("/selectInoculatePrintSetModel")
    @ResponseBody
    public R selectInoculatePrintSetModel(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        //String orgid = (String) request.getSession().getAttribute("currentdepartid");
        String orgid = Constant.GLOBAL_ORG_ID;
        map.put("orgid",orgid);
        List<Map<String,Object>> list = xmlFileService.queryInoculatePrintSetModel(response,map);
        PageUtils pageUtil = null;
        if(list!=null){
             pageUtil = new PageUtils(list, list.size(), list.size(), 1);
            return R.ok().put("page", pageUtil);
        }
        return R.error();

    }

    /**
     * @author 饶士培
     * @time  2018-09-04 11:22:34
     * @description接种证打印儿童信息模板另存为
     * @param request
     * @param response
     */
    @RequestMapping(value = "/ChildInfoPrintSetModelAs", method = RequestMethod.POST)
    @ResponseBody
    public R ChildInfoPrintSetModelAs(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String datas = (String) map.get("datass");
        String printType = (String) map.get("printType");
        String jrxml_name = (String) map.get("xml_name");
        int allX = 0;
        int allY = 0;
        if(map.get("allX")!=null){
            allX = Integer.valueOf(map.get("allX").toString());
        }
        if(map.get("allY")!=null){
            allY = Integer.valueOf(map.get("allY").toString());
        }

        List<ChildInfoBean> all = new ArrayList<ChildInfoBean>(18);
        JSONObject json = new JSONObject();
        try{
            JSONArray jsonArray = JSONArray.parseArray(datas);
            List<Map<String, Object>> listma = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {

                ChildInfoBean bean = new ChildInfoBean();
                JSONArray arr = (JSONArray) jsonArray.get(i);
                String name =null;
                for (int j=0; j<arr.size();j++) {
                    JSONObject jobj = (JSONObject)arr.get(j);
                    if(j%3==0){
                        name = String.valueOf(jobj.get("name"));
                    }
                    String fullname=String.valueOf(jobj.get("name"));
                    String nametrim =fullname.substring(name.length());
                    switch (nametrim){
                        case "": { bean.setProperty(String.valueOf(jobj.get("value")));break;}
                        case "_X": { bean.setProperty_X(Integer.valueOf(jobj.get("value").toString())+allX);break;}
                        case "_Y": { bean.setProperty_Y(Integer.valueOf(String.valueOf(jobj.get("value")))+allY);break;}
                    }
                }
                all.add(bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        String path = request.getSession().getServletContext().getRealPath("/");
        int result = 0;
        result = xmlFileService.createJrxml(path,path + "reports/selfdefineprintInfo.jrxml",jrxml_name, all);

        if (result == 0) {
            json.put("message", "true");
        } else {
            json.put("message", "false");
        }
        return R.ok(json);
    }

    /**删除模板方法弃用
     * @author 饶士培
     * @date 2018-09-01
     * @param ids
     * @return
     */
    @RequestMapping("/deleteModelById")
    @ResponseBody
    public  R deleteModelById(@RequestBody String[] ids){
        //Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        JSONObject json = new JSONObject();
        int result = 0;
        result = xmlFileService.deleteModel(ids);
        if (result == 1) {
            json.put("message", "true");
        } else {
            json.put("message", "false");
        }
        return R.ok(json);

    }


}
