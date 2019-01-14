package io.yfjz.service.bus;

import io.yfjz.entity.mgr.TMgrStockInfoEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/8/15 15:48
 */
public interface ReturnService {
    /** 
    * @Description: 查询领用的疫苗列表 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/15 15:50
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryStockList(Map<String, Object> map);
    /** 
    * @Description: 分页总条数 
    * @Param: [map] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/15 16:05
    * @Tel  17328595627
    */ 
    int queryStockTotal(Map<String, Object> map);
    /** 
    * @Description: 保存归还的疫苗 
    * @Param: [param] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/20 14:09
    * @Tel  17328595627
    */ 
    int saveReturnVac(Map param) throws ParseException;
    /** 
    * @Description: 查询还苗汇总记录
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/21 16:11
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReturnTotalList(Map<String, Object> map);

    /**
     * 还苗汇总总条数
     * @param map
     * @return
     */
    int queryReturnTotal(Map<String, Object> map);
    /** 
    * @Description: 查询汇总明细 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/21 16:12
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReturnItemList(Map<String, Object> map);

    /**
     * 还苗汇总明细
     * @param map
     * @return
     */
    int queryReturnItemTotal(Map<String, Object> map);
    /** 
    * @Description: 自动归还库存使用完成的疫苗
    * @Param: [infoEntity] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/30 20:14
    * @Tel  17328595627
    */ 
    void returnPersonAmountByZero(TMgrStockInfoEntity infoEntity) throws ParseException;

}
