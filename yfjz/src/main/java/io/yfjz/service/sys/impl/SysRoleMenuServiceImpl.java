//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys.impl;

import java.util.HashMap;
import java.util.List;

import io.yfjz.dao.sys.SysRoleMenuDao;
import io.yfjz.service.sys.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    public SysRoleMenuServiceImpl() {
    }

    @Transactional
    public void saveOrUpdate(String roleId, List<String> menuIdList) {
        if(menuIdList.size() != 0) {
            this.sysRoleMenuDao.delete(roleId);
            HashMap map = new HashMap();
            map.put("roleId", roleId);
            map.put("menuIdList", menuIdList);
            this.sysRoleMenuDao.save(map);
        }
    }

    public List<String> queryMenuIdList(String roleId) {
        return this.sysRoleMenuDao.queryMenuIdList(roleId);
    }
}
