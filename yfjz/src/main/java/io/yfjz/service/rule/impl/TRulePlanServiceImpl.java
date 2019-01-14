package io.yfjz.service.rule.impl;

import java.util.*;

import io.yfjz.entity.rule.TRulePlanConsultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yfjz.dao.rule.TRulePlanDao;
import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.service.rule.TRulePlanService;



/**
 * 每位儿童应接种计划保存，根据国家免疫规划表
 * @作者：邓召仕
 * 上午9:14:30
 */
@Service("tRulePlanService")
public class TRulePlanServiceImpl implements TRulePlanService {
	@Autowired
	private TRulePlanDao tRulePlanDao;
	
	@Override
	public TRulePlanEntity queryObject(String id){
		return tRulePlanDao.queryObject(id);
	}
	
	@Override
	public List<TRulePlanEntity> queryList(Map<String, Object> map){
		return tRulePlanDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tRulePlanDao.queryTotal(map);
	}
	
	@Override
	public void save(TRulePlanEntity tRulePlan){
		tRulePlanDao.save(tRulePlan);
	}
	
	@Override
	public void update(TRulePlanEntity tRulePlan){
		tRulePlanDao.update(tRulePlan);
	}
	
	@Override
	public void delete(String id){
		tRulePlanDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tRulePlanDao.deleteBatch(ids);
	}

	@Override
	public List<TRulePlanEntity> queryShouldButNot(String childId) {
		TRulePlanEntity plan = new TRulePlanEntity();
		plan.setChildId(childId);
		plan.setFirstTime(new Date());
		plan.setLastTime(new Date());
		plan.setState(0);
		return tRulePlanDao.queryListByCondition(plan);
	}

	@Override
	public List<TRulePlanEntity> queryShouldButNot(String childId, Date selectDate) {
		TRulePlanEntity plan = new TRulePlanEntity();
		plan.setChildId(childId);
		plan.setFirstTime(selectDate);
		plan.setLastTime(selectDate);
		plan.setState(0);
		return tRulePlanDao.queryListByCondition(plan);
	}

	@Override
	public List<TRulePlanEntity> queryQualifiedButNot(String childId) {
		TRulePlanEntity plan = new TRulePlanEntity();
		plan.setChildId(childId);
		plan.setFirstTime(new Date());
		plan.setQualifiledTime(new Date());
		plan.setState(0);
		return tRulePlanDao.queryListByCondition(plan);
	}

	@Override
	public List<TRulePlanEntity> queryQualifiedButNot(String childId, Date selectDate) {
		TRulePlanEntity plan = new TRulePlanEntity();
		plan.setChildId(childId);
		plan.setFirstTime(selectDate);
		plan.setQualifiledTime(selectDate);
		plan.setState(0);
		return tRulePlanDao.queryListByCondition(plan);
	}

	@Override
	public List<TRulePlanConsultEntity> getAllPlanByChild(String childCode) {
		return tRulePlanDao.queryListByChildCode(childCode);
	}

	@Override
	public TRulePlanConsultEntity getPlanByChildBio(String childCode, String bioCode) {
		Map<String,Object> param = new HashMap<>();
		param.put("childCode",childCode);
		param.put("bioCode",bioCode);
		return tRulePlanDao.queryPlanByChildBio(param);
	}

	@Override
	public List<String> getLaterBios(String childCode) {
		return tRulePlanDao.queryLaterBios(childCode);
	}

	@Override
	public List<TRulePlanEntity> getShouldAndQualified(String selectType, String chilCode, Integer monthLimit, Date selectDate, String[] biotypes) {
		Map<String,Object> param = new HashMap<>();
		param.put("selectType",selectType);
		param.put("childId",chilCode);

		param.put("selectDate",selectDate);
		if (biotypes != null){
			param.put("bios",biotypes);
		}
		if (monthLimit != null &&monthLimit != 0) {
			//月限
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(selectDate);
			calendar.add(Calendar.MONTH, - monthLimit);
			param.put("monthtime",calendar.getTime());
		}
		return tRulePlanDao.queryNotInoByCondition(param);
	}

	@Override
	public List<TRulePlanConsultEntity> getInoPlansByChild(String childCode) {
		return tRulePlanDao.queryInoPlansByChild(childCode);
	}

	@Override
	public List<TRulePlanEntity> getNoInoPlansByChild(String childCode) {
		return tRulePlanDao.queryNoInoPlansByChild(childCode);
	}

	@Override
	public void saveBatch(List<TRulePlanEntity> plans) {
		if(plans != null && !plans.isEmpty()){
			tRulePlanDao.saveBatch(plans);
		}
		
	}
	
}
