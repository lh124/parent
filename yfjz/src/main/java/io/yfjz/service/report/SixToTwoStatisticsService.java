package io.yfjz.service.report;

import io.yfjz.utils.R;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/10/16 16:06
 */
public interface SixToTwoStatisticsService {
    /** 
    * @Description: 统计6-2报表数据 
    * @Param: [queryMap, residence] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/20 21:08
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryList(Map<String, Object> queryMap);

    R uploadPlatform( Map<String, Object> queryMap);
}
