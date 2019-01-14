package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.TChildTabooEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童禁忌表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:47:59
 */
public interface TChildTabooDao extends BaseDao<TChildTabooEntity> {
    List<Map<String,Object>> findIstabus(String chilCode);
	
}
