package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.TMgrStockOutItemDao;
import io.yfjz.entity.mgr.TMgrStockOutItemEntity;
import io.yfjz.service.mgr.TMgrStockOutItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;





/**
 * 疫苗出库记录明细表
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-13 15:46:21
 */
@Service("tMgrStockOutItemService")
public class TMgrStockOutItemServiceImpl implements TMgrStockOutItemService {
	@Autowired
	private TMgrStockOutItemDao tMgrStockOutItemDao;
	
	@Override
	public TMgrStockOutItemEntity queryObject(String id){
		return tMgrStockOutItemDao.queryObject(id);
	}
	
	@Override
	public List<TMgrStockOutItemEntity> queryList(Map<String, Object> map){
		return tMgrStockOutItemDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tMgrStockOutItemDao.queryTotal(map);
	}
	
	@Override
	public void save(TMgrStockOutItemEntity tMgrStockOutItem){
		tMgrStockOutItemDao.save(tMgrStockOutItem);
	}
	
	@Override
	public void update(TMgrStockOutItemEntity tMgrStockOutItem){
		tMgrStockOutItemDao.update(tMgrStockOutItem);
	}
	
	@Override
	public void delete(String id){
		tMgrStockOutItemDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tMgrStockOutItemDao.deleteBatch(ids);
	}
	
}
