package io.yfjz.service.child.impl;

import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.child.TChildAllergyDao;
import io.yfjz.entity.child.TChildAllergyEntity;
import io.yfjz.service.child.TChildAllergyService;



/**
 * 儿童过敏表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */
@Service("tChildAllergyService")
public class TChildAllergyServiceImpl implements TChildAllergyService {
	@Autowired
	private TChildAllergyDao tChildAllergyDao;
	
	@Override
	public TChildAllergyEntity queryObject(String id){
		return tChildAllergyDao.queryObject(id);
	}
	
	@Override
	public List<TChildAllergyEntity> queryList(Map<String, Object> map){
		return tChildAllergyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildAllergyDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildAllergyEntity tChildAllergy){
		tChildAllergy.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		tChildAllergy.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
		tChildAllergyDao.save(tChildAllergy);
	}
	
	@Override
	public void update(TChildAllergyEntity tChildAllergy){
		tChildAllergyDao.update(tChildAllergy);
	}
	
	@Override
	public void delete(String id){
		tChildAllergyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildAllergyDao.deleteBatch(ids);
	}
	
}
