package io.yfjz.service.report;

import java.util.List;
import java.util.Map; /**
* @Description: 库存统计 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/8 16:16
* @Tel  17328595627
*/ 
public interface VaccineChangeService {
    /** 
    * @Description: 查询库存信息报表
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/8 16:17
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryList(Map<String, Object> queryMap);
    /** 
    * @Description: 查询盘点时间集合
    * @Param: [] 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/9 16:13
    * @Tel  17328595627
    */
    List<Map<String, Object>> getCheckTimeList();
    
}
