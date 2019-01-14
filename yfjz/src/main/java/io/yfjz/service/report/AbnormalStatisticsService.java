package io.yfjz.service.report;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/9/26 11:02
 */
public interface AbnormalStatisticsService {
    /** 
    * @Description: 查询数据 
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/26 11:04
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryList(Map queryMap);
    /** 
    * @Description: 查询总条数 
    * @Param: [queryMap] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/26 11:28
    * @Tel  17328595627
    */ 
    int queryTotal(Map queryMap);
}
