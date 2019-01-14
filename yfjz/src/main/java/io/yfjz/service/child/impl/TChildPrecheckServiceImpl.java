package io.yfjz.service.child.impl;

import io.yfjz.dao.child.TChildPrecheckDao;
import io.yfjz.entity.child.TChildPrecheckEntity;
import io.yfjz.service.child.TChildPrecheckService;
import io.yfjz.service.queue.TQueueNoService;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 预检信息表
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-23 09:41:20
 */
@Service("tChildPrecheckService")
public class TChildPrecheckServiceImpl implements TChildPrecheckService {
	@Autowired
	private TChildPrecheckDao tChildPrecheckDao;


	
	@Override
	public TChildPrecheckEntity queryObject(String id){
		return tChildPrecheckDao.queryObject(id);
	}
	
	@Override
	public List<TChildPrecheckEntity> queryList(Map<String, Object> map){
		return tChildPrecheckDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildPrecheckDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildPrecheckEntity tChildPrecheck){
		tChildPrecheck.setStatus(0);
		tChildPrecheck.setSyncstatus(0);
		tChildPrecheck.setType(0);
		tChildPrecheck.setDeleteStatus(0);
		tChildPrecheck.setCreateTime(new Date());
		tChildPrecheck.setOrgId(ShiroUtils.getUserEntity().getOrgId());
		tChildPrecheck.setCreateUserId(ShiroUtils.getUserId());
		tChildPrecheck.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
		tChildPrecheckDao.save(tChildPrecheck);


	}
	
	@Override
	public void update(TChildPrecheckEntity tChildPrecheck){
		tChildPrecheckDao.update(tChildPrecheck);
	}
	
	@Override
	public void delete(String id){
		tChildPrecheckDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildPrecheckDao.deleteBatch(ids);
	}

	@Override
	public List<TChildPrecheckEntity> sumprechecks() {
		return tChildPrecheckDao.sumprechecks();
	}

	@Override
	public void saveCheckNotice(TChildPrecheckEntity tChildPrecheck) {
		Map<String, Object> map = new HashMap<>();
		map.put("chilCode",tChildPrecheck.getChilCode());
		List<TChildPrecheckEntity> tChildPrecheckList = tChildPrecheckDao.queryList(map);
		//int row = tChildPrecheckDao.queryTotal(map);
		tChildPrecheck.setStatus(0);
		tChildPrecheck.setSyncstatus(0);
		tChildPrecheck.setType(0);
		tChildPrecheck.setDeleteStatus(0);
		tChildPrecheck.setCreateTime(new Date());
		tChildPrecheck.setOrgId(ShiroUtils.getUserEntity().getOrgId());
		if(tChildPrecheckList!=null && tChildPrecheckList.size()>0){
			tChildPrecheck.setId(tChildPrecheckList.get(0).getId());
			tChildPrecheckDao.update(tChildPrecheck);
		}else{
			tChildPrecheckDao.save(tChildPrecheck);
		}

	}
}
