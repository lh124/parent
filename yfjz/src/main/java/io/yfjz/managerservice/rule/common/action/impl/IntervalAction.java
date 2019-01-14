package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.action.PlanAction;
import io.yfjz.service.rule.TRuleIntervalService;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 间短校验处理器，排除间短疫苗
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class IntervalAction implements PlanAction{

    private TRuleIntervalService tRuleIntervalService;
    private List<TRulePlanConsultEntity> planConsults;
    private Date inoculateTime;

    /**
 * 当前月龄优推处理
 * @describe:
 * @param tRuleIntervalService 间短校验接口
 * @param planConsults 儿童接种记录
 * @param inoculateTime 接种时间
 * @return
 * @author 邓召仕
 * @date:
 **/
    public IntervalAction(TRuleIntervalService tRuleIntervalService, List<TRulePlanConsultEntity> planConsults, Date inoculateTime){
        this.tRuleIntervalService = tRuleIntervalService;
        this.planConsults = planConsults;
        this.inoculateTime = inoculateTime;
    }
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        Iterator<TRulePlanEntity> iterator = planList.iterator();
        while (iterator.hasNext()){
            TRulePlanEntity planEntity = iterator.next();
            TRulePlanConsultEntity consultEntity = new TRulePlanConsultEntity();
            BeanUtils.copyProperties(planEntity,consultEntity);
            consultEntity.setInoculateTime(inoculateTime);
            if (tRuleIntervalService.isShort(planConsults,consultEntity)){
                iterator.remove();
            }
        }
    }
}
