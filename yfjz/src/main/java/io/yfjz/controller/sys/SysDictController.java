//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;

import io.yfjz.controller.AbstractController;
import io.yfjz.entity.sys.SysDictEntity;
import io.yfjz.service.sys.SysDictService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/sys/dict"})
public class SysDictController extends AbstractController {
    @Autowired
    private SysDictService sysDictService;

    public SysDictController() {
    }


    @RequestMapping({"/list"})
    @RequiresPermissions({"sys:dict:list"})
    public R list(HttpServletRequest request, HttpServletResponse response,String ttype, Integer page, Integer limit) {
        HashMap map = new HashMap();
        map.put("ttype", ttype);
        map.put("offset", Integer.valueOf((page.intValue() - 1) * limit.intValue()));
        map.put("limit", limit);
        List configList = this.sysDictService.queryList(map);
        int total = this.sysDictService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(configList, total, limit.intValue(), page.intValue());
        return R.ok().put("page", pageUtil);
    }

    //接种界面加载初始化时，ajax一次性加载公用的数据，缓存到界面
    @RequestMapping({"/maplist"})
    public R maplist(HttpServletRequest request, HttpServletResponse response) {
        return sysDictService.queryMapList();
    }
    @RequestMapping({"/typeList"})
    public R selectListByType(String type) {
        List configList = this.sysDictService.selectListByType(type);
        return R.ok().put("page", configList);
    }
    @RequestMapping({"/info/{id}"})
    public R info(@PathVariable("id") String id) {
        SysDictEntity config = this.sysDictService.queryObject(id);
        return R.ok().put("dict", config);
    }

    @RequestMapping({"/save"})
    public R save(@RequestBody SysDictEntity dict) {
        this.verifyForm(dict);
        this.sysDictService.save(dict);
        return R.ok();
    }

    @RequestMapping({"/update"})
    public R update(@RequestBody SysDictEntity dict) {
        this.verifyForm(dict);
        this.sysDictService.update(dict);
        return R.ok();
    }

    @RequestMapping({"/delete"})
    public R delete(@RequestBody String[] ids) {
        this.sysDictService.deleteBatch(ids);
        return R.ok();
    }
    @RequestMapping({"/startUsing"})
    //@RequiresPermissions({"sys:dict:startUsing"})
    public R startUsing(@RequestBody String[] ids){
        this.sysDictService.startUsingBatch(ids);
        return R.ok();
    }

    @RequestMapping({"/forbidden"})
    //@RequiresPermissions({"sys:dict:forbidden"})
    public R forbidden(@RequestBody String[] ids){
        this.sysDictService.forbiddenBatch(ids);
        return R.ok();
    }
    private void verifyForm(SysDictEntity dict) {
        if(StringUtils.isBlank(dict.getTtype())) {
            throw new RRException("数据类型不能为空");
        } else if(StringUtils.isBlank(dict.getText())) {
            throw new RRException("数据名称不能为空");
        } else if(StringUtils.isBlank(dict.getValue())) {
            throw new RRException("数据值不能为空");
        }
    }

    /**
     * 省平台下载儿童一次性加载 户籍属性、个案状态和居住属性
     * @param ttype
     * @return
     */
    @RequestMapping("/queryResultTable")
    public R queryResultTable(String ttype){
        return sysDictService.queryResultTable(ttype);
    }
}
