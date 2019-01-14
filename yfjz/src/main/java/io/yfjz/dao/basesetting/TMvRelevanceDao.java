package io.yfjz.dao.basesetting;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TMvRelevanceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 保存app上传的文件基本信息表，每个Android盒子需要播放视频的路径
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-23 17:24:51
 */
public interface TMvRelevanceDao extends BaseDao<TMvRelevanceEntity> {

    
    /**
     * @method_name: deleteByMvId
     * @describe: 根据视频id删除关联表，批量
     * @param: [mvId]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/24  10:21
     **/
    void deleteByMvId(@Param("mvId") String mvId);

    /**
     * @method_name: queryListByMvId
     * @describe: 根据视频id查询关联的工作台
     * @param: [id]
     * @return: java.util.List<io.yfjz.entity.TMvRelevanceEntity>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/24  14:32
     **/
    List<TMvRelevanceEntity> queryListByMvId(String id);

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
