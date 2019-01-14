//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.dao.sys;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.sys.SysUserRoleEntity;

import java.util.List;


public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {
    List<String> queryRoleIdList(String var1);
}
