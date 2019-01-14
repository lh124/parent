package io.yfjz.dao.statistics;

import io.yfjz.entity.statistics.CreateCardRate;

import java.util.List;
import java.util.Map;

/**
 * @method_name:
 * @describe:
 * @param
 * @return
 * @author 邓召仕
 * @date: 2018-08-27  15:34
 **/
public interface CardStatisticsDao {
    /**
     * @method_name: getRateChildByCondition
     * @describe:
     * @param parameters
     * @return java.util.List<io.yfjz.entity.statistics.CreateCardRate>
     * @author 邓召仕
     * @date: 2018-08-27  15:34
     **/
    List<CreateCardRate> getRateChildByCondition(Map<String, Object> parameters);
    /**
     * @method_name: getCreateCradTimelyNumber
     * @describe:
     * @param parameters
     * @return java.lang.String
     * @author 邓召仕
     * @date: 2018-08-27  15:36
     **/
    String getCreateCradTimelyNumber(Map<String, Object> parameters);

}
