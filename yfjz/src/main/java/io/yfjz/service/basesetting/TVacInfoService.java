package io.yfjz.service.basesetting;

import io.yfjz.entity.basesetting.TVacInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 疫苗信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
public interface TVacInfoService {
	
	TVacInfoEntity queryObject(String bioCode);
	
	List<TVacInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TVacInfoEntity tVacInfo);
	
	void update(TVacInfoEntity tVacInfo);
	
	void delete(String bioCode);
	
	void deleteBatch(String[] bioCodes);

    List<TVacInfoEntity> getAllData();

}
