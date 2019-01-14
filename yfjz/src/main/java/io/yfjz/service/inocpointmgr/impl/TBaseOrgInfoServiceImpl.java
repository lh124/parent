package io.yfjz.service.inocpointmgr.impl;

import io.yfjz.entity.mgr.TBaseOrgInfoEntity;
import io.yfjz.service.inocpointmgr.TBaseOrgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.inocpointmgr.TBaseOrgInfoDao;



/**
 * 卫生院基本信息表
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-09-04 17:03:45
 */
@Service("tBaseOrgInfoService")
public class TBaseOrgInfoServiceImpl implements TBaseOrgInfoService {
	@Autowired
	private TBaseOrgInfoDao tBaseOrgInfoDao;
	
	@Override
	public TBaseOrgInfoEntity queryObject(String id){
		return tBaseOrgInfoDao.queryObject(id);
	}
	
	@Override
	public List<TBaseOrgInfoEntity> queryList(Map<String, Object> map){
		return tBaseOrgInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tBaseOrgInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(TBaseOrgInfoEntity tBaseOrgInfo){
		tBaseOrgInfoDao.save(tBaseOrgInfo);
	}
	
	@Override
	public void update(TBaseOrgInfoEntity tBaseOrgInfo){
		tBaseOrgInfoDao.update(tBaseOrgInfo);
	}
	
	@Override
	public void delete(String id){
		tBaseOrgInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tBaseOrgInfoDao.deleteBatch(ids);
	}
	
}
