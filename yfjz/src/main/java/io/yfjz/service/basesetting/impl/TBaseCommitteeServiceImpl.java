package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TBaseCommitteeDao;
import io.yfjz.entity.basesetting.TBaseCommitteeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.service.basesetting.TBaseCommitteeService;



/**
 * 居委会表(居委会/行政村)
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-28 10:45:07
 */
@Service("tBaseCommitteeService")
public class TBaseCommitteeServiceImpl implements TBaseCommitteeService {
	@Autowired
	private TBaseCommitteeDao tBaseCommitteeDao;
	
	@Override
	public TBaseCommitteeEntity queryObject(String code){
		return tBaseCommitteeDao.queryObject(code);
	}
	
	@Override
	public List<TBaseCommitteeEntity> queryList(Map<String, Object> map){
		return tBaseCommitteeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tBaseCommitteeDao.queryTotal(map);
	}
	
	@Override
	public void save(TBaseCommitteeEntity tBaseCommittee){
		tBaseCommitteeDao.save(tBaseCommittee);
	}
	
	@Override
	public void update(TBaseCommitteeEntity tBaseCommittee){
		tBaseCommitteeDao.update(tBaseCommittee);
	}
	
	@Override
	public void delete(String code){
		tBaseCommitteeDao.delete(code);
	}
	
	@Override
	public void deleteBatch(String[] codes){
		tBaseCommitteeDao.deleteBatch(codes);
	}
	
}
