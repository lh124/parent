package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.TChildMoveEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童迁移记录表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 15:56:11
 */
public interface TChildMoveDao extends BaseDao<TChildMoveEntity> {
    List<TChildMoveEntity> querychilcode(Map<String,Object> map);
    List<Map<String,Object>> findChildheres(String chilCode);
}
