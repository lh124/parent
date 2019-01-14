package io.yfjz.service.child;

import io.yfjz.entity.child.TBasePositionEntity;

import java.util.List;
import java.util.Map;

/**
 * 省市县镇村数据
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-30 13:41:54
 */
public interface TBasePositionService {
	
	TBasePositionEntity queryObject(String id);
	
	List<TBasePositionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TBasePositionEntity tBasePosition);
	
	void update(TBasePositionEntity tBasePosition);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	List<Map<String,Object>> queryprovince(Map<String, Object> map);

	List<Map<String,Object>> querycity(Map<String, Object> map);

	List<Map<String,Object>> querycounty(Map<String, Object> map);

	List<Map<String,Object>> querytown(Map<String, Object> map);

	List<Map<String,Object>> gethospital(Map<String, Object> map);
}
