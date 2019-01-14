package io.yfjz.service.print.impl;

import io.yfjz.dao.print.TChildInfoPrintPointDao;
import io.yfjz.entity.print.TChildInfoPrintPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.service.print.TChildInfoPrintPointService;



/**
 * 儿童打印基本信息打印坐标
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-17 11:27:06
 */
@Service("tChildInfoPrintPointService")
public class TChildInfoPrintPointServiceImpl implements TChildInfoPrintPointService {
	@Autowired
	private TChildInfoPrintPointDao tChildInfoPrintPointDao;
	
	@Override
	public TChildInfoPrintPointEntity queryObject(String id){
		return tChildInfoPrintPointDao.queryObject(id);
	}
	
	@Override
	public List<TChildInfoPrintPointEntity> queryList(Map<String, Object> map){
		return tChildInfoPrintPointDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildInfoPrintPointDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildInfoPrintPointEntity tChildInfoPrintPoint){
		tChildInfoPrintPointDao.save(tChildInfoPrintPoint);
	}
	
	@Override
	public void update(TChildInfoPrintPointEntity tChildInfoPrintPoint){
		tChildInfoPrintPointDao.update(tChildInfoPrintPoint);
	}
	
	@Override
	public void delete(String id){
		tChildInfoPrintPointDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildInfoPrintPointDao.deleteBatch(ids);
	}
	
}
