package io.yfjz.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @class_name: CommonUtils
 * @describe: 公共的工具类
 * @author 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date:  2017/12/26 13:25
 **/
public class CommonUtils {

    /**
     * @method_name: randomNumeric
     * @describe: 获取N个随机数
     * @param [randomNum:传入需要生成的位数]
     * @return java.lang.String
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/26  13:26
     **/
    public static String randomNumeric(int randomNum){
        return RandomStringUtils.randomNumeric(randomNum);
    }

    /**
     * @method_name: isEmpty
     * @describe:
     *      判断传入的obj对象是否为空
     *      1：检查String字符串  "" "null"  "  " 三种情况
     *      2：检查list 对象
     *      3：检查map对象
     * @param: [obj]
     * @return: boolean
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/6/26  17:58
     **/
    public final static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        if ((obj instanceof String)) {
            String key = obj.toString().replaceAll("\\s*", "");
            return key.equals("") || key.length() <= 0 || key.toLowerCase().equals("null");
        }
        if (obj instanceof String[]) {
            String[] arr = (String[]) obj;
            return arr == null || arr.length <= 0;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            return list == null || list.size() <= 0;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            return map == null || map.size() <= 0;
        }
        return false;
    }

    
    /**
     * @method_name: getBaseTowerInfoByName
     * @describe: 根据给定的类型名称，获取对应的值
     * @param: [name]
     * @return: java.lang.String
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/28  20:21
     **/
    public static String getBaseTowerInfoByName(String name) {
        if (StringUtils.isEmpty(name)){
            throw new RRException("请传入需要匹配的参数名称类型");
        }else if (name.equalsIgnoreCase("orgId")){//当前登录用户所属机构编码
            return Constant.GLOBAL_ORG_ID;
        }else if (name.equalsIgnoreCase("orgName")){//当前登录用户所属机构名称
            return Constant.GLOBAL_ORG_NAME;
        }else if (name.equalsIgnoreCase("userId")){//当前登录账户编码
            return ShiroUtils.getUserEntity().getUserId();
        }else if (name.equalsIgnoreCase("userName")){//当前登录用户名称
            return ShiroUtils.getUserEntity().getUsername();
        }else if (name.equalsIgnoreCase("regTowerId")){//登记台  工作台编码
            return ShiroUtils.getUserEntity().getRegisterTowerId();
        }else if (name.equalsIgnoreCase("inocTowerId")){//接种台  工作台编码
            return ShiroUtils.getUserEntity().getInoculateTowerId();
        }else if (name.equalsIgnoreCase("chiProId")){//儿保台  工作台编码
            return ShiroUtils.getUserEntity().getChildProtectionTowerId();
        }else if (name.equalsIgnoreCase("preId")){//预检台  工作台编码
            return ShiroUtils.getUserEntity().getPreCheckId();
        }else if (name.equalsIgnoreCase("regTowerName")){//登记台名称
            return ShiroUtils.getUserEntity().getRegisterTowerName();
        }else if (name.equalsIgnoreCase("inocTowerName")){//接种台名称
            return ShiroUtils.getUserEntity().getInoculateTowerName();
        }else if (name.equalsIgnoreCase("chiProName")){//儿保台名称
            return ShiroUtils.getUserEntity().getChildProtectionTowerName();
        }else if (name.equalsIgnoreCase("preName")){//预检台名称
            return ShiroUtils.getUserEntity().getPreCheckName();
        }else{
            throw new RRException("您输入的参数名没有匹配的值，请检查参数名,请检查");
        }
    }
    /** 
    * @Description:
    * @Param: [queryMap, date] 
    * @return: java.util.Date 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/8 16:09
    * @Tel  17328595627
    */ 
    public static  Date transformDate( String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dates=null;
        if (!org.springframework.util.StringUtils.isEmpty(date)){
            try {
                dates = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }
    public static void main(String[] args) {
        System.out.println(randomNumeric(12));
    }

}
