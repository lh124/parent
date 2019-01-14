package io.yfjz.service.child.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import io.yfjz.dao.child.TChildMoveDao;
import io.yfjz.entity.child.IntegrityRateEntity;
import io.yfjz.entity.child.TChildMoveEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.managerservice.rule.InitPlanService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.dao.child.TChildInfoDao;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.service.child.TChildInfoService;
import org.springframework.util.StringUtils;


/**
 * 儿童基本信息表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 15:02:10
 */
@Service("tChildInfoService")
public class TChildInfoServiceImpl implements TChildInfoService {
	@Autowired
	private TChildInfoDao tChildInfoDao;
	@Autowired
	private InitPlanService initPlanService;
	@Autowired
	private TChildMoveDao tChildMoveDao;

	@Override
	public TChildInfoEntity queryObject(String chilCode){
		return tChildInfoDao.queryObject(chilCode);
	}

	@Override
	public List<TChildInfoEntity> queryList(Map<String, Object> map){
		return tChildInfoDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildInfoDao.queryTotal(map);
	}

	@Override
	public void save(TChildInfoEntity tChildInfo){
		try {
			if (tChildInfo.getChilHabiaddress().length() < 5) {
				tChildInfo.setChilHabiId("");
			} else {
				String habiAdress = querychilHabiId(tChildInfo.getChilHabiaddress());
				tChildInfo.setChilHabiId(habiAdress);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		tChildInfoDao.save(tChildInfo);
		try{
			initPlanService.initChildPlan(tChildInfo.getChilCode());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 查询户籍县国标
	 * @Param: [address]
	 * @return: java.lang.String
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/11/15 10:38
	 * @Tel  17328595627
	 */
	private String querychilHabiId(String address){
		String habId="";
		if (!StringUtils.isEmpty(address)&&address.indexOf("-")>-1){
			String[] split = address.split("-");
			if(!StringUtils.isEmpty(split[0])){
				String newAddress=split[0];
				String[] newSplit = newAddress.split(" ");
				if ("北京".equals(newSplit[0])||"天津".equals(newSplit[0])||"重庆".equals(newSplit[0])||"上海".equals(newSplit[0])){
					habId= tChildInfoDao.queryHabiId(newSplit[1].trim());
				}else {
					habId=  tChildInfoDao.queryHabiId(newSplit[2].trim());
				}

			}
		}
		return habId;
	}
	@Override
	public void update(TChildInfoEntity tChildInfo){
		TChildInfoEntity entity = tChildInfoDao.queryObject(tChildInfo.getChilCode());
		String preHere = entity.getChilHere();
		String newHere = tChildInfo.getChilHere();
		try {
			if (StringUtils.isEmpty(tChildInfo.getChilHabiaddress())){
				tChildInfo.setChilHabiId(entity.getChilHabiId());
			}else{
				System.out.println(tChildInfo.getChilHabiaddress().lastIndexOf("-"));
				if ((tChildInfo.getChilHabiaddress().lastIndexOf("-")) <= 6) {
					tChildInfo.setChilHabiId("");
				} else {
					String habiAdress = querychilHabiId(tChildInfo.getChilHabiaddress());
					tChildInfo.setChilHabiId(habiAdress);
				}
			}
		}catch (Exception e){

			e.printStackTrace();
		}
//        String habiId = querychilHabiId(tChildInfo.getChilHabiaddress());
//        if (StringUtils.isEmpty(habiId)){
//            tChildInfo.setChilHabiId(entity.getChilHabiId());
//        }else{
//            tChildInfo.setChilHabiId(habiId);
//        }
		if(entity!=null){
			if(preHere!=null && newHere!=null && !preHere.equals(newHere)){
				tChildInfo.setChilLeavedate(new Date());
				tChildInfo.setChilLeaveremark(tChildInfo.getChilLeaveremark());
				if("1".equals(newHere)){
					tChildInfo.setChilCurdepartment(ShiroUtils.getUserEntity().getOrgId());
				}
			}
			tChildInfoDao.update(tChildInfo);
		}
		if(preHere!=null && newHere!=null && !preHere.equals(newHere)){
			TChildMoveEntity moveEntity = new TChildMoveEntity();
			moveEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
			moveEntity.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
			moveEntity.setOrgId(Constant.GLOBAL_ORG_ID);
			moveEntity.setChheDepaId(Constant.GLOBAL_ORG_ID);
			moveEntity.setChhePrehere(preHere);
			moveEntity.setChheHere(newHere);
			moveEntity.setChheChilProvinceremark(tChildInfo.getChilLeaveremark());
			moveEntity.setChilCode(tChildInfo.getChilCode());
			tChildMoveDao.save(moveEntity);
		}
	}

	@Override
	public void updatainfo(TChildInfoEntity tChildInfo){
		tChildInfoDao.updatainfo(tChildInfo);
	}

	@Override
	public void delete(String chilCode){
		tChildInfoDao.delete(chilCode);
	}

	@Override
	public void deleteBatch(String[] chilCodes){
		tChildInfoDao.deleteBatch(chilCodes);
	}

	@Override
	public List<IntegrityRateEntity> listIntegrityRate(Map<String, Object> map) {
		return tChildInfoDao.getIntegrityRateByCondition(map);
	}

	@Override
	public List<TChildInfoEntity> listImperfectChild(Map<String, Object> map) {
		return tChildInfoDao.getListImperfectDataChild(map);
	}

	@Override
	public int queryTotalImperfectChild(Map<String, Object> map) {

		return tChildInfoDao.queryTotalImperfectChild(map);
	}

	@Override
	public List<TChildInfoEntity> currentDayInocChild(Map<String, Object> map) {
		return tChildInfoDao.CurrentDayInoculateChild(map);
	}

	@Override
	public List<TChildInfoEntity> getListUnSyncstatusInocChild(Map<String, Object> map) {
		return tChildInfoDao.getListUnSyncstatusInocChild(map);
	}

	@Override
	public int queryTotalCurrentDayInocChild(Map<String, Object> map) {
		return tChildInfoDao.queryTotalCurrentDayInoculateChild(map);
	}

	@Override
	public int queryAllTotal() {
		return tChildInfoDao.queryAllTotal();
	}

	@Override
	public void updateBarCode(String childCode, String barCode) throws Exception{
		if (StringUtils.isEmpty(childCode) || StringUtils.isEmpty(barCode)){
			return;
		}else {
			Map<String,String> pama = new HashMap<>();
			pama.put("childCode",childCode);
			pama.put("barCode",barCode);
			tChildInfoDao.updateBarCode(pama);
		}
	}

	@Override
	public String getCurrentAddress() {
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		String orgId = userEntity.getOrgId();
		String currentAddress = tChildInfoDao.getCurrentAddress(orgId.substring(0, 8));
		if (!StringUtils.isEmpty(currentAddress)){
			return currentAddress;
		}
		return "";
	}

	@Override
	public List<Map<String,Object>> listImperfectInocChild(Map<String, Object> map) {
		return tChildInfoDao.getListImperfectInocChild(map);
	}

	@Override
	public int queryTotalImperfectInocChild(Map<String, Object> map) {
		return tChildInfoDao.queryTotalImperfectInocChild(map);
	}

	@Override
	public List<TChildInfoEntity> currentDayWaitInocChild(Map<String, Object> map) {
		return tChildInfoDao.currentDayWaitInoculateChild(map);
	}

	@Override
	public int queryTotalCurrentDayWaitInocChild(Map<String, Object> map) {
		return 0;
	}
}
