//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @class_name: R
 * @describe: 返回HashMap对象到界面，这里封装对象到HashMap中
 * @author 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date:  2017/12/23 23:52
 **/
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * @method_name: R
     * @describe: 默认构造函数，返回code 为0
     * @param []
     * @return
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date:
     **/
    public R() {
        this.put("code", 0);
    }

    /**
     * @method_name: error
     * @describe: 系统级别默认错误提示
     * @param []
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/26  13:18
     **/
    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    /**
     * @method_name: error
     * @describe: 传递自定义错误信息到前端，默认code为500
     * @param [msg]
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/26  13:18
     **/
    public static R error(String msg) {
        return error(500, msg);
    }

    /**
     * @method_name: error
     * @describe: 自定义错误编码，错误提示信息
     * @param [code, msg]
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/26  13:19
     **/
    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }


    /**
     * @method_name: ok
     * @describe: 默认正常
     * @param []
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/26  13:20
     **/
    public static R ok() {
        return new R();
    }

    /**
     * @method_name: ok
     * @describe: 自定义正确提示信息
     * @param [msg]
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/26  13:20
     **/
    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    /**
     * @method_name: ok
     * @describe: 自定义正确编码，提示信息
     * @param [code, msg]
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/26  13:21
     **/
    public static R ok(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * @describe: 将Map集合返回前端
     * @method_name: ok
     * @param [map]
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/24  12:31
     **/
    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    /**
     * @describe: 返回一个HashMap对象 key ,value是一个Object对象
     * @method_name: put
     * @param [key, value]
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/24  12:30
     **/
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}