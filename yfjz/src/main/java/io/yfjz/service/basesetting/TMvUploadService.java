package io.yfjz.service.basesetting;

import io.yfjz.entity.basesetting.TMvUploadEntity;
import io.yfjz.utils.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存app上传的文件基本信息表，每个Android盒子需要播放视频的路径
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-23 15:50:15
 */
public interface TMvUploadService {
	
	TMvUploadEntity queryObject(String id);
	
	List<TMvUploadEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TMvUploadEntity tMvUpload);
	
	void update(TMvUploadEntity tMvUpload);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);


	/**
	 * @method_name: saveRelevace
	 * @describe: 保存视频与工作台的关联关系
	 * @param: [paramMap]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/24  14:02
	 **/
    R saveRelevace(List<Map> paramMap);


    /**
	 * @method_name: getTowerListByMvId
	 * @describe: 根据视频的id，获取关联的工作台列表
	 * @param: [id]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/24  14:03
	 **/
    R getTowerListByMvId(String id);

	List<HashMap> queryListAndPlayerArea();
}
