package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.TChildPrecheckEntity;

import java.util.List;

/**
 * 预检信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-23 09:41:20
 */
public interface TChildPrecheckDao extends BaseDao<TChildPrecheckEntity> {

//	List<TChildPrecheckEntity> waitsumprecheck();

	/**
	 *预检完成总数
	 * @return
	 */
	List<TChildPrecheckEntity> sumprechecks();
}
