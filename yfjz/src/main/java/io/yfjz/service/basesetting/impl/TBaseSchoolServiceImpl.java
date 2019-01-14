package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TBaseSchoolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.entity.basesetting.TBaseSchoolEntity;
import io.yfjz.service.basesetting.TBaseSchoolService;



/**
 * 入学入托机构表
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-09 16:30:51
 */
@Service("tBaseSchoolService")
public class TBaseSchoolServiceImpl implements TBaseSchoolService {
	@Autowired
	private TBaseSchoolDao tBaseSchoolDao;
	
	@Override
	public TBaseSchoolEntity queryObject(String code){
		return tBaseSchoolDao.queryObject(code);
	}
	
	@Override
	public List<TBaseSchoolEntity> queryList(Map<String, Object> map){
		return tBaseSchoolDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tBaseSchoolDao.queryTotal(map);
	}
	
	@Override
	public void save(TBaseSchoolEntity tBaseSchool){
		tBaseSchoolDao.save(tBaseSchool);
	}
	
	@Override
	public void update(TBaseSchoolEntity tBaseSchool){
		tBaseSchoolDao.update(tBaseSchool);
	}
	
	@Override
	public void delete(String code){
		tBaseSchoolDao.delete(code);
	}
	
	@Override
	public void deleteBatch(String[] codes){
		tBaseSchoolDao.deleteBatch(codes);
	}
	
}
