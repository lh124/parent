package io.yfjz.service.provinceplatform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.yfjz.dao.provinceplatform.TChildDownloadDao;
import io.yfjz.entity.provinceplatform.TChildDownloadEntity;
import io.yfjz.service.provinceplatform.TChildDownloadService;



/**
 * 省平台下载新生儿表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 14:33:28
 */
@Service("tChildDownloadService")
public class TChildDownloadServiceImpl implements TChildDownloadService {
	@Autowired
	private TChildDownloadDao tChildDownloadDao;
	
	@Override
	public TChildDownloadEntity queryObject(String id){
		return tChildDownloadDao.queryObject(id);
	}
	
	@Override
	public List<TChildDownloadEntity> queryList(Map<String, Object> map){
		return tChildDownloadDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildDownloadDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildDownloadEntity tChildDownload){
		tChildDownloadDao.save(tChildDownload);
	}
	
	@Override
	public void update(TChildDownloadEntity tChildDownload){
		tChildDownloadDao.update(tChildDownload);
	}
	
	@Override
	public void delete(String id){
		tChildDownloadDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildDownloadDao.deleteBatch(ids);
	}
	
}
