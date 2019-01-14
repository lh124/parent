package io.yfjz.managerservice.rule.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.dao.rule.TRuleIntervalDao;
import io.yfjz.dao.rule.TRuleReplaceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.yfjz.entity.rule.TRuleIntervalEntity;
import io.yfjz.entity.rule.TRuleReplaceEntity;
/**
 * 接种规则缓存系统，以spring组件形式提供
 * @作者：邓召仕
 * 2018年7月27日上午10:42:34
 */
@Component
public class RuleCache {
	@Autowired
	private TRuleReplaceDao tRuleReplaceDao;
	@Autowired
	private TRuleIntervalDao tRuleIntervalDao;
//规划信息替代规则
	private static Map<String,Map<String,List<TRuleReplaceEntity>>> replaceRuleMap = new HashMap<String,Map<String,List<TRuleReplaceEntity>>>();
	//间隔规则
	private static List<TRuleIntervalEntity> intervals = new ArrayList<TRuleIntervalEntity>();
	//接种率，随机儿童缓存
	private static  Map<String,List<String>> inoRandChild = new HashMap<>();

	/**
	 * 添加替代规则到规则缓存
	 * @作者：邓召仕
	 * 2018年7月27日
	 * @param replace
	 */
	public void addReplace(TRuleReplaceEntity replace){
		if(replace != null){
			String conrultId = replace.getPlanConsultId();
			String typeStr = Integer.toString(replace.getReplaceType());
			Map<String,List<TRuleReplaceEntity>> replaceMap = replaceRuleMap.get(conrultId);//获取相应规划剂次总规则
			if(replaceMap != null){
				List<TRuleReplaceEntity> ruleList = replaceMap.get(typeStr);
				if(ruleList != null){
					ruleList.add(replace);
				}else{
					ruleList = new ArrayList<TRuleReplaceEntity>();
					ruleList.add(replace);
					replaceMap.put(typeStr, ruleList);
				}
			}else{
				replaceMap = new HashMap<String,List<TRuleReplaceEntity>>();
				List<TRuleReplaceEntity> ruleList = new ArrayList<TRuleReplaceEntity>();
				ruleList.add(replace);
				replaceMap.put(typeStr, ruleList);
				replaceRuleMap.put(conrultId, replaceMap);
			}
		}
	}
	
	/**
	 * 批量添加接种替代规则到缓存
	 * @作者：邓召仕
	 * 2018年7月27日
	 * @param ruleList
	 */
	public void addAllReplace(List<TRuleReplaceEntity> ruleList){
		if(ruleList != null && !ruleList.isEmpty()){
			for(TRuleReplaceEntity replace : ruleList){
				addReplace(replace);
			}
		}
	}
	
	/**
	 * 根据参照计划表ID，获取该剂次的接种替代规则，包含多种替代方式，分别以list集合体现，
	 * 任何一个list集合成立都代表该剂次以接种，其中，key为“0”的集合为或关系，其他集合为与关系。
	 * @作者：邓召仕
	 * 2018年7月27日
	 * @param consultId
	 * @return
	 */
	public Map<String,List<TRuleReplaceEntity>> getReplaceRuleByConsultId(String consultId){
		return replaceRuleMap.get(consultId);
	}

	/**
	 * 根据规划参照剂次ID查询间隔信息
	 * @作者：邓召仕
	 * 2018年7月30日
	 * @param classId
	 * @return
	 */
	public List<TRuleIntervalEntity> getIntervals(String classId , Integer times) {
		List<TRuleIntervalEntity> resultList =  new ArrayList<TRuleIntervalEntity>();
		if(intervals != null && classId != null){
			for(TRuleIntervalEntity interval : intervals){
				if(classId.equals(interval.getSourceClassId()) && interval.getSourceTimes() == times){
					resultList.add(interval);
				}
			}
		}
		return resultList;
	}
	
	public static Map<String, Map<String, List<TRuleReplaceEntity>>> getReplaceRuleMap() {
		return replaceRuleMap;
	}

	public static void setReplaceRuleMap(Map<String, Map<String, List<TRuleReplaceEntity>>> replaceRuleMap) {
		RuleCache.replaceRuleMap = replaceRuleMap;
	}

	public List<TRuleIntervalEntity> getIntervals() {
		return intervals;
	}

	public void setIntervals(List<TRuleIntervalEntity> intervals) {
		RuleCache.intervals = intervals;
	}
	
	/**
	 * @method_name: 判断规则缓存是否为空
	 * @describe:
	 * @param
	 * @return boolean
	 * @author 邓召仕
	 * @date: 2018-08-06  10:54
	 **/
	public boolean isCacheEmpty(){
		return replaceRuleMap.isEmpty();
	}

	/**
	 * 刷新缓存
	 * @describe:
	 * @param forceFinsh
	 * @return void
	 * @author 邓召仕
	 * @date: 2018-09-17  13:51
	 **/
	public void finshRuleCache(boolean forceFinsh) {
		if (forceFinsh || intervals.isEmpty() || replaceRuleMap.isEmpty()){
			List<TRuleReplaceEntity> ruleReplaceEntities = tRuleReplaceDao.queryList(new HashMap<>());
			addAllReplace(ruleReplaceEntities);
			setIntervals(tRuleIntervalDao.queryList(new HashMap<>()));
		}
	}

	/**
	 * @method_name: 接种率随机儿童缓存
	 * @describe:
	 * @return java.util.Map<java.lang.String , java.util.List < java.lang.String>>
	 * @author 邓召仕
	 * @date: 2018-11-20  18:29
	 **/
	public static Map<String, List<String>> getInoRandChild() {
		return inoRandChild;
	}
}
