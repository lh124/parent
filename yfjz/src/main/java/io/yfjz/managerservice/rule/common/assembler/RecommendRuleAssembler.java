package io.yfjz.managerservice.rule.common.assembler;

import io.yfjz.dao.rule.TRuleUnionDao;
import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.managerservice.rule.common.action.impl.*;
import io.yfjz.service.mgr.StockService;
import io.yfjz.service.rule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 推荐规则组装执行器
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-09 10:38
 */
@Component
@Scope("prototype")
public class RecommendRuleAssembler extends AbstractAssembler {
    private int pvTotal = 0;//接种脊灰总次数
    private int unBOPVTotal = 0 ;//非bOPV的脊灰总剂次
    private int iPVTotal = 0;//IPV的实际接种次数
    private int dtapDtTotal = 0;//接种百白破和白破总次数
    private List<String> nowList;//当前月龄该接种的规划id
    private List<TRulePlanConsultEntity> planConsults;//儿童已接种规划信息
    private int monthAge = 0;//儿童月龄
    private Date selectDate = new Date();

    private boolean isSelectStock = true;//是否判断库存
    private boolean isLimitTimes = true;//是否限制剂次

    @Autowired
    private TRuleIntervalService tRuleIntervalService;
    @Autowired
    private StockService stockService;
    @Autowired
    private TRuleReplaceService tRuleReplaceService;
    @Autowired
    private TRulePlanConsultService tRulePlanConsultService;
    @Autowired
    private TRulePlanService tRulePlanService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private TRuleUnionDao tRuleUnionDao;

    @Override
    public void initAction() {
        super.cleanAction();
        this.addPlanAction(new HepAMoveAction());//处理甲肝
        this.addPlanAction(new JEIMoveAction());//处理乙脑
        this.addPlanAction(new IPVAction(pvTotal, unBOPVTotal, iPVTotal, monthAge));//处理脊灰
        this.addPlanAction(new DTaPAction(dtapDtTotal, monthAge));//处理百白破
        this.addPlanAction(new MRAction(monthAge));//处理麻风
        this.addPlanAction(new MinScreenAction());//保留最小剂次
        this.addPlanAction(new IntervalAction(tRuleIntervalService,planConsults,selectDate));//间短查询
        if (isSelectStock){
            this.addPlanAction(new StockAction(stockService,tRuleReplaceService,monthAge));//库存查询
        }
        //this.addPlanAction(new DateShortAction());
        this.addPlanAction(new NowShortAction(nowList));//当前排序
        this.addPlanAction(new PlanShortAction(monthAge));//规划排序
        if (isLimitTimes){
            this.addPlanAction(new TwoOneAction());//两剂一口服
        }
    }

    /**
     * 以儿童当前数据库信息为基础，默认判断库存与限制两剂一口服，初始化过滤器规则
     * @describe:
     * @param childCode 儿童编码
     * @return void
     * @author 邓召仕
     * @date: 2018-08-22  14:27
     **/
    private void setAssemblerParam(String childCode){
        ChildData childData = tRuleUnionDao.getChildByCode(childCode);
        if (childData != null){
            monthAge = childData.getMoonAge();
            pvTotal = commonService.queryPvTotal(childCode,monthAge);
            unBOPVTotal = commonService.queryUnBOPVTotal(childCode,monthAge);
            iPVTotal = commonService.queryIPVTotal(childCode,monthAge);
            dtapDtTotal = commonService.queryDtapDtTotal(childCode,monthAge);
            nowList = tRulePlanConsultService.getClassIdsByMonth(monthAge);
            planConsults = tRulePlanService.getAllPlanByChild(childCode);
        }
        initAction();
    }
    /**
     * 以儿童当前数据库信息为基础，根据是否查库存和是否限制两剂一口服条件，初始化规则构造器
     * @describe:
     * @param childCode 儿童编码
     * @param isSelectStock 是否查询库存，默认是
     * @param isLimitTimes 是否限制两剂一口服，默认是
     * @return void
     * @author 邓召仕
     * @date: 2018-08-22  14:27
     **/
    public void setAssemblerParam(String childCode,boolean isSelectStock,boolean isLimitTimes){
        this.isLimitTimes = isLimitTimes;
        this.isSelectStock =isSelectStock;
        setAssemblerParam(childCode);
    }
    /**
     * 以儿童当前数据库信息为基础，根据是否查库存、是否限制两剂一口服、查询计算时间为条件，初始化规则构造器
     * @describe:
     * @param childCode 儿童编码
     * @param isSelectStock 是否查询库存，默认是
     * @param isLimitTimes 是否限制两剂一口服，默认是
     * @return void
     * @author 邓召仕
     * @date: 2018-08-22  14:27
     **/
    public void setAssemblerParam(String childCode,boolean isSelectStock,boolean isLimitTimes,Date selectDate){
        this.isLimitTimes = isLimitTimes;
        this.isSelectStock =isSelectStock;
        this.selectDate = selectDate;
        setAssemblerParam(childCode);
    }
    /**
     * 根据传入条件，初始化过滤器规则
     * @describe:
     * @param childCode 儿童编码
     * @return void
     * @author 邓召仕
     * @date: 2018-08-22  14:27
     **/
    public void setAssemblerParam(String childCode,Date selectDate,int pvTotal,int unBOPVTotal,int iPVTotal,int dtapDtTotal,List planConsults,boolean isSelectStock,boolean isLimitTimes){
        ChildData childData = tRuleUnionDao.getChildByCode(childCode);
        if (childData != null){
            monthAge = childData.getMoonAge(selectDate);
            this.selectDate = selectDate;
            this.pvTotal = pvTotal;
            this.unBOPVTotal = unBOPVTotal;
            this.iPVTotal = iPVTotal;
            this.dtapDtTotal = dtapDtTotal;
            nowList = tRulePlanConsultService.getClassIdsByMonth(monthAge);
            this.planConsults = planConsults;
        }
        this.isLimitTimes = isLimitTimes;
        this.isSelectStock =isSelectStock;
        initAction();
    }
    public int getPvTotal() {
        return pvTotal;
    }
/**脊灰接种总剂次*/
    public void setPvTotal(int pvTotal) {
        this.pvTotal = pvTotal;
    }

    public int getUnBOPVTotal() {
        return unBOPVTotal;
    }
    /**非bOPV的接种剂次*/
    public void setUnBOPVTotal(int unBOPVTotal) {
        this.unBOPVTotal = unBOPVTotal;
    }

    public int getiPVTotal() {
        return iPVTotal;
    }
/**ipv接种剂次*/
    public void setiPVTotal(int iPVTotal) {
        this.iPVTotal = iPVTotal;
    }

    public int getDtapDtTotal() {
        return dtapDtTotal;
    }
/**百白破接种剂次*/
    public void setDtapDtTotal(int dtapDtTotal) {
        this.dtapDtTotal = dtapDtTotal;
    }

    public List<String> getNowList() {
        return nowList;
    }
/**当前月龄应接种的疫苗规划剂次类别ID*/
    public void setNowList(List<String> nowList) {
        this.nowList = nowList;
    }

    public List<TRulePlanConsultEntity> getPlanConsults() {
        return planConsults;
    }
/**已经接种了的规划剂次，给计算间隔时使用*/
    public void setPlanConsults(List<TRulePlanConsultEntity> planConsults) {
        this.planConsults = planConsults;
    }

    public int getMonthAge() {
        return monthAge;
    }
/**计算规划信息时月龄*/
    public void setMonthAge(int monthAge) {
        this.monthAge = monthAge;
    }

    public boolean isSelectStock() {
        return isSelectStock;
    }
/**是否查库存*/
    public void setSelectStock(boolean selectStock) {
        isSelectStock = selectStock;
    }

    public boolean isLimitTimes() {
        return isLimitTimes;
    }
/**是否限制两剂一口服*/
    public void setLimitTimes(boolean limitTimes) {
        isLimitTimes = limitTimes;
    }

    public Date getSelectDate() {
        return selectDate;
    }
/**查询计算时间*/
    public void setSelectDate(Date selectDate) {
        this.selectDate = selectDate;
    }
}
