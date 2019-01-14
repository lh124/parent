package io.yfjz.service.sys;


import io.yfjz.entity.sys.SysDepartUserEntity;
import io.yfjz.entity.sys.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-17 13:57:20
 */
public interface SysDepartUserService {
	
	SysDepartUserEntity queryObject(String id);
	
	List<SysDepartUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysDepartUserEntity sysDepartUser);
	
	void update(SysDepartUserEntity sysDepartUser);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	void addRelationShip(SysUserEntity user);

    /**
     * 一个用户对应多个机构
     * @param user
     */
    void addRelationShips(SysUserEntity user);

	/**
	 * @method_name: queryOrgInfoByUserId
	 * @describe: 根据登录用户编码查询机构编码，名称
	 * @param: [userId]
	 * @return: io.yfjz.entity.SysUserEntity
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/31  16:27
	 **/
    SysUserEntity queryOrgInfoByUserId(String userId);

    /**
     * 根据用户编号查询角色信息
     * @param userId
     * @return
     */
    List<SysDepartUserEntity> getDepartList(String userId);
}
