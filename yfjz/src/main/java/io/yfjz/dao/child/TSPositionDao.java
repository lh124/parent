package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.TSPositionEntity;

import java.util.List;
import java.util.Map;

/**
 * 省市县镇村数据
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-11-12 20:38:42
 */
public interface TSPositionDao extends BaseDao<TSPositionEntity> {
    List<Map<String,Object>> queryprovince(Map<String, Object> map);

    List<Map<String,Object>> querycity(Map<String, Object> map);

    List<Map<String,Object>> querycounty(Map<String, Object> map);

    List<Map<String,Object>> querytown(Map<String, Object> map);

}
