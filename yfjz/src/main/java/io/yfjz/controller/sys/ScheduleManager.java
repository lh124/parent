package io.yfjz.controller.sys;

import io.yfjz.service.backup.FileService;
import io.yfjz.service.backup.impl.FileServiceImpl;
import io.yfjz.service.mgr.TMgrStockInfoService;
import io.yfjz.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** 
* @Description: spring 定时任务管理器 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/10/26 19:51
* @Tel  17328595627
*/ 
@Component
public class ScheduleManager {
    @Autowired
    private TMgrStockInfoService stockInfoService;
    @Autowired
    private FileService fileService;

    /** 
    * @Description: 定时任务，每月1号自动结转库存，查询库存数据，插入每月初始库存中
    * @Param: [] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/26 20:15
    * @Tel  17328595627
    */ 
    @Scheduled(cron = "0 0 0 1 * ?")
    public void initStock(){
        stockInfoService.carryOverStock();
        System.out.println("每月1号凌晨执行定时任务++++++++++++++++++++结转库存");
    }
    /** 
    * @Description: 每天备份一次数据库
    * @Param: [] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/26 20:20
    * @Tel  17328595627
    */
    @Scheduled(cron = "0 01 00 * * ?")
    public void backUpData(){
        String path=this.getDir();
        Constant.SHELLPATH=path+"shell/";
        //解决脚本没有执行权限问题
        String chmodPath = Constant.SHELLPATH + "*.sh";
        String template = "chmod 777 %s";
        String command = String.format(template, chmodPath);
        String[] cmd = new String[] { "/bin/sh", "-c", command };
        BufferedReader br = null;
        try {
            final Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            System.out.println("执行完成");
            Process processB = fileService.execShell(Constant.SHELLPATH, Constant.BACKUPSHELL);

                br = new BufferedReader(new InputStreamReader(processB.getInputStream()));
                String output = null;
                System.out.println("开始执行数据库自动备份+++++++++++++++++++");
                while (null != (output = br.readLine()))
                {
                    System.out.println(output);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getDir(){
        Thread thread = Thread.currentThread();
        return thread.getContextClassLoader().getResource("/").getPath();
    }

}
