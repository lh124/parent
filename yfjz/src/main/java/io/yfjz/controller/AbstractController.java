//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractController() {
    }

    protected SysUserEntity getUser() {
        return ShiroUtils.getUserEntity();
    }

    protected String getUserId() {
        return this.getUser().getUserId();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }


    /**
     * @method_name: getMapFromRequest
     * @describe: 将HttpServletRequest解析成Map参数返回
     * @param: [request]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/13  15:57
     **/
    public static Map<String, Object> getMapFromRequest(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement().toString();
            String[] paramValues = request.getParameterValues(paramName);//同一个name对应多个值，比如select，checkbox等类似组件
            if (paramValues.length == 1) {
                String pv = request.getParameter(paramName);
                paramName = paramName.replace("[]", "");
                if (pv.length() > 0) {
                    map.put(paramName, pv);
                }
            } else {
                paramName = paramName.replace("[]", "");
                map.put(paramName, paramValues);
            }
        }
        return map;
    }

    /**
     * @method_name: getPaginationMapFromRequest
     * @describe: 做分页时应该调用此方法做分页参数转换，page --> offfset ;  limit --> limt
     * 配合mybatis.xml方法中的offset，limit 两个参数使用，实现分页
     * 如果不需要分页，那么不要调用此方法
     * 如果需要分页，那么调用此方法获取分页参数，分页默认每页条数10条，页码从1开始
     * @param: [request]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/13  15:57
     **/
    public static Map<String, Object> getPaginationMapFromRequest(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement().toString();
            String[] paramValues = request.getParameterValues(paramName);//同一个name对应多个值，比如select，checkbox等类似组件
            if (paramValues.length == 1) {
                String pv = request.getParameter(paramName);
                paramName = paramName.replace("[]", "");
                if (pv.length() > 0) {
                    map.put(paramName, pv);
                }
            } else {
                paramName = paramName.replace("[]", "");
                map.put(paramName, paramValues);
            }
        }
        int page = Integer.parseInt(map.get("page").toString()) <= 1 ? 1 : Integer.parseInt(map.get("page").toString());
        int limit = Integer.parseInt(map.get("limit").toString()) <= 10 ? 10 : Integer.parseInt(map.get("limit").toString());
        map.put("offset", (page - 1) * limit); //分页第一个参数
        map.put("limit", limit); //分页第二个参数
        return map;
    }

}
