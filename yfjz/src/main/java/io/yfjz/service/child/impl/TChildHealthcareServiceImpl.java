package io.yfjz.service.child.impl;

import io.yfjz.dao.child.TChildHealthcareDao;
import io.yfjz.dao.sys.SysDepartUserDao;
import io.yfjz.entity.child.TChildHealthcareEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.child.TChildHealthcareService;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 儿童体检记录表
 *
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-24 15:22:38
 */
@Service("tChildHealthcareService")
public class TChildHealthcareServiceImpl implements TChildHealthcareService {
	@Autowired
	private TChildHealthcareDao tChildHealthcareDao;
	@Autowired
	private SysDepartUserDao departUserDao;
	
	@Override
	public TChildHealthcareEntity queryObject(String id){
		return tChildHealthcareDao.queryObject(id);
	}
	
	@Override
	public List<TChildHealthcareEntity> queryList(Map<String, Object> map){
		return tChildHealthcareDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildHealthcareDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildHealthcareEntity tChildHealthcare){
		tChildHealthcare.setId(UUID.randomUUID().toString());
		tChildHealthcare.setCreateTime(new Date());
		tChildHealthcare.setCreateUserId(ShiroUtils.getUserId());
		SysUserEntity sysUserEntity = departUserDao.queryOrgInfoByUserId(ShiroUtils.getUserId());
		tChildHealthcare.setOrgId(sysUserEntity.getOrgId());//组织机构
		tChildHealthcare.setRegisterUserId(ShiroUtils.getUserId());//登记医生
		tChildHealthcare.setRegisterDate(new Date());//登记日期

		tChildHealthcareDao.save(tChildHealthcare);
	}
	
	@Override
	public void update(TChildHealthcareEntity tChildHealthcare){
		tChildHealthcareDao.update(tChildHealthcare);
	}
	
	@Override
	public void delete(String id){
		tChildHealthcareDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildHealthcareDao.deleteBatch(ids);
	}

	@Override
	public List<TChildHealthcareEntity> healthcarelists() {
		return tChildHealthcareDao.healthcarelists();
	}

}
