package io.yfjz.service.provinceplatform;


import io.yfjz.entity.provinceplatform.Child;
import io.yfjz.entity.provinceplatform.Children;
import io.yfjz.entity.provinceplatform.TChildDownloadEntity;
import io.yfjz.utils.R;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


public interface ProvincePlatformService {

	 Children findOnProvincePlatform(Map<String, Object> params);

	 String downNewbornOnProvincePlatform(Map<String, Object> params,Integer count);
	String uploadNewbornOnProvincePlatform(Map<String, Object> params);

	@Transactional
	R saveToLocal(Children children, String orgid,String state);


	@SuppressWarnings("unchecked")
	@Transactional
	 HashMap<String, Object> saveBaseToLocal(Map<String, Object> childMap, String orgid);


	 Map<String, Object> findBaseOnProvincePlatform(HashMap<String, Object> params);


	@SuppressWarnings("unchecked")
	@Transactional
	 int saveDepartmentToLocal(Map<String, Object> departmentMap);


	List<Map<String, Object>> getUnionVac(String vac_code, String date);


	@SuppressWarnings("unchecked")
	@Transactional
	 int saveProductToLocal(Map<String, Object> corporationMap);


	@SuppressWarnings("unchecked")
	@Transactional
	 int saveBacterinToLocal(Map<String, Object> bacterinMap);

	 Map<String, Object> handleChildMap(Map<String, Object> childMap, String orgid);

	 Child handleNewborn(TChildDownloadEntity downloadEntity, String orgid);

	 String uploadChildInoculations(String departmentCode, String password, String[] childCodes);

	 String uploadRoutineImmuReport(String departmentCode, String password);

	 Map<String, Object> downloadInoculateFromProvinceByTimer(String childCode, String departmentCode);

	 R downloadMigrationChildNo(Map<String, Object> map);

	 void downNowChildFromProvicnce();

	 Child transformerAddress(Child childInfo,String orgId,String state);

	void getStringAddress(Child childInfo, String countyId, String detail, String targetAddress);
	 String getAddress(Map<String, Object> retMap,String dateiled);
	String getTownName(String townName);
	/**
	 * 上传儿童接种记录
	 * @author rsp
	 * @param departmentCode 机构国标码 10位，拿currentdepartid传进来就行
	 * @param password 贵州省免疫规划信息系统密码，目前和机构国标码相同，和上面的一样
	 * @param childCodes 儿童编码
	 * @return 上传是否成功
	 * @throws Exception
	 */
	 String uploadModifyChildInoculations(String departmentCode, String password, String[] childCodes);


	/**
	 * 修改儿童基本信息
	 * @author rsp
	 * @time  2018-06-25
	 * @param childElement
	 * @param mapChild
	 */
	 void modifyChildInfoXml(Element childElement, Map<String,Object> mapChild);

	/**
	 * 删除接种记录
	 * @author rsp
	 * @time  2018-06-25
	 * @param childCode
	 * @param childElement
	 * @param inoculationsElement
	 * @param inoculations
	 */
	 void deleteInoculationsXml(String childCode,Element childElement,Element inoculationsElement,Node inoculations);


	/**
	 * 修改接种记录方法
	 * @author rsp
	 * @time  2018-06-25
	 * @param childCode
	 * @param inoculationsElement
	 * @param childElement
	 * @param inoculations
	 * @param inoculationNodes
	 * @param inoculationsMapList
	 */
	 void modifyInoculationsXml(String childCode,Element inoculationsElement,Element childElement,Node inoculations,List<Node> inoculationNodes,List<Map<String,Object>> inoculationsMapList);


	/**
	 * 上传未上传过的儿童信息
	 * @author rsp
	 * @time  2018-06-25
	 * @param departmentCode
	 * @param password
	 * @param childCode
	 * @return
	 * @throws Exception
	 */
	 String uploadChildInoculationsNotOnplat(String departmentCode, String password, String childCode);



}