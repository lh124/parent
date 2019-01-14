package io.yfjz.service.mgr;

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
public interface TMgrStockOutTotalService {
	
	TMgrStockOutTotalEntity queryObject(String id);
	
	List<TMgrStockOutTotalEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TMgrStockOutTotalEntity tMgrStockOutTotal);
	
	void update(TMgrStockOutTotalEntity tMgrStockOutTotal);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
