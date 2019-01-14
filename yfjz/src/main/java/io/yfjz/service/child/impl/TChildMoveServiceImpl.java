package io.yfjz.service.child.impl;

import io.yfjz.dao.child.TChildInfoDao;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.yfjz.dao.child.TChildMoveDao;
import io.yfjz.entity.child.TChildMoveEntity;
import io.yfjz.service.child.TChildMoveService;



/**
 * 儿童迁移记录表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 15:56:11
 */
@Service("tChildMoveService")
public class TChildMoveServiceImpl implements TChildMoveService {
	@Autowired
	private TChildMoveDao tChildMoveDao;
	@Autowired
	private TChildInfoDao tChildInfoDao;
	
	@Override
	public TChildMoveEntity queryObject(String id){
		return tChildMoveDao.queryObject(id);
	}
	
	@Override
	public List<TChildMoveEntity> queryList(Map<String, Object> map){
		return tChildMoveDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tChildMoveDao.queryTotal(map);
	}
	
	@Override
	public void save(TChildMoveEntity tChildMove){
		TChildInfoEntity tChildInfoEntity = new TChildInfoEntity();
		String preHere = tChildMove.getChhePrehere();
		switch (preHere){
			case "迁出":tChildMove.setChhePrehere("2");break;
			case "本地":tChildMove.setChhePrehere("1");break;
			case "临时外转":tChildMove.setChhePrehere("3");break;
			case "死亡":tChildMove.setChhePrehere("4");break;
			case "删除":tChildMove.setChhePrehere("5");break;
			case "异地接种":tChildMove.setChhePrehere("8");break;
			case "临时接种":tChildMove.setChhePrehere("9");break;
			case "外地转来":tChildMove.setChhePrehere("10");break;
			case "入学":tChildMove.setChhePrehere("6");break;
			case "入托":tChildMove.setChhePrehere("7");break;
		}
		tChildMove.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		tChildMove.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
		tChildMove.setOrgId(Constant.GLOBAL_ORG_ID);
		tChildMove.setChheDepaId(Constant.GLOBAL_ORG_ID);
		tChildMoveDao.save(tChildMove);
		tChildInfoEntity.setChilLeavedate(new Date());
		tChildInfoEntity.setChilHere(tChildMove.getChheHere());
		tChildInfoEntity.setChilCode(tChildMove.getChilCode());
		tChildInfoEntity.setChilLeaveremark(tChildMove.getChheChilProvinceremark());
		if(tChildMove.getChheHere().equals("1")){
			tChildInfoEntity.setChilCurdepartment(tChildMove.getChheDepaId());
		}
		tChildInfoDao.update(tChildInfoEntity);
	}
	
	@Override
	public void update(TChildMoveEntity tChildMove){
		tChildMoveDao.update(tChildMove);
	}
	
	@Override
	public void delete(String id){
		tChildMoveDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tChildMoveDao.deleteBatch(ids);
	}
	
}
