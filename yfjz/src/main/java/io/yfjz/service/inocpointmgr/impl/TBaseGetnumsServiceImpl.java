package io.yfjz.service.inocpointmgr.impl;

import io.yfjz.dao.inocpointmgr.TBaseGetnumsDao;
import io.yfjz.dao.sys.SysConfigurationDao;
import io.yfjz.dao.sys.SysDictDao;
import io.yfjz.entity.sys.SysConfigurationEntity;
import io.yfjz.entity.sys.SysDictEntity;
import io.yfjz.service.inocpointmgr.TBaseGetnumsService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 取号设置
 * @describe:
 * @param 
 * @return 
 * @author 邓召仕
 * @date: 2018-08-31  14:16
 **/
@Service("tBaseGetnumsService")
public class TBaseGetnumsServiceImpl implements TBaseGetnumsService {
	@Autowired
	private TBaseGetnumsDao tBaseGetnumsDao;

	@Autowired
//	private SysDictDao dictDao;
	private SysConfigurationDao configurationDao;
	@Override
	public Map<String, Object> queryObject(String orgId){
		return tBaseGetnumsDao.queryByOrgId();
	}
	
	@Override
	public void saveOffernumber(Map<String, Object> param, String orgId){
	    param.put("orgid",orgId);
	    param.put("status",1);
	    tBaseGetnumsDao.deleteByOrgId(orgId);
	    tBaseGetnumsDao.save(param);
	}

	@Override
	public void offernumberState(Integer status, String orgId) {
		Map<String,Object> param = new HashMap<>();
		param.put("orgid",orgId);
		param.put("status",status);
		Map<String, Object> selMap = tBaseGetnumsDao.queryByOrgId();
		if (selMap != null){
			tBaseGetnumsDao.updateStateByOrgId(param);
		}else {
			tBaseGetnumsDao.save(param);
		}
	}

	@Override
	public R offerNumberCheck() {
		Map<String, Object> selMap = tBaseGetnumsDao.queryByOrgId();
		if (selMap == null){
			return R.ok();
		}else {
			try{
				Integer status = (Integer) selMap.get("status");
				if (status ==0){
					return R.error(1,"已禁用取号");
				}
				R rulst = getSysConType(Constant.QUEUE_TYPE,1);
				if (rulst != null && rulst.get("typeValue")!=null && ((Integer)rulst.get("typeValue")).intValue() == 0){
					return R.error(1,"未启用排队叫号，请直接到登记台登记");
				}
			}catch (Exception e){}
		}
		Map<String, Object> map = tBaseGetnumsDao.queryCheckDataByOrgId();
		if(map!=null && map.get("morning").toString().equals("0") && map.get("afternoon").toString().equals("0")){

			String msg = "不在取号时间，上午取号时间从"+map.get("m_stime")+"到"+map.get("m_etime")+",下午取号时间从"+map.get("a_stime")+"到"+map.get("a_etime")+"！";
			return R.error(1,msg);
		}
		else if(map!=null && map.get("morning").toString().equals("1") && map.get("m_num").toString().equals("1")){
			String msg = "上午取号人数已满，请下午再来！";
			return R.error(1,msg);
		}
		else if(map!=null && map.get("afternoon").toString().equals("1") && map.get("a_num").toString().equals("1")){
			String msg = "取号人数已满，请明天再来！";
			return R.error(1,msg);
		}
		return R.ok();
	}

	@Override
	public void updateBarCodeIp(SysConfigurationEntity ipAddress) {
       /* List<SysDictEntity> ips = dictDao.selectListByType("barCodeIpAddress");
        if (ips.size()>0){
            SysDictEntity sysDictEntity = ips.get(0);
            sysDictEntity.setValue(ipAddress.getValue());
            dictDao.update(sysDictEntity);
        }else{
            dictDao.save(ipAddress);
        }*/
         ipAddress.setType("barCodeIpAddress");
		SysConfigurationEntity ips = configurationDao.queryObject(ipAddress);
		if (ips!=null){

			ips.setRemark(ipAddress.getRemark());
			configurationDao.update(ips);
		}else{
			configurationDao.save(ipAddress);
		}
	}

	@Override
	public void setSysConType(Integer status, String type,String remark) {
		SysConfigurationEntity sysConfigurationEntity = new SysConfigurationEntity();
		sysConfigurationEntity.setType(type);
		SysConfigurationEntity queueType = configurationDao.queryObject(sysConfigurationEntity);
		if (queueType != null){
			queueType.setStartUsing(status);
			queueType.setRemark(remark);
			queueType.setUpdateTime(new Date());
			configurationDao.update(queueType);
		}else{
			queueType = new SysConfigurationEntity();
			queueType.setId(UUID.randomUUID().toString());
			queueType.setType(type);
			queueType.setStartUsing(status);
			queueType.setRemark(remark);
			queueType.setCreateTime(new Date());
			configurationDao.save(queueType);
		}
	}

	@Override
	public R getSysConType(String sysType,Integer defaultValue) {
		SysConfigurationEntity sysConfigurationEntity = new SysConfigurationEntity();
		sysConfigurationEntity.setType(sysType);
		SysConfigurationEntity queueType = configurationDao.queryObject(sysConfigurationEntity);
		if (queueType != null){
			return R.ok().put("typeValue",queueType.getStartUsing()==null?defaultValue:queueType.getStartUsing());
		}else {
			return R.ok().put("typeValue",defaultValue);
		}
	}
}
