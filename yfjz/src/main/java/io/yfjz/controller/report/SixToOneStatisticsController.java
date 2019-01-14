package io.yfjz.controller.report;

import io.yfjz.entity.statistics.SixToOneResult;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.report.SixToOneStatisticsService;
import io.yfjz.utils.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: 6-1报表 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/29 9:51
* @Tel  17328595627
*/
@Controller
@RequestMapping("sixToOne")
public class SixToOneStatisticsController {
    @Autowired
    private SixToOneStatisticsService sixToOneService;


    @RequestMapping("/list")
    @ResponseBody
    public R getList(String residence,String year,String month){

        Map<String, Object> queryMap = new HashMap<>();

        /*String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
        List<Map<String,Object>> list=sixToOneService.queryList(queryMap,residence);
        return R.ok().put("page", list);

    }
    @RequestMapping("/excel")
    @ResponseBody
    public void excel(HttpServletResponse response,String residence, String year, String month) throws IOException {

        Map<String, Object> queryMap = new HashMap<>();

        /*String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
        List<Map<String,Object>> list=sixToOneService.queryList(queryMap,residence);

        //画表
        HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
        String sheetTitle="国家免规疫苗常规接种情况表(6-1)";
        HSSFSheet sheet = wb.createSheet(sheetTitle);
        sheet.setDefaultColumnWidth(5);//设置默认行宽

        //表头样式（加粗，水平居中，垂直居中）
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中


        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle setBorder = wb.createCellStyle();
        cellStyle.setFont(fontStyle);

        //设置边框:
        setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

        //创建表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);//行高

        HSSFCell cell = row.createCell(0);
        cell.setCellValue(sheetTitle);
        cell.setCellStyle(cellStyle);

        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(2);
        HSSFRow row1 = sheet.createRow(0);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell01 = row0.createCell(68);
        HSSFCell cell1 = row1.createCell(0);
        // HSSFCell cell2 = row1.createCell();
        // 设置单元格内容
        cell0.setCellValue("统计日期：");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
        cell01.setCellValue("统计人：");
        cell1.setCellValue("国家免规疫苗常规接种情况表(6-1)");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 70));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中;
        HSSFFont font = wb.createFont();
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 20);// 设置字体大小
        titleStyle.setFont(font);
        cell1.setCellStyle(titleStyle);
        // 在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(3);
        HSSFRow row4 = sheet.createRow(4);
        HSSFRow row5 = sheet.createRow(5);
        for (int i = 0; i < 71; i++) {
            row2.createCell(i);
            row4.createCell(i);
            row5.createCell(i);
        }

        //
        row2.getCell(0).setCellValue("序号");
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 0, 0));
        row2.getCell(1).setCellValue("报告单位");
        sheet.setColumnWidth(1, 4000);
        sheet.addMergedRegion(new CellRangeAddress(3, 5, 1, 1));
        row2.getCell(2).setCellValue("本月(次)应种剂次数");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 35));
        row2.getCell(36).setCellValue("本月(次)实种剂次数");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 36, 70));
        //本月(次)应种剂次数
        row4.getCell(2).setCellValue("HepB");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 4));
        row4.getCell(5).setCellValue("BCG");
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 5, 5));
        row4.getCell(6).setCellValue("PV");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 9));
        row4.getCell(10).setCellValue("DTP");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 10, 13));
        row4.getCell(14).setCellValue("DT");
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 14, 14));
        row4.getCell(15).setCellValue("MR");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 15, 16));
        row4.getCell(17).setCellValue("MMR");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 17, 18));
        row4.getCell(19).setCellValue("MM");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 19, 20));
        row4.getCell(21).setCellValue("MV");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 21, 22));
        row4.getCell(23).setCellValue("MenA");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 23, 24));
        row4.getCell(25).setCellValue("MenAC");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 25, 26));
        row4.getCell(27).setCellValue("JE-L");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 27, 28));
        row4.getCell(29).setCellValue("JE-I");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 29, 32));
        row4.getCell(33).setCellValue("HepA-L");
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 33, 33));
        row4.getCell(34).setCellValue("HepA-I");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 34, 35));
        //本月(次)实种剂次数
        row4.createCell(36).setCellValue("HepB");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 36, 39));
        row4.createCell(40).setCellValue("BCG");
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 40, 40));
        row4.createCell(41).setCellValue("PV");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 41, 44));
        row4.createCell(45).setCellValue("DTP");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 45, 48));
        row4.createCell(49).setCellValue("DT");
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 49, 49));
        row4.createCell(50).setCellValue("MR");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 50, 51));
        row4.createCell(52).setCellValue("MMR");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 52, 53));
        row4.createCell(54).setCellValue("MM");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 54, 55));
        row4.createCell(56).setCellValue("MV");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 56, 57));
        row4.createCell(58).setCellValue("MenA");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 58, 59));
        row4.createCell(60).setCellValue("MenAC");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 60, 61));
        row4.createCell(62).setCellValue("JE-L");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 62, 63));
        row4.createCell(64).setCellValue("JE-I");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 64, 67));
        row4.createCell(68).setCellValue("HepA-L");
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 68, 68));
        row4.createCell(69).setCellValue("HepA-I");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 69, 70));
        //1	2	3	1	2	3	4	1	2	3	4/	1	2	1	2	1	2	1	2	1	2	1	2	1	2	1	2	3	4	1	2
        row5.createCell(2).setCellValue(1);
        row5.createCell(3).setCellValue(2);
        row5.createCell(4).setCellValue(3);

        row5.createCell(6).setCellValue(1);
        row5.createCell(7).setCellValue(2);
        row5.createCell(8).setCellValue(3);
        row5.createCell(9).setCellValue(4);

        row5.createCell(10).setCellValue(1);
        row5.createCell(11).setCellValue(2);
        row5.createCell(12).setCellValue(3);
        row5.createCell(13).setCellValue(4);

        row5.createCell(15).setCellValue(1);
        row5.createCell(16).setCellValue(2);

        row5.createCell(17).setCellValue(1);
        row5.createCell(18).setCellValue(2);

        row5.createCell(19).setCellValue(1);
        row5.createCell(20).setCellValue(2);

        row5.createCell(21).setCellValue(1);
        row5.createCell(22).setCellValue(2);

        row5.createCell(23).setCellValue(1);
        row5.createCell(24).setCellValue(2);

        row5.createCell(25).setCellValue(1);
        row5.createCell(26).setCellValue(2);

        row5.createCell(27).setCellValue(1);
        row5.createCell(28).setCellValue(2);

        row5.createCell(29).setCellValue(1);
        row5.createCell(30).setCellValue(2);
        row5.createCell(31).setCellValue(3);
        row5.createCell(32).setCellValue(4);

        row5.createCell(34).setCellValue(1);
        row5.createCell(35).setCellValue(2);

        // 1	及时	2	3	1	2	3	4	1	2	3	4	1	2	1	2	1	2	1	2	1	2	1	2	1	2	1	2	3	4	1	2
        row5.createCell(36).setCellValue(1);
        row5.createCell(37).setCellValue("及时");
        row5.createCell(38).setCellValue(2);
        row5.createCell(39).setCellValue(3);

        row5.createCell(41).setCellValue(1);
        row5.createCell(42).setCellValue(2);
        row5.createCell(43).setCellValue(3);
        row5.createCell(44).setCellValue(4);

        row5.createCell(45).setCellValue(1);
        row5.createCell(46).setCellValue(2);
        row5.createCell(47).setCellValue(3);
        row5.createCell(48).setCellValue(4);

        row5.createCell(50).setCellValue(1);
        row5.createCell(51).setCellValue(2);

        row5.createCell(52).setCellValue(1);
        row5.createCell(53).setCellValue(2);

        row5.createCell(54).setCellValue(1);
        row5.createCell(55).setCellValue(2);

        row5.createCell(56).setCellValue(1);
        row5.createCell(57).setCellValue(2);

        row5.createCell(58).setCellValue(1);
        row5.createCell(59).setCellValue(2);

        row5.createCell(60).setCellValue(1);
        row5.createCell(61).setCellValue(2);

        row5.createCell(62).setCellValue(1);
        row5.createCell(63).setCellValue(2);

        row5.createCell(64).setCellValue(1);
        row5.createCell(65).setCellValue(2);
        row5.createCell(66).setCellValue(3);
        row5.createCell(67).setCellValue(4);

        row5.createCell(69).setCellValue(1);
        row5.createCell(70).setCellValue(2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        row0.createCell(2).setCellValue(sdf.format(new Date()));
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        row0.createCell(69).setCellValue(userEntity.getRealName()==null?"":userEntity.getRealName());
        //设置边框
        for (int i = 0; i < 71; i++) {
            if (row2.getCell(i)!=null){
                row2.getCell(i).setCellStyle(setBorder);
            }
            if (row4.getCell(i)!=null){
                row4.getCell(i).setCellStyle(setBorder);
            }
            if (row5.getCell(i)!=null){
                row5.getCell(i).setCellStyle(setBorder);
            }

        }
        //导出数据

        for (int i = 0; i <list.size() ; i++) {
            Map<String, Object> map = list.get(i);
            HSSFRow rows = sheet.createRow(i+6);
            rows.createCell(0).setCellValue(i+1);
            rows.createCell(1).setCellValue(map.get("committee").toString());
            //本月应种
            rows.createCell(2).setCellValue(map.get("HepB1s").toString());
            rows.createCell(3).setCellValue(map.get("HepB2s").toString());
            rows.createCell(4).setCellValue(map.get("HepB3s").toString());
            rows.createCell(5).setCellValue(map.get("BCG1s").toString());
            rows.createCell(6).setCellValue(map.get("IPV1s").toString());
            rows.createCell(7).setCellValue(map.get("OPV1s").toString());
            rows.createCell(8).setCellValue(map.get("OPV2s").toString());
            rows.createCell(9).setCellValue(map.get("OPV3s").toString());
            rows.createCell(10).setCellValue(map.get("DTaP1s").toString());
            rows.createCell(11).setCellValue(map.get("DTaP2s").toString());
            rows.createCell(12).setCellValue(map.get("DTaP3s").toString());
            rows.createCell(13).setCellValue(map.get("DTaP4s").toString());
            rows.createCell(14).setCellValue(map.get("DT1s").toString());
            rows.createCell(15).setCellValue(map.get("MR1s").toString());
            rows.createCell(16).setCellValue(0);
            rows.createCell(17).setCellValue(map.get("MMR1s").toString());
            rows.createCell(18).setCellValue(0);

            //mm
            rows.createCell(19).setCellValue(0);
            rows.createCell(20).setCellValue(0);
            //mv
            rows.createCell(21).setCellValue(0);
            rows.createCell(22).setCellValue(0);
            //mena
            rows.createCell(23).setCellValue(0);
            rows.createCell(24).setCellValue(0);
            //menac
            rows.createCell(25).setCellValue(0);
            rows.createCell(26).setCellValue(0);
            //je-l
            rows.createCell(27).setCellValue(map.get("JE-L1s").toString());
            rows.createCell(28).setCellValue(map.get("JE-L2s").toString());
            //je-i
            rows.createCell(29).setCellValue(map.get("JE-I1s").toString());
            rows.createCell(30).setCellValue(map.get("JE-I2s").toString());
            rows.createCell(31).setCellValue(map.get("JE-I3s").toString());
            rows.createCell(32).setCellValue(map.get("JE-I4s").toString());
            //HepA-L
            rows.createCell(33).setCellValue(map.get("HepA-L1s").toString());
            //HepA-I
            rows.createCell(34).setCellValue(map.get("HepA-I1s").toString());
            rows.createCell(35).setCellValue(map.get("HepA-I2s").toString());
            //本月实种
            rows.createCell(36).setCellValue(map.get("HepB1r").toString());
            rows.createCell(37).setCellValue(map.get("HepB1T").toString());
            rows.createCell(38).setCellValue(map.get("HepB2r").toString());
            rows.createCell(39).setCellValue(map.get("HepB3r").toString());

            rows.createCell(40).setCellValue(map.get("BCG1r").toString());

            rows.createCell(41).setCellValue(map.get("IPV1r").toString());
            rows.createCell(42).setCellValue(map.get("OPV1r").toString());
            rows.createCell(43).setCellValue(map.get("OPV2r").toString());
            rows.createCell(44).setCellValue(map.get("OPV3r").toString());

            rows.createCell(45).setCellValue(map.get("DTaP1r").toString());
            rows.createCell(46).setCellValue(map.get("DTaP2r").toString());
            rows.createCell(47).setCellValue(map.get("DTaP3r").toString());
            rows.createCell(48).setCellValue(map.get("DTaP4r").toString());
            rows.createCell(49).setCellValue(map.get("DT1r").toString());
            rows.createCell(50).setCellValue(map.get("MR1r").toString());
            rows.createCell(51).setCellValue(0);
            rows.createCell(52).setCellValue(map.get("MMR1r").toString());
            rows.createCell(53).setCellValue(0);
            //mm
            rows.createCell(54).setCellValue(0);
            rows.createCell(55).setCellValue(0);
            //mv
            rows.createCell(56).setCellValue(0);
            rows.createCell(57).setCellValue(0);
            //mena
            rows.createCell(58).setCellValue(0);
            rows.createCell(59).setCellValue(0);
            //menac
            rows.createCell(60).setCellValue(0);
            rows.createCell(61).setCellValue(0);
            //je-l
            rows.createCell(62).setCellValue(map.get("JE-L1r").toString());
            rows.createCell(63).setCellValue(map.get("JE-L2r").toString());
            //je-i
            rows.createCell(64).setCellValue(map.get("JE-I1r").toString());
            rows.createCell(65).setCellValue(map.get("JE-I2r").toString());
            rows.createCell(66).setCellValue(map.get("JE-I3r").toString());
            rows.createCell(67).setCellValue(map.get("JE-I4r").toString());
            //HepA-L
            rows.createCell(68).setCellValue(map.get("HepA-L1r").toString());
            //HepA-I
            rows.createCell(69).setCellValue(map.get("HepA-I1r").toString());
            rows.createCell(70).setCellValue(map.get("HepA-I2r").toString());
            for (int j = 0; j < 71; j++) {
                rows.getCell(j).setCellStyle(setBorder);
            }


        }
        // 输出Excel文件
        OutputStream output = response.getOutputStream();
//        System.out.println("output=" + output);
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+new String("国家免规疫苗常规接种情况表(6-1).xls".getBytes(), "iso-8859-1"));
        response.setContentType("application/msexcel");
        wb.write(output);
        output.flush();
        output.close();
    }
    @RequestMapping("/print")
    @ResponseBody
    public void printPdf(HttpServletResponse response, HttpServletRequest request,String residence, String year, String month){

        Map<String, Object> queryMap = new HashMap<>();

        try {
           /* String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
            String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
            queryMap.put("startDate",year);
            queryMap.put("endDate",month);
            List<Map<String,Object>> list=sixToOneService.queryList(queryMap,residence);
            for (int i = 0; i <list.size() ; i++) {
                list.get(i).put("ids",i+1);
            }
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            //获取Jasper文件路径
            String path = request.getRealPath("/");
            String filepath = path +"reports/sixToOne.jrxml";
            //获取JasperReport对象
            JasperReport jreport = JasperCompileManager.compileReport(filepath);
            Map<String, Object> params = new HashMap<String,Object>();
            //把数据放到Map中
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            params.put("nowDate",sdf.format(new Date()));
            params.put("doctorName", userEntity.getRealName());
            JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
            // print对象
            JasperPrint print = JasperFillManager.fillReport(jreport, params,
                    dataSource);
            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "inline; filename="
                    + new String("国家免规疫苗常规接种情况表(6-1)".getBytes("UTF-8")) + ".pdf" + "");

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


    @RequestMapping("/totalList")
    @ResponseBody
    public R getTotalList(String residence,String year,String month){

        Map<String, Object> queryMap = new HashMap<>();

        /*String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
        List<SixToOneResult> list = sixToOneService.queryTotalList(queryMap,residence);
        PageUtils pageUtil = new PageUtils(list, 28, 28, 1);
        return R.ok().put("page", pageUtil);

    }


    @RequestMapping("/totalExcel")
    @ResponseBody
    public void totalExcel(HttpServletResponse response, String year, String month) throws IOException {

        Map<String, Object> queryMap = new HashMap<>();

        /*String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
        String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
//        List<Map<String,Object>> list=sixToOneService.queryList(queryMap,residence);

        //画表
        HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
        String sheetTitle="国家免规疫苗常规接种情况表(6-1)";
        HSSFSheet sheet = wb.createSheet(sheetTitle);
        sheet.setDefaultColumnWidth(20);//设置默认行宽

        //表头样式（加粗，水平居中，垂直居中）
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中


        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle setBorder = wb.createCellStyle();
        cellStyle.setFont(fontStyle);

        //设置边框:
        setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

        //创建表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);//行高

        HSSFCell cell = row.createCell(0);
        cell.setCellValue(sheetTitle);
        cell.setCellStyle(cellStyle);

        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(2);
        HSSFRow row1 = sheet.createRow(0);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell01 = row0.createCell(4);
        HSSFCell cell1 = row1.createCell(0);
        // HSSFCell cell2 = row1.createCell();
        // 设置单元格内容
        cell0.setCellValue("统计日期：");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 0));
        cell01.setCellValue("统计人：");
        cell1.setCellValue("国家免规疫苗常规接种情况表(6-1)");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中;
        HSSFFont font = wb.createFont();
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 20);// 设置字体大小
        titleStyle.setFont(font);
        cell1.setCellStyle(titleStyle);
        // 在sheet里创建第二行
        HSSFRow row3 = sheet.createRow(3);
        HSSFRow row4 = sheet.createRow(4);
        for (int i = 0; i <6 ; i++) {
            row3.createCell(i).setCellStyle(setBorder);
            row4.createCell(i).setCellStyle(setBorder);
        }
        row3.getCell(0).setCellValue("疫苗");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
        row3.getCell(1).setCellValue("剂次");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));
        row3.getCell(2).setCellValue("本地");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));
        row3.getCell(4).setCellValue("流动");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 5));
        row4.getCell(2).setCellValue("应种剂次数");
        row4.getCell(3).setCellValue("实种剂次数");
        row4.getCell(4).setCellValue("应种剂次数");
        row4.getCell(5).setCellValue("实种剂次数");



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        row0.createCell(1).setCellValue(sdf.format(new Date()));
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        row0.createCell(5).setCellValue(userEntity.getRealName());



        // 输出Excel文件
        OutputStream output = response.getOutputStream();
        System.out.println("output=" + output);
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+new String("国家免规疫苗常规接种情况表(6-1)汇总.xls".getBytes(), "iso-8859-1"));
        response.setContentType("application/msexcel");
        wb.write(output);
        output.flush();
        output.close();
    }
    @RequestMapping("/totalPrint")
    @ResponseBody
    public void totalPrint(HttpServletResponse response, HttpServletRequest request,String residence, String year, String month){

        Map<String, Object> queryMap = new HashMap<>();

        try {
            /*String lastDayOfMonth = DateUtils.getLastDayOfMonth(year, month);
            String firstDayOfMonth = DateUtils.getFirstDayOfMonth(year, month);*/
            queryMap.put("startDate",year);
            queryMap.put("endDate",month);
            List<Map<String,Object>> list=sixToOneService.queryList(queryMap,residence);
            for (int i = 0; i <list.size() ; i++) {
                list.get(i).put("ids",i+1);
            }
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            //获取Jasper文件路径
            String path = request.getRealPath("/");
            String filepath = path +"reports/sixToOneTotal.jrxml";
            //获取JasperReport对象
            JasperReport jreport = JasperCompileManager.compileReport(filepath);
            Map<String, Object> params = new HashMap<String,Object>();
            //把数据放到Map中
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            params.put("nowDate",sdf.format(new Date()));
            params.put("doctorName", userEntity.getRealName());
            JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
            // print对象
            JasperPrint print = JasperFillManager.fillReport(jreport, params,
                    dataSource);
            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "inline; filename="
                    + new String("国家免规疫苗常规接种情况表(6-1)汇总".getBytes("UTF-8")) + ".pdf" + "");

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

    /**
     * @method_name: 报表上传平台
     * @describe:
     * @param year
     * @param month
     * @return io.yfjz.utils.R
     * @author 饶士培
     * @date: 2018-11-26  10:49
     **/
    @RequestMapping("/upload")
    @ResponseBody
    public R upload(HttpServletResponse response, HttpServletRequest request, String year, String month){

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("startDate",year);
        queryMap.put("endDate",month);
        R rulst = sixToOneService.uploadPlatform(queryMap);
        return rulst;

    }

}
