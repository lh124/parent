package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.TChildAbnormalEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童异常反应表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */
public interface TChildAbnormalDao extends BaseDao<TChildAbnormalEntity> {
    List<Map<String,Object>> findAefis(String chilCode);
	
}
