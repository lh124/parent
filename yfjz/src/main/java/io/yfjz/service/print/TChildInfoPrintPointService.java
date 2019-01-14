package io.yfjz.service.print;

import io.yfjz.entity.print.TChildInfoPrintPointEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童打印基本信息打印坐标
 *
 * @author饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-08-17 11:27:06
 */
public interface TChildInfoPrintPointService {
	
	TChildInfoPrintPointEntity queryObject(String id);
	
	List<TChildInfoPrintPointEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildInfoPrintPointEntity tChildInfoPrintPoint);
	
	void update(TChildInfoPrintPointEntity tChildInfoPrintPoint);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
