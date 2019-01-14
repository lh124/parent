//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys;

import io.yfjz.entity.sys.ScheduleJobLogEntity;

import java.util.List;
import java.util.Map;

public interface ScheduleJobLogService {
    ScheduleJobLogEntity queryObject(Long var1);

    List<ScheduleJobLogEntity> queryList(Map<String, Object> var1);

    int queryTotal(Map<String, Object> var1);

    void save(ScheduleJobLogEntity var1);
}
