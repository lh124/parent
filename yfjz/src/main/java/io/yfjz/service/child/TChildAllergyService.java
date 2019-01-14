package io.yfjz.service.child;

import io.yfjz.entity.child.TChildAllergyEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童过敏表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */
public interface TChildAllergyService {
	
	TChildAllergyEntity queryObject(String id);
	
	List<TChildAllergyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildAllergyEntity tChildAllergy);
	
	void update(TChildAllergyEntity tChildAllergy);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
