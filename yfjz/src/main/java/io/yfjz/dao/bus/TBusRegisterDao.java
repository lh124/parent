package io.yfjz.dao.bus;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.bus.TBusRegisterEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.queue.TQueueNoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 儿童接种登记表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-17 14:25:03
 */
public interface TBusRegisterDao extends BaseDao<TBusRegisterEntity> {

    /**
     * @method_name: registeList
     * @describe: 根据儿童编码获取登记的列表
     * @param: [childCode]
     * @return: java.util.List<io.yfjz.entity.bus.TBusRegisterEntity>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/17  17:11
     **/
    List<Map> registeList(@Param("childCode") String childCode);

    /**
     * @method_name: exist
     * @describe: 判断儿童是否已经登记了同类型的疫苗
     * @param: [paramMap]
     * @return: boolean
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/18  15:08
     **/
    int exist(HashMap<String, Object> paramMap);


    /**
     * @method_name: alreadyRegisterList
     * @describe: 获取某儿童当日已登记的疫苗
     * @param: [paramMap]
     * @return: java.util.List<io.yfjz.entity.bus.TBusRegisterEntity>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/22  17:45
     **/
    List<HashMap> alreadyRegisterList(HashMap<String, Object> paramMap);

    /**
     * @method_name: deleteAll
     * @describe: 优先删除今日已登记的疫苗，然后再保存登记界面的推荐列表
     * @param: [childCode]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/23  10:32
     *
     **/
    void deleteAll(@Param("childCode") String childCode);

    /**
     * @method_name: getTodayRegisterList
     * @describe: 获取某儿童当日登记的疫苗列表
     * @param: [childCode]
     * @return: java.util.List<java.util.Map>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/23  15:24
     **/
    List<Map> getTodayRegisterList(String childCode);

    /**
     * @method_name: updateStatus
     * @describe: 更新状态为已完成 更新登记列表的状态
     * @param: [param]
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/4  17:24
     **/
    void updateStatus(HashMap<String, Object> param);


    /**
     * @method_name: countTodayRegister
     * @describe: 统计今日是否还有未接种的疫苗，已登记成功的
     * @param: [childCode]
     * @return: java.util.Map
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/12  15:47
     **/
    Map countTodayRegister(@Param("childCode") String childCode);
    /**
    * @Description: 查询今日已登记的儿童
    * @Param: []
    * @return: java.util.List<java.util.Map>
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/8 18:26
    * @Tel  17328595627
     * @param queryMap
    */
    List<Map> queryToDayRegisterList(Map<String, Object> queryMap);
    /**
    * @Description: 查询登记的疫苗
    * @Param: []
    * @return: java.lang.String
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/8 19:58
    * @Tel  17328595627
    */
    String  queryRegisterNames(String childCode);
    /**
    * @Description: 查询待登记列表
    * @Param: []
    * @return: java.util.List<java.util.Map>
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/9 10:34
    * @Tel  17328595627
     * @param queryMap
    */
    List<Map> queryToDayWaitList(Map<String, Object> queryMap);




    /**
     * 当日登记总人数
     * @return
     */
    Integer sumregister();

    /**
     * 当日还未登记人数
     * @return
     */
    Integer noregister();

    List<TQueueNoEntity> noregisterlist();

    /**
     * 未登记人数
     * @return
     */
    List<TQueueNoEntity> noregisterlists();


//    List<TQueueNoEntity> registerlist();
    /**
     * 已登记人数
     * @return
     */
    List<TQueueNoEntity> registerlists();

    /**
     * 未接种人数
     * @return
     */
    List<TQueueNoEntity> noinoculatelist();

    /**
     * 未接种人数
     * @return
     */
    Integer noinoculate();

    /**
     * 取号数
     * @return
     */
    Integer  getNumber();

    /**
     * 待预检人数
     * @return
     */
    List<TQueueNoEntity> waitsumprecheck();

    List<TQueueNoEntity> sumprechecks();

    /**
     * 待儿保人数
     * @return
     */
    List<TQueueNoEntity> sumhealthcare();

    List<TQueueNoEntity> sumhealthcares();

    List<TChildInfoEntity> finishInoculate();

    int queryToDayWaitTotal();

    int queryToDayRegisterTotal();
//查询导航栏监控所有数据，不用分开查询
    Map<String,Object> navCount(String orgid);
}
