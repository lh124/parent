package io.yfjz.service.basesetting;

import io.yfjz.entity.basesetting.TBaseDaySettingEntity;
import io.yfjz.utils.R;

import java.util.Date;
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
public interface TBaseDaySettingService {
	
	TBaseDaySettingEntity queryObject(String id);
	
	List<TBaseDaySettingEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TBaseDaySettingEntity tBaseDaySetting);
	
	void update(TBaseDaySettingEntity tBaseDaySetting);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	
	/**
	 * @method_name: saveInoculationDaySetting
	 * @describe: 周门诊-接种点 保存
	 * @param: [map]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/26  22:04
	 **/
    R saveInoculationDaySetting(Map map);

    /**
	 * @method_name: getCurrentStartDays
	 * @describe: 获取当前启用的门诊类型
	 * @param: []
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/26  22:59
	 **/
    R getCurrentStartDays();


    /**
	 * @method_name: updateBySettingType
	 * @describe: 根据setting_type类型，设置启用状态
	 * @param: [settingType]
	 * @return: void
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/26  23:19
	 **/
	R updateBySettingType(String settingType);

	/**
	 * @method_name: getAnInoculationTime
	 * @describe: 根据给定的条件，返回可以接种的日期  bioCode，committee参数可以为空；orgId，inocDate必填
	 * @param: [orgId, bioCode, committee, inocDate]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪（饶士培改2018/10/10）
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/9/7  16:16
	 **/
	R getAnInoculationTime(String orgId, String bioCode, String committee, Date inocDate );

	R queryListDaySettings(String orgId, String committeeCode, Date startInocDate,Date endInocDate,String bioCode);
}
