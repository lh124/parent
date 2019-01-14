package io.yfjz.service.basesetting;

import io.yfjz.entity.basesetting.TMvRelevanceEntity;

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
public interface TMvRelevanceService {
	
	TMvRelevanceEntity queryObject(String id);
	
	List<TMvRelevanceEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TMvRelevanceEntity tMvRelevance);
	
	void update(TMvRelevanceEntity tMvRelevance);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 * @method_name: queryListByPlayerAreaId
	 * @describe: 根据播放台类型获取关联列表
	 * @param [playerareaiD]
	 * @return java.util.List<io.yfjz.entity.basesetting.TMvRelevanceEntity> 
	 * @author 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/9/23  16:13
	 **/
	 List<HashMap> queryListByPlayerAreaId(String playerareaiD);
}
