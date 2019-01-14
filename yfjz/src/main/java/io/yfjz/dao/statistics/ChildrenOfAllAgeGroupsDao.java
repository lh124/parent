package io.yfjz.dao.statistics;


import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 各年龄组儿童构成情况统计表（免疫规划来源）
 * @Date 14:45 2018/12/19
 */

public interface ChildrenOfAllAgeGroupsDao {

    /**
     *  查询 各年龄组儿童构成情况统计表（免疫规划来源）
     * @param here 1、常住： 1 本地 6 入学  7入托  (6 1) 1、流入： 10 外地转来 9 临时接种 3、流出： 2 转出，3 临时外转 8 异地接种
     * @param committee 根据行政村查询
     * @param data 查询日期
     * @return
     */
    List<Map<String,Object>>  queryChildrenOfAllAgeGroups(Map<String,Object> map);

    /**
     * 根据建卡时间范围查询出生医院和月龄儿童数量
     * @param startDate 建卡开始时间
     * @param endDate 建卡结束时间
     * @return
     */
    List<Map<String,Object>> queryCountJianKaChilQuantity(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 根据建卡时间查询统计 卡介苗和乙肝疫苗 第一剂接种数
     * @param chil_birthhospital
     * @param startDate
     * @param endDate
     * @return
     */
    Integer queryCountJianKanChilTheFirstDose(@Param("chil_birthhospital")String chil_birthhospital,@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 根据建卡时间查询统计 卡介苗和乙肝疫苗 及时接种数
     * @param chil_birthhospital
     * @param startDate
     * @param endDate
     * @return
     */
    Integer queryCountJianKanChilTimelyVaccination(@Param("chil_birthhospital")String chil_birthhospital,@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
