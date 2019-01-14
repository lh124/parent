package io.yfjz.service.bus;


import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.utils.page.PageBean;
import io.yfjz.utils.page.PageParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 16:48 2018/09/29
 */

public interface ChildService {
    /**
     * 儿童重复筛选列表,同一张表分为表1和表2来查询(根据不同条件)
     * @param tChildInfoEntity
     * @param pageParam
     * @return
     */
    PageBean<List<Map<String,Object>>> listDataChild(TChildInfoEntity tChildInfoEntity, PageParam pageParam);

    /**
     * 根据儿童编码获取接种信息
     * @param map
     * @return
     */
    Map<String,Object> getInoculateInfoByChildId(Map<String,Object> map);

    /**
     * 合并重复儿童的基本信息，接种记录
     * @param param
     * @return
     */
    Map<String,Object> margeInoculate(String param);
}
