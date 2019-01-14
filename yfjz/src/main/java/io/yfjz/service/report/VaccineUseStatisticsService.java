package io.yfjz.service.report;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/9/10 14:12
 */
public interface VaccineUseStatisticsService {
    /** 
    * @Description: 查询疫苗的领用明细
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/10 14:14
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryList(Map<String, Object> queryMap);
}
