package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 按照最早可接种时间进行升序排列
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class DateShortAction implements PlanAction{


    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        //排序
        Collections.sort(planList,new Comparator<TRulePlanEntity>() {
            @Override
            public int compare(TRulePlanEntity o1, TRulePlanEntity o2) {
                try {
                    if (o1.getFirstTime().getTime() > o2.getFirstTime().getTime()) {
                        return -1;
                    } else if (o1.getFirstTime().getTime() < o2.getFirstTime().getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
