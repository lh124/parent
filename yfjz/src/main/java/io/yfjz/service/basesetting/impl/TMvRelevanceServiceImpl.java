package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TMvRelevanceDao;
import io.yfjz.entity.basesetting.TMvRelevanceEntity;
import io.yfjz.service.basesetting.TMvRelevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 保存app上传的文件基本信息表，每个Android盒子需要播放视频的路径
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-23 17:24:51
 */
@Service("tMvRelevanceService")
public class TMvRelevanceServiceImpl implements TMvRelevanceService {
	@Autowired
	private TMvRelevanceDao tMvRelevanceDao;
	
	@Override
	public TMvRelevanceEntity queryObject(String id){
		return tMvRelevanceDao.queryObject(id);
	}
	
	@Override
	public List<TMvRelevanceEntity> queryList(Map<String, Object> map){
		return tMvRelevanceDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tMvRelevanceDao.queryTotal(map);
	}
	
	@Override
	public void save(TMvRelevanceEntity tMvRelevance){
		tMvRelevanceDao.save(tMvRelevance);
	}
	
	@Override
	public void update(TMvRelevanceEntity tMvRelevance){
		tMvRelevanceDao.update(tMvRelevance);
	}
	
	@Override
	public void delete(String id){
		tMvRelevanceDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tMvRelevanceDao.deleteBatch(ids);
	}

	@Override
	public List<HashMap> queryListByPlayerAreaId(String playerareaiD) {
		return tMvRelevanceDao.queryListByPlayerAreaId(playerareaiD);
	}


}
