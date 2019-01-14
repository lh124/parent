package io.yfjz.service.print;

import io.yfjz.entity.print.TChildPrintModelEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童打印模板
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-17 11:27:05
 */
public interface TChildPrintModelService {
	
	TChildPrintModelEntity queryObject(Long id);
	
	List<TChildPrintModelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildPrintModelEntity tChildPrintModel);
	
	void update(TChildPrintModelEntity tChildPrintModel);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
