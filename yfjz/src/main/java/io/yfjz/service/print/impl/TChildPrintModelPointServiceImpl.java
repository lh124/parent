package io.yfjz.service.print.impl;

import io.yfjz.dao.print.TChildPrintModelPointDao;
import io.yfjz.entity.print.TChildPrintModelPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.service.print.TChildPrintModelPointService;



/**
 * 儿童打印接种记录模板坐标
 *
 * @author饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-08-17 11:27:06
 */
@Service("tChildPrintModelPointService")
public class TChildPrintModelPointServiceImpl implements TChildPrintModelPointService {
	@Autowired
	private TChildPrintModelPointDao tChildPrintModelPointDao;
	
	@Override
	public TChildPrintModelPointEntity queryObject(String id){
		return tChildPrintModelPointDao.queryObject(id);
	}
	
	@Override
	public List<TChildPrintModelPointEntity> queryList(Map<String, Object> map){
		return tChildPrintModelPointDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildPrintModelPointDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildPrintModelPointEntity tChildPrintModelPoint){
		tChildPrintModelPointDao.save(tChildPrintModelPoint);
	}
	
	@Override
	public void update(TChildPrintModelPointEntity tChildPrintModelPoint){
		tChildPrintModelPointDao.update(tChildPrintModelPoint);
	}
	
	@Override
	public void delete(String id){
		tChildPrintModelPointDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildPrintModelPointDao.deleteBatch(ids);
	}
	
}
