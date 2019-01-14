package io.yfjz.service.provinceplatform;

import io.yfjz.entity.provinceplatform.TChildElsewhereEntity;

import java.util.List;
import java.util.Map;

/**
 * 异地儿童接种信息
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-09-12 11:22:13
 */
public interface TChildElsewhereService {
	
	TChildElsewhereEntity queryObject(String id);
	
	List<TChildElsewhereEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildElsewhereEntity tChildElsewhere);
	
	void update(TChildElsewhereEntity tChildElsewhere);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
