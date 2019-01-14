package io.yfjz.service.mgr;


import io.yfjz.entity.mgr.TMgrCheckEntity;

import java.text.ParseException;
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
public interface TMgrCheckService {
	
	TMgrCheckEntity queryObject(String id);
	
	List<TMgrCheckEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TMgrCheckEntity tMgrCheck);
	
	void update(TMgrCheckEntity tMgrCheck);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	/** 
	* @Description: 保存盘点数据
	* @Param: [param] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/8 11:47
	* @Tel  17328595627
	*/ 
    void saveResult(Map param) throws ParseException;
}
