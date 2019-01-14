package io.yfjz.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 加载属性文件
 * Created by lq on 2017/6/13.
 */
public class PropertiesUtils {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    public final static String PROPERTY_FILE_NAME = "config.properties";  //属性文件名称
    public final static String AESCODE_NAME = "aesCode.properties";  //属性文件名称
    private static Map<String, String> smartMap = null;

    /**
     * 根据key获取对应的属性值
     * 说明：调用此方法，直接将属性文件中key作为参数传入方法内，
     * 内部加载属性文件到静态map中，通过 map 的get方法类获取对应的值
     *
     * @param key
     * @return
     */
    public static String getMapValue(String key) {
        if (null != smartMap) {
            return smartMap.get(key);
        }
        smartMap = new ConcurrentHashMap<>();//刷新，意味着重新给smarMap赋值
        loadProperties();//加载一次属性值到map中
        return smartMap.get(key);
    }


    /**
     * 刷新静态Map集合
     * 说明：通过http请求，刷新属性属性文件的值到map中
     *
     */
    public static void reloadStaticMap() {
        smartMap.clear();//清除map中的属性值
        loadProperties();//加载一次属性值到map中
    }


    /**
     * 加载属性文件到静态map中
     * 说明：此方法是私有方法，不对外发布
     *
     * @throws IOException
     */
    private static void loadProperties() {
        try {
            Properties properties = new Properties();
            properties.load(new ClassPathResource(PROPERTY_FILE_NAME).getInputStream());
            Set<Object> sets = properties.keySet();
            for (Object str : sets) {
                smartMap.put(str.toString(), properties.getProperty(str.toString()));
            }
            logger.info("###总共加载" + smartMap.size() + "条属性####");
        } catch (IOException e) {
            logger.info("###加载属性文件出现IO异常####");
            e.printStackTrace();
        }


    }

    public static String getProperty (String url,String key){
        try {
        Properties properties = new Properties();
        properties.load(new ClassPathResource(url).getInputStream());
        String property =  properties.get(key).toString();
        return property;
        } catch (IOException e) {
            logger.info("###加载属性文件出现IO异常####");
            e.printStackTrace();
            return null;
        }
    }
}
