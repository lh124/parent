package io.yfjz.dao.statistics;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/11/1 13:32
 */
public interface DynamicChildDao {
    List<Map<String, Object>> queryList(Map<String, Object> queryMap);
}
