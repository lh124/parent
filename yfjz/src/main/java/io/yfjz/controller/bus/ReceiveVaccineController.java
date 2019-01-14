package io.yfjz.controller.bus;

import io.yfjz.service.bus.ReceiveService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import io.yfjz.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 领取疫苗
 * @author tianjinhai
 * @date 2018/8/11 14:19
 */
@RequestMapping("receive")
@Controller
public class ReceiveVaccineController
{

    @Autowired
    private ReceiveService receiveService;
    /** 
    * @Description: 领取疫苗操作 
    * @Param: [param] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/15 15:47
    * @Tel  17328595627
    */ 
    @RequestMapping("/vaccine")
    @ResponseBody
    public R receive(@RequestBody Map param){
        try {
            int result=receiveService.saveReceiveVac(param);
        } catch (RRException e) {

            e.printStackTrace();
            return R.error(-1,e.getMsg());
        }catch (Exception e) {

            e.printStackTrace();
            return R.error(-1,"领取失败！系统异常请联系管理员！");
        }

        return R.ok();
    }
    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(Integer page, Integer limit,String type,String searchProductName,String searchFactoryName,String searchBatch,String  searchDate, Integer searchType,String storeId){
        PageUtils pageUtil = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
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
            if(!StringUtils.isEmpty(searchDate)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(searchDate);
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
            List<Map<String, Object>> list = receiveService.queryStockList(map);
            int total = receiveService.queryStockTotal(map);

            pageUtil = new PageUtils(list, total, limit, page);
        } catch (ParseException e) {

            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("page", pageUtil);
    }
    /** 
    * @Description: 查询已经领取的疫苗
    * @Param: [page, limit] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 11:32
    * @Tel  17328595627
    */ 
    @ResponseBody
    @RequestMapping("/receiveList")
    public R receiveList(Integer page, Integer limit,String type){
        PageUtils pageUtil = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            map.put("type", type);


            //查询列表数据
            List<Map<String, Object>> list = receiveService.queryReceiveList(map);
            int total = receiveService.queryReceiveListTotal(map);

            pageUtil = new PageUtils(list, total, limit, page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("page", pageUtil);
    }
    /**
     * @Description: 查询领取总记录
     * @Param: [page, limit]
     * @return: io.yfjz.utils.R
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/8/14 11:32
     * @Tel  17328595627
     */
    @ResponseBody
    @RequestMapping("/receiveTotalList")
    public R receiveTotalList(Integer page, Integer limit){
        PageUtils pageUtil = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);

            //查询列表数据
            List<Map<String, Object>> list = receiveService.queryReceiveTotalList(map);
            int total = receiveService.queryReceiveTotalListTotal(map);

            pageUtil = new PageUtils(list, total, limit, page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("page", pageUtil);
    }

    /**
     * @Description: 查询领取明细记录
     * @Param: [page, limit]
     * @return: io.yfjz.utils.R
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/8/14 11:32
     * @Tel  17328595627
     */
    @ResponseBody
    @RequestMapping("/receiveItemList/{rowId}")
    public R receiveItemList(Integer page, Integer limit, @PathVariable("rowId") String rowId){
        PageUtils pageUtil = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", (page - 1) * limit);
            map.put("limit", limit);
            map.put("totalId", rowId);

            //查询列表数据
            List<Map<String, Object>> list = receiveService.queryReceiveItemList(map);
            int total = receiveService.queryReceiveItemListTotal(map);

            pageUtil = new PageUtils(list, total, limit, page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("page", pageUtil);
    }
    /** 
    * @Description: 根据疫苗编码，查询接种台领取的该疫苗批号 
    * @Param: [page, limit, type] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/15 10:39
    * @Tel  17328595627
    */ 
    @ResponseBody
    @RequestMapping("/queryBatchNum")
    public R queryBatchNum(Integer page, Integer limit,String code,String type){
        PageUtils pageUtil = null;
        List<Map<String, Object>> list=null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            map.put("type", type);
            //查询列表数据
            list = receiveService.queryBatchNum(map);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("data", list);
    }
    /**
     * @Description: 根据疫苗编码，其他接种台领取的该疫苗批号
     * @Param: [page, limit, type]
     * @return: io.yfjz.utils.R
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/10/15 10:39
     * @Tel  17328595627
     */
    @ResponseBody
    @RequestMapping("/queryOtherBatchNum")
    public R queryOtherBatchNum(String code){
        List<Map<String, Object>> list=null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            //查询列表数据
            list = receiveService.queryOtherBatchNum(map);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常，请联系管理员");
        }

        return R.ok().put("data", list);
    }

    /** 
    * @Description: 导出温度运输单
    * @Param:  
    * @return:  
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/23 15:22
    * @Tel  17328595627
    */
    @RequestMapping("outputVaccineTransport")
    public void outputVaccineTransport(HttpServletRequest request, HttpServletResponse response){
        String username = ShiroUtils.getUserEntity().getRealName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("疫苗运输温度记录表");
        sheet.setDefaultColumnWidth(15);//设置默认行宽
        sheet.setDefaultRowHeightInPoints(20);
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        //设置边框:
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中


        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        HSSFRow row2 = sheet.createRow(2);
        HSSFRow row3 = sheet.createRow(3);
        HSSFRow row4 = sheet.createRow(4);
        HSSFRow row5 = sheet.createRow(5);
        HSSFRow row6 = sheet.createRow(6);

        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row0.createCell(0);

        HSSFCell cell020 = row2.createCell(0);
        HSSFCell cell021 = row2.createCell(3);
        HSSFCell cell030 = row3.createCell(0);
        HSSFCell cell040 = row4.createCell(0);
        HSSFCell cell050 = row5.createCell(0);
//        HSSFCell cell060 = row9.createCell(0);
        // 设置单元格内容
//        cell0.setCellValue("导出时间：");
////        cell1.setCellValue("导出条件：");
//        cell01.setCellValue("医生：");
        HSSFFont font1 = wb.createFont();
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short) 25);// 设置字体大小
        titleStyle.setFont(font1);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("贵州省疫苗运输温度记录表");
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 8));
        cell020.setCellValue("出/入库日期:");
        row2.createCell(1).setCellValue(sdf.format(new Date()));
        cell021.setCellValue("出/入库单号:");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        String orderNumber=ShiroUtils.getUserEntity().getOrgId()+sdf1.format(new Date())+"01";
        row2.createCell(4).setCellValue(orderNumber);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
        cell030.setCellValue("疫苗运输工具:");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 8));
        row3.createCell(1).setCellValue("(1)冷藏车    (2)疫苗运输车    (3)其他          ");
        cell040.setCellValue("疫苗冷藏工具:");
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 8));
        row4.createCell(1).setCellValue("(1)冷藏车    (2)车载冷藏箱    (3)其他          ");
        cell050.setCellValue("疫苗配送方式:");
        row5.createCell(1).setCellValue("(1)本级配送(车牌号:                 )    (1)下级自运(车牌号:                )");
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 8));
        row6.createCell(0).setCellValue("运输疫苗情况:");

        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
//        cell.setCellStyle(cellStyle);
        Map<String, Object> temp = new HashMap<>();
        temp.put("type",0);
        List<Map<String,Object>> list=receiveService.queryReceiveListByExcel(temp);
        try{

            // 在sheet里创建第二行
            HSSFRow row22 = sheet.createRow(7);
            // 创建单元格并设置单元格内容
            row22.createCell(0).setCellValue("疫苗名称");
            row22.createCell(1).setCellValue("生产企业");
            row22.createCell(2).setCellValue("批准文号");
            row22.createCell(3).setCellValue("批签发合格编号");

            row22.createCell(4).setCellValue("规格");
            row22.createCell(5).setCellValue("疫苗批号");
            row22.createCell(6).setCellValue("疫苗效期");
            row22.createCell(7).setCellValue("数量(支)");
            row22.createCell(8).setCellValue("数量(人份)");
            for (int j = 0; j <9 ; j++) {
                row22.getCell(j).setCellStyle(setBorder);
            }
            for (int i = 0; i <list.size() ; i++) {
                HSSFRow row = sheet.createRow(8 + i);
                Map<String, Object> map = list.get(i);
                row.createCell(0).setCellValue(map.get("productName").toString());
                row.createCell(1).setCellValue(map.get("factoryName").toString());
                row.createCell(2).setCellValue(map.get("licenseNumber").toString());
                row.createCell(3).setCellValue(map.get("lotRelease").toString());
                row.createCell(4).setCellValue(map.get("unit").toString());
                row.createCell(5).setCellValue(map.get("batchnum").toString());
                row.createCell(6).setCellValue(map.get("expiryDate").toString());
                row.createCell(7).setCellValue(map.get("amount").toString());
                row.createCell(8).setCellValue(map.get("personAmount").toString());
                for (int j = 0; j <9 ; j++) {
                    row.getCell(j).setCellStyle(setBorder);
                }
            }
            HSSFRow row7 = sheet.createRow( 8+list.size());
            int lengthNumber = 9 + list.size();
            HSSFRow row60 = sheet.createRow( lengthNumber);
            row7.createCell(0).setCellValue("运输温度记录:");
            row60.createCell(0).setCellValue("项目");
            row60.createCell(1).setCellValue("日期/时间");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber, lengthNumber, 1, 3));
            row60.createCell(4).setCellValue("疫苗存储温度");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber, lengthNumber, 4, 5));
            row60.createCell(6).setCellValue("冰排状态");
            row60.createCell(7).setCellValue("环境温度");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber, lengthNumber, 7, 8));
            HSSFRow row11 = sheet.createRow(lengthNumber + 1);
            row11.createCell(0).setCellValue("启运");
            row11.createCell(1).setCellValue("        年    月    日    时    分");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 1, lengthNumber + 1, 1, 3));
            row11.createCell(4).setCellValue("        °C");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber+1, lengthNumber+1, 4, 5));
            row11.createCell(7).setCellValue("        °C");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber+1, lengthNumber+1, 7, 8));
            for (int i = 0; i < 5; i++) {
                HSSFRow row = sheet.createRow(lengthNumber + 2+i);
                row.createCell(1).setCellValue("        年    月    日    时    分");
                sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 2+i, lengthNumber + 2+i, 1, 3));
                row.createCell(4).setCellValue("        °C");
                sheet.addMergedRegion(new CellRangeAddress(lengthNumber+ 2+i, lengthNumber+ 2+i, 4, 5));
                row.createCell(7).setCellValue("        °C");
                sheet.addMergedRegion(new CellRangeAddress(lengthNumber+2+i, lengthNumber+2+i, 7, 8));
            }
            HSSFRow rowTitle = sheet.getRow(lengthNumber+2);
            rowTitle.createCell(0).setCellValue("途中");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 2, lengthNumber + 5, 0, 0));
            HSSFRow row = sheet.getRow(lengthNumber + 6);
            row.createCell(0).setCellValue("到达");
            for (int i = 0; i < 7; i++) {
                HSSFRow row8 = sheet.getRow(i + lengthNumber);
                for (int j = 0; j < 9; j++) {
                    HSSFCell cell1 = row8.getCell(j);
                    if (cell1==null){
                        cell1= row8.createCell(j);
                    }
                    cell1.setCellStyle(setBorder);
                }
            }
            HSSFRow row71 = sheet.createRow(lengthNumber + 7);
            row71.createCell(0).setCellValue("启运至到达行驶里程数__________千米");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 7, lengthNumber + 7, 0, 8));
            HSSFRow row81 = sheet.createRow(lengthNumber + 8);
            row81.createCell(0).setCellValue("发货单位：");
            row81.createCell(1).setCellValue("_____________________");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 8, lengthNumber + 8, 1, 3));
            row81.createCell(5).setCellValue("发货人签名：");
            row81.createCell(6).setCellValue("______________________");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 8, lengthNumber + 8, 6, 7));

            HSSFRow row91 = sheet.createRow(lengthNumber + 9);
            row91.createCell(0).setCellValue("收货单位：");
            row91.createCell(1).setCellValue("_____________________");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 9, lengthNumber + 9, 1, 3));
            row91.createCell(5).setCellValue("收货人签名：");
            row91.createCell(6).setCellValue("______________________");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 9, lengthNumber + 9, 6, 7));

            HSSFRow rowFoot = sheet.createRow(lengthNumber + 10);
            HSSFCellStyle cellStyle1 = wb.createCellStyle();
            cellStyle1.setWrapText(true);
            rowFoot.createCell(0).setCellValue("填写说明：①本表供疫苗配送企业，疾病预防控制机构，接种单位疫苗运输时填写；②出入库单号为单位机构编码+年月日+2位流水号；" +"\r\n"+
                    "③运输超过6小时需记录途中温度，间隔不超过6小时；④疫苗类别：一类疫苗/二类疫苗");
            sheet.addMergedRegion(new CellRangeAddress(lengthNumber + 10, lengthNumber + 11, 0, 8));
            rowFoot.getCell(0).setCellStyle(cellStyle1);


//            row0.createCell(6).setCellValue(username);
//            row0.createCell(1).setCellValue(sdf.format(new Date()));


            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+new String("疫苗运输温度记录表.xls".getBytes(), "iso-8859-1"));
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
