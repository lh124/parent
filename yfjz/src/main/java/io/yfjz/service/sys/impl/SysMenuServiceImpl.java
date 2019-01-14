//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;


import io.yfjz.dao.sys.SysMenuDao;
import io.yfjz.entity.sys.SysMenuEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysMenuService;
import io.yfjz.service.sys.SysRoleMenuService;
import io.yfjz.service.sys.SysUserService;
import io.yfjz.utils.Constant.MenuType;

import java.io.IOException;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    public SysMenuServiceImpl() {
    }

    public List<SysMenuEntity> queryListParentId(String parentId, List<String> menuIdList) {
        List menuList = sysMenuDao.queryListParentId(parentId);
        if(menuIdList == null) {
            return menuList;
        } else {
            ArrayList userMenuList = new ArrayList();
            Iterator var6 = menuList.iterator();

            while(var6.hasNext()) {
                SysMenuEntity menu = (SysMenuEntity)var6.next();
                if(menuIdList.contains(menu.getMenuId())) {
                    userMenuList.add(menu);
                }
            }

            return userMenuList;
        }
    }

    public List<SysMenuEntity> queryNotButtonList() {
        return this.sysMenuDao.queryNotButtonList();
    }
    @Override
    public List<SysMenuEntity> getUserMenuList(String userId) {
        //查询所有的系统菜单
//        List menus =sysUserService.queryAllMenu();
        List<SysMenuEntity> allMenu = getAllMenuList((List) null);
        List<SysMenuEntity> temp = new ArrayList<>();
        for (SysMenuEntity menu : allMenu) {
            try {
                SysMenuEntity o = (SysMenuEntity)menu.deepClone();
                temp.add(o);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        SysUserEntity user = sysUserService.queryObject(userId);
        if(user.getType().intValue()==1) {
            return this.getAllMenuList((List)null);
        } else {
            List menuIdList = sysUserService.queryAllMenuId(userId);
            //查询角色用有的菜单
            List<SysMenuEntity> hasMenu = getAllMenuList(menuIdList);
            //删除用户没有的菜单url
            for (SysMenuEntity menu : temp) {
                for (SysMenuEntity allItem : menu.getList()) {
                    for (SysMenuEntity has : hasMenu) {
                        for (SysMenuEntity  hasItem: has.getList()) {
                            if (menu.getName().equals(has.getName())&&allItem.getName().equals(hasItem.getName())) {
                                allItem.setHasStatus(1);
                                break;
                            }
                        }

                    }
                }
            }
            for (SysMenuEntity menu : temp) {
                for (SysMenuEntity item : menu.getList()) {
                    if (item.getHasStatus()==0){
                        item.setUrl("");
                    }
                }
            }
        }
        return temp;
    }

    public SysMenuEntity queryObject(String menuId) {
        return (SysMenuEntity)this.sysMenuDao.queryObject(menuId);
    }

    public List<SysMenuEntity> queryList(Map<String, Object> map) {
        return this.sysMenuDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return this.sysMenuDao.queryTotal(map);
    }

    public void save(SysMenuEntity menu) {
        this.sysMenuDao.save(menu);
    }

    public void update(SysMenuEntity menu) {
        this.sysMenuDao.update(menu);
    }

    @Transactional
    public void deleteBatch(String[] menuIds) {
        this.sysMenuDao.deleteBatch(menuIds);
    }

    private List<SysMenuEntity> getAllMenuList(List<String> menuIdList) {
        List menuList = queryListParentId("7ccf4b5f-8bd0-11e8-aec8-54e1ad773d4d", menuIdList);
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<String> menuIdList) {
        ArrayList subMenuList = new ArrayList();

        SysMenuEntity entity;
        for(Iterator var5 = menuList.iterator(); var5.hasNext(); subMenuList.add(entity)) {
            entity = (SysMenuEntity)var5.next();
            if(entity.getType().intValue() == MenuType.CATALOG.getValue()) {
                List list =getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList);
                List aList = new ArrayList();
                aList.addAll(list);
                entity.setList(aList);
            }
        }

        return subMenuList;
    }
}
