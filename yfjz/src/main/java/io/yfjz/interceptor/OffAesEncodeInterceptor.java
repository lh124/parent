package io.yfjz.interceptor;


import java.lang.reflect.Field;

import io.yfjz.utils.AESCodec;
import io.yfjz.entity.PersistentEntity;
import io.yfjz.utils.PropertiesUtils;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
/**
 * class_name: OffAesEncodeInterceptor
 *
 * @Description:
 * @author: 饶士培
 * @QQ: 1013147559@qq.com
 * @tel: 18798010686
 * @date: 2018-08-01 9:14
 */
@Intercepts({@org.apache.ibatis.plugin.Signature(type=org.apache.ibatis.executor.Executor.class, method="update", args={MappedStatement.class, Object.class})})
public class OffAesEncodeInterceptor  implements Interceptor{
    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    private Properties properties;
    private static String path = "AES.txt";
    private static String KEY_PATH = "";
    private static String[] aesCodes = new String[0];

    public Object intercept(Invocation invocation)
            throws Throwable
    {
        if (aesCodes.length == 0)
        {
            String aesCodeStr = PropertiesUtils.getProperty("aesCode.properties", "AESCODE");
            if ((aesCodeStr != null) && (!"".equals(aesCodeStr))) {
                aesCodes = aesCodeStr.split(";");
            }
            String localPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            if (localPath.indexOf("%") >= 0) {
                localPath = URLDecoder.decode(localPath, "utf-8");
            }
            KEY_PATH = localPath + path;
        }
        Object[] updateArgs = invocation.getArgs();
        MappedStatement ms = (MappedStatement)updateArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = updateArgs[PARAMETER_INDEX];
        BoundSql ss = ms.getBoundSql(parameter);
        String oldSql = ss.getSql().trim();
        for (String code : aesCodes)
        {
            String[] aesCode = code.split(":");
            String table = aesCode[0].substring(0, aesCode[0].indexOf("["));
            String[] collName = aesCode[1].split(",");
            if (oldSql.toLowerCase().contains(table.toLowerCase())) {
                for (String name : collName) {
                    if ((parameter instanceof PersistentEntity))
                    {
                        Object val = getFieldValue(parameter, name);
                        if ((val != null) && (!"".equals(val))) {
                            setFieldValue(parameter, name, AESCodec.getAESEncode_new(val.toString(), KEY_PATH));
                        }
                    }
                    else if ((parameter instanceof Map))
                    {
                        Map<Object, Object> val = (Map)parameter;
                        if ((val.containsKey("list")) && ((val.get("list") instanceof ArrayList)))
                        {
                            ArrayList<Object> lis = (ArrayList)val.get("list");
                            for (Object lisfObj : lis) {
                                if ((lisfObj instanceof PersistentEntity))
                                {
                                    Object obVal = getFieldValue(lisfObj, name);
                                    if ((obVal != null) && (!"".equals(obVal))) {
                                        setFieldValue(lisfObj, name, AESCodec.getAESEncode_new(obVal.toString(), KEY_PATH));
                                    }
                                }
                                else if ((lisfObj instanceof Map))
                                {
                                    Map<Object, Object> objMap = (Map)lisfObj;
                                    if ((objMap.containsKey(name)) && (objMap.get(name) != null) && (!"".equals(objMap.get(name)))) {
                                        val.put(name, AESCodec.getAESEncode_new(objMap.get(name).toString(), KEY_PATH));
                                    }
                                }
                            }
                        }
                        else if ((val.containsKey(name)) && (val.get(name) != null) && (!"".equals(val.get(name))))
                        {
                            val.put(name, AESCodec.getAESEncode_new(val.get(name).toString(), KEY_PATH));
                        }
                    }
                }
            }
        }
        return invocation.proceed();
    }

    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }

    public static Object getFieldValue(Object obj, String fieldName)
    {
        Object result = null;
        Field field = getField(obj, fieldName);
        if (field != null)
        {
            field.setAccessible(true);
            try
            {
                result = field.get(obj);
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static Field getField(Object obj, String fieldName)
    {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try
            {
                field = clazz.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException localNoSuchFieldException) {}
        }
        return field;
    }

    public static void setFieldValue(Object obj, String fieldName, String fieldValue)
    {
        Field field = getField(obj, fieldName);
        if (field != null) {
            try
            {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }
}
