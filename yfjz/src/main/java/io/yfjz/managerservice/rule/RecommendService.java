package io.yfjz.managerservice.rule;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.utils.R;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * class_name: RecommendService
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-16 14:07
 */
public interface RecommendService {
    /**
     * 根据儿童编码获取当前推荐疫苗
     * @describe:
     * @param childCode 儿童编码
     * @return java.util.List<io.yfjz.entity.rule.TRulePlanEntity>
     * @author 邓召仕
     * @date: 2018-08-16  14:12
     **/
    List<TRulePlanEntity> getrecommend(String childCode);

    /**
     * 判断该儿童是否可以接种该剂疫苗
     * @describe:
     * @param [childCode, bioCode]
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-08-17  14:06
     **/
    R judgeOkIno(String childCode, String bioCode);

    /**
     * 儿童应种未种、逾期未种的两剂一口服统计
     * @describe:
     * @param [selectType, monthLimit, chilBirthdayStart, chilBirthdayEnd, selectDate, planDate, chilCommittees, infostatus, biotypes]
     * @return java.util.List<io.yfjz.entity.rule.ChildData>
     * @author 邓召仕
     * @date: 2018-08-21  10:44
     **/
    List<ChildData> inoNoteTwoOne(String selectType,String limitType, Integer monthLimit, Date chilBirthdayStart, Date chilBirthdayEnd, Date selectDate, Date planDate, String[] chilCommittees, String[] infostatus, String[] biotypes,String chilResidence,String chilAccount,String chilSchool,String chilSex);

    /**
     * 根据未种信息按行政村/居委会统计未种疫苗剂次数
     * @describe:
     * @param datas
     * @return List<ChildData>
     * @author 邓召仕
     * @date: 2018-09-04  15:09
     **/
    List<ChildData> countPlanTimes(List<ChildData> datas);

    /**
     * 根据规划信息及儿童月龄获取当前库存有的、失效期最短的疫苗编码bioCode
     * @describe:
     * @param planDicId 规划字典表id
     * @param agentTimes 规划剂次
     * @param mothAge 儿童月龄
     * @return java.lang.String
     * @author 邓召仕
     * @date: 2018-09-06  9:16
     **/
    String getBioCodeByPlan(String planDicId,Integer agentTimes,int mothAge);

    /**
     * 下次预约
     * @describe:
     * @param childId
     * @param orgId
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-09-11  11:38
     **/
    R nextIno(String childId, String orgId);

    /**
     * 儿童全程接种计划
     * @describe:
     * @param childId
     * @param orgId
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-09-13  15:43
     **/
    R childAllPlan(String childId, String orgId);

    /**
     * @method_name: 根据疫苗名称统计剂次
     * @describe:
     * @return java.util.List<io.yfjz.entity.rule.ChildData>
     * @author 邓召仕
     * @date: 2018-11-27  15:19
     **/
    List<ChildData> countPlanNameTimes(List<ChildData> list);
}
