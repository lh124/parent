package io.yfjz.service.rule.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yfjz.dao.rule.TRuleHivDao;
import io.yfjz.entity.rule.TRuleHivEntity;
import io.yfjz.service.rule.TRuleHivService;



/**
 * 母亲感染HIV所生儿童接种规则表
 * @作者：邓召仕
 * 上午9:13:35
 */
@Service("tRuleHivService")
public class TRuleHivServiceImpl implements TRuleHivService {
	@Autowired
	private TRuleHivDao tRuleHivDao;
	
	@Override
	public TRuleHivEntity queryObject(String id){
		return tRuleHivDao.queryObject(id);
	}
	
	@Override
	public List<TRuleHivEntity> queryList(Map<String, Object> map){
		return tRuleHivDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tRuleHivDao.queryTotal(map);
	}
	
	@Override
	public void save(TRuleHivEntity tRuleHiv){
		tRuleHivDao.save(tRuleHiv);
	}
	
	@Override
	public void update(TRuleHivEntity tRuleHiv){
		tRuleHivDao.update(tRuleHiv);
	}
	
	@Override
	public void delete(String id){
		tRuleHivDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tRuleHivDao.deleteBatch(ids);
	}
	
}
