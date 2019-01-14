//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.service.sys;

import io.yfjz.entity.sys.SysMenuEntity;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    List<SysMenuEntity> queryListParentId(String var1, List<String> var2);

    List<SysMenuEntity> queryNotButtonList();

    List<SysMenuEntity> getUserMenuList(String var1);

    SysMenuEntity queryObject(String var1);

    List<SysMenuEntity> queryList(Map<String, Object> var1);

    int queryTotal(Map<String, Object> var1);

    void save(SysMenuEntity var1);

    void update(SysMenuEntity var1);

    void deleteBatch(String[] var1);
}
