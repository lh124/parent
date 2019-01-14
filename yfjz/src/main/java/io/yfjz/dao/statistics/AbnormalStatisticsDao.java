package io.yfjz.dao.statistics;

import java.util.List;
import java.util.Map;

/**
* @Description: 儿童全程服务接口 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/23 14:29
* @Tel  17328595627
*/ 
public interface AbnormalStatisticsDao {
    /** 
    * @Description: 根据查询条件，查询儿童异常记录
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/23 14:32
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryList(Map<String, Object> queryMap);

    int queryTotal(Map queryMap);
    /** 
    * @Description: 根据条件查询取消原因
    * @Param: [registerList] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/26 18:34
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryCancelReason(List registerList);
    /** 
    * @Description: 根据儿童ID 查询当天的留观时间 
    * @Param: [observeList] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/26 18:37
    * @Tel  17328595627
     * @param observeList
    */ 
    List<Map<String,Object>> queryLeaveTime(Map<String, Object> observeList);
}
