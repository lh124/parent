package io.yfjz.service.child.impl;

import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.child.TChildAbnormalDao;
import io.yfjz.entity.child.TChildAbnormalEntity;
import io.yfjz.service.child.TChildAbnormalService;



/**
 * 儿童异常反应表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */
@Service("tChildAbnormalService")
public class TChildAbnormalServiceImpl implements TChildAbnormalService {
	@Autowired
	private TChildAbnormalDao tChildAbnormalDao;
	
	@Override
	public TChildAbnormalEntity queryObject(String id){
		return tChildAbnormalDao.queryObject(id);
	}
	
	@Override
	public List<TChildAbnormalEntity> queryList(Map<String, Object> map){
		return tChildAbnormalDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildAbnormalDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildAbnormalEntity tChildAbnormal){
		tChildAbnormal.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		tChildAbnormal.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
		tChildAbnormal.setOrgId(Constant.GLOBAL_ORG_ID);
		tChildAbnormalDao.save(tChildAbnormal);
	}
	
	@Override
	public void update(TChildAbnormalEntity tChildAbnormal){
		tChildAbnormalDao.update(tChildAbnormal);
	}
	
	@Override
	public void delete(String id){
		tChildAbnormalDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildAbnormalDao.deleteBatch(ids);
	}
	
}
