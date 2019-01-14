package io.yfjz.dao.mgr;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.TMgrStockOutTotalEntity;

import java.util.List;
import java.util.Map;

/**
 * 疫苗出库记录表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-13 15:46:21
 */
public interface TMgrStockOutTotalDao extends BaseDao<TMgrStockOutTotalEntity> {
    /** 
    * @Description: 根据入库库存编号查询 领取信息
    * @Param: [map]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 13:54
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryTotalList(Map<String, Object> map);

    int queryReceiveTotalListTotal(Map<String, Object> map);
}
