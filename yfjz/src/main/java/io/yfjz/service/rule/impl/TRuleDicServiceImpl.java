package io.yfjz.service.rule.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yfjz.dao.rule.TRuleDicDao;
import io.yfjz.entity.rule.TRuleDicEntity;
import io.yfjz.service.rule.TRuleDicService;



/**
 * 免疫规划字典表
 * @作者：邓召仕
 * 上午9:13:19
 */
@Service("tRuleDicService")
public class TRuleDicServiceImpl implements TRuleDicService {
	@Autowired
	private TRuleDicDao tRuleDicDao;
	
	@Override
	public TRuleDicEntity queryObject(String id){
		return tRuleDicDao.queryObject(id);
	}
	
	@Override
	public List<TRuleDicEntity> queryList(Map<String, Object> map){
		return tRuleDicDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tRuleDicDao.queryTotal(map);
	}
	
	@Override
	public void save(TRuleDicEntity tRuleDic){
		if(tRuleDic != null){
			tRuleDic.setId(UUID.randomUUID().toString());
		}
		tRuleDicDao.save(tRuleDic);
	}
	
	@Override
	public void update(TRuleDicEntity tRuleDic){
		tRuleDicDao.update(tRuleDic);
	}
	
	@Override
	public void delete(String id){
		tRuleDicDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tRuleDicDao.deleteBatch(ids);
	}
	
}
