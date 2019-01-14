package io.yfjz.dao.inocpointmgr;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.ProcessSetEntity;

import java.util.List;
import java.util.Map;

/** 
* @Description: 流程设置 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/13 10:24
* @Tel  17328595627
*/ 
public interface ProcessSetDao extends BaseDao<ProcessSetEntity> {

    int deleteByOrgId(String globalOrgId);

    Integer queryProcessFirst();

    List<Map<String,Integer>> queryAllList();

    List<ProcessSetEntity> queryAll(String globalOrgId);

    /**
     * 获取流程配置
     * @param orgId
     * @return
     */
    List<ProcessSetEntity> sumtower(String orgId);
}
