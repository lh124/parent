package io.yfjz.controller.report;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.report.ChildServiceStatisticsService;
import io.yfjz.service.report.WorkStatisticsService;
import io.yfjz.utils.*;
import io.yfjz.utils.pdf.PDFUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @Description: 儿童全程服务记录
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/23 11:44
* @Tel  17328595627
*/ 
@RequestMapping("childService")
@Controller
public class ChildServiceStatisticsController {

    @Autowired
    private ChildServiceStatisticsService childService;

    @RequestMapping("/list")
    @ResponseBody
    public R getList(Integer page, Integer limit,String startDate,String endDate,String childName){

        Map<String, Object> queryMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //默认查询当天数据
        if (StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
            queryMap.put("startDate",sdf.format(new Date()));
            queryMap.put("endDate",sdf.format(new Date()));
        }else{
            Date start=CommonUtils.transformDate(startDate);
            Date end=CommonUtils.transformDate(endDate);
            queryMap.put("startDate",sdf.format(start));
            queryMap.put("endDate",sdf.format(end));
        }
        if (!StringUtils.isEmpty(childName)){
            queryMap.put("childName",childName);
        }
        queryMap.put("page",page);
        queryMap.put("offset", (page - 1) * limit);
        queryMap.put("limit",limit);
        List<Map<String,Object>> list=childService.queryList(queryMap);
        int total=childService.queryTotal(queryMap);
        PageUtils pageUtil = new PageUtils(list, total, limit, page);
        return R.ok().put("page", pageUtil);

    }
    @RequestMapping("/excel")
    @ResponseBody
    public void outPutExcel(HttpServletResponse response, Integer page, Integer limit, String startDate, String endDate){

        Map<String, Object> queryMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
            queryMap.put("startDate",sdf.format(new Date()));
            queryMap.put("endDate",sdf.format(new Date()));
        }else{
            Date start=CommonUtils.transformDate(startDate);
            Date end=CommonUtils.transformDate(endDate);
            queryMap.put("startDate",start);
            queryMap.put("endDate",end);
        }
        queryMap.put("page",1);
        queryMap.put("offset", 0);
        List<Map<String,Object>> list=childService.queryList(queryMap);

        if (list != null && list.size()>0){
            String excelTite = "全程服务记录表";
            String[] titles = { "儿童编码,childId","儿童姓名,childName","取号时间,printCodeTime", "取号号码,number", "登记时间,registerTime" , "登记医生,registerDoctor","登记疫苗,registerVaccine","取消登记,cancelRegister",
                    "取消登记原因,cancelReason", "接种时间,inoculateTime", "接种医生,inoculateDoctor", "接种疫苗,inoculateVaccine", "疫苗批号,inoculateBatchnum", "取消接种,inoculateCancel", "取消接种原因,inoculateCancelReason", "是否完成留观,isObserve"};
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
    public void printPDF(HttpServletRequest request, HttpServletResponse response, String startDate, String endDate)
    {
        Map<String, Object> queryMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){

            Date start= CommonUtils.transformDate(startDate);
            Date end=CommonUtils.transformDate(endDate);
            queryMap.put("startDate",start);
            queryMap.put("endDate",end);
        }
        queryMap.put("page",1);
        queryMap.put("offset", 0);
        List<Map<String,Object>> list=childService.queryList(queryMap);

        if (list != null && list.size()>0){
            String excelTite = "全程服务记录表";

            String[] titles = { "儿童编码,childId","儿童姓名,childName","取号时间,printCodeTime", "取号号码,number", "登记时间,registerTime" , "登记医生,registerDoctor","登记疫苗,registerVaccine","取消登记,cancelRegister",
                    "取消登记原因,cancelReason", "接种时间,inoculateTime", "接种医生,inoculateDoctor", "接种疫苗,inoculateVaccine", "疫苗批号,inoculateBatchnum", "取消接种,inoculateCancel", "取消接种原因,inoculateCancelReason", "是否完成留观,isObserve"};

            PDFUtils.commonPrintPDF(request,response,"childServiceStatistics",titles,list,excelTite);
        }
    }
}
