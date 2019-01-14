package io.yfjz.service.rule;

import java.util.List;
import java.util.Map;

import io.yfjz.entity.rule.TRuleHivEntity;

/**
 * 母亲感染HIV所生儿童接种规则表
 * @作者：邓召仕
 * 上午9:11:34
 */
public interface TRuleHivService {
	
	TRuleHivEntity queryObject(String id);
	
	List<TRuleHivEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TRuleHivEntity tRuleHiv);
	
	void update(TRuleHivEntity tRuleHiv);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
