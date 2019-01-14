package io.yfjz.service.bus.impl;

import io.yfjz.dao.bus.TBusCancelDao;
import io.yfjz.entity.bus.TBusCancelEntity;
import io.yfjz.service.bus.TBusCancelService;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 取消原因表
 *
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-26 09:54:38
 */
@Service("tBusCancelService")
public class TBusCancelServiceImpl implements TBusCancelService {
	@Autowired
	private TBusCancelDao tBusCancelDao;
	
	@Override
	public TBusCancelEntity queryObject(String id){
		return tBusCancelDao.queryObject(id);
	}
	
	@Override
	public List<TBusCancelEntity> queryList(Map<String, Object> map){
		return tBusCancelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tBusCancelDao.queryTotal(map);
	}
	
	@Override
	public void save(TBusCancelEntity tBusCancel){
		tBusCancelDao.save(tBusCancel);
	}

	@Override
	public void saveRelsult(TBusCancelEntity tBusCancel,String chilCode) {
		tBusCancel.setId(UUID.randomUUID().toString());
		tBusCancel.setChilCode(chilCode);
		tBusCancel.setCreateTime(new Date());
		tBusCancel.setFkOperateUserId(ShiroUtils.getUserId());
		tBusCancel.setPosId(ShiroUtils.getUserEntity().getInoculateTowerId());//工作台id
		tBusCancel.setStatus(0);
		tBusCancelDao.save(tBusCancel);
	}

	@Override
	public void update(TBusCancelEntity tBusCancel){
		tBusCancelDao.update(tBusCancel);
	}
	
	@Override
	public void delete(String id){
		tBusCancelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tBusCancelDao.deleteBatch(ids);
	}
	
}
