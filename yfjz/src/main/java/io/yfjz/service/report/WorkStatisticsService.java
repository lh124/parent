package io.yfjz.service.report;

import java.util.List;
import java.util.Map; /** 
* @Description: 工作量统计接口 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/6 17:05
* @Tel  17328595627
*/ 
public interface WorkStatisticsService {
    /** 
    * @Description: 查询工作量统计 
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/6 17:08
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryList(Map<String, Object> queryMap);
}
