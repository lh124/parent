package io.yfjz.service.queue.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.queue.TQueueNoOperateDao;
import io.yfjz.entity.queue.TQueueNoOperateEntity;
import io.yfjz.service.queue.TQueueNoOperateService;



/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-25 02:25:55
 */
@Service("tQueueNoOperateService")
public class TQueueNoOperateServiceImpl implements TQueueNoOperateService {
	@Autowired
	private TQueueNoOperateDao tQueueNoOperateDao;
	
	@Override
	public TQueueNoOperateEntity queryObject(String id){
		return tQueueNoOperateDao.queryObject(id);
	}
	
	@Override
	public List<TQueueNoOperateEntity> queryList(Map<String, Object> map){
		return tQueueNoOperateDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tQueueNoOperateDao.queryTotal(map);
	}
	
	@Override
	public void save(TQueueNoOperateEntity tQueueNoOperate){
		tQueueNoOperateDao.save(tQueueNoOperate);
	}
	
	@Override
	public void update(TQueueNoOperateEntity tQueueNoOperate){
		tQueueNoOperateDao.update(tQueueNoOperate);
	}
	
	@Override
	public void delete(String id){
		tQueueNoOperateDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tQueueNoOperateDao.deleteBatch(ids);
	}
	
}
