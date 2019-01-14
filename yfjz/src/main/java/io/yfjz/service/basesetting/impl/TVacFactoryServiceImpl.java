package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TVacFactoryDao;
import io.yfjz.entity.basesetting.TVacFactoryEntity;
import io.yfjz.service.basesetting.TVacFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;





/**
 * 疫苗生产厂家
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
@Service("tVacFactoryService")
public class TVacFactoryServiceImpl implements TVacFactoryService {
	@Autowired
	private TVacFactoryDao tVacFactoryDao;
	
	@Override
	public TVacFactoryEntity queryObject(String factoryCode){
		return tVacFactoryDao.queryObject(factoryCode);
	}
	
	@Override
	public List<TVacFactoryEntity> queryList(){
		return tVacFactoryDao.selectList();
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tVacFactoryDao.queryTotal(map);
	}
	
	@Override
	public void save(TVacFactoryEntity tVacFactory){
		tVacFactoryDao.save(tVacFactory);
	}
	
	@Override
	public void update(TVacFactoryEntity tVacFactory){
		tVacFactoryDao.update(tVacFactory);
	}
	
	@Override
	public void delete(String factoryCode){
		tVacFactoryDao.delete(factoryCode);
	}
	
	@Override
	public void deleteBatch(String[] factoryCodes){
		tVacFactoryDao.deleteBatch(factoryCodes);
	}
	@Override
	public List<TVacFactoryEntity> getAllData() {
		List<TVacFactoryEntity> list=tVacFactoryDao.getAllData();
		return list;
	}
}
