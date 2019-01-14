//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys;


import io.yfjz.entity.sys.SysUserEntity;

import java.util.List;
import java.util.Map;

public interface SysUserService {
    List<String> queryAllPerms(String var1);

    List<String> queryAllMenuId(String var1);

    SysUserEntity queryByUserName(String var1);

    SysUserEntity queryObject(String var1);

    List<SysUserEntity> queryList(Map<String, Object> var1);

    int queryTotal(Map<String, Object> var1);

    void save(SysUserEntity var1);

    void update(SysUserEntity var1);

    void deleteBatch(String[] var1);

    void stopUser(String[] userIds,Integer status);

    int updatePassword(String var1, String var2, String var3);

    List<SysUserEntity> queryListGroup(Map<String, Object> var1);
    int queryListGroupTotal(Map<String, Object> var1);

    List<String> getLoginUserRoleName(String userId);
}
