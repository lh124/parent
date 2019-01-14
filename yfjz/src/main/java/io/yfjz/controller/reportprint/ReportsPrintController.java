package io.yfjz.controller.reportprint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.IntegrityRateEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.service.child.TChildInfoService;
import io.yfjz.service.child.TChildInoculateService;
import io.yfjz.service.mgr.TMgrStockInfoService;
import io.yfjz.service.report.ReportsPrintService;
import io.yfjz.service.statics.InoculateService;
import io.yfjz.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 打印控制
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 13:53:13
 */
@Controller
@RequestMapping("/reports")
public class ReportsPrintController {
    @Autowired
    private ReportsPrintService reportsPrintService;
    @Autowired
    private TChildInfoService tChildInfoService;
    @Autowired
    private TChildInoculateService tChildInoculateService;
    @Autowired
    private TMgrStockInfoService tMgrStockInfoService;
    /**
     * 通过接种证模板打印儿童基本信息
     * @author 饶士培
     * @time  2018-08-12 09:16:32
     * @param request
     * @throws IOException
     */
    @RequestMapping("/printchildinfoByModel")
    public void printchildinfo(HttpServletRequest request,HttpServletResponse response) {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("path",path);
        Map<String,Object> childObj = reportsPrintService.queryForPrint(map,response);

    }

    /**
     * 接种卡新卡打印
     * @author 饶士培
     * @time  2018-08-30 09:16:32
     * @param request
     * @throws IOException
     */
    @RequestMapping("/printNewCard")
    public void printNewCard(HttpServletRequest request,HttpServletResponse response) {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("path",path);
        Map<String,Object> childObj = reportsPrintService.printCard(map,response);

    }

    /**
     * 打印儿童当天接种记录
     * 动态模板打印
     * @author 饶士培
     * @date 2018-08-27
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/printCurrentDayInoculateByModel")
    public void printCurrentDayChildInoculateByModel(HttpServletRequest request,HttpServletResponse response)  {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("path",path);
        Map<String,Object> childObj = reportsPrintService.queryInoculationForPrint(map,response);
    }

    /**
     * 打印儿童二类苗接种记录（接种证）
     * 动态模板打印
     * @author 饶士培
     * @date 2018-10-30
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/printSecondVaccBylineCount")
    public void printSecondVaccBylineCount(HttpServletRequest request,HttpServletResponse response)  {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("path",path);
        Map<String,Object> childObj = reportsPrintService.querySecondVaccInocForPrint(map,response);
    }

    /**
     * 打印儿童接种记录（不定数量）
     * 动态模板打印
     * @author 饶士培
     * @date 2018-08-29
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/printChildInoculateByModel")
    public void printChildInoculateByModel(HttpServletRequest request,HttpServletResponse response)  {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("path",path);
        Map<String,Object> childObj = reportsPrintService.queryInoculationByIdForPrint(map,response);
    }

    /**
     * 打印儿童基本资料完整性汇总
     * @param request
     * @param response
     */
    @RequestMapping("/printchildGather")
    public void printchildGather(@RequestParam Map<String,Object> param,HttpServletRequest request, HttpServletResponse response){
        String path = request.getSession().getServletContext().getRealPath("/");
        param.put("path",path);
        int year_start = 0;
        int year_end = 0;
        if(!param.get("year_start").equals("null") && param.get("year_start")!="") {
            year_start = Integer.parseInt(param.get("year_start").toString());
        }
        if(!param.get("year_end").equals("null") && param.get("year_end")!="") {
            year_end = Integer.parseInt(param.get("year_end").toString());
        }
        if(param.get("year_start").equals("null")){
            param.put("year_start",null);
        }
        if(param.get("year_end").equals("null")){
            param.put("year_end",null);
        }
        int year = year_end-year_start;
        if(year>0){
            String[] yearStr = new String[year+1];
            int[] years = new int[year+1];
            for(int i = 0; i <= year;i++){
                years[i] = year_start+i;
                yearStr[i] = String.valueOf(years[i]);
            }
            if(null != years) {
                List yearList = Arrays.asList(yearStr);
                param.put("yearList", yearList);
            }
        }
        List<IntegrityRateEntity> integrityRate= tChildInfoService.listIntegrityRate(param);
        reportsPrintService.queryAndPrintchildGather(integrityRate,path,response);

    }
    /**
     * 打印儿童信息不完整记录
     * @param request
     * @param response
     * 廖欢
     */
    @RequestMapping("/imperfectChild")
    public void imperfectChild(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params){
        params.put("org_id", ShiroUtils.getUserEntity().getOrgId());
        Query param = new Query(params);
        List<TChildInfoEntity> listImperfectChild= tChildInfoService.listImperfectChild(param);
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("path",path);
        List<TChildInfoEntity> maps = reportsPrintService.ImperfectDataChild(listImperfectChild, map,response);
    }

    /**
     * 打印儿童接种日志
     * @param request
     * @param response
     * 廖欢
     */
    @RequestMapping("/InoculateLogs")
    public void InoculateLogs(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String,Object> param){
        String path = request.getRealPath("/");
        param.put("path",path);
        param.put("page", 0);
        param.put("limit",10000 );
        Query query = new Query(param);
        List<Map<String,Object>> list =  reportsPrintService.InoculateLogs(query,response);
    }

    /**
     * 儿童接种信息完整性统计-打印
     * @param request
     * @param response
     * 廖欢
     */
    @RequestMapping("/inoculateGather")
    public void inoculateGather(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params){
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("org_id",ShiroUtils.getUserEntity().getOrgId());
        Object obj = map.get("inoVacc");
        Object Committees = map.get("chilCommittees");
        if(obj != null && !(obj instanceof String)){
            String[] inoVacc =(String[]) map.get("inoVacc");
            if(null != inoVacc) {
                List bios = Arrays.asList(inoVacc);
                map.put("bios", bios);
            }
        }
        if(Committees != null && !(Committees instanceof String)){
            String[] chilCommittees =(String[]) map.get("chilCommittees");
            if(null != chilCommittees) {
                List Committee = Arrays.asList(chilCommittees);
                map.put("Committee", Committee);
            }
        }
        List<InoculateIntegrityRateEntity> integrityRate = tChildInoculateService.listInocIntegrityRate(map);
        String path = request.getRealPath("/");
        map.put("path",path);
        reportsPrintService.inoculateGather(integrityRate, map,response);
    }

    /**
     * 打印今日接种儿童printCurrentDayWaitInocChild
     * @author 饶士培
     * @date 2018-10-08
     * @param request
     * @param response
     */
    @RequestMapping("/printCurrentDayInocChild")
    public void printCurrentDayInocChild(HttpServletRequest request,HttpServletResponse response)  {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("path",path);
        //Query param = new Query(params);
        map.put("org_id",ShiroUtils.getUserEntity().getOrgId());
        List<TChildInfoEntity> listCurrentDayInocChild= tChildInfoService.currentDayInocChild(map);
        for (TChildInfoEntity entity:listCurrentDayInocChild){
            entity.setChilHabiaddress(entity.getChilHabiaddress().replaceAll(" ", "").replaceAll("-", ""));
            entity.setChilAddress(entity.getChilAddress().replaceAll(" ", "").replaceAll("-", ""));
        }
        reportsPrintService.printCurrentDayInocChild(listCurrentDayInocChild,path, response);

    }

    /**
     * 打印今日待接种儿童
     * @author 饶士培
     * @date 2018-10-09
     * @param request
     * @param response
     */
    @RequestMapping("/printCurrentDayWaitInocChild")
    public void printCurrentDayWaitInocChild(HttpServletRequest request,HttpServletResponse response)  {
        String path = request.getRealPath("/");
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("path",path);
        //Query param = new Query(params);
        map.put("org_id",ShiroUtils.getUserEntity().getOrgId());
        List<TChildInfoEntity> listCurrentDayWaitInocChild= tChildInfoService.currentDayWaitInocChild(map);
        for (TChildInfoEntity entity:listCurrentDayWaitInocChild){
            entity.setChilHabiaddress(entity.getChilHabiaddress().replaceAll(" ", "").replaceAll("-", ""));
            entity.setChilAddress(entity.getChilAddress().replaceAll(" ", "").replaceAll("-", ""));
        }
        reportsPrintService.printCurrentDayWaitInocChild(listCurrentDayWaitInocChild,path, response);

    }



    @RequestMapping("/printStockControl")
    public void printStockControl(String type, String searchProductName, String searchFactoryName, String searchBatch,
                                  String  searchDate,Integer searchType, String storeId,String selectType,HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        map.put("offset", 0);
        map.put("limit", 1000);
        if(!StringUtils.isEmpty(searchProductName)){
            map.put("searchProductName", searchProductName);
        }
        if(!StringUtils.isEmpty(searchFactoryName)){
            map.put("searchFactoryName", searchFactoryName);
        }
        if(!StringUtils.isEmpty(searchBatch)){
            map.put("searchBatch", searchBatch);
        }
        //查询的类型1 为领苗查询，不显示库存为0的数据
        if(!StringUtils.isEmpty(type)){
            map.put("type", type);
        }
        if(StringUtils.isEmpty(selectType)){
            map.put("selectType", "hideOverdue");
        }else{
            map.put("selectType", selectType);
        }
        if(!StringUtils.isEmpty(searchDate)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(searchDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date addDays = DateUtils.addDays(date, 1);
            map.put("searchDate", addDays);
        }
        if(searchType!=null){
            map.put("searchType", searchType);
        }
        if(storeId!=null){
            map.put("storeId", storeId);
        }
        //查询列表数据
        List<Map<String, Object>> list = tMgrStockInfoService.queryList(map);
        String path = request.getRealPath("/");
        Map<String,Object> map2= new HashMap<>();
        map2.put("path", path);
        reportsPrintService.printStockControl(list,path, response);
    }
}