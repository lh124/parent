package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.List;

/**
 * class_name: JEIMoveAction
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class HepAMoveAction implements PlanAction{
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        TRulePlanEntity HepAL1=null,HepAI1=null,HepAI2=null;
        for (TRulePlanEntity plan : planList){
            if (PlanDicContext.HepA_L.equals(plan.getClassId()) && plan.getInjectionTimes() == 1){//甲肝减毒第一剂
                HepAL1 = plan;
            }else if (PlanDicContext.HepA_I.equals(plan.getClassId()) && plan.getInjectionTimes() == 1){//甲肝灭活第一剂
                HepAI1 = plan;
            }else if (PlanDicContext.HepA_I.equals(plan.getClassId()) && plan.getInjectionTimes() == 2){//甲肝灭活第二剂
                HepAI2 = plan;
            }
        }
        /*
         * 1、接种了甲肝减毒第一剂的，不用接种甲肝灭活1、2剂；
         **/
        if(HepAL1 == null){//已经接种，故应种未中为空
            if(HepAI1 != null){
                planList.remove(HepAI1);
            }
            if(HepAI2 != null){
                planList.remove(HepAI2);
            }
        }else {//没有接种
            //1、接种了乙脑灭活第一剂
            if (HepAI1 == null){
                planList.remove(HepAL1);
            }else {
                planList.remove(HepAI1);
                if(HepAI2 != null){
                    planList.remove(HepAI2);
                }
            }
        }
    }
}
