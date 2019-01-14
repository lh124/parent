package io.yfjz.dao.statistics;

import java.util.List;
import java.util.Map; /**
* @Description: 儿童全程服务接口 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/23 14:29
* @Tel  17328595627
*/ 
public interface ChildServiceStatisticsDao {
    /** 
    * @Description: 根据查询条件，查询儿童登记记录 
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/23 14:32
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryRegisterList(Map<String, Object> queryMap);
    /** 
    * @Description: 查询儿童的接种记录 
    * @Param: [queryMap]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/23 14:56
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryInoculateList(Map<String, Object> queryMap);

    int queryTotal(Map<String, Object> queryMap);
}
