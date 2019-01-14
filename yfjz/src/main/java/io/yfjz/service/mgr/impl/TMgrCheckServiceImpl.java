package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.TMgrCheckDao;
import io.yfjz.dao.mgr.TMgrCheckItemDao;
import io.yfjz.dao.mgr.TMgrStockInfoDao;
import io.yfjz.entity.mgr.TMgrCheckEntity;
import io.yfjz.entity.mgr.TMgrCheckItemEntity;
import io.yfjz.entity.mgr.TMgrStockInfoEntity;
import io.yfjz.service.mgr.TMgrCheckService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.RRException;
import io.yfjz.utils.ShiroUtils;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
* @Description: 盘点表 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/7 16:25
* @Tel  17328595627
*/ 
@Service("tMgrCheckService")
public class TMgrCheckServiceImpl implements TMgrCheckService {
	@Autowired
	private TMgrCheckDao tMgrCheckDao;
	@Autowired
	private TMgrCheckItemDao itemDao;
	@Autowired
	private TMgrStockInfoDao infoDao;
	
	@Override
	public TMgrCheckEntity queryObject(String id){
		return tMgrCheckDao.queryObject(id);
	}
	
	@Override
	public List<TMgrCheckEntity> queryList(Map<String, Object> map){
		return tMgrCheckDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tMgrCheckDao.queryTotal(map);
	}
	
	@Override
	public void save(TMgrCheckEntity tMgrCheck){
		tMgrCheckDao.save(tMgrCheck);
	}
	
	@Override
	public void update(TMgrCheckEntity tMgrCheck){
		tMgrCheckDao.update(tMgrCheck);
	}
	
	@Override
	public void delete(String id){
		tMgrCheckDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tMgrCheckDao.deleteBatch(ids);
	}

	@Override
	public void saveResult(Map param) throws ParseException {
		//盘点单号
		Object order = param.get("order");
		//盘点人
		Object checkUser = param.get("checkUser");
		//盘点时间
		Object checkTime = param.get("checkTime");
		//盘点仓库
		Object storeId = param.get("storeId");
		//盘点数据
		List<Map<String,Object>> rows = (List<Map<String, Object>>) param.get("rows");
		if(StringUtils.isEmpty(order)||StringUtils.isEmpty(checkUser)||checkUser==null||StringUtils.isEmpty(storeId)||rows==null){
			throw new RRException("请填写相关数据");
		}
		//检查是否全部归还疫苗
		List<Map<String,Object>> stock=tMgrCheckDao.queryTowerStock();
		if (stock.size()>0) {
			throw new RRException("接种台有疫苗未归还！请先归还疫苗再进行盘点操作!");
		}
		//插入汇总记录
		TMgrCheckEntity check = new TMgrCheckEntity();
		check.setCheckCode(order.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		check.setCheckTime(sdf.parse(checkTime.toString()));
		check.setCreateTime(new Date());
		check.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		check.setFkCheckUserId(checkUser.toString());
		check.setFkStoreId(storeId.toString());
		String uuid = UUID.randomUUID().toString();
		check.setId(uuid);
		tMgrCheckDao.save(check);
		//插入明细记录
		if (rows.size()>0){
			for (Map<String, Object> row : rows) {
				TMgrCheckItemEntity item = new TMgrCheckItemEntity();
				String stockInfoId=null;
				Long realNumber=null;
				//库存基础信息ID
				if (row.get("id")!=null){
					stockInfoId=row.get("id").toString();
				 	item.setFkStockInfoId(stockInfoId);
				}
				//实际数量
				if (row.get("realNumber")!=null) {
					realNumber = Long.valueOf(row.get("realNumber").toString());
					item.setRealNumber(realNumber);
				}
				//系统数量
				if (row.get("amount")!=null){
					item.setSysNumber(Long.valueOf(row.get("amount").toString()));
				}
				//差异数量
				if (row.get("diffNumber")!=null){
					//差异状态
					Long diff=Long.valueOf(row.get("diffNumber").toString());
					if(diff>0){
						item.setCheckStatus(TMgrCheckItemEntity.STATUS_UP);
					}else if(diff<0){
						item.setCheckStatus(TMgrCheckItemEntity.STATUS_DOWN);
					}else{
						item.setCheckStatus(TMgrCheckItemEntity.STATUS_NORMAL);
					}
					//差异数量
					item.setDiffNumber(diff);
				}
				//差异原因
				if (row.get("cause")!=null){
					item.setCause(row.get("cause").toString());
				}
				//处理方法
				if (row.get("handle")!=null){
					item.setHandle(row.get("handle").toString());
				}
				//创建时间
				item.setCreateTime(new Date());
				//汇总记录ID
				item.setFkCheckTotalId(uuid);
				itemDao.save(item);
				/**
				 * 根据库存信息ID，更新库存商品数量,人份总数
				 */
				if(stockInfoId!=null &&realNumber!=null){
					TMgrStockInfoEntity infoEntity = infoDao.queryObject(stockInfoId);

					//库存数量
					infoEntity.setAmount(realNumber);
					if(infoEntity.getConversion()!=null&&infoEntity.getConversion()!=0){
						Integer conversion = infoEntity.getConversion();
						//更新人份总数
						infoEntity.setPersonAmount(realNumber*conversion);
					}
					infoDao.update(infoEntity);
				}
			}
		}

	}
}
