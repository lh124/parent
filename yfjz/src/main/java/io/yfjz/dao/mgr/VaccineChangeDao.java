package io.yfjz.dao.mgr;

import io.yfjz.entity.mgr.TMgrStockInfoEntity;

import java.util.List;
import java.util.Map; /**
* @Description: 库存疫苗统计DAO
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/8 16:45
* @Tel  17328595627
*/ 
public interface VaccineChangeDao {
    /** 
    * @Description: 根据上次盘点时间和截止时间查询入库记录
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/9 13:56
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryInputList(Map<String, Object> queryMap);
    /** 
    * @Description:  根据上次盘点时间和截止时间查询报损记录
     * @Param: [queryMap]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/9 14:41
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryDamageList(Map<String, Object> queryMap);
    /** 
    * @Description: 根据上次盘点时间和截止时间查询退货记录
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/9 14:56
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReturnList(Map<String, Object> queryMap);
    /** 
    * @Description: 根据上次盘点时间和截止时间查询使用人份和报损人份
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/9 15:07
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryUseAndDestroyList(Map<String, Object> queryMap);
    /** 
    * @Description: 查询库存记录
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/9 15:53
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryInfoListMap(Map<String, Object> queryMap);
    /** 
    * @Description: 查询盘点时间集合
    * @Param: [] 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/9 16:14
    * @Tel  17328595627
    */
    List<Map<String, Object>> getCheckTimeList();
    /** 
    * @Description: 查询上月结余 
    * @Param: [] 
    * @return: java.util.List<io.yfjz.entity.mgr.TMgrStockInfoEntity> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/19 16:36
    * @Tel  17328595627
     * @param queryMap
    */ 
    List<Map<String, Object>> queryBeforeMonth(Map<String, Object> queryMap);
}
