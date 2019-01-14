package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TVacInfoDao;
import io.yfjz.entity.basesetting.TVacInfoEntity;
import io.yfjz.service.basesetting.TVacInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;





/**
 * 疫苗信息表
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
@Service("tVacInfoService")
public class TVacInfoServiceImpl implements TVacInfoService {
	@Autowired
	private TVacInfoDao tVacInfoDao;
	
	@Override
	public TVacInfoEntity queryObject(String bioCode){
		return tVacInfoDao.queryObject(bioCode);
	}
	
	@Override
	public List<TVacInfoEntity> queryList(Map<String,Object> map){
		return tVacInfoDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return tVacInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(TVacInfoEntity tVacInfo){
		tVacInfoDao.save(tVacInfo);
	}
	
	@Override
	public void update(TVacInfoEntity tVacInfo){
		tVacInfoDao.update(tVacInfo);
	}
	
	@Override
	public void delete(String bioCode){
		tVacInfoDao.delete(bioCode);
	}
	
	@Override
	public void deleteBatch(String[] bioCodes){
		tVacInfoDao.deleteBatch(bioCodes);
	}

	@Override
	public List<TVacInfoEntity> getAllData() {
		List<TVacInfoEntity> vac = tVacInfoDao.getAllVaccine();
		return vac;
	}
}
