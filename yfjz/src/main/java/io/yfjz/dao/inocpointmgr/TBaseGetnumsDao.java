package io.yfjz.dao.inocpointmgr;

import java.util.Map;

/**
 * class_name: TBaseGetnumsDao
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-31 14:11
 */
public interface TBaseGetnumsDao {
    Map<String,Object> queryByOrgId();

    void save(Map<String,Object> param);
    void deleteByOrgId(String orgId);

    void updateStateByOrgId(Map<String, Object> param);

    Map<String,Object> queryCheckDataByOrgId();
}
