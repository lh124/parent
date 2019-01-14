package io.yfjz.dao.bus;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/8/11 15:01
 * 领取疫苗DAO
 * @author tianjinhai
 */
public interface ReceiveDao {
    /** 
    * @Description: 查询库存信息 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/11 15:05
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryStockList(Map<String, Object> map);
    /** 
    * @Description: 查询库存信息总条数 
    * @Param: [map] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/11 15:06
    * @Tel  17328595627
    */ 
    int queryStockTotal(Map<String, Object> map);
    /** 
    * @Description: 查询接种台绑定的疫苗ID 集合 
    * @Param: [towerId] 
    * @return: java.util.List<java.lang.String> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/11 15:20
    * @Tel  17328595627
    */ 
    List<String> queryBindVac(String towerId);
    /** 
    * @Description: 查询领取的库存ID 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 11:00
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReceiveList(Map<String, Object> map);
    /** 
    * @Description: 查询领取疫苗的总条数 
    * @Param: [map] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/14 11:12
    * @Tel  17328595627
    */ 
    int queryReceiveListTotal(Map<String, Object> map);

    List<Map<String,Object>> queryBatchNum(Map<String, Object> map);

    List<Map<String,Object>> queryReceiveListByExcel(Map<String, Object> temp);

    List<Map<String,Object>> queryOtherBatchNum(Map<String, Object> map);
}
