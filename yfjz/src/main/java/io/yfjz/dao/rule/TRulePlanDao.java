package io.yfjz.dao.rule;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.entity.rule.TRulePlanEntity;

/**
 * 每位儿童应接种计划保存，根据国家免疫规划表
 * @作者：邓召仕
 * 下午6:04:16
 */
public interface TRulePlanDao extends BaseDao<TRulePlanEntity> {
	/**
	 * 快速清空计划表
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @return
	 */
	int truncate();
	/**
	 * 根据儿童ID即code删除该儿童计划
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @param childId
	 * @return
	 */
	int deleteByChildId(String childId);
	/**
	 * 查询条件不为空时：儿童ID等常规条件取相等，最早接种时间取小于等于，其他时间取大于等于
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @param plan
	 * @return
	 */
	List<TRulePlanEntity> queryListByCondition(TRulePlanEntity plan);
	
	/**
	 * @method_name: 批量保存规划好的参照信息到儿童规划表
	 * @describe:
	 * @param list
	 * @return int
	 * @author 邓召仕
	 * @date: 2018-08-02  10:16
	 **/
	int batchSaveConsultToPlan(List<TRulePlanConsultEntity> list);

	List<TRulePlanConsultEntity> queryListByChildCode(String childCode);

	/**
	 * 查询该儿童当前能被该疫苗替代的规划剂次
	 * @describe:
	 * @param param
	 * @return io.yfjz.entity.rule.TRulePlanConsultEntity
	 * @author 邓召仕
	 * @date: 2018-08-17  15:08
	 **/
    TRulePlanConsultEntity queryPlanByChildBio(Map<String, Object> param);

    /**
	 * 当前未到达接种时间的疫苗ID集合
	 * @describe:
	 * @param childCode
	 * @return java.util.List<java.lang.String>
	 * @author 邓召仕
	 * @date: 2018-08-17  15:50
	 **/
	List<String> queryLaterBios(String childCode);

	/**
	 * 根据条件查询应种未种或逾期未种
	 * @describe:
	 * @param param 查询条件
	 * @return java.util.List<io.yfjz.entity.rule.TRulePlanEntity>
	 * @author 邓召仕
	 * @date: 2018-08-21  17:30
	 **/
	List<TRulePlanEntity> queryNotInoByCondition(Map<String, Object> param);

	/**
	 * 获取儿童已经接种的规划信息
	 * @describe:
	 * @param childCode
	 * @return java.util.List<io.yfjz.entity.rule.TRulePlanConsultEntity>
	 * @author 邓召仕
	 * @date: 2018-09-11  17:55
	 **/
    List<TRulePlanConsultEntity> queryInoPlansByChild(String childCode);

    /**
	 * 获取儿童还未接种的全部规划信息
	 * @describe:
	 * @param childCode
	 * @return java.util.List<io.yfjz.entity.rule.TRulePlanEntity>
	 * @author 邓召仕
	 * @date: 2018-09-17  10:23
	 **/
	List<TRulePlanEntity> queryNoInoPlansByChild(String childCode);
}
