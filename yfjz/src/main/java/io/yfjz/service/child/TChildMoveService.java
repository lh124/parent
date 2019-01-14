package io.yfjz.service.child;

import io.yfjz.entity.child.TChildMoveEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童迁移记录表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 15:56:11
 */
public interface TChildMoveService {
	
	TChildMoveEntity queryObject(String id);
	
	List<TChildMoveEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildMoveEntity tChildMove);
	
	void update(TChildMoveEntity tChildMove);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
