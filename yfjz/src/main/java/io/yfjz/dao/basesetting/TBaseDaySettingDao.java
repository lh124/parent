package io.yfjz.dao.basesetting;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TBaseDaySettingEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构接种日设置表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-26 17:45:18
 */
public interface TBaseDaySettingDao extends BaseDao<TBaseDaySettingEntity> {

    /**
     * @method_name: removeByUserType
     * @describe: 根据settingType类型删除数据
     * @param: [settingType]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/26  23:00
     **/
    void removeByUserType(String settingType);

    
    /**
     * @method_name: getCurrentStartDays
     * @describe: 获取当前启用的门诊类型
     * @param: [orgId]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/26  23:00
     **/
    Map getCurrentStartDays(String orgId);

    
    /**
     * @method_name: updateBySettingType
     * @describe: 根据setting_type类型，设置启用状态
     * @param: [settingType]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/26  23:18
     **/
    void updateBySettingType(String settingType);

    /**
     * @method_name: updateByNotSettingType
     * @describe: 根据setting_type类型，设置禁用状态
     * @param: [settingType]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/26  23:37
     **/
    void updateByNotSettingType(String settingType);

    /**
     * @method_name: removeBySettingTypeAndCommitteeAndOrgId
     * @describe: 优先删除当前机构下该行政村/居委会 所设置的接种日
     * @param: [hashMap]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/8  11:20
     **/
    void removeBySettingTypeAndCommitteeAndOrgId(HashMap<String,String> hashMap);

    /**
     * @method_name: removeBySettingTypeAndVaccAndOrgId
     * @describe: 优先删除当前机构下该疫苗 所设置的接种日
     * @param: [hashMap]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/8  15:45
     **/
    void removeBySettingTypeAndVaccAndOrgId(HashMap<String, String> hashMap);

    /**
     * @method_name: getCurrentStartDaysByCondition
     * @describe: 根据给定的条件，返回可以接种的日期  bioCode，committee参数可以为空；orgId必填
     * @param: [orgId, bioCode, committeeCode, inocDate]
     * @return: java.util.HashMap
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/7  16:43
     **/
    HashMap getCurrentStartDaysByCondition(HashMap<String,Object> hashMap);


    List<Map<String, Object>> getCurrentSettingDaysByCondition(Map<String, Object> map);

}
