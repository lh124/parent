//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;


import java.util.*;

import io.yfjz.dao.sys.SysUserDao;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysDepartUserService;
import io.yfjz.service.sys.SysUserRoleService;
import io.yfjz.service.sys.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDepartUserService sysDepartUserService;
    public SysUserServiceImpl() {
    }

    public List<String> queryAllPerms(String userId) {
        return this.sysUserDao.queryAllPerms(userId);
    }

    public List<String> queryAllMenuId(String userId) {
        return this.sysUserDao.queryAllMenuId(userId);
    }

    public SysUserEntity queryByUserName(String username) {
        return this.sysUserDao.queryByUserName(username);
    }

    public SysUserEntity queryObject(String userId) {
        return (SysUserEntity)this.sysUserDao.queryObject(userId);
    }

    public List<SysUserEntity> queryList(Map<String, Object> map) {
        return this.sysUserDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return this.sysUserDao.queryTotal(map);
    }

    @Transactional
    public void save(SysUserEntity user) {
        user.setUserId(UUID.randomUUID().toString());//生成编号不在用sql生成编号
        user.setCreateTime(new Date());
        user.setPassword((new Sha256Hash(user.getPassword())).toHex());
        this.sysUserDao.save(user);
        this.sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
        if(!StringUtils.isBlank(user.getOrgId())){
            sysDepartUserService.addRelationShips(user);
        }
    }

    @Transactional
    public void update(SysUserEntity user) {
        if(StringUtils.isBlank(user.getPassword())) {
            user.setPassword((String)null);
        } else {
            user.setPassword((new Sha256Hash(user.getPassword())).toHex());
        }

        this.sysUserDao.update(user);
        this.sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Transactional
    public void deleteBatch(String[] userId) {
        this.sysUserDao.deleteBatch(userId);
    }

    public int updatePassword(String userId, String password, String newPassword) {
        HashMap map = new HashMap();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);
        return this.sysUserDao.updatePassword(map);
    }

    @Override
    public void stopUser(String[] userIds, Integer status) {
        SysUserEntity user = new SysUserEntity();
        user.setUserId(userIds[0]);
        user.setStatus(status);
        sysUserDao.update(user);
    }

    @Override
    public List<SysUserEntity> queryListGroup(Map<String, Object> var1) {
        return sysUserDao.queryListGroup(var1);
    }

    @Override
    public int queryListGroupTotal(Map<String, Object> var1) {
        return sysUserDao.queryListGroupTotal(var1);
    }

    @Override
    public List<String> getLoginUserRoleName(String userId) {
        return sysUserDao.getLoginUserRoleName(userId);
    }
}
