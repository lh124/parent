package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.TChildInfectionEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童传染病表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:37:19
 */
public interface TChildInfectionDao extends BaseDao<TChildInfectionEntity> {
    List<Map<String,Object>> findInfections(String chilCode);
	
}
