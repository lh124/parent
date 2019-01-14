package io.yfjz.service.rule;

import java.util.List;
import java.util.Map;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.ChildInoData;
import io.yfjz.entity.rule.TRuleReplaceEntity;

/**
 * 接种规划映射关系表
 * @作者：邓召仕
 * 上午9:12:55
 */
public interface TRuleReplaceService {
	
	TRuleReplaceEntity queryObject(String id);
	
	List<TRuleReplaceEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TRuleReplaceEntity tRuleReplace);
	
	void update(TRuleReplaceEntity tRuleReplace);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
	/**
	 * 获取这个月龄还不能接种的疫苗ID集
	 * @作者：邓召仕
	 * 2018年7月25日
	 * @param moonAge
	 * @return
	 */
	List<String> canNotInoculateBioId(int moonAge);
	
	/**
	 * 互斥替代疫苗可替代接种信息查询，不存在则返回null
	 * @作者：邓召仕
	 * 2018年7月28日
	 * @param childData 儿童信息
	 * @param replaces 同一规划参照剂次下，所有互斥替代关系
	 * @return 可替代的接种信息，不存在则返回null
	 */
	ChildInoData selectMutexReplaceIno(ChildData childData, List<TRuleReplaceEntity> replaces);
	/**
	 * 联合替代接种信息查询，返回最晚接种的一剂替代接种信息
	 * @作者：邓召仕
	 * 2018年7月29日
	 * @param ChildData 儿童
	 * @param replaces 同一规划参照剂次下，所有联合替代关系
	 * @return 最晚接种的一剂替代接种信息，不存在则返回null
	 */
	ChildInoData selectJiontlyReplaceIno(ChildData childData,List<TRuleReplaceEntity> replaces);

	/**
	 * @method_name: 根据规划疫苗ID、剂次、儿童月龄，查询可替代疫苗ID（code）
	 * @describe:
	 * @param classId 规划自字典ID
	 * @param injectionTimes, 接种剂次
	 * @param monthAge 儿童月龄
	 * @return java.util.List<java.lang.String>
	 * @author 邓召仕
	 * @date: 2018-08-15  11:50
	 **/
	List<String> selectReplaceCode(String classId, Integer injectionTimes, Integer monthAge);

	/**
	 * @method_name: 查询可替代该疫苗剂次所以疫苗ID，不区分月龄
	 * @describe:
	 * @param classId
	 * @param injectionTimes
	 * @return java.util.List<java.lang.String>
	 * @author 邓召仕
	 * @date: 2018-11-07  17:39
	 **/
	List<String> selectReplaceCodeNoAge(String classId, Integer injectionTimes);
}
