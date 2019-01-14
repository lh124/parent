package io.yfjz.managerservice.rule;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanEntity;

import java.util.List;

/**
 * class_name: InitPlanService
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-01 17:30
 */
public interface InitPlanService {
    /**
     * @method_name: 根据儿童ID，初始化该儿童规划信息
     * @describe:
     * @param childCode
     * @return void
     * @author 邓召仕
     * @date: 2018-08-01  17:35
     **/
    void initChildPlan(String childCode);
    /**
     * @method_name: 批量初始化儿童规划信息
     * @describe:
     * @param childCodes
     * @return void
     * @author 邓召仕
     * @date: 2018-08-01  17:36
     **/
    void initChildsPlan(String[] childCodes);
    /**
     * @method_name: 初始化全部儿童信息
     * @describe:
     * @param
     * @return void
     * @author 邓召仕
     * @date: 2018-08-01  17:38
     **/
    void initAllChildsPlan();

    /**
     * @method_name: 分页获取未被初始化儿童信息
     * @describe:
     * @return java.util.List<io.yfjz.managerservice.rule.common.data.ChildData>
     * @author 邓召仕
     * @date: 2018-08-02  14:33
     **/
    List<ChildData> queryNoPlanChildsList(Integer page, Integer limit);

    /**
     * @method_name: 查询未被初始化儿童总数
     * @describe:
     * @param
     * @return int
     * @author 邓召仕
     * @date: 2018-08-02  14:33
     **/
    int queryNoPlanChildsTotal();
    /**
     * @method_name: 查询儿童总数
     * @describe:
     * @param
     * @return int
     * @author 邓召仕
     * @date: 2018-08-02  14:33
     **/
    int queryChildsTotal();

    /**
     * @method_name: 刷新规则缓存
     * @describe:
     * @param forceFinsh 是否强制刷新
     * @return void
     * @author 邓召仕
     * @date: 2018-08-06  10:57
     **/
    void finshRuleCache(boolean forceFinsh);
}
