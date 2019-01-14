package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TMvRelevanceDao;
import io.yfjz.dao.basesetting.TMvUploadDao;
import io.yfjz.entity.basesetting.TMvRelevanceEntity;
import io.yfjz.entity.basesetting.TMvUploadEntity;
import io.yfjz.service.basesetting.TMvUploadService;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 保存app上传的文件基本信息表，每个Android盒子需要播放视频的路径
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-23 15:50:15
 */
@Service("tMvUploadService")
public class TMvUploadServiceImpl implements TMvUploadService {
	@Autowired
	private TMvUploadDao tMvUploadDao;

	@Autowired
	TMvRelevanceDao tMvRelevanceDao;
	
	@Override
	public TMvUploadEntity queryObject(String id){
		return tMvUploadDao.queryObject(id);
	}
	
	@Override
	public List<TMvUploadEntity> queryList(Map<String, Object> map){
		return tMvUploadDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tMvUploadDao.queryTotal(map);
	}
	
	@Override
	public void save(TMvUploadEntity tMvUpload){
		tMvUploadDao.save(tMvUpload);
	}
	
	@Override
	public void update(TMvUploadEntity tMvUpload){
		tMvUploadDao.update(tMvUpload);
	}
	
	@Override
	public void delete(String id){
		tMvUploadDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tMvUploadDao.deleteBatch(ids);
	}


	@Transactional
	@Override
	public R saveRelevace(List<Map> paramMap) {
		List mvList = (List) paramMap.get(0).get("mvIds");
		List playerAreaIds = (List) paramMap.get(1).get("playerAreaIds");

		//做逻辑判断，必须两个同时不能为空才进行操作
		if (mvList == null || mvList.size() <=0 || playerAreaIds == null ||playerAreaIds.size() <=0)
			return R.error(-1,"请选择需要关联的列表数据");

		for (int i = 0; i < mvList.size(); i++) {
			String mvId = (String) mvList.get(i);
			String fileUrl = "";
			TMvUploadEntity tMvUploadEntity = tMvUploadDao.queryObject(mvId);
			if (tMvUploadEntity != null) {
				fileUrl = tMvUploadEntity.getUrl();
			}

			tMvRelevanceDao.deleteByMvId(mvId);//优先删除之前关联的数据
			for (int j = 0; j < playerAreaIds.size(); j++) {
				TMvRelevanceEntity tMvRelevanceEntity = new TMvRelevanceEntity();
				tMvRelevanceEntity.setId(UUID.randomUUID().toString());
				tMvRelevanceEntity.setCreateTime(new Date());
				tMvRelevanceEntity.setFileurl(fileUrl);
				tMvRelevanceEntity.setMvId(mvId);
				tMvRelevanceEntity.setPlayerareaId(playerAreaIds.get(j).toString());
				tMvRelevanceDao.save(tMvRelevanceEntity);
			}
		}
		return R.ok(0, "添加成功!");
	}

	@Override
	public R getTowerListByMvId(String id) {
		List<TMvRelevanceEntity> tMvRelevanceEntityList = tMvRelevanceDao.queryListByMvId(id);
		return R.ok().put("list",tMvRelevanceEntityList);
	}

	@Override
	public List<HashMap> queryListAndPlayerArea() {
		return tMvUploadDao.queryListAndPlayerArea();
	}

}
