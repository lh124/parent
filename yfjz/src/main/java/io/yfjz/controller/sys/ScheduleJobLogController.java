//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;

import io.yfjz.entity.sys.ScheduleJobLogEntity;
import io.yfjz.service.sys.ScheduleJobLogService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import java.util.HashMap;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/sys/scheduleLog"})
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    public ScheduleJobLogController() {
    }

    @RequestMapping({"/list"})
    public R list(Integer page, Integer limit, Integer jobId) {
        HashMap map = new HashMap();
        map.put("jobId", jobId);
        map.put("offset", Integer.valueOf((page.intValue() - 1) * limit.intValue()));
        map.put("limit", limit);
        List jobList = this.scheduleJobLogService.queryList(map);
        int total = this.scheduleJobLogService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(jobList, total, limit.intValue(), page.intValue());
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping({"/info/{logId}"})
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = this.scheduleJobLogService.queryObject(logId);
        return R.ok().put("log", log);
    }
}
