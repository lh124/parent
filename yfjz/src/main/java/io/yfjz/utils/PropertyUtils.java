package io.yfjz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘琪
 * @describe: 属性文件操作工具类
 *              说明：此工具只加载resources目录下的config.properties文件，需要动态配置的内容，请配置在config.properties文件中
 *              静态工具类，通过key获取value的值，该工具类中只使用了java.util包中的类，不依赖其他的jar
 * @class_name: PropertyUtils
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017-12-14  14:20
 **/
public class PropertyUtils {
    private static String filepath = "config.properties";//默认读取这个属性文件
    private static Map<String, String> smartMap = null;//存放properties属性文件中的key,value键值对的值

    /**
     * @param [key：属性文件中的key值]
     * @return java.lang.String
     * @method_name: getValue
     * @describe: 根据属性文件中配置的key获取对应的value值
     *              说明：此方法将属性文件的内容加载到了静态resultMap中，map的get方法根据key获取value值
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-14  14:26
     **/
    public static String getValue(String key) {
        if (null == smartMap)
            load2Map();
        return smartMap.get(key);
    }

    /**
     * @method_name: load2Map
     * @describe: 加载属性文件内容到静态map中（key,value）
     *              说明：此方法主要是在调用初始化时，加载属性文件的内容到静态map中，使用key,value的方式存储到map中，
     *              就是为了一次加载，重复调用，不会再此加载，提高系统的访问性能，
     *              唯一不足的是会消耗一定的内存，这个静态map是常驻内存的
     * @param []
     * @return void
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-14  14:40
     **/
    private static void load2Map() {
        InputStream in = null;
        try {
            smartMap = new ConcurrentHashMap<>();//刷新，意味着重新给resultMap赋值
            Properties _p = new Properties();
            in = PropertyUtils.class.getClassLoader().getResourceAsStream(filepath);//通过类加载器进行获取properties文件流，使用jdk提供的类加载方式加载文件到文件流中
            _p.load(in);
            Set<Object> _set = _p.keySet();
            for (Object _s : _set) {
                smartMap.put(_s.toString(), _p.getProperty(_s.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param []
     * @return void
     * @method_name: reloadMap
     * @describe: 通过调用该方法，直接刷新属性文件中的内容加载到smartMap中
     *              说明：此方法使用的情况，主要是手工更改了属性文件的内容，需要及时刷新静态map中的内容，防止用户还在使用旧数据，造成不可预知的错误
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017-12-14  14:29
     **/
    public static void reloadMap() {
        smartMap.clear();//首先清空smartMap中的键值对
        load2Map();//调用此方法加载属性文件内容到map中
    }
}
