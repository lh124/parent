//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RRExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RRExceptionHandler() {
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        R r = new R();

        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            if(ex instanceof RRException) {
                r.put("code", Integer.valueOf(((RRException)ex).getCode()));
                r.put("msg", ((RRException)ex).getMessage());
            } else if(ex instanceof DuplicateKeyException) {
                r = R.error("数据库中已存在该记录");
            } else if(ex instanceof AuthorizationException) {
                r = R.error("没有权限，请联系管理员授权");
            } else {
                r = R.error();
            }

            this.logger.error(ex.getMessage(), ex);
            String e = JSON.toJSONString(r);
            response.getWriter().print(e);
        } catch (Exception var7) {
            this.logger.error("RRExceptionHandler 异常处理失败", var7);
        }

        return new ModelAndView();
    }
}
