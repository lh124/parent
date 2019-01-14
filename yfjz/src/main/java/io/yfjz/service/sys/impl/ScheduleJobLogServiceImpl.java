//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;


import java.util.List;
import java.util.Map;

import io.yfjz.dao.sys.ScheduleJobLogDao;
import io.yfjz.entity.sys.ScheduleJobLogEntity;
import io.yfjz.service.sys.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
    @Autowired
    private ScheduleJobLogDao scheduleJobLogDao;

    public ScheduleJobLogServiceImpl() {
    }

    public ScheduleJobLogEntity queryObject(Long jobId) {
        return (ScheduleJobLogEntity)this.scheduleJobLogDao.queryObject(jobId);
    }

    public List<ScheduleJobLogEntity> queryList(Map<String, Object> map) {
        return this.scheduleJobLogDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return this.scheduleJobLogDao.queryTotal(map);
    }

    public void save(ScheduleJobLogEntity log) {
        this.scheduleJobLogDao.save(log);
    }
}
