//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys;

import java.util.List;

public interface SysUserRoleService {
    void saveOrUpdate(String var1, List<String> var2);

    List<String> queryRoleIdList(String var1);

    void delete(String var1);
}
