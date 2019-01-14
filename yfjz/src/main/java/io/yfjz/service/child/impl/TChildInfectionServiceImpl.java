package io.yfjz.service.child.impl;

import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.child.TChildInfectionDao;
import io.yfjz.entity.child.TChildInfectionEntity;
import io.yfjz.service.child.TChildInfectionService;



/**
 * 儿童传染病表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:37:19
 */
@Service("tChildInfectionService")
public class TChildInfectionServiceImpl implements TChildInfectionService {
	@Autowired
	private TChildInfectionDao tChildInfectionDao;
	
	@Override
	public TChildInfectionEntity queryObject(String id){
		return tChildInfectionDao.queryObject(id);
	}
	
	@Override
	public List<TChildInfectionEntity> queryList(Map<String, Object> map){
		return tChildInfectionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildInfectionDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildInfectionEntity tChildInfection){
		tChildInfection.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		tChildInfection.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
		tChildInfection.setOrgId(Constant.GLOBAL_ORG_ID);
		tChildInfectionDao.save(tChildInfection);
	}
	
	@Override
	public void update(TChildInfectionEntity tChildInfection){
		tChildInfectionDao.update(tChildInfection);
	}
	
	@Override
	public void delete(String id){
		tChildInfectionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildInfectionDao.deleteBatch(ids);
	}
	
}
