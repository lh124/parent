//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys;

import io.yfjz.entity.sys.ScheduleJobEntity;

import java.util.List;
import java.util.Map;

public interface ScheduleJobService {
    ScheduleJobEntity queryObject(Long var1);

    List<ScheduleJobEntity> queryList(Map<String, Object> var1);

    int queryTotal(Map<String, Object> var1);

    void save(ScheduleJobEntity var1);

    void update(ScheduleJobEntity var1);

    void deleteBatch(Long[] var1);

    int updateBatch(Long[] var1, int var2);

    void run(Long[] var1);

    void pause(Long[] var1);

    void resume(Long[] var1);
}
