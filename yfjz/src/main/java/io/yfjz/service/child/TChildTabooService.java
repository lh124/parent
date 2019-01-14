package io.yfjz.service.child;

import io.yfjz.entity.child.TChildTabooEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童禁忌表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:47:59
 */
public interface TChildTabooService {
	
	TChildTabooEntity queryObject(String id);
	
	List<TChildTabooEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildTabooEntity tChildTaboo);
	
	void update(TChildTabooEntity tChildTaboo);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
