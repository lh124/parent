package io.yfjz.service.rule;

import java.util.List;
import java.util.Map;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanConsultEntity;

/**
 * 儿童应接种计划参照表
 * @作者：邓召仕
 * 上午9:12:23
 */
public interface TRulePlanConsultService {
	
	TRulePlanConsultEntity queryObject(String id);
	
	List<TRulePlanConsultEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TRulePlanConsultEntity tRulePlanConsult);
	
	void update(TRulePlanConsultEntity tRulePlanConsult);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	List<TRulePlanConsultEntity> findAll();
	
	/**
	 * 根据儿童信息获取当前规划信息
	 * @作者：邓召仕
	 * 2018年7月30日
	 * @param childData 儿童信息
	 * @return
	 */
	List<TRulePlanConsultEntity> getNowPlanList(ChildData childData, List<TRulePlanConsultEntity> planConsults);
/**
 * @method_name: 批量保存规划参照信息到计划表
 * @describe:
 * @param [plans]
 * @return void
 * @author 邓召仕
 * @date: 2018-08-06  16:50
 **/
	void batchSaveConsultToPlan(List<TRulePlanConsultEntity> plans);

	/**
	 * 根据儿童月龄，获取儿童当前该接种的规划疫苗ID
	 * @describe:
	 * @param monthAge 儿童月龄
	 * @return java.util.List<java.lang.String>
	 * @author 邓召仕
	 * @date: 2018-08-16  11:45
	 **/
	List<String> getClassIdsByMonth(Integer monthAge);
}
