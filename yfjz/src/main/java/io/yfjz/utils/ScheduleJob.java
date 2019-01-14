//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

import io.yfjz.entity.sys.ScheduleJobEntity;
import io.yfjz.entity.sys.ScheduleJobLogEntity;
import io.yfjz.service.sys.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScheduleJob extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService service = Executors.newSingleThreadExecutor();

    public ScheduleJob() {
    }

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity)context.getMergedJobDataMap().get("JOB_PARAM_KEY");
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService)SpringContextUtils.getBean("scheduleJobLogService");
        ScheduleJobLogEntity log = new ScheduleJobLogEntity();
        log.setJobId(scheduleJob.getJobId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setCreateTime(new Date());
        long startTime = System.currentTimeMillis();

        try {
            this.logger.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable e = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            Future times2 = this.service.submit(e);
            times2.get();
            long times1 = System.currentTimeMillis() - startTime;
            log.setTimes(Integer.valueOf((int)times1));
            log.setStatus(Integer.valueOf(0));
            this.logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times1 + "毫秒");
        } catch (Exception var14) {
            this.logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), var14);
            long times = System.currentTimeMillis() - startTime;
            log.setTimes(Integer.valueOf((int)times));
            log.setStatus(Integer.valueOf(1));
            log.setError(StringUtils.substring(var14.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.save(log);
        }

    }
}
