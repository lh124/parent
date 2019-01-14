package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TBaseHospitalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.entity.basesetting.TBaseHospitalEntity;
import io.yfjz.service.basesetting.TBaseHospitalService;



/**
 * 儿童出生医院
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-09 17:05:08
 */
@Service("tBaseHospitalService")
public class TBaseHospitalServiceImpl implements TBaseHospitalService {
	@Autowired
	private TBaseHospitalDao tBaseHospitalDao;
	
	@Override
	public TBaseHospitalEntity queryObject(String hospitalCode){
		return tBaseHospitalDao.queryObject(hospitalCode);
	}
	
	@Override
	public List<TBaseHospitalEntity> queryList(Map<String, Object> map){
		return tBaseHospitalDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tBaseHospitalDao.queryTotal(map);
	}
	
	@Override
	public void save(TBaseHospitalEntity tBaseHospital){
		tBaseHospitalDao.save(tBaseHospital);
	}
	
	@Override
	public void update(TBaseHospitalEntity tBaseHospital){
		tBaseHospitalDao.update(tBaseHospital);
	}
	
	@Override
	public void delete(String hospitalCode){
		tBaseHospitalDao.delete(hospitalCode);
	}
	
	@Override
	public void deleteBatch(String[] hospitalCodes){
		tBaseHospitalDao.deleteBatch(hospitalCodes);
	}
	
}
