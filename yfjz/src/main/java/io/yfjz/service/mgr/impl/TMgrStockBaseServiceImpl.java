package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.TMgrStockBaseDao;
import io.yfjz.entity.mgr.TMgrStockBaseEntity;
import io.yfjz.service.mgr.TMgrStockBaseService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 库存产品基础信息表
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 11:13:14
 */
@Service("tMgrStockBaseService")
public class TMgrStockBaseServiceImpl implements TMgrStockBaseService {
	@Autowired
	private TMgrStockBaseDao tMgrStockBaseDao;
	
	@Override
	public TMgrStockBaseEntity queryObject(String id){
		return tMgrStockBaseDao.queryObject(id);
	}
	
	@Override
	public List<TMgrStockBaseEntity> queryList(Map<String, Object> map){
		map.put("orgId", Constant.GLOBAL_ORG_ID);
		return tMgrStockBaseDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		map.put("orgId", Constant.GLOBAL_ORG_ID);
		return tMgrStockBaseDao.queryTotal(map);
	}
	
	@Override
	public void save(TMgrStockBaseEntity tMgrStockBase){
		//检查批号和失效期是否相同

        if (tMgrStockBase.getId()!=null) {
            if (tMgrStockBase.getType()==0){
                TMgrStockBaseEntity entity= tMgrStockBaseDao.queryByBatchnumAndExpiryDate(tMgrStockBase);
                if (entity!=null&&!entity.getId().equals(tMgrStockBase.getId())) {
                    throw  new RRException("已经存在批号和失效期相同的产品，不能录入！");
                }
            }
			tMgrStockBaseDao.update(tMgrStockBase);
		}else{
            if (tMgrStockBase.getType()==0){
                TMgrStockBaseEntity entity= tMgrStockBaseDao.queryByBatchnumAndExpiryDate(tMgrStockBase);
                if (entity!=null) {
                    throw  new RRException("已经存在批号和失效期相同的产品，不能录入！");
                }
            }
		    tMgrStockBaseDao.save(tMgrStockBase);
		}
	}
	
	@Override
	public void update(TMgrStockBaseEntity tMgrStockBase){
		save(tMgrStockBase);
	}
	
	@Override
	public void delete(String id){
		tMgrStockBaseDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tMgrStockBaseDao.deleteBatch(ids);
	}

	@Override
	public void updateStatus(String id) {
		tMgrStockBaseDao.updateStatus(id);
	}

	@Override
	public List<TMgrStockBaseEntity> getAllData() {
		List<TMgrStockBaseEntity> list=tMgrStockBaseDao.getAllData(Constant.GLOBAL_ORG_ID);
		return list;
	}
	@Override
	public List<TMgrStockBaseEntity> getAllBatchnum() {
		List<TMgrStockBaseEntity> list=tMgrStockBaseDao.getAllBatchnum(Constant.GLOBAL_ORG_ID);
		return list;
	}

    @Override
    public TMgrStockBaseEntity getCodeInfo(String code) {
		return tMgrStockBaseDao.getCodeInfo(code);
    }
}
