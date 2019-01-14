package io.yfjz.service.sys.impl;

import io.yfjz.dao.sys.SysDepartUserDao;
import io.yfjz.entity.sys.SysDepartUserEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysDepartUserService;
import io.yfjz.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



/**
 * 角色与菜单对应关系
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-17 13:57:20
 */
@Service("sysDepartUserService")
public class SysDepartUserServiceImpl implements SysDepartUserService {
	@Autowired
	private SysDepartUserDao sysDepartUserDao;
	
	@Override
	public SysDepartUserEntity queryObject(String id){
		return sysDepartUserDao.queryObject(id);
	}
	
	@Override
	public List<SysDepartUserEntity> queryList(Map<String, Object> map){
		return sysDepartUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysDepartUserDao.queryTotal(map);
	}
	
	@Override
	public void save(SysDepartUserEntity sysDepartUser){
		sysDepartUserDao.save(sysDepartUser);
	}
	
	@Override
	public void update(SysDepartUserEntity sysDepartUser){
		sysDepartUserDao.update(sysDepartUser);
	}
	
	@Override
	public void delete(String id){
		sysDepartUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		sysDepartUserDao.deleteBatch(ids);
	}

	@Override
	public void addRelationShip(SysUserEntity user) {
		//部门与人员关系
		String orgId = user.getOrgId();
		List<String> userIds = user.getUserIds();
		if (orgId == null || userIds == null || userIds.size()<0){
			throw new RRException("机构参数或用户列表未选中",-1001);
		}



		SysDepartUserEntity sysDepartUserEntity = new SysDepartUserEntity();
		for (String userId : userIds){
			sysDepartUserEntity.setOrgId(orgId);
			sysDepartUserEntity.setUserId(userId);
			//优先删除之前绑定的机构
			sysDepartUserDao.removeByUserId(userId);
			//判断入库 同一个机构 ，同一个用户是否存在
			int count = sysDepartUserDao.countByDepartIdAndUserId(sysDepartUserEntity);
			if (count >0){
				continue;
			}
			sysDepartUserDao.save(sysDepartUserEntity);
		}
	}

    @Override
	public void addRelationShips(SysUserEntity user) {
		//部门与人员关系
		String userId = user.getUserId();
		String orgId = user.getOrgId();
		if (orgId == null || userId == null){
			throw new RRException("机构参数或用户列表未选中",-1001);
		}
		SysDepartUserEntity sysDepartUserEntity = new SysDepartUserEntity();
		String[] orgIds;
		//判断当前用户是否绑定多个机构
		if(user.getOrgId().indexOf(",") != 0){
			orgIds = user.getOrgId().split(",");
		}else{
			orgIds = new String[0];
            orgIds[0] = user.getOrgId();
		}
		sysDepartUserEntity.setUserId(userId);
		for (String org : orgIds){
			sysDepartUserEntity.setOrgId(org);
			//判断入库 同一个机构 ，同一个用户是否存在
			int count = sysDepartUserDao.countByDepartIdAndUserId(sysDepartUserEntity);
			if (count >0){
				continue;
			}
			sysDepartUserDao.save(sysDepartUserEntity);
		}
	}

	@Override
	public SysUserEntity queryOrgInfoByUserId(String userId) {
		if (StringUtils.isEmpty(userId)){
			throw new RRException("查询用户参数为空，方法名：queryOrgInfoByUserId（userId）",-1001);
		}
		return sysDepartUserDao.queryOrgInfoByUserId(userId);
	}

    @Override
    public List<SysDepartUserEntity> getDepartList(String userId) {
        return sysDepartUserDao.getDepartList(userId);
    }
}
