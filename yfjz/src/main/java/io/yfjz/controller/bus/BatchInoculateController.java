package io.yfjz.controller.bus;

import com.alibaba.fastjson.JSON;
import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.bus.BatchInoculateService;
import io.yfjz.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @Description: 批量补录
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/4 9:27
* @Tel  17328595627
*/
@Controller
@RequestMapping("batch")
public class BatchInoculateController {

    @Autowired
    private BatchInoculateService inoculateService;

    @RequestMapping("/list")
    @ResponseBody
    public R list(String biotypes[],String infostatus[],String chilCommittees[],String selectTimes,String chilBirthdayStart, String chilBirthdayEnd){
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("selectTimes",selectTimes);
        params.put("biotypes",biotypes);
        params.put("infostatus",infostatus);
        params.put("chilCommittees",chilCommittees);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(chilBirthdayStart)){
            try {
                Date start = sdf.parse(chilBirthdayStart);
                params.put("chilBirthdayStart",start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!StringUtils.isEmpty(chilBirthdayEnd)){
            try {
                Date end = sdf.parse(chilBirthdayEnd);
                params.put("chilBirthdayEnd",end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        params.put("page",1);
        params.put("limit",-1);
        Query query = new Query(params);
        List<ChildData> tBusCancelList = inoculateService.queryList(query);

        int total = inoculateService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(tBusCancelList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
    @RequestMapping("queryAllTimes")
    @ResponseBody
    public List<Map<String,Object>> queryAllTimes(){
        List<Map<String,Object>> times=inoculateService.queryAllTimes();
        if (times.size()>0){
            return times;
        }else{
            return Collections.EMPTY_LIST;
        }
    }
    @RequestMapping("deleteTimes")
    @ResponseBody
    public Map<String,Object> deleteTimes(String selectTimes){
        Map<String, Object> resultMap = new HashMap<>();
        int ret=inoculateService.deleteTimes(selectTimes);

        if (ret>0){
            resultMap.put("msg","删除成功");
        }else{
            resultMap.put("msg","删除失败");
        }
        return resultMap;
    }
    @RequestMapping("saveInoculateInfo")
    @ResponseBody
    public Map<String,Object> saveInoculateInfo(@RequestBody Map param){
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String,Object>> rows = (List)param.get("rows");
        try {
            Map ret=inoculateService.saveInoculateInfo(rows);
            resultMap.put("msg","补录成功！");
            resultMap.put("code",0);
            resultMap.put("type",ret.get("type"));
        } catch (RRException e) {
            resultMap.put("msg",e.getMessage());
            resultMap.put("code",1);
            e.printStackTrace();
        } catch (Exception e){
            resultMap.put("msg","系统异常，请联系管理员！");
            resultMap.put("code",1);
            e.printStackTrace();
        }

        return resultMap;
    }
    @RequestMapping("saveSingle")
    @ResponseBody
    public Map<String,Object> saveSingle(@RequestBody Map param){
        Map<String, Object> resultMap = new HashMap<>();
        try {
             inoculateService.singleSaveInoc(param);
            resultMap.put("msg","补录成功！");
            resultMap.put("code",0);
        } catch (RRException e) {
            resultMap.put("msg",e.getMessage());
            resultMap.put("code",1);
            e.printStackTrace();
        } catch (Exception e){
            resultMap.put("msg","系统异常，请联系管理员！");
            resultMap.put("code",1);
            e.printStackTrace();
        }

        return resultMap;
    }
    /** 
    * @Description: 导出excel 
    * @Param: [response, param, chilCommittees, infostatus, biotypes] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/6 9:15
    * @Tel  17328595627
    */ 
    @RequestMapping("batchOutputExcel")
    @ResponseBody
    public void inoNoteExcel(HttpServletResponse response,String biotypes[],String infostatus[],String chilCommittees[],String selectTimes,String chilBirthdayStart, String chilBirthdayEnd){
        Map<String, Object> params = new HashMap<>();
        params.put("selectTimes",selectTimes);
        params.put("biotypes",biotypes);
        params.put("infostatus",infostatus);
        params.put("chilCommittees",chilCommittees);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(chilBirthdayStart)){
            try {
                Date start = sdf.parse(chilBirthdayStart);
                params.put("chilBirthdayStart",start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!StringUtils.isEmpty(chilBirthdayEnd)){
            try {
                Date end = sdf.parse(chilBirthdayEnd);
                params.put("chilBirthdayEnd",end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        params.put("page",1);
        params.put("limit",-1);
        Query query = new Query(params);
        List<ChildData> list = inoculateService.queryList(query);
        if (list != null && list.size()>0){
            //画表
            try {
                HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
                String sheetTitle="未种通知表";
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
                HSSFCell cell01 = row2.createCell(13);
                HSSFCell cell02 = row2.createCell(14);
                HSSFCell cell1 = row2.createCell(0);
                HSSFCell cell2 = row2.createCell(1);
                // HSSFCell cell2 = row1.createCell();
                // 设置单元格内容
                cell1.setCellValue("统计日期：");
                cell2.setCellValue(sdf.format(new Date()));
                SysUserEntity userEntity = ShiroUtils.getUserEntity();
                cell01.setCellValue("统计人：");
                cell02.setCellValue(userEntity.getRealName());
                cell0.setCellValue("未种通知表");
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 16));
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
                for (int i = 0; i < 18; i++) {
                    row3.createCell(i).setCellStyle(setBorder);
                }
                row3.getCell(0).setCellValue("行政村");
                row3.getCell(1).setCellValue("儿童编码");
                row3.getCell(2).setCellValue("姓名");
                row3.getCell(3).setCellValue("性别");
                row3.getCell(4).setCellValue("出生日期");
                row3.getCell(5).setCellValue("父亲姓名");
                row3.getCell(6).setCellValue("母亲姓名");
                row3.getCell(7).setCellValue("家庭电话");
                row3.getCell(8).setCellValue("手机");
                row3.getCell(9).setCellValue("联系地址");
                row3.getCell(10).setCellValue("疫苗");
                row3.getCell(11).setCellValue("批号");
                row3.getCell(12).setCellValue("剂次");
                row3.getCell(13).setCellValue("安排接种日期");
                row3.getCell(14).setCellValue("接种部位");
                row3.getCell(15).setCellValue("在册情况");
                row3.getCell(16).setCellValue("备注");
                row3.getCell(17).setCellValue("是否接种");
                //填充数据
                for (int i = 0; i <list.size() ; i++) {
                    HSSFRow rows = sheet.createRow(i + 4);
                    ChildData childData = list.get(i);
                    for (int j = 0; j < 18; j++) {
                        rows.createCell(j).setCellStyle(setBorder);
                    }
                    rows.getCell(0).setCellValue(childData.getCommittee());
                    rows.getCell(1).setCellValue(childData.getChilCode());
                    rows.getCell(2).setCellValue(childData.getChilName());
                    rows.getCell(3).setCellValue(childData.getChilSex());
                    rows.getCell(4).setCellValue(sdf.format(childData.getChilBirthday()));
                    rows.getCell(5).setCellValue(childData.getFatherName());
                    rows.getCell(6).setCellValue(childData.getMatherName());
                    rows.getCell(7).setCellValue(childData.getChilTel());
                    rows.getCell(8).setCellValue(childData.getChilMobile());
                    rows.getCell(9).setCellValue(childData.getAddress());
                    rows.getCell(10).setCellValue(childData.getPlanName());
                    rows.getCell(12).setCellValue(childData.getTimes());
                    rows.getCell(13).setCellValue(sdf.format(childData.getInoDate()));
//                    rows.getCell(14).setCellValue("口服");
                    rows.getCell(15).setCellValue(childData.getHere());
                    rows.getCell(16).setCellValue(childData.getRemark());
                    rows.getCell(17).setCellValue("否");
                    CellRangeAddressList regions = new CellRangeAddressList(i + 4, i + 4, 11, 11);
                    CellRangeAddressList inocSite = new CellRangeAddressList(i + 4, i + 4, 14, 14);
                    List<Map<String,Object>>  batchnums=inoculateService.queryVaccineBatchnum(childData.getPlanId());
                    if (batchnums.size()>0){
                         String[] batchs=new String[batchnums.size()];
                        for (int j = 0; j < batchnums.size(); j++) {
                            if (batchnums.get(j)!=null) {
                                Map<String, Object> map = batchnums.get(j);
                                batchs[j]=map.get("batchnum").toString();
                            }
                        }
                        Map<String, Object> map = batchnums.get(0);
                        rows.getCell(11).setCellValue(map.get("batchnum")==null?"":map.get("batchnum").toString());
                        DVConstraint batchumList = DVConstraint.createExplicitListConstraint(batchs);
                        HSSFDataValidation hssfDataValidation = new HSSFDataValidation(regions, batchumList);
                        hssfDataValidation.setShowErrorBox(false);
                        hssfDataValidation.createErrorBox("提示","验证失败");
                        hssfDataValidation.setShowErrorBox(true);
                        sheet.addValidationData(hssfDataValidation);

                        rows.getCell(14).setCellValue(map.get("inocSite")==null?"":map.get("inocSite").toString());

                    }
                    String[] sites=inoculateService.queryInoculateSiteList();
                    DVConstraint siteList = DVConstraint.createExplicitListConstraint(sites);
                    HSSFDataValidation hssfDataValidation = new HSSFDataValidation(inocSite, siteList);
                    sheet.addValidationData(hssfDataValidation);



                }
                //为表格绑定下拉选框的值
                //只对（0，0）单元格有效

                CellRangeAddressList regions = new CellRangeAddressList(4, list.size()+3, 17, 17);
                CellRangeAddressList times = new CellRangeAddressList(4, list.size()+3, 12, 12);
                CellRangeAddressList vaccine = new CellRangeAddressList(4, list.size()+3, 10, 10);
                CellRangeAddressList infoStatus = new CellRangeAddressList(4, list.size()+3, 15, 15);


                //生成下拉框内容
                String[] isInoc={"是","否"};
                String[] timesList={"1","2","3","4","5","6","7","8"};
                String[] vaccineList=inoculateService.getAllVaccine();
                String[] statusList=inoculateService.queryChildInfoStatus();


                DVConstraint constraint = DVConstraint.createExplicitListConstraint(isInoc);
                DVConstraint timesConstraint = DVConstraint.createExplicitListConstraint(timesList);
                DVConstraint vaccineConstraint = DVConstraint.createExplicitListConstraint(vaccineList);
                DVConstraint statusConstraint = DVConstraint.createExplicitListConstraint(statusList);

                //绑定下拉框和作用区域

                HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
                HSSFDataValidation data_times = new HSSFDataValidation(times, timesConstraint);
                HSSFDataValidation data_vaccine = new HSSFDataValidation(vaccine, vaccineConstraint);
                HSSFDataValidation data_status = new HSSFDataValidation(infoStatus, statusConstraint);

                //对sheet页生效

                sheet.addValidationData(data_validation);
                sheet.addValidationData(data_times);
                sheet.addValidationData(data_vaccine);
                sheet.addValidationData(data_status);
                // 输出Excel文件
                OutputStream output = response.getOutputStream();
                System.out.println("output=" + output);
                response.reset();
                response.setHeader("Content-disposition", "attachment; filename="+new String("未种通知表.xls".getBytes(), "iso-8859-1"));
                response.setContentType("application/msexcel");
                wb.write(output);
                output.flush();
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 上传excel文件补录疫苗
     * @param request
     * @param response
     * @param file
     */
    @RequestMapping(value="/uploadFile",method= RequestMethod.POST)
    @ResponseBody
    public Map uploadfile(HttpServletRequest request, HttpServletResponse response, @RequestParam("batchInoculate") MultipartFile file){
        String realPath = request.getRealPath("/");
        String uploadfilePath=realPath+"upload/excel";
        final Map<String, Object> map = inoculateService.uploadFile(file, uploadfilePath);
        return map;
    }
    /** 
    * @Description: 批量变更儿童的个案信息和备注信息
    * @Param: [request, response, file] 
    * @return: java.util.Map 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018-12-07 9:54
    * @Tel  17328595627
    */ 
    @RequestMapping(value="/changeChildInfo")
    @ResponseBody
    public Map changeChildInfo(@RequestBody Map param){
        Map<String, Object> resultMap = new HashMap<>();
        if(param.get("rows")!=null){
            try {
                List<Map<String,Object>> rows = (List)param.get("rows");
                int ret=inoculateService.changeChildInfo(rows);
                if(ret==1){
                    resultMap.put("msg","变更信息成功！");
                    resultMap.put("code",0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("msg","系统异常，请联系管理员！");
                resultMap.put("code",1);
            }

        }
        return resultMap;
    }
}
