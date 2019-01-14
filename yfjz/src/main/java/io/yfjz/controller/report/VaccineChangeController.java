package io.yfjz.controller.report;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.report.VaccineChangeService;
import io.yfjz.utils.*;
import io.yfjz.utils.pdf.PDFUtils;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
* @Description: 库存产品变动统计
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/8 16:01
* @Tel  17328595627
*/ 
@Controller
@RequestMapping("vaccineChange")
public class VaccineChangeController {

    @Autowired
    private VaccineChangeService vaccineChangeService;

    @RequestMapping("/list")
    @ResponseBody
    public R getList( String year, String month){

        Map<String, Object> queryMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if (!StringUtils.isEmpty(year)&&!StringUtils.isEmpty(month)){
            queryMap.put("year",year);
            queryMap.put("month",month);
        }
        List<Map<String,Object>> list=vaccineChangeService.queryList(queryMap);
        PageUtils pageUtil = new PageUtils(list, list.size(), 1000, 1);
        return R.ok().put("page", pageUtil);

    }
    /** 
    * @Description: 查询盘点记录时间 
    * @Param: [] 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/9 16:12
    * @Tel  17328595627
    */ 
    @RequestMapping("/getCheckTimeList")
    @ResponseBody
    public List<Map<String, Object>> getCheckTimeList(){

        List<Map<String, Object>> checkTimeList = vaccineChangeService.getCheckTimeList();

        return checkTimeList;
    }
    @RequestMapping("/excel")
    @ResponseBody
    public void outPutExcel(HttpServletResponse response, String year, String month){

        try {
            Map<String, Object> queryMap = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            if (!StringUtils.isEmpty(year)&&!StringUtils.isEmpty(month)){
                queryMap.put("year",year);
                queryMap.put("month",month);
            }
            List<Map<String,Object>> list=vaccineChangeService.queryList(queryMap);
            //画表
            HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
            String sheetTitle="疫苗出入库统计表";
            HSSFSheet sheet = wb.createSheet(sheetTitle);
            sheet.setDefaultColumnWidth(15);//设置默认行宽

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
            HSSFRow row0 = sheet.createRow(0);
            HSSFRow row2 = sheet.createRow(2);
            // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell0 = row0.createCell(0);
            HSSFCell cell01 = row2.createCell(11);
            HSSFCell cell02 = row2.createCell(12);
            HSSFCell cell1 = row2.createCell(0);
            HSSFCell cell2 = row2.createCell(1);
            // HSSFCell cell2 = row1.createCell();
            // 设置单元格内容
            cell1.setCellValue("统计日期：");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            cell2.setCellValue(sf.format(new Date()));
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            cell01.setCellValue("统计人：");
            cell02.setCellValue(userEntity.getRealName()==null?"":userEntity.getRealName());
            cell0.setCellValue("疫苗出入库统计表");
            // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 12));
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中;
            HSSFFont font = wb.createFont();
            HSSFCellStyle titleStyle = wb.createCellStyle();
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 20);// 设置字体大小
            titleStyle.setFont(font);
            cell0.setCellStyle(titleStyle);
            // 在sheet里创建第二行
            HSSFRow row3 = sheet.createRow(3);
            HSSFRow row4 = sheet.createRow(4);
            for (int i = 0; i < 13; i++) {
                row3.createCell(i).setCellStyle(setBorder);
                row4.createCell(i).setCellStyle(setBorder);
            }
            row3.getCell(0).setCellValue("疫苗/产品名称");
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
            row3.getCell(1).setCellValue("生产厂家");
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));
            row3.getCell(2).setCellValue("批号");
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 2, 2));
            row3.getCell(3).setCellValue("失效期");
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 3, 3));
            row3.getCell(4).setCellValue("人份转换");
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 4, 4));
            row3.getCell(5).setCellValue("上月结余");
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 5, 5));
            row3.getCell(6).setCellValue("本月发生");
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 11));
            row4.getCell(6).setCellValue("入库数量");
            row4.getCell(7).setCellValue("报损数量");
            row4.getCell(8).setCellValue("退货数量");
            row4.getCell(9).setCellValue("使用数量(含损耗数量)");
            row4.getCell(10).setCellValue("使用人份");
            row4.getCell(11).setCellValue("损耗人份");
            row3.getCell(12).setCellValue("本月结余");
            sheet.addMergedRegion(new CellRangeAddress(3, 4, 12, 12));
            //填充数据
            for (int i = 0; i <list.size() ; i++) {
                HSSFRow rows = sheet.createRow(i + 5);
                Map<String, Object> map = list.get(i);
                for (int j = 0; j < 13; j++) {
                    rows.createCell(j).setCellStyle(setBorder);
                }
                rows.getCell(0).setCellValue(map.get("productName")==null?"":map.get("productName").toString());
                rows.getCell(1).setCellValue(map.get("factoryName")==null?"":map.get("factoryName").toString());
                rows.getCell(2).setCellValue(map.get("batchnum")==null?"":map.get("batchnum").toString());
                rows.getCell(3).setCellValue(map.get("expiryDate")==null?"":map.get("expiryDate").toString());
                rows.getCell(4).setCellValue(map.get("conversion")==null?"":map.get("conversion").toString());
                rows.getCell(5).setCellValue(map.get("initAmount")==null?"":map.get("initAmount").toString());
                rows.getCell(6).setCellValue(map.get("inputAmount")==null?"":map.get("inputAmount").toString());
                rows.getCell(7).setCellValue(map.get("damageAmount")==null?"":map.get("damageAmount").toString());
                rows.getCell(8).setCellValue(map.get("returnAmount")==null?"":map.get("returnAmount").toString());
                rows.getCell(9).setCellValue(map.get("useAmount")==null?"":map.get("useAmount").toString());
                rows.getCell(10).setCellValue(map.get("usePersonAmount")==null?"":map.get("usePersonAmount").toString());
                rows.getCell(11).setCellValue(map.get("damagePersonAmount")==null?"":map.get("damagePersonAmount").toString());
                rows.getCell(12).setCellValue(map.get("endAmount")==null?"":map.get("endAmount").toString());
            }
            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            System.out.println("output=" + output);
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+new String("疫苗出入库统计表.xls".getBytes(), "iso-8859-1"));
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
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
    public void printPDF(HttpServletRequest request, HttpServletResponse response, String year, String month)
    {
        Map<String, Object> queryMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if (!StringUtils.isEmpty(year)&&!StringUtils.isEmpty(month)){
            queryMap.put("year",year);
            queryMap.put("month",month);
        }
        List<Map<String,Object>> list=vaccineChangeService.queryList(queryMap);

        if (list != null && list.size()>0){
            String excelTite = "疫苗出入库统计表";
            String[] titles = { "疫苗/产品名称,productName","生产厂家,factoryName", "批号,batchnum", "失效期,expiryDate" , "人份转换,conversion","上月结余,initAmount","入库数量,inputAmount",
                    "报损数量,damageAmount", "退货数量,returnAmount", "使用数量,useAmount", "使用人份,usePersonAmount", "损耗人份,damagePersonAmount", "本月结余,endAmount"};

            PDFUtils.commonPrintPDF(request,response,"vaccineChange",titles,list,excelTite);
        }
    }


}
