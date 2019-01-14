package io.yfjz.managerservice.rule.common.action;

import io.yfjz.entity.rule.TRulePlanEntity;

import java.util.List;

/**
 * class_name: 规划信息处理，用于规划信息推荐时条件过滤
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 13:56
 */
public interface PlanAction {
    void onAction(List<TRulePlanEntity> planList);
}
