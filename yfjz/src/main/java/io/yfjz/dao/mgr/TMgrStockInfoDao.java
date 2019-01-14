package io.yfjz.dao.mgr;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.mgr.TMgrStockInfoEntity;
import io.yfjz.entity.mgr.TMgrUseRecord;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/** 
* @Description: 库存信息
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 17:08
* @Tel  17328595627
*/ 
public interface TMgrStockInfoDao extends BaseDao<TMgrStockInfoEntity> {
    /**
     * 根据产品基础信息ID，查询库存中是否存在该产品
     *
     * @param storeId 仓库ID
     * @param baseInfoId 库存基本信息ID
     * @return
     */
    TMgrStockInfoEntity  queryByBaseInfoId(@Param("baseInfoId") String baseInfoId,@Param("storeId") String storeId,@Param("equipmentId") String equipmentId);

    List<Map<String,Object>> queryRegisterList(Map<String, Object> map);

    int queryRegisterTotal(Map<String, Object> map);
    /**
     * 根据产品基础信息ID，查询库存中是否存在该产品
     *
     * @param storeId 仓库ID
     * @param baseInfoId 库存基本信息ID
     * @return
     */
    List<TMgrStockInfoEntity> queryStoreInfo(@Param("baseInfoId") String baseInfoId, @Param("storeId") String store);

    int updateZero(TMgrStockInfoEntity towerInfo);
    /** 
    * @Description: 根据疫苗id和批号查询库存中是否 有该疫苗
    * @Param: map
    * @return: List<TMgrStockInfoEntity>
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/3 16:11
    * @Tel  17328595627
    */ 
    TMgrStockInfoEntity queryListByCodeIdAndBatchnum(Map map);

    /** 
    * @Description: 更加疫苗Id和库存ID 查询所有批号的疫苗
    * @Param: [bioCode, storeId] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/5 18:16
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryByCodeAndStore( @Param("bioCode") String bioCode, @Param("storeId") String storeId,@Param("batchnum") String batchnum);

    /**
     * @Description: 每月1号凌晨查询所有仓库的库存,插入库存结余表，作为下月期初库存
     * @Param: []
     * @return: java.util.List<io.yfjz.entity.mgr.TMgrStockInfoEntity>
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/10/26 19:07
     * @Tel  17328595627
     */
    int insertStockInit();

    List<Map<String,Object>> queryReceiveVaccine(@Param("classCode")String className,@Param("storeId")  String id);
    /** 
    * @Description: 查询领取的疫苗是否足够扣减 
    * @Param: [temp] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/28 20:33
    * @Tel  17328595627
    */ 
    int queryStockAmount(Map<String, Object> temp);

    /** 
    * @Description: 判断库存该商品是否充足 
    * @Param: [classId, number, id] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/28 20:53
    * @Tel  17328595627
    */ 
    Map<String,Object> queryStockAmount(@Param("classId") String classId,@Param("storeId") String id);

    TMgrStockInfoEntity queryStockInfoByEquipmentAndStore(Map map);
    /** 
    * @Description: 查询收发记录 
    * @Param: [map] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/26 19:10
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryDispatchList(Map<String, Object> map);
    /** 
    * @Description: 查询领取的疫苗批号
    * @Param: [planId] 
    * @return: java.lang.String[] 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018-12-05 14:45
    * @Tel  17328595627
     * @param planId
     */
    List<Map<String, Object>> queryVaccineBatchum(HashMap<Object, Object> planId);
    /** 
    * @Description: 根据批号查询领取的疫苗
    * @Param: [queryMap] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018-12-07 15:17
    * @Tel  17328595627
    */ 
    List<Map<String,Object>> queryReceiveVaccineByBatchnum(Map<String, Object> queryMap);
    /** 
    * @Description: 插入使用其他工作台疫苗记录
    * @Param: [useRecord] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018-12-24 11:25
    * @Tel  17328595627
    */ 
    void insertUseRecord(TMgrUseRecord useRecord);

    void updateOther(TMgrStockInfoEntity towerInfo);
}
