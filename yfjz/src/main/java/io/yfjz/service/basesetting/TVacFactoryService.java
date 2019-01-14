package io.yfjz.service.basesetting;


import io.yfjz.entity.basesetting.TVacFactoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 疫苗生产厂家
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
public interface TVacFactoryService {
	
	TVacFactoryEntity queryObject(String factoryCode);
	
	List<TVacFactoryEntity> queryList();
	
	int queryTotal(Map<String, Object> map);
	
	void save(TVacFactoryEntity tVacFactory);
	
	void update(TVacFactoryEntity tVacFactory);
	
	void delete(String factoryCode);
	
	void deleteBatch(String[] factoryCodes);

	/**
	 * @Description: 查询所有厂家的信息
	 * @Param: []
	 * @return: java.util.List<io.yfjz.entity.medical.TVacFactoryEntity>
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/8/2 10:45
	 * @Tel  17328595627
	 */
	List<TVacFactoryEntity> getAllData();
}
