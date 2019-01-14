package io.yfjz.controller.report;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.report.VaccineUseStatisticsService;
import io.yfjz.utils.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: 疫苗使用情况统计 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/10 14:10
* @Tel  17328595627
*/
@Controller
@RequestMapping("/vaccineUse")
public class VaccineUseStatisticsController {

    @Autowired
    private VaccineUseStatisticsService vaccineUseStatisticsService;

    @RequestMapping("/list")
    @ResponseBody
    public R getList(Integer page, Integer limit, String startDate, String endDate){

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
        queryMap.put("page",page);
        queryMap.put("offset", (page - 1) * limit);
        queryMap.put("limit",limit);
        List<Map<String,Object>> list=vaccineUseStatisticsService.queryList(queryMap);
        PageUtils pageUtil = new PageUtils(list, list.size(), limit, page);
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
        queryMap.put("limit",10000);
        List<Map<String,Object>> list=vaccineUseStatisticsService.queryList(queryMap);

        if (list != null && list.size()>0){
            String excelTite = "疫苗使用记录统计表";
            String[] titles = { "工作台,towerName","医生,username","疫苗名称,productName","疫苗归属,belong", "批号,batchnum", "失效期,expiryDate" , "人份转换,conversion","领用人份数,receivePersonAmount","使用人份数,usePersonAmount",
                    "报损人份数,damagePersonAmount", "归还人份数,returnAmount"};
            String manName = ShiroUtils.getUserEntity().getRealName() == null ? "" : ShiroUtils.getUserEntity().getRealName();
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
    public void printPDF(HttpServletRequest request,HttpServletResponse response,  String startDate, String endDate)
    {
        //查询统计数据
        try {
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
            queryMap.put("limit",10000);
            List<Map<String,Object>> list=vaccineUseStatisticsService.queryList(queryMap);

            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            //获取Jasper文件路径
            String path = request.getRealPath("/");
            String filepath = path +"reports/vaccineUseStatistics.jrxml";
            //获取JasperReport对象
            JasperReport jreport = JasperCompileManager.compileReport(filepath);
            Map<String, Object> params = new HashMap<String,Object>();
            //把数据放到Map中
                params.put("nowDate",sdf.format(new Date()));
                params.put("doctorName", userEntity.getRealName());
            JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
            // print对象
            JasperPrint print = JasperFillManager.fillReport(jreport, params,
                    dataSource);
            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "inline; filename="
                    + new String("疫苗出入库统计".getBytes("UTF-8")) + ".pdf" + "");

            JRAbstractExporter exporter = new JRPdfExporter();
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                    response.getOutputStream());
            ExporterInput exporterInput = new SimpleExporterInput(print);

            exporter.setExporterOutput(exporterOutput);
            // 设置输入项
            exporter.setExporterInput(exporterInput);
            exporter.exportReport();
            exporterOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
