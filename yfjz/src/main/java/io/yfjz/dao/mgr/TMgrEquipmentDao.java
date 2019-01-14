package io.yfjz.dao.mgr;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.TMgrEquipmentEntity;
import org.apache.ibatis.annotations.Param;

/** 
* @Description: 设备管理 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 15:08
* @Tel  17328595627
*/ 
public interface TMgrEquipmentDao extends BaseDao<TMgrEquipmentEntity> {

     int updateStatus(@Param("id") String s, @Param("orgId") String id);

     /**
      * 根据id和结构编码查询数据
      * @param id
      * @param orgId
      * @return
      */
     TMgrEquipmentEntity queryOneObject(@Param("id") String id, @Param("orgId") String orgId);
}
