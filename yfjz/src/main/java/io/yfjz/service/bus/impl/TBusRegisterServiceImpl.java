package io.yfjz.service.bus.impl;

import io.yfjz.dao.bus.TBusRegisterDao;
import io.yfjz.dao.queue.TQueueNoDao;
import io.yfjz.entity.bus.TBusRegisterEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.queue.TQueueNoEntity;
import io.yfjz.service.bus.TBusRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service("tBusRegisterService")
public class TBusRegisterServiceImpl implements TBusRegisterService {
	@Autowired
	private TBusRegisterDao tBusRegisterDao;
	@Autowired
	private TQueueNoDao queueNoDao;
	
	@Override
	public TBusRegisterEntity queryObject(String id){
		return tBusRegisterDao.queryObject(id);
	}


	@Override
	public List<TBusRegisterEntity> queryList(Map<String, Object> map){
		return tBusRegisterDao.queryList(map);
	}


	@Override
	public int queryTotal(Map<String, Object> map){
		return tBusRegisterDao.queryTotal(map);
	}
	
	@Override
	public void save(TBusRegisterEntity tBusRegister){
		tBusRegisterDao.save(tBusRegister);
	}
	
	@Override
	public void update(TBusRegisterEntity tBusRegister){
		tBusRegisterDao.update(tBusRegister);
	}
	
	@Override
	public void delete(String id){
		tBusRegisterDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tBusRegisterDao.deleteBatch(ids);
	}

	@Override
	public List<Map> registeList(String childCode) {
		return tBusRegisterDao.registeList(childCode);
	}

	@Override
	public List<Map> getTodayRegisterList(String childCode) {
		return tBusRegisterDao.getTodayRegisterList(childCode);
	}

    @Override
    public void updateQueue(Map<String, Object> tempMap) {
		queueNoDao.updateChildCode(tempMap);
    }

	@Override
	public List<Map> queryToDayRegisterList(Map<String, Object> queryMap) {
		List<Map> list=tBusRegisterDao.queryToDayRegisterList(queryMap);
//		for (Map map : list) {
//			String bioNames = tBusRegisterDao.queryRegisterNames(map.get("childCode").toString());
//			map.put("bioNames",bioNames);
//		}
		return list;
	}

	@Override
	public List<Map> queryToDayWaitList(Map<String, Object> queryMap) {
		List<Map> list=tBusRegisterDao.queryToDayWaitList(queryMap);
		return list;
	}

	@Override
	public int queryToDayWaitTotal() {

		return tBusRegisterDao.queryToDayWaitTotal();
	}

	@Override
	public int queryToDayRegisterTotal() {
		return tBusRegisterDao.queryToDayRegisterTotal();
	}

	@Override
	public Integer sumregister() {
		return tBusRegisterDao.sumregister();
	}

	@Override
	public Integer noregister() {
		return tBusRegisterDao.noregister();
	}

	@Override
	public List<TQueueNoEntity> noregisterlist() {
		return tBusRegisterDao.noregisterlist();
	}

	@Override
	public List<TQueueNoEntity> noregisterlists() {
		return tBusRegisterDao.noregisterlists();
	}

	@Override
	public List<TQueueNoEntity> registerlists() {
		return tBusRegisterDao.registerlists();
	}

	@Override
	public List<TQueueNoEntity> noinoculatelist() {
		return tBusRegisterDao.noinoculatelist();
	}

	@Override
	public Integer noinoculate() {
		return tBusRegisterDao.noinoculate();
	}

	@Override
	public Integer getNumber() {
		return tBusRegisterDao.getNumber();
	}

	@Override
	public List<TQueueNoEntity> waitsumprecheck() {
		return tBusRegisterDao.waitsumprecheck();
	}

	@Override
	public List<TQueueNoEntity> sumprechecks() {
		return tBusRegisterDao.sumprechecks();
	}

	@Override
	public List<TQueueNoEntity> sumhealthcare() {
		return tBusRegisterDao.sumhealthcare();
	}

	@Override
	public List<TQueueNoEntity> sumhealthcares() {
		return tBusRegisterDao.sumhealthcares();
	}

	@Override
	public List<TChildInfoEntity> finishInoculate() {
		return tBusRegisterDao.finishInoculate();
	}
	@Override
	public Map<String,Object> navCount(String orgid) {
		return tBusRegisterDao.navCount(orgid);
	}

}
