package io.yfjz.service.report;

import io.yfjz.entity.statistics.SixToOneResult;
import io.yfjz.utils.R;

import java.util.List;
import java.util.Map; /**
* @Description: 6-1报表接口
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/29 9:53
* @Tel  17328595627
*/ 
public interface SixToOneStatisticsService {
    /** 
    * @Description: 查询6-1报表数据
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/29 9:55
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryList(Map<String, Object> queryMap, String residence);

    /**
     * @method_name: 6-1汇总查询
     * @describe:
     * @param queryMap
     * @return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @author 邓召仕
     * @date: 2018-11-04  15:49
     **/
    List<SixToOneResult> queryTotalList(Map<String, Object> queryMap, String residence);

    /**
     * @author 饶士培
     * @param queryMap
     * @return
     * @date 2018-11-26  10:49
     */
    R uploadPlatform(Map<String, Object> queryMap);
}
