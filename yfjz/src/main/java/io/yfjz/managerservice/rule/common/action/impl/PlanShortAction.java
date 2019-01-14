package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.Iterator;
import java.util.List;

/**
 * 按规划推优规则进行推优:小于3月龄，重点推荐卡介 ,2~4月龄，重点推荐脊灰 ,8月龄，重点推荐麻风，18月龄推荐麻腮风
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class PlanShortAction implements PlanAction{

    private int monthAge = 0;//儿童月龄

    /**
 * @method_name: 规划推优处理器
 * @describe:
 * @param monthAge 儿童月龄
 * @return
 * @author 邓召仕
 * @date:
 **/
    public PlanShortAction(int monthAge){
        this.monthAge = monthAge;
    }
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        if ( monthAge <= 4) {// 推荐脊灰
            for(int i = 0;i<planList.size();i++){
                TRulePlanEntity plan = planList.get(i);
                if(PlanDicContext.IPV.equals(plan.getClassId()) || PlanDicContext.OPV.equals(plan.getClassId())){
                    planList.remove(plan);
                    planList.add(0, plan);
                }
            }

        }
        if (monthAge <= 3) {// 推荐卡介
            for(int i = 0;i<planList.size();i++){
                TRulePlanEntity plan = planList.get(i);
                if(PlanDicContext.BCG.equals(plan.getClassId())){
                    planList.remove(plan);
                    planList.add(0, plan);
                }
            }

        }

        if (monthAge >= 8) {// 推荐麻类(麻风)
            for(int i = 0;i<planList.size();i++){
                TRulePlanEntity plan = planList.get(i);
                if(PlanDicContext.MR.equals(plan.getClassId())){
                    planList.remove(plan);
                    planList.add(0, plan);
                }
            }
        }
        if (monthAge >= 18) {// 推荐麻类(麻腮风)
            for(int i = 0;i<planList.size();i++){
                TRulePlanEntity plan = planList.get(i);
                if(PlanDicContext.MMR.equals(plan.getClassId())){
                    planList.remove(plan);
                    planList.add(0, plan);
                }
            }
        }

    }
}
