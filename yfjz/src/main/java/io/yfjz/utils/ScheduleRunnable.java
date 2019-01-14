//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class ScheduleRunnable implements Runnable {
    private Object target;
    private Method method;
    private String params;

    public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
        this.target = SpringContextUtils.getBean(beanName);
        this.params = params;
        if(StringUtils.isNotBlank(params)) {
            this.method = this.target.getClass().getDeclaredMethod(methodName, new Class[]{String.class});
        } else {
            this.method = this.target.getClass().getDeclaredMethod(methodName, new Class[0]);
        }

    }

    public void run() {
        try {
            ReflectionUtils.makeAccessible(this.method);
            if(StringUtils.isNotBlank(this.params)) {
                this.method.invoke(this.target, new Object[]{this.params});
            } else {
                this.method.invoke(this.target, new Object[0]);
            }

        } catch (Exception var2) {
            throw new RRException("执行定时任务失败", var2);
        }
    }
}
