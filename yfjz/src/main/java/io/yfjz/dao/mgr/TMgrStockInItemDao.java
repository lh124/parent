package io.yfjz.dao.mgr;


import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.ModifyStock;
import io.yfjz.entity.mgr.TMgrStockInItemEntity;
import io.yfjz.entity.mgr.TMgrStockInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 疫苗入库记录明细表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-30 09:40:37
 */
public interface TMgrStockInItemDao extends BaseDao<TMgrStockInItemEntity> {
    /** 
    * @Description: 查询明细集合 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/7 11:27
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryItemList(Map<String, Object> map);

    List<Map<String,Object>> queryItemListMap(Map<String, Object> map);
    /** 
    * @Description: 疫苗使用情况记录报表使用
    * @Param: [] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/10 16:37
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReceiveVaccineRecord(Map<String, Object> queryMap);
    /** 
    * @Description: 插入修改记录
    * @Param: [modify] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/5 9:57
    * @Tel  17328595627
    */ 
    void insertModify(ModifyStock modify);

    List<Map<String,Object>> getModifyList(Map<String, Object> map);

    int getModifyListTotal(Map<String, Object> map);

    List<Map<String,Object>> queryOtherUseVaccine(Map<String, Object> queryMap);

    void updateInfo(TMgrStockInfoEntity towerInfo);
}
