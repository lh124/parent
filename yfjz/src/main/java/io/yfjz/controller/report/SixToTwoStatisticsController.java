package io.yfjz.controller.report;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.report.SixToTwoStatisticsService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: 6-2报表统计 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/10/16 16:07
* @Tel  17328595627
*/
@Controller
@RequestMapping("sixToTwo")
public class SixToTwoStatisticsController {

    @Autowired
    private SixToTwoStatisticsService sixToTwoService;

    @RequestMapping("/list")
    @ResponseBody
    public R getList( String year, String month){

        Map<String, Object> queryMap = new HashMap<>();
       /* String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
        List<Map<String,Object>> list=sixToTwoService.queryList(queryMap);
        PageUtils pageUtil = new PageUtils(list, list.size(), 100, 1);
        return R.ok().put("page", pageUtil);

    }

    /**
     * @method_name: 报表上传平台
     * @describe:
     * @param year
     * @param month
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-11-13  14:49
     **/
    @RequestMapping("/upload")
    @ResponseBody
    public R upload(String year, String month){
        try{
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("startDate",year);
            queryMap.put("endDate",month);
            R rulst = sixToTwoService.uploadPlatform(queryMap);
            return rulst;
        }catch (Exception e){
            e.printStackTrace();
            return R.error("系统错误,未上传成功！");
        }
    }

    @RequestMapping("/excel")
    @ResponseBody
    public void excle(HttpServletResponse response, HttpServletRequest request, String year, String month){

        Map<String, Object> queryMap = new HashMap<>();

        /*String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
        List<Map<String,Object>> list=sixToTwoService.queryList(queryMap);
        if (list != null && list.size()>0){
            String excelTite = "国家免规疫苗常规接种情况表(6-2)";
            String[] titles = { "疫苗名称,className","接种剂次数,number","疫苗名称,classNamePlus","接种剂次数,numberPlus"};
            String manName = ShiroUtils.getUserEntity().getRealName();
            ExcelUtil.export(response,manName==null||manName==""?"":manName,excelTite,titles,list);
        }

    }
    @RequestMapping("/print")
    @ResponseBody
    public void print( HttpServletResponse response, HttpServletRequest request, String year, String month){

        Map<String, Object> queryMap = new HashMap<>();

        /*String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
        List<Map<String,Object>> list=sixToTwoService.queryList(queryMap);
        if (list != null && list.size()>0){
            try {
                SysUserEntity userEntity = ShiroUtils.getUserEntity();
                //获取Jasper文件路径
                String path = request.getRealPath("/");
                String filepath = path +"reports/sixToTwo.jrxml";
                //获取JasperReport对象
                JasperReport jreport = JasperCompileManager.compileReport(filepath);
                Map<String, Object> params = new HashMap<String,Object>();
                //把数据放到Map中
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                params.put("nowDate",sdf.format(new Date()));
                params.put("doctorName", userEntity.getRealName());
                params.put("year", year.substring(0,4)+"年");
                params.put("month", year.substring(5,7)+"月");
                params.put("orgId", Constant.GLOBAL_ORG_ID);
                params.put("orgName",Constant.GLOBAL_ORG_NAME);
                JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
                // print对象
                JasperPrint print = JasperFillManager.fillReport(jreport, params,
                        dataSource);
                response.setContentType("application/pdf");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "inline; filename="
                        + new String("国家免规疫苗常规接种情况表(6-2)".getBytes("UTF-8")) + ".pdf" + "");

                JRAbstractExporter exporter = new JRPdfExporter();
                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                        response.getOutputStream());
                ExporterInput exporterInput = new SimpleExporterInput(print);

                exporter.setExporterOutput(exporterOutput);
                // 设置输入项
                exporter.setExporterInput(exporterInput);
                exporter.exportReport();
                exporterOutput.close();
            } catch (JRException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
