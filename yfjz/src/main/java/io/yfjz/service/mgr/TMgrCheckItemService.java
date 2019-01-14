package io.yfjz.service.mgr;


import io.yfjz.entity.mgr.TMgrCheckItemEntity;

import java.util.List;
import java.util.Map;

/** 
* @Description: 盘点明细表 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/7 16:23
* @Tel  17328595627
*/ 
public interface TMgrCheckItemService {
	
	TMgrCheckItemEntity queryObject(String id);
	
	List<TMgrCheckItemEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TMgrCheckItemEntity tMgrCheckItem);
	
	void update(TMgrCheckItemEntity tMgrCheckItem);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	/** 
	* @Description: 根据汇总ID查询判断明细集合 
	* @Param: [map] 
	* @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/8 15:44
	* @Tel  17328595627
	*/ 
	List<Map<String,Object>> queryListMap(Map<String, Object> map);
}
