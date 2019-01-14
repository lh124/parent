package io.yfjz.service.sys.impl;

import io.yfjz.dao.sys.SysHash256Dao;
import io.yfjz.entity.sys.SysHash256Entity;
import io.yfjz.service.sys.SysHash256Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("sysHash256Service")
public class SysHash256ServiceImpl implements SysHash256Service {
	@Autowired
	private SysHash256Dao sysHash256Dao;
	
	@Override
	public SysHash256Entity queryObject(String hashcode){
		return sysHash256Dao.queryObject(hashcode);
	}
	
	@Override
	public List<SysHash256Entity> queryList(Map<String, Object> map){
		return sysHash256Dao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysHash256Dao.queryTotal(map);
	}
	
	@Override
	public void save(SysHash256Entity sysHash256){
		sysHash256Dao.save(sysHash256);
	}
	
	@Override
	public void update(SysHash256Entity sysHash256){
		sysHash256Dao.update(sysHash256);
	}
	
	@Override
	public void delete(String hashcode){
		sysHash256Dao.delete(hashcode);
	}
	
	@Override
	public void deleteBatch(String[] hashcodes){
		sysHash256Dao.deleteBatch(hashcodes);
	}
	
}
