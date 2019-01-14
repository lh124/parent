package io.yfjz.service.provinceplatform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.provinceplatform.TChildElsewhereDao;
import io.yfjz.entity.provinceplatform.TChildElsewhereEntity;
import io.yfjz.service.provinceplatform.TChildElsewhereService;



/**
 * 异地儿童接种信息
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-09-12 11:22:13
 */
@Service("tChildElsewhereService")
public class TChildElsewhereServiceImpl implements TChildElsewhereService {
	@Autowired
	private TChildElsewhereDao tChildElsewhereDao;
	
	@Override
	public TChildElsewhereEntity queryObject(String id){
		return tChildElsewhereDao.queryObject(id);
	}
	
	@Override
	public List<TChildElsewhereEntity> queryList(Map<String, Object> map){
		return tChildElsewhereDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildElsewhereDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildElsewhereEntity tChildElsewhere){
		tChildElsewhereDao.save(tChildElsewhere);
	}
	
	@Override
	public void update(TChildElsewhereEntity tChildElsewhere){
		tChildElsewhereDao.update(tChildElsewhere);
	}
	
	@Override
	public void delete(String id){
		tChildElsewhereDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildElsewhereDao.deleteBatch(ids);
	}
	
}
