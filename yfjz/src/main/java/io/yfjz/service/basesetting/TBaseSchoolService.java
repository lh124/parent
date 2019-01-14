package io.yfjz.service.basesetting;

import io.yfjz.entity.basesetting.TBaseSchoolEntity;

import java.util.List;
import java.util.Map;

/**
 * 入学入托机构表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-09 16:30:51
 */
public interface TBaseSchoolService {
	
	TBaseSchoolEntity queryObject(String code);
	
	List<TBaseSchoolEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TBaseSchoolEntity tBaseSchool);
	
	void update(TBaseSchoolEntity tBaseSchool);
	
	void delete(String code);
	
	void deleteBatch(String[] codes);
}
