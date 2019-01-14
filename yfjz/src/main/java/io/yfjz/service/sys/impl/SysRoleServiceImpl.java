//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import io.yfjz.dao.sys.SysRoleDao;
import io.yfjz.entity.sys.SysRoleEntity;
import io.yfjz.service.sys.SysRoleMenuService;
import io.yfjz.service.sys.SysRoleService;
import io.yfjz.service.sys.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    public SysRoleServiceImpl() {
    }

    public SysRoleEntity queryObject(String roleId) {
        return (SysRoleEntity)this.sysRoleDao.queryObject(roleId);
    }

    public List<SysRoleEntity> queryList(Map<String, Object> map) {
        return this.sysRoleDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return this.sysRoleDao.queryTotal(map);
    }

    @Transactional
    public void save(SysRoleEntity role) {
        role.setCreateTime(new Date());
        this.sysRoleDao.save(role);
        this.sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Transactional
    public void update(SysRoleEntity role) {
        this.sysRoleDao.update(role);
        this.sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Transactional
    public void deleteBatch(String[] roleIds) {
        this.sysRoleDao.deleteBatch(roleIds);
    }
}
