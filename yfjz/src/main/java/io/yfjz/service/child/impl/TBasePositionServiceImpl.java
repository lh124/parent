package io.yfjz.service.child.impl;


import io.yfjz.dao.child.TBasePositionDao;
import io.yfjz.entity.child.TBasePositionEntity;
import io.yfjz.service.child.TBasePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 省市县镇村数据
 *
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-30 13:41:54
 */
@Service("tBasePositionService")
public class TBasePositionServiceImpl implements TBasePositionService {
	@Autowired
	private TBasePositionDao tBasePositionDao;
	
	@Override
	public TBasePositionEntity queryObject(String id){
		return tBasePositionDao.queryObject(id);
	}
	
	@Override
	public List<TBasePositionEntity> queryList(Map<String, Object> map){
		return tBasePositionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tBasePositionDao.queryTotal(map);
	}
	
	@Override
	public void save(TBasePositionEntity tBasePosition){
		tBasePositionDao.save(tBasePosition);
	}
	
	@Override
	public void update(TBasePositionEntity tBasePosition){
		tBasePositionDao.update(tBasePosition);
	}
	
	@Override
	public void delete(String id){
		tBasePositionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tBasePositionDao.deleteBatch(ids);
	}

	@Override
	public List<Map<String, Object>> queryprovince(Map<String, Object> map) {
		return tBasePositionDao.queryprovince(map);
	}

	@Override
	public List<Map<String, Object>> querycity(Map<String, Object> map) {
		return tBasePositionDao.querycity(map);
	}

	@Override
	public List<Map<String, Object>> querycounty(Map<String, Object> map) {
		return tBasePositionDao.querycounty(map);
	}

	@Override
	public List<Map<String, Object>> querytown(Map<String, Object> map) {
		return tBasePositionDao.querytown(map);
	}
	@Override
	public List<Map<String, Object>> gethospital(Map<String, Object> map) {
		return tBasePositionDao.gethospital(map);
	}


}
