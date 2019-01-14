//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;

import java.util.HashMap;
import java.util.List;

import io.yfjz.dao.sys.SysUserRoleDao;
import io.yfjz.service.sys.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    public SysUserRoleServiceImpl() {
    }

    public void saveOrUpdate(String userId, List<String> roleIdList) {
        if(roleIdList.size() != 0) {
            this.sysUserRoleDao.delete(userId);
            HashMap map = new HashMap();
            map.put("userId", userId);
            map.put("roleIdList", roleIdList);
            this.sysUserRoleDao.save(map);
        }
    }

    public List<String> queryRoleIdList(String userId) {
        return this.sysUserRoleDao.queryRoleIdList(userId);
    }

    public void delete(String userId) {
        this.sysUserRoleDao.delete(userId);
    }
}
