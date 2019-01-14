package io.yfjz.service.child;

import io.yfjz.entity.child.TChildHealthcareEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童体检记录表
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-24 15:22:38
 */
public interface TChildHealthcareService {
	
	TChildHealthcareEntity queryObject(String id);
	
	List<TChildHealthcareEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildHealthcareEntity tChildHealthcare);
	
	void update(TChildHealthcareEntity tChildHealthcare);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	List<TChildHealthcareEntity> healthcarelists();
}
