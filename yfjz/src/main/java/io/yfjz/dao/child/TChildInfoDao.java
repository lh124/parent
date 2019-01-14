package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.child.IntegrityRateEntity;
import io.yfjz.entity.child.TChildInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童基本信息表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 15:02:10
 */
public interface TChildInfoDao extends BaseDao<TChildInfoEntity> {
    //List<TChildInfoEntity> queryListtype(Map<String, Object> map);
    Map queryForPrint(Map<String, Object> map);
    List<IntegrityRateEntity> getIntegrityRateByCondition(Map<String, Object> map);

    /**
     * 儿童不完整信息
     * @param map
     * @return
     */
    List<TChildInfoEntity> getListImperfectDataChild(Map<String, Object> map);

    int queryTotalImperfectChild(Map<String, Object> map);

    List<TChildInfoEntity> CurrentDayInoculateChild(Map<String, Object> map);

    List<TChildInfoEntity> getListUnSyncstatusInocChild(Map<String, Object> map);
    int queryTotalCurrentDayInoculateChild(Map<String, Object> map);

    List<TChildInfoEntity> currentDayWaitInoculateChild(Map<String, Object> map);
    int queryTotalCurrentDayWaitInoculateChild(Map<String, Object> map);

    List<Map<String,Object>> findChildInfoForUpload(String chilCode);

    List<Map<String,Object>> getListImperfectInocChild (Map<String, Object> map);

    int queryTotalImperfectInocChild(Map<String, Object> map);

    int queryAllTotal();

    /**
     * 修改儿童条形码信息
     * @describe:
     * @param pama
     * @return void
     * @author 邓召仕
     * @date: 2018-10-15  16:53
     **/
    void updateBarCode(Map<String, String> pama);

    void updatainfo(TChildInfoEntity var1);
    /** 
    * @Description: 查询户籍县国标 
    * @Param: [s] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/15 10:46
    * @Tel  17328595627
    */
    String queryHabiId(String s);
    /** 
    * @Description: 查询当前接种点所在地址 
    * @Param: [orgId] 
    * @return: java.lang.String 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/11/17 17:17
    * @Tel  17328595627
    */ 
    String getCurrentAddress(String orgId);
}
