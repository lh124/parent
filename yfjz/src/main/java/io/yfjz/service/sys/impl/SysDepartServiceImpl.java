package io.yfjz.service.sys.impl;

import io.yfjz.dao.sys.SysDepartDao;
import io.yfjz.entity.sys.SysDepartEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysDepartService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




/**
 * 机构、部门信息表
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-16 23:03:13
 */
@Service("sysDepartService")
public class SysDepartServiceImpl implements SysDepartService {
	@Autowired
	private SysDepartDao sysDepartDao;
	
	@Override
	public SysDepartEntity queryObject(String id){
		return sysDepartDao.queryObject(id);
	}

	@Override
	public List<SysDepartEntity> queryListDepart(Map<String, Object> map){
			return sysDepartDao.queryList(map);

	}

	@Override
	public List<SysDepartEntity> queryList(Map<String, Object> map){
		String userType = Constant.GLOBAL_ORG_ID;

		SysUserEntity user = ShiroUtils.getUserEntity();
		//判断用户是否是超级管理员
		if(user.getType() == 1){
			return sysDepartDao.queryList(map);
		}else{
			List<SysDepartEntity> departEntity = new ArrayList<>();
			queryDepartPidList(departEntity,user.getOrgId());
			queryDepartList(departEntity,user.getOrgId());
			return departEntity;
		}

	}

	@Override
	public List<SysDepartEntity> queryDepart(String orgId){
		List<SysDepartEntity> departEntity = new ArrayList<>();
		queryDepartPidList(departEntity,orgId);
		 queryDepartList(departEntity,orgId);
		 return departEntity;
	}

	/**
	 * 获取当前机构下的所有的机构
	 * @param departEntity
	 * @param orgId
	 */
	public void queryDepartList(List<SysDepartEntity> departEntity ,String orgId){
		List<SysDepartEntity> suer0 = sysDepartDao.queryDepartList(orgId);
		for(SysDepartEntity depart : suer0){
			departEntity.add(depart);
			queryDepartList(departEntity,depart.getId());
		}
	}

	/**
	 * 获取登录用户当前机构上的所有机构
	 * @param departEntity
	 * @param orgId
	 */
	public void queryDepartPidList(List<SysDepartEntity> departEntity ,String orgId){
		List<SysDepartEntity> suer0 = sysDepartDao.queryDepartPidList(orgId);
		if(suer0.size() != 0){
			departEntity.add(suer0.get(0));
			queryDepartPidList(departEntity ,suer0.get(0).getPid());
		}
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysDepartDao.queryTotal(map);
	}
	
	@Override
	public void save(SysDepartEntity sysDepart){
		sysDepartDao.save(sysDepart);
	}
	
	@Override
	public void update(SysDepartEntity sysDepart){
		sysDepartDao.update(sysDepart);
	}
	
	@Override
	public void delete(String id){
		sysDepartDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		sysDepartDao.deleteBatch(ids);
	}

	@Override
	public List<SysDepartEntity> getAsyncDepartMenuTree(Map<String, Object> map) {
		return sysDepartDao.getAsyncDepartMenuTree(map);
	}

	@Override
	public void updateStatus(String[] ids) {
		sysDepartDao.updateStatus(ids);
	}
}
