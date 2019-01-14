package io.yfjz.service.child;

import io.yfjz.entity.basesetting.TVacInfoEntity;
import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.TChildInoculateEntity;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 儿童接种记录表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 14:02:29
 */
public interface TChildInoculateService {

	TChildInoculateEntity queryObject(String id);

	TChildInoculateEntity queryLastInoObject(String id);

	List<TChildInoculateEntity> queryList(Map<String, Object> map);

	List<TChildInoculateEntity> querydoses(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	R batchInsertAccountInfo(TChildInoculateEntity tChildInoculate);

	R batchInsertAccountInfos(TChildInoculateEntity tChildInoculate);

	List<TChildInoculateEntity> queryListdoses(Map<String, Object> map);

	void save(TChildInoculateEntity tChildInoculate);

	void update(TChildInoculateEntity tChildInoculate);

	void delete(String id);

	void deleteBatch(String[] ids);

	R finished(List<Map> mapList);

	List<InoculateIntegrityRateEntity> listInocIntegrityRate(Map<String, Object> map);

	int queryAllTotal();

	List<Map<String,Object>> getchoolAdmission(String chilCode);

	/**
	 * 今日留观查询
	 * @param notext
	 * @return
	 */
	List<Map<String,Object>> queryobservation(String notext);
	/**
	 * 历史留观查询
	 * @param chilCode
	 * @return
	 */
	List<Map<String,Object>> queryhistoryobservation(String chilCode);

	/**
	 * 获取处方单
	 * @param chilCode
	 * @return
	 */
	List<Map<String,Object>> getPrescription(String chilCode);

	List<Map<String,Object>> getCurrentRegisterVacc(String chilCode);

	List<Map<String,Object>> getCurrentInoculateVacc(String chilCode);

	int saveAsBackUp(TChildInoculateEntity tChildInoculate);

	List<Map<String,Object>> queryUploadRecord(Map<String, Object> map);

	int queryTotalUploadRecord(Map<String, Object> map);

	/**
	 * 今日已接种总人数
	 * @return
	 */
	List<TChildInoculateEntity> suminoculateall();

	/**
	 * //今日已接种总数/带留观完成数
	 * @return
	 */
	List<TChildInoculateEntity> inoculatelists(String orgid);

	/**
	 * 留观完成总数
	 * @return
	 */
	List<TChildInoculateEntity> observelist();

	List<TChildInoculateEntity> querylistjzbl(Map<String,Object> map);

	/**
	 *未留观完成总数
	 * @return
	 */
	List<TChildInoculateEntity> noobservelist();

	int observeupdate(TChildInoculateEntity tChildInoculateEntity);
	//获取常规疫苗
	List<TVacInfoEntity> outsideinoculatebio();

	List<TVacInfoEntity> getBioClassIdType(String bioClassId);

	/**
	 *  判断 同一接种部位同一天不能重复接种
	 * @param map
	 * @return
	 */
	int queryUpdateInoculateNumByMap(Map<String, Object> map);

	/**
	 * 根据儿童编码 疫苗大类判断同类疫苗的第二剂次时间是否小于第一次剂次时间
	 * @param map
	 * @return
	 */
	List<TChildInoculateEntity> queryUpdateInoculateDate(Map<String,Object> map);

}
