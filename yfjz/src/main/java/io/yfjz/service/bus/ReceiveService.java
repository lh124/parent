package io.yfjz.service.bus;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/8/11 14:57
 * 领取疫苗接口
 */
public interface ReceiveService {
    /** 
    * @Description: 查询接种台绑定的疫苗库存信息
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/11 15:00
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryStockList(Map<String, Object> map);
    /** 
    * @Description:  查询接种台绑定的疫苗库存信息总数
    * @Param: [map] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/11 15:00
    * @Tel  17328595627
    */ 
    int queryStockTotal(Map<String, Object> map);
    /** 
    * @Description: 保存领取的疫苗 
    * @Param: [param] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/11 16:18
    * @Tel  17328595627
    */ 
    int saveReceiveVac(Map param) throws Exception;
    /** 
    * @Description: 查询已经领取的疫苗库存 
    * @Param: [map]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 10:56
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReceiveList(Map<String, Object> map);
    /** 
    * @Description: 查询领取库存的总条数
    * @Param: [map] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 11:32
    * @Tel  17328595627
    */ 
    int queryReceiveListTotal(Map<String, Object> map);
    /** 
    * @Description: 查询领取记录总条数 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 11:34
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReceiveTotalList(Map<String, Object> map);
    /** 
    * @Description: 查询分页条数 
    * @Param: [map]
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 11:53
    * @Tel  17328595627
    */ 
    int queryReceiveTotalListTotal(Map<String, Object> map);
    /** 
    * @Description: 查询领取记录明细 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 14:32
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReceiveItemList(Map<String, Object> map);
    /** 
    * @Description:  查询领取记录明细
     * @Param: [map]
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 14:32
    * @Tel  17328595627
    */ 
    int queryReceiveItemListTotal(Map<String, Object> map);
    /** 
    * @Description: 查询领取的疫苗批号 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/15 10:43
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryBatchNum(Map<String, Object> map);
    /** 
    * @Description: 查询领取的疫苗生产运输单 
    * @Param: [temp] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/23 16:52
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReceiveListByExcel(Map<String, Object> temp);
    /** 
    * @Description: 查询其他接种台领取该疫苗信息
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018-12-22 14:11
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryOtherBatchNum(Map<String, Object> map);
}
