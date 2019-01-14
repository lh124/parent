//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys;

import io.yfjz.entity.sys.SysRoleEntity;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    SysRoleEntity queryObject(String var1);

    List<SysRoleEntity> queryList(Map<String, Object> var1);

    int queryTotal(Map<String, Object> var1);

    void save(SysRoleEntity var1);

    void update(SysRoleEntity var1);

    void deleteBatch(String[] var1);
}
