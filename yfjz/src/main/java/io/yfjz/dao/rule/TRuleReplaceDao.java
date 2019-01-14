package io.yfjz.dao.rule;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.rule.TRuleReplaceEntity;

/**
 * 接种规划映射关系表
 * @作者：邓召仕
 * 下午6:04:38
 */
public interface TRuleReplaceDao extends BaseDao<TRuleReplaceEntity> {
	List<String> selectNotInoculateBioId(int moonAge);

	/**
     * 根据条件查询当前月龄的替代苗
     * @describe:
     * @author 邓召仕
     * @date: 2018-08-15  13:42
     **/
    List<String> selectReplaceCode(Map<String, Object> pamarMap);
}
