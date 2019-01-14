package io.yfjz.dao.basesetting;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TMvUploadEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 保存app上传的文件基本信息表，每个Android盒子需要播放视频的路径
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-23 15:50:15
 */
public interface TMvUploadDao extends BaseDao<TMvUploadEntity> {

    List<HashMap> queryListAndPlayerArea();
}
