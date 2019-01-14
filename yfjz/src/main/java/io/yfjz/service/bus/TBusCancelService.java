package io.yfjz.service.bus;



import io.yfjz.entity.bus.TBusCancelEntity;

import java.util.List;
import java.util.Map;

/**
 * 取消原因表
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-26 09:54:38
 */
public interface TBusCancelService {
	
	TBusCancelEntity queryObject(String id);
	
	List<TBusCancelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TBusCancelEntity tBusCancel);

	void saveRelsult(TBusCancelEntity tBusCancel, String chilCode);

	void update(TBusCancelEntity tBusCancel);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
