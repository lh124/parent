//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;

import io.yfjz.controller.AbstractController;
import io.yfjz.entity.sys.SysRoleEntity;
import io.yfjz.service.sys.SysRoleMenuService;
import io.yfjz.service.sys.SysRoleService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
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
@RequestMapping({"/sys/role"})
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    public SysRoleController() {
    }

    @RequestMapping({"/list"})
    public R list(String roleName, Integer page, Integer limit) {
        HashMap map = new HashMap();
        map.put("roleName", roleName);
        map.put("offset", Integer.valueOf((page.intValue() - 1) * limit.intValue()));
        map.put("limit", limit);
        List list = this.sysRoleService.queryList(map);//这个是根据条件查询所有数据,取offset,limit之间的数据
        int total = this.sysRoleService.queryTotal(map);//这个是根据条件查询所有数据
        PageUtils pageUtil = new PageUtils(list, total, limit.intValue(), page.intValue());
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping({"/select"})
    public R select() {
        List list = this.sysRoleService.queryList(new HashMap());
        return R.ok().put("list", list);
    }

    @RequestMapping({"/info/{roleId}"})
    public R info(@PathVariable("roleId") String roleId) {
        SysRoleEntity role = this.sysRoleService.queryObject(roleId);
        List menuIdList = this.sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        return R.ok().put("role", role);
    }

    @RequestMapping({"/save"})
    public R save(@RequestBody SysRoleEntity role) {
        if(StringUtils.isBlank(role.getRoleName())) {
            return R.error("角色名称不能为空");
        } else {
            this.sysRoleService.save(role);
            return R.ok();
        }
    }

    @RequestMapping({"/update"})
    public R update(@RequestBody SysRoleEntity role) {
        if(StringUtils.isBlank(role.getRoleName())) {
            return R.error("角色名称不能为空");
        } else {
            this.sysRoleService.update(role);
            return R.ok();
        }
    }

    @RequestMapping({"/delete"})
    public R delete(@RequestBody String[] roleIds) {
        this.sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }
}
