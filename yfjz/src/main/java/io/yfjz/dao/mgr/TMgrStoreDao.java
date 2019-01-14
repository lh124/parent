package io.yfjz.dao.mgr;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/** 
* @Description: 仓库表 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 13:40
* @Tel  17328595627
*/
public interface TMgrStoreDao extends BaseDao<TMgrStoreEntity> {
    int updateStatus(TMgrStoreEntity store);
    /**
     * @Description: 仓库绑定设备
     * @Param: [storeId, equipmentIds]
     * @return: int
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/7/23 15:57
     * @Tel  17328595627
     */
    int insertRelation(List<Map<String, Object>> relations);
    /**
     * @Description: 删除仓库绑定的设备
     * @Param: [storeId]
     * @return: int
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/7/23 16:25
     * @Tel  17328595627
     */
    int deleteRelation(String storeId);

    /**
     * 查询所有启用的仓库
     * @return
     * @param orgId
     */
    List<TMgrStoreEntity> getAllStore(@Param("orgId") String orgId, @Param("ttype")int type);
    /**
     * @Description: 查询仓库管理的所有设备
     * @Param: [storeId]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/8/6 11:28
     * @Tel  17328595627
     */
    List<Map<String,Object>> getEquipmentById(String storeId);
    /**
     * @Description: 根据接种台绑定的ID查询对应的仓库
     * @Param: [towerId]
     * @return: io.yfjz.entity.mgr.TMgrStoreEntity
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/8/13 9:28
     * @Tel  17328595627
     */
    TMgrStoreEntity selectByTowerId(String towerId);
    /** 
    * @Description: 根据用户ID查询对应的仓库
    * @Param: [userId] 
    * @return: io.yfjz.entity.mgr.TMgrStoreEntity 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/12/1 12:34
    * @Tel  17328595627
    */
    TMgrStoreEntity selectByUserId(String userId);
}
