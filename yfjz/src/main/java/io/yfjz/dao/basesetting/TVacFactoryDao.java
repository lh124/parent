package io.yfjz.dao.basesetting;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TVacFactoryEntity;

import java.util.List;

/**
 * 疫苗生产厂家
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-30 16:03:56
 */
public interface TVacFactoryDao extends BaseDao<TVacFactoryEntity> {
    void updateDelStatus(String[] ids);

    void updateStatusOff(String[] ids);

    void updateStatusIn(String[] ids);

    List<TVacFactoryEntity> getAllData();

    /**
     * 查询列表信息
     * @return
     */
    List<TVacFactoryEntity> selectList();

}
