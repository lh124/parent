package io.yfjz.dao.sys;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.sys.SysDepartUserEntity;
import io.yfjz.entity.sys.SysUserEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-17 13:57:20
 */
public interface SysDepartUserDao extends BaseDao<SysDepartUserEntity> {

    int countByDepartIdAndUserId(SysDepartUserEntity sysDepartUserEntity);

    /**
     * @method_name: queryOrgInfoByUserId
     * @describe: 根据登录用户编码查询机构编码，名称
     * @param: [userId]
     * @return: io.yfjz.entity.sys.SysUserEntity
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/31  16:30
     **/
    SysUserEntity queryOrgInfoByUserId(String userId);

    /**
     * 平台 根据登录用户编码查询机构编码，名称
     * @param userId
     * @return
     */
    SysDepartUserEntity queryPlatformOrgInfoByUserId(String userId);

    List<SysDepartUserEntity> getDepartList(String userId);

    /**
     * @method_name: removeByUserId
     * @describe: 根据用户编码删除关联关系 机构与用户的关联关系
     * @param: [userId]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/6  13:20
     **/
    void removeByUserId(String userId);
}
