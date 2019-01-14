//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;


import io.yfjz.dao.sys.ScheduleJobDao;
import io.yfjz.entity.sys.ScheduleJobEntity;
import io.yfjz.service.sys.ScheduleJobService;
import io.yfjz.utils.ScheduleUtils;
import io.yfjz.utils.Constant.ScheduleStatus;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobDao schedulerJobDao;

    public ScheduleJobServiceImpl() {
    }

    @PostConstruct
    public void init() {
        List scheduleJobList = this.schedulerJobDao.queryList(new HashMap());
        Iterator var3 = scheduleJobList.iterator();

        while(var3.hasNext()) {
            ScheduleJobEntity scheduleJob = (ScheduleJobEntity)var3.next();
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(this.scheduler, scheduleJob.getJobId());
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(this.scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(this.scheduler, scheduleJob);
            }
        }

    }

    public ScheduleJobEntity queryObject(Long jobId) {
        return (ScheduleJobEntity)this.schedulerJobDao.queryObject(jobId);
    }

    public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
        return this.schedulerJobDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return this.schedulerJobDao.queryTotal(map);
    }

    @Transactional
    public void save(ScheduleJobEntity scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(Integer.valueOf(ScheduleStatus.NORMAL.getValue()));
        this.schedulerJobDao.save(scheduleJob);
        ScheduleUtils.createScheduleJob(this.scheduler, scheduleJob);
    }

    @Transactional
    public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(this.scheduler, scheduleJob);
        this.schedulerJobDao.update(scheduleJob);
    }

    @Transactional
    public void deleteBatch(Long[] jobIds) {
        Long[] var5 = jobIds;
        int var4 = jobIds.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Long jobId = var5[var3];
            ScheduleUtils.deleteScheduleJob(this.scheduler, jobId);
        }

        this.schedulerJobDao.deleteBatch(jobIds);
    }

    public int updateBatch(Long[] jobIds, int status) {
        HashMap map = new HashMap();
        map.put("list", jobIds);
        map.put("status", Integer.valueOf(status));
        return this.schedulerJobDao.updateBatch(map);
    }

    @Transactional
    public void run(Long[] jobIds) {
        Long[] var5 = jobIds;
        int var4 = jobIds.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Long jobId = var5[var3];
            ScheduleUtils.run(this.scheduler, this.queryObject(jobId));
        }

    }

    @Transactional
    public void pause(Long[] jobIds) {
        Long[] var5 = jobIds;
        int var4 = jobIds.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Long jobId = var5[var3];
            ScheduleUtils.pauseJob(this.scheduler, jobId);
        }

        this.updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }

    @Transactional
    public void resume(Long[] jobIds) {
        Long[] var5 = jobIds;
        int var4 = jobIds.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Long jobId = var5[var3];
            ScheduleUtils.resumeJob(this.scheduler, jobId);
        }

        this.updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }
}
