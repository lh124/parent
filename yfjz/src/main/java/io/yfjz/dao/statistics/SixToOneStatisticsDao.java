package io.yfjz.dao.statistics;

import io.yfjz.entity.statistics.SixToOneResult;

import java.util.List;
import java.util.Map;

/**
* @Description: 儿童全程服务接口 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/23 14:29
* @Tel  17328595627
*/ 
public interface SixToOneStatisticsDao {
    /** 
    * @Description: 查询6-1记录
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/23 14:32
    * @Tel  17328595627
    */ 
    List<SixToOneResult> queryList(Map<String, Object> queryMap);

    /** 
    * @Description: 计算第一针乙肝是否及时
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/29 15:30
    * @Tel  17328595627
    */ 
    List<SixToOneResult> articleFirstTimely(Map<String, Object> queryMap);

    /**
     * @method_name: 6-1汇总查询
     * @describe:
     * @param queryMap
     * @return java.util.List<io.yfjz.entity.statistics.SixToOneResult>
     * @author 邓召仕
     * @date: 2018-11-04  15:53
     **/
    List<SixToOneResult> queryTotalList(Map<String, Object> queryMap);

    /**
     * @method_name: 本地应种或流动应种
     * @describe:
     * @return java.lang.Integer
     * @author 邓召仕
     * @date: 2018-11-10  15:59
     **/
    Integer queryShouldIno(Map<String, Object> queryMap);

    /**
     * @method_name: 本地实种或流动实种
     * @describe:
     * @return java.lang.Integer
     * @author 邓召仕
     * @date: 2018-11-10  16:21
     **/
    Integer queryRealIno(Map<String, Object> queryMap);

    /**
     * @method_name: 更加接种记录查询实种数
     * @describe:
     * @return java.lang.Integer
     * @author 邓召仕
     * @date: 2018-11-10  17:37
     **/
    Integer queryRealInoFromIno(Map<String, Object> queryMap);
    /**
     * @method_name: 查询实种及时数
     * @describe:
     * @return java.lang.Integer
     * @author 邓召仕
     * @date: 2018-11-10  17:37
     **/
    Integer queryRealInoTimely(Map<String, Object> queryMap);

    /**
     * @method_name: queryUploadList
     * @describe: 上传报表数据查询
     * @param queryMap
     * @return
     * @author 饶士培
     * @date 2018-11-26 10:51
     */
    List<Map<String, Object>> queryUploadList(Map<String, Object> queryMap);
}
