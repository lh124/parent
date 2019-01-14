package io.yfjz.service.report;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/11/1 12:32
 */
public interface DynamicChildService {
    /** 
    * @Description: 查询儿童动态一览表数据 
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/1 12:32
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryList(Map<String, Object> queryMap);
}
