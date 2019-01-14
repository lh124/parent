package io.yfjz.service.report.impl;


import io.yfjz.dao.statistics.ChildrenOfAllAgeGroupsDao;
import io.yfjz.service.report.ChildrenOfAllAgeGroupsService;
import io.yfjz.utils.Constant;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO  各年龄组儿童构成情况统计表（免疫规划来源）
 * @Date 14:43 2018/12/19
 */
@Service("childrenOfAllAgeGroupsService")
public class ChildrenOfAllAgeGroupsServiceImpl implements ChildrenOfAllAgeGroupsService {

    @Resource
    private ChildrenOfAllAgeGroupsDao childrenOfAllAgeGroupsDao;

    @Override
    public List<Map<String, Object>> queryChildrenOfAllAgeGroups(List committee, Date data) {
       /* List  resident = new ArrayList(){{this.add("1");this.add("6");this.add("7");}};
        List  influx = new ArrayList(){{this.add("10");this.add("9");}};
        List  outflow = new ArrayList(){{this.add("2");this.add("3");this.add("8");}};*/

        String[] resident = {"1", "6", "7"};
        String[] influx = {"10", "9"};
        String[] outflow = {"2", "3", "8"};
        Map<String, Object> mapValue = new HashMap<String, Object>() {{
            this.put("here", resident);
            this.put("committee", committee);
            this.put("data", data);
        }};

        //查询常住0-6岁儿童
        List<Map<String, Object>> residentList = childrenOfAllAgeGroupsDao.queryChildrenOfAllAgeGroups(mapValue);
        mapValue.put("here", influx);
        //查询流入0-6岁儿童
        List<Map<String, Object>> influxList = childrenOfAllAgeGroupsDao.queryChildrenOfAllAgeGroups(mapValue);
        mapValue.put("here", outflow);
        //查询流出0-6岁儿童
        List<Map<String, Object>> outflowList = childrenOfAllAgeGroupsDao.queryChildrenOfAllAgeGroups(mapValue);

        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> residentMap : residentList) {

            if (result.size() > 0) { //有数据时遍历数据是否是相同行政村不同年龄
                Boolean is = true;
                for (Map<String, Object> map : result) {
                    String committeeCode = String.valueOf(map.get("committeeCode"));
                    if (!committeeCode.equals(residentMap.get("chil_committee").toString())) {
                        continue;
                    } else {
                        is = false;
                        Map<String, Object> ageMap = new HashMap<>();
                        ageMap.put("常住", residentMap.get("quantity"));
                        ageMap.put("流入", 0);
                        ageMap.put("流出", 0);
                        ageMap.put("age", residentMap.get("age"));
                        ageMap.put("chil_here", residentMap.get("chil_here"));
                        map.put("age" + residentMap.get("age"), ageMap);
                    }
                }
                //添加不是相同行政村的数据
                if (is) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("committeeCode", residentMap.get("chil_committee"));
                    map.put("committeeName", residentMap.get("name"));

                    Map<String, Object> ageMap = new HashMap<>();
                    ageMap.put("常住", residentMap.get("quantity"));
                    ageMap.put("流入", 0);
                    ageMap.put("流出", 0);
                    ageMap.put("age", residentMap.get("age"));
                    ageMap.put("chil_here", residentMap.get("chil_here"));
                    map.put("age" + residentMap.get("age"), ageMap);
                    result.add(map);
                }
            } else { //没有数据时添加数据
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("committeeCode", residentMap.get("chil_committee"));
                map.put("committeeName", residentMap.get("name"));

                Map<String, Object> ageMap = new HashMap<>();
                ageMap.put("常住", residentMap.get("quantity"));
                ageMap.put("流入", 0);
                ageMap.put("流出", 0);
                ageMap.put("age", residentMap.get("age"));
                ageMap.put("chil_here", residentMap.get("chil_here"));
                map.put("age" + residentMap.get("age"), ageMap);
                result.add(map);
            }
        }

        for (Map<String, Object> resultMap : result) {
            for (Map<String, Object> influxMap : influxList) {
                String committeeCode = String.valueOf(resultMap.get("committeeCode"));
                if (!committeeCode.equals(influxMap.get("chil_committee").toString())) {
                    continue;
                }
                //判断当前行政村是否有相同年龄
                if (resultMap.containsKey("age" + influxMap.get("age"))) {
                    Map<String, Object> map1 = (Map<String, Object>) resultMap.get("age" + influxMap.get("age"));
                    map1.put("流入", influxMap.get("quantity"));
                } else {
                    Map<String, Object> ageMap = new HashMap<>();
                    ageMap.put("常住", 0);
                    ageMap.put("流入", influxMap.get("quantity"));
                    ageMap.put("流出", 0);
                    ageMap.put("age", influxMap.get("age"));
                    ageMap.put("chil_here", influxMap.get("chil_here"));
                    resultMap.put("age" + influxMap.get("age"), ageMap);
                }

            }
        }

        for (Map<String, Object> resultMap : result) {
            for (Map<String, Object> outflowMap : outflowList) {
                String committeeCode = String.valueOf(resultMap.get("committeeCode"));
                if (!committeeCode.equals(outflowMap.get("chil_committee").toString())) {
                    continue;
                }
                //判断当前行政村是否有相同年龄
                if (resultMap.containsKey("age" + outflowMap.get("age"))) {
                    Map<String, Object> map1 = (Map<String, Object>) resultMap.get("age" + outflowMap.get("age"));
                    map1.put("流出", outflowMap.get("quantity"));
                } else {
                    Map<String, Object> ageMap = new HashMap<>();
                    ageMap.put("常住", 0);
                    ageMap.put("流入", 0);
                    ageMap.put("流出", outflowMap.get("quantity"));
                    ageMap.put("age", outflowMap.get("age"));
                    ageMap.put("chil_here", outflowMap.get("chil_here"));
                    resultMap.put("age" + outflowMap.get("age"), ageMap);
                }
            }
        }

        Map<String, Object> totalMap = new HashMap<>();
        totalMap.put("committeeName", "合计");
        totalMap.put("total", 0);
        //统计 行数据和列数据
        for (Map<String, Object> resultMap : result) {
            Integer total = 0;
            for (int i = 0; i <= 6; i++) {
                //判断是否有这个年龄的数据，有才统计，没有就不统计了
                if (resultMap.containsKey("age" + i)) {
                    //行统计
                    Map<String, Object> map = (Map<String, Object>) resultMap.get("age" + i);
                    total = total + Integer.valueOf(map.get("常住") + "") +
                            Integer.valueOf(map.get("流入") + "") + Integer.valueOf(map.get("流出") + "");

                    //列统计
                    if (totalMap.containsKey("age" + i)) {
                        Map<String, Object> mapCol = (Map<String, Object>) totalMap.get("age" + i);
                        mapCol.put("常住", Integer.valueOf(mapCol.get("常住") + "") + Integer.valueOf(map.get("常住") + ""));
                        mapCol.put("流入", Integer.valueOf(mapCol.get("流入") + "") + Integer.valueOf(map.get("流入") + ""));
                        mapCol.put("流出", Integer.valueOf(mapCol.get("流出") + "") + Integer.valueOf(map.get("流出") + ""));
                        //totalMap.put("age"+i,mapCol);
                    } else {
                        Map<String, Object> mapCol = new HashMap<>();
                        mapCol.put("常住", map.get("常住"));
                        mapCol.put("流入", map.get("流入"));
                        mapCol.put("流出", map.get("流出"));
                        totalMap.put("age" + i, mapCol);
                    }
                }
            }
            resultMap.put("total", total);
            totalMap.put("total", total + Integer.valueOf(totalMap.get("total") + ""));
        }

        result.add(totalMap);
        return result;
    }

    @Override
    public void exportChildrenOfAllAgeGroups(String path, HttpServletResponse response, List<String> committee, String[] address) {
        //数据
        List<Map<String, Object>> list = queryChildrenOfAllAgeGroups(committee, null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("各年龄组儿童构成情况统计表（免疫规划来源）");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个  总23列
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("各年龄组儿童构成情况统计表（免疫规划来源）");

        HSSFRow row1 = sheet.createRow(1);
        HSSFCell cell10 = row1.createCell(0);

        String data = sdf.format(new Date());

        String addressStr1 = address[1].replace("市", "").replace("州", "").replace("地", "");
        String addressStr2 = address[2].replace("县", "").replace("市", "").replace("区", "");
        String addressStr3 = address[3].replace("乡", "").replace("镇", "").replace("街道办", "");

        HSSFRichTextString str = new HSSFRichTextString("贵州省  " + addressStr1 + "   市（州、地）  " + addressStr2 + "   县（市、区）  " + addressStr3 + "   乡（镇、街道办）  " + data + "   年");
        int address1 = addressStr1.length();
        int address2 = addressStr2.length();
        int address3 = addressStr3.length();

        Font fontxhx = wb.createFont();
        fontxhx.setUnderline(Font.U_SINGLE); //下划线
        str.applyFont(3, 7 + address1, fontxhx);
        str.applyFont(14 + address1, 18 + address1 + address2, fontxhx);
        str.applyFont(25 + address1 + address2, 29 + address1 + address2 + address3, fontxhx);
        str.applyFont(38 + address1 + address2 + address3, 42 + address1 + address2 + address3 + data.length(), fontxhx);


        cell10.setCellValue(str);

        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 22));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 22));

        //设置报表头
        sheet.addMergedRegion(new CellRangeAddress(2, 4, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 22));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 9));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 10, 12));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 13, 15));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 16, 18));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 19, 21));
        sheet.addMergedRegion(new CellRangeAddress(38, 38, 0, 22));

        //设置单元格样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle.setWrapText(true);
        cell0.setCellStyle(cellStyle);
        cell10.setCellStyle(cellStyle);

        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle3.setWrapText(true);
        cellStyle3.setFont(font);
        //设置样式结束

        //画边框线
        for (int i = 2; i < 5 + list.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            row.setHeightInPoints(26);
            for (int j = 0; j < 23; j++) {
                HSSFCell cel = row.createCell(j);
                cel.setCellStyle(cellStyle3);
            }
        }

        HSSFRow row2 = sheet.createRow(2);
        HSSFCell cell20 = row2.createCell(0);
        cell20.setCellValue("单位");
        cell20.setCellStyle(cellStyle3);

        HSSFCell cell21 = row2.createCell(1);
        cell21.setCellValue("年龄（岁）");
        cell21.setCellStyle(cellStyle3);
        row2.setRowStyle(cellStyle3);

        HSSFRow row3 = sheet.createRow(3);
        HSSFCell cell31 = row3.createCell(1);
        cell31.setCellValue("0");
        cell31.setCellStyle(cellStyle3);
        HSSFCell cell32 = row3.createCell(4);
        cell32.setCellValue("1");
        cell32.setCellStyle(cellStyle3);
        HSSFCell cell33 = row3.createCell(7);
        cell33.setCellValue("2");
        cell33.setCellStyle(cellStyle3);
        HSSFCell cell34 = row3.createCell(10);
        cell34.setCellValue("3");
        cell34.setCellStyle(cellStyle3);
        HSSFCell cell35 = row3.createCell(13);
        cell35.setCellValue("4");
        cell35.setCellStyle(cellStyle3);
        HSSFCell cell36 = row3.createCell(16);
        cell36.setCellValue("5");
        cell36.setCellStyle(cellStyle3);
        HSSFCell cell37 = row3.createCell(19);
        cell37.setCellValue("6");
        cell37.setCellStyle(cellStyle3);
        HSSFCell cell322 = row3.createCell(22);
        cell322.setCellValue("合计");
        cell322.setCellStyle(cellStyle3);

        HSSFRow row4 = sheet.createRow(4);

        //设置表头
        HSSFCell cell41 = row4.createCell(1);
        cell41.setCellValue("常住");
        cell41.setCellStyle(cellStyle3);
        HSSFCell cell42 = row4.createCell(2);
        cell42.setCellValue("流出");
        cell42.setCellStyle(cellStyle3);
        HSSFCell cell43 = row4.createCell(3);
        cell43.setCellValue("流入");
        cell43.setCellStyle(cellStyle3);
        HSSFCell cell44 = row4.createCell(4);
        cell44.setCellValue("常住");
        cell44.setCellStyle(cellStyle3);
        HSSFCell cell45 = row4.createCell(5);
        cell45.setCellValue("流出");
        cell45.setCellStyle(cellStyle3);
        HSSFCell cell46 = row4.createCell(6);
        cell46.setCellValue("流入");
        cell46.setCellStyle(cellStyle3);
        HSSFCell cell47 = row4.createCell(7);
        cell47.setCellValue("常住");
        cell47.setCellStyle(cellStyle3);
        HSSFCell cell48 = row4.createCell(8);
        cell48.setCellValue("流出");
        cell48.setCellStyle(cellStyle3);
        HSSFCell cell49 = row4.createCell(9);
        cell49.setCellValue("流入");
        cell49.setCellStyle(cellStyle3);
        HSSFCell cell410 = row4.createCell(10);
        cell410.setCellValue("常住");
        cell410.setCellStyle(cellStyle3);
        HSSFCell cell411 = row4.createCell(11);
        cell411.setCellValue("流出");
        cell411.setCellStyle(cellStyle3);
        HSSFCell cell412 = row4.createCell(12);
        cell412.setCellValue("流入");
        cell412.setCellStyle(cellStyle3);
        HSSFCell cell413 = row4.createCell(13);
        cell413.setCellValue("常住");
        cell413.setCellStyle(cellStyle3);
        HSSFCell cell414 = row4.createCell(14);
        cell414.setCellValue("流出");
        cell414.setCellStyle(cellStyle3);
        HSSFCell cell415 = row4.createCell(15);
        cell415.setCellValue("流入");
        cell415.setCellStyle(cellStyle3);
        HSSFCell cell416 = row4.createCell(16);
        cell416.setCellValue("常住");
        cell416.setCellStyle(cellStyle3);
        HSSFCell cell417 = row4.createCell(17);
        cell417.setCellValue("流出");
        cell417.setCellStyle(cellStyle3);
        HSSFCell cell418 = row4.createCell(18);
        cell418.setCellValue("流入");
        cell418.setCellStyle(cellStyle3);
        HSSFCell cell419 = row4.createCell(19);
        cell419.setCellValue("常住");
        cell419.setCellStyle(cellStyle3);
        HSSFCell cell420 = row4.createCell(20);
        cell420.setCellValue("流出");
        cell420.setCellStyle(cellStyle3);
        HSSFCell cell421 = row4.createCell(21);
        cell421.setCellValue("流入");
        cell421.setCellStyle(cellStyle3);
        HSSFCell cell422 = row4.createCell(22);
        cell422.setCellStyle(cellStyle3);

        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

        try {
            //填充数据
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                HSSFRow rowCol = sheet.createRow(i + 5);
                HSSFCell cellCel = rowCol.createCell(0);
                cellCel.setCellValue(map.get("committeeName") + "");
                cellCel.setCellStyle(cellStyle3);

                if (map.containsKey("age0")) {
                    Map<String, Object> age0 = (Map<String, Object>) map.get("age0");
                    HSSFCell cellCel1 = rowCol.createCell(1);
                    cellCel1.setCellValue(age0.get("常住") + "");
                    cellCel1.setCellStyle(cellStyle3);
                    HSSFCell cellCel2 = rowCol.createCell(2);
                    cellCel2.setCellValue(age0.get("流出") + "");
                    cellCel2.setCellStyle(cellStyle3);
                    HSSFCell cellCel3 = rowCol.createCell(3);
                    cellCel3.setCellValue(age0.get("流入") + "");
                    cellCel3.setCellStyle(cellStyle3);
                } else {
                    HSSFCell cellCel1 = rowCol.createCell(1);
                    cellCel1.setCellValue(0);
                    cellCel1.setCellStyle(cellStyle3);
                    HSSFCell cellCel2 = rowCol.createCell(2);
                    cellCel2.setCellValue(0);
                    cellCel2.setCellStyle(cellStyle3);
                    HSSFCell cellCel3 = rowCol.createCell(3);
                    cellCel3.setCellValue(0);
                    cellCel3.setCellStyle(cellStyle3);
                }

                if (map.containsKey("age1")) {
                    Map<String, Object> age1 = (Map<String, Object>) map.get("age1");
                    HSSFCell cellCel4 = rowCol.createCell(4);
                    cellCel4.setCellValue(age1.get("常住") + "");
                    cellCel4.setCellStyle(cellStyle3);
                    HSSFCell cellCel5 = rowCol.createCell(5);
                    cellCel5.setCellValue(age1.get("流出") + "");
                    cellCel5.setCellStyle(cellStyle3);
                    HSSFCell cellCel6 = rowCol.createCell(6);
                    cellCel6.setCellValue(age1.get("流入") + "");
                    cellCel6.setCellStyle(cellStyle3);
                } else {
                    HSSFCell cellCel4 = rowCol.createCell(4);
                    cellCel4.setCellValue(0);
                    cellCel4.setCellStyle(cellStyle3);
                    HSSFCell cellCel5 = rowCol.createCell(5);
                    cellCel5.setCellValue(0);
                    cellCel5.setCellStyle(cellStyle3);
                    HSSFCell cellCel6 = rowCol.createCell(6);
                    cellCel6.setCellValue(0);
                    cellCel6.setCellStyle(cellStyle3);
                }

                if (map.containsKey("age2")) {
                    Map<String, Object> age2 = (Map<String, Object>) map.get("age2");
                    HSSFCell cellCel7 = rowCol.createCell(7);
                    cellCel7.setCellValue(age2.get("常住") + "");
                    cellCel7.setCellStyle(cellStyle3);
                    HSSFCell cellCel8 = rowCol.createCell(8);
                    cellCel8.setCellValue(age2.get("流出") + "");
                    cellCel8.setCellStyle(cellStyle3);
                    HSSFCell cellCel9 = rowCol.createCell(9);
                    cellCel9.setCellValue(age2.get("流入") + "");
                    cellCel9.setCellStyle(cellStyle3);
                } else {
                    HSSFCell cellCel7 = rowCol.createCell(7);
                    cellCel7.setCellValue(0);
                    cellCel7.setCellStyle(cellStyle3);
                    HSSFCell cellCel8 = rowCol.createCell(8);
                    cellCel8.setCellValue(0);
                    cellCel8.setCellStyle(cellStyle3);
                    HSSFCell cellCel9 = rowCol.createCell(9);
                    cellCel9.setCellValue(0);
                    cellCel9.setCellStyle(cellStyle3);
                }

                if (map.containsKey("age3")) {
                    Map<String, Object> age3 = (Map<String, Object>) map.get("age3");
                    HSSFCell cellCel10 = rowCol.createCell(10);
                    cellCel10.setCellValue(age3.get("常住") + "");
                    cellCel10.setCellStyle(cellStyle3);
                    HSSFCell cellCel11 = rowCol.createCell(11);
                    cellCel11.setCellValue(age3.get("流出") + "");
                    cellCel11.setCellStyle(cellStyle3);
                    HSSFCell cellCel12 = rowCol.createCell(12);
                    cellCel12.setCellValue(age3.get("流入") + "");
                    cellCel12.setCellStyle(cellStyle3);
                } else {
                    HSSFCell cellCel10 = rowCol.createCell(10);
                    cellCel10.setCellValue(0);
                    cellCel10.setCellStyle(cellStyle3);
                    HSSFCell cellCel11 = rowCol.createCell(11);
                    cellCel11.setCellValue(0);
                    cellCel11.setCellStyle(cellStyle3);
                    HSSFCell cellCel12 = rowCol.createCell(12);
                    cellCel12.setCellValue(0);
                    cellCel12.setCellStyle(cellStyle3);
                }

                if (map.containsKey("age4")) {
                    Map<String, Object> age4 = (Map<String, Object>) map.get("age4");
                    HSSFCell cellCel13 = rowCol.createCell(13);
                    cellCel13.setCellValue(age4.get("常住") + "");
                    cellCel13.setCellStyle(cellStyle3);
                    HSSFCell cellCel14 = rowCol.createCell(14);
                    cellCel14.setCellValue(age4.get("流出") + "");
                    cellCel14.setCellStyle(cellStyle3);
                    HSSFCell cellCel15 = rowCol.createCell(15);
                    cellCel15.setCellValue(age4.get("流入") + "");
                    cellCel15.setCellStyle(cellStyle3);
                } else {
                    HSSFCell cellCel13 = rowCol.createCell(13);
                    cellCel13.setCellValue(0);
                    cellCel13.setCellStyle(cellStyle3);
                    HSSFCell cellCel14 = rowCol.createCell(14);
                    cellCel14.setCellValue(0);
                    cellCel14.setCellStyle(cellStyle3);
                    HSSFCell cellCel15 = rowCol.createCell(15);
                    cellCel15.setCellValue(0);
                    cellCel15.setCellStyle(cellStyle3);
                }

                if (map.containsKey("age5")) {
                    Map<String, Object> age5 = (Map<String, Object>) map.get("age5");
                    HSSFCell cellCel16 = rowCol.createCell(16);
                    cellCel16.setCellValue(age5.get("常住") + "");
                    cellCel16.setCellStyle(cellStyle3);
                    HSSFCell cellCel17 = rowCol.createCell(17);
                    cellCel17.setCellValue(age5.get("流出") + "");
                    cellCel17.setCellStyle(cellStyle3);
                    HSSFCell cellCel18 = rowCol.createCell(18);
                    cellCel18.setCellValue(age5.get("流入") + "");
                    cellCel18.setCellStyle(cellStyle3);
                } else {
                    HSSFCell cellCel16 = rowCol.createCell(16);
                    cellCel16.setCellValue(0);
                    cellCel16.setCellStyle(cellStyle3);
                    HSSFCell cellCel17 = rowCol.createCell(17);
                    cellCel17.setCellValue(0);
                    cellCel17.setCellStyle(cellStyle3);
                    HSSFCell cellCel18 = rowCol.createCell(18);
                    cellCel18.setCellValue(0);
                    cellCel18.setCellStyle(cellStyle3);
                }

                if (map.containsKey("age6")) {
                    Map<String, Object> age6 = (Map<String, Object>) map.get("age6");
                    HSSFCell cellCel19 = rowCol.createCell(19);
                    cellCel19.setCellValue(age6.get("常住") + "");
                    cellCel19.setCellStyle(cellStyle3);
                    HSSFCell cellCel20 = rowCol.createCell(20);
                    cellCel20.setCellValue(age6.get("流出") + "");
                    cellCel20.setCellStyle(cellStyle3);
                    HSSFCell cellCel21 = rowCol.createCell(21);
                    cellCel21.setCellValue(age6.get("流入") + "");
                    cellCel21.setCellStyle(cellStyle3);
                } else {
                    HSSFCell cellCel19 = rowCol.createCell(19);
                    cellCel19.setCellValue(0);
                    cellCel19.setCellStyle(cellStyle3);
                    HSSFCell cellCel20 = rowCol.createCell(20);
                    cellCel20.setCellValue(0);
                    cellCel20.setCellStyle(cellStyle3);
                    HSSFCell cellCel21 = rowCol.createCell(21);
                    cellCel21.setCellValue(0);
                    cellCel21.setCellStyle(cellStyle3);
                }


                HSSFCell cellCel22 = rowCol.createCell(22);
                cellCel22.setCellValue(map.get("total") + "");
                cellCel22.setCellStyle(cellStyle3);
            }

            HSSFRow rowCol = sheet.createRow(38);
            HSSFCell cellCel = rowCol.createCell(0);
            cellCel.setCellValue("      流出：指户籍在本辖区，外出时间大于3个月的儿童。流入：指户籍在外地，在本辖区居住时间大于3个月的儿童");
            cellCel.setCellStyle(cellStyle3);


            //设置横向打印
            sheet.getPrintSetup().setLandscape(true);
            //设置上下左右页边距
            sheet.setMargin(HSSFSheet.TopMargin, 0.5);
            sheet.setMargin(HSSFSheet.BottomMargin, 0.5);
            sheet.setMargin(HSSFSheet.LeftMargin, 0.4);
            sheet.setMargin(HSSFSheet.RightMargin, 0.4);

            sheet.setDefaultRowHeightInPoints(26);


            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + toUtf8String("各年龄组儿童构成情况统计表（免疫规划来源）") + ".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //中文导出名使用
    public String toUtf8String(String title) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
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

    @Override
    public List<Map<String, Object>> queryJiankaChilBirthHospitalDistributionTable(Date startDate, Date endDate) {

        //根据建卡时间范围查询出生医院和月龄儿童数量

        List<Map<String, Object>> list = childrenOfAllAgeGroupsDao.queryCountJianKaChilQuantity(startDate, endDate);

        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> residentMap : list) {

            Boolean is = true;
            for (Map<String, Object> map : result) {
                //判断是否有相同的医院
                if (residentMap.containsValue(map.get("chil_birthhospital"))) {
                    is = false;
                    monthSum(map, residentMap.get("months"), residentMap.get("quantity"));
                }
            }
            //添加不是相同行政村的数据
            if (is) {
                Map<String, Object> month = new LinkedHashMap<>();
                month.put("chil_birthhospital", residentMap.get("chil_birthhospital"));
                month.put("hospital_name", residentMap.get("hospital_name"));
                monthMap(month);
                monthSum(month, residentMap.get("months"), residentMap.get("quantity"));
                result.add(month);
            }
        }


        Map<String, Object> totalMap = new HashMap<>();
        totalMap.put("hospital_name", "合计");
        totalMap.put("chil_birthhospital", "合计");
        monthMap(totalMap);

        for (Map<String, Object> map : result) {
            Integer month0 = Integer.valueOf(map.get("month0") + "");
            Integer month1 = Integer.valueOf(map.get("month1") + "");
            Integer month2 = Integer.valueOf(map.get("month2") + "");
            Integer month3 = Integer.valueOf(map.get("month3") + "");
            Integer month4_6 = Integer.valueOf(map.get("month4_6") + "");
            Integer month7_12 = Integer.valueOf(map.get("month7_12") + "");
            Integer month12_ = Integer.valueOf(map.get("month12_") + "");
            //计算总人数
            Integer monthTotal = month0 + month1 + month2 + month3 + month4_6 + month7_12 + month12_;
            map.put("monthTotal", monthTotal);
            String chil_birthhospital = map.get("chil_birthhospital") + "";

            //根据建卡时间查询统计 卡介苗和乙肝疫苗 第一剂接种数
            Integer numberOfFirstDose = childrenOfAllAgeGroupsDao.queryCountJianKanChilTheFirstDose(chil_birthhospital, startDate, endDate);
            //根据建卡时间查询统计 卡介苗和乙肝疫苗 及时接种数
            Integer timelyInoculationNumber = childrenOfAllAgeGroupsDao.queryCountJianKanChilTimelyVaccination(chil_birthhospital, startDate, endDate);
            Double total = Double.valueOf(monthTotal);
            map.put("numberOfFirstDose", numberOfFirstDose);//第一剂接种数

            Double rates = numberOfFirstDose / total * 100;
            if (rates.isNaN()) {
                rates = 0.00D;
            }
            map.put("vaccinationRates1", String.format("%.2f", rates));//第一剂接种数	接种率
            map.put("timelyInoculationNumber", timelyInoculationNumber);//及时接种数

            Double rates2 = (Double.valueOf(timelyInoculationNumber) / Double.valueOf(numberOfFirstDose)) * 100;
            if (rates2.isNaN()) {
                rates2 = 0.00D;
            }
            map.put("vaccinationRates2", String.format("%.2f", rates2));//及时接种数 接种率

            //合计数据
            totalMap.put("month0", Integer.valueOf(totalMap.get("month0") + "") + month0);//0月龄
            totalMap.put("month1", Integer.valueOf(totalMap.get("month1") + "") + month1);//1月龄
            totalMap.put("month2", Integer.valueOf(totalMap.get("month2") + "") + month2);//2月龄
            totalMap.put("month3", Integer.valueOf(totalMap.get("month3") + "") + month3);//3月龄
            totalMap.put("month4_6", Integer.valueOf(totalMap.get("month4_6") + "") + month4_6);//4-6月龄
            totalMap.put("month7_12", Integer.valueOf(totalMap.get("month7_12") + "") + month7_12);//7-12月龄
            totalMap.put("month12_", Integer.valueOf(totalMap.get("month12_") + "") + month12_);//12月龄以上
            totalMap.put("monthTotal", Integer.valueOf(totalMap.get("monthTotal") + "") + monthTotal);//合计
            totalMap.put("numberOfFirstDose", Integer.valueOf(totalMap.get("numberOfFirstDose") + "") + numberOfFirstDose);//第一剂接种数
            totalMap.put("timelyInoculationNumber", Integer.valueOf(totalMap.get("timelyInoculationNumber") + "") + timelyInoculationNumber);//及时接种数
        }

        //计算合计数据的百分比
        Double total = Double.valueOf(totalMap.get("monthTotal") + "");
        Double numberOfFirstDose = Double.valueOf(totalMap.get("numberOfFirstDose") + "");
        Double timelyInoculationNumber = Double.valueOf(totalMap.get("timelyInoculationNumber") + "");

        Double rates = numberOfFirstDose / total * 100;
        if (rates.isNaN()) {
            rates = 0.00D;
        }
        Double rates2 = (Double.valueOf(timelyInoculationNumber) / Double.valueOf(numberOfFirstDose)) * 100;
        if (rates2.isNaN()) {
            rates2 = 0.00D;
        }
        totalMap.put("vaccinationRates1", String.format("%.2f", rates));//第一剂接种数	接种率
        totalMap.put("vaccinationRates2", String.format("%.2f", rates2));//及时接种数 接种率
        result.add(totalMap);
        return result;
    }

    /**
     * 初始医院月龄数据
     *
     * @param map
     */
    void monthMap(Map<String, Object> map) {
        map.put("month0", 0);//0月龄
        map.put("month1", 0);//1月龄
        map.put("month2", 0);//2月龄
        map.put("month3", 0);//3月龄
        map.put("month4_6", 0);//4-6月龄
        map.put("month7_12", 0);//7-12月龄
        map.put("month12_", 0);//12月龄以上
        map.put("monthTotal", 0);//合计
        map.put("numberOfFirstDose", 0);//第一剂接种数
        map.put("vaccinationRates1", 0);//第一剂接种数	接种率
        map.put("timelyInoculationNumber", 0);//及时接种数
        map.put("vaccinationRates2", 0);//及时接种数 接种率
    }

    /**
     * 月龄的赋值和相加
     *
     * @param map
     * @param month
     * @param quantity
     */
    void monthSum(Map<String, Object> map, Object month, Object quantity) {

        Integer cont = Integer.valueOf(month + "");
        Integer quan = Integer.valueOf(quantity + "");

        if (cont == 0) {
            map.put("month0", quan);
        } else if (cont == 1) {
            map.put("month1", quan);
        } else if (cont == 2) {
            map.put("month2", quan);
        } else if (cont == 3) {
            map.put("month3", quan);
        } else if (cont >= 4 && cont <= 6) {
            Integer month4_6 = Integer.valueOf(map.get("month4_6") + "");
            map.put("month4_6", quan + month4_6);
        } else if (cont >= 7 && cont <= 12) {
            Integer month7_12 = Integer.valueOf(map.get("month7_12") + "");
            map.put("month7_12", quan + month7_12);
        } else if (cont > 12) {
            Integer month12_ = Integer.valueOf(map.get("month12_") + "");
            map.put("month12_", quan + month12_);
        }


    }

    @Override
    public void exportJiankaChilBirthHospital(String path, HttpServletResponse response, Date startDate, Date end) {

        //数据
        List<Map<String, Object>> list = queryJiankaChilBirthHospitalDistributionTable(startDate, end);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("建卡儿童出生医院分布统计表");
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row0 = sheet.createRow(0);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个  总12列
        HSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("建卡儿童出生医院分布统计表");

        HSSFRow row1 = sheet.createRow(1);
        HSSFCell cell10 = row1.createCell(0);

        String orgName = Constant.GLOBAL_ORG_NAME;
        HSSFRichTextString str = new HSSFRichTextString("单位：" + orgName + "      统计时间：" + sdf.format(startDate) + " ~ " + sdf.format(end));

        HSSFFont fontx = wb.createFont();
        fontx.setTypeOffset(FontFormatting.SS_SUPER);//设置上标下标
        str.applyFont(25 + orgName.length(), 26 + orgName.length(), fontx);
        cell10.setCellValue(str);

        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));

        //设置单元格样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);//设置字体大小
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle.setWrapText(true);

        HSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle3.setWrapText(true);
        cellStyle3.setFont(font);

        cell0.setCellStyle(cellStyle);

        String[] table = {"出生医院", "0月龄", "1月龄", "2月龄", "3月龄", "4-6月龄", "7-12月龄", "12月龄以上",
                "合计", "第一剂接种数", "接种率", "及时接种数", "接种率"};

        HSSFRow row2 = sheet.createRow(2);
        for (int i = 0; i < table.length; i++) {
            HSSFCell cell2 = row2.createCell(i);
            cell2.setCellValue(table[i]);
            cell2.setCellStyle(cellStyle3);
        }

        try {

            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                HSSFRow row = sheet.createRow(3 + i);
                for (String key : map.keySet()) {
                    String value = map.get(key) + "";
                    System.out.println("key: " + key + " value: " + value);

                    switch (key) {
                        case "hospital_name":
                            createCell(row, 0, value == null||"null".equals(value) ? "":value, cellStyle3);
                            break;
                        case "month0":
                            createCell(row, 1, value, cellStyle3);
                            break;
                        case "month1":
                            createCell(row, 2, value, cellStyle3);
                            break;
                        case "month2":
                            createCell(row, 3, value, cellStyle3);
                            break;
                        case "month3":
                            createCell(row, 4, value, cellStyle3);
                            break;
                        case "month4_6":
                            createCell(row, 5, value, cellStyle3);
                            break;
                        case "month7_12":
                            createCell(row, 6, value, cellStyle3);
                            break;
                        case "month12_":
                            createCell(row, 7, value, cellStyle3);
                            break;
                        case "monthTotal":
                            createCell(row, 8, value, cellStyle3);
                            break;
                        case "numberOfFirstDose":
                            createCell(row, 9, value, cellStyle3);
                            break;
                        case "vaccinationRates1":
                            createCell(row, 10, value, cellStyle3);
                            break;
                        case "timelyInoculationNumber":
                            createCell(row, 11, value, cellStyle3);
                            break;
                        case "vaccinationRates2":
                            createCell(row, 12, value, cellStyle3);
                            break;
                        default:
                            break;
                    }
                }

            }

            //设置横向打印
            sheet.getPrintSetup().setLandscape(true);
            //设置上下左右页边距
            sheet.setMargin(HSSFSheet.TopMargin, 0.5);
            sheet.setMargin(HSSFSheet.BottomMargin, 0.5);
            sheet.setMargin(HSSFSheet.LeftMargin, 0.4);
            sheet.setMargin(HSSFSheet.RightMargin, 0.4);

            //sheet.setDefaultRowHeightInPoints(26);

            // 必须在单元格设值以后进行
            // 设置为根据内容自动调整列宽
            for (int k = 0; k < 13; k++) {
                sheet.autoSizeColumn(k);
            }
            // 处理中文不能自动调整列宽的问题
            this.setSizeColumn(sheet, 13);
            //setSizeColumn(sheet,13);

           // sheet.setColumnWidth(0,256*35+184);

            //HSSFSheet.setColumnWidth(int columnIndex, int width);

            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            //FileOutputStream output =new FileOutputStream("D:\\服务记录表.xls");
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + toUtf8String("建卡儿童出生医院分布统计表") + ".xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给单元格赋值
     *
     * @param row
     * @param col
     * @param value
     * @param cellStyle
     */
    void createCell(HSSFRow row, int col, Object value, HSSFCellStyle cellStyle) {
        HSSFCell cell2 = row.createCell(col);
        cell2.setCellValue(value + "");
        cell2.setCellStyle(cellStyle);
    }

    // 自适应宽度(中文支持)
    private void setSizeColumn(HSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256 + 184);//256*35+184
        }
    }
}
