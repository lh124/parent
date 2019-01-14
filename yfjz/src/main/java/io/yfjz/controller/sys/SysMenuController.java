//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.controller.sys;

import io.yfjz.controller.AbstractController;
import io.yfjz.entity.sys.SysMenuEntity;
import io.yfjz.service.sys.SysMenuService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import io.yfjz.utils.Constant.MenuType;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/sys/menu"})
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;

    public SysMenuController() {
    }

    @RequestMapping({"/list"})
    @RequiresPermissions({"sys:menu:list"})
    public R list(Integer page, Integer limit,String text) {
        HashMap map = new HashMap();
        map.put("offset", Integer.valueOf((page.intValue() - 1) * limit.intValue()));
        map.put("limit", limit);
        map.put("name", text);
        List menuList = this.sysMenuService.queryList(map);
        int total = this.sysMenuService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(menuList, total, limit.intValue(), page.intValue());
        return R.ok().put("page", pageUtil);
    }

    @RequestMapping({"/select"})
    @RequiresPermissions({"sys:menu:select"})
    public R select() {
        List menuList = this.sysMenuService.queryNotButtonList();
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(UUID.randomUUID().toString());
        root.setName("一级菜单");
        root.setOpen(Boolean.valueOf(true));
        menuList.add(root);
        return R.ok().put("menuList", menuList);
    }

    @RequestMapping({"/perms"})
    @RequiresPermissions({"sys:menu:perms"})
    public R perms() {
        List menuList = this.sysMenuService.queryList(new HashMap());
        return R.ok().put("menuList", menuList);
    }

    @RequestMapping({"/info/{menuId}"})
    @RequiresPermissions({"sys:menu:info"})
    public R info(@PathVariable("menuId") String menuId) {
        SysMenuEntity menu = this.sysMenuService.queryObject(menuId);
        return R.ok().put("menu", menu);
    }

    @RequestMapping({"/save"})
    @RequiresPermissions({"sys:menu:save"})
    public R save(@RequestBody SysMenuEntity menu) {
        this.verifyForm(menu);
        this.sysMenuService.save(menu);
        return R.ok();
    }

    @RequestMapping({"/update"})
    @RequiresPermissions({"sys:menu:update"})
    public R update(@RequestBody SysMenuEntity menu) {
        this.verifyForm(menu);
        this.sysMenuService.update(menu);
        return R.ok();
    }

    @RequestMapping({"/delete"})
    @RequiresPermissions({"sys:menu:delete"})
    public R delete(@RequestBody String[] menuIds) {
        this.sysMenuService.deleteBatch(menuIds);
        return R.ok();
    }

    @RequestMapping({"/user"})
    public R user() {
        List menuList = this.sysMenuService.getUserMenuList(this.getUserId());
        return R.ok().put("menuList", menuList);
    }

    private void verifyForm(SysMenuEntity menu) {
        if(StringUtils.isBlank(menu.getName())) {
            throw new RRException("菜单名称不能为空");
        } else if(menu.getParentId() == null) {
            throw new RRException("上级菜单不能为空");
        } else if(menu.getType() == MenuType.MENU.getValue() && StringUtils.isBlank(menu.getUrl())) {
            throw new RRException("菜单URL不能为空");
        } else {
            int parentType = MenuType.CATALOG.getValue();
            if(!menu.getParentId().equalsIgnoreCase("7ccf4b5f-8bd0-11e8-aec8-54e1ad773d4d")) {
                SysMenuEntity parentMenu = this.sysMenuService.queryObject(menu.getParentId());
                if(parentMenu != null){
                    parentType = parentMenu.getType();
                }
            }

            if(menu.getType() != MenuType.CATALOG.getValue() && menu.getType() != MenuType.MENU.getValue()) {
                if(menu.getType() == MenuType.BUTTON.getValue()) {
                    if(parentType != MenuType.MENU.getValue()) {
                        throw new RRException("上级菜单只能为菜单类型");
                    }
                }
            } else if(parentType != MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
        }
    }
}
