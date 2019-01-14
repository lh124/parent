package io.yfjz.service.queue;

import io.yfjz.entity.queue.TQueueNoOperateEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-25 02:25:55
 */
public interface TQueueNoOperateService {
	
	TQueueNoOperateEntity queryObject(String id);
	
	List<TQueueNoOperateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TQueueNoOperateEntity tQueueNoOperate);
	
	void update(TQueueNoOperateEntity tQueueNoOperate);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
