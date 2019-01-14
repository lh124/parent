package io.yfjz.dao.rule;

import java.util.List;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.rule.TRulePlanConsultEntity;

/**
 * 儿童应接种计划参照表
 * @作者：邓召仕
 * 下午6:03:45
 */
public interface TRulePlanConsultDao extends BaseDao<TRulePlanConsultEntity> {
	/**
	 * 获取所有参照信息
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @return
	 */
	List<TRulePlanConsultEntity> queryAll();

	/**
	 * 当前月龄可应接种ID
	 * @describe:
	 * @param monthAge
	 * @return java.util.List<java.lang.String>
	 * @author 邓召仕
	 * @date: 2018-08-16  11:49
	 **/
    List<String> queryNowPlanId(Integer monthAge);
}
