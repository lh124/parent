package io.yfjz.service.rule;

import java.util.List;
import java.util.Map;

import io.yfjz.entity.rule.TRuleIntervalEntity;
import io.yfjz.entity.rule.TRulePlanConsultEntity;

/**
 * 计划针剂间隔表
 * 
 * @作者：邓召仕 上午9:11:53
 */
public interface TRuleIntervalService {

	TRuleIntervalEntity queryObject(String id);

	List<TRuleIntervalEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(TRuleIntervalEntity tRuleInterval);

	void update(TRuleIntervalEntity tRuleInterval);

	void delete(String id);

	void deleteBatch(String[] ids);
	
	/**
	 * 计算接种状态是否间短
	 * @作者：邓召仕
	 * 2018年7月30日
	 * @param planConsults 儿童规划接种信息id替换为参照ID
	 * @param plan 需要计算的剂次
	 * @return 
	 */
	boolean isShort(List<TRulePlanConsultEntity> planConsults,TRulePlanConsultEntity plan);

	/**
	 * @method_name: calculatePlanState 计算接种规划信息的接种评价
	 * @describe:
	 * @param [planConsults, consult]
	 * @return java.lang.Integer
	 * @author 邓召仕
	 * @date: 2018-08-01  16:22
	 **/
    Integer calculatePlanState(List<TRulePlanConsultEntity> planConsults, TRulePlanConsultEntity consult);
}
