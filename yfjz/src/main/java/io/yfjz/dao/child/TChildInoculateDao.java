package io.yfjz.dao.child;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TVacInfoEntity;
import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.TChildInoculateEntity;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 儿童接种记录表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 14:02:29
 */
public interface TChildInoculateDao extends BaseDao<TChildInoculateEntity> {

    /**
     * 接种剂次计算查询
     * @param map
     * @return
     */
    List<TChildInoculateEntity> querydoses(Map<String, Object> map);

    List<TChildInoculateEntity> queryListdoses(Map<String, Object> map);

    /**
     * 查询儿童最后一剂次接种的时间
     * @param var1
     * @return
     */
    TChildInoculateEntity queryLastInoObject(Object var1);

    /**
     * 查询当天儿童接种记录for打印
     * @param chilCode
     * @return
     */
    List<Map<String,Object>>  queryCurrentDayInoculation(String chilCode);

    /**
     * 今日留观查询
     * @param notext
     * @return
     */
    List<Map<String,Object>>  queryobservation(String notext);
    /**
     * 历史记录留观查询
     * @param notext
     * @return
     */
    List<Map<String,Object>>  queryhistoryobservation(String chilCode);

    /**
     * 查询儿童接种记录for打印
     * @param chilCode
     * @return
     */
    List<Map<String,Object>>  queryAllInoculation(String chilCode);

    /**
     * 查询儿童接种记录for打印
     * @param map
     * @return
     */
    Map<String,Object> queryInoculationById(Map<String,Object> map);

    Map<String,Object> queryWaitInocVaccById(Map<String,Object> map);



    /**
     * @method_name: queryTotalByChildCodeAndVaccCode
     * @describe: 根据疫苗编码在接种信息表中查询该儿童接种了多少剂次
     * @param: [childCode, vaccineCode]
     * @return: int
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/17  14:47
     **/
    int queryTotalByChildCodeAndVaccCode(@Param("childCode") String childCode, @Param("vaccineCode") String vaccineCode);


    /**
     * 批量插入数据
     *
     * @param accountInfoList
     * @return
     */
    void batchInsertAccountInfo(TChildInoculateEntity accountInfoList);

    /**
     * 查询要上传的接种记录
     * @param chilCode
     * @return
     */
    List<Map<String,Object>> findInoclationsForUpload(String chilCode);

    List<Map<String,Object>> uploadRecord(Map<String,Object> map);
    int uploadtotalRecord(Map<String,Object> map);

    List<InoculateIntegrityRateEntity> getInocIntegrityRateByCondition(Map<String,Object> map);

    int queryAllTotal();


    /**
     * @method_name: queryInoculateNumByMap
     * @describe: 判断 同一接种部位同一天不能重复接种
     * @param: [map]
     * @return: int
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/26  10:21
     **/
    int queryInoculateNumByMap(Map<String, Object> map);

    int queryUpdateInoculateNumByMap(Map<String, Object> map);

    List<TChildInoculateEntity> queryUpdateInoculateDate(Map<String,Object> map);

    List<Map<String,Object>>  schoolAdmissionIno(String chilCode);

    List<Map<String,Object>> selectCurrentRegisterRecord(String chilCode);

    int saveAsBackUp(TChildInoculateEntity accountInfoList);

    /**
     * @method_name: queryInoculateVacccodeNumByMap
     * @describe: 判断疫苗今日是否接种，不能重复接种
     * @param: [m]
     * @return: int
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/27  11:18
     **/
    int queryInoculateVacccodeNumByMap(Map m);


    /**
     * 今日已接种总人数
     * @return
     */
    List<TChildInoculateEntity> suminoculateall();

    /**
     * //今日已接种总数/带留观完成数
     * @return
     */
    List<TChildInoculateEntity> inoculatelists(String org_id);

    /**
     * 留观完成总数
     * @return
     */
    List<TChildInoculateEntity> observelist(String orgid);

    /**
     *未留观完成总数
     * @return
     */
    List<TChildInoculateEntity> noobservelist();

    List<TChildInoculateEntity> querylistjzbl(Map<String,Object> map);


    /**
     * 查询儿童登记记录for打印
//     * @param list
     * @return
     */
    List<Map<String,Object>> queryCurrentDayWaitInocBioName(String chilCode);

    int observeupdate(TChildInoculateEntity var1);

    List<TVacInfoEntity> outsideinoculatebio();

    List<TVacInfoEntity> getBioClassIdType(String bioClassId);



}
