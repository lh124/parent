package io.yfjz.service.child.impl;

import io.yfjz.dao.child.TSPositionDao;
import io.yfjz.entity.child.TSPositionEntity;
import io.yfjz.service.child.TSPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



/**
 * 省市县镇村数据
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-11-12 20:38:42
 */
@Service("tSPositionService")
public class TSPositionServiceImpl implements TSPositionService {
	@Autowired
	private TSPositionDao tSPositionDao;
	
	@Override
	public TSPositionEntity queryObject(Integer id){
		return tSPositionDao.queryObject(id);
	}
	
	@Override
	public List<TSPositionEntity> queryList(Map<String, Object> map){
		return tSPositionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tSPositionDao.queryTotal(map);
	}
	
	@Override
	public void save(TSPositionEntity tSPosition){
		tSPositionDao.save(tSPosition);
	}
	
	@Override
	public void update(TSPositionEntity tSPosition){
		tSPositionDao.update(tSPosition);
	}
	
	@Override
	public void delete(Integer id){
		tSPositionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		tSPositionDao.deleteBatch(ids);
	}
	@Override
	public List<Map<String, Object>> queryprovince(Map<String, Object> map) {
		return tSPositionDao.queryprovince(map);
	}

	@Override
	public List<Map<String, Object>> querycity(Map<String, Object> map) {
		return tSPositionDao.querycity(map);
	}

	@Override
	public List<Map<String, Object>> querycounty(Map<String, Object> map) {
		return tSPositionDao.querycounty(map);
	}

	@Override
	public List<Map<String, Object>> querytown(Map<String, Object> map) {
		return tSPositionDao.querytown(map);
	}
}
