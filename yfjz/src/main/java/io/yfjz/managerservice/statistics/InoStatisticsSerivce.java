package io.yfjz.managerservice.statistics;

import io.yfjz.entity.statistics.InoculateRate;

import java.util.Date;
import java.util.List;

/**
 * class_name: InoStatisticsSerivce
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-23 17:24
 */
public interface InoStatisticsSerivce {
    /**
     * 接种点接种率统计
     * @describe:
     * @param chilBirthdayStart 开始日期（生日）
     * @param chilBirthdayEnd 结束日期（生日）
     * @param chilResidence 居住属性
     * @param chilCommittees 行政村
     * @param infostatus 个案状态
     * @param biotypes 疫苗类别
     * @return java.util.List<io.yfjz.entity.statistics.InoculateRate>
     * @author 邓召仕
     * @date: 2018-08-23  17:29
     **/
    List<InoculateRate> getInoRates(Date chilBirthdayStart, Date chilBirthdayEnd, Date inoculateStart, Date inoculateEnd, String chilResidence, String[] chilCommittees, String[] infostatus, String[] biotypes,String chilAccount,Integer limit);

    /**
     * 根据条件分页查询应种未种或超期接种儿童信息
     * @describe:
     * @return java.util.List<io.yfjz.entity.statistics.InoculateRate>
     * @author 邓召仕
     * @date: 2018-08-24  17:33
     **/
    List<InoculateRate> getRateChildInfo(String selectType, String classId, Integer agentTime, Date chilBirthdayStart, Date chilBirthdayEnd, String chilResidence, String[] chilCommittees, String[] infostatus, String[] biotypes,String chilAccount,String random, Integer page, Integer limit);

    /**
     * 根据条件统计应种未种或逾期接种儿童数
     * @describe:
     * @return int
     * @author 邓召仕
     * @date: 2018-08-24  17:33
     **/
    int getRateChildInfoCount(String selectType, String classId, Integer agentTime, Date chilBirthdayStart, Date chilBirthdayEnd, String chilResidence, String[] chilCommittees, String[] infostatus, String[] biotypes,String chilAccount,String random);
}
