package io.yfjz.managerservice.rule.impl;

import io.yfjz.dao.rule.TRulePlanDao;
import io.yfjz.dao.rule.TRuleReplaceDao;
import io.yfjz.dao.rule.TRuleUnionDao;
import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.entity.rule.TRuleReplaceEntity;
import io.yfjz.managerservice.rule.InitPlanService;
import io.yfjz.managerservice.rule.common.cache.RuleCache;
import io.yfjz.service.rule.TRulePlanConsultService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * class_name: InitPlanServiceImpl
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-01 17:39
 */
@Service
public class InitPlanServiceImpl implements InitPlanService {
    private static Logger log = Logger.getLogger(PlanInitThread.class);
    @Autowired
    private TRulePlanConsultService tRulePlanConsultService;
    @Autowired
    private TRulePlanDao tRulePlanDao;
    @Autowired
    private TRuleUnionDao tRuleUnionDao;
    @Autowired
    private RuleCache ruleCache;
    @Autowired
    private TRuleReplaceDao tRuleReplaceDao;
    //线程池
    private static ExecutorService poolExecutor;

    public static  int childNumber = 0;
    @Override
    public void initChildPlan(String childCode) {
        try{
            ChildData childData;
            if(!StringUtils.isEmpty(childCode)){
                childData = tRuleUnionDao.getChildByCode(childCode);
            }else{
                childData = tRuleUnionDao.getLastCreateChild();
            }
            finshRuleCache(false);//刷新替代规则缓存
            List<TRulePlanConsultEntity> consults = tRulePlanConsultService.findAll();
            List<TRulePlanConsultEntity> plans = tRulePlanConsultService.getNowPlanList(childData,consults);
            tRulePlanDao.deleteByChildId(childCode);//清空该儿童接种规划信息
            tRulePlanDao.batchSaveConsultToPlan(consults);//保存计划后信息到数据库
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initChildsPlan(String[] childCodes) {

        if(childCodes != null){
            finshRuleCache(false);//刷新替代规则缓存
            List<TRulePlanConsultEntity> consults = tRulePlanConsultService.findAll();
            for (String childCode : childCodes ) {
                ChildData childData = tRuleUnionDao.getChildByCode(childCode);
                List<TRulePlanConsultEntity> plans = tRulePlanConsultService.getNowPlanList(childData,consults);
                tRulePlanDao.deleteByChildId(childCode);//清空该儿童接种规划信息
                tRulePlanDao.batchSaveConsultToPlan(consults);//保存计划后信息到数据库
            }
        }
    }

    @Override
    public void initAllChildsPlan() {
        poolExecutor = Executors.newFixedThreadPool(30);
        childNumber = 0;
        finshRuleCache(false);//刷新替代规则缓存
        List<TRulePlanConsultEntity> consults = tRulePlanConsultService.findAll();
        List<ChildData> childDatas = tRuleUnionDao.getAllChild();
        tRulePlanDao.truncate();
        log.info("已经清空儿童计划表");
        if (childDatas != null && !childDatas.isEmpty()){
            log.info("开始儿童计划信息初始化");
            for (ChildData child : childDatas){
                List<TRulePlanConsultEntity> initConsults = new ArrayList<TRulePlanConsultEntity>();
                for (TRulePlanConsultEntity planc : consults){
                    initConsults.add(planc.clone());
                }
                poolExecutor.execute(new PlanInitThread(child,initConsults));
            }
        }
        if(poolExecutor != null && !poolExecutor.isShutdown()){
            poolExecutor.shutdown();
        }
    }

    @Override
    public List<ChildData> queryNoPlanChildsList(Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        return tRuleUnionDao.getNoPlanChildsByPage(map);
    }

    @Override
    public int queryNoPlanChildsTotal() {
        return tRuleUnionDao.queryNoPlanChildsTotal();
    }

    @Override
    public int queryChildsTotal() {
        return tRuleUnionDao.queryChildsTotal();
    }

    @Override
    public void finshRuleCache(boolean forceFinsh) {
        if (forceFinsh || ruleCache.isCacheEmpty()){
            List<TRuleReplaceEntity> ruleReplaceEntities = tRuleReplaceDao.queryList(new HashMap<>());
            ruleCache.addAllReplace(ruleReplaceEntities);
        }
    }
}
