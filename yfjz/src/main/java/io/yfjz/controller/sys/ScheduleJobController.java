//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;

import io.yfjz.entity.sys.ScheduleJobEntity;
import io.yfjz.service.sys.ScheduleJobService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/sys/schedule"})
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    public ScheduleJobController() {
    }

    @RequestMapping({"/list"})
    public R list(String beanName, Integer page, Integer limit) {
        HashMap map = new HashMap();
        map.put("beanName", beanName);
        map.put("offset", Integer.valueOf((page.intValue() - 1) * limit.intValue()));
        map.put("limit", limit);
        List jobList = this.scheduleJobService.queryList(map);
        int total = this.scheduleJobService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(jobList, total, limit.intValue(), page.intValue());
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping({"/info/{jobId}"})
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = this.scheduleJobService.queryObject(jobId);
        return R.ok().put("schedule", schedule);
    }

    @RequestMapping({"/save"})
    public R save(@RequestBody ScheduleJobEntity scheduleJob) {
        this.verifyForm(scheduleJob);
        this.scheduleJobService.save(scheduleJob);
        return R.ok();
    }

    @RequestMapping({"/update"})
    public R update(@RequestBody ScheduleJobEntity scheduleJob) {
        this.verifyForm(scheduleJob);
        this.scheduleJobService.update(scheduleJob);
        return R.ok();
    }

    @RequestMapping({"/delete"})
    public R delete(@RequestBody Long[] jobIds) {
        this.scheduleJobService.deleteBatch(jobIds);
        return R.ok();
    }

    @RequestMapping({"/run"})
    public R run(@RequestBody Long[] jobIds) {
        this.scheduleJobService.run(jobIds);
        return R.ok();
    }

    @RequestMapping({"/pause"})
    public R pause(@RequestBody Long[] jobIds) {
        this.scheduleJobService.pause(jobIds);
        return R.ok();
    }

    @RequestMapping({"/resume"})
    public R resume(@RequestBody Long[] jobIds) {
        this.scheduleJobService.resume(jobIds);
        return R.ok();
    }

    private void verifyForm(ScheduleJobEntity scheduleJob) {
        if(StringUtils.isBlank(scheduleJob.getBeanName())) {
            throw new RRException("bean名称不能为空");
        } else if(StringUtils.isBlank(scheduleJob.getMethodName())) {
            throw new RRException("方法名称不能为空");
        } else if(StringUtils.isBlank(scheduleJob.getCronExpression())) {
            throw new RRException("cron表达式不能为空");
        }
    }
}
