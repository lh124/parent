package io.yfjz.service.queue;

import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.queue.TQueueNoEntity;
import io.yfjz.utils.R;

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
public interface TQueueNoService {
	
	TQueueNoEntity queryObject(String id);
	
	List<TQueueNoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TQueueNoEntity tQueueNo);

	void update(TQueueNoEntity tQueueNo);

	void delete(String id);

	void deleteBatch(String[] ids);

	/**
	 * 生成号码
	 */
	R cateateNo(String  childCode);

	/**
	 * 完成当前步骤
	 * @param step
	 */
	void finishedCurrentStep(String step,String queue_id,String childCode,String childName);

	/**
	 * 获取下一步骤
	 * @param step
	 * @return
	 */
	String getNextStep(String step);

	/**
	 * 关闭或打开取号功能
	 */
	void disabledQueue(Boolean value);

	/**
	 * 从队列表取出属于本台的队列
	 * @param paramMap
	 * @return
	 */
	List<TQueueNoEntity> getOwnQueueList(HashMap<String, Object> paramMap);
	/**
	 * 从队列表取出属于本台的延迟队列
	 * @param paramMap
	 * @return
	 */
	List<TQueueNoEntity> getOwnDelayQueueList(HashMap<String, Object> paramMap);
	/**
	 * 从队列表取出特定步骤的队列
	 * @param paramMap
	 * @return
	 */
	List<TQueueNoEntity> getStepQueueList(HashMap<String, Object> paramMap);

	/**
	 * 获取已登录的桌台
	 * @param towerType
	 * @return
	 */
	List<TBaseTowerEntity> getLoginedTower(Integer towerType);

	/**
	 * 获取本次已登记疫苗名称
	 */
	List<String> getRegisterVaccine(String childCode);
	/**
	 * 获取本次已登记疫苗id集合
	 */
	List<String> getRegisterVaccineIds(String childCode);

	/**
	 * @method_name: 获取正在留观的号及儿童
	 * @describe:
	 * @return java.util.List<io.yfjz.entity.queue.TQueueNoEntity>
	 * @author 邓召仕
	 * @date: 2018-11-17  17:26
	 **/
    List<TQueueNoEntity> getObserveNo();
}
