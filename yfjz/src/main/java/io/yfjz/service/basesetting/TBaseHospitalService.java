package io.yfjz.service.basesetting;

import io.yfjz.entity.basesetting.TBaseHospitalEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童出生医院
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-09 17:05:08
 */
public interface TBaseHospitalService {
	
	TBaseHospitalEntity queryObject(String hospitalCode);
	
	List<TBaseHospitalEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TBaseHospitalEntity tBaseHospital);
	
	void update(TBaseHospitalEntity tBaseHospital);
	
	void delete(String hospitalCode);
	
	void deleteBatch(String[] hospitalCodes);
}
