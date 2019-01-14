package io.yfjz.service.queue;


import io.yfjz.entity.queue.TQueueNoticeEntity;

import java.util.List;
import java.util.Map;

public interface TQueueNoticeService {
	
	TQueueNoticeEntity queryObject(String id);
	
	List<TQueueNoticeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TQueueNoticeEntity tQueueNotice);
	
	void update(TQueueNoticeEntity tQueueNotice);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
