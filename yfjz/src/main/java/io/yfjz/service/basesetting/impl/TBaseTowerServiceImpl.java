package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.bus.ReceiveDao;
import io.yfjz.dao.queue.TQueueNoDao;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.sys.SysUserService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.R;
import io.yfjz.utils.RRException;
import io.yfjz.utils.ShiroUtils;
import io.yfjz.websocket.TowerSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import io.yfjz.dao.basesetting.TBaseTowerDao;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.service.basesetting.TBaseTowerService;



/**
 * 工作台基本信息表
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-24 11:01:00
 */
@Service("tBaseTowerService")
public class TBaseTowerServiceImpl implements TBaseTowerService {
	@Autowired
	private TBaseTowerDao tBaseTowerDao;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	private ReceiveDao receiveDao;
	@Autowired
	private TQueueNoDao tQueueNoDao;
	
	@Override
	public TBaseTowerEntity queryObject(String id){
		return tBaseTowerDao.queryObject(id);
	}
	
	@Override
	public List<TBaseTowerEntity> queryList(Map<String, Object> map){
		return tBaseTowerDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tBaseTowerDao.queryTotal(map);
	}
	
	@Override
	public void save(TBaseTowerEntity tBaseTower){
		tBaseTowerDao.save(tBaseTower);
	}
	
	@Override
	public void update(TBaseTowerEntity tBaseTower){
		tBaseTowerDao.update(tBaseTower);
	}
	
	@Override
	public void delete(String id){
		tBaseTowerDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tBaseTowerDao.deleteBatch(ids);
	}

	@Override
	public List<String> getAllTowers() {
		return tBaseTowerDao.getAllTowers();
	}


	@Override
	public List<Map> getTowerList() {
		SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getSubject().getPrincipal();
		List<String> list = sysUserService.getLoginUserRoleName(sysUserEntity.getUserId());
		//根据登录用户id获取角色,匹配角色名称
		//登记医生
		//超级管理员
		//接种医生
		//儿保医生
		//机构管理员
		//预检医生
		List<Integer> towerTypes = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String roleName = list.get(i);
			if ("登记医生".equalsIgnoreCase(roleName)){
				towerTypes.add(Constant.TOWER_TYPE_REGISTER);
			}
			if ("接种医生".equalsIgnoreCase(roleName)){
				towerTypes.add(Constant.TOWER_TYPE_INOCULATE);
			}
			if ("儿保医生".equalsIgnoreCase(roleName)){
				towerTypes.add(Constant.TOWER_TYPE_CHILD_HEALTHCARE);
			}
			if ("预检医生".equalsIgnoreCase(roleName)){
				towerTypes.add(Constant.TOWER_TYPE_CHILD_CHECK);
			}
		}

		List<String> canSelectTowers = TowerSessionUtils.towers;
		if (towerTypes == null || towerTypes.size()<=0){
			throw new RRException("用户关联的工作台为空");
		}

		HashMap param = new HashMap();
		param.put("orgId",Constant.GLOBAL_ORG_ID);
		param.put("towerTypes",towerTypes);
		param.put("towerIds",canSelectTowers);
		return tBaseTowerDao.getTowerListByMap(param);
	}

    @Override
    public List<TBaseTowerEntity> getLoginTowerList(Integer type) {
//        List<TBaseTowerEntity> towerEntityList = queryList(null);
//        Iterator<TBaseTowerEntity> it = towerEntityList.iterator();
        List<String> towers = TowerSessionUtils.towers;
        //查询那些接种台在线
		List<TBaseTowerEntity>  onlineTower=tBaseTowerDao.queryTowerOnline(towers,type);
       /* for (String tower : towers) {
            while(it.hasNext()){
                TBaseTowerEntity next = it.next();
                if (next.getId().equals(tower)){
                    it.remove();
                    break;
                }
            }
        }
       List<TBaseTowerEntity> retList = new ArrayList<>();
        if(type==null){
            return towerEntityList;
        }
        for (TBaseTowerEntity entity : towerEntityList) {
            if (type!=null&&entity.getTowerNatureId().equals(type)){
                retList.add(entity);
            }
        }*/
        return onlineTower;
    }
	@Override
	public R synergicPosition(List<String> bioIds){
		List<TBaseTowerEntity> inoRowers = getYetLoginTowers(Constant.TOWER_TYPE_INOCULATE);//接种台类型编号
		if (inoRowers == null || inoRowers.isEmpty()){//接种台没有登录
			return R.ok(0,"接种台没有登录就绪！");
		}else {//存在已经登录的接种台
			if (bioIds == null || bioIds.isEmpty()){//没有传入疫苗信息，直接返回人数最少的接种台
				TBaseTowerEntity towerEntity =  getTowerByPopulationMin(inoRowers);
				return R.ok(1,"成功").put("position",towerEntity.getId());
			}
			List<TBaseTowerEntity> bioAllTowers = new ArrayList<>();//保存可以同时接种所传疫苗的接种台集合
			List<TBaseTowerEntity> bioPartTowers = new ArrayList<>();//保存可以同时接种所传疫苗的接种台集合
			for (TBaseTowerEntity towerEntity : inoRowers){
				List<String> vacList = receiveDao.queryBindVac(towerEntity.getId());
				if (vacList == null || vacList.isEmpty()){
					bioAllTowers.add(towerEntity);
				}else if (isOneContainTwo(vacList,bioIds)){
					bioAllTowers.add(towerEntity);
				}else if (isOneOfTwo(vacList,bioIds)){
					bioPartTowers.add(towerEntity);
				}
			}
			//接下来还需判断领苗了的接种台
			if (bioAllTowers.isEmpty() && bioPartTowers.isEmpty()){
				return R.ok(0,"没有找到可接种该类疫苗的接种台！");
			}else {
				if (!bioAllTowers.isEmpty()){
					TBaseTowerEntity towerEntity =  getTowerByPopulationMin(bioAllTowers);
					return R.ok(1,"成功").put("position",towerEntity.getId());
				}else if (!bioPartTowers.isEmpty()){
					TBaseTowerEntity towerEntity =  getTowerByPopulationMin(bioPartTowers);
					return R.ok(1,"成功").put("position",towerEntity.getId());
				}else {
					return R.ok(0,"没有找到可接种该类疫苗的接种台！");
				}
			}

		}
	}

	@Override
    public List<TBaseTowerEntity> getYetLoginTowers(Integer type){
        List<Integer> towerTypes = new ArrayList<>();
        if (type != null){
            towerTypes.add(type);
        }
        List<String> canSelectTowers = TowerSessionUtils.towers;
        HashMap param = new HashMap();
        param.put("orgId",Constant.GLOBAL_ORG_ID);
        if(towerTypes.size()>0){
        	param.put("towerTypes",towerTypes);
		}
		if(canSelectTowers.size()>0){
           param.put("towerIds",canSelectTowers);
		}

        /*暂时使用“村”字判断村台，不推送队列*/
		List<TBaseTowerEntity> towers = tBaseTowerDao.getTowerListByNotIds(param);
		if (towers != null && !towers.isEmpty()){
			Iterator<TBaseTowerEntity> iterator = towers.iterator();
			while (iterator.hasNext()){
				TBaseTowerEntity tower = iterator.next();
				String towerName =  tower.getTowerName();
				if (towerName != null && (towerName.contains("村") || towerName.contains("社区"))){
					iterator.remove();
				}
			}
		}
        return towers;
    }
	@Override
    public List<TBaseTowerEntity> getYetLoginTowers(){
        List<TBaseTowerEntity> towerList = new ArrayList<>();
        List<TBaseTowerEntity> list1 = getYetLoginTowers(Constant.TOWER_TYPE_REGISTER);
        if (list1 != null && !list1.isEmpty()){
        	towerList.addAll(list1);
		}
		List<TBaseTowerEntity> list2 = getYetLoginTowers(Constant.TOWER_TYPE_INOCULATE);
        if (list2 != null && !list2.isEmpty()){
        	towerList.addAll(list2);
		}
		List<TBaseTowerEntity> list3 = getYetLoginTowers(Constant.TOWER_TYPE_CHILD_HEALTHCARE);
        if (list3 != null && !list3.isEmpty()){
        	towerList.addAll(list3);
		}
		List<TBaseTowerEntity> list4 = getYetLoginTowers(Constant.TOWER_TYPE_CHILD_CHECK);
        if (list4 != null && !list4.isEmpty()){
        	towerList.addAll(list4);
		}
        return towerList;
    }
	/**
	 * 获取排队人数最少的一个接种台，根据所给的接种台
	 * @describe:
	 * @param inoRowers
	 * @return io.yfjz.entity.basesetting.TBaseTowerEntity
	 * @author 邓召仕
	 * @date: 2018-10-06  15:55
	 **/
	private TBaseTowerEntity getTowerByPopulationMin(List<TBaseTowerEntity> inoRowers){
		int total = tQueueNoDao.queryTodayTotalByPosition(inoRowers.get(0).getId());
		TBaseTowerEntity def = inoRowers.get(0);
		for (TBaseTowerEntity towerEntity : inoRowers){
			int nextInt = tQueueNoDao.queryTodayTotalByPosition(towerEntity.getId());
			if (nextInt<total){
				total = nextInt;
				def = towerEntity;
			}
		}
		return def;
	}

	/**
	 * 判断list one 是否包含two
	 * @describe:
	 * @param one
	 * @param two
	 * @return boolean
	 * @author 邓召仕
	 * @date: 2018-10-06  17:21
	 **/
	private boolean isOneContainTwo(List<String> one,List<String> two){
		if (one == null || one.isEmpty()){//空判断
			if (two == null || two.isEmpty()){
				return true;
			}else {
				return false;
			}
		}else {
			for (String twoStr :two){
				boolean isyou = false;
				for (String oneStr : one){
					if (twoStr.equals(oneStr)){
						isyou = true;
						break;
					}
				}
				if (!isyou){
					return false;
				}
			}
			return true;
		}
	}
/**
 * 集合one与集合two存在交集
 * @describe:
 * @param one
 * @param two
 * @return boolean
 * @author 邓召仕
 * @date: 2018-10-06  17:43
 **/
	private boolean isOneOfTwo(List<String> one,List<String> two){
			for (String twoStr :two){
				for (String oneStr : one){
					if (twoStr.equals(oneStr)){
						return true;
					}
				}
			}
			return false;
	}
}
