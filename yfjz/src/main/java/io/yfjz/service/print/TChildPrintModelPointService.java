package io.yfjz.service.print;

import io.yfjz.entity.print.TChildPrintModelPointEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童打印接种记录模板坐标
 *
 * @author饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-08-17 11:27:06
 */
public interface TChildPrintModelPointService {
	
	TChildPrintModelPointEntity queryObject(String id);
	
	List<TChildPrintModelPointEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildPrintModelPointEntity tChildPrintModelPoint);
	
	void update(TChildPrintModelPointEntity tChildPrintModelPoint);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
