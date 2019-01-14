package io.yfjz.service.mgr;

import io.yfjz.entity.mgr.TMgrStockInItemEntity;
import io.yfjz.entity.mgr.TMgrStockInTotalEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import sun.util.resources.cldr.ta.CalendarData_ta_IN;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/8/7 9:39
 */
public interface TMgrStockInTotalService {
    /** 
    * @Description: 查询所有数据列表 
    * @Param: [map] 
    * @return: java.util.List<io.yfjz.entity.mgr.TMgrStoreEntity> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/7 10:40
    * @Tel  17328595627
    */ 
    List<TMgrStockInTotalEntity> queryList(Map<String, Object> map);
    /** 
    * @Description: 查询数据中条数 
    * @Param: [map] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/7 10:41
    * @Tel  17328595627
    */ 
    int queryTotal(Map<String, Object> map);
    /** 
    * @Description: 查询明细列表
    * @Param: [map] 
    * @return: java.util.List<io.yfjz.entity.mgr.TMgrStockInTotalEntity> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/7 11:21
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryItemList(Map<String, Object> map);
    /** 
    * @Description: 查询明细的总条数
    * @Param: [map] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/7 11:22
    * @Tel  17328595627
    */ 
    int queryItemTotal(Map<String, Object> map);
    /** 
    * @Description: 根据ID查询结果
    * @Param: [id] 
    * @return: io.yfjz.entity.mgr.TMgrStockInTotalEntity 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/13 14:10
    * @Tel  17328595627
    */ 
    TMgrStockInTotalEntity queryInfoById(String id);
    /** 
    * @Description: 修改入库数量之后，更新库存。 
    * @Param: [map] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/7 17:22
    * @Tel  17328595627
    */ 
    void updateAmount(Map map);
    /** 
    * @Description: 查询修改记录集合
    * @Param: [map] 
    * @return: java.util.List<io.yfjz.entity.mgr.TMgrStockInTotalEntity> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/8 9:45
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> getModifyList(Map<String, Object> map);

    int getModifyListTotal(Map<String, Object> map);
}
