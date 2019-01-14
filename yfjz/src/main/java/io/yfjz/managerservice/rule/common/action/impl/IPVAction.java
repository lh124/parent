package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.Iterator;
import java.util.List;

/**
 * 脊灰灭活特殊处理
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class IPVAction implements PlanAction{
    private int pvTotal = 0;//接种脊灰总次数
    private int unBOPVTotal = 0 ;//非bOPV的脊灰总剂次
    private int iPVTotal = 0;//IPV的实际接种次数
    private int monthAge = 0;//儿童月龄

    /**
 * @method_name: 脊灰处理器
 * @describe:
 * @param [pvTotal, unBOPVTotal, iPVTotal, monthAge]
 * @return
 * @author 邓召仕
 * @date:
 **/
    public IPVAction(int pvTotal,int unBOPVTotal,int iPVTotal,int monthAge){
        this.pvTotal = pvTotal;
        this.unBOPVTotal = unBOPVTotal;
        this.iPVTotal = iPVTotal;
        this.monthAge = monthAge;
    }
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        TRulePlanEntity ipv=null;
        for (TRulePlanEntity plan : planList){
            if (PlanDicContext.IPV.equals(plan.getClassId())){
                ipv = plan;
            }
        }
        /*
         * 1、脊灰特殊处理，第一剂打ipv，第一剂非ipv且全打bopv的，补一剂ipv
         **/
        if(pvTotal > 0){//推荐了ipv,且脊灰接种剂次大于零
            if(monthAge <= 48){//儿童小于4岁，不用补种，按程序接种即可
                if (ipv != null) planList.remove(ipv);
            }else {//大于4岁零1个月还未种植脊灰
                if (ipv != null){
                    if (unBOPVTotal >0 ||iPVTotal >0){
                        planList.remove(ipv);//接种过topv或ipv的，不再推荐ipv
                        if (pvTotal < 4){//但如果脊灰接种不足4剂次的，用opv补足
                            ipv.setClassId(PlanDicContext.OPV);
                            ipv.setInjectionTimes(pvTotal+1);
                        }
                    }else {//大于4岁，没接种过值接种过bopv的先补种ipv
                        Iterator<TRulePlanEntity> planIterator = planList.iterator();
                        while (planIterator.hasNext()){
                            TRulePlanEntity plan = planIterator.next();
                            if (PlanDicContext.OPV.equals(plan.getClassId())){
                                planIterator.remove();
                            }
                        }
                    }
                }
            }

        }else {//第一剂脊灰推荐ipv
            Iterator<TRulePlanEntity> planIterator = planList.iterator();
            while (planIterator.hasNext()){
                TRulePlanEntity plan = planIterator.next();
                if (PlanDicContext.OPV.equals(plan.getClassId())){
                    planIterator.remove();
                }
            }
        }

    }
}
