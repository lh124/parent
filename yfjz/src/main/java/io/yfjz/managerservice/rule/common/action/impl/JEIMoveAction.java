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
public class JEIMoveAction implements PlanAction{
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        TRulePlanEntity JEL1=null,JEL2=null,JEI1=null,JEI2=null,JEI3=null,JEI4=null;
        for (TRulePlanEntity plan : planList){
            if (PlanDicContext.JE_L.equals(plan.getClassId()) && plan.getInjectionTimes() == 1){//乙脑减毒第一剂
                JEL1 = plan;
            }else if (PlanDicContext.JE_L.equals(plan.getClassId()) && plan.getInjectionTimes() == 2){//乙脑减毒第二剂
                JEL2 = plan;
            }else if (PlanDicContext.JE_I.equals(plan.getClassId()) && plan.getInjectionTimes() == 1){//乙脑减毒第二剂
                JEI1 = plan;
            }else if (PlanDicContext.JE_I.equals(plan.getClassId()) && plan.getInjectionTimes() == 2){//乙脑减毒第二剂
                JEI2 = plan;
            }else if (PlanDicContext.JE_I.equals(plan.getClassId()) && plan.getInjectionTimes() == 3){//乙脑减毒第二剂
                JEI3 = plan;
            }else if (PlanDicContext.JE_I.equals(plan.getClassId()) && plan.getInjectionTimes() == 4){//乙脑减毒第二剂
                JEI4 = plan;
            }
        }
        /*
         * 1、接种了乙脑减毒第一剂的，不用接种乙脑灭活1、2剂；
         * 2、接种了乙脑减毒第二剂的，不用接种乙脑灭活3、4剂；
         * 3、在没有接种乙脑减毒第一剂情况下，接种了一剂灭活，则去除乙脑第一剂，推荐乙脑灭活第二剂
         * 4、同上，接种了灭活第三剂的不用推荐减毒第二剂，只推荐减毒第四剂
         **/
        if(JEL1 == null){//已经接种，故应种未中为空
            if(JEI1 != null){
                planList.remove(JEI1);
            }
            if(JEI2 != null){
                planList.remove(JEI2);
            }
        }else {//没有接种
            //1、接种了乙脑灭活第一剂
            if (JEI1 == null){
                planList.remove(JEL1);
            }else {
                planList.remove(JEI1);
                if(JEI2 != null){
                    planList.remove(JEI2);
                }
            }
        }
        //乙脑减毒第二剂
        if(JEL2 == null){//已经接种，故应种未中为空
            if(JEI3 != null){
                planList.remove(JEI3);
            }
            if(JEI4 != null){
                planList.remove(JEI4);
            }
        }else {//没有接种
            //1、接种了乙脑灭活第一剂
            if (JEI3 == null){
                planList.remove(JEL2);
            }else {
                planList.remove(JEI3);
                if(JEI4 != null){
                    planList.remove(JEI4);
                }
            }
        }
    }
}
