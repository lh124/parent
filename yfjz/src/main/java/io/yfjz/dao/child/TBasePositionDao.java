package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.TBasePositionEntity;

import java.util.List;
import java.util.Map;

/**
 * 省市县镇村数据
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-30 13:41:54
 */
public interface TBasePositionDao extends BaseDao<TBasePositionEntity> {
    List<Map<String,Object>> queryprovince(Map<String, Object> map);

    List<Map<String,Object>> querycity(Map<String, Object> map);

    List<Map<String,Object>> querycounty(Map<String, Object> map);

    List<Map<String,Object>> querytown(Map<String, Object> map);

    List<Map<String,Object>> gethospital(Map<String, Object> map);

    TBasePositionEntity queryHosptial(String str);

}
