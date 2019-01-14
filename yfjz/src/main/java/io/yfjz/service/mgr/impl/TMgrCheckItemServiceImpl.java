package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.TMgrCheckItemDao;
import io.yfjz.entity.mgr.TMgrCheckItemEntity;
import io.yfjz.service.mgr.TMgrCheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;





/** 
* @Description: 盘点明细实现类 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/7 16:00
* @Tel  17328595627
*/ 
@Service("tMgrCheckItemService")
public class TMgrCheckItemServiceImpl implements TMgrCheckItemService {
	@Autowired
	private TMgrCheckItemDao tMgrCheckItemDao;
	
	@Override
	public TMgrCheckItemEntity queryObject(String id){
		return tMgrCheckItemDao.queryObject(id);
	}
	
	@Override
	public List<TMgrCheckItemEntity> queryList(Map<String, Object> map){
		return tMgrCheckItemDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tMgrCheckItemDao.queryTotal(map);
	}
	
	@Override
	public void save(TMgrCheckItemEntity tMgrCheckItem){
		tMgrCheckItemDao.save(tMgrCheckItem);
	}
	
	@Override
	public void update(TMgrCheckItemEntity tMgrCheckItem){
		tMgrCheckItemDao.update(tMgrCheckItem);
	}
	
	@Override
	public void delete(String id){
		tMgrCheckItemDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tMgrCheckItemDao.deleteBatch(ids);
	}

	@Override
	public List<Map<String, Object>> queryListMap(Map<String, Object> map) {
        List<Map<String, Object>> list = tMgrCheckItemDao.queryListMap(map);
        return list;
	}
}
