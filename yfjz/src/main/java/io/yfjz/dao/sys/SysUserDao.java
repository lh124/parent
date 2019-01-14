//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.dao.sys;

import com.github.pagehelper.Page;
import io.yfjz.dao.BaseDao;
import io.yfjz.entity.sys.SysUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysUserDao extends BaseDao<SysUserEntity> {
    List<String> queryAllPerms(String var1);

    List<String> queryAllMenuId(String var1);

    List<SysUserEntity> queryByUserId(Map<String,Object> map);

    SysUserEntity queryByUserName(String var1);

    int updatePassword(Map<String, Object> var1);


    int queryListGroupTotal(Map<String, Object> var1);

    Page<List<SysUserEntity>> queryListGroup(@Param("userName") String username, @Param("orgId") String orgId, @Param("type") Integer type);

    List<SysUserEntity>  queryListGroup(Map map);



    //门诊
    // 获取用户所拥有的角色列表
    List<String> getLoginUserRoleName(@Param("userId") String userId);
}
