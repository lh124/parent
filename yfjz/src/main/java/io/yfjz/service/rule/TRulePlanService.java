package io.yfjz.service.rule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.entity.rule.TRulePlanEntity;

/**
 * 每位儿童应接种计划保存，根据国家免疫规划表
 * @作者：邓召仕
 * 上午9:12:42
 */
public interface TRulePlanService {
	
	TRulePlanEntity queryObject(String id);
	
	List<TRulePlanEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TRulePlanEntity tRulePlan);
	
	void update(TRulePlanEntity tRulePlan);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	/**
	 * 批量保存儿童计划数据
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @param plans
	 */
	void saveBatch(List<TRulePlanEntity> plans);
	/**
	 * 查询儿童当前系统时间的应种未种(包含逾期未中)
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @param childId
	 * @return
	 */
	List<TRulePlanEntity> queryShouldButNot(String childId);
	/**
	 * 根据时间查询应种未中(包含逾期未中)
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @param childId
	 * @param selectDate
	 * @return
	 */
	List<TRulePlanEntity> queryShouldButNot(String childId,Date selectDate);
	/**
	 * 当前时间处在合格期内未种的（老版应种未中）
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @param childId
	 * @return
	 */
	List<TRulePlanEntity> queryQualifiedButNot(String childId);
	/**
	 * 查询时间处在合格期内未种的（老版应种未中）
	 * @作者：邓召仕
	 * 2018年7月24日
	 * @param childId
	 * @param selectDate
	 * @return
	 */
	List<TRulePlanEntity> queryQualifiedButNot(String childId,Date selectDate);

	/**
	 * 根据儿童编码获取该儿童所有规划信息
	 * @describe:
	 * @param childCode
	 * @return java.util.List<io.yfjz.entity.rule.TRulePlanConsultEntity>
	 * @author 邓召仕
	 * @date: 2018-08-16  13:35
	 **/
	List<TRulePlanConsultEntity> getAllPlanByChild(String childCode);

	/**
	 * 根据儿童的ID，疫苗ID获该儿童当前因被替代的规划剂次
	 * @describe:
	 * @param childCode 儿童编码
	 * @param  bioCode 疫苗编码
	 * @return java.util.List<io.yfjz.entity.rule.TRulePlanConsultEntity>
	 * @author 邓召仕
	 * @date: 2018-08-17  15:02
	 **/
	TRulePlanConsultEntity getPlanByChildBio(String childCode,String bioCode);

	/**
	 * 根据儿童编码获取该儿童当前还没到达接种时间的疫苗编码集合
	 * @describe:
	 * @param childCode 儿童编码
	 * @return java.util.List<java.lang.String>
	 * @author 邓召仕
	 * @date: 2018-08-17  15:48
	 **/
	List<String> getLaterBios(String childCode);

	/**
	 * 查询儿童应种未种或逾期未种信息
	 * @describe:
	 * @param selectType 1、应种未种；2、逾期未种
	 * @param chilCode 儿童编码
	 * @param monthLimit 月限
	 * @param selectDate 查询时间，默认当前
	 * @param biotypes 查询范围
	 * @return java.util.List<io.yfjz.entity.rule.TRulePlanEntity>
	 * @author 邓召仕
	 * @date: 2018-08-22  9:14
	 **/
	List<TRulePlanEntity> getShouldAndQualified(String selectType, String chilCode, Integer monthLimit, Date selectDate, String[] biotypes);

	/**
	 * 根据儿童id获取该儿童当前已经接种了的规划信息
	 * @describe:
	 * @param childCode
	 * @return java.util.List<io.yfjz.entity.rule.TRulePlanConsultEntity>
	 * @author 邓召仕
	 * @date: 2018-09-11  17:51
	 **/
	List<TRulePlanConsultEntity> getInoPlansByChild(String childCode);

	/**
	 * 获取该儿童所有还未接种的信息
	 * @describe:
	 * @param childCode
	 * @return java.util.List<io.yfjz.entity.rule.TRulePlanEntity>
	 * @author 邓召仕
	 * @date: 2018-09-17  10:19
	 **/
	List<TRulePlanEntity> getNoInoPlansByChild(String childCode);
}
