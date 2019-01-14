package io.yfjz.service.child.impl;

import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.child.TChildTabooDao;
import io.yfjz.entity.child.TChildTabooEntity;
import io.yfjz.service.child.TChildTabooService;



/**
 * 儿童禁忌表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:47:59
 */
@Service("tChildTabooService")
public class TChildTabooServiceImpl implements TChildTabooService {
	@Autowired
	private TChildTabooDao tChildTabooDao;
	
	@Override
	public TChildTabooEntity queryObject(String id){
		return tChildTabooDao.queryObject(id);
	}
	
	@Override
	public List<TChildTabooEntity> queryList(Map<String, Object> map){
		return tChildTabooDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildTabooDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildTabooEntity tChildTaboo){
		tChildTaboo.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		tChildTaboo.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
		tChildTabooDao.save(tChildTaboo);
	}
	
	@Override
	public void update(TChildTabooEntity tChildTaboo){
		tChildTabooDao.update(tChildTaboo);
	}
	
	@Override
	public void delete(String id){
		tChildTabooDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildTabooDao.deleteBatch(ids);
	}
	
}
