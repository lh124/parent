package io.yfjz.service.rule.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import io.yfjz.managerservice.rule.common.PlanDicContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yfjz.dao.rule.TRuleIntervalDao;
import io.yfjz.entity.rule.TRuleIntervalEntity;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.managerservice.rule.common.cache.RuleCache;
import io.yfjz.service.rule.TRuleIntervalService;



/**
 * 计划针剂间隔表
 * @作者：邓召仕
 * 上午9:13:55
 */
@Service("tRuleIntervalService")
public class TRuleIntervalServiceImpl implements TRuleIntervalService {
	@Autowired
	private TRuleIntervalDao tRuleIntervalDao;
	@Autowired
	private RuleCache ruleCache;
	
	@Override
	public TRuleIntervalEntity queryObject(String id){
		return tRuleIntervalDao.queryObject(id);
	}
	
	@Override
	public List<TRuleIntervalEntity> queryList(Map<String, Object> map){
		return tRuleIntervalDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tRuleIntervalDao.queryTotal(map);
	}
	
	@Override
	public void save(TRuleIntervalEntity tRuleInterval){
		tRuleIntervalDao.save(tRuleInterval);
	}
	
	@Override
	public void update(TRuleIntervalEntity tRuleInterval){
		tRuleIntervalDao.update(tRuleInterval);
	}
	
	@Override
	public void delete(String id){
		tRuleIntervalDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tRuleIntervalDao.deleteBatch(ids);
	}

	@Override
	public boolean isShort(List<TRulePlanConsultEntity> planConsults, TRulePlanConsultEntity plan) {
		if (planConsults != null && plan != null && plan.getInoculateTime() != null) {
			List<TRuleIntervalEntity> intervals = ruleCache.getIntervals(plan.getClassId(),plan.getInjectionTimes());
			if (intervals != null && !intervals.isEmpty()) {
				for (TRuleIntervalEntity interval : intervals) {
					//获取需要间隔的剂次
					if (interval.getTargetTimes() != 0) {
						TRulePlanConsultEntity selectPlan = selectConsult(planConsults, interval.getTargetClassId(), interval.getTargetTimes());
						if (selectPlan != null && selectPlan.getInoculateTime() != null) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(selectPlan.getInoculateTime());
							if (interval.getIntervalMonth() != null) {
								cal.add(Calendar.MONTH, interval.getIntervalMonth());
							}
							if (interval.getIntervalDay() != null) {
								cal.add(Calendar.DATE, interval.getIntervalDay());
							}
							if (cal.getTime().getTime() > plan.getInoculateTime().getTime()) {
								return true;
							}
						}
					}else {//与所有剂次间隔
						for(TRulePlanConsultEntity intervalPlan : planConsults){
							if(interval.getTargetClassId().equals(intervalPlan.getId())){
								if (intervalPlan.getInoculateTime() != null) {
									Calendar cal = Calendar.getInstance();
									cal.setTime(intervalPlan.getInoculateTime());
									if (interval.getIntervalMonth() != null) {
										cal.add(Calendar.MONTH, interval.getIntervalMonth());
									}
									if (interval.getIntervalDay() != null) {
										cal.add(Calendar.DATE, interval.getIntervalDay());
									}
									if (cal.getTime().getTime() > plan.getInoculateTime().getTime()) {
										return true;
									}
								}
							}
						}
					}
				}
			} 
		}
		return false;
	}

    @Override
    public Integer calculatePlanState(List<TRulePlanConsultEntity> planConsults, TRulePlanConsultEntity consult) {
	    if(consult == null || consult.getInoculateTime() == null){
	        return TRulePlanConsultEntity.STATE_NO;
        }else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String s = sdf.format(consult.getFirstTime());
			try{
				consult.setFirstTime(sdf.parse(s));
			}catch (Exception e){}
	    	if(isShort(planConsults,consult)){
				return  TRulePlanConsultEntity.STATE_SHORT;
			}else if (consult.getInoculateTime().getTime() < consult.getFirstTime().getTime()) {// 过早
				return  TRulePlanConsultEntity.STATE_ADVANCE;
			} else if (consult.getInoculateTime().getTime() <= consult.getTimelyTime().getTime()) {// 及时
				return  TRulePlanConsultEntity.STATE_TIMELY;
			} else if (consult.getInoculateTime().getTime() <= consult.getQualifiledTime().getTime()) {// 合格
				return  TRulePlanConsultEntity.STATE_QUALIFILED;
			} else {// 超期
				return  TRulePlanConsultEntity.STATE_OUT;
			}
		}
    }

    /**
	 * 根据传入ID从传入规划集中选择规划信息返回
	 * @作者：邓召仕
	 * 2018年7月30日
	 * @param planConsults
	 * @param classId 需与之间隔的剂次类别ID
	 * @param times 与之间隔剂次
	 * @return
	 */
	private TRulePlanConsultEntity selectConsult(List<TRulePlanConsultEntity> planConsults,String classId,Integer times){
		if(planConsults != null && classId != null){
			for(TRulePlanConsultEntity plan : planConsults){
				if(classId.equals(plan.getClassId()) && times.equals(plan.getInjectionTimes())){
					return plan;
				}
			}
		}
        return null;
	}
}
