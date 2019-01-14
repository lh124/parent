//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

import io.yfjz.entity.sys.ScheduleJobEntity;
import io.yfjz.utils.Constant.ScheduleStatus;
import org.quartz.*;

public class ScheduleUtils {
    private static final String JOB_NAME = "TASK_";

    public ScheduleUtils() {
    }

    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey("TASK_" + jobId);
    }

    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey("TASK_" + jobId);
    }

    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger)scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException var3) {
            throw new RRException("获取定时任务CronTrigger出现异常", var3);
        }
    }

    public static void createScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            JobDetail e = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
            CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(scheduleBuilder).build();
            e.getJobDataMap().put("JOB_PARAM_KEY", scheduleJob);
            scheduler.scheduleJob(e, trigger);
            if(scheduleJob.getStatus().intValue() == ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }

        } catch (SchedulerException var5) {
            throw new RRException("创建定时任务失败", var5);
        }
    }

    public static void updateScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            TriggerKey e = getTriggerKey(scheduleJob.getJobId());
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());
            trigger = (CronTrigger)trigger.getTriggerBuilder().withIdentity(e).withSchedule(scheduleBuilder).build();
            trigger.getJobDataMap().put("JOB_PARAM_KEY", scheduleJob);
            scheduler.rescheduleJob(e, trigger);
            if(scheduleJob.getStatus().intValue() == ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }

        } catch (SchedulerException var5) {
            throw new RRException("更新定时任务失败", var5);
        }
    }

    public static void run(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            JobDataMap e = new JobDataMap();
            e.put("JOB_PARAM_KEY", scheduleJob);
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), e);
        } catch (SchedulerException var3) {
            throw new RRException("立即执行定时任务失败", var3);
        }
    }

    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException var3) {
            throw new RRException("暂停定时任务失败", var3);
        }
    }

    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException var3) {
            throw new RRException("暂停定时任务失败", var3);
        }
    }

    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException var3) {
            throw new RRException("删除定时任务失败", var3);
        }
    }
}
