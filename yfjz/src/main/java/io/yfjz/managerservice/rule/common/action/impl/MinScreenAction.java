package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.*;

/**
 * 各类疫苗只保留最小剂次
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class MinScreenAction implements PlanAction{

    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        Map<String,TRulePlanEntity> minMap = new HashMap<>();
        for (TRulePlanEntity plan : planList){
            TRulePlanEntity minPlan = minMap.get(plan.getClassId());
            if (minPlan == null){
                minMap.put(plan.getClassId(),plan);
            }else {
                if (plan.getInjectionTimes() < minPlan.getInjectionTimes()){
                    minMap.put(plan.getClassId(),plan);
                }
            }
        }
        //清空
        planList.clear();
        //重新加入
        for (TRulePlanEntity resultPlan : minMap.values()){
            planList.add(resultPlan);
        }
    }
}
