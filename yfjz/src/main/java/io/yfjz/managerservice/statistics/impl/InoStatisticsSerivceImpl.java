package io.yfjz.managerservice.statistics.impl;

import io.yfjz.dao.rule.TRuleUnionDao;
import io.yfjz.dao.statistics.InoStatisticsDao;
import io.yfjz.entity.statistics.InoculateRate;
import io.yfjz.managerservice.rule.common.cache.RuleCache;
import io.yfjz.managerservice.statistics.InoStatisticsSerivce;
import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * class_name: InoStatisticsSerivceImpl
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-23 17:25
 */
@Service
public class InoStatisticsSerivceImpl implements InoStatisticsSerivce {
    @Autowired
    private InoStatisticsDao inoStatisticsDao;

    @Autowired
    private TRuleUnionDao tRuleUnionDao;

    @Override
    public List<InoculateRate> getInoRates(Date chilBirthdayStart, Date chilBirthdayEnd, Date inoculateStart, Date inoculateEnd, String chilResidence, String[] chilCommittees, String[] infostatus, String[] biotypes,String chilAccount,Integer limit) {
        Map<String,Object> param = new HashMap<>();
        if (chilBirthdayStart != null){
            param.put("chilBirthdayStart",chilBirthdayStart);
        }
        if (chilBirthdayEnd != null){
            param.put("chilBirthdayEnd",chilBirthdayEnd);
        }
//        接种日期添加
        if (inoculateStart != null){
            param.put("inoculateStart",inoculateStart);
        }
        if (inoculateEnd != null){
            param.put("inoculateEnd",inoculateEnd);
        }

        if(!StringUtils.isEmpty(chilResidence)){
            param.put("chilResidence",chilResidence);
        }
        if (chilCommittees != null){
            param.put("chilCommittees",chilCommittees);
        }
        if(infostatus != null){
            param.put("infostatus",infostatus);
        }
        if(biotypes != null){
            param.put("biotypes",biotypes);
        }
        if(!StringUtils.isEmpty(chilAccount)){
            param.put("chilAccount",chilAccount);
        }
        if(!StringUtils.isEmpty(Constant.GLOBAL_ORG_ID)){
            param.put("curdepartment",Constant.GLOBAL_ORG_ID);
        }
        if(limit != null){
            param.put("limit",limit);
        }
        //随机儿童
        List<String> childCodes = tRuleUnionDao.getRandomChildCode(param);
        if (childCodes == null){
            childCodes = new ArrayList<>();
        }
        param.put("childCodes",childCodes);
        //添加到缓存
        RuleCache.getInoRandChild().put(ShiroUtils.getUserEntity().getUserId(),childCodes);

        //查询所有类别及名称剂次
        List<InoculateRate>  allClassList = inoStatisticsDao.queryInoculateClassByList(param);
        if(null != allClassList && !allClassList.isEmpty()) {//防错判断
            //循环获取各类疫苗接种数据
            for(InoculateRate classs:allClassList) {
                param.put("classId", classs.getPlanId());
                param.put("agentTimes", classs.getAgentTimes());

                Long tatolNumber = 0l;//应种数
                Long realNumber = 0l;//实种数
                //根据疫苗类别、剂次，查询0未种、1间短、2及时、3合格、4超期数 5、提早
                List<Map<String,Object>> numberList = inoStatisticsDao.getInoculateNumberList(param);
                for(Map<String,Object> numberMap : numberList){
                    String state = ""+numberMap.get("state");
                    Long number = (Long)numberMap.get("number");
                    tatolNumber += number;
                    if("0".equals(state)){//未种
                        classs.setYinweiCount(number.intValue());
                    }else if("1".equals(state)){//间短
                        classs.setShortCount(number.intValue());
                        realNumber += number;
                    }else if("2".equals(state)){//及时
                        classs.setTimelyCount(number.intValue());
                        realNumber += number;
                    }else if("3".equals(state)){//合格
                        classs.setQualifiedCount(number.intValue());
                        realNumber += number;
                    }else if("4".equals(state)){//超期
                        classs.setOutQualifiedCount(number.intValue());
                        realNumber += number;
                    }else if("5".equals(state)){//提早数
                        if (classs.getShortCount() != null){
                            //提早也算间短
                            classs.setShortCount(classs.getShortCount()+number.intValue());
                        }else {
                            classs.setShortCount(number.intValue());
                        }
                        realNumber += number;
                    }

                }
                classs.setFactCount(realNumber.intValue());
                classs.setYinCount(tatolNumber.intValue());
                //合格数为及时数加合格数
                if (classs.getQualifiedCount() != null){
                    classs.setQualifiedCount(classs.getQualifiedCount()+classs.getTimelyCount());
                }else {
                    classs.setQualifiedCount(classs.getTimelyCount());
                }
                initZero(classs);


            }

        }

        return calculateRate(allClassList);
    }

    @Override
    public List<InoculateRate> getRateChildInfo(String selectType, String classId, Integer agentTime, Date chilBirthdayStart, Date chilBirthdayEnd, String chilResidence, String[] chilCommittees, String[] infostatus, String[] biotypes,String chilAccount,String random, Integer page, Integer limit) {
        Map<String,Object> param = new HashMap<>();
        if (chilBirthdayStart != null){
            param.put("chilBirthdayStart",chilBirthdayStart);
        }
        if (chilBirthdayEnd != null){
            param.put("chilBirthdayEnd",chilBirthdayEnd);
        }
        if(!StringUtils.isEmpty(chilResidence)){
            param.put("chilResidence",chilResidence);
        }
        if (chilCommittees != null){
            param.put("chilCommittees",chilCommittees);
        }
        if(infostatus != null){
            param.put("infostatus",infostatus);
        }
        if(biotypes != null){
            param.put("biotypes",biotypes);
        }
        if(!StringUtils.isEmpty(chilAccount)){
            param.put("chilAccount",chilAccount);
        }
        //随机儿童
        List<String> childCodes = RuleCache.getInoRandChild().get(ShiroUtils.getUserEntity().getUserId());
        if (childCodes == null){
            childCodes = new ArrayList<>();
        }
        param.put("childCodes",childCodes);
        param.put("selectType", selectType);
        param.put("classId", classId);
        param.put("agentTimes", agentTime);
        param.put("offset", (page - 1) * limit);
        param.put("limit", limit);
        return inoStatisticsDao.queryRateChildInfo(param);
    }

    @Override
    public int getRateChildInfoCount(String selectType, String classId, Integer agentTime, Date chilBirthdayStart, Date chilBirthdayEnd, String chilResidence, String[] chilCommittees, String[] infostatus, String[] biotypes,String chilAccount,String random) {
        Map<String,Object> param = new HashMap<>();
        if (chilBirthdayStart != null){
            param.put("chilBirthdayStart",chilBirthdayStart);
        }
        if (chilBirthdayEnd != null){
            param.put("chilBirthdayEnd",chilBirthdayEnd);
        }
        if(!StringUtils.isEmpty(chilResidence)){
            param.put("chilResidence",chilResidence);
        }
        if (chilCommittees != null){
            param.put("chilCommittees",chilCommittees);
        }
        if(infostatus != null){
            param.put("infostatus",infostatus);
        }
        if(biotypes != null){
            param.put("biotypes",biotypes);
        }
        if(!StringUtils.isEmpty(chilAccount)){
            param.put("chilAccount",chilAccount);
        }
        //随机儿童
        List<String> childCodes = RuleCache.getInoRandChild().get(ShiroUtils.getUserEntity().getUserId());
        if (childCodes == null){
            childCodes = new ArrayList<>();
        }
        param.put("childCodes",childCodes);
        param.put("selectType", selectType);
        param.put("classId", classId);
        param.put("agentTimes", agentTime);
        return inoStatisticsDao.queryRateChildInfoCount(param);
    }

    /**
     * 接种率计算
     * @describe:
     * @param inoculateRates
     * @return java.util.List<io.yfjz.entity.statistics.InoculateRate>
     * @author 邓召仕
     * @date: 2018-08-23  17:58
     **/
    private List<InoculateRate> calculateRate(List<InoculateRate> inoculateRates){
        for(InoculateRate inoculateRate:inoculateRates){
            //接种率
            if(inoculateRate.getFactCount()!=null&&inoculateRate.getYinCount()!=null&&inoculateRate.getYinCount()>0&&inoculateRate.getYinCount()>0){
                double d =  new BigDecimal((double)inoculateRate.getFactCount()*100/inoculateRate.getYinCount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                inoculateRate.setInoculateRate(d);
            }else {
                inoculateRate.setInoculateRate(0.00);
            }
            //间短率
            if(inoculateRate.getShortCount()!=null&&inoculateRate.getYinCount()!=null&&inoculateRate.getYinCount()>0&&inoculateRate.getShortCount()>0){
                double d=   new BigDecimal((double)inoculateRate.getShortCount()*100/inoculateRate.getYinCount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                inoculateRate.setShortRate(d);
            }else {
                inoculateRate.setShortRate(0.00);
            }
            //及时率
            if(inoculateRate.getTimelyCount()!=null&&inoculateRate.getYinCount()!=null&&inoculateRate.getYinCount()>0&&inoculateRate.getTimelyCount()>0){
                double d=   new BigDecimal((double)inoculateRate.getTimelyCount()*100/inoculateRate.getYinCount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                inoculateRate.setTimelyRate(d);
            }else {
                inoculateRate.setTimelyRate(0.00);
            }
            //合格率
            if(inoculateRate.getQualifiedCount()!=null&&inoculateRate.getYinCount()!=null&&inoculateRate.getYinCount()>0&&inoculateRate.getQualifiedCount()>0){
                double d=   new BigDecimal((double)inoculateRate.getQualifiedCount()*100/inoculateRate.getYinCount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                inoculateRate.setQualifiedRate(d);
            }else {
                inoculateRate.setQualifiedRate(0.00);
            }
            //超期率
            if(inoculateRate.getOutQualifiedCount()!=null&&inoculateRate.getYinCount()!=null&&inoculateRate.getYinCount()>0&&inoculateRate.getOutQualifiedCount()>0){
                double d=   new BigDecimal((double)inoculateRate.getOutQualifiedCount()*100/inoculateRate.getYinCount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                inoculateRate.setOutQualifiedRate(d);
            }else {
                inoculateRate.setOutQualifiedRate(0.00);
            }
            if(inoculateRate.getFactCount()!=null&&inoculateRate.getYinCount()!=null&&inoculateRate.getYinCount()>0){
                int i= inoculateRate.getYinCount()-inoculateRate.getFactCount();
                inoculateRate.setYinweiCount(i);
                if(i>0){
                    double d=   new BigDecimal((double)i*100/inoculateRate.getYinCount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    inoculateRate.setYinweiRate(d);
                }else {
                    inoculateRate.setYinweiRate(0.00);
                }
            }
        }
        return inoculateRates;
    }
    /**
     * 接种率null值零化
     * @author 邓召仕 2018-5-7
     * @param classs
     */
    private void initZero(InoculateRate classs){
        if(null == classs.getFactCount()) classs.setFactCount(0);
        if(null == classs.getOutQualifiedCount()) classs.setOutQualifiedCount(0);
        if(null == classs.getQualifiedCount()) classs.setQualifiedCount(0);
        if(null == classs.getShortCount()) classs.setShortCount(0);
        if(null == classs.getTimelyCount()) classs.setTimelyCount(0);
        if(null == classs.getYinCount()) classs.setYinCount(0);
        if(null == classs.getYinweiCount()) classs.setYinweiCount(0);
    }
}
