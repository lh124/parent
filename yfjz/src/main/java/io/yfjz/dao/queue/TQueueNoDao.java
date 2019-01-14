package io.yfjz.dao.queue;

import io.yfjz.dao.BaseDao;
import io.yfjz.entity.queue.TQueueNoEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-22 23:59:55
 */
public interface TQueueNoDao extends BaseDao<TQueueNoEntity> {
	/**
	 * 获取今日已取号总数
	 * @return
	 */
	Integer queryTodayTotal();

	int queryTodayTotalByPosition(String position);

	/**
	 * 获取当前桌台的号码列表
	 * @param paramMap
	 * @return
	 */
	List<TQueueNoEntity> getOwnQueueList(HashMap<String, Object> paramMap);

	/**
	 * 获取当前桌台的延迟号码列表
	 * @param paramMap
	 * @return
	 */
	List<TQueueNoEntity> getOwnDelayQueueList(HashMap<String,Object> paramMap);

	/**
	 * 获取当前步骤的未处理号码列表
	 * @param paramMap
	 * @return
	 */
	List<TQueueNoEntity> getStepQueueList(HashMap<String,Object> paramMap);
	/**
	 * 获取当前等待人数
	 * @return
	 */
	Integer queryTodayWaitingNum();
	/** 
	* @Description: 绑定儿童 
	* @Param: [tempMap] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/9/25 18:06
	* @Tel  17328595627
	*/ 
    void updateChildCode(Map<String, Object> tempMap);

    List<String> getRegisterVaccine(String childCode);

    /**根据儿童编码获取该儿童本次登记的疫苗编码*/
    List<String> getRegisterVaccineIds(String childCode);

	/**
	 * @method_name: 获取正在留观的号
	 * @describe:
	 * @return java.util.List<io.yfjz.entity.queue.TQueueNoEntity>
	 * @author 邓召仕
	 * @date: 2018-11-17  17:28
	 **/
    List<TQueueNoEntity> getObserveNo();
}
