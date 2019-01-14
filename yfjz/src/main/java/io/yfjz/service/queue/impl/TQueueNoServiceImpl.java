package io.yfjz.service.queue.impl;

import io.yfjz.dao.child.TChildInfoDao;
import io.yfjz.dao.sys.SysDictDao;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.queue.QueueAction;
import io.yfjz.entity.queue.QueueConfig;
import io.yfjz.entity.sys.SysConfigurationEntity;
import io.yfjz.service.basesetting.TBaseTowerService;
import io.yfjz.service.inocpointmgr.InoculationManagerService;
import io.yfjz.service.inocpointmgr.TBaseGetnumsService;
import io.yfjz.service.sys.SysConfigurationService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import io.yfjz.dao.queue.TQueueNoDao;
import io.yfjz.entity.queue.TQueueNoEntity;
import io.yfjz.service.queue.TQueueNoService;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;


/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-22 23:59:55
 */
@Service("tQueueNoService")
public class TQueueNoServiceImpl implements TQueueNoService {
	@Autowired
	private TQueueNoDao tQueueNoDao;
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	QueueConfig queueConfig;
    @Autowired
    SysDictDao sysDictDao;
    @Autowired
	TBaseGetnumsService tBaseGetnumsService;
    @Autowired
	TBaseTowerService tBaseTowerService;
    @Autowired
	InoculationManagerService inoculationManagerService;

	@Autowired
	private SysConfigurationService sysConfigurationService;
	//儿童dao
	@Autowired
	private TChildInfoDao tChildInfoDao;


	@Override
	public TQueueNoEntity queryObject(String id){
		return tQueueNoDao.queryObject(id);
	}
	
	@Override
	public List<TQueueNoEntity> queryList(Map<String, Object> map){
		return tQueueNoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tQueueNoDao.queryTotal(map);
	}
	
	@Override
	public void save(TQueueNoEntity tQueueNo){
		tQueueNoDao.save(tQueueNo);
	}
	
	@Override
	public void update(TQueueNoEntity tQueueNo){
		tQueueNoDao.update(tQueueNo);
	}
	
	@Override
	public void delete(String id){
		tQueueNoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tQueueNoDao.deleteBatch(ids);
	}

    /**
     * 取号
     * @return
     */
	@Override
	public synchronized R cateateNo(String childCode) {
		SysConfigurationEntity config = sysConfigurationService.configuration("pause_number");
		//判断是否暂停取号
		if(config != null && config.getStartUsing() == 0){
			return R.error("已暂停取号");
		}
		R flag = tBaseGetnumsService.offerNumberCheck();
		R result = new R();
		if(Integer.parseInt(flag.get("code").toString()) == 0){
			//判断是否为扫描取号（判断儿童编码是否为空）
     		TChildInfoEntity childInfo = null;
     		try{
     			if (!StringUtils.isEmpty(childCode)){
     				childInfo = tChildInfoDao.queryObject(childCode);
				}
			}catch (Exception e){}
			TQueueNoEntity tQueueNoEntity = new TQueueNoEntity();
			Integer count = tQueueNoDao.queryTodayTotal();
			tQueueNoEntity.setCreateTime(new Date());
			tQueueNoEntity.setNo(count+1);
			tQueueNoEntity.setNoText(generateNoText(count,"A"));
			tQueueNoEntity.setAction(QueueAction.CREATE);
			//添加扫描取号绑儿童
			if (childInfo != null){
				tQueueNoEntity.setChildCode(childInfo.getChilCode());
				tQueueNoEntity.setChildName(childInfo.getChilName());
			}
			//将步骤设为配置的首步骤
			String firstStep  = inoculationManagerService.getProcessFirst().toString();
			tQueueNoEntity.setStep(firstStep);
			tQueueNoDao.save(tQueueNoEntity);
			jmsTemplate.convertAndSend(queueConfig.getTopicMap().get(firstStep),tQueueNoEntity);
			result.put("noText",tQueueNoEntity.getNoText());
			result.put("id",tQueueNoEntity.getNoText());
			result.put("currentTime",new Date());
			result.put("waiting",String.valueOf(tQueueNoDao.queryTodayWaitingNum()));
		}else{
			return R.error(flag.get("msg").toString());
		}

		return result;
	}


	/**
	 * 完成当前步骤,并转移到下一步骤
	 * @param step
	 * @param queue_id
	 * @param childCode
	 * @param childName
	 */
    @Override
    public void finishedCurrentStep(String step,String queue_id,String childCode,String childName) {
    	String userId = null;//deng
    	try{
    		userId = ShiroUtils.getUserId();
		}catch (Exception e){}//deng
    	//获取到当前操作QueueNo
		TQueueNoEntity queue = tQueueNoDao.queryObject(Optional.ofNullable(queue_id).orElseThrow(()->new IllegalArgumentException("step is null")));
		if(queue!=null){
			Optional.ofNullable(childCode).ifPresent((code)->{queue.setChildCode(code);tQueueNoDao.update(queue);});
			Optional.ofNullable(childName).ifPresent((name)->{queue.setChildName(name);tQueueNoDao.update(queue);});
			//邓召仕添加
			Optional.ofNullable(userId).ifPresent((operator)->{queue.setOperator(operator);tQueueNoDao.update(queue);});
			//发送步骤完成消息
			queue.setAction(QueueAction.FINISH);
			jmsTemplate.convertAndSend(queueConfig.getTopicMap().get(step),queue);
		}
	}

	@Override
	public String getNextStep(String step){
		return "test";
	}

    @Override
    public void disabledQueue(Boolean value) {

    }

    @Override
	public List<TQueueNoEntity> getOwnQueueList(HashMap<String, Object> paramMap){
    	return tQueueNoDao.getOwnQueueList(paramMap);
	}

	@Override
	public List<TQueueNoEntity> getOwnDelayQueueList(HashMap<String, Object> paramMap){
		return tQueueNoDao.getOwnDelayQueueList(paramMap);
	}
	@Override
	public List<TQueueNoEntity> getStepQueueList(HashMap<String, Object> paramMap){
		return tQueueNoDao.getStepQueueList(paramMap);
	}
	@Override
	public List<TBaseTowerEntity> getLoginedTower(Integer towerType) {
		return tBaseTowerService.getLoginTowerList(towerType);
	}
	@Override
	public List<String> getRegisterVaccine(String childCode){
    	return tQueueNoDao.getRegisterVaccine(childCode);
	}
	@Override
	public List<String> getRegisterVaccineIds(String childCode){
    	return tQueueNoDao.getRegisterVaccineIds(childCode);
	}

	@Override
	public List<TQueueNoEntity> getObserveNo() {
		return tQueueNoDao.getObserveNo();
	}

	private String generateNoText (Integer count,String type){
	    String prefix = type;
	    if(count < 9){
	        prefix += "00";
        }else if(count < 99){
	        prefix += "0";
        }
	    return prefix + String.valueOf(count+1);
    }





}
