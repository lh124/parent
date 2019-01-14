package io.yfjz.task;

import io.yfjz.managerservice.provinceplatform.ProvincePlatformServiceManager;
import io.yfjz.service.provinceplatform.ProvincePlatformService;
import io.yfjz.service.report.ExcelService;
import io.yfjz.utils.PropertiesUtils;
import io.yfjz.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 饶士培
 * @Date: 2018-10-02 13:39
 * @Description:
 * @tel:18798010686
 * @qq:1013147559
 */
public class javaTimerTask implements ServletContextListener {
    @Autowired
    ProvincePlatformServiceManager provincePlatformServiceManager;
    private Timer timer = null;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        timer = new Timer(true);
        event.getServletContext().log("定时器已启动");

        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的11:00:00执行
        calendar.set(year, month, day, 18, 00, 00);
        java.util.Date date = calendar.getTime();
        int period = 30 * 1000;

        //每天的date时刻执行SynchronousDataFromProvinceTask，每隔30秒重复执行
        //timer.schedule(new SynchronousDataFromProvinceTask(), date, period);
        //每天的date时刻执行SynchronousDataFromProvinceTask, 仅执行一次
        timer.schedule(new SynchronousDataFromProvinceTask(), date);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (timer != null) {
            timer.cancel();
            event.getServletContext().log("定时器销毁");
        }
    }


   public class SynchronousDataFromProvinceTask extends TimerTask{
        @Override
        public void run(){
            int days = -7;
            provincePlatformServiceManager = (ProvincePlatformServiceManager) SpringContextUtils.getBean("provincePlatformServiceManager");
            provincePlatformServiceManager.downloadMigrationChildNo(days);
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("你好");

            }
        };
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(runnable,0,12, TimeUnit.HOURS);
    }
}

