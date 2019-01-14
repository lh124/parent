package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.Iterator;
import java.util.List;

/**
 * 百白破与白破推荐处理器
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class DTaPAction implements PlanAction{
    private int dtapDtTotal = 0;//接种百白破和白破总次数
    private int monthAge = 0;//儿童月龄

    /**
 *  百白破与白破处理器
 * @describe:
 * @param [dtapDtTotal, monthAge]
 * @return
 * @author 邓召仕
 * @date:
 **/
    public DTaPAction(int dtapDtTotal,int monthAge){
        this.dtapDtTotal = dtapDtTotal;
        this.monthAge = monthAge;
    }
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;

        /*
         * 1、大于等于6岁儿童，如果白破和百白破共接种剂次小于3剂次的，用白破补齐
         **/
        if(monthAge >= 72){//大于等于6岁
            Iterator<TRulePlanEntity> planIterator = planList.iterator();
            while (planIterator.hasNext()){
                TRulePlanEntity plan = planIterator.next();
                if (PlanDicContext.DTaP.equals(plan.getClassId())){
                    planIterator.remove();
                }
            }
            if (dtapDtTotal < 3){//白破和百白破共接种剂次小于3剂次
                TRulePlanEntity dt = new TRulePlanEntity();
                dt.setClassId(PlanDicContext.DT);
                dt.setInjectionTimes(dtapDtTotal+1);
                dt.setInoculateId("白破疫苗");
                planList.add(dt);
            }
        }

    }
}
