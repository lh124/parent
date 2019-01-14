package io.yfjz.dao.queue;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.queue.TQueueNoOperateEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-25 02:25:55
 */
public interface TQueueNoOperateDao extends BaseDao<TQueueNoOperateEntity> {
    /** 
    * @Description: 查询职工的工作量 
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/6 17:10
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryListByWork(Map<String, Object> queryMap);
    /** 
    * @Description: 查询服务记录 
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/7 10:36
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryService(Map<String, Object> queryMap);


}
