package io.yfjz.managerservice.rule.common.action.impl;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.PlanAction;
import io.yfjz.service.mgr.StockService;
import io.yfjz.service.rule.TRuleReplaceService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 库存判断及免费推荐
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-08 14:15
 */
public class StockAction implements PlanAction{

    private StockService stockService;
    private TRuleReplaceService tRuleReplaceService;
    private int mothAge;

    /**
 * 根据库存过滤，移除没有库存的剂次
 * @describe:
 * @param stockService 库存疫苗推荐接口
 * @return
 * @author 邓召仕
 * @date:
 **/
    public StockAction(StockService stockService,TRuleReplaceService tRuleReplaceService,int mothAge){
        this.stockService = stockService;
        this.tRuleReplaceService = tRuleReplaceService;
        this.mothAge = mothAge;
    }
    @Override
    public void onAction(List<TRulePlanEntity> planList) {
        if (planList == null || planList.isEmpty()) return;
        Iterator<TRulePlanEntity> iterator = planList.iterator();
        while (iterator.hasNext()){
            TRulePlanEntity planEntity = iterator.next();
            //麻风、麻腮风特殊处理
            if (PlanDicContext.MMR.equals(planEntity.getClassId()) || PlanDicContext.MR.equals(planEntity.getClassId())){
                if (mothAge >= 18){
                    List<String> codes = new ArrayList<>();
                    codes.add("1201");
                    Map<String,Object> bio = stockService.judgeStock(codes);
                    if(bio != null){
                        planEntity.setInoculateId(bio.get("code")+","+bio.get("batchnum"));
                        continue;
                    }
                }else {
                    List<String> codes = new ArrayList<>();
                    codes.add("1401");
                    Map<String,Object> bio = stockService.judgeStock(codes);
                    if(bio != null){
                        planEntity.setInoculateId(bio.get("code")+","+bio.get("batchnum"));
                        continue;
                    }
                }
            }
            List<String> codeList = tRuleReplaceService.selectReplaceCode(planEntity.getClassId(),planEntity.getInjectionTimes(),mothAge);
           if (PlanDicContext.OPV.equals(planEntity.getClassId())){//2016年5月1日后不打这两种疫苗
               Iterator<String> bioiterator = codeList.iterator();
               while (bioiterator.hasNext()){
                   String bioIdstr = bioiterator.next();
                   if ("0301".equals(bioIdstr) || "0302".equals(bioIdstr) || "0303".equals(bioIdstr) || "0306".equals(bioIdstr)) bioiterator.remove();
               }
           }
            Map<String,Object> bioMap = stockService.judgeStock(codeList);
            if (bioMap == null){
                iterator.remove();
            }else {
                planEntity.setInoculateId(bioMap.get("code")+","+bioMap.get("batchnum"));
            }
        }
    }
}
