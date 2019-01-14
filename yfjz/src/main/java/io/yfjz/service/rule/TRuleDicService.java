package io.yfjz.service.rule;

import java.util.List;
import java.util.Map;

import io.yfjz.entity.rule.TRuleDicEntity;

/**
 * 免疫规划字典表
 * @作者：邓召仕
 * 上午9:11:07
 */
public interface TRuleDicService {

	TRuleDicEntity queryObject(String id);

	List<TRuleDicEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(TRuleDicEntity tRuleDic);

	void update(TRuleDicEntity tRuleDic);

	void delete(String id);

	void deleteBatch(String[] ids);
}
