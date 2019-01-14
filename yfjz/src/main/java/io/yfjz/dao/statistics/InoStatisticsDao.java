package io.yfjz.dao.statistics;

import io.yfjz.entity.statistics.InoculateRate;

import java.util.List;
import java.util.Map;

/**
 * class_name: InoStatisticsDao
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-23 17:46
 */
public interface InoStatisticsDao {
    /**
     * 获取所有需要查询疫苗类型的id、名字和剂次
     * @describe:
     * @param parameters
     * @return java.util.List<io.yfjz.entity.statistics.InoculateRate>
     * @author 邓召仕
     * @date: 2018-08-23  17:49
     **/
    List<InoculateRate> queryInoculateClassByList(Map<String, Object> parameters);

    /**
     * 查询接种数
     * @describe:
     * @param param
     * @return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @author 邓召仕
     * @date: 2018-08-24  9:45
     **/
    List<Map<String,Object>> getInoculateNumberList(Map<String, Object> param);

    /**
     * 查询超期或应种未种儿童信息
     * @describe:
     * @author 邓召仕
     * @date: 2018-08-24  17:39
     **/
    List<InoculateRate> queryRateChildInfo(Map<String, Object> param);

    /**
     * 查询超期或应种未种儿童数
     * @describe:
     * @param param
     * @return int
     * @author 邓召仕
     * @date: 2018-08-24  17:39
     **/
    int queryRateChildInfoCount(Map<String, Object> param);
}
