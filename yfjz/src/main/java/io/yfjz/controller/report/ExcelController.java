package io.yfjz.controller.report;

import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.IntegrityRateEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.service.child.TChildInfoService;
import io.yfjz.service.child.TChildInoculateService;
import io.yfjz.service.report.ExcelService;
import io.yfjz.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 饶士培
 * @Date: 2018-09-10 17:48
 * @Description:
 * @tel:18798010686
 * @qq:1013147559
 */
@Controller
@RequestMapping("/ExcelController")
public class ExcelController {
    @Autowired
    private TChildInfoService tChildInfoService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private TChildInoculateService tChildInoculateService;

    /**
     * @method_name: IntegrityRate
     * @describe:儿童基本资料完整性汇总表导出
     * @param request, response]
     * @return void
     * @author 饶士培
     * @QQ: 1013147559@qq.com
     * @tel:18798010686
     * @date: 2018-04-22  12:49
     **/
    @RequestMapping(value = {"/IntegrityRate"})
    public void IntegrityRate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> param  = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
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
        excelService.IntegrityRate(integrityRate,path,response);
    }


    /**
     * @method_name: IntegrityRate
     * @describe:儿童基本资料不完整性汇总表导出
     * @param request, response]
     * @return void
     * @author 廖欢
     * @QQ: 1215077166@qq.com
     * @tel:18785185063
     **/
    @RequestMapping(value = {"/imperfectChild"})
    public void imperfectChild(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String,Object> params) throws IOException {
        params.put("org_id", ShiroUtils.getUserEntity().getOrgId());
        Query param = new Query(params);
        Map<String, Object> map  = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String path = request.getSession().getServletContext().getRealPath("/");
        map.put("path",path);
        List<TChildInfoEntity> listImperfectChild= tChildInfoService.listImperfectChild(param);
        excelService.imperfectChild(listImperfectChild,path,response);

    }

    /**
     * @method_name: IntegrityRate
     * @describe:儿童基本接种日志导出
     * @param request, response]
     * @return void
     * @author 廖欢
     * @QQ: 1215077166@qq.com
     * @tel:18785185063
     **/
    @RequestMapping(value = {"/ExcelInoLog"})
    public void ExcelInoLog(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String,Object> params) throws IOException {
        Query param = new Query(params);
        Map<String, Object> map  = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String path = request.getSession().getServletContext().getRealPath("/");
        map.put("path",path);
        excelService.ExcelInoLog(param,path ,response);

    }


    /**
     * 儿童接种信息完整性统计-导出
     * @method_name: IntegrityRate
     * @describe:儿童接种信息整性导出
     * @param request, response]
     * @return void
     * @author 廖欢
     * @QQ: 1215077166@qq.com
     * @tel:18785185063
     **/
    @RequestMapping(value = {"/inoculateGather"})
    @ResponseBody
    public void inoculateGather(@RequestParam Map<String,Object> param,HttpServletResponse response,HttpServletRequest request) throws IOException {

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
        String path = request.getSession().getServletContext().getRealPath("/");
        excelService.inoculateGather(integrityRate,path,response);
    }

    /**
     * 儿童档案按条件查询导出
     * @author 饶士培
     * @time 2018-10-20
     * @param param
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = {"/child"})
    @ResponseBody
    public void child(@RequestParam Map<String,Object> param,HttpServletResponse response,HttpServletRequest request) throws IOException {

        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("org_id",ShiroUtils.getUserEntity().getOrgId());
        if(map.get("infostatus")!=null){
            map.put("chilHere",map.get("infostatus"));
        }

        //查询列表数据
        List<TChildInfoEntity> tChildInfoList = tChildInfoService.queryList(map);
        String path = request.getSession().getServletContext().getRealPath("/");
        excelService.childrenList(tChildInfoList,map,response);
    }

    /**
     * 6-1 国家免疫规划疫苗常规接种情况报表
     * @author 张羽丰
     * @time 2018-10-25
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("exportNiprvsr")
    @ResponseBody
    public void exportNiprvsr(String year,String month,HttpServletResponse response,HttpServletRequest request) throws IOException{
        Map<String, Object> queryMap = new HashMap<>();
        /*String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
        excelService.exportNiprvsr(queryMap,response);

    }

    /**
     * 贵州省免疫规划目标儿童动态管理一览表
     * @author 张羽丰
     * @time 2018-10-25
     * @param param
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("exportAlodmotcigip")
    @ResponseBody
    public void exportAlodmotcigip(String year,String month,@RequestParam Map<String,Object> queryMap,HttpServletResponse response,HttpServletRequest request) throws IOException{
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
      //  map.put("org_id",ShiroUtils.getUserEntity().getOrgId());
        String[] chilCommittees = null;
        if(queryMap.get("chilCommittees")==null||"".equals(queryMap.get("chilCommittees")) || "null".equals(queryMap.get("chilCommittees"))){
            queryMap.put("chilCommittees",null);
        }else{
            String str2=queryMap.get("chilCommittees").toString();
            chilCommittees= str2.split(",");
            queryMap.put("chilCommittees", chilCommittees);
        }
        map.put("chilCommittees", chilCommittees);
        excelService.exportAlodmotcigip(year,month,map,chilCommittees,response);

    }

    //vac_transport_temp

    /**
     * @method_name: IntegrityRate
     * @describe:疫苗运输温度记录表导出
     * @param request, response]
     * @return void
     * @author 廖欢
     * @QQ: 1215077166@qq.com
     * @tel:18785185063
     **/
    @RequestMapping(value = {"/vacTransportTemp"})
    @ResponseBody
    public void vacTransportTemp(@RequestParam Map<String,Object> param,HttpServletResponse response,HttpServletRequest request){
        excelService.vacTransportTemp(response);

    }

    /**
     * @method_name: IntegrityRate
     * @describe:国家免疫规划疫苗计划表
     * @param request, response]
     * @return void
     * @author 廖欢
     * @QQ: 1215077166@qq.com
     * @tel:18785185063
     **/
    @RequestMapping(value = {"/vacScheduleVillag"})
    @ResponseBody
    public void vacScheduleVillag(@RequestParam Map<String,Object> param,HttpServletResponse response,HttpServletRequest request){
        excelService.vacScheduleVillag(response);

    }


    /**
     * @author Woods
     * @desc 导出儿童基本信息与接种记录
     */
    @GetMapping("exportChildAndInoculate")
    @ResponseBody
    public void exportChildAndInoculate(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("org_id",ShiroUtils.getUserEntity().getOrgId());
        if(map.get("infostatus")!=null){
            map.put("chilHere",map.get("infostatus"));
        }

        //查询列表数据
        List<TChildInfoEntity> tChildInfoList = tChildInfoService.queryList(map);

        excelService.exportChildAndInoculate(tChildInfoList,map,response);

    }

}
