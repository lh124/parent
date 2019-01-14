package io.yfjz.service.mgr;


import io.yfjz.entity.mgr.TMgrEquipmentEntity;

import java.util.List;
import java.util.Map;

/** 
* @Description: 设备管理 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 15:12
* @Tel  17328595627
*/ 
public interface TMgrEquipmentService {
	/** 
	* @Description: 根据ID查询设备
	* @Param: [id] 
	* @return: io.yfjz.entity.mgr.TMgrEquipmentEntity 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 15:13
	* @Tel  17328595627
	*/ 
	TMgrEquipmentEntity queryObject(String id);
	/** 
	* @Description: 查询所有的设备 
	* @Param: [map] 
	* @return: java.util.List<io.yfjz.entity.mgr.TMgrEquipmentEntity> 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 15:19
	* @Tel  17328595627
	*/ 
	List<TMgrEquipmentEntity> queryList(Map<String, Object> map);
	/** 
	* @Description: 查询设备总数 
	* @Param: [map] 
	* @return: int 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 15:19
	* @Tel  17328595627
	*/ 
	int queryTotal(Map<String, Object> map);
	/** 
	* @Description: 新增设备
	* @Param: [tMgrEquipment] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 15:19
	* @Tel  17328595627
	*/ 
	void save(TMgrEquipmentEntity tMgrEquipment);
	/** 
	* @Description: 更新设备信息 
	* @Param: [tMgrEquipment] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 15:19
	* @Tel  17328595627
	*/ 
	void update(TMgrEquipmentEntity tMgrEquipment);
    /** 
    * @Description: 设备是否启用 
    * @Param: 【id】 设备ID
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/7/23 15:21
    * @Tel  17328595627
    */ 
	int updateStatus(String id);



}
