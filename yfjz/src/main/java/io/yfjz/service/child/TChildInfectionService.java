package io.yfjz.service.child;

import io.yfjz.entity.child.TChildInfectionEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童传染病表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:37:19
 */
public interface TChildInfectionService {
	
	TChildInfectionEntity queryObject(String id);
	
	List<TChildInfectionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildInfectionEntity tChildInfection);
	
	void update(TChildInfectionEntity tChildInfection);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
