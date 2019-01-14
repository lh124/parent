package io.yfjz.service.child;

import io.yfjz.entity.child.TChildAbnormalEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童异常反应表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */
public interface TChildAbnormalService {
	
	TChildAbnormalEntity queryObject(String id);
	
	List<TChildAbnormalEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildAbnormalEntity tChildAbnormal);
	
	void update(TChildAbnormalEntity tChildAbnormal);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
