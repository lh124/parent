package io.yfjz.service.inocpointmgr;

import io.yfjz.entity.mgr.TBaseOrgInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 卫生院基本信息表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-09-04 17:03:45
 */
public interface TBaseOrgInfoService {
	
	TBaseOrgInfoEntity queryObject(String id);
	
	List<TBaseOrgInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TBaseOrgInfoEntity tBaseOrgInfo);
	
	void update(TBaseOrgInfoEntity tBaseOrgInfo);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
