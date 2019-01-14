package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.Iterator;
import java.util.List;

/**
 * 当前月龄应种的排先
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class NowShortAction implements PlanAction{

    private List<String> nowList;//当前月龄要打的规划疫苗

    /**
 * 当前月龄优推处理
 * @describe:
 * @param nowList 当前月龄接种的规划字典表ID集合
 * @return
 * @author 邓召仕
 * @date:
 **/
    public NowShortAction(List<String> nowList){
        this.nowList = nowList;
    }
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        if (nowList != null){
            for (int i = 0; i < planList.size(); i++){
                TRulePlanEntity plan = planList.get(i);
                for (String classId : nowList){
                    if (classId.equals(plan.getClassId())){
                        planList.remove(plan);
                        planList.add(0,plan);
                    }
                }
            }
        }

    }
}
