package io.yfjz.managerservice.statistics;

import io.yfjz.entity.statistics.CreateCardRate;

import java.util.Date;
import java.util.List;

/**
 * 建卡率统计
 * @describe:
 * @param 
 * @return 
 * @author 邓召仕
 * @date: 2018-08-27  15:13
 **/
public interface CardStatisticsSerivce {

    /**
     * @method_name: getCreateCardRate
     * @describe:
     * @param createDateStart
     * @param createDateEnd
     * @param chilCommittees
     * @param infostatus
     * @return java.util.List<io.yfjz.entity.statistics.CreateCardRate>
     * @author 邓召仕
     * @date: 2018-08-27  15:22
     **/
    List<CreateCardRate> getCreateCardRate(Date createDateStart, Date createDateEnd, String[] chilCommittees, String[] infostatus,Date birthDayStart,Date birthDayEnd);
}
