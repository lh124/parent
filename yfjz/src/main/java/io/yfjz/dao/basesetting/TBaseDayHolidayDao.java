package io.yfjz.dao.basesetting;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TBaseDayHolidayEntity;

/**
 * 机构节假日设置表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-08-30 10:43:53
 */
public interface TBaseDayHolidayDao extends BaseDao<TBaseDayHolidayEntity> {

    /**
     * @method_name: queryNationalHolidays
     * @describe: 查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
     * @param: [month]
     * @return: io.yfjz.entity.basesetting.TBaseDayHolidayEntity
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/26  11:41
     **/
    TBaseDayHolidayEntity queryNationalHolidays(int month);
}
