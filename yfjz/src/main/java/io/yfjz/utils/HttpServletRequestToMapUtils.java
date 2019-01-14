package io.yfjz.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 刘琪
 * @class_name: HttpServletRequestToMapUtils
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-07-23 22:37
 */
public class HttpServletRequestToMapUtils {
    /**
     * @method_name: getParamMapByHttpServletRequest
     * @describe: request转map
     * @param: [request]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/23  22:38
     **/
    public static Map<String,Object> getParamMapByHttpServletRequest(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement().toString();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String pv = request.getParameter(paramName);
                if (!StringUtils.isEmpty(pv)&& pv.length() > 0) {
                    paramName = paramName.replace("[]", "");
                    paramMap.put(paramName, pv);
                }
            } else {
                paramMap.put(paramName, paramValues);
            }
        }
        return paramMap;
    }

    /**
     * @method_name: getParamMapByHttpServletRequest
     * @describe: 根据request 获取 前台传递的参数名及参数值,此方法获取所有的参数及参数值，
     * @param: [request]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/23  22:38
     **/
    public static Map<String,Object> converToMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement().toString();
            String[] paramValues = request.getParameterValues(paramName);
            paramName = paramName.replace("[]", "");
            if (paramValues.length == 1) {
                paramMap.put(paramName, paramValues[0]);
            } else {
                paramMap.put(paramName, paramValues);
            }

        }
        return paramMap;
    }
    /** 
    * @Description: 从请求中获取参数，去掉空串 
    * @Param: [request] 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/26 14:44
    * @Tel  17328595627
    */ 
    public static Map<String,Object> requestToMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement().toString();
            String[] paramValues = request.getParameterValues(paramName);
            paramName = paramName.replace("[]", "");
            if(!StringUtils.isEmpty(paramValues[0])&&!paramValues[0].equals("null")){
                if (paramValues.length == 1) {
                    paramMap.put(paramName, paramValues[0]);
                } else {
                    paramMap.put(paramName, paramValues);
                }
            }
        }
        return paramMap;
    }
}
