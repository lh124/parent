package io.yfjz.dao.basesetting;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TVacInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 疫苗信息表
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-27 17:07:33
 */
public interface TVacInfoDao extends BaseDao<TVacInfoEntity> {

    List<TVacInfoEntity> getAllVaccine();
    List<TVacInfoEntity> queryListByType(Map<String, Object> map);
    /**
     * 查询所有的疫苗数据信息
     * @return
     */
    List<TVacInfoEntity> selectList();
}
