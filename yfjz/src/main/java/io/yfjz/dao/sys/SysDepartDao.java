package io.yfjz.dao.sys;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.sys.SysDepartEntity;

import java.util.List;
import java.util.Map;

/**
 * 机构、部门信息表
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-16 23:33:07
 */
public interface SysDepartDao extends BaseDao<SysDepartEntity> {
    /**
     * 异步加载机构树
     * @param map
     * @return
     */
    List<SysDepartEntity> getAsyncDepartMenuTree(Map<String, Object> map);

    /**
     * 禁用
     * @param ids
     */
    void updateStatusOff(String[] ids);

    /**
     * 启用
     * @param ids
     */
    void updateStatusIn(String[] ids);

    /**
     * 逻辑删除
     * @param ids
     */
    void updateDelStatus(String[] ids);

    /**
     * 获取登录用户机构信息
     * @param orgId
     * @return
     */
    List<SysDepartEntity> queryDepartList(String orgId);

    /**
     * 获取登录用户当前机构上的所有机构
     * @param orgId
     * @return
     */
    List<SysDepartEntity> queryDepartPidList(String orgId);

    void updateStatus(String[] ids);
}
