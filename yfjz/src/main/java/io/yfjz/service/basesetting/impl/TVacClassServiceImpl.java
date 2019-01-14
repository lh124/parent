package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TVacClassDao;
import io.yfjz.entity.basesetting.TVacClassEntity;
import io.yfjz.service.basesetting.TVacClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




/**
 * 疫苗分类种类表
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
@Service("tVacClassService")
public class TVacClassServiceImpl implements TVacClassService {
	@Autowired
	private TVacClassDao tVacClassDao;
	
	@Override
	public TVacClassEntity queryObject(String classCode){
		return tVacClassDao.queryObject(classCode);
	}
	
	@Override
	public List<TVacClassEntity> queryList(Map<String, Object> map){
		return tVacClassDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tVacClassDao.queryTotal(map);
	}
	
	@Override
	public void save(TVacClassEntity tVacClass){
		tVacClassDao.save(tVacClass);
	}
	
	@Override
	public void update(TVacClassEntity tVacClass){
		tVacClassDao.update(tVacClass);
	}
	
	@Override
	public void delete(String classCode){
		tVacClassDao.delete(classCode);
	}
	
	@Override
	public void deleteBatch(String[] classCodes){
		tVacClassDao.deleteBatch(classCodes);
	}

	@Override
	public List<TVacClassEntity> queryAllVaccClassList() {
		return tVacClassDao.queryAllVaccClassList();
	}

}
