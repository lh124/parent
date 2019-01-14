//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.dao.sys;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.sys.ScheduleJobEntity;

import java.util.Map;

public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
    int updateBatch(Map<String, Object> var1);
}
