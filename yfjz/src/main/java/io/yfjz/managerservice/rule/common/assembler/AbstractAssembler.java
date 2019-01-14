package io.yfjz.managerservice.rule.common.assembler;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.action.PlanAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * class_name: 规则组装器抽象类
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-09 10:43
 */
public abstract class AbstractAssembler {
    private List<PlanAction> planActions = new CopyOnWriteArrayList<PlanAction>();

    public abstract void initAction();
    /**
     * @method_name: 添加规则处理器，有序添加，按顺序处理
     * @describe:
     * @param planAction
     * @return void
     * @author 邓召仕
     * @date: 2018-08-09  10:48
     **/
    public void addPlanAction(PlanAction planAction){
        planActions.add(planAction);
    }

    /**
     * @method_name: 按顺序执行处理
     * @describe:
     * @param planList
     * @return void
     * @author 邓召仕
     * @date: 2018-08-09  10:54
     **/
    public void executeAction(List<TRulePlanEntity> planList){
        for (PlanAction action : planActions){
            action.onAction(planList);
        }
    }

    public void cleanAction(){
        planActions.clear();
    }
}
