package io.yfjz.dao.mgr;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.TMgrCheckEntity;
import io.yfjz.entity.mgr.TMgrCheckItemEntity;

import java.util.List;
import java.util.Map;

/**
 * 盘点表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-07 15:12:26
 */
public interface TMgrCheckDao extends BaseDao<TMgrCheckEntity> {
    /** 
    * @Description: 查询最近的一次盘点记录
    * @Param: [] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/8 16:48
    * @Tel  17328595627
     * @param queryMap
    */ 
    List<TMgrCheckItemEntity> queryListByLately(Map<String, Object> queryMap);
    /** 
    * @Description: 检查接种台是否有疫苗未归还
    * @Param: [] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/10 9:40
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryTowerStock();
    /** 
    * @Description: 查询盘点单号 
    * @Param: [] 
    * @return: java.lang.String 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/12 15:14
    * @Tel  17328595627
    */ 
    String queryOrderId();
}
