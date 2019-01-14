package io.yfjz.service.basesetting;



import io.yfjz.entity.basesetting.TVacClassEntity;

import java.util.List;
import java.util.Map;

/**
 * 疫苗分类种类表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
public interface TVacClassService {
	
	TVacClassEntity queryObject(String classCode);
	
	List<TVacClassEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TVacClassEntity tVacClass);
	
	void update(TVacClassEntity tVacClass);
	
	void delete(String classCode);
	
	void deleteBatch(String[] classCodes);

	List<TVacClassEntity> queryAllVaccClassList();

}
