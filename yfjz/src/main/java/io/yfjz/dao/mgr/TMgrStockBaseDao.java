package io.yfjz.dao.mgr;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.TMgrStockBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 库存产品基础信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 11:13:14
 */
public interface TMgrStockBaseDao extends BaseDao<TMgrStockBaseEntity> {

    void updateStatus(String id);

    List<TMgrStockBaseEntity> getAllData(String orgId);

    List<TMgrStockBaseEntity> getAllBatchnum(String orgId);

    Map<String,Object> queryListByCodeId(String code);
    /** 
    * @Description: 根据疫苗批号，疫苗Id，失效期，检查是否存在 
    * @Param: [tMgrStockBase] 
    * @return: io.yfjz.entity.mgr.TMgrStockBaseEntity 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/12 12:34
    * @Tel  17328595627
    */ 
    TMgrStockBaseEntity queryByBatchnumAndExpiryDate(TMgrStockBaseEntity tMgrStockBase);

    TMgrStockBaseEntity getCodeInfo(String code);
}
