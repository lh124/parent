package io.yfjz.service.sys;


import io.yfjz.entity.sys.SysHash256Entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-01-05 10:29:20
 */
public interface SysHash256Service {
	
	SysHash256Entity queryObject(String hashcode);
	
	List<SysHash256Entity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysHash256Entity sysHash256);
	
	void update(SysHash256Entity sysHash256);
	
	void delete(String hashcode);
	
	void deleteBatch(String[] hashcodes);
}
