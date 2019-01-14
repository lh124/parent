package io.yfjz.service.bus;

import groovy.json.internal.CacheType;
import io.yfjz.entity.bus.TBusCancelEntity;
import io.yfjz.entity.rule.ChildData;
import io.yfjz.utils.Query;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * create by tianjinhai on 2018/9/4 9:29
 */
public interface BatchInoculateService {
    /** 
    * @Description: 保存应种未种查询结果
    * @Param: [queryResult]
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/4 9:30
    * @Tel  17328595627
     * @param queryResult
     * @param param
     */
    void saveQueryResult(List<ChildData> queryResult, Map<String, Object> param);
    /** 
    * @Description: 查询总条数 
    * @Param: [query] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/4 15:54
    * @Tel  17328595627
    */ 
    int queryTotal(Query query);
    /** 
    * @Description: 查询批量集合
    * @Param: [query] 
    * @return: java.util.List<io.yfjz.entity.bus.TBusCancelEntity> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/4 15:54
    * @Tel  17328595627
    */ 
    List<ChildData> queryList(Query query);
    /** 
    * @Description: 查询所有的查询记录
    * @Param: [] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/5 9:41
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryAllTimes();

    /** 
    * @Description: 删除查询记录 
    * @Param: [selectTimes] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/5 10:31
    * @Tel  17328595627
    */ 
    int deleteTimes(String selectTimes);
    /** 
    * @Description: 批量补录疫苗 
    * @Param: [list] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/5 17:27
    * @Tel  17328595627
    */
    Map<String,Object> saveInoculateInfo(List<Map<String,Object>> list) throws Exception;
    /** 
    * @Description: 从查询记录中删除已经补录的信息，根据儿童的ID，疫苗规划ID，剂次删除
    * @return: int
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/5 19:08
    * @Tel  17328595627
    */ 
    int deleteHistoryRecord(Map param);

    Map<String,Object> uploadFile(MultipartFile file, String uploadfilepath);
    /** 
    * @Description: 查询所规划字典表所有的疫苗分类名称
    * @Param: [] 
    * @return: java.lang.String[] 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/28 15:40
    * @Tel  17328595627
    */ 
    String[] getAllVaccine();
    /** 
    * @Description: 查询个案状态码表 
    * @Param: [] 
    * @return: java.lang.String[] 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/28 17:09
    * @Tel  17328595627
    */ 
    String[] queryChildInfoStatus();
    /** 
    * @Description: 单条补录 
    * @Param: [m] 
    * @return: java.util.Map 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/4 13:27
    * @Tel  17328595627
    */ 
    void singleSaveInoc(Map<String,Object> m) throws Exception;
    /** 
    * @Description: 查询领取的疫苗批号 
    * @Param: [planId]
    * @return: java.lang.String[] 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018-12-05 14:44
    * @Tel  17328595627
    */ 
    List<Map<String, Object>> queryVaccineBatchnum(String planId);
    /** 
    * @Description: 查询接种部位 
    * @Param: [] 
    * @return: java.lang.String[] 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018-12-06 10:40
    * @Tel  17328595627
    */ 
    String[] queryInoculateSiteList();
    /** 
    * @Description: 改变儿童的个案信息和备注信息
    * @Param: [rows] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018-12-07 10:19
    * @Tel  17328595627
    */ 
    int changeChildInfo(List<Map<String,Object>> rows);
}
