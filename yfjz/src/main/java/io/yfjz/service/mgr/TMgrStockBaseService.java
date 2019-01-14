package io.yfjz.service.mgr;


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
public interface TMgrStockBaseService {
	
	TMgrStockBaseEntity queryObject(String id);
	
	List<TMgrStockBaseEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TMgrStockBaseEntity tMgrStockBase);
	
	void update(TMgrStockBaseEntity tMgrStockBase);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	/** 
	* @Description: 更新产品的状态 
	* @Param: [id] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/27 15:19
	* @Tel  17328595627
	*/ 
	void updateStatus(String id);
	/** 
	* @Description: 查询产品基础表中的所有数据，并按照录入时间排序 
	* @Param: [] 
	* @return: java.util.List<io.yfjz.entity.mgr.TMgrStockBaseEntity> 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/27 17:54
	* @Tel  17328595627
	*/ 
    List<TMgrStockBaseEntity> getAllData();
	/** 
	* @Description: 查询最近添加的疫苗信息 
	* @Param: [code] 
	* @return: io.yfjz.entity.mgr.TMgrStockBaseEntity 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/10/12 16:17
	* @Tel  17328595627
	*/ 
    TMgrStockBaseEntity getCodeInfo(String code);

	/**
	 * 获取疫苗所有批号
	 * @return java.util.List<io.yfjz.entity.mgr.TMgrStockBaseEntity>
	 */
	List<TMgrStockBaseEntity> getAllBatchnum();
}
