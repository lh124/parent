package io.yfjz.managerservice.rule.impl;

import io.yfjz.dao.rule.TRuleUnionDao;
import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.RecommendService;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.managerservice.rule.common.action.impl.*;
import io.yfjz.managerservice.rule.common.assembler.RecommendRuleAssembler;
import io.yfjz.managerservice.rule.common.cache.RuleCache;
import io.yfjz.service.mgr.StockService;
import io.yfjz.service.basesetting.TBaseDaySettingService;
import io.yfjz.service.rule.*;
import io.yfjz.utils.Constant;
import io.yfjz.utils.DateUtils;
import io.yfjz.utils.R;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 疫苗推荐服务
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-01 17:39
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    private static Logger log = Logger.getLogger(RecommendServiceImpl.class);
    @Autowired
    private TRulePlanService rulePlanService;
    @Autowired
    private RecommendRuleAssembler recommendRuleAssembler;
    @Autowired
    private TRulePlanService tRulePlanService;
    @Autowired
    private TRuleIntervalService tRuleIntervalService;
    @Autowired
    private TRuleReplaceService tRuleReplaceService;
    @Autowired
    private StockService stockService;
    @Autowired
    private TRuleDicService tRuleDicService;
    @Autowired
    private TBaseDaySettingService tBaseDaySettingService;
    @Autowired
    private TRulePlanConsultService tRulePlanConsultService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private TRuleUnionDao tRuleUnionDao;
    @Autowired
    private RuleCache ruleCache;
    @Override
    public List<TRulePlanEntity> getrecommend(String childCode) {
        //已经接种了的规划剂次
        List<TRulePlanConsultEntity> allPlanConsults = tRulePlanService.getInoPlansByChild(childCode);
        //设置起始规划时间，与上针剂最低间隔10天
        if(allPlanConsults != null && !allPlanConsults.isEmpty()){//已有接种记录才更改起始规划时间
            try{
                if(DateUtils.dateBetween(allPlanConsults.get(0).getInoculateTime(),new Date()) < 10){
                  return null;//接种时间过短，不推荐
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ruleCache.finshRuleCache(true);
        List<TRulePlanEntity> list = rulePlanService.queryShouldButNot(childCode);
        recommendRuleAssembler.setAssemblerParam(childCode,true,true,new Date());
        recommendRuleAssembler.executeAction(list);
        try{
           /* if (list == null || list.isEmpty()){//没有可推荐的
                if(allPlanConsults !=null && !allPlanConsults.isEmpty() && DateUtils.dateBetween(allPlanConsults.get(0).getInoculateTime(),new Date()) >= 28){//推荐二类苗
                   ChildData childData = tRuleUnionDao.getChildByCode(childCode);
                   List<String> bios = new ArrayList<>();
                   if (childData.getMoonAge()>8){//腮腺炎
                       bios.add("1001");
                   }
                   if (childData.getMoonAge()>2){//b型流感
                        bios.add("2301");
                        bios.add("2302");
                        bios.add("2401");
                   }
                   if (childData.getMoonAge()>24){//b型流感
                        bios.add("2501");
                        bios.add("2503");
                   }
                   if (childData.getMoonAge()>6){//b型流感
                        bios.add("5401");
                        bios.add("5402");
                   }
                   if (childData.getMoonAge()>12){//b型流感
                        bios.add("2201");
                   }

                   Map<String,Object> map = new HashMap<>();
                   map.put("bios",bios);
                   map.put("childCode",childCode);
                  Map<String,Object> bio = tRuleUnionDao.getBio2ByStock(map);
                  if(bio != null){
                      TRulePlanEntity planEntity = new TRulePlanEntity();
                      planEntity.setInoculateId(bio.get("code")+","+bio.get("batchnum"));
                      list.add(planEntity);
                  }
                }
            }*/
        }catch (Exception e){}

        return list;
    }

    @Override
    public R judgeOkIno(String childCode, String bioCode) {
        List<TRulePlanConsultEntity> planConsults = tRulePlanService.getAllPlanByChild(childCode);//儿童接种记录
        TRulePlanConsultEntity consultEntity = tRulePlanService.getPlanByChildBio(childCode,bioCode);
        //卡介提醒
        if("0101".equals(bioCode)){
            ChildData childData = tRuleUnionDao.getChildByCode(childCode);
            if (childData != null){
                int age = childData.getMoonAge();
                if (age >= 3){
                    return R.ok(201,"请确定该儿童已做PPD检测");
                }
            }
        }
        if (consultEntity == null){
            return R.ok();
        }else {
            consultEntity.setInoculateTime(new Date());
            int result = tRuleIntervalService.calculatePlanState(planConsults,consultEntity);
            if (result == TRulePlanConsultEntity.STATE_SHORT){
                return R.ok(201,"登记剂次间短");
            }else if (result == TRulePlanConsultEntity.STATE_ADVANCE){
                return R.ok(201,"登记疫苗提早");
            }else {
                return R.ok();
            }
        }

    }

    @Override
    public List<ChildData> inoNoteTwoOne(String selectType,String limitType, Integer monthLimit, Date chilBirthdayStart, Date chilBirthdayEnd, Date selectDate, Date planDate,
                                         String[] chilCommittees, String[] infostatus, String[] biotypes,String chilResidence,String chilAccount,String chilSchool,String chilSex) {
        Map<String,Object> param = new HashMap<>();
        List<ChildData> resultList = new ArrayList<>();
        boolean isLimit = true;
        if ("0".equals(limitType)){
            isLimit = false;
        }
        if (chilBirthdayStart != null){
            param.put("chilBirthdayStart",chilBirthdayStart);
        }
        if (chilBirthdayEnd != null){
            param.put("chilBirthdayEnd",chilBirthdayEnd);
        }
        if (chilCommittees != null){
            param.put("chilCommittees",chilCommittees);
        }
        if(infostatus != null){
            param.put("infostatus",infostatus);
        }

        if(!StringUtils.isEmpty(chilResidence)){
            param.put("chilResidence",chilResidence);
        }
        if(!StringUtils.isEmpty(chilAccount)){
            param.put("chilAccount",chilAccount);
        }
        if(!StringUtils.isEmpty(chilSchool)){
            param.put("chilSchool",chilSchool);
        }
        if(!StringUtils.isEmpty(Constant.GLOBAL_ORG_ID)){
            param.put("curdepartment",Constant.GLOBAL_ORG_ID);
        }
        if(!StringUtils.isEmpty(chilSex)){
            param.put("chilSex",chilSex);
        }
        if (selectDate == null){
            selectDate = new Date();
        }
        //查询儿童数据
        List<ChildData> childs = tRuleUnionDao.getAllChildsByCondition(param);

        if (childs != null && !childs.isEmpty()){
            for (ChildData child : childs){
                Map inoDateMap =  tBaseDaySettingService.queryListDaySettings(Constant.GLOBAL_ORG_ID,child.getCommittee(),new Date(),selectDate,null);
                List<Date> dateList = new ArrayList<>();
                if (inoDateMap !=null && inoDateMap.get("inocDate") !=null && inoDateMap.get("inocDate") instanceof Collection){
                    try {
                        dateList = (List<Date>) inoDateMap.get("inocDate");
                        if(dateList == null || dateList.isEmpty()){
                            dateList = new ArrayList<>();
                            dateList.add(selectDate);
                        }
                    }catch (Exception e){
                        dateList.add(selectDate);
                    }
                }else {
                    dateList.add(selectDate);
                }
                for (Date date : dateList){
                    List<TRulePlanEntity> plans =  rulePlanService.getShouldAndQualified(selectType,child.getChilCode(),monthLimit,date,biotypes);
                    //两剂一口服过滤
                    if (plans != null && !plans.isEmpty()){
                        recommendRuleAssembler.setAssemblerParam(child.getChilCode(),false,isLimit,date);
                        recommendRuleAssembler.executeAction(plans);
                    }
                    //配置通知参数
                    if (plans != null && !plans.isEmpty()){
                        for (TRulePlanEntity plan : plans){
                            ChildData notInoData = child.clone();
                            notInoData.setPlanId(plan.getClassId());
                            notInoData.setPlanName(plan.getInoculateId());
                            notInoData.setTimes(plan.getInjectionTimes()+"");
                            if(planDate != null){
                                notInoData.setInoDate(planDate);
                            }else {
                                notInoData.setInoDate(date);
                            }
                            resultList.add(notInoData);
                        }
                        break;
                    }
                }
            }
        }

        return resultList;
    }

    @Override
    public List<ChildData> countPlanTimes(List<ChildData> datas){
        if (datas != null && !datas.isEmpty()){
            Map<String,Map<String,Integer>> countMap = new HashMap<>();
            for (ChildData childData : datas){
                Map<String,Integer> map = countMap.get(childData.getCommittee());
                if (map == null){
                    map = new HashMap<>();
                    countMap.put(childData.getCommittee(),map);
                }
                addTimesToMap(map,childData);
            }
            List<ChildData> results = new ArrayList<>();
            int i = 0;
            for ( String teeKey : countMap.keySet()){
                Map<String,Integer> resMap = countMap.get(teeKey);
                for (String plKey : resMap.keySet()){
                    Integer resInt = resMap.get(plKey);
                    ChildData resData = new ChildData();
                    resData.setPlanId(""+i++);
                    resData.setCommittee(teeKey);
                    resData.setPlanName(plKey);
                    resData.setTimes(resInt+"");
                    results.add(resData);
                }
            }
            return results;
        }
        return null;
    }

    @Override
    public String getBioCodeByPlan(String planDicId, Integer agentTimes, int mothAge) {
        List<String> codeList = tRuleReplaceService.selectReplaceCode(planDicId,agentTimes,mothAge);
        Map<String,Object> bioMap = stockService.judgeStock(codeList);
        if (bioMap != null){
            return (String) bioMap.get("code");
        }
        return null;
    }

    /**
     * 下次预约
     **/
    @Override
    public R nextIno(String childId, String orgId) {
        ruleCache.finshRuleCache(true);
        if(!StringUtils.isEmpty(childId)){
            ChildData childData = tRuleUnionDao.getChildByCode(childId);
            Calendar calendar = Calendar.getInstance();
            Date selectDate = new Date();
            calendar.setTime(selectDate);
            //已经接种了的规划剂次
            List<TRulePlanConsultEntity> allPlanConsults = tRulePlanService.getInoPlansByChild(childId);
            //还未接种的规划剂次
            List<TRulePlanEntity> noInoPlans = tRulePlanService.getNoInoPlansByChild(childId);
            int pvTotal = commonService.queryPvTotal(childData.getChilCode(),childData.getMoonAge());//接种脊灰总次数
            int unBOPVTotal = commonService.queryUnBOPVTotal(childData.getChilCode(),childData.getMoonAge());//非bOPV的脊灰总剂次
            if(isFinishIno(noInoPlans,childData,pvTotal,unBOPVTotal)){
                return R.error("该儿童已完成接种规划");
            }
            //设置起始规划时间，与上针剂最低间隔10天
            if(allPlanConsults != null && !allPlanConsults.isEmpty()){//已有接种记录才更改起始规划时间
                try{
                    if(DateUtils.dateBetween(allPlanConsults.get(0).getInoculateTime(),selectDate) < 10){
                        calendar.setTime(allPlanConsults.get(0).getInoculateTime());
                        calendar.add(Calendar.DATE, 10);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //循环获取接种日查找下次接种
            while (true){
                //接种日获取模块
                try{
                    Map inoDateMap =  tBaseDaySettingService.getAnInoculationTime(orgId,null,childData.getCommittee(),calendar.getTime());
                    if(inoDateMap != null && inoDateMap.get("inocDate") != null){
                        selectDate = (Date) inoDateMap.get("inocDate");
                    }else {
                        selectDate = calendar.getTime();
                    }
                }catch (Exception e){
                    selectDate = calendar.getTime();
                }
                //处理下针次
                //1、获取已达到接种时间剂次
                List<TRulePlanEntity> arrivePlans = getArrivePlans(noInoPlans, selectDate);
                if (arrivePlans.isEmpty()){
                    calendar.setTime(noInoPlans.get(0).getFirstTime());
                    continue;
                }
                //规则处理计算两剂一口服
                recommendRuleAssembler.setAssemblerParam(childId,false,true,selectDate);
                recommendRuleAssembler.executeAction(arrivePlans);

                //计算后不为空，已经找到下一剂，为空的话加一天继续找
                if (arrivePlans != null && !arrivePlans.isEmpty()){
                    for (TRulePlanEntity plan:arrivePlans){
                        plan.setClassId(tRuleDicService.queryObject(plan.getClassId()).getName());
                    }
                    Format f = new SimpleDateFormat("yyyy-MM-dd");
                    return R.ok().put("childName",childData.getChilName()).put("nextdate",f.format(selectDate)).put("plans",arrivePlans);
                }else {
                    calendar.setTime(selectDate);
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                }
            }
        }
        return R.error("儿童编码不能为空");
    }

    @Override
    public R childAllPlan(String childId, String orgId) {
        ruleCache.finshRuleCache(true);
        if(!StringUtils.isEmpty(childId)){
            ChildData childData = tRuleUnionDao.getChildByCode(childId);
            int pvTotal = commonService.queryPvTotal(childId,childData.getMoonAge());//接种脊灰总次数
            int unBOPVTotal = commonService.queryUnBOPVTotal(childId,childData.getMoonAge());//非bOPV的脊灰总剂次
            int iPVTotal = commonService.queryIPVTotal(childId,childData.getMoonAge());//IPV的实际接种次数
            int dtapDtTotal = commonService.queryDtapDtTotal(childId,childData.getMoonAge());//接种百白破和白破总次数
            //已经接种了的规划剂次
            List<TRulePlanConsultEntity> allPlanConsults = tRulePlanService.getInoPlansByChild(childId);
            if (allPlanConsults == null) allPlanConsults = new ArrayList<>();
            //还未接种的规划剂次
            List<TRulePlanEntity> noInoPlans = tRulePlanService.getNoInoPlansByChild(childId);
            //查询日
            Calendar calendar = Calendar.getInstance();
            Date selectDate = new Date();
            calendar.setTime(selectDate);//设置查询时间
            //设置起始规划时间，与上针剂最低间隔10天
            if(allPlanConsults != null && !allPlanConsults.isEmpty()){//已有接种记录才更改起始规划时间
                try{
                    if(DateUtils.dateBetween(allPlanConsults.get(0).getInoculateTime(),selectDate) < 10){
                        calendar.setTime(allPlanConsults.get(0).getInoculateTime());
                        calendar.add(Calendar.DATE, 10);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            while (true) {
                if (isFinishIno(noInoPlans,childData,pvTotal,unBOPVTotal)) {//处理结束反馈前端
                    for (TRulePlanConsultEntity plan:allPlanConsults){
                        plan.setClassId(tRuleDicService.queryObject(plan.getClassId()).getName());
                    }
                    return R.ok().put("plans", allPlanConsults);
                }
                //接种日获取模块
                try {
                    Map inoDateMap = tBaseDaySettingService.getAnInoculationTime(orgId, null, childData.getCommittee(), calendar.getTime());
                    if (inoDateMap != null && inoDateMap.get("inocDate") != null) {
                        selectDate = (Date) inoDateMap.get("inocDate");
                    } else {
                        selectDate = calendar.getTime();
                    }
                } catch (Exception e) {
                    selectDate = calendar.getTime();
                }

                //处理下针次
                //1、获取已达到接种时间剂次
                List<TRulePlanEntity> arrivePlans = getArrivePlans(noInoPlans, selectDate);
                if (arrivePlans.isEmpty()){
                    calendar.setTime(noInoPlans.get(0).getFirstTime());
                    continue;
                }

                //规则处理计算两剂一口服
                recommendRuleAssembler.setAssemblerParam(childId,selectDate,pvTotal,unBOPVTotal,iPVTotal,dtapDtTotal,allPlanConsults,false,true);
                recommendRuleAssembler.executeAction(arrivePlans);

                if (arrivePlans.isEmpty()) {
                    calendar.setTime(selectDate);
                    calendar.add(Calendar.DATE, 1);
                    continue;
                } else {
                    /*Iterator<TRulePlanEntity> iterator = arrivePlans.iterator();
                    while (iterator.hasNext()) {*/
                    for (TRulePlanEntity p : arrivePlans){
                        //TRulePlanEntity p = iterator.next();
                        TRulePlanConsultEntity pce = new TRulePlanConsultEntity();
                        BeanUtils.copyProperties(p, pce);
                        pce.setInoculateTime(selectDate);
                        allPlanConsults.add(pce);
                        /**修改查询条件*/
                        if (PlanDicContext.IPV.equals(p.getClassId())){
                            pvTotal++;
                            iPVTotal++;
                            unBOPVTotal++;
                        }else if (PlanDicContext.OPV.equals(p.getClassId())){
                            pvTotal++;
                        }else if (PlanDicContext.DTaP.equals(p.getClassId()) || PlanDicContext.DT.equals(p.getClassId())){
                            dtapDtTotal++;
                        }
                        //移除未接种
                        noInoPlans.remove(p);
                        /*Iterator<TRulePlanEntity> noInoiterator = noInoPlans.iterator();
                        while (noInoiterator.hasNext()) {
                            TRulePlanEntity pc = noInoiterator.next();
                            if (p.getId().equals(pc.getId())) {
                                noInoiterator.remove();
                                break;
                            }
                        }*/
                    }
                    calendar.setTime(selectDate);
                    calendar.add(Calendar.DATE, 10);
                }
            }
        }
        return R.error("儿童编码不能为空");
    }

    @Override
    public List<ChildData> countPlanNameTimes(List<ChildData> list) {
        List<ChildData> childDataList = new ArrayList<>();
        if (list !=null && !list.isEmpty()){
            Map<String,Integer> map = new HashMap<>();
            for (ChildData child : list){
                Integer number = map.get(child.getPlanName().trim());
                if (number != null){
                    map.put(child.getPlanName().trim(),number+=1);
                }else {
                    map.put(child.getPlanName().trim(),1);
                }
            }
            for (String namestr : map.keySet()){
                ChildData childData = new ChildData();
                childData.setId(namestr);
                childData.setPlanName(namestr);
                childData.setTimes(""+map.get(namestr));
                childDataList.add(childData);
            }
        }
        return childDataList;
    }

    /**
     * 根据时间筛选出最早可接种时间已经到达了的疫苗剂次
     * @describe:
     * @param noInoPlans
     * @return java.util.List<io.yfjz.entity.rule.TRulePlanEntity>
     * @author 邓召仕
     * @date: 2018-09-17  10:45
     **/
    private List<TRulePlanEntity> getArrivePlans(List<TRulePlanEntity> noInoPlans,Date selectDate) {
        List<TRulePlanEntity>  resultList = new ArrayList<>();
        if (noInoPlans != null){
            for (TRulePlanEntity plan : noInoPlans){
                if (plan.getFirstTime().getTime() <= selectDate.getTime()){
                    resultList.add(plan);
                }
            }
        }
        return resultList;
    }

    private void addTimesToMap(Map<String,Integer> map,ChildData notInoData){
        String strKey = notInoData.getPlanName() + notInoData.getTimes();
        Integer number = map.get(strKey);
        if (number != null){
            number ++;
        }else {
            number = 1;
        }
        map.put(strKey,number);
    }

    /**
     * 根据儿童未接种规划疫苗剂次判断该儿童是否已经接种完成
     * @describe:
     * @param list
     * @return boolean
     * @author 邓召仕
     * @date: 2018-10-04  16:34
     **/
    private boolean isFinishIno(List<TRulePlanEntity> list,ChildData childData,int pvTotal,int unBOPVTotal){

        if (list == null || list.isEmpty()){
            return true;
        }else {
            if (pvTotal >= 4 && unBOPVTotal>0){//脊灰灭活处理
                Iterator<TRulePlanEntity> planIterator = list.iterator();
                while (planIterator.hasNext()){
                    TRulePlanEntity plan = planIterator.next();
                    if (PlanDicContext.IPV.equals(plan.getClassId())){
                        planIterator.remove();
                    }
                }
            }
            List<TRulePlanEntity> jsList = new ArrayList<>(list.size());
            jsList.addAll(list);
            HepAMoveAction hepAMoveAction = new HepAMoveAction();
            hepAMoveAction.onAction(jsList);//处理甲肝
            JEIMoveAction jeiMoveAction = new JEIMoveAction();
            jeiMoveAction.onAction(jsList);//处理乙脑
            if (jsList == null || jsList.isEmpty()){
                return true;
            }else {
                return false;
            }
        }
    }
}
