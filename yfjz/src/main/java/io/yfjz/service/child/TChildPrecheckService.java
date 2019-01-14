package io.yfjz.service.child;

import io.yfjz.entity.child.TChildPrecheckEntity;

import java.util.List;
import java.util.Map;

/**
 * 预检信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-23 09:41:20
 */
public interface TChildPrecheckService {
	
	TChildPrecheckEntity queryObject(String id);
	
	List<TChildPrecheckEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildPrecheckEntity tChildPrecheck);

	void saveCheckNotice(TChildPrecheckEntity tChildPrecheck);

	void update(TChildPrecheckEntity tChildPrecheck);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	List<TChildPrecheckEntity> sumprechecks();
}
