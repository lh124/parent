package io.yfjz.dao.mgr;


import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.TMgrStockInTotalEntity;

/**
 * 疫苗入库记录汇总表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-30 09:40:37
 */
public interface TMgrStockInTotalDao extends BaseDao<TMgrStockInTotalEntity> {
    /** 
    * @Description: 查询入库单号
    * @Param: [] 
    * @return: java.lang.String 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/12 14:29
    * @Tel  17328595627
    */ 
    String queryOrderId();
}
