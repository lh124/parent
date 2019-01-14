package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.Iterator;
import java.util.List;

/**
 * 麻风与麻腮风一次只能打其一
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class MRAction implements PlanAction{

    private int monthAge = 0;//儿童月龄

    /**
 * @method_name: 麻风处理器
 * @describe:
 * @param monthAge
 * @return
 * @author 邓召仕
 * @date:
 **/
    public MRAction( int monthAge){
        this.monthAge = monthAge;
    }
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        TRulePlanEntity mr=null;
        for (TRulePlanEntity plan : planList){
            if (PlanDicContext.MR.equals(plan.getClassId())){
                mr = plan;
            }
        }
        /*
         * 1、推荐麻风就先不打麻腮风
         **/
        if(mr != null){//第一剂脊灰推荐ipv
            Iterator<TRulePlanEntity> planIterator = planList.iterator();
            while (planIterator.hasNext()){
                TRulePlanEntity plan = planIterator.next();
                if (PlanDicContext.MMR.equals(plan.getClassId())){
                    planIterator.remove();
                }
            }
        }

    }
}
