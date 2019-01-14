package io.yfjz.service.queue.impl;

import io.yfjz.dao.queue.TQueueNoticeDao;
import io.yfjz.entity.queue.TQueueNoticeEntity;
import io.yfjz.service.queue.TQueueNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("tQueueNoticeService")
public class TQueueNoticeServiceImpl implements TQueueNoticeService {
	@Autowired
	private TQueueNoticeDao tQueueNoticeDao;
	
	@Override
	public TQueueNoticeEntity queryObject(String id){
		return tQueueNoticeDao.queryObject(id);
	}
	
	@Override
	public List<TQueueNoticeEntity> queryList(Map<String, Object> map){
		return tQueueNoticeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tQueueNoticeDao.queryTotal(map);
	}
	
	@Override
	public void save(TQueueNoticeEntity tQueueNotice){
		tQueueNoticeDao.save(tQueueNotice);
	}
	
	@Override
	public void update(TQueueNoticeEntity tQueueNotice){
		tQueueNoticeDao.update(tQueueNotice);
	}
	
	@Override
	public void delete(String id){
		tQueueNoticeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tQueueNoticeDao.deleteBatch(ids);
	}
	
}
