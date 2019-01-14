package io.yfjz.service.webService;


import io.yfjz.entity.sys.SysDepartEntity;

import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 14:06 2018/09/12
 */

public interface DataSynchronizationService {
    /**
     * 根据编号查询机构所有信息
     * @param orgId
     * @return
     */
    SysDepartEntity queryDepartList(String orgId);

    /**
     * 本地和平台根据表更新增量数据
     * @param tableName 表名
     * @param where
     * @return
     */
    Map<String,Object> updateTableData(String orgId,String tableName,Map<String, Object> where);

    /**
     * 本地和平台数据的增量数据同步
     * @return
     */
    Map<String,Object> updateFullData(String orgId,String pId);

    /**
     * 更新表平台的全量数据
     * @param orgId
     * @param pId
     * @param userId
     * @return
     */
    Map<String,Object> updateFullData(String orgId,String pId,String userId);

}
