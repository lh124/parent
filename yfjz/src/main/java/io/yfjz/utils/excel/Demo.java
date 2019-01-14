package io.yfjz.utils.excel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * class_name: Demo
 *
 * @author 刘琪
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-06-24 23:30
 */
public class Demo {

    public static void main(String[] args) throws Exception {

        //测试
        final HashMap<String,String> mapper = new HashMap<>();
        //序号	问题类型	严重程度	模块	问题描述
        // 提出时间	提出人	出现问题版本号	开发接收问题时间
        // 问题分析	前置任务	计划提交测试时间	是否延期
        // 研发版本号	计划发布现场时间	开发人员
        // JIRA编号	实际发布现场时间	发布版本号	问题状态
        mapper.put("序号","xh");
        mapper.put("问题类型","wtlx");
        mapper.put("严重程度","yzcd");
        mapper.put("模块","mk");
        mapper.put("问题描述","wtms");
        mapper.put("提出时间","tcsj");
        mapper.put("提出人","tcr");
        mapper.put("出现问题版本号","cxwtbbh");
        mapper.put("开发接收问题时间","kfjswtsj");
        mapper.put("问题分析","wtfx");
        mapper.put("前置任务","qzrw");
        mapper.put("计划提交测试时间","jhtjcssj");
        mapper.put("是否延期","sfyq");
        mapper.put("研发版本号","yfbbh");
        mapper.put("计划发布现场时间","jhfbxcsj");
        mapper.put("开发人员","kfry");
        mapper.put("JIRA编号","jirabh");
        mapper.put("实际发布现场时间","sjfbxcsj");
        mapper.put("发布版本号","fbbh");
        mapper.put("问题状态","wtzt");
        final ExcelUtils excel = new ExcelUtils(1,mapper);
        excel.readExcel("F:\\test\\aa.xlsx");
        final ArrayList<HashMap<String, String>> list = excel.getExcelData();

        for (HashMap<String,String> map : list ) {
            for (String key : map.keySet()){
                System.out.print(key +":"+ map.get(key) +"\t\t");
            }
            System.out.println("\n\n\n");
        }
    }
}
