package io.yfjz.service.rule.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.ChildInoData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yfjz.dao.rule.TRuleReplaceDao;
import io.yfjz.dao.rule.TRuleUnionDao;
import io.yfjz.entity.rule.TRuleReplaceEntity;
import io.yfjz.service.rule.TRuleReplaceService;

/**
 * 接种规划映射关系表
 * 
 * @作者：邓召仕 上午9:14:59
 */
@Service("tRuleReplaceService")
public class TRuleReplaceServiceImpl implements TRuleReplaceService {
	private static Logger logger = LoggerFactory.getLogger(TRuleReplaceServiceImpl.class);
	@Autowired
	private TRuleReplaceDao tRuleReplaceDao;
	@Autowired
	private TRuleUnionDao tRuleUnion;

	@Override
	public TRuleReplaceEntity queryObject(String id) {
		return tRuleReplaceDao.queryObject(id);
	}

	@Override
	public List<TRuleReplaceEntity> queryList(Map<String, Object> map) {
		return tRuleReplaceDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return tRuleReplaceDao.queryTotal(map);
	}

	@Override
	public void save(TRuleReplaceEntity tRuleReplace) {
		tRuleReplaceDao.save(tRuleReplace);
	}

	@Override
	public void update(TRuleReplaceEntity tRuleReplace) {
		tRuleReplaceDao.update(tRuleReplace);
	}

	@Override
	public void delete(String id) {
		tRuleReplaceDao.delete(id);
	}

	@Override
	public void deleteBatch(String[] ids) {
		tRuleReplaceDao.deleteBatch(ids);
	}

	@Override
	public List<String> canNotInoculateBioId(int moonAge) {
		logger.info("根据月龄获取没有达到接种时间的疫苗ID集合");
		return tRuleReplaceDao.selectNotInoculateBioId(moonAge);
	}

	@Override
	public ChildInoData selectMutexReplaceIno(ChildData childData, List<TRuleReplaceEntity> replaces) {
		if(childData==null || replaces == null || replaces.isEmpty()) return null;
		TRuleReplaceEntity replace = replaces.get(0);
		//Calendar calendar = Calendar.getInstance();
		//calendar.setTime(childData.getChilBirthday());
		//查询参数定义
		List<String> bios = new ArrayList<String>();
		//Date startTime, endTime;
		int dose = replace.getAgentTimes();
		for(TRuleReplaceEntity rep : replaces){//添加查询疫苗
			bios.add(rep.getBioClassId());
		}
		Map<String,Object> paramerMap =  new HashMap<String,Object>();
		paramerMap.put("childCode", childData.getChilCode());
		/*if (replace.getStartMonth() != null) {//最早接种月龄
			calendar.add(Calendar.MONTH, replace.getStartMonth());
			startTime = calendar.getTime();
			paramerMap.put("startTime", startTime);
		}
		if (replace.getEndMonth() != null) {//最晚接种月龄
			calendar.clear();
			calendar.setTime(childData.getChilBirthday());
			calendar.add(Calendar.MONTH, replace.getEndMonth());
			endTime = calendar.getTime();
			paramerMap.put("endTime", endTime);
		}*/
		paramerMap.put("bios", bios);
		paramerMap.put("n", dose - 1);
		return tRuleUnion.queryInoByBioTime(paramerMap);
	}

	@Override
	public ChildInoData selectJiontlyReplaceIno(ChildData childData, List<TRuleReplaceEntity> replaces) {
		ChildInoData childInoData = null;
		List<TRuleReplaceEntity> list = new ArrayList<TRuleReplaceEntity>();
		if(replaces != null){
			for(TRuleReplaceEntity rep : replaces){//遍历所有联合替代针次是否存并选出最后一针
				list.clear();
				list.add(rep);
				ChildInoData jiontlyChildInoData = this.selectMutexReplaceIno(childData, list);
				if(jiontlyChildInoData != null){
					//选出最晚接种的做返回参数
					if(childInoData == null || jiontlyChildInoData.getInocDate().getTime() > childInoData.getInocDate().getTime()){
						childInoData = jiontlyChildInoData;
					}
				}else{
					return null;
				}
			}
		}
		return childInoData;
	}

	@Override
	public List<String> selectReplaceCode(String classId, Integer injectionTimes, Integer monthAge) {
		Map<String,Object> pamarMap =  new HashMap<>();
		pamarMap.put("classId",classId);
		pamarMap.put("times",injectionTimes);
		pamarMap.put("monthAge",monthAge);
		return tRuleReplaceDao.selectReplaceCode(pamarMap);
	}

	@Override
	public List<String> selectReplaceCodeNoAge(String classId, Integer injectionTimes) {
		Map<String,Object> pamarMap =  new HashMap<>();
		pamarMap.put("classId",classId);
		pamarMap.put("times",injectionTimes);
		return tRuleReplaceDao.selectReplaceCode(pamarMap);
	}

}
