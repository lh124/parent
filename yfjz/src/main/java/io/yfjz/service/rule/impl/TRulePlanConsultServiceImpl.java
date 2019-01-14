package io.yfjz.service.rule.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import io.yfjz.dao.rule.TRulePlanDao;
import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.ChildInoData;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import io.yfjz.dao.rule.TRulePlanConsultDao;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.entity.rule.TRuleReplaceEntity;
import io.yfjz.managerservice.rule.common.cache.RuleCache;
import io.yfjz.service.rule.TRuleIntervalService;
import io.yfjz.service.rule.TRulePlanConsultService;
import io.yfjz.service.rule.TRuleReplaceService;



/**
 * 儿童应接种计划参照表
 * @作者：邓召仕
 * 上午9:14:10
 */
@Service("tRulePlanConsultService")
@Scope("prototype")
public class TRulePlanConsultServiceImpl implements TRulePlanConsultService {
	@Autowired
	private TRulePlanConsultDao tRulePlanConsultDao;
	@Autowired
	private RuleCache ruleCache;
	@Autowired
	private TRuleReplaceService tRuleReplaceService;
	@Autowired
	private TRuleIntervalService tRuleIntervalService;
	@Autowired
	private TRulePlanDao tRulePlanDao;
	
	@Override
	public TRulePlanConsultEntity queryObject(String id){
		return tRulePlanConsultDao.queryObject(id);
	}
	
	@Override
	public List<TRulePlanConsultEntity> queryList(Map<String, Object> map){
		return tRulePlanConsultDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tRulePlanConsultDao.queryTotal(map);
	}
	
	@Override
	public void save(TRulePlanConsultEntity tRulePlanConsult){
		tRulePlanConsultDao.save(tRulePlanConsult);
	}
	
	@Override
	public void update(TRulePlanConsultEntity tRulePlanConsult){
		tRulePlanConsultDao.update(tRulePlanConsult);
	}
	
	@Override
	public void delete(String id){
		tRulePlanConsultDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tRulePlanConsultDao.deleteBatch(ids);
	}

	@Override
	public List<TRulePlanConsultEntity> findAll() {
		return tRulePlanConsultDao.queryAll();
	}

	@Override
	public List<TRulePlanConsultEntity> getNowPlanList(ChildData childData, List<TRulePlanConsultEntity> planConsults) {
		if(childData == null || planConsults == null) return null;//防报错判断
		for(TRulePlanConsultEntity consult : planConsults){
			setPlanConsult(consult,childData);
			//获取该计划剂次替代规则
			Map<String,List<TRuleReplaceEntity>> ruleMap = ruleCache.getReplaceRuleByConsultId(consult.getId());
			if (ruleMap == null){
				continue;
			}
			//if (PlanDicContext.OPV.equals(consult.getClassId())){
				//System.out.println("opv");
			//}
			for(String replaceType : ruleMap.keySet()){
				List<TRuleReplaceEntity> ruleReplaces = ruleMap.get(replaceType);
				if("0".equals(replaceType)){//互斥替代
					ChildInoData inoData = tRuleReplaceService.selectMutexReplaceIno(childData, ruleReplaces);
					if(inoData != null){//存在替代该疫苗的替代信息
						consult.setInoculateId(inoData.getId());
						consult.setInoculateTime(inoData.getInocDate());
						//consult.setState(tRuleIntervalService.calculatePlanState(planConsults, consult));
						break;
					}
				}else{//联合替代
					ChildInoData inoData = tRuleReplaceService.selectJiontlyReplaceIno(childData, ruleReplaces);
					if(inoData != null){//存在替代该疫苗的替代信息
						consult.setInoculateId(inoData.getId());
						consult.setInoculateTime(inoData.getInocDate());
						consult.setState(TRulePlanConsultEntity.STATE_QUALIFILED);//联合替代为合格
						break;
					}
					
				}
			}
		}
		for(TRulePlanConsultEntity stateConsult : planConsults){
			if (stateConsult.getInoculateTime() != null && stateConsult.getState().equals(0)){
				stateConsult.setState(tRuleIntervalService.calculatePlanState(planConsults, stateConsult));
			}
		}
		
		return planConsults;
	}

	@Override
	public void batchSaveConsultToPlan(List<TRulePlanConsultEntity> plans) {
		if (plans == null || plans.isEmpty()) return;
		tRulePlanDao.batchSaveConsultToPlan(plans);
	}

	@Override
	public List<String> getClassIdsByMonth(Integer monthAge) {
		return tRulePlanConsultDao.queryNowPlanId(monthAge);
	}

	/**
	 * 计算儿童接种各剂疫苗的最早、及时、合格时间
	 * 
	 * @author 邓召仕 2018-4-18
	 * @param pc
	 * @param child
	 * @return
	 */
	private TRulePlanConsultEntity setPlanConsult(TRulePlanConsultEntity pc, ChildData child) {
		pc.setChildCode(child.getChilCode());
		Calendar calendar = Calendar.getInstance();
		// 最早接种时间
		calendar.setTime(child.getChilBirthday());
		if (pc.getFirstMonth() > 0) {
			calendar.add(Calendar.MONTH, pc.getFirstMonth());
		}
		pc.setFirstTime(calendar.getTime());
		// 及时时间
		calendar.clear();
		calendar.setTime(child.getChilBirthday());
		if (pc.getTimelyMonth() > 0) {
			calendar.add(Calendar.MONTH, pc.getTimelyMonth());// 加1，存储为最后及时时间
		} else {
			calendar.add(Calendar.DATE, 1);// 乙肝第一剂加1天;
		}
		pc.setTimelyTime(calendar.getTime());
		// 合格时间
		calendar.clear();
		calendar.setTime(child.getChilBirthday());
		if (pc.getQualifiledMonth() > 0) {
			calendar.add(Calendar.MONTH, pc.getQualifiledMonth());// 加1，存储为最后及时时间
		} else {
			calendar.add(Calendar.DATE, 1);// 乙肝第一剂加1天;
		}
		pc.setQualifiledTime(calendar.getTime());
		// 最晚可接种时间
		calendar.clear();
		calendar.setTime(child.getChilBirthday());
		calendar.add(Calendar.MONTH, pc.getLastMonth());
		pc.setLastTime(calendar.getTime());
		return pc;
	}
}
