package io.yfjz.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Excel 导出通用工具类
 * @describe:
 * @param
 * @return
 * @author 邓召仕
 * @date: 2018-08-30  13:46
 **/
public class ExcelUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static void export1(HttpServletResponse response,String manName, String sheetTitle, String[] title,String[] title1,String[] title2, List list,List list1,int count,List list2,int count1) {

        HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
        HSSFSheet sheet = wb.createSheet(sheetTitle);
        sheet.setDefaultColumnWidth(20);//设置默认行宽

        //表头样式（加粗，水平居中，垂直居中）
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        //cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //设置边框样式
        //cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        // cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        //cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        // cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        cellStyle.setFont(fontStyle);

        //标题样式（加粗，垂直居中）
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        //cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle2.setFont(fontStyle);

        //设置边框样式
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //字段样式（垂直居中）
        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        // cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中

        //设置边框样式
        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //创建表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);//行高

        HSSFCell cell = row.createCell(0);
        cell.setCellValue(sheetTitle);
        cell.setCellStyle(cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,(title.length-1)));
//        sheet.addMergedRegion(new CellRangeAddress(list.size()+3,list.size()+3,0,(title1.length-1)));
//打印人信息
        HSSFRow rowMan = sheet.createRow(1);
        rowMan.setHeightInPoints(30);
        //创建标题
        HSSFRow rowTitle = sheet.createRow(2);
        rowTitle.setHeightInPoints(20);


        HSSFCell hc;
        HSSFCell dateHc;
        for (int i = 0; i < title.length; i++) {
            hc = rowTitle.createCell(i);
            String titleStr = title[i];
            hc.setCellValue(titleStr.substring(0,titleStr.lastIndexOf(",")));
            hc.setCellStyle(cellStyle2);

            //创建打印时间以打印人
            dateHc = rowMan.createCell(i);
            if (i==0){
                dateHc.setCellValue("日期： "+sdf.format(new Date()));
            }else if (i == title.length-1){
                dateHc.setCellValue("打印医生："+manName);
            }
        }
//        sheet.addMergedRegion(new CellRangeAddress(6,6,0,(title1.length-1)));
        HSSFRow  rowTitle1 = sheet.createRow(count+4);
        rowTitle1.setHeightInPoints(20);
        for (int i = 0; i < title1.length; i++) {
            hc = rowTitle1.createCell(i);
            String titleStr = title1[i];
            hc.setCellValue(titleStr.substring(0,titleStr.lastIndexOf(",")));
            hc.setCellStyle(cellStyle2);

        }
        HSSFRow  rowTitle2= sheet.createRow(count+count1+6);
        rowTitle2.setHeightInPoints(20);
        for (int j=0;j<title2.length;j++){
            hc = rowTitle2.createCell(j);
            String titleStr1 = title2[j];
            hc.setCellValue(titleStr1.substring(0,titleStr1.lastIndexOf(",")));
            hc.setCellStyle(cellStyle2);
        }


        //填充数据

        try {
            //创建表格数据
            int i = 3;

            for (Object obj : list) {

                HSSFRow rowBody = sheet.createRow(i);
                rowBody.setHeightInPoints(20);

                for (int j = 0 ;j < title.length; j++){
                    String titleStr = title[j];
                    String fieldName = titleStr.substring(titleStr.lastIndexOf(",")+1,titleStr.length());

                    Object va=null;
                    if (obj instanceof Map){
                        va = ((Map) obj).get(fieldName);
                    }else {
                        Field f = obj.getClass().getDeclaredField(fieldName);
                        if (f != null){
                            f.setAccessible(true);
                            va = f.get(obj);
                        }
                    }
                    if (null == va) {
                        va = "";
                    }
                    //日期判断
                    if(va instanceof Date){
                        va = sdf.format(va);
                    }

                    hc = rowBody.createCell(j);
                    hc.setCellValue(va.toString());
                    hc.setCellStyle(cellStyle3);
                }

                i++;

            }
            for(Object obj1 : list1){
                HSSFRow   rowBody = sheet.createRow(i+2);
                rowBody.setHeightInPoints(20);
                for (int j = 0 ;j < title1.length; j++){
                    String titleStr = title1[j];
                    String fieldName = titleStr.substring(titleStr.lastIndexOf(",")+1,titleStr.length());

                    Object va=null;
                    if (obj1 instanceof Map){
                        va = ((Map) obj1).get(fieldName);
                    }else {
                        Field f = obj1.getClass().getDeclaredField(fieldName);
                        if (f != null){
                            f.setAccessible(true);
                            va = f.get(obj1);
                        }
                    }
                    if (null == va) {
                        va = "";
                    }
//                    //日期判断
                    if(va instanceof Date){
                        va = sdf.format(va);
                    }

                    hc = rowBody.createCell(j);
                    hc.setCellValue(va.toString());
                    hc.setCellStyle(cellStyle3);
                }
                i++;
            }

            for(Object obj2 : list2){
                HSSFRow   rowBody = sheet.createRow(i+4);
                rowBody.setHeightInPoints(20);
                for (int x = 0 ;x < title2.length; x++){
                    String titleStr = title2[x];
                    String fieldName = titleStr.substring(titleStr.lastIndexOf(",")+1,titleStr.length());

                    Object va=null;
                    if (obj2 instanceof Map){
                        va = ((Map) obj2).get(fieldName);
                    }else {
                        Field f = obj2.getClass().getDeclaredField(fieldName);
                        if (f != null){
                            f.setAccessible(true);
                            va = f.get(obj2);
                        }
                    }
                    if (null == va) {
                        va = "";
                    }
//                    //日期判断
                    if(va instanceof Date){
                        va = sdf.format(va);
                    }

                    hc = rowBody.createCell(x);
                    hc.setCellValue(va.toString());
                    hc.setCellStyle(cellStyle3);
                }
                i++;
            }

            if(sheetTitle ==null){
                sheetTitle = "导出";
            }
            sheetTitle = sheetTitle +".xls";
            ServletOutputStream out=response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(sheetTitle.getBytes(), "iso-8859-1"));
            response.setContentType("application/msexcel");
            wb.write(out);
            out.flush();
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(ExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void export(HttpServletResponse response,String manName, String sheetTitle, String[] title, List list) {

        HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
        HSSFSheet sheet = wb.createSheet(sheetTitle);
        sheet.setDefaultColumnWidth(20);//设置默认行宽

        //表头样式（加粗，水平居中，垂直居中）
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        //cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //设置边框样式
        //cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        // cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        //cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        // cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        cellStyle.setFont(fontStyle);

        //标题样式（加粗，垂直居中）
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        //cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle2.setFont(fontStyle);

        //设置边框样式
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //字段样式（垂直居中）
        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        // cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中

        //设置边框样式
        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //创建表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);//行高

        HSSFCell cell = row.createCell(0);
        cell.setCellValue(sheetTitle);
        cell.setCellStyle(cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,(title.length-1)));
//打印人信息
        HSSFRow rowMan = sheet.createRow(1);
        rowMan.setHeightInPoints(30);
        //创建标题
        HSSFRow rowTitle = sheet.createRow(2);
        rowTitle.setHeightInPoints(20);

        HSSFCell hc;
        HSSFCell dateHc;
        for (int i = 0; i < title.length; i++) {
            hc = rowTitle.createCell(i);
            String titleStr = title[i];
            hc.setCellValue(titleStr.substring(0,titleStr.lastIndexOf(",")));
            hc.setCellStyle(cellStyle2);

            //创建打印时间以打印人
            dateHc = rowMan.createCell(i);
            if (i==0){
                dateHc.setCellValue("日期： "+sdf.format(new Date()));
            }else if (i == title.length-1){
                dateHc.setCellValue("打印医生："+manName);
            }
        }


        //填充数据

        try {
            //创建表格数据
            int i = 3;

            for (Object obj : list) {

                HSSFRow rowBody = sheet.createRow(i);
                rowBody.setHeightInPoints(20);

                for (int j = 0 ;j < title.length; j++){
                    String titleStr = title[j];
                    String fieldName = titleStr.substring(titleStr.lastIndexOf(",")+1,titleStr.length());

                    Object va=null;
                    if (obj instanceof Map){
                        va = ((Map) obj).get(fieldName);
                    }else {
                        Field f = obj.getClass().getDeclaredField(fieldName);
                        if (f != null){
                            f.setAccessible(true);
                            va = f.get(obj);
                        }
                    }
                    if (null == va) {
                        va = "";
                    }
                    //日期判断
                    if(va instanceof Date){
                        va = sdf.format(va);
                    }

                    hc = rowBody.createCell(j);
                    hc.setCellValue(va.toString());
                    hc.setCellStyle(cellStyle3);
                }

                i++;
            }

            if(sheetTitle ==null){
                sheetTitle = "导出";
            }
            sheetTitle = sheetTitle +".xls";
            ServletOutputStream out=response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(sheetTitle.getBytes(), "iso-8859-1"));
            response.setContentType("application/msexcel");
            wb.write(out);
            out.flush();
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(ExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
