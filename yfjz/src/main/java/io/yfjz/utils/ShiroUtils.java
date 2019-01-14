//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

import io.yfjz.entity.sys.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
    public ShiroUtils() {
    }

    /**
     * @method_name: getSession
     * @describe: 获取web的shiro session
     * @param: []
     * @return: org.apache.shiro.session.Session
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/25  9:17
     **/
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * @method_name: getSubject
     * @describe: 获取主体对象subject  shiro的核心
     * @param: []
     * @return: org.apache.shiro.subject.Subject
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/25  9:16
     **/
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }


    /**
     * @method_name: getUserEntity
     * @describe: 获取登录用户的基本信息
     * @param: []
     * @return: io.yfjz.entity.SysUserEntity
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/25  9:15
     **/
    public static SysUserEntity getUserEntity() {
        return (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
    }

    public static String getUserId() {
        return getUserEntity().getUserId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getKaptcha(String key) {
        String kaptcha = getSessionAttribute(key).toString();
        getSession().removeAttribute(key);
        return kaptcha;
    }
}
