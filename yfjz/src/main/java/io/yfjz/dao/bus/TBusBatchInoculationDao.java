package io.yfjz.dao.bus;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.bus.TBusBatchInoculationEntity;
import io.yfjz.entity.bus.TBusCancelEntity;
import io.yfjz.entity.rule.ChildData;
import io.yfjz.utils.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/** 
* @Description: 批量补录
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/4 9:32
* @Tel  17328595627
*/ 
public interface TBusBatchInoculationDao extends BaseDao<TBusBatchInoculationEntity> {
    /** 
    * @Description: 批量保存查询结果
    * @Param: [queryResult] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/4 9:40
    * @Tel  17328595627
    */
    void saveBatchResult(List<ChildData> queryResult);

    List<ChildData> queryListByCondition(Query query);

    List<Map<String,Object>> queryAllTimes();

    int deleteTimes(String selectTimes);

    int deleteHistoryRecord(Map param);

    String[] getAllVaccine();

    String[] queryChildInfoStatus();

    String queryVaccineClass(String planName);

    List<Map<String,Object>> queryCodeTimes(@Param("chilCode") Object chilCode,@Param("classId") String className);

    int queryInoculateVacccodeNumByMap(Map<String, Object> m);

    int queryInoculateNumByMap(Map<String, Object> m);
}
