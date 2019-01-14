//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.dao.sys;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.sys.SysMenuEntity;

import java.util.List;


public interface SysMenuDao extends BaseDao<SysMenuEntity> {
    List<SysMenuEntity> queryListParentId(String var1);

    List<SysMenuEntity> queryNotButtonList();
}
