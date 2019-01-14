package io.yfjz.dao.mgr;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.TMgrStockOutItemEntity;

import java.util.List;
import java.util.Map;

/**
 * 疫苗出库记录明细表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-13 15:46:21
 */
public interface TMgrStockOutItemDao extends BaseDao<TMgrStockOutItemEntity> {
    /** 
    * @Description: 查询疫苗领用明细记录
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 14:35
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReceiveItemList(Map<String, Object> map);

    int queryReceiveItemListTotal(Map<String, Object> map);
}
