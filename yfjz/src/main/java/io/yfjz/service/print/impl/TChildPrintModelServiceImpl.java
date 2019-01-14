package io.yfjz.service.print.impl;

import io.yfjz.dao.print.TChildPrintModelDao;
import io.yfjz.entity.print.TChildPrintModelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.service.print.TChildPrintModelService;



/**
 * 儿童打印模板
 *
 * @author饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-08-17 11:27:06
 */
@Service("tChildPrintModelService")
public class TChildPrintModelServiceImpl implements TChildPrintModelService {
	@Autowired
	private TChildPrintModelDao tChildPrintModelDao;
	
	@Override
	public TChildPrintModelEntity queryObject(Long id){
		return tChildPrintModelDao.queryObject(id);
	}
	
	@Override
	public List<TChildPrintModelEntity> queryList(Map<String, Object> map){
		return tChildPrintModelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildPrintModelDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildPrintModelEntity tChildPrintModel){
		tChildPrintModelDao.save(tChildPrintModel);
	}
	
	@Override
	public void update(TChildPrintModelEntity tChildPrintModel){
		tChildPrintModelDao.update(tChildPrintModel);
	}
	
	@Override
	public void delete(Long id){
		tChildPrintModelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tChildPrintModelDao.deleteBatch(ids);
	}
	
}
