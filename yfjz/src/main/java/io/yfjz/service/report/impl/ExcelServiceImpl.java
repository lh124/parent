package io.yfjz.service.report.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.yfjz.dao.statistics.InoculateLosgDao;
import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.IntegrityRateEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.child.TChildInoculateEntity;
import io.yfjz.entity.statistics.SixToOneResult;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.child.TChildInoculateService;
import io.yfjz.service.report.DynamicChildService;
import io.yfjz.service.report.ExcelService;
import io.yfjz.service.report.SixToOneStatisticsService;
import io.yfjz.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 饶士培
 * @Date: 2018-09-10 17:57
 * @Description:
 * @tel:18798010686
 * @qq:1013147559
 */
@Service("ExcelServiceImpl")
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private InoculateLosgDao inoculateLosgDao;

    @Autowired
    private DynamicChildService dynamicChildService;

    @Autowired
    private SixToOneStatisticsService sixToOneStatisticsService;

    @Autowired
    private TChildInoculateService tChildInoculateService;

    @Override
    public void IntegrityRate(List<IntegrityRateEntity> integrityRateEntities, String path, HttpServletResponse response) {
        String username = ShiroUtils.getUserEntity().getRealName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("儿童基本资料完整性汇总表");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell01 = row0.createCell(20);
        HSSFCell cell = row1.createCell(0);
        // 设置单元格内容
        cell0.setCellValue("导出时间：");
        cell01.setCellValue("医生：");
        cell.setCellValue("儿童基本资料完整性汇总表");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 21));

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 7));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 8, 9));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 10, 11));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 12, 13));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 14, 15));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 16, 17));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 18, 19));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 20, 21));

        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        try {
            for (IntegrityRateEntity rate:integrityRateEntities) {
                Map<String, Object> maps = new HashMap<String,Object>();
                // System.out.println("log="+log.getVillagey());
                maps.put("year",rate.getYear());
                maps.put("totalChild",rate.getTotalChild());
                maps.put("fullCount",rate.getFullCount());
                maps.put("fullNameCount",rate.getFullNameCount());
                maps.put("fullSexCount",rate.getFullSexCount());
                maps.put("fullBirthTimeCount",rate.getFullBirthTimeCount());
                maps.put("fullMothernameCount",rate.getFullMothernameCount());
                maps.put("fullChild_habi_idCount",rate.getFullHabiIdCount());
                maps.put("fullHukouAddressCount",rate.getFullHukouAddressCount());
                maps.put("fullCreateSiteCount",rate.getFullCreateSiteCount());

                maps.put("integrityRate",rate.getIntegrityRate());
                maps.put("fullContactAddressCount",rate.getFullContactAddressCount());
                maps.put("fullTelCount",rate.getFullTelCount());
                lists.add(maps);

            }

            // 在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(2);
            HSSFRow row3 = sheet.createRow(3);
            // 创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("年度");
            row2.createCell(1).setCellValue("儿童总数");
            row2.createCell(2).setCellValue("完整数");
            row2.createCell(3).setCellValue("完整率(%)");
            row2.createCell(4).setCellValue("儿童姓名");
            row2.createCell(6).setCellValue("性别");
            row2.createCell(8).setCellValue("出生日期");
            row2.createCell(10).setCellValue("建证单位");
            row2.createCell(12).setCellValue("联系电话");
            row2.createCell(14).setCellValue("家长姓名");
            row2.createCell(16).setCellValue("户口县国标");
            row2.createCell(18).setCellValue("户口地址");
            row2.createCell(20).setCellValue("通讯地址");

            row3.createCell(4).setCellValue("录入数");
            row3.createCell(5).setCellValue("录入率");
            row3.createCell(6).setCellValue("录入数");
            row3.createCell(7).setCellValue("录入率");
            row3.createCell(8).setCellValue("录入数");
            row3.createCell(9).setCellValue("录入率");
            row3.createCell(10).setCellValue("录入数");
            row3.createCell(11).setCellValue("录入率");
            row3.createCell(12).setCellValue("录入数");
            row3.createCell(13).setCellValue("录入率");
            row3.createCell(14).setCellValue("录入数");
            row3.createCell(15).setCellValue("录入率");
            row3.createCell(16).setCellValue("录入数");
            row3.createCell(17).setCellValue("录入率");
            row3.createCell(18).setCellValue("录入数");
            row3.createCell(19).setCellValue("录入率");
            row3.createCell(20).setCellValue("录入数");
            row3.createCell(21).setCellValue("录入率");
            for(int j = 0; j <=20;j+=2){
                row2.getCell(j).setCellStyle(cellStyle2);
            }
            for(int j = 4; j <=21;j++){
                row3.getCell(j).setCellStyle(cellStyle2);
            }
            int fullCount = 0;
            double integrityRate = 0.0;
            int fullNameCount = 0;
            int fullSexCount = 0;
            int fullBirthTimeCount = 0;
            int totalChild = 0;
            int fullTelCount = 0;
            int fullCreateSiteCount = 0;
            int fullMothernameCount = 0;
            int fullChild_habi_idCount = 0;
            int fullHukouAddressCount = 0;
            int fullContactAddressCount = 0;
            Double nameEntryRate = 0.0;
            Double sexEntryRate = 0.0;
            Double birthTimeEntryRate = 0.0;
            Double createSiteEntryRate = 0.0;
            Double telEntryRate = 0.0;
            Double mothernameEntryRate = 0.0;
            Double habiIdEntryRate = 0.0;
            Double hukouAddressEntryRate = 0.0;
            Double contactAddressEntryRate = 0.0;

            for (Integer i = 0; i < lists.size();) {
                HSSFRow rown = sheet.createRow(4 + i);

                fullCount += Integer.parseInt(lists.get(i).get("fullCount").toString());
                fullNameCount += Integer.parseInt(lists.get(i).get("fullNameCount").toString());
                fullSexCount += Integer.parseInt(lists.get(i).get("fullSexCount").toString());
                fullBirthTimeCount += Integer.parseInt(lists.get(i).get("fullBirthTimeCount").toString());
                totalChild += Integer.parseInt(lists.get(i).get("totalChild").toString());
                fullCreateSiteCount += Integer.parseInt(lists.get(i).get("fullCreateSiteCount").toString());
                fullTelCount += Integer.parseInt(lists.get(i).get("fullTelCount").toString());
                fullMothernameCount += Integer.parseInt(lists.get(i).get("fullMothernameCount").toString());
                fullChild_habi_idCount += Integer.parseInt(lists.get(i).get("fullChild_habi_idCount").toString());
                fullHukouAddressCount += Integer.parseInt(lists.get(i).get("fullHukouAddressCount").toString());
                fullContactAddressCount += Integer.parseInt(lists.get(i).get("fullContactAddressCount").toString());

                Integer total = Integer.parseInt(lists.get(i).get("totalChild").toString());
                nameEntryRate = Integer.parseInt(lists.get(i).get("fullNameCount").toString())*1.0/total*100;
                sexEntryRate = Integer.parseInt(lists.get(i).get("fullSexCount").toString())*1.0/total*100;
                birthTimeEntryRate = Integer.parseInt(lists.get(i).get("fullBirthTimeCount").toString())*1.0/total*100;
                createSiteEntryRate = Integer.parseInt(lists.get(i).get("fullCreateSiteCount").toString())*1.0/total*100;
                telEntryRate = Integer.parseInt(lists.get(i).get("fullTelCount").toString())*1.0/total*100;
                mothernameEntryRate = Integer.parseInt(lists.get(i).get("fullMothernameCount").toString())*1.0/total*100;
                habiIdEntryRate = Integer.parseInt(lists.get(i).get("fullChild_habi_idCount").toString())*1.0/total*100;
                hukouAddressEntryRate = Integer.parseInt(lists.get(i).get("fullHukouAddressCount").toString())*1.0/total*100;
                contactAddressEntryRate = Integer.parseInt(lists.get(i).get("fullContactAddressCount").toString())*1.0/total*100;

                rown.createCell(0).setCellValue(lists.get(i).get("year").toString());
                rown.createCell(1).setCellValue( lists.get(i).get("totalChild").toString());
                rown.createCell(2).setCellValue(lists.get(i).get("fullCount").toString());
                rown.createCell(3).setCellValue(lists.get(i).get("integrityRate").toString());
                rown.createCell(4).setCellValue( lists.get(i).get("fullNameCount").toString());
                rown.createCell(5).setCellValue( Double.parseDouble(String.format("%.2f",nameEntryRate)));
                rown.createCell(6).setCellValue( lists.get(i).get("fullSexCount").toString());
                rown.createCell(7).setCellValue( Double.parseDouble(String.format("%.2f",sexEntryRate)));
                rown.createCell(8).setCellValue(lists.get(i).get("fullBirthTimeCount").toString());
                rown.createCell(9).setCellValue(Double.parseDouble(String.format("%.2f",birthTimeEntryRate)));
                rown.createCell(10).setCellValue(lists.get(i).get("fullCreateSiteCount").toString());
                rown.createCell(11).setCellValue(Double.parseDouble(String.format("%.2f",createSiteEntryRate)));
                rown.createCell(12).setCellValue(lists.get(i).get("fullTelCount").toString());
                rown.createCell(13).setCellValue(Double.parseDouble(String.format("%.2f",telEntryRate)));
                rown.createCell(14).setCellValue(lists.get(i).get("fullMothernameCount").toString());
                rown.createCell(15).setCellValue(Double.parseDouble(String.format("%.2f",mothernameEntryRate)));

                rown.createCell(16).setCellValue(lists.get(i).get("fullChild_habi_idCount").toString());
                rown.createCell(17).setCellValue(Double.parseDouble(String.format("%.2f",habiIdEntryRate)));
                rown.createCell(18).setCellValue(lists.get(i).get("fullHukouAddressCount").toString());
                rown.createCell(19).setCellValue(Double.parseDouble(String.format("%.2f",hukouAddressEntryRate)));
                rown.createCell(20).setCellValue( lists.get(i).get("fullContactAddressCount").toString());
                rown.createCell(21).setCellValue( Double.parseDouble(String.format("%.2f",contactAddressEntryRate)));
                for(int j = 0; j <=21;j++){
                    rown.getCell(j).setCellStyle(cellStyle2);
                }
                i++;
                if(i==lists.size()){
                    HSSFRow rowns = sheet.createRow(4 + i);
                    integrityRate = fullCount*1.0/totalChild*100;
                    integrityRate = Double.parseDouble(String.format("%.2f", integrityRate));

                    nameEntryRate = Double.parseDouble(String.format("%.2f",fullNameCount*1.0/totalChild*100));
                    sexEntryRate = Double.parseDouble(String.format("%.2f",fullSexCount*1.0/totalChild*100));
                    birthTimeEntryRate = Double.parseDouble(String.format("%.2f",fullBirthTimeCount*1.0/totalChild*100));
                    createSiteEntryRate = Double.parseDouble(String.format("%.2f",fullCreateSiteCount*1.0/totalChild*100));
                    telEntryRate = Double.parseDouble(String.format("%.2f",fullTelCount*1.0/totalChild*100));
                    mothernameEntryRate = Double.parseDouble(String.format("%.2f",fullMothernameCount*1.0/totalChild*100));
                    habiIdEntryRate = Double.parseDouble(String.format("%.2f",fullChild_habi_idCount*1.0/totalChild*100));
                    hukouAddressEntryRate = Double.parseDouble(String.format("%.2f",fullHukouAddressCount*1.0/totalChild*100));
                    contactAddressEntryRate = Double.parseDouble(String.format("%.2f",fullContactAddressCount*1.0/totalChild*100));

                    rowns.createCell(0).setCellValue("合计");
                    rowns.createCell(1).setCellValue(totalChild);
                    rowns.createCell(2).setCellValue(fullCount);
                    rowns.createCell(3).setCellValue(integrityRate);
                    rowns.createCell(4).setCellValue(fullNameCount);
                    rowns.createCell(5).setCellValue(nameEntryRate);
                    rowns.createCell(6).setCellValue(fullSexCount);
                    rowns.createCell(7).setCellValue(sexEntryRate);
                    rowns.createCell(8).setCellValue(fullBirthTimeCount);
                    rowns.createCell(9).setCellValue(birthTimeEntryRate);
                    rowns.createCell(10).setCellValue(fullCreateSiteCount);
                    rowns.createCell(11).setCellValue(createSiteEntryRate);
                    rowns.createCell(12).setCellValue(fullTelCount);
                    rowns.createCell(13).setCellValue(telEntryRate);
                    rowns.createCell(14).setCellValue(fullMothernameCount);
                    rowns.createCell(15).setCellValue(mothernameEntryRate);
                    rowns.createCell(16).setCellValue(fullChild_habi_idCount);
                    rowns.createCell(17).setCellValue(habiIdEntryRate);
                    rowns.createCell(18).setCellValue(fullHukouAddressCount);
                    rowns.createCell(19).setCellValue(hukouAddressEntryRate);
                    rowns.createCell(20).setCellValue(fullContactAddressCount);
                    rowns.createCell(21).setCellValue(contactAddressEntryRate);
                    for(int j = 0; j <=21;j++){
                        rowns.getCell(j).setCellStyle(cellStyle2);
                    }
                }
            }

            row0.createCell(21).setCellValue(username);
            row0.createCell(1).setCellValue(sdf.format(new Date()));
            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("儿童基本资料完整性汇总表")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @method_name: IntegrityRate
     * @describe:儿童基本资料不完整性明细表
     * @return void
     * @author 廖欢
     * @QQ: 1215077166@qq.com
     * @tel:18785185063
     **/
    @Override
    public void imperfectChild(List<TChildInfoEntity> integrityRateEntities, String path, HttpServletResponse response) {
        String username = ShiroUtils.getUserEntity().getRealName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("儿童基本资料不完整性明细表");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell01 = row0.createCell(16);
        HSSFCell cell = row1.createCell(0);
        // 设置单元格内容
        cell0.setCellValue("导出时间：");
        cell01.setCellValue("医生：");
        cell.setCellValue("儿童基本资料不完整性明细表");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 18));
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        try {
            for (TChildInfoEntity rate:integrityRateEntities){
                if("1".equals(rate.getChilSex())){
                    rate.setChilSex("男");
                }
                if("2".equals(rate.getChilSex())){
                    rate.setChilSex("女");
                }
                Map<String, Object> maps = new HashMap<String,Object>();
                maps.put("chilCode", rate.getChilCode());
                maps.put("chilName", rate.getChilName());
                maps.put("chilSex", rate.getChilSex());
                maps.put("chilBirthday", rate.getChilBirthday()==null?"":new SimpleDateFormat("yyyy-MM-dd").format(rate.getChilBirthday()));

                maps.put("chilMother", rate.getChilMother());
                maps.put("chilTel", rate.getChilTel());
                maps.put("chilAddress", rate.getChilAddress());
                maps.put("createCardTime", rate.getCreateCardTime()==null?"":new SimpleDateFormat("yyyy-MM-dd").format(rate.getCreateCardTime()));

                maps.put("chilFather", rate.getChilFather());
                maps.put("chilHere", rate.getChilHere());
                maps.put("chilCommittee", rate.getChilCommittee());
                maps.put("chilMobile", rate.getChilMobile());

                maps.put("chilResidence", rate.getChilResidence());
                maps.put("chilNo", rate.getChilNo());
                maps.put("chilHabiaddress", rate.getChilHabiaddress());
                maps.put("chilCreatesite", rate.getChilCreatesite());

                maps.put("chilHabiId", rate.getChilHabiId());

                maps.put("chilFatherno", rate.getChilFatherno());
                lists.add(maps);
            }
            // 在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(2);
            // 创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("儿童编码");
            row2.createCell(1).setCellValue("儿童姓名");
            row2.createCell(2).setCellValue("性别");
            row2.createCell(3).setCellValue("出生日期");

            row2.createCell(4).setCellValue("父亲姓名");
            row2.createCell(5).setCellValue("母亲姓名");
            row2.createCell(6).setCellValue("家庭电话");
            row2.createCell(7).setCellValue("手机号码");

            row2.createCell(8).setCellValue("建卡日期");
//            row2.createCell(9).setCellValue("通讯地址");
            row2.createCell(9).setCellValue("居委会/行政村");
            row2.createCell(10).setCellValue("在册情况");

            row2.createCell(11).setCellValue("居住属性");
            row2.createCell(12).setCellValue("儿童身份证");
            row2.createCell(13).setCellValue("建证单位");
            row2.createCell(14).setCellValue("户口县国标");

            row2.createCell(15).setCellValue("户口地址");
            row2.createCell(16).setCellValue("现居地址");
            row2.createCell(17).setCellValue("家长身份证");

            for (Integer i = 0; i < lists.size();) {
                HSSFRow rown = sheet.createRow(3 + i);
                rown.createCell(0).setCellValue(lists.get(i).get("chilCode") == null ? "" : lists.get(i).get("chilCode").toString());
                rown.createCell(1).setCellValue(lists.get(i).get("chilName") == null ? "" : lists.get(i).get("chilName").toString());
                rown.createCell(2).setCellValue(lists.get(i).get("chilSex") == null ? "" : lists.get(i).get("chilSex").toString());
                rown.createCell(3).setCellValue(lists.get(i).get("chilBirthday") == null ? "" : lists.get(i).get("chilBirthday").toString());

                rown.createCell(4).setCellValue(lists.get(i).get("chilFather") == null ? "" : lists.get(i).get("chilFather").toString());
                rown.createCell(5).setCellValue(lists.get(i).get("chilMother") == null ? "" : lists.get(i).get("chilMother").toString());
                rown.createCell(6).setCellValue(lists.get(i).get("chilTel") == null ? "" : lists.get(i).get("chilTel").toString());
                rown.createCell(7).setCellValue(lists.get(i).get("chilMobile") == null ? "" : lists.get(i).get("chilMobile").toString());

                rown.createCell(8).setCellValue(lists.get(i).get("createCardTime") == null ? "" : lists.get(i).get("createCardTime").toString());
//                rown.createCell(9).setCellValue(lists.get(i).get("chilHabiId") == null ? "" : lists.get(i).get("chilHabiId").toString());
                rown.createCell(9).setCellValue(lists.get(i).get("chilCommittee") == null ? "" : lists.get(i).get("chilCommittee").toString());
                rown.createCell(10).setCellValue(lists.get(i).get("chilHere") == null ? "" : lists.get(i).get("chilHere").toString());
                rown.createCell(11).setCellValue(lists.get(i).get("chilResidence") == null ? "" : lists.get(i).get("chilResidence").toString());

                rown.createCell(12).setCellValue(lists.get(i).get("chilNo") == null ? "" : lists.get(i).get("chilNo").toString());
                rown.createCell(13).setCellValue(lists.get(i).get("chilCreatesite") == null ? "" : lists.get(i).get("chilCreatesite").toString());

                rown.createCell(14).setCellValue(lists.get(i).get("chilHabiId") == null ? "" : lists.get(i).get("chilHabiId").toString());
                rown.createCell(15).setCellValue(lists.get(i).get("chilHabiaddress") == null ? "" : lists.get(i).get("chilHabiaddress").toString());
                rown.createCell(16).setCellValue(lists.get(i).get("chilAddress") == null ? "" : lists.get(i).get("chilAddress").toString());
                rown.createCell(17).setCellValue(lists.get(i).get("chilFatherno") == null ? "" : lists.get(i).get("chilFatherno").toString());
                for(int j = 0; j <=17;j++){
                    rown.getCell(j).setCellStyle(cellStyle2);
                }
                i++;
            }

            row0.createCell(17).setCellValue(username);
            row0.createCell(1).setCellValue(sdf.format(new Date()));
            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("儿童基本资料不完整性明细表")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //中文导出名使用
    public  String toUtf8String(String title){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<title.length();i++){
            char c = title.charAt(i);
            if (c >= 0 && c <= 255){sb.append(c);}
            else{
                byte[] b;
                try { b = Character.toString(c).getBytes("utf-8");}
                catch (Exception ex) {
//                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * @method_name: IntegrityRate
     * @describe:儿童基本接种日志导出
     * @return void
     * @author 廖欢
     * @QQ: 1215077166@qq.com
     * @tel:18785185063
     **/
    @Override
    public void ExcelInoLog(Map<String, Object> paramMap,String path,HttpServletResponse response) {
        //根据条件四个返回步骤：
        HashMap<String,Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(String.valueOf(paramMap.get("chilBirthdayStart")))) {
            param.put("chilBirthdayStart", paramMap.get("chilBirthdayStart"));
        }
        if (!StringUtils.isEmpty(String.valueOf(paramMap.get("chilBirthdayEnd")))) {
            param.put("chilBirthdayEnd", paramMap.get("chilBirthdayEnd"));
        }
        if (!StringUtils.isEmpty(String.valueOf(paramMap.get("(String)")))) {
            param.put("inoculateStart", paramMap.get("inoculateStart"));
        }
        if (!StringUtils.isEmpty(String.valueOf(paramMap.get("inoculateEnd")))) {
            param.put("inoculateEnd", paramMap.get("inoculateEnd"));
        }

        if(paramMap.get("chilResidences")==null||"".equals(paramMap.get("chilResidences"))){
        }else{
            String str1=paramMap.get("chilResidences").toString();
            String chilResidence[]= str1.split(",");
            param.put("chilResidence", chilResidence);
        }
        if(paramMap.get("chilCommittees")==null||"".equals(paramMap.get("chilCommittees"))){
        }else{
            String str2=paramMap.get("chilCommittees").toString();
            String chilCommittees[]= str2.split(",");
            param.put("chilCommittees", chilCommittees);
        }
        if(paramMap.get("infostatus")==null||"".equals(paramMap.get("infostatus"))){
        }else{
            String str3=paramMap.get("infostatus").toString();
            String infostatus[]= str3.split(",");
            param.put("infostatus", infostatus);
        }
        if(paramMap.get("biotypes")==null||"".equals(paramMap.get("biotypes"))){
        }else{
            String str4=paramMap.get("biotypes").toString();
            String biotypes[]= str4.split(",");
            param.put("biotypes", biotypes);
        }
        if(paramMap.get("bioNos")==null||"".equals(paramMap.get("bioNos"))){
        }else{
            String str5=paramMap.get("bioNos").toString();
            String bioNos[]=str5.split(",");
            param.put("bioNos", bioNos);
        }
        if(paramMap.get("inocDoctors") != null && !"".equals(paramMap.get("inocDoctors"))){
            String inocDoctor=paramMap.get("inocDoctors").toString();
            String inocDoctors[]=inocDoctor.split(",");
            param.put("inocDoctors", inocDoctors);
        }
        if(paramMap.get("inocbatchno")==null||"".equals(paramMap.get("inocbatchno"))){
        }else{
            String inocbatchno=paramMap.get("inocbatchno").toString();
            String inocbatchnos[]=inocbatchno.split(",");
            param.put("inocbatchno", inocbatchnos);
        }
        if(paramMap.get("schools")==null||"".equals(paramMap.get("schools"))){
        }else{
            String str6=paramMap.get("schools").toString();
            String school[]=  str6.split(",");
            param.put("school", school);
        }
        param.put("org_id",ShiroUtils.getUserEntity().getOrgId());
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("儿童接种日志信息表");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);

        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell01 = row0.createCell(7);
        HSSFCell cell00 = row0.createCell(1);
        HSSFCell cell011 = row0.createCell(8);

//            HSSFCell cell2 = row1.createCell(18);
        // 设置单元格内容
        cell0.setCellValue("日志日期：");
        cell00.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        cell01.setCellValue("统计人：");
        cell011.setCellValue(ShiroUtils.getUserEntity().getRealName());
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cell01.setCellStyle(cellStyle);

        try {
            //疫苗加强
            List<Map<String, Object>> inocpropertys = inoculateLosgDao.getInoulateLogsGroupInocProperty(param);
            List<Map<String,Object>> list = new ArrayList<>();
            Map<String,Object> maps = null;
            Integer totalBase = 0;
            Integer totalReinforce = 0;
            Integer totalIntensify = 0;
            Integer totalOutburst = 0;
            Integer total = 0;
            for (Map<String, Object> log : inocpropertys) {
                maps=new HashMap<>();
                maps.put("bio_name", log.get("疫苗"));
                maps.put("base", log.get("基础"));
                maps.put("reinforce", log.get("加强"));
                maps.put("intensify", log.get("强化"));
                maps.put("outburst", log.get("应急"));
               Integer  alltotal=Integer.parseInt(log.get("基础").toString())
                        +Integer.parseInt(log.get("加强").toString())+Integer.parseInt(log.get("应急").toString());
                maps.put("total", alltotal);
                list.add(maps);
                totalBase += maps.get("base")==null ? 0 : Integer.parseInt(maps.get("base").toString());
                totalReinforce += maps.get("reinforce")==null ? 0 : Integer.parseInt(maps.get("reinforce").toString());
                totalIntensify +=  maps.get("intensify")==null ? 0 : Integer.parseInt(maps.get("intensify").toString());
                totalOutburst +=  maps.get("outburst")==null ? 0 : Integer.parseInt(maps.get("outburst").toString());
                total +=  maps.get("total")==null ? 0 : Integer.parseInt(maps.get("total").toString());
            }

            //设置表一的表头
            int beginRowTable1 = 2;
            HSSFRow row1 = sheet.createRow(beginRowTable1);
            HSSFRow row2 = sheet.createRow(beginRowTable1 + 1);
            HSSFCell cell = row1.createCell(0);
            cell.setCellValue("疫苗使用情况表(接种属性)");
            // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(beginRowTable1, beginRowTable1, 0, 5));
            //设置表一列名
            row2.createCell(0).setCellValue("疫苗名称");
            row2.createCell(1).setCellValue("基础");
            row2.createCell(2).setCellValue("加强");
            row2.createCell(3).setCellValue("强化");
            row2.createCell(4).setCellValue("应急");
            row2.createCell(5).setCellValue("小计");

            //        疫苗剂次
            List<Map<String, Object>> inocDoses = inoculateLosgDao.getInoulateLogsGroupDose(param);
            List<Map<String,Object>> list2 = new ArrayList<>();
            Integer totaltimes1 = 0;
            Integer totaltimes2 = 0;
            Integer totaltimes3 = 0;
            Integer totaltimes4 = 0;
            Integer totaltimes5 = 0;
            Integer totaltimes6 = 0;
            Integer totaltimes7 = 0;
            Integer totaltimes8 = 0;
            Integer totaltotaltimes = 0;
            for (Map<String,Object> mapsinocDoses:inocDoses){
                Map<String,Object> mapsdoses = new HashMap<>();
                mapsdoses.put("bio_name",mapsinocDoses.get("疫苗"));
                mapsdoses.put("times1",mapsinocDoses.get("剂次1"));
                mapsdoses.put("times2",mapsinocDoses.get("剂次2"));
                mapsdoses.put("times3",mapsinocDoses.get("剂次3"));
                mapsdoses.put("times4",mapsinocDoses.get("剂次4"));
                mapsdoses.put("times5",mapsinocDoses.get("剂次5"));
                mapsdoses.put("times6",mapsinocDoses.get("剂次6"));
                mapsdoses.put("times7",mapsinocDoses.get("剂次7"));
                mapsdoses.put("times8",mapsinocDoses.get("剂次8"));
             Integer    alltotaltotaltimes=Integer.parseInt(mapsinocDoses.get("剂次1").toString())+Integer.parseInt(mapsinocDoses.get("剂次2").toString())+
                        Integer.parseInt(mapsinocDoses.get("剂次3").toString())+Integer.parseInt(mapsinocDoses.get("剂次4").toString())+
                        Integer.parseInt(mapsinocDoses.get("剂次5").toString())+Integer.parseInt(mapsinocDoses.get("剂次6").toString())+
                        Integer.parseInt(mapsinocDoses.get("剂次7").toString())+Integer.parseInt(mapsinocDoses.get("剂次8").toString());
                mapsdoses.put("total", alltotaltotaltimes);
                list2.add(mapsdoses);
                totaltimes1 +=  mapsdoses.get("times1")==null ? 0 : Integer.parseInt(mapsdoses.get("times1").toString());
                totaltimes2 +=  mapsdoses.get("times2")==null ? 0 : Integer.parseInt(mapsdoses.get("times2").toString());
                totaltimes3 +=  mapsdoses.get("times3")==null ? 0 : Integer.parseInt(mapsdoses.get("times3").toString());
                totaltimes4 +=  mapsdoses.get("times4")==null ? 0 : Integer.parseInt(mapsdoses.get("times4").toString());
                totaltimes5 +=  mapsdoses.get("times5")==null ? 0 : Integer.parseInt(mapsdoses.get("times5").toString());
                totaltimes6 +=  mapsdoses.get("times6")==null ? 0 : Integer.parseInt(mapsdoses.get("times6").toString());
                totaltimes7 +=  mapsdoses.get("times7")==null ? 0 : Integer.parseInt(mapsdoses.get("times7").toString());
                totaltimes8 +=  mapsdoses.get("times8")==null ? 0 : Integer.parseInt(mapsdoses.get("times8").toString());
                totaltotaltimes +=  mapsdoses.get("total")==null ? 0 : Integer.parseInt(mapsdoses.get("total").toString());

            }
            //创建表二的开始行
            int beginRowTable2=list2.size() + 6;
            HSSFRow rowTable2 = sheet.createRow(beginRowTable2);
            //创建单元格
            HSSFCell cellTable2 = row1.createCell(8);
            cellTable2.setCellValue("疫苗使用情况表(接种剂次)");
            // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(beginRowTable1, beginRowTable1, 8, 17));
            //设置表二列名
//            HSSFRow row4 = sheet.createRow(beginRowTable2+1);
            row2.createCell(8).setCellValue("疫苗名称");
            row2.createCell(9).setCellValue("剂次1");
            row2.createCell(10).setCellValue("剂次2");
            row2.createCell(11).setCellValue("剂次3");
            row2.createCell(12).setCellValue("剂次4");
            row2.createCell(13).setCellValue("剂次5");
            row2.createCell(14).setCellValue("剂次6");
            row2.createCell(15).setCellValue("剂次7");
            row2.createCell(16).setCellValue("剂次8");
            row2.createCell(17).setCellValue("小计");
            Integer len=0;
            if(list.size()>=list2.size()){
                len=list.size();
            }else{
                len=list2.size();
            }
            for (Integer i = 0; i < len + 1; i++) {
                HSSFRow rown = sheet.createRow(beginRowTable1 + 2 + i);
                //设置表一的数据
                if (i < list.size()) {
                    rown.createCell(0).setCellValue(list.get(i).get("bio_name")==null?"":list.get(i).get("bio_name").toString());
                    rown.createCell(1).setCellValue(list.get(i).get("base")==null?"":list.get(i).get("base").toString());
                    rown.createCell(2).setCellValue(list.get(i).get("reinforce")==null?"":list.get(i).get("reinforce").toString());
                    rown.createCell(3).setCellValue(list.get(i).get("intensify")==null?"":list.get(i).get("intensify").toString());
                    rown.createCell(4).setCellValue(list.get(i).get("outburst")==null?"":list.get(i).get("outburst").toString());
                    rown.createCell(5).setCellValue(list.get(i).get("total").toString());
                }
                if (list.size() == i) {

                    rown.createCell(0).setCellValue("合计");
                    rown.createCell(1).setCellValue(totalBase);
                    rown.createCell(2).setCellValue(totalReinforce);
                    rown.createCell(3).setCellValue(totalIntensify);
                    rown.createCell(4).setCellValue(totalOutburst);
                    rown.createCell(5).setCellValue(total);
                }
                //设置表二的数据
                if (i < list2.size()) {
                    rown.createCell(8).setCellValue((String) list2.get(i).get("bio_name"));
                    rown.createCell(9).setCellValue(list2.get(i).get("times1").toString());
                    rown.createCell(10).setCellValue(list2.get(i).get("times2").toString());
                    rown.createCell(11).setCellValue(list2.get(i).get("times3").toString());
                    rown.createCell(12).setCellValue(list2.get(i).get("times4").toString());
                    rown.createCell(13).setCellValue(list2.get(i).get("times5").toString());
                    rown.createCell(14).setCellValue(list2.get(i).get("times6").toString());
                    rown.createCell(15).setCellValue(list2.get(i).get("times7").toString());
                    rown.createCell(16).setCellValue(list2.get(i).get("times8").toString());
                    rown.createCell(17).setCellValue(list2.get(i).get("total").toString());
                }
                if (list2.size() == i) {
                    rown.createCell(8).setCellValue("合计");
                    rown.createCell(9).setCellValue(totaltimes1);
                    rown.createCell(10).setCellValue(totaltimes2);
                    rown.createCell(11).setCellValue(totaltimes3);
                    rown.createCell(12).setCellValue(totaltimes4);
                    rown.createCell(13).setCellValue(totaltimes5);
                    rown.createCell(14).setCellValue(totaltimes6);
                    rown.createCell(15).setCellValue(totaltimes7);
                    rown.createCell(16).setCellValue(totaltimes8);
                    rown.createCell(17).setCellValue(totaltotaltimes);
                }
            }
            //        接种日志
            List<Map<String,Object>> historys = inoculateLosgDao.getInoulateLogss(param);
            for(Map<String,Object> mapshistorys:historys){
                if(mapshistorys.get("chil_committee")==null){
                    mapshistorys.put("chil_committee", "暂无归属");
                }
                if(mapshistorys.get("inoc_validdate")==null){
                    mapshistorys.put("inoc_validdate", "");
                }
            }
            int beginRow=list2.size()+10;
            HSSFRow rowTable3 = sheet.createRow(beginRow);
            HSSFCell cellTable3 = rowTable3.createCell(0);
            cellTable3.setCellValue("儿童接种日志信息表"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            sheet.addMergedRegion(new CellRangeAddress(beginRow, beginRow, 0, 18));
            HSSFCellStyle cellStyle3 = wb.createCellStyle();
            HSSFFont fonts = wb.createFont();
            //设置字体大小
            fonts.setFontHeightInPoints((short)10);
            cellStyle3.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 居左
            cellStyle3.setFont(fonts);
            sheet.setColumnWidth(11,20*480);
            sheet.setColumnWidth(4,20*160);
            sheet.setColumnWidth(1,20*80);
            sheet.setColumnWidth(2,20*150);
            sheet.setColumnWidth(5,20*160);
            sheet.setColumnWidth(6,20*150);
            sheet.setColumnWidth(10,20*140);
            sheet.setColumnWidth(13,20*150);
            sheet.setColumnWidth(12,20*80);
            sheet.setColumnWidth(14,20*80);
            sheet.setColumnWidth(16,20*80);
            sheet.setColumnWidth(15,20*150);
            sheet.setColumnWidth(17,20*160);


            //设置数据
            for (Integer i = 0,flag = 0; i < historys.size(); i++) {
                HSSFRow rown = null;
                rown = sheet.createRow(beginRow+3 + i+flag);
                if(i==0){
                    //设置表三列名
                    HSSFRow row5 = sheet.createRow(beginRow + 1);
                    row5.createCell(0).setCellValue("所在区域:"+historys.get(i).get("chil_committee")==null?"暂无归属":historys.get(i).get("chil_committee").toString());
                    sheet.addMergedRegion(new CellRangeAddress(beginRow + 1, beginRow + 1, 0, 18));
                    row5.getCell(0).setCellStyle(cellStyle3);
                    row5.setHeightInPoints(20);
                    HSSFRow row6 = sheet.createRow(beginRow + 2);
                    row6.createCell(0).setCellValue("儿童姓名");
                    row6.createCell(1).setCellValue("性别");
                    row6.createCell(2).setCellValue("出生日期");
                    row6.createCell(3).setCellValue("家长姓名");

                    row6.createCell(4).setCellValue("联系电话");
                    row6.createCell(5).setCellValue("手机号码");
                    row6.createCell(6).setCellValue("建卡日期");
                    row6.createCell(7).setCellValue("在册情况");

                    row6.createCell(8).setCellValue("通讯地址");
                    row6.createCell(9).setCellValue("接种疫苗");
                    row6.createCell(10).setCellValue("剂次");
                    row6.createCell(11).setCellValue("失效期");

                    row6.createCell(12).setCellValue("接种日期");
                    row6.createCell(13).setCellValue("免费");
                    row6.createCell(14).setCellValue("疫苗批号");
                }
                if(i>0 && !(historys.get(i).get("chil_committee").toString().trim().equals(historys.get(i-1).get("chil_committee").toString().trim()))){
                    rown.createCell(0).setCellValue("所在区域:"+historys.get(i).get("chil_committee").toString());
                    sheet.addMergedRegion(new CellRangeAddress(beginRow+3 + i+flag, beginRow+3 + i+flag, 0, 18));

                    rown.getCell(0).setCellStyle(cellStyle3);
                    rown.setHeightInPoints(20);
                    flag += 1;
                    rown = sheet.createRow(beginRow+3 + i+flag);
                    //rown.createCell(0).setCellValue("所在区域");
                    rown.createCell(0).setCellValue("儿童姓名");
                    rown.createCell(1).setCellValue("性别");
                    rown.createCell(2).setCellValue("出生日期");
                    rown.createCell(3).setCellValue("家长姓名");

                    rown.createCell(4).setCellValue("联系电话");
                    rown.createCell(5).setCellValue("手机号码");
                    rown.createCell(6).setCellValue("建卡日期");
                    rown.createCell(7).setCellValue("在册情况");

                    rown.createCell(8).setCellValue("通讯地址");
                    rown.createCell(9).setCellValue("接种疫苗");
                    rown.createCell(10).setCellValue("剂次");
                    rown.createCell(11).setCellValue("失效期");

                    rown.createCell(12).setCellValue("接种日期");
                    rown.createCell(13).setCellValue("免费");
                    rown.createCell(14).setCellValue("疫苗批号");
                    flag += 1;
                    rown = sheet.createRow(beginRow+3 + i+flag);
                }

                //rown.createCell(0).setCellValue(lists.get(i).get("villagey").toString());
                rown.createCell(0).setCellValue(historys.get(i).get("chil_name")==null?"":historys.get(i).get("chil_name").toString());
                rown.createCell(1).setCellValue(historys.get(i).get("chil_sex")==null?"":historys.get(i).get("chil_sex").toString());
                rown.createCell(2).setCellValue(historys.get(i).get("chil_birthday")==null?"":historys.get(i).get("chil_birthday").toString());
                rown.createCell(3).setCellValue(historys.get(i).get("chil_mother")==null?"":historys.get(i).get("chil_mother").toString());

                rown.createCell(4).setCellValue(historys.get(i).get("chilTel")==null?"":historys.get(i).get("chilTel").toString());
                rown.createCell(5).setCellValue(historys.get(i).get("chilMobile")==null?"":historys.get(i).get("chilMobile").toString());
                rown.createCell(6).setCellValue(historys.get(i).get("create_card_time")==null?"":historys.get(i).get("create_card_time").toString());
                rown.createCell(7).setCellValue(historys.get(i).get("chil_here")==null?"":historys.get(i).get("chil_here").toString());

                rown.createCell(8).setCellValue( historys.get(i).get("chil_address")==null?"": historys.get(i).get("chil_address").toString());
                rown.createCell(9).setCellValue(historys.get(i).get("bio_cn_sortname")==null?"":historys.get(i).get("bio_cn_sortname").toString());
                rown.createCell(10).setCellValue(historys.get(i).get("inoc_time")==null?"":historys.get(i).get("inoc_time").toString());
                rown.createCell(11).setCellValue( historys.get(i).get("inoc_validdate")==null?"":historys.get(i).get("inoc_validdate").toString());

                rown.createCell(12).setCellValue( historys.get(i).get("inoc_date")==null?"":historys.get(i).get("inoc_date").toString());
                rown.createCell(13).setCellValue( historys.get(i).get("inoc_free")==null?"":historys.get(i).get("inoc_free").toString());
                rown.createCell(14).setCellValue(historys.get(i).get("inoc_batchno")==null?"":historys.get(i).get("inoc_batchno").toString());
            }
            OutputStream output = response.getOutputStream();
//            System.out.println("output=" + output);
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + toUtf8String("接种日志表")+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * @method_name: IntegrityRate
     * @describe:接种信息完整性统计
     * @return void
     * @author 廖欢
     * @QQ: 1215077166@qq.com
     * @tel:18785185063
     **/
    @Override
    public void inoculateGather(List<InoculateIntegrityRateEntity> inoculatelists, String path, HttpServletResponse response) {
        String username = ShiroUtils.getUserEntity().getRealName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("接种信息完整性统计");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell01 = row0.createCell(22);
        HSSFCell cell = row1.createCell(0);
        // 设置单元格内容
        cell0.setCellValue("导出时间：");
        cell01.setCellValue("医生：");
        cell.setCellValue("接种信息完整性统计");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 23));

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 7));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 8, 9));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 10, 11));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 12, 13));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 14, 15));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 16, 17));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 18, 19));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 20, 21));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 22, 23));

        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        try {
            for (InoculateIntegrityRateEntity rate:inoculatelists) {
                Map<String, Object> maps = new HashMap<String,Object>();
                maps.put("bioName",rate.getBioName());//疫苗名称
                maps.put("totalInoTimes",rate.getTotalInoTimes());//剂次数
                maps.put("FullCount",rate.getFullCount());//完整度
                maps.put("inocIntegrityRate",rate.getInocIntegrityRate());//完整率

                maps.put("fullBioNameCount",rate.getFullBioNameCount());//接种疫苗
                maps.put("fullInoDateCount",rate.getFullInoDateCount());//接种日期
                maps.put("fullInocBatchnoCount",rate.getFullInocBatchnoCount());//疫苗批号
                maps.put("fullInocCorpCodeCount",rate.getFullInocCorpCodeCount());//生产企业

                maps.put("fullInocRoadCount",rate.getFullInocRoadCount());//接种途径
                maps.put("fullInocInplIdCount",rate.getFullInocInplIdCount());//接种部位
                maps.put("fullInocPlaceCount",rate.getFullInocPlaceCount());//接种地点
                maps.put("fullInocNurseCount",rate.getFullInocNurseCount());//接种医生

                maps.put("fullPayStatusCount",rate.getFullPayStatusCount());//收费状态
                maps.put("fullTimelyCount",rate.getFullTimelyCount());//及时录入

                lists.add(maps);

            }

            // 在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(2);
            HSSFRow row3 = sheet.createRow(3);
            // 创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("疫苗类别");
            row2.createCell(1).setCellValue("剂次数");
            row2.createCell(2).setCellValue("完整度");
            row2.createCell(3).setCellValue("完整率(%)");
            row2.createCell(4).setCellValue("接种疫苗");
            row2.createCell(6).setCellValue("接种日期");
            row2.createCell(8).setCellValue("疫苗批号");
            row2.createCell(10).setCellValue("生产企业");
            row2.createCell(12).setCellValue("接种途径");
            row2.createCell(14).setCellValue("接种部位");
            row2.createCell(16).setCellValue("接种地点");
            row2.createCell(18).setCellValue("接种医生");
            row2.createCell(20).setCellValue("收费状态");
            row2.createCell(22).setCellValue("及时录入");

            row3.createCell(4).setCellValue("录入数");
            row3.createCell(5).setCellValue("录入率");
            row3.createCell(6).setCellValue("录入数");
            row3.createCell(7).setCellValue("录入率");
            row3.createCell(8).setCellValue("录入数");
            row3.createCell(9).setCellValue("录入率");
            row3.createCell(10).setCellValue("录入数");
            row3.createCell(11).setCellValue("录入率");
            row3.createCell(12).setCellValue("录入数");
            row3.createCell(13).setCellValue("录入率");
            row3.createCell(14).setCellValue("录入数");
            row3.createCell(15).setCellValue("录入率");
            row3.createCell(16).setCellValue("录入数");
            row3.createCell(17).setCellValue("录入率");
            row3.createCell(18).setCellValue("录入数");
            row3.createCell(19).setCellValue("录入率");
            row3.createCell(20).setCellValue("录入数");
            row3.createCell(21).setCellValue("录入率");
            row3.createCell(22).setCellValue("录入数");
            row3.createCell(23).setCellValue("录入率");
            int totalInoTimes = 0;
            int FullCount = 0;
            double inocIntegrityRate = 0.0;
            int fullBioNameCount = 0;

            int fullInoDateCount = 0;
            int fullInocBatchnoCount = 0;
            int fullInocCorpCodeCount = 0;

            int fullInocRoadCount = 0;
            int fullInocInplIdCount = 0;
            int fullInocPlaceCount = 0;
            int fullInocNurseCount = 0;
            int fullTimelyCount = 0;
            int fullPayStatusCount = 0;
            Double bioNameEntryRate = 0.0;
            Double inoDateEntryRate = 0.0;
            Double inocBatchnoEntryRate = 0.0;
            Double inocCorpCodeEntryRate = 0.0;
            Double inocRoadEntryRate = 0.0;
            Double inocInplIdEntryRate = 0.0;
            Double inocPlaceEntryRate = 0.0;
            Double inocNurseEntryRate = 0.0;
            Double payStatusEntryRate = 0.0;
            Double TimelyEntryRate = 0.0;
            for (Integer i = 0; i < lists.size();) {
                HSSFRow rown = sheet.createRow(4 + i);
                totalInoTimes += Integer.parseInt(lists.get(i).get("totalInoTimes").toString());
                FullCount += Integer.parseInt(lists.get(i).get("FullCount").toString());
                inocIntegrityRate += Double.parseDouble(lists.get(i).get("inocIntegrityRate").toString());
                fullBioNameCount += Integer.parseInt(lists.get(i).get("fullBioNameCount").toString());

                fullInoDateCount += Integer.parseInt(lists.get(i).get("fullInoDateCount").toString());
                fullInocBatchnoCount += Integer.parseInt(lists.get(i).get("fullInocBatchnoCount").toString());
                fullInocCorpCodeCount += Integer.parseInt(lists.get(i).get("fullInocCorpCodeCount").toString());
                fullInocRoadCount += Integer.parseInt(lists.get(i).get("fullInocRoadCount").toString());

                fullInocInplIdCount += Integer.parseInt(lists.get(i).get("fullInocInplIdCount").toString());
                fullInocPlaceCount += Integer.parseInt(lists.get(i).get("fullInocPlaceCount").toString());
                fullInocNurseCount += Integer.parseInt(lists.get(i).get("fullInocNurseCount").toString());
                fullTimelyCount += Integer.parseInt(lists.get(i).get("fullTimelyCount").toString());
                fullPayStatusCount += Integer.parseInt(lists.get(i).get("fullPayStatusCount").toString());

                Integer total = Integer.parseInt(lists.get(i).get("totalInoTimes").toString());
                bioNameEntryRate = Integer.parseInt(lists.get(i).get("fullBioNameCount").toString())*1.0/total*100;
                inoDateEntryRate = Integer.parseInt(lists.get(i).get("fullInoDateCount").toString())*1.0/total*100;
                inocBatchnoEntryRate = Integer.parseInt(lists.get(i).get("fullInocBatchnoCount").toString())*1.0/total*100;
                inocCorpCodeEntryRate = Integer.parseInt(lists.get(i).get("fullInocCorpCodeCount").toString())*1.0/total*100;
                inocRoadEntryRate = Integer.parseInt(lists.get(i).get("fullInocRoadCount").toString())*1.0/total*100;
                inocInplIdEntryRate = Integer.parseInt(lists.get(i).get("fullInocInplIdCount").toString())*1.0/total*100;
                inocPlaceEntryRate = Integer.parseInt(lists.get(i).get("fullInocPlaceCount").toString())*1.0/total*100;
                inocNurseEntryRate = Integer.parseInt(lists.get(i).get("fullInocNurseCount").toString())*1.0/total*100;
                payStatusEntryRate = Integer.parseInt(lists.get(i).get("fullPayStatusCount").toString())*1.0/total*100;
                TimelyEntryRate = Integer.parseInt(lists.get(i).get("fullTimelyCount").toString())*1.0/total*100;

                rown.createCell(0).setCellValue(lists.get(i).get("bioName").toString());
                rown.createCell(1).setCellValue( lists.get(i).get("totalInoTimes").toString());
                rown.createCell(2).setCellValue(lists.get(i).get("FullCount").toString());
                rown.createCell(3).setCellValue(lists.get(i).get("inocIntegrityRate").toString());

                rown.createCell(4).setCellValue( lists.get(i).get("fullBioNameCount").toString());
                rown.createCell(5).setCellValue( Double.parseDouble(String.format("%.2f",bioNameEntryRate)));
                rown.createCell(6).setCellValue( lists.get(i).get("fullInoDateCount").toString());
                rown.createCell(7).setCellValue( Double.parseDouble(String.format("%.2f",inoDateEntryRate)));
                rown.createCell(8).setCellValue(lists.get(i).get("fullInocBatchnoCount").toString());
                rown.createCell(9).setCellValue(Double.parseDouble(String.format("%.2f",inocBatchnoEntryRate)));
                rown.createCell(10).setCellValue(lists.get(i).get("fullInocCorpCodeCount").toString());
                rown.createCell(11).setCellValue(Double.parseDouble(String.format("%.2f",inocCorpCodeEntryRate)));

                rown.createCell(12).setCellValue(lists.get(i).get("fullInocRoadCount").toString());
                rown.createCell(13).setCellValue(Double.parseDouble(String.format("%.2f",inocRoadEntryRate)));
                rown.createCell(14).setCellValue( lists.get(i).get("fullInocInplIdCount").toString());
                rown.createCell(15).setCellValue(Double.parseDouble(String.format("%.2f",inocInplIdEntryRate)));
                rown.createCell(16).setCellValue(lists.get(i).get("fullInocPlaceCount").toString());
                rown.createCell(17).setCellValue(Double.parseDouble(String.format("%.2f",inocPlaceEntryRate)));
                rown.createCell(18).setCellValue(lists.get(i).get("fullInocNurseCount").toString());
                rown.createCell(19).setCellValue(Double.parseDouble(String.format("%.2f",inocNurseEntryRate)));

                rown.createCell(20).setCellValue( lists.get(i).get("fullPayStatusCount").toString());
                rown.createCell(21).setCellValue( Double.parseDouble(String.format("%.2f",payStatusEntryRate)));
                rown.createCell(22).setCellValue( lists.get(i).get("fullTimelyCount").toString());
                rown.createCell(23).setCellValue( Double.parseDouble(String.format("%.2f",TimelyEntryRate)));
                for(int j = 0; j <=23;j++){
                    rown.getCell(j).setCellStyle(cellStyle2);
                }
                i++;
                if(i==lists.size()){
                    HSSFRow rowns = sheet.createRow(4 + i);
                    inocIntegrityRate = FullCount*1.0/totalInoTimes*100;
                    inocIntegrityRate = Double.parseDouble(String.format("%.2f", inocIntegrityRate));

                    bioNameEntryRate = Double.parseDouble(String.format("%.2f",fullBioNameCount*1.0/totalInoTimes*100));
                    inoDateEntryRate = Double.parseDouble(String.format("%.2f",fullInoDateCount*1.0/totalInoTimes*100));
                    inocBatchnoEntryRate = Double.parseDouble(String.format("%.2f",fullInocBatchnoCount*1.0/totalInoTimes*100));
                    inocCorpCodeEntryRate = Double.parseDouble(String.format("%.2f",fullInocCorpCodeCount*1.0/totalInoTimes*100));
                    inocRoadEntryRate = Double.parseDouble(String.format("%.2f",fullInocRoadCount*1.0/totalInoTimes*100));
                    inocInplIdEntryRate = Double.parseDouble(String.format("%.2f",fullInocInplIdCount*1.0/totalInoTimes*100));
                    inocPlaceEntryRate = Double.parseDouble(String.format("%.2f",fullInocPlaceCount*1.0/totalInoTimes*100));
                    inocNurseEntryRate = Double.parseDouble(String.format("%.2f",fullInocNurseCount*1.0/totalInoTimes*100));
                    payStatusEntryRate = Double.parseDouble(String.format("%.2f",fullPayStatusCount*1.0/totalInoTimes*100));
                    TimelyEntryRate = Double.parseDouble(String.format("%.2f",fullTimelyCount*1.0/totalInoTimes*100));

                    rowns.createCell(0).setCellValue("合计");
                    rowns.createCell(1).setCellValue(totalInoTimes);
                    rowns.createCell(2).setCellValue(FullCount);
                    rowns.createCell(3).setCellValue(inocIntegrityRate);

                    rowns.createCell(4).setCellValue(fullBioNameCount);
                    rowns.createCell(5).setCellValue(bioNameEntryRate);
                    rowns.createCell(6).setCellValue(fullInoDateCount);
                    rowns.createCell(7).setCellValue(inoDateEntryRate);
                    rowns.createCell(8).setCellValue(fullInocBatchnoCount);
                    rowns.createCell(9).setCellValue(inocBatchnoEntryRate);
                    rowns.createCell(10).setCellValue(fullInocCorpCodeCount);
                    rowns.createCell(11).setCellValue(inocCorpCodeEntryRate);

                    rowns.createCell(12).setCellValue(fullInocRoadCount);
                    rowns.createCell(13).setCellValue(inocRoadEntryRate);
                    rowns.createCell(14).setCellValue(fullInocInplIdCount);
                    rowns.createCell(15).setCellValue(inocInplIdEntryRate);
                    rowns.createCell(16).setCellValue(fullInocPlaceCount);
                    rowns.createCell(17).setCellValue(inocPlaceEntryRate);
                    rowns.createCell(18).setCellValue(fullInocNurseCount);
                    rowns.createCell(19).setCellValue(inocNurseEntryRate);

                    rowns.createCell(20).setCellValue(fullPayStatusCount);
                    rowns.createCell(21).setCellValue(payStatusEntryRate);
                    rowns.createCell(22).setCellValue(fullTimelyCount);
                    rowns.createCell(23).setCellValue(TimelyEntryRate);
                    for(int j = 0; j <=23;j++){
                        rowns.getCell(j).setCellStyle(cellStyle2);
                    }
                }

            }

            row0.createCell(23).setCellValue(username);
            row0.createCell(1).setCellValue(sdf.format(new Date()));
            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("接种信息完整性统计")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void childrenList(List<TChildInfoEntity> childrenList, Map<String,Object> param, HttpServletResponse response) {
        if(childrenList.isEmpty()){
            throw new RRException("导出的数据为空，请选择正确的查询条件！");
        }


        String username = ShiroUtils.getUserEntity().getRealName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("儿童基本信息表");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell1 = row0.createCell(3);
        HSSFCell cell01 = row0.createCell(17);
        HSSFCell cell = row1.createCell(0);
        // 设置单元格内容
        cell0.setCellValue("导出时间：");
        cell1.setCellValue("导出条件：");
        cell01.setCellValue("医生：");
        cell.setCellValue("儿童基本信息表");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 19));
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        try {
            // 在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(2);
            // 创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("儿童编码");
            row2.createCell(1).setCellValue("儿童姓名");
            row2.createCell(2).setCellValue("性别");
            row2.createCell(3).setCellValue("出生日期");

            row2.createCell(4).setCellValue("父亲姓名");
            row2.createCell(5).setCellValue("父亲身份证");
            row2.createCell(6).setCellValue("母亲姓名");
            row2.createCell(7).setCellValue("母亲身份证");
            row2.createCell(8).setCellValue("家庭电话");
            row2.createCell(9).setCellValue("手机号码");

            row2.createCell(10).setCellValue("居委会/行政村");
            row2.createCell(11).setCellValue("在册情况");

            row2.createCell(12).setCellValue("居住属性");
            row2.createCell(13).setCellValue("建档日期");
            row2.createCell(14).setCellValue("建档人");
            row2.createCell(15).setCellValue("建卡日期");
            row2.createCell(16).setCellValue("现管单位");

            row2.createCell(17).setCellValue("现居地址");
            row2.createCell(18).setCellValue("户籍地址");
            row2.createCell(19).setCellValue("个案变化日期");

            for (Integer i = 0; i < childrenList.size();) {
                HSSFRow rown = sheet.createRow(3 + i);
                rown.createCell(0).setCellValue(childrenList.get(i).getChilCode() == null || "undefined".equals(childrenList.get(i).getChilCode()) ? "" : childrenList.get(i).getChilCode());
                rown.createCell(1).setCellValue(childrenList.get(i).getChilName() == null ? "" : childrenList.get(i).getChilName());
                rown.createCell(2).setCellValue(childrenList.get(i).getChilSex() == null ? "" : childrenList.get(i).getChilSex());
                rown.createCell(3).setCellValue(childrenList.get(i).getChilBirthday() == null ? "" : sdf.format(childrenList.get(i).getChilBirthday()));

                rown.createCell(4).setCellValue(childrenList.get(i).getChilFather() == null ? "" : childrenList.get(i).getChilFather());
                rown.createCell(5).setCellValue(childrenList.get(i).getChilFatherno() == null ? "" : childrenList.get(i).getChilFatherno());
                rown.createCell(6).setCellValue(childrenList.get(i).getChilMother() == null ? "" : childrenList.get(i).getChilMother());
                rown.createCell(7).setCellValue(childrenList.get(i).getChilMotherno() == null ? "" : childrenList.get(i).getChilMotherno());
                rown.createCell(8).setCellValue(childrenList.get(i).getChilTel() == null ? "" : childrenList.get(i).getChilTel());
                rown.createCell(9).setCellValue(childrenList.get(i).getChilMobile() == null ? "" : childrenList.get(i).getChilMobile());

                rown.createCell(10).setCellValue(childrenList.get(i).getChilCommittee() == null ? "" : childrenList.get(i).getChilCommittee());
                rown.createCell(11).setCellValue(childrenList.get(i).getChilHere() == null ? "" : childrenList.get(i).getChilHere());
                rown.createCell(12).setCellValue(childrenList.get(i).getChilResidence() == null ? "" : childrenList.get(i).getChilResidence());
                rown.createCell(13).setCellValue(childrenList.get(i).getChilCreatedate() == null ? "" : sdf.format(childrenList.get(i).getChilCreatedate()));
                //rown.createCell(14).setCellValue(childrenList.get(i).getChilNo() == null ? "" : childrenList.get(i).getChilNo());
                rown.createCell(14).setCellValue(childrenList.get(i).getCreateUserName() == null ? "" : childrenList.get(i).getCreateUserName());
                rown.createCell(15).setCellValue(childrenList.get(i).getCreateCardTime() == null ? "" : sdf.format(childrenList.get(i).getCreateCardTime()));
                rown.createCell(16).setCellValue(childrenList.get(i).getChilCurdepartment() == null ? "" : childrenList.get(i).getChilCurdepartment());
                rown.createCell(17).setCellValue(childrenList.get(i).getChilAddress() == null ? "" : childrenList.get(i).getChilAddress());
                rown.createCell(18).setCellValue(childrenList.get(i).getChilHabiaddress() == null ? "" : childrenList.get(i).getChilHabiaddress());
                rown.createCell(19).setCellValue(childrenList.get(i).getChilLeavedate() == null ? "" : sdf.format(childrenList.get(i).getChilLeavedate()));

                for(int j = 0; j <=19;j++){
                    rown.getCell(j).setCellStyle(cellStyle2);
                }
                i++;
            }

            row0.createCell(18).setCellValue(username);
            row0.createCell(1).setCellValue(sdf.format(new Date()));

            row0.createCell(4).setCellValue("null".equals(param.get("chilCommittee"))?"":"行政村："+childrenList.get(0).getChilCommittee());
            if(param.get("chilCommittee")==null || param.get("chilCommittee").equals("null")){
                row0.createCell(4).setCellValue(param.get("chilHere").equals("null")?"":"在册情况："+childrenList.get(0).getChilHere());
                if("null".equals(param.get("chilHere"))){
                    row0.createCell(4).setCellValue(!("null".equals(param.get("chilBirthdayStart")) && "null".equals(param.get("chilBirthdayEnd")))?"出生日期："+param.get("chilBirthdayStart")+"~~"+param.get("chilBirthdayEnd"):"");
                    if("null".equals(param.get("chilBirthdayStart"))){
                        row0.createCell(4).setCellValue(!("null".equals(param.get("inoculateDateStart"))&& "null".equals(param.get("inoculateDateEnd")))?"接种日期："+param.get("inoculateDateStart")+"~~"+param.get("inoculateDateEnd"):"");
                        if("null".equals(param.get("inoculateDateStart"))){
                            row0.createCell(4).setCellValue("null".equals(param.get("chilCreatedateStart"))&& "null".equals(param.get("chilCreatedateEnd"))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");
                        }else{
                            row0.createCell(5).setCellValue(!("null".equals(param.get("chilCreatedateStart"))&& "null".equals(param.get("chilCreatedateEnd")))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");
                        }
                    }else{
                        row0.createCell(5).setCellValue(!("null".equals(param.get("inoculateDateStart"))&& "null".equals(param.get("inoculateDateEnd")))?"接种日期："+param.get("inoculateDateStart")+"~~"+param.get("inoculateDateEnd"):"");
                        if("null".equals(param.get("inoculateDateStart"))){
                            row0.createCell(5).setCellValue(!("null".equals(param.get("chilCreatedateStart"))&& "null".equals(param.get("chilCreatedateEnd")))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");

                        }
                        row0.createCell(6).setCellValue(!("null".equals(param.get("chilBirthdayStart")) && "null".equals(param.get("chilBirthdayEnd")))?"出生日期："+param.get("chilBirthdayStart")+"~~"+param.get("chilBirthdayEnd"):"");

                    }
                }else{
                    row0.createCell(5).setCellValue(!("null".equals(param.get("chilBirthdayStart")) && "null".equals(param.get("chilBirthdayEnd")))?"出生日期："+param.get("chilBirthdayStart")+"~~"+param.get("chilBirthdayEnd"):"");
                    row0.createCell(6).setCellValue(!("null".equals(param.get("chilCreatedateStart"))&& "null".equals(param.get("chilCreatedateEnd")))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");
                }
            }else {
                row0.createCell(5).setCellValue(param.get("chilHere")==null?"":param.get("chilHere").equals("null")?"":"在册情况："+childrenList.get(0).getChilHere());
                row0.createCell(6).setCellValue(param.get("chilCreatedateStart")!=null&& param.get("chilCreatedateEnd")!=null && !(param.get("chilCreatedateEnd").equals("null")&&param.get("chilCreatedateStart").equals("null"))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");

            }
            row0.createCell(7).setCellValue(param.get("isUpload")==null?"":param.get("isUpload").equals("null")?"":"上传状态："+param.get("isUpload")=="false"?"":"未上传");
            row0.createCell(8).setCellValue(param.get("currentDepartChild").equals("null") || param.get("currentDepartChild").equals("undefined")?"":"儿童归属：本单位儿童");



            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("儿童基本信息表")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }



    }
    @Override
    public void exportAlodmotcigip(String year,String month,Map<String, Object> map,String [] chilCommittees, HttpServletResponse response) {
        int rowHeight = 26;
        /*String firstDayOfMonth = DateUtils.getFirstDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));
        String lastDayOfMonth = DateUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month));*/
        map.put("firstDay",year);
        map.put("lastDay",month);
       /* if(map.get("chilCommittees")==null || "".equals(map.get("chilCommittees")) || "null".equals(map.get("chilCommittees"))){
        }else{
            String str2=map.get("chilCommittees").toString();
            String chilCommitteess[]= str2.split(",");
            map.put("chilCommittees", chilCommitteess);
        }
        map.put("orgId", Constant.GLOBAL_ORG_ID);*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("贵州省免疫规划目标儿童动态管理一览表");
        //sheet.setDefaultRowHeight((short)(20 * 50));//设置默认行高
        sheet.setDefaultRowHeightInPoints(rowHeight);
        //设置样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        HSSFCellStyle cellStyleMediate = wb.createCellStyle();
        cellStyleMediate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle2.setWrapText(true);

        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);

        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle3.setWrapText(true);
        //设置样式结束

        //画边框线
        for(int i=2;i<19;i++){
            HSSFRow row = sheet.createRow(i);
            row.setHeightInPoints(rowHeight);
            for(int j=0;j<15;j++){
                HSSFCell cel = row.createCell(j);
                cel.setCellStyle(cellStyle3);
            }
        }

        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeightInPoints(rowHeight);
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeightInPoints(30);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell1 = row0.createCell(3);
        HSSFCell cell3 = row0.createCell(4);
        HSSFCell cell01 = row0.createCell(13);
        HSSFCell cell = row1.createCell(0);

        // 设置单元格内容
        cell0.setCellValue("导出时间：");
        row0.createCell(1).setCellValue(sdf.format(new Date()));
        cell1.setCellValue("导出条件：");
        cell3.setCellValue("开始时间："+map.get("firstDay")+",结束时间："+map.get("lastDay"));
        cell01.setCellValue("医生：");
        SysUserEntity user = ShiroUtils.getUserEntity();
        row0.createCell(14).setCellValue(user.getRealName());
        cell.setCellValue("贵州省免疫规划目标儿童动态管理一览表");
        cell.setCellStyle(cellStyle);
        //画表头
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 14));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 14));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 14));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 8, 14));

        HSSFRow row2 = sheet.createRow(2);
        row2.setHeightInPoints(rowHeight);
        HSSFCell cell20 = row2.createCell(0);
        String[] strData = month.split("-");
        HSSFRichTextString str = new HSSFRichTextString("（"+strData[0]+"年"+strData[1]+"月）");//当且单元格的内容
        /*HSSFFont fontxhx = wb.createFont();
        //fontxhx.setFontName(HSSFFont.FONT_ARIAL);
        fontxhx.setUnderline(Font.U_SINGLE);//设置有下划线的字体
        str.applyFont(0,5,fontxhx);
        str.applyFont(7,11,fontxhx);
        //cel20.setCellType(HSSFCell.CELL_TYPE_STRING);*/
        cell20.setCellValue(str);
        //设置这个单元格样式
        HSSFCellStyle cel20Style = wb.createCellStyle();
        cel20Style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cel20Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        //cel20Style.setFont(font);
        cell20.setCellStyle(cel20Style);

        HSSFRow row3 = sheet.createRow(3);
        row3.setHeightInPoints(rowHeight);
        HSSFCell cell30 = row3.createCell(0);
        HSSFRichTextString cell30Str = new HSSFRichTextString(" 填报单位：_________县（市、区）___________乡镇（街道办）____________村（居委会）");//当且单元格的内容
        cell30.setCellValue(cell30Str);
        cell30.setCellStyle(cellStyleMediate);

        HSSFRow row4 = sheet.createRow(4);
        row4.setHeightInPoints(rowHeight);
        HSSFCell cell40 = row4.createCell(0);
        HSSFRichTextString cell40Str = new HSSFRichTextString(" 填报人：__________________");//当且单元格的内容
        cell40.setCellValue(cell40Str);
        cell40.setCellStyle(cellStyleMediate);
        HSSFCell cell48 = row4.createCell(8);
        HSSFRichTextString cell48Str = new HSSFRichTextString("填报日期：_______年______月_____日");//当且单元格的内容
        cell48.setCellValue(cell48Str);
        cell48.setCellStyle(cel20Style);





        for(int i = 0;i<= 7;i++){
            sheet.addMergedRegion(new CellRangeAddress(5, 7, i, i));
        }
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 8, 11));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 9));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 10, 11));
        //sheet.addMergedRegion(new CellRangeAddress(2, 4, 12, 15));
        for(int i = 12;i< 15;i++){
            sheet.addMergedRegion(new CellRangeAddress(5, 7, i, i));
        }


        HSSFRow row5 = sheet.createRow(5);

        HSSFRow row6 = sheet.createRow(6);
        HSSFRow row7 = sheet.createRow(7);
        for (int i = 0; i <15 ; i++) {
            row5.createCell(i).setCellStyle(cellStyle3);
            row6.createCell(i).setCellStyle(cellStyle3);
            row7.createCell(i).setCellStyle(cellStyle3);
        }
        row5.setHeightInPoints(rowHeight);
        row6.setHeightInPoints(rowHeight);
        row7.setHeightInPoints(rowHeight+10);
        String[] row1Conet = {"编号","父亲姓名","母亲姓名","儿童姓名","性别","出生日期","家庭详细地址","联系电话","分娩情况","乙肝首针接种情况","分类","备注"};
        for(int i = 0;i < row1Conet.length;i++){
            HSSFCell cel;
            if(i>8){
                cel = row5.getCell(i+3);
            }else{
                cel = row5.getCell(i);
            }
            cel.setCellValue(row1Conet[i]);
            cel.setCellStyle(cellStyle2);
        }
        HSSFCell cel38 = row6.getCell(8);

        cel38.setCellValue("住院");
        HSSFCell cel310 = row6.getCell(10);
        cel310.setCellValue("在家");
        HSSFCell cel48 = row7.getCell(8);
        cel48.setCellValue("县级以下");
        HSSFCell cel49 = row7.getCell(9);
        cel49.setCellValue("乡级");
        HSSFCell cel410 = row7.getCell(10);
        cel410.setCellValue("外省");
        HSSFCell cel411 = row7.getCell(11);
        cel411.setCellValue("本省");


        List<Map<String, Object>> list = dynamicChildService.queryList(map);
        HSSFRow row19 = sheet.createRow(list.size()+8);
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.createRow(i + 8);
            Map<String, Object> maps = list.get(i);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(maps.get("chil_father")==null?"":maps.get("chil_father").toString());
            row.createCell(2).setCellValue(maps.get("chil_mother")==null?"":maps.get("chil_mother").toString());
            row.createCell(3).setCellValue(maps.get("chil_name")==null?"":maps.get("chil_name").toString());
            row.createCell(4).setCellValue(maps.get("chilSex")==null?"":maps.get("chilSex").toString());
            row.createCell(5).setCellValue(maps.get("chil_birthday")==null?"":maps.get("chil_birthday").toString());
            row.createCell(6).setCellValue(maps.get("chil_address")==null?"":maps.get("chil_address").toString());
            row.createCell(7).setCellValue(maps.get("chilTel")==null?"":maps.get("chilTel").toString());
            row.createCell(8).setCellValue(maps.get("xianji")==null?"":maps.get("xianji").toString());
            row.createCell(9).setCellValue(maps.get("xiang")==null?"":maps.get("xiang").toString());
            row.createCell(10).setCellValue(maps.get("waisheng")==null?"":maps.get("waisheng").toString());
            row.createCell(11).setCellValue(maps.get("bensheng")==null?"":maps.get("bensheng").toString());
            row.createCell(12).setCellValue(maps.get("inotime")==null?"":maps.get("inotime").toString());
            row.createCell(13).setCellValue(maps.get("type")==null?"":maps.get("type").toString());
            row.createCell(14).setCellValue(maps.get("remarks")==null?"":maps.get("remarks").toString());
            for (int j = 0; j <15 ; j++) {
                row.getCell(j).setCellStyle(cellStyle3);
            }

        }
        row19.setHeightInPoints(43);
        sheet.addMergedRegion(new CellRangeAddress(list.size()+8, list.size()+8, 0, 14));
        HSSFCell cel190 = row19.createCell(0);
        HSSFRichTextString ts = new HSSFRichTextString("注：1、“分娩情况” 填写：如为“住院” 分娩，请填写具体分娩医院名称：如为“在家”分娩；在相应栏目内划“√”。" +
                "2、“乙肝首针接种情况”栏目内请填写具体接种日期，如未接种，填写“否”。3、“分类”填写：A、新出生儿童 B、新发现在儿童" +
                "C、新流入儿童 D、本地流出儿童。如为C、D、，请在备注栏填写流入、流出时间和地区（省、县、乡）。" +
                "4、此表由承担摸底工作的机构每月填写（报）1次，无目标对象应进行“零”报告。");//当且单元格的内容
        cel190.setCellValue(ts);
        HSSFCellStyle cellStyle5 = wb.createCellStyle();
        cellStyle5.setWrapText(true);//是否自动换行
        cel190.setCellStyle(cellStyle5);

        sheet.setColumnWidth(0, 20 * 70);
        sheet.setColumnWidth(1, 20 * 120);
        sheet.setColumnWidth(2, 20 * 120);
        sheet.setColumnWidth(3, 20 * 120);
        sheet.setColumnWidth(4, 20 * 60);
        sheet.setColumnWidth(5, 20 * 120);
        sheet.setColumnWidth(6, 20 * 160);
        sheet.setColumnWidth(7, 20 * 120);
        sheet.setColumnWidth(8, 20 * 70);
        sheet.setColumnWidth(9, 20 * 70);
        sheet.setColumnWidth(10, 20 * 70);
        sheet.setColumnWidth(11, 20 * 70);
        sheet.setColumnWidth(12, 20 * 110);
        sheet.setColumnWidth(13, 20 * 70);
        sheet.setColumnWidth(14, 20 * 345);

        //设置横向打印
        sheet.getPrintSetup().setLandscape(true);

        //设置第一个工作簿上的打印区域  方法2
        //这里后面的四个参数和合并单元格的参数的意思是一样的
        wb.setPrintArea(
                0, //工作薄 下标0开始
                0, //起始列 下标0开始
                14, //终止列 下标0开始
                1, //起始行 下标0开始
                19  //终止行 下标0开始
        );
        //设置上下左右页边距
        sheet.setMargin(HSSFSheet.TopMargin, 0.5);
        sheet.setMargin(HSSFSheet.BottomMargin, 0.5);
        sheet.setMargin(HSSFSheet.LeftMargin, 0.4);
        sheet.setMargin(HSSFSheet.RightMargin, 0.4);
        //添加数据

        try {
            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("贵州省免疫规划目标儿童动态管理一览表")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void exportNiprvsr(Map<String, Object> map, HttpServletResponse response) {
       List<SixToOneResult> list = sixToOneStatisticsService.queryTotalList(map,null);
        /*List<SixToOneResult> list = new ArrayList<>(35);
        for (int i=0;i<35;i++){
            SixToOneResult sixToOneResult = new SixToOneResult();
            sixToOneResult.setLocalShould(i);
            sixToOneResult.setLocalReal(i);
            sixToOneResult.setMoveShould(i);
            sixToOneResult.setMoveReal(i);
            list.add(sixToOneResult);
        }*/
        int row = 3;
        int rowHeight = 15;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("表6-1");

        sheet.setDefaultRowHeightInPoints(16);//设置默认行高

        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeightInPoints(rowHeight);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell1 = row0.createCell(2);
        HSSFCell cell2 = row0.createCell(3);
        HSSFCell cell01 = row0.createCell(5);
        HSSFCell cell = row1.createCell(0);

        // 设置单元格内容
        cell0.setCellValue("导出时间：");
        row0.createCell(1).setCellValue(sdf.format(new Date()));
        cell1.setCellValue("导出条件：");
        cell2.setCellValue("开始时间："+map.get("startDate")+",结束时间："+map.get("endDate"));
        cell01.setCellValue("医生：");
        SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();//获取当前用户
        row0.createCell(6).setCellValue(sysUserEntity.getRealName());

        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        HSSFCellStyle cellStyle4 = wb.createCellStyle();
        HSSFCellStyle cellStyle3 = wb.createCellStyle();

        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直

        cellStyle4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直

        cellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        //cellStyle3.setWrapText(true);//是否自动换行
        //cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 14);//设置字体大小
        cellStyle.setFont(font);
        cell.setCellValue("表6-1");
        cell.setCellStyle(cellStyle);


        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 6));
        HSSFRow addrow22 = sheet.createRow(2);
        HSSFRow addrow23 = sheet.createRow(3);
        HSSFRow addrow24 = sheet.createRow(4);
        addrow22.setHeightInPoints(rowHeight);
        addrow23.setHeightInPoints(rowHeight);
        addrow24.setHeightInPoints(rowHeight);

        HSSFCell cel20 = addrow22.createCell(0);
        HSSFRichTextString str = new HSSFRichTextString("      年     月国家免疫规划疫苗常规接种情况报表");//当且单元格的内容
        HSSFFont fontxhx = wb.createFont();
        //fontxhx.setFontName(HSSFFont.FONT_ARIAL);
        fontxhx.setUnderline(Font.U_SINGLE);//设置有下划线的字体
        str.applyFont(0,5,fontxhx);
        str.applyFont(7,11,fontxhx);
        //cel20.setCellType(HSSFCell.CELL_TYPE_STRING);
        cel20.setCellValue(str);
        //设置这个单元格样式
        HSSFCellStyle cel20Style = wb.createCellStyle();
        cel20Style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cel20Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cel20Style.setFont(font);
        cel20.setCellStyle(cel20Style);

        HSSFCell addcel230 = addrow23.createCell(0);
        HSSFRichTextString strl230 = new HSSFRichTextString(" 贵州  省        市（州、地区）       县（市、区）          （镇、街道）");//当且单元格的内容
        fontxhx.setUnderline(Font.U_SINGLE);//设置有下划线的字体
        strl230.applyFont(1,5,fontxhx);
        strl230.applyFont(6,14,fontxhx);
        strl230.applyFont(21,28,fontxhx);
        strl230.applyFont(34,44,fontxhx);
        addcel230.setCellValue(strl230);

        HSSFCell addcel240 = addrow24.createCell(0);
        HSSFRichTextString strl240 = new HSSFRichTextString(" 接种单位名称：              填表日期：     月     日  填表人：             ");//当且单元格的内容
        fontxhx.setUnderline(Font.U_SINGLE);//设置有下划线的字体
        strl240.applyFont(8,19,fontxhx);
        strl240.applyFont(27,32,fontxhx);
        strl240.applyFont(34,38,fontxhx);
        strl240.applyFont(45,58,fontxhx);
        addcel240.setCellValue(strl240);


        //画表头
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));


        //画边框线
        /*for(int i=2;i<18;i++){
            HSSFRow row = sheet.createRow(i);
            for(int j=0;j<15;j++){
                HSSFCell cel = row.createCell(j);
                cel.setCellStyle(cellStyle3);
            }

        }*/
        HSSFRow row2 = sheet.createRow(2+row);
        HSSFRow row3 = sheet.createRow(3+row);
        row2.setHeightInPoints(rowHeight);
        row3.setHeightInPoints(rowHeight);
        //画表头
        sheet.addMergedRegion(new CellRangeAddress(2+row, 3+row, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(2+row, 2+row, 3, 4));
        sheet.addMergedRegion(new CellRangeAddress(2+row, 2+row, 5, 6));

        HSSFCell cell20 = row2.createCell(0);
        cell20.setCellValue("疫苗");
        cell20.setCellStyle(cellStyle2);
        HSSFCell cell22 = row2.createCell(3);
        cell22.setCellValue("本地儿童");
        cell22.setCellStyle(cellStyle2);
        HSSFCell cell24 = row2.createCell(5);
        cell24.setCellValue("外来儿童");
        cell24.setCellStyle(cellStyle2);
        HSSFCell cell32 = row3.createCell(3);
        cell32.setCellValue("应种剂次数");
        cell32.setCellStyle(cellStyle2);
        HSSFCell cell33 = row3.createCell(4);
        cell33.setCellValue("实种剂次数");
        cell33.setCellStyle(cellStyle2);
        HSSFCell cell34 = row3.createCell(5);
        cell34.setCellValue("应种剂次数");
        cell34.setCellStyle(cellStyle2);
        HSSFCell cell35 = row3.createCell(6);
        cell35.setCellValue("实种剂次数");
        cell35.setCellStyle(cellStyle2);
        //画表头完成
        HSSFRow row4 = sheet.createRow(4+row);
        row4.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(4+row, 7+row, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(4+row, 4+row, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(5+row, 5+row, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(6+row, 6+row, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(7+row, 7+row, 1, 2));
        HSSFRow row5 = sheet.createRow(5+row);
        HSSFRow row6 = sheet.createRow(6+row);
        HSSFRow row7 = sheet.createRow(7+row);
        row5.setHeightInPoints(rowHeight);
        row6.setHeightInPoints(rowHeight);
        row7.setHeightInPoints(rowHeight);
        HSSFCell cell40 = row4.createCell(0);
        cell40.setCellValue("乙肝疫苗");
        cell40.setCellStyle(cellStyle4);
        HSSFCell cell41 = row4.createCell(1);
        cell41.setCellValue("1");
        cell41.setCellStyle(cellStyle2);
        HSSFCell cell5 = row5.createCell(1);
        cell5.setCellValue("1（及时）");
        cell5.setCellStyle(cellStyle2);
        HSSFCell cell6 = row6.createCell(1);
        cell6.setCellValue("2");
        cell6.setCellStyle(cellStyle2);
        HSSFCell cell7 = row7.createCell(1);
        cell7.setCellValue("3");
        cell7.setCellStyle(cellStyle2);

        HSSFRow row8 = sheet.createRow(8+row);
        row8.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(8+row, 8+row, 0, 2));
        HSSFCell cell80 = row8.createCell(0);
        cell80.setCellValue("卡介苗");
        cell80.setCellStyle(cellStyle4);

        HSSFRow row9 = sheet.createRow(9+row);
        HSSFRow row10 = sheet.createRow(10+row);
        HSSFRow row11 = sheet.createRow(11+row);
        HSSFRow row12 = sheet.createRow(12+row);
        row9.setHeightInPoints(rowHeight);
        row10.setHeightInPoints(rowHeight);
        row11.setHeightInPoints(rowHeight);
        row12.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(9+row, 12+row, 0, 0));
        HSSFCell cell90 = row9.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(9+row, 9+row, 1, 2));
        cell90.setCellValue("灰疫苗");
        cell90.setCellStyle(cellStyle4);;
        HSSFCell cell91 = row9.createCell(1);
        cell91.setCellValue("1");
        cell91.setCellStyle(cellStyle2);
        HSSFCell cell100 = row10.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(10+row, 10+row, 1, 2));
        cell100.setCellValue("2");
        cell100.setCellStyle(cellStyle2);
        HSSFCell cell110 = row11.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(11+row, 11+row, 1, 2));
        cell110.setCellValue("3");
        cell110.setCellStyle(cellStyle2);
        HSSFCell cell120 = row12.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(12+row, 12+row, 1, 2));
        cell120.setCellValue("4");
        cell120.setCellStyle(cellStyle2);

        HSSFRow row13 = sheet.createRow(13+row);
        HSSFRow row14 = sheet.createRow(14+row);
        HSSFRow row15 = sheet.createRow(15+row);
        HSSFRow row16 = sheet.createRow(16+row);
        row13.setHeightInPoints(rowHeight);
        row14.setHeightInPoints(rowHeight);
        row15.setHeightInPoints(rowHeight);
        row16.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(13+row, 16+row, 0, 0));
        HSSFCell cell130 = row13.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(13+row, 13+row, 1, 2));
        cell130.setCellValue("百白破疫苗");
        cell130.setCellStyle(cellStyle4);
        HSSFCell cell131 = row13.createCell(1);
        cell131.setCellValue("1");
        cell131.setCellStyle(cellStyle2);
        HSSFCell cell141 = row14.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(14+row, 14+row, 1, 2));
        cell141.setCellValue("2");
        cell141.setCellStyle(cellStyle2);
        HSSFCell cell151 = row15.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(15+row, 15+row, 1, 2));
        cell151.setCellValue("3");
        cell151.setCellStyle(cellStyle2);
        HSSFCell cell161 = row16.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(16+row, 16+row, 1, 2));
        cell161.setCellValue("4");
        cell161.setCellStyle(cellStyle2);

        HSSFRow row17 = sheet.createRow(17+row);
        row17.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(17+row, 17+row, 0, 2));
        HSSFCell cell170 = row17.createCell(0);
        cell170.setCellValue("白破疫苗");
        cell170.setCellStyle(cellStyle4);

        //第二表格

        HSSFRow row18 = sheet.createRow(18+row);
        HSSFRow row19 = sheet.createRow(19+row);
        row18.setHeightInPoints(rowHeight);
        row19.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(18+row, 19+row, 0, 1));
        HSSFCell cell180 = row18.createCell(0);
        cell180.setCellValue("麻风疫苗");
        cell180.setCellStyle(cellStyle4);
        HSSFCell cell181 = row18.createCell(2);
        cell181.setCellValue("1");
        cell181.setCellStyle(cellStyle2);
        HSSFCell cell190 = row19.createCell(2);
        cell190.setCellValue("2");
        cell190.setCellStyle(cellStyle2);

        HSSFRow row20 = sheet.createRow(20+row);
        HSSFRow row21 = sheet.createRow(21+row);
        row20.setHeightInPoints(rowHeight);
        row21.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(20+row, 21+row, 0, 1));
        HSSFCell cell200 = row20.createCell(0);
        cell200.setCellValue("麻腮风疫苗");
        cell200.setCellStyle(cellStyle4);
        HSSFCell cell201 = row20.createCell(2);
        cell201.setCellValue("1");
        cell201.setCellStyle(cellStyle2);
        HSSFCell cell211 = row21.createCell(2);
        cell211.setCellValue("2");
        cell211.setCellStyle(cellStyle2);

        HSSFRow row22 = sheet.createRow(22+row);
        HSSFRow row23 = sheet.createRow(23+row);
        row22.setHeightInPoints(rowHeight);
        row23.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(22+row, 23+row, 0, 1));
        HSSFCell cell220 = row22.createCell(0);
        cell220.setCellValue("麻腮疫苗");
        cell220.setCellStyle(cellStyle4);
        HSSFCell cell221 = row22.createCell(2);
        cell221.setCellValue("1");
        cell221.setCellStyle(cellStyle2);
        HSSFCell cell230 = row23.createCell(2);
        cell230.setCellValue("2");
        cell230.setCellStyle(cellStyle2);

        HSSFRow row24 = sheet.createRow(24+row);
        HSSFRow row25 = sheet.createRow(25+row);
        row24.setHeightInPoints(rowHeight);
        row25.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(24+row, 25+row, 0, 1));
        HSSFCell cell240 = row24.createCell(0);
        cell240.setCellValue("麻疹疫苗");
        cell240.setCellStyle(cellStyle4);
        HSSFCell cell241 = row24.createCell(2);
        cell241.setCellValue("1");
        cell241.setCellStyle(cellStyle2);
        HSSFCell cell251 = row25.createCell(2);
        cell251.setCellValue("2");
        cell251.setCellStyle(cellStyle2);

        HSSFRow row26 = sheet.createRow(26+row);
        HSSFRow row27 = sheet.createRow(27+row);
        row26.setHeightInPoints(rowHeight);
        row27.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(26+row, 27+row, 0, 1));
        HSSFCell cell260 = row26.createCell(0);
        cell260.setCellValue("A群流脑疫苗");
        cell260.setCellStyle(cellStyle4);
        HSSFCell cell261 = row26.createCell(2);
        cell261.setCellValue("1");
        cell261.setCellStyle(cellStyle2);
        HSSFCell cell270 = row27.createCell(2);
        cell270.setCellValue("2");
        cell270.setCellStyle(cellStyle2);

        HSSFRow row28 = sheet.createRow(28+row);
        HSSFRow row29 = sheet.createRow(29+row);
        row28.setHeightInPoints(rowHeight);
        row29.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(28+row, 29+row, 0, 1));
        HSSFCell cell280 = row28.createCell(0);
        cell280.setCellValue("A+C群流脑疫苗");
        cell280.setCellStyle(cellStyle4);
        HSSFCell cell281 = row28.createCell(2);
        cell281.setCellValue("1");
        cell281.setCellStyle(cellStyle2);
        HSSFCell cell290 = row29.createCell(2);
        cell290.setCellValue("2");
        cell290.setCellStyle(cellStyle2);

        HSSFRow row30 = sheet.createRow(30+row);
        HSSFRow row31 = sheet.createRow(31+row);
        row30.setHeightInPoints(rowHeight);
        row31.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(30+row, 31+row, 0, 1));
        HSSFCell cell300 = row30.createCell(0);
        cell300.setCellValue("乙脑减毒活疫苗");
        cell300.setCellStyle(cellStyle4);
        HSSFCell cell301 = row30.createCell(2);
        cell301.setCellValue("1");
        cell301.setCellStyle(cellStyle2);
        HSSFCell cell310 = row31.createCell(2);
        cell310.setCellValue("2");
        cell310.setCellStyle(cellStyle2);

        HSSFRow row32 = sheet.createRow(32+row);
        HSSFRow row33 = sheet.createRow(33+row);
        HSSFRow row34 = sheet.createRow(34+row);
        HSSFRow row35 = sheet.createRow(35+row);
        row32.setHeightInPoints(rowHeight);
        row33.setHeightInPoints(rowHeight);
        row34.setHeightInPoints(rowHeight);
        row35.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(32+row, 35+row, 0, 1));
        HSSFCell cell320 = row32.createCell(0);
        cell320.setCellValue("乙脑灭活疫苗");
        cell320.setCellStyle(cellStyle4);
        HSSFCell cell321 = row32.createCell(2);
        cell321.setCellValue("1");
        cell321.setCellStyle(cellStyle2);
        HSSFCell cell331 = row33.createCell(2);
        cell331.setCellValue("2");
        cell331.setCellStyle(cellStyle2);
        HSSFCell cell341 = row34.createCell(2);
        cell341.setCellValue("3");
        cell341.setCellStyle(cellStyle2);
        HSSFCell cell351 = row35.createCell(2);
        cell351.setCellValue("4");
        cell351.setCellStyle(cellStyle2);

        HSSFRow row36 = sheet.createRow(36+row);
        row36.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(36+row, 36+row, 0, 2));
        HSSFCell cell360 = row36.createCell(0);
        cell360.setCellValue("甲肝减毒活疫苗");
        cell360.setCellStyle(cellStyle4);

        HSSFRow row37 = sheet.createRow(37+row);
        HSSFRow row38 = sheet.createRow(38+row);
        row37.setHeightInPoints(rowHeight);
        row38.setHeightInPoints(rowHeight);
        sheet.addMergedRegion(new CellRangeAddress(37+row, 38+row, 0, 1));
        HSSFCell cell370 = row37.createCell(0);
        cell370.setCellValue("甲肝灭活疫苗");
        cell370.setCellStyle(cellStyle4);
        HSSFCell cell372 = row37.createCell(2);
        cell372.setCellValue("1");
        cell372.setCellStyle(cellStyle2);

        HSSFCell cell382 = row38.createCell(2);
        cell382.setCellValue("2");
        cell382.setCellStyle(cellStyle2);

        HSSFRow row42 = sheet.createRow(42);
        HSSFCell cell420 = row42.createCell(0);
        cell420.setCellValue("填写说明：");
        row42.setHeightInPoints(20);

        HSSFRow row43 = sheet.createRow(43);
        row43.setHeightInPoints(40);
        sheet.addMergedRegion(new CellRangeAddress(43, 43, 0, 6));
        HSSFCell cell430 = row43.createCell(0);
        HSSFRichTextString ts = new HSSFRichTextString("1.乡级防保组织每月5日前收集辖区内接种单位接种数据，" +
                "汇总后上级县级疾控机构：县级疾控 机构每月10日前将分乡或接种单位的接种数据录入或导入《中国免疫规划监测信息管理系统》" +
                "4.0 以上版本。2、麻疹类疫苗应和人数：第一剂填在麻风疫苗第一剂栏：第二剂次填出麻腮风疫苗 第二剂栏。");//当且单元格的内容
        cell430.setCellValue(ts);
        HSSFCellStyle cellStyle5 = wb.createCellStyle();
        cellStyle5.setWrapText(true);//是否自动换行
        cell430.setCellStyle(cellStyle5);

        sheet.setColumnWidth(0, 20 * 150);
        sheet.setColumnWidth(2, 20 * 150);
        sheet.setColumnWidth(3, 20 * 180);
        sheet.setColumnWidth(4, 20 * 180);
        sheet.setColumnWidth(5, 20 * 180);
        sheet.setColumnWidth(6, 20 * 180);

        //设置第一个工作簿上的打印区域  方法2
        //这里后面的四个参数和合并单元格的参数的意思是一样的
        wb.setPrintArea(
                0, //工作薄 下标0开始
                0, //起始列 下标0开始
                6, //终止列 下标0开始
                1, //起始行 下标0开始
                43  //终止行 下标0开始
        );
        //设置上下左右页边距
        sheet.setMargin(HSSFSheet.TopMargin, 0.8);
        sheet.setMargin(HSSFSheet.BottomMargin, 0.8);
        sheet.setMargin(HSSFSheet.LeftMargin, 0.8);
        sheet.setMargin(HSSFSheet.RightMargin, 0.8);
        /**
         * 添加数据
         **/
        int rowdata = 7;
        for (SixToOneResult rult : list){
            HSSFRow newrow = sheet.getRow(rowdata);
            HSSFCell cellnew4 = newrow.createCell(3);
            cellnew4.setCellValue(rult.getLocalShould());
            cellnew4.setCellStyle(cellStyle2);
            HSSFCell cellnew5 = newrow.createCell(4);
            cellnew5.setCellValue(rult.getLocalReal());
            cellnew5.setCellStyle(cellStyle2);
            HSSFCell cellnew6 = newrow.createCell(5);
            cellnew6.setCellValue(rult.getMoveShould());
            cellnew6.setCellStyle(cellStyle2);
            HSSFCell cellnew7 = newrow.createCell(6);
            cellnew7.setCellValue(rult.getMoveReal());
            cellnew7.setCellStyle(cellStyle2);
            rowdata ++;
        }
        try {
            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("国家免疫规划疫苗常规接种情况报表")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void vacTransportTemp(HttpServletResponse response){
        String username = ShiroUtils.getUserEntity().getRealName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("疫苗运输温度记录表");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        HSSFRow row2 = sheet.createRow(2);
        HSSFRow row3 = sheet.createRow(3);
        HSSFRow row4 = sheet.createRow(4);
        HSSFRow row5 = sheet.createRow(5);
        HSSFRow row6 = sheet.createRow(9);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell01 = row0.createCell(5);
        HSSFCell cell = row1.createCell(0);

        HSSFCell cell020 = row2.createCell(0);
        HSSFCell cell021 = row2.createCell(3);
        HSSFCell cell030 = row3.createCell(0);
        HSSFCell cell040 = row4.createCell(0);
        HSSFCell cell050 = row5.createCell(0);
        HSSFCell cell060 = row6.createCell(0);
        // 设置单元格内容
        cell0.setCellValue("导出时间：");
//        cell1.setCellValue("导出条件：");
        cell01.setCellValue("医生：");
        cell.setCellValue("疫苗运输温度记录表");
        cell020.setCellValue("出/入库日期");
        cell021.setCellValue("出/入库单号");
        cell030.setCellValue("疫苗运输工具");
        cell040.setCellValue("疫苗冷链方式");
        cell050.setCellValue("疫苗运输情况:");
        cell060.setCellValue("运输温度记录:");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        try{

            // 在sheet里创建第二行
            HSSFRow row22 = sheet.createRow(6);
            // 创建单元格并设置单元格内容
            row22.createCell(0).setCellValue("疫苗名称");
            row22.createCell(1).setCellValue("生产企业");
            row22.createCell(2).setCellValue("规格");
            row22.createCell(3).setCellValue("有效期");

            row22.createCell(4).setCellValue("数量（支）");
            row22.createCell(5).setCellValue("疫苗类别");


            HSSFRow row60 = sheet.createRow( 10);
            row60.createCell(0).setCellValue("项目");
            row60.createCell(1).setCellValue("日期/时间");
            row60.createCell(2).setCellValue("疫苗存储温度");
            row60.createCell(3).setCellValue("环境温度");

            row0.createCell(6).setCellValue(username);
            row0.createCell(1).setCellValue(sdf.format(new Date()));


            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("疫苗运输温度记录表")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void vacScheduleVillag(HttpServletResponse response){
        String username = ShiroUtils.getUserEntity().getRealName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("国家免疫规划疫苗计划表");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);

        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0 = row0.createCell(0);
        HSSFCell cell01 = row0.createCell(9);
        HSSFCell cell = row1.createCell(0);


        // 设置单元格内容
        cell0.setCellValue("导出时间：");
//        cell1.setCellValue("导出条件：");
        cell01.setCellValue("医生：");
        cell.setCellValue("国家免疫规划疫苗计划表");

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle3.setWrapText(true);


        try{
            //画边框线


            HSSFRow   rowvillag = sheet.createRow(2);
            rowvillag.createCell(0).setCellValue("贵州");
            rowvillag.createCell(1).setCellValue("省");
            rowvillag.createCell(2).setCellValue("安顺");
            rowvillag.createCell(3).setCellValue("市");
            rowvillag.createCell(4).setCellValue("关岭");
            rowvillag.createCell(5).setCellValue("县");
            rowvillag.createCell(6).setCellValue("龙源");
            rowvillag.createCell(7).setCellValue("乡（镇,街道）");
            rowvillag.createCell(8).setCellValue("————");
            rowvillag.createCell(9).setCellValue("村（居委会）");
            for(int i=2;i<33;i++){
                // 在sheet里创建第二行
                rowvillag = sheet.createRow(3);
                sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
                rowvillag.createCell(0).setCellValue("疫苗与剂次（人份）");
                rowvillag.createCell(2).setCellValue("上月累计入库");
                rowvillag.createCell(3).setCellValue("本月延苗数");
                rowvillag.createCell(4).setCellValue("本年累计入库");
                rowvillag.createCell(5).setCellValue("本月底库存");
                rowvillag.createCell(6).setCellValue("下月领取计划");
                rowvillag.createCell(7).setCellValue("耗损数");
                rowvillag.createCell(8).setCellValue("耗损原因");



                for(int j=0;j<10;j++){
                    HSSFCell cel = rowvillag.createCell(j);
                    HSSFCellStyle cellStyle11=wb.createCellStyle();
                    cellStyle11.setBorderBottom(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);
                    cellStyle11.setBottomBorderColor(HSSFColor.DARK_RED.index);
                    cellStyle11.setBorderTop(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);
                    cellStyle11.setTopBorderColor(HSSFColor.DARK_RED.index);
                    cellStyle11.setBorderLeft(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);
                    cellStyle11.setLeftBorderColor(HSSFColor.DARK_RED.index);
                    cellStyle11.setBorderRight(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);
                    cellStyle11.setRightBorderColor(HSSFColor.DARK_RED.index);
                    cel.setCellStyle(cellStyle11);
                    rowvillag.setRowStyle(cellStyle11);
//                    cel.setCellStyle(cellStyle3);
                }

            }




            rowvillag = sheet.createRow(4);
            sheet.addMergedRegion(new CellRangeAddress(4, 7, 0, 0));
            //            乙肝第一剂次
            rowvillag.createCell(0).setCellValue("乙肝疫苗HepB");
            rowvillag.createCell(1).setCellValue("1");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
            //            乙肝首次剂次
            rowvillag = sheet.createRow(5);
            rowvillag.createCell(1).setCellValue("首剂及时");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
            //            乙肝第二剂次
            rowvillag = sheet.createRow(6);
            rowvillag.createCell(1).setCellValue("2");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //            乙肝第三剂次
            rowvillag = sheet.createRow(7);
            rowvillag.createCell(1).setCellValue("3");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
            //卡介疫苗
            rowvillag = sheet.createRow(8);
            sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 1));
            rowvillag.createCell(0).setCellValue("卡介苗BCG");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
            //脊灰灭活
            rowvillag = sheet.createRow(9);
            rowvillag.createCell(0).setCellValue("脊灰灭活");
            rowvillag.createCell(1).setCellValue("1");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //            二价脊灰灭活 二剂次
            rowvillag = sheet.createRow(10);
            rowvillag.createCell(0).setCellValue("二价脊灰（人份）");
            sheet.addMergedRegion(new CellRangeAddress(10, 12, 0, 0));
            rowvillag.createCell(1).setCellValue("2");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //            二价脊灰灭活 三剂次
            rowvillag = sheet.createRow(11);
            rowvillag.createCell(1).setCellValue("3");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

//            二价脊灰灭活 四剂次
            rowvillag = sheet.createRow(12);
            rowvillag.createCell(1).setCellValue("4");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);



            rowvillag = sheet.createRow(13);
            sheet.addMergedRegion(new CellRangeAddress(13, 16, 0, 0));
//            百白破第一剂次
            rowvillag.createCell(0).setCellValue("百白破疫苗DPT");
            rowvillag.createCell(1).setCellValue("1");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
//           百白破二剂次
            rowvillag = sheet.createRow(14);
            rowvillag.createCell(1).setCellValue("2");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
//            百白破第三剂次
            rowvillag = sheet.createRow(15);
            rowvillag.createCell(1).setCellValue("3");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

//            百白破第四剂次
            rowvillag = sheet.createRow(16);
            rowvillag.createCell(1).setCellValue("4");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);


//白破疫苗DT(人份)
            rowvillag = sheet.createRow(17);
            sheet.addMergedRegion(new CellRangeAddress(17, 17, 0, 1));
            rowvillag.createCell(0).setCellValue("白破疫苗DT(人份)");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //麻疹疫苗M
            rowvillag = sheet.createRow(18);
            sheet.addMergedRegion(new CellRangeAddress(18, 18, 0, 1));
            rowvillag.createCell(0).setCellValue("麻疹疫苗M");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
            //麻风疫苗MR
            rowvillag = sheet.createRow(19);
            sheet.addMergedRegion(new CellRangeAddress(19, 19, 0, 1));
            rowvillag.createCell(0).setCellValue("麻风疫苗MR");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //麻腮风疫苗MMR1
            rowvillag = sheet.createRow(20);
            sheet.addMergedRegion(new CellRangeAddress(20, 20, 0, 1));
            rowvillag.createCell(0).setCellValue("麻腮风疫苗MMR1");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //麻腮
            rowvillag = sheet.createRow(21);
            sheet.addMergedRegion(new CellRangeAddress(21, 21, 0, 1));
            rowvillag.createCell(0).setCellValue("麻腮疫苗MM");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);


            rowvillag = sheet.createRow(22);
            sheet.addMergedRegion(new CellRangeAddress(22, 23, 0, 0));
//            A群流脑疫苗MenA(人份)第一剂次
            rowvillag.createCell(0).setCellValue("A群流脑疫苗MenA(人份)");
            rowvillag.createCell(1).setCellValue("1");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
//           A群流脑疫苗MenA(人份)二剂次
            rowvillag = sheet.createRow(23);
            rowvillag.createCell(1).setCellValue("2");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            rowvillag = sheet.createRow(24);
            sheet.addMergedRegion(new CellRangeAddress(24, 25, 0, 0));
//            A+C群流脑疫苗MenAC第一剂次
            rowvillag.createCell(0).setCellValue("A+C群流脑疫苗MenAC");
            rowvillag.createCell(1).setCellValue("1");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
//           A+C群流脑二剂次
            rowvillag = sheet.createRow(25);
            rowvillag.createCell(1).setCellValue("2");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);


            rowvillag = sheet.createRow(26);
            sheet.addMergedRegion(new CellRangeAddress(26, 27, 0, 0));
//           乙肝减毒灭活JE-1第一剂次
            rowvillag.createCell(0).setCellValue("乙肝减毒灭活JE-1");
            rowvillag.createCell(1).setCellValue("1");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);
//           乙肝减毒灭活JE-1二剂次
            rowvillag = sheet.createRow(27);
            rowvillag.createCell(1).setCellValue("2");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);


            //甲肝减毒活疫苗HepA-1
            rowvillag = sheet.createRow(28);
            sheet.addMergedRegion(new CellRangeAddress(28, 28, 0, 1));
            rowvillag.createCell(0).setCellValue("甲肝减毒活疫苗HepA-1");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //D注射器__ml
            rowvillag = sheet.createRow(29);
            sheet.addMergedRegion(new CellRangeAddress(29, 29, 0, 1));
            rowvillag.createCell(0).setCellValue("AD注射器__ml");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //D注射器__ml
            rowvillag = sheet.createRow(30);
            sheet.addMergedRegion(new CellRangeAddress(30, 30, 0, 1));
            rowvillag.createCell(0).setCellValue("D注射器__ml");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            //一次性注射器__0.5___ml
            rowvillag = sheet.createRow(31);
            sheet.addMergedRegion(new CellRangeAddress(31, 31, 0, 1));
            rowvillag.createCell(0).setCellValue("一次性注射器__0.5___ml");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);


            //一次性注射器__0.25___ml
            rowvillag = sheet.createRow(32);
            sheet.addMergedRegion(new CellRangeAddress(32, 32, 0, 1));
            rowvillag.createCell(0).setCellValue("一次性注射器__0.25___ml");
            rowvillag.createCell(2).setCellValue(0);
            rowvillag.createCell(3).setCellValue(0);
            rowvillag.createCell(4).setCellValue(0);
            rowvillag.createCell(5).setCellValue(0);
            rowvillag.createCell(6).setCellValue(0);
            rowvillag.createCell(7).setCellValue(0);
            rowvillag.createCell(8).setCellValue(0);

            rowvillag = sheet.createRow(33);
            sheet.addMergedRegion(new CellRangeAddress(33, 33, 0, 8));
            rowvillag.createCell(0).setCellValue("填写说明：");

            rowvillag = sheet.createRow(34);
            sheet.addMergedRegion(new CellRangeAddress(34, 34, 0, 8));
            rowvillag.createCell(0).setCellValue("乡级防保组织每月5日前汇总总上报县级疾病预防控制机构；百白破疫苗实种人数包括全细胞和无细胞百白破疫苗实种人数；疫苗单位为剂次，注射器单位为支。");
            row0.createCell(10).setCellValue(username);
            row0.createCell(1).setCellValue(sdf.format(new Date()));

            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("国家免疫规划疫苗计划表")+".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void exportChildAndInoculate(List<TChildInfoEntity> childrenList, Map<String,Object> param, HttpServletResponse response) {
        String username = ShiroUtils.getUserEntity().getRealName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //List<Map<String, Object>> lists = new ArrayList();
        // 创建HSSFWorkbook对象(excel的文档对象)
        SXSSFWorkbook wb = new SXSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        Sheet sheet = wb.createSheet("儿童基本信息及接种记录表");
        Row row0 = sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);
        row0.createCell(12).setCellValue(username);
        row0.createCell(1).setCellValue(sdf.format(new Date()));



        row0.createCell(4).setCellValue("null".equals(param.get("chilCommittee"))?"":"行政村："+childrenList.get(0).getChilCommittee());
        if(param.get("chilCommittee")==null || param.get("chilCommittee").equals("null")){
            row0.createCell(4).setCellValue(param.get("chilHere").equals("null")?"":"在册情况："+childrenList.get(0).getChilHere());
            if("null".equals(param.get("chilHere"))){
                row0.createCell(4).setCellValue(!("null".equals(param.get("chilBirthdayStart")) && "null".equals(param.get("chilBirthdayEnd")))?"出生日期："+param.get("chilBirthdayStart")+"~~"+param.get("chilBirthdayEnd"):"");
                if("null".equals(param.get("chilBirthdayStart"))){
                    row0.createCell(4).setCellValue(!("null".equals(param.get("inoculateDateStart"))&& "null".equals(param.get("inoculateDateEnd")))?"接种日期："+param.get("inoculateDateStart")+"~~"+param.get("inoculateDateEnd"):"");
                    if("null".equals(param.get("inoculateDateStart"))){
                        row0.createCell(4).setCellValue("null".equals(param.get("chilCreatedateStart"))&& "null".equals(param.get("chilCreatedateEnd"))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");
                    }else{
                        row0.createCell(5).setCellValue(!("null".equals(param.get("chilCreatedateStart"))&& "null".equals(param.get("chilCreatedateEnd")))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");
                    }
                }else{
                    row0.createCell(5).setCellValue(!("null".equals(param.get("inoculateDateStart"))&& "null".equals(param.get("inoculateDateEnd")))?"接种日期："+param.get("inoculateDateStart")+"~~"+param.get("inoculateDateEnd"):"");
                    if("null".equals(param.get("inoculateDateStart"))){
                        row0.createCell(5).setCellValue(!("null".equals(param.get("chilCreatedateStart"))&& "null".equals(param.get("chilCreatedateEnd")))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");

                    }
                    row0.createCell(6).setCellValue(!("null".equals(param.get("chilBirthdayStart")) && "null".equals(param.get("chilBirthdayEnd")))?"出生日期："+param.get("chilBirthdayStart")+"~~"+param.get("chilBirthdayEnd"):"");

                }
            }else{
                row0.createCell(5).setCellValue(!("null".equals(param.get("chilBirthdayStart")) && "null".equals(param.get("chilBirthdayEnd")))?"出生日期："+param.get("chilBirthdayStart")+"~~"+param.get("chilBirthdayEnd"):"");
                row0.createCell(6).setCellValue(!("null".equals(param.get("chilCreatedateStart"))&& "null".equals(param.get("chilCreatedateEnd")))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");
            }
        }else {
            row0.createCell(5).setCellValue(param.get("chilHere")==null?"":param.get("chilHere").equals("null")?"":"在册情况："+childrenList.get(0).getChilHere());
            row0.createCell(6).setCellValue(param.get("chilCreatedateStart")!=null&& param.get("chilCreatedateEnd")!=null && !(param.get("chilCreatedateEnd").equals("null")&&param.get("chilCreatedateStart").equals("null"))?"建档日期："+param.get("chilCreatedateStart")+"~~"+param.get("chilCreatedateEnd"):"");

        }
        row0.createCell(7).setCellValue(param.get("isUpload")==null?"":param.get("isUpload").equals("null")?"":"上传状态："+param.get("isUpload")=="false"?"":"未上传");
        row0.createCell(8).setCellValue(param.get("currentDepartChild").equals("null") || param.get("currentDepartChild").equals("undefined")?"":"儿童归属：本单位儿童");
        Cell cell0 = row0.createCell(0);
        Cell cell1 = row0.createCell(3);
        Cell cell01 = row0.createCell(11);
        Cell cell = row1.createCell(0);
        // 设置单元格内容
        cell0.setCellValue("导出时间：");
        cell1.setCellValue("导出条件：");
        cell01.setCellValue("医生：");
        cell.setCellValue("儿童基本信息及接种记录表");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));
        CellStyle cellStyle = wb.createCellStyle();
        CellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);


        CellStyle titleStyle = wb.createCellStyle();
        XSSFFont titleFont = (XSSFFont) wb.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short)10);
        titleStyle.setFont(titleFont);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        CellStyle titleValueStyle = wb.createCellStyle();
        XSSFFont valueFont = (XSSFFont) wb.createFont();

        valueFont.setFontHeightInPoints((short)10);
        titleValueStyle.setFont(valueFont);
        titleValueStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        titleValueStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        titleValueStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        titleValueStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        Iterator<Cell> cellIterator = row2.cellIterator();
        while(cellIterator.hasNext()){
            cellIterator.next().setCellStyle(titleStyle);
        }


        for(TChildInfoEntity infoEntity:childrenList){

            Row titleRow = sheet.createRow(sheet.getLastRowNum()+1);
            //姓名、性别、出生日期、出生医院、监护人姓名、联系电话、居住地址、户口地址、在册情况、接种管理单位、建档单位、建档日期、建卡人
            titleRow.createCell(0).setCellValue("姓名");
            titleRow.createCell(1).setCellValue("父亲姓名");
            titleRow.createCell(2).setCellValue("出生日期");
            titleRow.createCell(3).setCellValue("联系电话");
            titleRow.createCell(4).setCellValue("在册情况");
            titleRow.createCell(5).setCellValue("出生医院");
            titleRow.createCell(6).setCellValue("户口地址");
            titleRow.createCell(7).setCellValue("");
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 6, 7));
            Iterator<Cell> twi = titleRow.cellIterator();
            while(twi.hasNext()){
                twi.next().setCellStyle(titleStyle);
            }
            Row titleValueRow = sheet.createRow(sheet.getLastRowNum()+1);
            titleValueRow.createCell(0).setCellValue(infoEntity.getChilName());
            titleValueRow.createCell(1).setCellValue(infoEntity.getChilFather());
            titleValueRow.createCell(2).setCellValue(infoEntity.getChilBirthday()!=null?sdf.format(infoEntity.getChilBirthday()):null);
            titleValueRow.createCell(3).setCellValue(infoEntity.getChilTel());
            titleValueRow.createCell(4).setCellValue(infoEntity.getChilHere());
            titleValueRow.createCell(5).setCellValue(infoEntity.getChilBirthhospitalname());
            titleValueRow.createCell(6).setCellValue(infoEntity.getChilHabiaddress());
            titleValueRow.createCell(7).setCellValue("");
            sheet.addMergedRegion(new CellRangeAddress(titleValueRow.getRowNum(), titleValueRow.getRowNum(), 6, 7));
            Iterator<Cell> valCellIt = titleValueRow.cellIterator();
            while(valCellIt.hasNext()){
                valCellIt.next().setCellStyle(titleValueStyle);
            }

            Row titleRow2 = sheet.createRow(sheet.getLastRowNum()+1);
            titleRow2.createCell(0).setCellValue("性别");
            titleRow2.createCell(1).setCellValue("母亲姓名");
            titleRow2.createCell(2).setCellValue("建档日期");
            titleRow2.createCell(3).setCellValue("建档单位");
            titleRow2.createCell(4).setCellValue("建卡人");
            titleRow2.createCell(5).setCellValue("接种管理单位");
            titleRow2.createCell(6).setCellValue("居住地址");
            titleRow2.createCell(7).setCellValue("");
            sheet.addMergedRegion(new CellRangeAddress(titleRow2.getRowNum(), titleRow2.getRowNum(), 6, 7));
            Iterator<Cell> twi2 = titleRow2.cellIterator();
            while(twi2.hasNext()){
                twi2.next().setCellStyle(titleStyle);
            }

            Row titleValueRow2 = sheet.createRow(sheet.getLastRowNum()+1);
            //姓名、性别、出生日期、出生医院、监护人姓名、联系电话、居住地址、户口地址、在册情况、接种管理单位、建档单位、建档日期、建卡人

            titleValueRow2.createCell(0).setCellValue(infoEntity.getChilSex());
            titleValueRow2.createCell(1).setCellValue(infoEntity.getChilMother());
            titleValueRow2.createCell(2).setCellValue(infoEntity.getChilCreatedate()!=null?sdf.format(infoEntity.getChilCreatedate()):null);
            titleValueRow2.createCell(3).setCellValue(infoEntity.getChilCreatesite());
            titleValueRow2.createCell(4).setCellValue(infoEntity.getChilCreateman());
            titleValueRow2.createCell(5).setCellValue(infoEntity.getChilCurdepartment());
            titleValueRow2.createCell(6).setCellValue(infoEntity.getChilAddress());
            titleValueRow2.createCell(7).setCellValue("");
            sheet.addMergedRegion(new CellRangeAddress(titleValueRow2.getRowNum(), titleValueRow2.getRowNum(), 6, 7));
            Iterator<Cell> valCellIt2 = titleValueRow2.cellIterator();
            while(valCellIt2.hasNext()){
                valCellIt2.next().setCellStyle(titleValueStyle);
            }



            HashMap<String,Object> query = new HashMap<>();
            query.put("chilCode",infoEntity.getChilCode());
            List<TChildInoculateEntity> tChildInoculateList = tChildInoculateService.queryList(query);
            if(!tChildInoculateList.isEmpty()){
                Row inocTitleRow = sheet.createRow(sheet.getLastRowNum()+1);
                inocTitleRow.createCell(0).setCellValue("疫苗类别");
                inocTitleRow.createCell(1).setCellValue("剂次");
                inocTitleRow.createCell(2).setCellValue("接种日期");
                inocTitleRow.createCell(3).setCellValue("接种部位");
                inocTitleRow.createCell(4).setCellValue("疫苗批号");
                inocTitleRow.createCell(5).setCellValue("生产企业");
                inocTitleRow.createCell(6).setCellValue("接种单位");
                inocTitleRow.createCell(7).setCellValue("接种者");
                Iterator<Cell> itrIt = inocTitleRow.cellIterator();
                while(itrIt.hasNext()){
                    itrIt.next().setCellStyle(titleStyle);
                }
            }
            for(TChildInoculateEntity inoc :tChildInoculateList){
                Row inocRow = sheet.createRow(sheet.getLastRowNum()+1);
                //疫苗类别、剂次、接种日期、接种部位、疫苗批号、生产企业、接种点位、接种者、等
                inocRow.createCell(0).setCellValue(inoc.getInocBactCode());
                inocRow.createCell(1).setCellValue(inoc.getInocTime());
                inocRow.createCell(2).setCellValue(inoc.getInocDate()!=null?sdf.format(inoc.getInocDate()):null);
                inocRow.createCell(3).setCellValue(inoc.getInocInplId());
                inocRow.createCell(4).setCellValue(inoc.getInocBatchno());
                inocRow.createCell(5).setCellValue(inoc.getInocCorpCode());
                inocRow.createCell(6).setCellValue(inoc.getInocDepaCode());
                inocRow.createCell(7).setCellValue(inoc.getInocNurse());
                Iterator<Cell> inocCells = inocRow.cellIterator();
                while(inocCells.hasNext()){
                    inocCells.next().setCellStyle(titleValueStyle);
                }

            }
            sheet.createRow(sheet.getLastRowNum()+1);
        }


        OutputStream output = null;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="+toUtf8String("儿童基本信息及接种记录")+".xlsx");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
