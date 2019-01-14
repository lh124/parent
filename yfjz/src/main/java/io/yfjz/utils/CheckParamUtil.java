package io.yfjz.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckParamUtil<T> {
    /**
     *
     * @Title:checkParam
     * @Description:(该方法用来校验对象及其属性是否为空)
     * @param t
     * @param args
     * @author 廖欢
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public void checkParam(T t, String... args) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        // 如果传入的对象为空，则直接抛出异常
        if (t == null) {
            throw new IllegalArgumentException("对象不能为空!");
        }
        Class<? extends Object> clazz = t.getClass();
        // 定义属性列表
        List<String> argsList = new ArrayList<String>();
        // 如果传入的属性名不为空，则将传入的属性名放入属性列表
        if (args != null && args.length > 0) {
            argsList = Arrays.asList(args);
        } else {// 如果传入的属性名为空，则将所有属性名放入属性列表
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                argsList.add(field.getName());
            }
        }
        // 获取该类自定义的方法数组
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 方法名
            String methodName = method.getName();
            // 获取方法对应的属性名
            String fieldName = "";
            if (methodName.length() >= 4) {
                fieldName = methodName.substring(3, 4).toLowerCase()
                        + methodName.substring(4);
                // 如果方法是“get方法”，并且属性列表中包含该方法对应的属性名
                if (methodName.startsWith("get")
                        && argsList.contains(fieldName)) {
                    // 如果为null，抛出异常
                    if (method.invoke(t) == null) {
                        throw new IllegalArgumentException(fieldName
                                + " 不能为空!");
                    }
                    // 如果该方法返回类型为String,返回结果为空字符串，抛出异常。
                    Class<?> returnType = method.getReturnType();
                    String returnTypeName = returnType.getSimpleName();
                    if (returnTypeName.equals("String")
                            && "".equals(((String)(method.invoke(t))).trim())) {
                        throw new IllegalArgumentException(fieldName
                                + " 不能为空!");
                    }
                }
            }

        }
    }
}
