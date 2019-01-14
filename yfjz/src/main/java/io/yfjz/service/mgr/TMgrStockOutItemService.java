package io.yfjz.service.mgr;


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
public interface TMgrStockOutItemService {
	
	TMgrStockOutItemEntity queryObject(String id);
	
	List<TMgrStockOutItemEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TMgrStockOutItemEntity tMgrStockOutItem);
	
	void update(TMgrStockOutItemEntity tMgrStockOutItem);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
