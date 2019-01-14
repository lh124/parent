package io.yfjz.service.sys;

import io.yfjz.entity.sys.SysDepartEntity;

import java.util.List;
import java.util.Map;

/**
 * 机构、部门信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-16 23:03:13
 */
public interface SysDepartService {
	
	SysDepartEntity queryObject(String id);
	
	List<SysDepartEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysDepartEntity sysDepart);
	
	void update(SysDepartEntity sysDepart);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 * 异步加载机构树
	 * @param map
	 * @return
	 */
	List<SysDepartEntity> getAsyncDepartMenuTree(Map<String, Object> map);

	void updateStatus(String[] ids);

	 List<SysDepartEntity>queryDepart(String orgId);

	List<SysDepartEntity> queryListDepart(Map<String, Object> map);
}
