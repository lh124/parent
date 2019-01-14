package io.yfjz.dao.bus;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/8/15 15:51
 */
public interface ReturnDao {
    List<Map<String,Object>> queryStockList(Map<String, Object> map);

    int queryStockTotal(Map<String, Object> map);
}
