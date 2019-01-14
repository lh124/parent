package io.yfjz.service.mgr.impl;

import io.yfjz.dao.basesetting.TBaseTowerDao;
import io.yfjz.dao.mgr.TMgrStoreDao;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import io.yfjz.service.mgr.TMgrStoreService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


@Service("tMgrStoreService")
public class TMgrStoreServiceImpl implements TMgrStoreService {


    @Autowired
	private TMgrStoreDao tMgrStoreDao;
	@Autowired
	private TBaseTowerDao baseTowerDao;
	
	@Override
	public TMgrStoreEntity queryObject(String id){
		return tMgrStoreDao.queryObject(id);
	}
	
	@Override
	public List<TMgrStoreEntity> queryList(Map<String, Object> map){
		map.put("orgId", Constant.GLOBAL_ORG_ID);
		return tMgrStoreDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		map.put("orgId", Constant.GLOBAL_ORG_ID);
		return tMgrStoreDao.queryTotal(map);
	}
	
	@Override
	public void save(TMgrStoreEntity tMgrStore){
		tMgrStore.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		tMgrStore.setCreateTime(new Date());
		tMgrStore.setOrgId(Constant.GLOBAL_ORG_ID);
		if(tMgrStore.getPosId()!=null){
			tMgrStore.setTtype(TMgrStoreEntity.TYPE_TOWER);//接种台仓库
		}else{
			tMgrStore.setTtype(TMgrStoreEntity.TYPE_MAIN);//主仓库
		}
		 tMgrStoreDao.save(tMgrStore);
	}
	
	@Override
	public void update(TMgrStoreEntity tMgrStore){
		tMgrStoreDao.update(tMgrStore);
	}
	
	@Override
	public void delete(String id){
		tMgrStoreDao.delete(id);
	}

	@Override
	public void deleteBatch(String[] ids){
		tMgrStoreDao.deleteBatch(ids);
	}

    @Override
    public int updateStatus(String id) {
        int result=0;
	    if (!StringUtils.isEmpty(id)){
            TMgrStoreEntity store = new TMgrStoreEntity();
            store.setId(id);
            result=tMgrStoreDao.updateStatus(store);
        }
        return result;
    }



    @Override
    public int relationEquipment(String storeId, String[] equipmentIds) {
	    int result=0;
        List<Map<String,Object>> relations = new ArrayList<>();
        if (!StringUtils.isEmpty(storeId)&&equipmentIds!=null&&equipmentIds.length>0){

            for (String id : equipmentIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("storeId",storeId);
                map.put("equipmentId",id);
                relations.add(map);
            }
           result=tMgrStoreDao.insertRelation(relations);
        }
        return result;
    }
    @Override
    public int deleteRelation(String storeId) {
	    int result=0;
	    if (!StringUtils.isEmpty(storeId)){
	        result=tMgrStoreDao.deleteRelation(storeId);
        }
        return result;
    }
	@Override
	public List<TBaseTowerEntity> getToweSrList() {

		return baseTowerDao.queryList(Constant.GLOBAL_ORG_ID);

	}

	@Override
	public List<TBaseTowerEntity> getToweSrListByIds(List<String> towers) {
		HashMap param = new HashMap();
		param.put("orgId",Constant.GLOBAL_ORG_ID);
		param.put("towerIds",towers);
		return baseTowerDao.getTowerListByTowerIds(param);
	}

	@Override
	public List<TMgrStoreEntity> getAllStore(int type) {
		List<TMgrStoreEntity> list=tMgrStoreDao.getAllStore(Constant.GLOBAL_ORG_ID,type);
		return list;
	}

	@Override
	public String getEquipmentById(String storeId) {
		List<Map<String, Object>> equipment = tMgrStoreDao.getEquipmentById(storeId);
		StringBuilder sb = new StringBuilder();
		if (equipment.size()>0){
			for (Map<String, Object> map : equipment) {
				sb.append(map.get("id")).append(":").append(map.get("name")).append(";");
			}
			sb.deleteCharAt(sb.length()-1);
		}

		return sb.toString();
	}
}
