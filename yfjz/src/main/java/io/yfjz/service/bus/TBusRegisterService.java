package io.yfjz.service.bus;


import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.bus.TBusRegisterEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.queue.TQueueNoEntity;

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
public interface TBusRegisterService {

	TBusRegisterEntity queryObject(String id);


	List<TBusRegisterEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(TBusRegisterEntity tBusRegister);

	void update(TBusRegisterEntity tBusRegister);

	void delete(String id);

	void deleteBatch(String[] ids);

	/**
	 * @method_name: registeList
	 * @describe: 根据儿童编码获取登记的列表
	 * @param: [childCode]
	 * @return: io.yfjz.entity.bus.TBusRegisterEntity
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/17  17:07
	 **/
	List<Map> registeList(String childCode);

	/**
	 * @method_name: getTodayRegisterList
	 * @describe: 获取某儿童当日登记的疫苗列表
	 * @param: [childCode]
	 * @return: java.util.List<java.util.Map> 
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/23  15:23
	 **/
	List<Map> getTodayRegisterList(String childCode);
	/**
	 * @Description: 取消时，绑定儿童
	 * @Param: [tempMap]
	 * @return: void
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/9/25 18:04
	 * @Tel  17328595627
	 */
	void updateQueue(Map<String, Object> tempMap);
	/**
	 * @Description: 查询今日已登记儿童
	 * @Param: []
	 * @return: java.util.List<java.util.Map>
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/10/8 18:22
	 * @Tel  17328595627
	 * @param map
	 */
	List<Map> queryToDayRegisterList(Map<String, Object> map);
	/**
	 * @Description: 查询登记等待队列
	 * @Param: [map]
	 * @return: java.util.List<java.util.Map>
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/10/9 18:05
	 * @Tel  17328595627
	 */
	List<Map> queryToDayWaitList(Map<String, Object> map);

	int queryToDayWaitTotal();

	int queryToDayRegisterTotal();



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

	/**
	 * 已完成接种统计
	 * @return
	 */
	List<TChildInfoEntity> finishInoculate();

	Map<String,Object> navCount(String orgid);

}
