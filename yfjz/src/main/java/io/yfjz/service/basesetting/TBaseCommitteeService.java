package io.yfjz.service.basesetting;


import io.yfjz.entity.basesetting.TBaseCommitteeEntity;

import java.util.List;
import java.util.Map;

/**
 * 居委会表(居委会/行政村)
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-28 10:45:07
 */
public interface TBaseCommitteeService {
	
	TBaseCommitteeEntity queryObject(String code);
	
	List<TBaseCommitteeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TBaseCommitteeEntity tBaseCommittee);
	
	void update(TBaseCommitteeEntity tBaseCommittee);
	
	void delete(String code);
	
	void deleteBatch(String[] codes);
}
