package io.yfjz.service.report;

import java.util.List;
import java.util.Map;

/**
* @Description: 儿童全程服务记录统计
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/6 17:05
* @Tel  17328595627
*/ 
public interface ChildServiceStatisticsService {
   /** 
   * @Description: 查询统计数据 
   * @Param: [queryMap] 
   * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
   * @Author: 田金海
   * @Email: 895101047@qq.com
   * @Date: 2018/9/23 14:28
   * @Tel  17328595627
   */ 
    List<Map<String,Object>> queryList(Map<String, Object> queryMap);
    /** 
    * @Description: 查询总数 
    * @Param: [queryMap] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/25 9:39
    * @Tel  17328595627
    */ 
    int queryTotal(Map<String, Object> queryMap);
}
