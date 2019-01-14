package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.Iterator;
import java.util.List;

/**
 * 两剂一口服处理，系统默认脊灰减毒为口服
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class TwoOneAction implements PlanAction{

    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty() || planList.size() < 3) return;
        TRulePlanEntity opv = null;
        TRulePlanEntity plan1;
        TRulePlanEntity plan2;
        Iterator<TRulePlanEntity> iterator = planList.iterator();
        while (iterator.hasNext()){
            TRulePlanEntity plan = iterator.next();
            if (PlanDicContext.OPV.equals(plan.getClassId())){
                opv = plan;
                iterator.remove();
                break;
            }
        }
        plan1 = planList.get(0);
        plan2 = planList.get(1);
        planList.clear();
        if (plan1 != null){
            planList.add(plan1);
        }
        if (plan2 != null){
            planList.add(plan2);
        }
        if (opv != null){
            if (planList .size() >1 && opv.getInoculateId() != null){
                String[] results = opv.getInoculateId().split(",");
                if(results[0].equals("0305") || results[0].equals("0311")){
                    planList.add(opv);
                }
            }else {
                planList.add(opv);
            }
        }
    }
}
