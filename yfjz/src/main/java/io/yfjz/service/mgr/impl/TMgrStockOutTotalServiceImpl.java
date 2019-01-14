package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.TMgrStockOutTotalDao;
import io.yfjz.entity.mgr.TMgrStockOutTotalEntity;
import io.yfjz.service.mgr.TMgrStockOutTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




/**
 * 疫苗出库记录表
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-13 15:46:21
 */
@Service("tMgrStockOutTotalService")
public class TMgrStockOutTotalServiceImpl implements TMgrStockOutTotalService {
	@Autowired
	private TMgrStockOutTotalDao tMgrStockOutTotalDao;
	
	@Override
	public TMgrStockOutTotalEntity queryObject(String id){
		return tMgrStockOutTotalDao.queryObject(id);
	}
	
	@Override
	public List<TMgrStockOutTotalEntity> queryList(Map<String, Object> map){
		return tMgrStockOutTotalDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tMgrStockOutTotalDao.queryTotal(map);
	}
	
	@Override
	public void save(TMgrStockOutTotalEntity tMgrStockOutTotal){
		tMgrStockOutTotalDao.save(tMgrStockOutTotal);
	}
	
	@Override
	public void update(TMgrStockOutTotalEntity tMgrStockOutTotal){
		tMgrStockOutTotalDao.update(tMgrStockOutTotal);
	}
	
	@Override
	public void delete(String id){
		tMgrStockOutTotalDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tMgrStockOutTotalDao.deleteBatch(ids);
	}
	
}
