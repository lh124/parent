//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.dao.sys;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.sys.SysRoleMenuEntity;

import java.util.List;


public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {
    List<String> queryMenuIdList(String var1);
}
