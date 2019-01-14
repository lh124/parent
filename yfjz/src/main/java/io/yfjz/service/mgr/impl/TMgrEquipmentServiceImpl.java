package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.TMgrEquipmentDao;
import io.yfjz.entity.mgr.TMgrEquipmentEntity;
import io.yfjz.service.mgr.TMgrEquipmentService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("tMgrEquipmentService")
public class TMgrEquipmentServiceImpl implements TMgrEquipmentService {
	@Autowired
	private TMgrEquipmentDao tMgrEquipmentDao;


	@Override
	public TMgrEquipmentEntity queryObject(String id){
		return tMgrEquipmentDao.queryOneObject(id, Constant.GLOBAL_ORG_ID);
	}
	
	@Override
	public List<TMgrEquipmentEntity> queryList(Map<String, Object> map){
		map.put("orgId",Constant.GLOBAL_ORG_ID);
		return tMgrEquipmentDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		map.put("orgId",Constant.GLOBAL_ORG_ID);
		return tMgrEquipmentDao.queryTotal(map);
	}
	
	@Override
	public void save(TMgrEquipmentEntity tMgrEquipment){
		//设置用户ID
		tMgrEquipment.setCreateUserId(ShiroUtils.getUserId());
		//创建时间
		tMgrEquipment.setCreateTime(new Date());
		//机构ID
		tMgrEquipment.setOrgId(Constant.GLOBAL_ORG_ID);
		tMgrEquipmentDao.save(tMgrEquipment);
	}
	
	@Override
	public void update(TMgrEquipmentEntity tMgrEquipment){
		//机构ID
		tMgrEquipment.setOrgId(Constant.GLOBAL_ORG_ID);
		tMgrEquipmentDao.update(tMgrEquipment);
	}


	@Override
	public int updateStatus(String id) {
		int result=tMgrEquipmentDao.updateStatus(id,Constant.GLOBAL_ORG_ID);
		return result;
	}
}
