//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;

import io.yfjz.controller.AbstractController;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysDepartUserService;
import io.yfjz.service.sys.SysUserRoleService;
import io.yfjz.service.sys.SysUserService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/sys/user"})
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    SysDepartUserService sysDepartUserService;

    public SysUserController() {
    }

    @RequestMapping({"/list"})
    public R list(String username, Integer page, Integer limit) {
        HashMap map = new HashMap();
        map.put("userName", username);
        map.put("offset", Integer.valueOf((page.intValue() - 1) * limit.intValue()));
        map.put("limit", limit);
        List userList = this.sysUserService.queryListGroup(map);
        int total = this.sysUserService.queryListGroupTotal(map);
        PageUtils pageUtil = new PageUtils(userList, total, limit.intValue(), page.intValue());
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping({"/info"})
    public R info() {
        return R.ok().put("user", this.getUser());
    }

    @RequestMapping({"/password"})
    public R password(String password, String newPassword) {
        if(StringUtils.isBlank(newPassword)) {
            return R.error("新密码不为能空");
        } else {
            password = (new Sha256Hash(password)).toHex();
            newPassword = (new Sha256Hash(newPassword)).toHex();
            int count = this.sysUserService.updatePassword(this.getUserId(), password, newPassword);
            if(count == 0) {
                return R.error("原密码不正确");
            } else {
                ShiroUtils.logout();
                return R.ok();
            }
        }
    }

    @RequestMapping({"/info/{userId}"})
    public R info(@PathVariable("userId") String userId) {
        SysUserEntity user = this.sysUserService.queryObject(userId);
        List roleIdList = this.sysUserRoleService.queryRoleIdList(userId);
        user.setPassword(null);//不要将密码返回到界面，防止泄露
        user.setRoleIdList(roleIdList);
        return R.ok().put("user", user);
    }

    @RequestMapping({"/save"})
    public R save(@RequestBody SysUserEntity user) {
        if(StringUtils.isBlank(user.getUsername())) {
            return R.error("用户名不能为空");
        } else if(StringUtils.isBlank(user.getPassword())) {
            return R.error("密码不能为空");
        }/* else if(StringUtils.isBlank(user.getOrgId())){
            return R.error("系统机构不能为空");
        }*/else {
            this.sysUserService.save(user);
            return R.ok();
        }
    }

    @RequestMapping({"/update"})
    public R update(@RequestBody SysUserEntity user) {
        if(StringUtils.isBlank(user.getUsername())) {
            return R.error("用户名不能为空");
        } else {
            this.sysUserService.update(user);
            return R.ok();
        }
    }

    @RequestMapping({"/bindingDepart"})
    public R bindingDepart(@RequestBody SysUserEntity user) {
        try {
            this.sysDepartUserService.addRelationShip(user);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @RequestMapping({"/delete"})
    public R delete(@RequestBody String[] userIds) {
        if(ArrayUtils.contains(userIds, Long.valueOf(1L))) {
            return R.error("系统管理员不能删除");
        } else if(ArrayUtils.contains(userIds, this.getUserId())) {
            return R.error("当前用户不能删除");
        } else {
            this.sysUserService.deleteBatch(userIds);
            return R.ok();
        }
    }
    @RequestMapping({"/stop"})
    public R stopUser(@RequestBody Map param){
        String[] userIds = {(String)param.get("userIds")};
        Integer status = Integer.valueOf((String) param.get("status"));
        if(ArrayUtils.contains(userIds, Long.valueOf(1L)) && status == -1) {
            return R.error("系统管理员不能停用");
        } else if(ArrayUtils.contains(userIds, this.getUserId()) && status == -1) {
            return R.error("当前用户不能停用");
        } else {
            this.sysUserService.stopUser(userIds,status);
            return R.ok();
        }
    }
}
