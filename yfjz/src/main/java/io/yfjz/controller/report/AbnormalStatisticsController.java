package io.yfjz.controller.report;

import io.yfjz.service.report.AbnormalStatisticsService;
import io.yfjz.service.report.ChildServiceStatisticsService;
import io.yfjz.utils.*;
import io.yfjz.utils.pdf.PDFUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
* @Description: 儿童全程服务记录
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/23 11:44
* @Tel  17328595627
*/ 
@RequestMapping("abnormal")
@Controller
public class AbnormalStatisticsController {

    @Autowired
    private AbnormalStatisticsService abnormalService;

    @RequestMapping("/list")
    @ResponseBody
    public R getList(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "chilCommittees[]", required = false) String[] chilCommittees,
                     @RequestParam(value = "chilResidences[]", required = false) String[] chilResidences,
                     @RequestParam(value = "infostatus[]", required = false) String[] infostatus,
                     @RequestParam(value = "schools[]", required = false) String[] schools){

        Map paramMap = HttpServletRequestToMapUtils.requestToMap(request);
        //默认查询最近一月的数据
        if (paramMap.get("inoculateStart")==null&&paramMap.get("inoculateEnd")==null){
            Date nowDate = new Date();
            Date date = DateUtils.addDays(nowDate, -30);
            paramMap.put("inoculateStart",date);
            paramMap.put("inoculateEnd",nowDate);
        }
        if (chilCommittees!=null&&chilCommittees.length>0){
            paramMap.put("chilCommittees", Arrays.asList(chilCommittees));
        }
        if (chilResidences!=null&&chilResidences.length>0){
            paramMap.put("chilResidences",Arrays.asList(chilResidences));
        }
        if (schools!=null&&schools.length>0){
            paramMap.put("schools",Arrays.asList(schools));
        }
        if (infostatus!=null&&infostatus.length>0){
            paramMap.put("infostatus",Arrays.asList(infostatus));
        }
        Integer limit = Integer.valueOf(paramMap.get("limit").toString());
        Integer page = Integer.valueOf(paramMap.get("page").toString());
        int offset=(page - 1) * limit;
        paramMap.put("limit", limit);
        List<Map<String,Object>> list=abnormalService.queryList(paramMap);
        List<Map<String,Object>> temp = new ArrayList<>();
        int indexSize=0;
        if ((offset+limit)<list.size()) {
            indexSize=offset+limit;
        }else{
            indexSize=list.size();
        }
        for (int i = offset; i <indexSize ; i++) {
            if (i<list.size()){
                temp.add(list.get(i));
            }
        }
//        int total=abnormalService.queryTotal(paramMap);
        PageUtils pageUtil = new PageUtils(temp, list.size(), limit ,page);
        return R.ok().put("page", pageUtil);

    }
    /** 
    * @Description: 导出excel
    * @Param: [request, response, chilCommittees, chilResidences, infostatus, schools] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/27 9:56
    * @Tel  17328595627
    */ 
    @RequestMapping("/excel")
    @ResponseBody
    public void outPutExcel(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "chilCommittees[]", required = false) String[] chilCommittees,
                     @RequestParam(value = "chilResidences[]", required = false) String[] chilResidences,
                     @RequestParam(value = "infostatus[]", required = false) String[] infostatus,
                     @RequestParam(value = "schools[]", required = false) String[] schools){

        Map paramMap = HttpServletRequestToMapUtils.requestToMap(request);
        //默认查询最近一月的数据
        if (paramMap.get("inoculateStart")==null&&paramMap.get("inoculateEnd")==null){
            Date nowDate = new Date();
            Date date = DateUtils.addDays(nowDate, -30);
            paramMap.put("inoculateStart",date);
            paramMap.put("inoculateEnd",nowDate);
        }
        if (chilCommittees!=null&&chilCommittees.length>0){
            paramMap.put("chilCommittees", Arrays.asList(chilCommittees));
        }
        if (chilResidences!=null&&chilResidences.length>0){
            paramMap.put("chilResidences",Arrays.asList(chilResidences));
        }
        if (schools!=null&&schools.length>0){
            paramMap.put("schools",Arrays.asList(schools));
        }
        if (infostatus!=null&&infostatus.length>0){
            paramMap.put("infostatus",Arrays.asList(infostatus));
        }
        Integer limit = Integer.valueOf(paramMap.get("limit").toString());
        Integer page = Integer.valueOf(paramMap.get("page").toString());
        paramMap.put("offset", (page - 1) * limit);
        paramMap.put("limit", limit);
        List<Map<String,Object>> list=abnormalService.queryList(paramMap);
        if (list != null && list.size()>0){
            String excelTite = "异常接种情况统计";
            String[] titles = { "儿童编码,childCode","姓名,childName","接种证号,cardNumber", "异常状态,abStatus", "异常详细情况,abnormalDetail" , "性别,sex","出生日期,birthTime","居委会|行政村,committee",
                    "入学|入托机构,school", "居住属性,residenceStatus", "现居地址,currentAddress", "户籍地址,residenceAddress", "联系电话,chilMobile"};
            String manName = ShiroUtils.getUserEntity().getRealName()==null?"":ShiroUtils.getUserEntity().getRealName();
            ExcelUtil.export(response,manName,excelTite,titles,list);
        }

    }
    /**
     * @Description: 打印文件
     * @Param: [request, response, startDate, endDate]
     * @return: void
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/9/11 9:38
     * @Tel  17328595627
     */
    @RequestMapping("/print")
    @ResponseBody
    public void printPDF(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "chilCommittees[]", required = false) String[] chilCommittees,
                            @RequestParam(value = "chilResidences[]", required = false) String[] chilResidences,
                            @RequestParam(value = "infostatus[]", required = false) String[] infostatus,
                            @RequestParam(value = "schools[]", required = false) String[] schools){

        Map paramMap = HttpServletRequestToMapUtils.requestToMap(request);
        //默认查询最近一月的数据
        if (paramMap.get("inoculateStart")==null&&paramMap.get("inoculateEnd")==null){
            Date nowDate = new Date();
            Date date = DateUtils.addDays(nowDate, -30);
            paramMap.put("inoculateStart",date);
            paramMap.put("inoculateEnd",nowDate);
        }
        if (chilCommittees!=null&&chilCommittees.length>0){
            paramMap.put("chilCommittees", Arrays.asList(chilCommittees));
        }
        if (chilResidences!=null&&chilResidences.length>0){
            paramMap.put("chilResidences",Arrays.asList(chilResidences));
        }
        if (schools!=null&&schools.length>0){
            paramMap.put("schools",Arrays.asList(schools));
        }
        if (infostatus!=null&&infostatus.length>0){
            paramMap.put("infostatus",Arrays.asList(infostatus));
        }
        Integer limit = Integer.valueOf(paramMap.get("limit").toString());
        Integer page = Integer.valueOf(paramMap.get("page").toString());
        paramMap.put("offset", (page - 1) * limit);
        paramMap.put("limit", limit);
        List<Map<String,Object>> list=abnormalService.queryList(paramMap);
        if (list != null && list.size()>0){
            String excelTite = "异常接种情况统计";
            String[] titles = { "儿童编码,childCode","姓名,childName","接种证号,cardNumber", "异常状态,abStatus", "异常详细情况,abnormalDetail" , "性别,sex","出生日期,birthTime","居委会|行政村,committee",
                    "入学|入托机构,school", "居住属性,residenceStatus", "现居地址,currentAddress", "户籍地址,residenceAddress", "联系电话,chilMobile"};
            PDFUtils.commonPrintPDF(request,response,"abnormalStatistics",titles,list,excelTite);
        }

    }
}
