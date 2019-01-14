package io.yfjz.service.provinceplatform.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import io.yfjz.dao.child.*;
import io.yfjz.dao.provinceplatform.ProvincePlatformDao;
import io.yfjz.dao.provinceplatform.TChildDownloadDao;
import io.yfjz.entity.child.TChildMoveEntity;
import io.yfjz.entity.provinceplatform.*;
import io.yfjz.managerservice.rule.InitPlanService;
import io.yfjz.service.provinceplatform.ProvincePlatformService;

import io.yfjz.service.jwxplat.ServiceChild;
import io.yfjz.service.jwxplat.ServiceChildServiceLocator;
import io.yfjz.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 贵州省免疫规划信息系统对接的Service
 * @author 研发部 饶士培
 * @date 2018年7月24日10:31:48
 */
@Service
public class ProvincePlatformServiceImpl implements ProvincePlatformService {

	private static 	ServiceChildServiceLocator serviceChildServiceLocator = new ServiceChildServiceLocator();
	private static Logger LOGGER= Logger.getLogger(ProvincePlatformServiceImpl.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	ProvincePlatformDao provincePlatformDao;
	@Autowired
	TChildDownloadDao tChildDownloadDao;
	@Autowired
	TChildInfoDao tChildInfoDao;
	@Autowired
	TChildInoculateDao tChildInoculateDao;
	@Autowired
	TChildAllergyDao tChildAllergyDao;
	@Autowired
	TChildAbnormalDao tChildAbnormalDao;
	@Autowired
	TChildTabooDao tChildTabooDao;
	@Autowired
	TChildInfectionDao tChildInfectionDao;
	@Autowired
	TChildMoveDao tChildMoveDao;
	@Autowired
	private InitPlanService initPlanService;
	@Override
	public Children findOnProvincePlatform(Map<String, Object> params)  {
		if (params == null || params.isEmpty()) {
			LOGGER.info("参数不足");
			return null;
		}
		String departmentCode = (String) params.get("departmentCode");	// 接种点国标码
		//String departmentCode ="5201112402";
		if (StringUtils.isEmpty(departmentCode) && departmentCode.matches("\\w{10}")) {

		}
		String password = departmentCode;								// 密码
		String childCode = (String) params.get("childCode");			// 儿童编码
		String childName = (String) params.get("childName");			// 儿童姓名
		String sex = (String) params.get("sex");						// 性别
		String motherName = (String) params.get("motherName");			// 母亲姓名
		String fatherName = (String) params.get("fatherName");			// 母亲姓名
		String birthday = (String) params.get("birthday");				// 出生日期
		String birthNo = (String) params.get("birthNo");				// 出生证号
		String childId = (String) params.get("childId");				// 儿童身份证
		String regCountyCode = (String) params.get("regCountyCode");	// 建档县国标码
		String state =(String)params.get("state");
		// 当儿童编码不为空时，调用downloadVaccByChildNo接口按儿童编码来查询儿童信息
		Boolean shouldCallDownloadVaccByChildNo = StringUtils.isNotEmpty(childCode) && childCode.matches("\\d{18}");
		// 如果params中没有儿童编码但是有儿童出生证号，调用downloadVaccByBirthNo接口按儿童出生证来查询儿童信息
		Boolean shouldCallDownloadVaccByBirthNo = !shouldCallDownloadVaccByChildNo && (StringUtils.isNotEmpty(birthNo) && birthNo.matches("\\w{10}"));
		// 如果params中没有儿童编码也没有儿童出生证号，但是有儿童身份证号，调用downloadVaccByChildID接口按儿童身份证号来查询儿童信息
		Boolean shouldCallDownloadVaccByChildID = !shouldCallDownloadVaccByBirthNo && (StringUtils.isNotEmpty(childId) && childId.matches("\\w{18}"));
		// 如果以上条件都查询不到，调用downloadVaccByName接口来按儿童名称来查询儿童信息，但是该接口的条件如下
		// 儿童姓名和母亲姓名必选其一，性别和出生日期必须同时存在
		Boolean nameCondition1 = StringUtils.isEmpty(childName) && StringUtils.isNotEmpty(motherName);
		Boolean nameCondition2 = StringUtils.isNotEmpty(childName) && StringUtils.isEmpty(motherName);
		Boolean nameCondition3 = StringUtils.isNotEmpty(childName) && StringUtils.isNotEmpty(motherName);
		Boolean nameCondition4 = StringUtils.isNotEmpty(fatherName);
		Boolean nameCondition = nameCondition1 || nameCondition2 || nameCondition3;
		Boolean nameConditions = nameCondition1 || nameCondition2 || nameCondition3 || nameCondition4;//添加父亲姓名查询
		Boolean sexRequired = StringUtils.isNotEmpty(sex);
		Boolean birthdayRequired = StringUtils.isNotEmpty(birthday);
		Boolean sexAndBirthdayMustExists = sexRequired && birthdayRequired;
		//Boolean shouldCallDownloadVaccByNameAndMother = !shouldCallDownloadVaccByChildID && (nameCondition && nameCondition3 || sexAndBirthdayMustExists);
		Boolean shouldCallDownloadVaccByName = !shouldCallDownloadVaccByChildID && (nameCondition || sexAndBirthdayMustExists);
		Boolean shouldCallDownloadVaccByFarName = !shouldCallDownloadVaccByChildID && (nameConditions && nameCondition4 || sexAndBirthdayMustExists);
		byte[] rawXmlBytes = null;
		Children children = null;
		Map<String,Object> childrenMap = null;
		XStream xStream = new XStream();
		try{
			ServiceChild serviceChild =  serviceChildServiceLocator.getChildService();
			if (shouldCallDownloadVaccByChildNo && state != null) {
				LOGGER.info("按儿童编码查询...");
				rawXmlBytes = serviceChild.downloadVaccByChildNo(departmentCode, password, childCode, state);
			}else if(shouldCallDownloadVaccByChildNo && state==null){
				LOGGER.info("按儿童编码查询...");
				rawXmlBytes = serviceChild.downloadVaccByChildNo(departmentCode, password, childCode, "0");
			}else if (shouldCallDownloadVaccByBirthNo) {
				LOGGER.info("按出生证号查询...");
				rawXmlBytes = serviceChild.downloadVaccByBirthNo(departmentCode, password, birthNo);
			} else if (shouldCallDownloadVaccByChildID) {
				LOGGER.info("按儿童身份证查询...");
				rawXmlBytes = serviceChild.downloadVaccByChildID(departmentCode, password, childId);
			} else if (shouldCallDownloadVaccByFarName) {
				LOGGER.info("按儿童父亲姓名等查询...");
				rawXmlBytes = serviceChild.downloadVaccByFarName(departmentCode, password, childName, birthday, sex, fatherName, regCountyCode);
			}else if (shouldCallDownloadVaccByName) {
				LOGGER.info("按儿童姓名等查询...");
				rawXmlBytes = serviceChild.downloadVaccByName(departmentCode, password, childName, birthday, sex, motherName, regCountyCode);
			}else {
				LOGGER.info("缺少必要的参数，方法直接返回null...");
				return null;
			}
			if (rawXmlBytes == null) {
				LOGGER.info("省免疫规划信息系统接口返回null，方法直接退出，返回值返回null...");
				return null;
			}

			if(rawXmlBytes.length>1) {
				XStream.setupDefaultSecurity(xStream);
				xStream.allowTypes(new Class[]{Children.class,Child.class, Inoculation.class, ChildHere.class,
						Infection.class, Aefi.class, Istabu.class});
				xStream.ignoreUnknownElements();
				DateConverter converter = new CustomDateConverter("yyyy-MM-dd HH:mm:ss",new String[]{"yyyy-MM-dd HH:mm:ss"},
						TimeZone.getDefault());
				String xml = new String(XmlUtils.unzipBytes(rawXmlBytes),"utf-8");
				//System.out.print(xml);
				//@XStreamConverter(value=FormatableDoubleConverter.class, strings={"###,##0.0########"})
				xStream.registerConverter(converter);
				/*xStream.registerConverter(converter1);*/
				xStream.processAnnotations(Children.class);
				 children = (Children)xStream.fromXML(
						 new ByteArrayInputStream(XmlUtils.unzipBytes(rawXmlBytes)));

			}else{
				String result = new String(rawXmlBytes);
				LOGGER.info("平台返回"+result);
				return children;
			}
		}catch (Exception e){
			e.printStackTrace();
			return children;
		}
		return children;
	}

	@Override
	public R saveToLocal(Children children, String orgid,String state) {
		// 检查参数
		boolean childMapExists = children != null;
		if (!childMapExists) {
			LOGGER.info("-------- childMap参数不合法...方法返回null --------");
			return R.error().put("msg","查不到该儿童");
		}
		if (StringUtils.isEmpty(orgid)) {
			LOGGER.info("-------- orgid为空...方法返回null --------");
			return R.error();
		}
		int rows = 0;
		Child transformerAddressChild = null;
		//Child child = children.getChild();
		List<Child> childs = children.getChilds();
		// 先拿出儿童编码，复制表里面保存靠这个关联
		for(int i = 0; i < childs.size();i++){
		String childCode = childs.get(i).getChilCode();

		// 儿童基本信息比较多，转换一下需要的参数map
		transformerAddressChild = transformerAddress(childs.get(i),orgid,state);
		//transformerAddressChild = handleChildMap(transformerAddressChild, orgid);
		Child provincePlatformChildDto = null;
		//int Integer
		try{
			LOGGER.info("————根据平台查询到的儿童id查询数据库中是否有该儿童id，没有则保存");
			provincePlatformChildDto = provincePlatformDao.queryObject(childCode);
			LOGGER.info("————查询结果："+provincePlatformChildDto);
		}catch(Exception ex){
			LOGGER.info("查询出现异常:"+ex);
		}
		if(provincePlatformChildDto==null){
			// 添加t_sys_child表记录
			if("".equals(transformerAddressChild.getChilLeavedate().trim()))
	       {
				transformerAddressChild.setChilLeavedate(null);
			}

			provincePlatformDao.saveChild(transformerAddressChild);
			if(transformerAddressChild.getChilSensitivity()!=null && transformerAddressChild.getChilSensitivity().toString().trim().length()>0){
				provincePlatformDao.saveAllergy(transformerAddressChild);
			}

		}else {
			rows = provincePlatformDao.updateChild(transformerAddressChild);
			if(transformerAddressChild.getChilSensitivity()!=null && transformerAddressChild.getChilSensitivity().toString().trim().length()>0){
				int rowsallergy = provincePlatformDao.findAllergyByCondition(transformerAddressChild);
				if(rowsallergy<=0){
					rowsallergy = provincePlatformDao.saveAllergy(transformerAddressChild);
				}
			}

		}
		// 保存接种记录
		List<Inoculation> inoculationsObj = childs.get(i).getInoculationList();
		for(Inoculation ino :inoculationsObj){
			ino.setChildCode(childCode);
			String inocCounty = ino.getInocCounty();
			String inocNationcode = ino.getInocNationcode();
			if(ino.getInocDepaCode()==null || ino.getInocDepaCode().trim().length()<=0){
				ino.setInocDepaCode(inocCounty+inocNationcode);
			}
			rows = provincePlatformDao.findInoculateByCondition(ino);//返回单条记录
			LOGGER.info("查询结果rows="+rows);
			if(rows<=0){
				rows = provincePlatformDao.saveLocalInoculations(ino);
				if (rows <= 0) {
					LOGGER.info("新增接种记录"+ino+"失败");
				}else{
					LOGGER.info("新增接种记录"+ino+"成功");
				}
			}else{
				rows = provincePlatformDao.updateLocalInoculations(ino);
				if(rows > 0){
					LOGGER.info("更新接种记录成功");
				}
			}
		}

		// 保存在册信息
		List<ChildHere> childheresObj = childs.get(i).getChildHeresList();
		for(ChildHere here :childheresObj){
			here.setChildCode(childCode);
			rows = provincePlatformDao.findMoveByCondition(here);//返回单条记录
			LOGGER.info("查询结果rows="+rows);
			if(rows<=0){
				rows = provincePlatformDao.saveMove(here);
				if (rows <= 0) {
					LOGGER.info("新增在册信息记录"+here+"失败");
				}else{
					LOGGER.info("新增在册信息记录"+here+"成功");
				}
			}
		}
		// 保存传染病
		List<Infection> infectionsObj = childs.get(i).getInfectionList();
		for(Infection infection:infectionsObj){
			infection.setChildCode(childCode);
			rows = provincePlatformDao.findInfectionByCondition(infection);
			if(rows<=0){
				rows = provincePlatformDao.saveInfection(infection);
				if (rows <= 0) {
					LOGGER.info("新增传染病记录"+infection+"失败");
				}else{
					LOGGER.info("新增传染病记录"+infection+"成功");
				}
			}

		}

		// 保存异常反应
		List<Aefi> aefisObj = childs.get(i).getAefiList();
		for(Aefi aefi:aefisObj){
			aefi.setChildCode(childCode);
			rows = provincePlatformDao.findAbnormalByCondition(aefi);
			if(rows<=0){
				rows = provincePlatformDao.saveAbnormal(aefi);
				if (rows <= 0) {
					LOGGER.info("新增异常反应记录"+aefi+"失败");
				}else{
					LOGGER.info("新增异常反应记录"+aefi+"成功");
				}
			}

		}
		// 保存禁忌
		List<Istabu> istabusObj = childs.get(i).getIstabuList();
		for(Istabu istabu:istabusObj){
			istabu.setChilCode(childCode);
			rows = provincePlatformDao.findTabooByCondition(istabu);
			if(rows<=0){
				rows = provincePlatformDao.saveTaboo(istabu);
				if (rows <= 0) {
					LOGGER.info("新增禁忌记录"+istabu+"失败");
				}else{
					LOGGER.info("新增禁忌记录"+istabu+"成功");
				}
			}
		}
		try{
			initPlanService.initChildPlan(childCode);
		}catch (Exception e){
			e.printStackTrace();
		}
		}

		return R.ok();
	}

	@Override
	public String  downNewbornOnProvincePlatform(Map<String, Object> params,Integer count){
		if (params == null || params.isEmpty()) {
			LOGGER.info("参数params为空: 方法已提前退出...");
			return null;
		}
		String departmentCode = (String) params.get("departmentCode");    // 接种点国标码
		if (StringUtils.isEmpty(departmentCode) && departmentCode.matches("\\w{10}")) {
			LOGGER.info("params中没有departmentCode属性，或者departmentCode长度不够10位， 方法已提前退出...");
			return null;
		}
		String password = departmentCode;                                // 密码
		byte[] rawXmlBytes = null;
		String results = null;
		NewBorns childrenDownLoad = null;
		Integer flag = count;
		XStream xStream = new XStream();
		try {
			ServiceChild serviceChild = serviceChildServiceLocator.getChildService();
			LOGGER.info("按部门编码查询新生儿...");
			rawXmlBytes = serviceChild.downloadNewBornInoculateBatchGz(departmentCode, password);
			if (rawXmlBytes.length > 1) {
				XStream.setupDefaultSecurity(xStream);
				xStream.allowTypes(new Class[]{NewBorns.class,TChildDownloadEntity.class,NewbornInoculate.class});
				xStream.ignoreUnknownElements();
				DateConverter converter = new CustomDateConverter("yyyy-MM-dd",new String[]{"yyyy-MM-dd"},
						TimeZone.getDefault());
				String xml = new String(XmlUtils.unzipBytes(rawXmlBytes), "utf-8");
				//System.out.print(xml);
				xStream.registerConverter(converter);
				xStream.processAnnotations(NewBorns.class);
				childrenDownLoad = (NewBorns) xStream.fromXML(
						new ByteArrayInputStream(XmlUtils.unzipBytes(rawXmlBytes)));
				if (childrenDownLoad != null) {
					//保存新生儿
					LOGGER.info("保存新生儿...");
					TChildDownloadEntity newborn = childrenDownLoad.gettChildDownloadEntity();
					String newborn_id = newborn.getNeboId();
					Child child = provincePlatformDao.queryObject(newborn.getNeboCode());
					TChildDownloadEntity query = tChildDownloadDao.queryObject(newborn.getNeboCode());
					if(child == null){
						child = handleNewborn(newborn,departmentCode);
						Child transformerAddressChild = transformerAddress(child,departmentCode,"0");
						provincePlatformDao.saveChild(transformerAddressChild);
					}
					if(query==null){
						tChildDownloadDao.save(newborn);
					}
					// 保存接种记录
					List<NewbornInoculate> inoculationsObj = newborn.getInoculationList();
					for(NewbornInoculate ino :inoculationsObj){
						ino.setChildCode(newborn.getNeboCode());
						ino.setInocProperty("0");
						int rows = provincePlatformDao.findInoculateByCondition(ino);//返回单条记录
						LOGGER.info("查询结果rows="+rows);
						if(rows<=0){
							rows = provincePlatformDao.saveLocalInoculations(ino);
							if (rows <= 0) {
								LOGGER.info("新增接种记录"+ino+"失败");
							}else{
								LOGGER.info("新增接种记录"+ino+"成功");
							}
						}else{
							rows = provincePlatformDao.updateLocalInoculations(ino);
							if(rows > 0){
								LOGGER.info("更新接种记录成功");
							}
						}
					}
					params.put("newborn_id",newborn_id);
					//results =  "1";
					results =  uploadNewbornOnProvincePlatform(params);

				}else{
					LOGGER.info("新生儿转换失败");
					return null;
				}
			} else {
				String result = new String(rawXmlBytes);
				LOGGER.info("平台返回" + result);
				return result;
			}
			//resultMap = XmlUtils.xmlBytesToMap(rawXmlBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
		return results;
	}

	@Override
	public String uploadNewbornOnProvincePlatform(Map<String, Object> params) {

		if (params == null || params.isEmpty()) {
			LOGGER.info("参数params为空: 方法已提前退出...");
			return null;
		}
		String departmentCode = (String) params.get("departmentCode");	// 接种点国标码
		if (StringUtils.isEmpty(departmentCode) && departmentCode.matches("\\w{10}")) {
			LOGGER.info("params中没有departmentCode属性，或者departmentCode长度不够10位， 方法已提前退出...");
		}
		String password = departmentCode;								// 密码
		String newborn_id = (String)params.get("newborn_id");
		String results = null;
		try {
			ServiceChild serviceChild = serviceChildServiceLocator.getChildService();
			results = serviceChild.uploadNewbornBackCommunalGz(departmentCode, password, newborn_id, "", "");
		}catch (Exception e){
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public HashMap<String, Object> saveBaseToLocal(Map<String, Object> childMap, String orgid) {
		return null;
	}

	@Override
	public Map<String, Object> findBaseOnProvincePlatform(HashMap<String, Object> params) {
		return null;
	}

	@Override
	public int saveDepartmentToLocal(Map<String, Object> departmentMap) {
		return 0;
	}

	@Override
	public List<Map<String, Object>> getUnionVac(String vac_code, String date) {
		return null;
	}

	@Override
	public int saveProductToLocal(Map<String, Object> corporationMap) {
		return 0;
	}

	@Override
	public int saveBacterinToLocal(Map<String, Object> bacterinMap) {
		return 0;
	}

	@Override
	public Map<String, Object> handleChildMap(Map<String, Object> childMap, String orgid) {
		return null;
	}


	@Override
	public String uploadChildInoculations(String departmentCode, String password, String[] childCodes) {
		String result = null;
		Map<String,Object> queryMap = new HashMap<>();
		// 先下载儿童原始数据，如果没有查询到对应的儿童，返回false，否则查询本地数据库中的异地儿童接种记录
		ServiceChild serviceChild = null;
		try{
			 serviceChild = serviceChildServiceLocator.getChildService();
		}catch (Exception e){
			e.printStackTrace();
		}

		for(int i = 0; i < childCodes.length;i++) {
			String childCode = childCodes[i];
			queryMap.put("chilCode",childCode);
			byte[] results = null;
			try{
				results = serviceChild.downloadVaccByChildNo(departmentCode, password, childCode, "0");
			}catch (Exception e){
				e.printStackTrace();
			}
			//byte[] results = CountryPlatformWebservice.downloadVaccByChildNo("5224240306", "5224240306", "522424030620159999", "0");
			//String childCode1 = "520111030100000002";
			if(results.length<=1) {
				result =  uploadChildInoculationsNotOnplat( departmentCode,  password,  childCode);
			} else {
				List<Map<String, Object>> childList = tChildInfoDao.findChildInfoForUpload(childCode);
				List<Map<String, Object>> alleryMapList = tChildAllergyDao.findAlleryForUpload(childCode);
				if (alleryMapList == null || alleryMapList.isEmpty()) {
					//LOGGER.info("本地数据库没有查询该儿童传染病未上传记录");
				}
				String allery = "";
				for (Map<String, Object> alleryMap : alleryMapList) {
					allery = allery.concat(alleryMap.get("describes").toString());

				}

				// 用下载的儿童字节数组生成String，然后用String生成xml的Document，再将Document转换成Map，利用这个Map生成一个Document的拷贝
				try{
					//String xml = new String(XmlUtils.unzipBytes(rawXmlBytes),"utf-8");
					String rawXmlString = new String(XmlUtils.unzipBytes(results), "UTF-8");
					LOGGER.info("下载的结果: " + XmlUtils.formatXml(rawXmlString));
					Document document = XmlUtils.stringToXmlDocument(rawXmlString);
					Map<String, Object> map = XmlUtils.xmlToMap(document);
					// 原始xml的拷贝，用作上传
					Document newDoc = XmlUtils.map2xml(map);

					// 查询拷贝文档的child节点
					List<Node> childNodes = newDoc.selectNodes("/children/child");
					// 按儿童编码查询的数据一般只有一个child节点，获取第一个就行了
					Node childNodeFirst = childNodes.get(0);
					// 强转成Element类型，接口更多
					Element childElement = (Element) childNodeFirst;
					// 上传接口必须在child标签下添加该子标签，代表目前状态是更改，否则上传无效，该标签可进行 ‘增’，‘删’ ‘改’
					childElement.addElement("chil_status").addText("C");
					//childElement.element()
					// 获得接种记录的外层标签inoculations
					List<Node> inoculationsNodes = newDoc.selectNodes("/children/child/inoculations");

					// 获得第一个inoculations并强转成Element
					Node inoculations = inoculationsNodes.get(0);
					Element inoculationsElement = (Element) inoculations;

					// 循环本地数据库中查询的结果，并添加到xml下
					for (Map<String, Object> mapChild : childList) {
						mapChild.put("chil_tel",mapChild.get("chilTel"));
						mapChild.put("chil_mobile",mapChild.get("chilMobile"));
						mapChild.put("chil_sensitivity",allery);
						mapChild.put("chil_fatherno",mapChild.get("chilFatherno"));
						mapChild.put("chil_motherno",mapChild.get("chilMotherno"));
						mapChild.put("chil_no",mapChild.get("chilNo"));

						if(mapChild.get("chil_birthhospital").equals("0000000000")){
							mapChild.put("chil_birthhospital","");
						}

						/*if(!departmentCode.equals(childList.get(0).get("chil_curdepartment").toString())){
							LOGGER.info("该儿童是异地临时接种,只传接种记录");
						}else {*/
							modifyChildInfoXml(childElement,mapChild);
						//}
						/*****************************************接种记录**************************************/
						List<Map<String, Object>> inoculationsMapList = tChildInoculateDao.findInoclationsForUpload(childCode);
						for(Map<String,Object> inoculationsMap:inoculationsMapList){
							String obj = inoculationsMap.get("inoc_depa_code").toString();
							boolean flag =obj.matches("[0-9]+");
							if (flag == true) {
								//System.out.println("接种单位："+obj);
							}else{
								inoculationsMap.put("inoc_depa_code","");
							}
						}

						//List<Node> childChildNodes = childElement.selectNodes("inoculation");
						if (inoculationsMapList == null || inoculationsMapList.isEmpty()) {
							//LOGGER.info("本地数据库没有查询到该儿童未上传接种记录: ，方法已退出");
							//return "0";
						}else {
							/*List elementsinoculation = inoculationsElement.elements();
							if(elementsinoculation.size()<=0){*/
								childElement.remove(inoculations);//移除所有接种记录，全量上传本地接种记录
								inoculationsElement = childElement.addElement("inoculations");
							//}
							for (Map<String, Object> inoculationMap : inoculationsMapList) {
								Element inoculationElement = inoculationsElement.addElement("inoculation");
								inoculationElement.addElement("inoc_bact_code").addText((String) inoculationMap.get("inoc_bact_code"));                    // 疫苗编码 4位

								inoculationElement.addElement("inoc_property").addText(inoculationMap.get("inoc_property") == null ? "0" : inoculationMap.get("inoc_property").toString());                    // 接种属性 参见文档 不知道传什么就传0
								inoculationElement.addElement("inoc_reinforce").addText(inoculationMap.get("inoc_reinforce") == null ? "0" : inoculationMap.get("inoc_reinforce").toString());                    // 强化属性 参见文档 不知道传什么就传0
								inoculationElement.addElement("inoc_time").addText(inoculationMap.get("inoc_time").toString());                            // 剂次 数字的字符串
								//inoculationElement.addElement("inoc_dose").addText((String) inoculationMap.get("inoc_dose"));                            // 剂量 可以为空字符串
								inoculationElement.addElement("inoc_opinion").addText((String) inoculationMap.get("inoc_opinion"));                        // 评价 数字的字符串 含义参见文档
								inoculationElement.addElement("inoc_date").addText(inoculationMap.get("inoc_date").toString());                            // 接种日期 格式可以是yyyy-MM-dd 或者后面跟上HH:mm:ss
								inoculationElement.addElement("inoc_union_code").addText((String) inoculationMap.get("inoc_union_code"));                // 联合疫苗编码 可以为空字符串
								inoculationElement.addElement("inoc_batchno").addText((String) inoculationMap.get("inoc_batchno"));                        // 批号	可以为空字符串
								inoculationElement.addElement("inoc_validdate").addText(inoculationMap.get("inoc_validdate").toString());                    // 疫苗有效期 可以为空字符串
								inoculationElement.addElement("inoc_corp_code").addText((String) inoculationMap.get("inoc_corp_code"));                    // 生产企业 参见文档，可以为空之城
								inoculationElement.addElement("inoc_regulatory_code").addText(inoculationMap.get("inoc_regulatory_code")!=null?inoculationMap.get("inoc_regulatory_code").toString():"");        // 疫苗监管码 可以为空字符串
								inoculationElement.addElement("inoc_free").addText((String) inoculationMap.get("inoc_free"));                            // 是否免费疫苗 1或者0的字符串
								inoculationElement.addElement("inoc_pay").addText((String) inoculationMap.get("inoc_pay"));                                // 是否补助疫苗 1或者0的字符串
								inoculationElement.addElement("inoc_modify_code").addText((String) inoculationMap.get("inoc_modify_code"));                                // 是否补助疫苗 1或者0的字符串

								inoculationElement.addElement("inoc_county").addText((String) inoculationMap.get("inoc_county"));                        // 接种县国标6位 参考值522631
								inoculationElement.addElement("inoc_nationcode").addText((String) inoculationMap.get("inoc_nationcode"));                // 接种单位国标后4位 参考值1701 在本系统中将currentdepartid拆分即可得到
								inoculationElement.addElement("inoc_inpl_id").addText(inoculationMap.get("inoc_inpl_id")!=null?String.valueOf(inoculationMap.get("inoc_inpl_id")):"");                        // 接种部位 数字的字符串 含义参见文档
								inoculationElement.addElement("inoc_editdate").addText(inoculationMap.get("inoc_editdate").toString());                   //更新日期
								inoculationElement.addElement("inoc_nurse").addText(inoculationMap.get("inoc_nurse")==null?"":inoculationMap.get("inoc_nurse").toString());                            // 接种护士 人名
								inoculationElement.addElement("inoc_useticket").addText((String) inoculationMap.get("inoc_useticket"));
								inoculationElement.addElement("zxhs").addText((String) inoculationMap.get("zxhs"));                            //
								inoculationElement.addElement("inoc_depa_code").addText((String) inoculationMap.get("inoc_depa_code"));
								inoculationElement.addElement("inoc_depa_id").addText((String) inoculationMap.get("inoc_depa_id"));
								inoculationElement.addElement("inoc_vcn_kind").addText((String) inoculationMap.get("inoc_vcn_kind"));


							}
						}
					/***********************************传染病反应**************************************/

						List<Map<String, Object>> infectionsMapList = tChildInfectionDao.findInfections(childCode);
						if (infectionsMapList == null || infectionsMapList.isEmpty()) {
							LOGGER.info("本地数据库没有查询该儿童传染病未上传记录");
							//return "0";
						}
						// 获得传染病的外层标签infections
						List<Node> infectionsNodes = newDoc.selectNodes("/children/child/infections");
						Node infections = infectionsNodes.get(0);
						Element infectionsElement = (Element) infections;
						List elementsinfection = infectionsElement.elements();
						if(elementsinfection.size()<=0){
							childElement.remove(infections);
							infectionsElement = childElement.addElement("infections");
						}
						for (Map<String, Object> infectionsMap : infectionsMapList) {
							Element infectionElement = infectionsElement.addElement("infection");
							infectionElement.addElement("infe_code").addText(infectionsMap.get("infe_code").toString());
							infectionElement.addElement("infe_date").addText(infectionsMap.get("infe_date").toString());
							//infectionElement.addElement("infe_chil_id").addText((String) infectionsMap.get("infe_chil_id"));

						}

						/***********************************异常反应**************************************/

						List<Map<String, Object>> aefisMapList = tChildAbnormalDao.findAefis(childCode);
						if (aefisMapList == null || aefisMapList.isEmpty()) {
							LOGGER.info("本地数据库没有查询该儿童异常反应未上传记录");
							//return "0";
						}
						// 获得传染病的外层标签infections
						List<Node> aefisNodes = newDoc.selectNodes("/children/child/aefis");
						Node aefis = aefisNodes.get(0);
						Element aefisElement = (Element) aefis;
						List elementsaefi = aefisElement.elements();
						if(elementsaefi.size()<=0){
							childElement.remove(aefis);
							aefisElement = childElement.addElement("aefis");
						}
						for (Map<String, Object> aefisMap : aefisMapList) {
							Element aefiElement = aefisElement.addElement("aefi");

							aefiElement.addElement("aefi_bact_code").addText((String) aefisMap.get("aefi_bact_code"));                    // 疫苗编码 4位
							aefiElement.addElement("aefi_date").addText(aefisMap.get("aefi_date").toString());                                // 反应日期
							aefiElement.addElement("aefi_code").addText((String) aefisMap.get("aefi_code"));//// 反应症状编码
							//aefiElement.addElement("aefi_chil_id").addText((String) aefisMap.get("aefi_chil_id"));
						}

						/***********************************禁忌**************************************/

						List<Map<String, Object>> istabusMapList = tChildTabooDao.findIstabus(childCode);
						if (istabusMapList == null || istabusMapList.isEmpty()) {
							LOGGER.info("本地数据库没有查询该儿童禁忌未上传记录");
							//return "0";
						}
						// 获得传染病的外层标签infections
						List<Node> istabusNodes = newDoc.selectNodes("/children/child/istabus");
						Node istabus = istabusNodes.get(0);
						Element istabusElement = (Element) istabus;
						List elementsistabu = istabusElement.elements();
						if(elementsistabu.size()<=0){
							childElement.remove(istabus);
							istabusElement = childElement.addElement("istabus");

						}
						for (Map<String, Object> istabusMap : istabusMapList) {
							Element istabuElement = istabusElement.addElement("istabu");
							istabuElement.addElement("ista_code").addText((String) istabusMap.get("ista_code"));//禁忌症编码
							//istabuElement.addElement("ista_chil_id").addText((String) istabusMap.get("ista_chil_id"));
						}


						/***********************************在册**************************************/

						List<Map<String, Object>> childheresMapList = tChildMoveDao.findChildheres(childCode);
						if (childheresMapList == null || childheresMapList.isEmpty()) {
							LOGGER.info("本地数据库没有查询该儿童变更未上传记录");
						}
						// 获得传染病的外层标签infections
						List<Node> childheresNodes = newDoc.selectNodes("/children/child/childheres");
						Node childheres = childheresNodes.get(0);
						Element childheresElement = (Element) childheres;
						List elementschildhere = childheresElement.elements();
						if(elementschildhere.size()<=0){
							childElement.remove(childheres);
							childheresElement = childElement.addElement("childheres");

						}
						for (Map<String, Object> childheresMap : childheresMapList) {
							Element childhereElement = childheresElement.addElement("childhere");
							childhereElement.addElement("chhe_here").addText((String) childheresMap.get("chhe_here"));
							childhereElement.addElement("chhe_prehere").addText((String)childheresMap.get("chhe_prehere"));
							//childhereElement.addElement("chhe_chil_province").addText("");
							childhereElement.addElement("chhe_chil_provinceremark").addText(childheresMap.get("chhe_chil_provinceremark")!=null?childheresMap.get("chhe_chil_provinceremark").toString():"");
							childhereElement.addElement("chhe_changedate").addText(childheresMap.get("chhe_changedate").toString());
							childhereElement.addElement("chhe_depa_id").addText((String)childheresMap.get("chhe_depa_id"));
							//childhereElement.addElement("chhe_chil_id").addText((String)childheresMap.get("chhe_chil_id"));
						}
					}

					String xmlString = XmlUtils.formatXml(newDoc);
					LOGGER.info("输出准备上传的xml文本: " + xmlString);
					byte[] zippedBytes = XmlUtils.zipBytes(xmlString, "Children.xml");
					LOGGER.info("准备上传的压缩文本zippedBytes: " + zippedBytes);
					//result = CountryPlatformWebservice.uploadChildrenInfo("654765445", "675645645", zippedBytes);
					result = serviceChild.uploadChildrenInfo(departmentCode, password, zippedBytes);
					if(result.equals("1")){
						provincePlatformDao.updateSyncstatus(childCode);
						LOGGER.info("webservice接口返回值: " + result);
						LOGGER.info("上传成功! " +childCode);
					}else{
						LOGGER.info("webservice接口返回值: " + result);
						//return result;
					}
				}catch (Exception e){
					e.printStackTrace();
					LOGGER.info("上传数据异常: ，请检查：");
					return "0";
				}
			}

		}
		return result;

	}

	/**
	 * 上传未上传过的儿童信息
	 * @author rsp
	 * @time  2018-06-25
	 * @param departmentCode
	 * @param password
	 * @param chilCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String uploadChildInoculationsNotOnplat(String departmentCode, String password, String chilCode){
		ServiceChild serviceChild = null;
		try{
			serviceChild = serviceChildServiceLocator.getChildService();
		}catch (Exception e){
			e.printStackTrace();
		}
		String result = null;
			String childCode = chilCode;
			List<Map<String, Object>> childList = tChildInfoDao.findChildInfoForUpload(childCode);
			for(Map<String, Object> map:childList){
				if(map.get("chil_birthhospital").equals("0000000000")){
					map.put("chil_birthhospital","");
				}
			}
			List<Map<String, Object>> alleryMapList = tChildAllergyDao.findAlleryForUpload(childCode);
			if (alleryMapList == null || alleryMapList.isEmpty()) {
				//LOGGER.info("本地数据库没有查询该儿童传染病未上传记录");
			}
			String allery = "";
			for (Map<String, Object> alleryMap : alleryMapList) {
				allery = allery.concat(alleryMap.get("describes").toString());

			}
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("children");
			for (Map<String, Object> map : childList) {
				map.put("chil_sensitivity",allery);
				map.put("chil_fatherno",map.get("chilFatherno"));
				map.put("chil_motherno",map.get("chilMotherno"));
				map.put("chil_tel",map.get("chilTel"));
				map.put("chil_mobile",map.get("chilMobile"));
				map.put("chil_no",map.get("chilNo"));
				map.remove("chilFatherno");
				map.remove("chilMotherno");
				map.remove("chilTel");
				map.remove("chilMobile");
				map.remove("chilNo");
				try{
					map.put("chil_habiaddress",((JSONObject)JSON.parse(map.get("chil_habiaddress").toString())).get("position").toString());
					map.put("chil_address",((JSONObject)JSON.parse(map.get("chil_address").toString())).get("position").toString());
				}catch (Exception e){
					map.put("chil_habiaddress",map.get("chil_habiaddress")==null? "" :map.get("chil_habiaddress").toString());
					map.put("chil_address",map.get("chil_address")==null? "" :map.get("chil_address").toString());
				}

				Element child = root.addElement("child");
				Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
				child.addElement("chil_status").addText("C");
				while (entries.hasNext()) {
					Map.Entry<String, Object> entry = entries.next();
					try {
						if (entry.getValue() == null) {
							child.addElement(entry.getKey()).setText("");
						}else {
							String value = new String(entry.getValue().toString());
							String key = new String(entry.getKey());
							child.addElement(key).setText(value);
						}
					} catch (Exception ex) {
						LOGGER.info("<child>元素添加子元素"+entry.getKey()+"失败");
						ex.printStackTrace();
						//child.addElement(entry.getKey(), "");
					}
				}
				Element inoculationsElement = child.addElement("inoculations");
				List<Map<String, Object>> inoculationsMapList = tChildInoculateDao.findInoclationsForUpload(childCode);
				for(Map<String,Object> inoculationsMap:inoculationsMapList){
					String obj = inoculationsMap.get("inoc_depa_code").toString();
					boolean flag =obj.matches("[0-9]+");
					if (flag == true) {
//						System.out.println("接种单位："+obj);
					}else{
						inoculationsMap.put("inoc_depa_code","");
					}
				}
				if (inoculationsMapList == null || inoculationsMapList.isEmpty()) {
					LOGGER.info("本地数据库没有查询到该儿童未上传接种记录");
					//return "0";
				}else {
					for (Map<String, Object> inoculationMap : inoculationsMapList) {
						Element inoculationElement = inoculationsElement.addElement("inoculation");
						inoculationElement.addElement("inoc_bact_code").addText((String) inoculationMap.get("inoc_bact_code"));                    // 疫苗编码 4位

						inoculationElement.addElement("inoc_property").addText(inoculationMap.get("inoc_property") == null ? "0" : inoculationMap.get("inoc_property").toString());                    // 接种属性 参见文档 不知道传什么就传0
						inoculationElement.addElement("inoc_reinforce").addText(inoculationMap.get("inoc_reinforce") == null ? "0" : inoculationMap.get("inoc_reinforce").toString());                    // 强化属性 参见文档 不知道传什么就传0
						//inoculationElement.addElement("inoc_reinforce").addText("");
						inoculationElement.addElement("inoc_time").addText(inoculationMap.get("inoc_time").toString());                            // 剂次 数字的字符串
						//inoculationElement.addElement("inoc_dose").addText((String) inoculationMap.get("inoc_dose"));                            // 剂量 可以为空字符串
						inoculationElement.addElement("inoc_opinion").addText((String) inoculationMap.get("inoc_opinion"));                        // 评价 数字的字符串 含义参见文档
						inoculationElement.addElement("inoc_date").addText(inoculationMap.get("inoc_date").toString());                            // 接种日期 格式可以是yyyy-MM-dd 或者后面跟上HH:mm:ss
						inoculationElement.addElement("inoc_union_code").addText((String) inoculationMap.get("inoc_union_code"));                // 联合疫苗编码 可以为空字符串
						inoculationElement.addElement("inoc_batchno").addText((String) inoculationMap.get("inoc_batchno"));                        // 批号	可以为空字符串
						inoculationElement.addElement("inoc_validdate").addText((String) inoculationMap.get("inoc_validdate"));                    // 疫苗有效期 可以为空字符串
						inoculationElement.addElement("inoc_corp_code").addText((String) inoculationMap.get("inoc_corp_code"));                    // 生产企业 参见文档，可以为空之城
						//inoculationElement.addElement("inoc_corp_code").addText("03");
						inoculationElement.addElement("inoc_regulatory_code").addText(inoculationMap.get("inoc_regulatory_code")==null?"":inoculationMap.get("inoc_regulatory_code").toString());        // 疫苗监管码 可以为空字符串
						inoculationElement.addElement("inoc_free").addText((String) inoculationMap.get("inoc_free"));                            // 是否免费疫苗 1或者0的字符串
						inoculationElement.addElement("inoc_pay").addText((String) inoculationMap.get("inoc_pay"));                                // 是否补助疫苗 1或者0的字符串
						inoculationElement.addElement("inoc_county").addText((String) inoculationMap.get("inoc_county"));                        // 接种县国标6位 参考值522631
						inoculationElement.addElement("inoc_nationcode").addText((String) inoculationMap.get("inoc_nationcode"));                // 接种单位国标后4位 参考值1701 在本系统中将currentdepartid拆分即可得到
						inoculationElement.addElement("inoc_inpl_id").addText(inoculationMap.get("inoc_inpl_id")==null?"":inoculationMap.get("inoc_inpl_id").toString());                        // 接种部位 数字的字符串 含义参见文档
						//inoculationElement.addElement("inoc_inpl_id").addText("13");
						inoculationElement.addElement("inoc_editdate").addText(inoculationMap.get("inoc_editdate").toString());                   //更新日期
						inoculationElement.addElement("inoc_nurse").addText((String) inoculationMap.get("inoc_nurse"));                            // 接种护士 人名
						inoculationElement.addElement("inoc_useticket").addText((String) inoculationMap.get("inoc_useticket"));
						inoculationElement.addElement("zxhs").addText((String) inoculationMap.get("zxhs"));                            // 接种护士 人名
						inoculationElement.addElement("inoc_depa_code").addText((String) inoculationMap.get("inoc_depa_code"));
						inoculationElement.addElement("inoc_depa_id").addText((String) inoculationMap.get("inoc_depa_id"));
						inoculationElement.addElement("inoc_vcn_kind").addText((String) inoculationMap.get("inoc_vcn_kind"));
					}
				}
				//传染病
				Element infectionsElement = child.addElement("infections");
				List<Map<String, Object>> infectionsMapList = tChildInfectionDao.findInfections(childCode);
				if (infectionsMapList == null || infectionsMapList.isEmpty()) {
					LOGGER.info("本地数据库没有查询到该儿童未上传传染病记录");
					//return "0";
				}
				for (Map<String, Object> infectionMap : infectionsMapList) {
					Element infectionElement = infectionsElement.addElement("infection");
					//infectionElement.addElement("infe_chil_id").addText((String) infectionMap.get("infe_chil_id"));
					infectionElement.addElement("infe_code").addText((String) infectionMap.get("infe_code"));                    // 传染病编码 4位 主键
					infectionElement.addElement("infe_date").addText(infectionMap.get("infe_date").toString());                    // 发病日期
				}
				//异常反应
				Element aefisElement = child.addElement("aefis");
				List<Map<String, Object>> aefisMapList = tChildAbnormalDao.findAefis(childCode);
				if (aefisMapList == null || aefisMapList.isEmpty()) {
					LOGGER.info("本地数据库没有查询该儿童未上传异常反应记录");
					//return "0";
				}

				for (Map<String, Object> aefiMap : aefisMapList) {
					Element aefiElement = aefisElement.addElement("aefi");
					aefiElement.addElement("aefi_bact_code").addText((String) aefiMap.get("aefi_bact_code"));                    // 疫苗编码 4位
					aefiElement.addElement("aefi_date").addText((String) aefiMap.get("aefi_date"));                                // 反应日期
					aefiElement.addElement("aefi_code").addText((String) aefiMap.get("aefi_code"));                                // 反应症状编码
					//aefiElement.addElement("aefi_chil_id").addText((String) aefiMap.get("aefi_chil_id"));
				}
				//禁忌
				Element istabusElement = child.addElement("istabus");
				List<Map<String, Object>> istabusMapList = tChildTabooDao.findIstabus(childCode);
				if (istabusMapList == null || istabusMapList.isEmpty()) {
					LOGGER.info("本地数据库没有查询该儿童未上传禁忌记录");
					//return "0";
				}
				for (Map<String, Object> istabuMap : istabusMapList) {
					Element istabuElement = istabusElement.addElement("istabu");
					istabuElement.addElement("ista_code").addText((String) istabuMap.get("ista_code"));                    // 禁忌症编码
					//istabuElement.addElement("ista_chil_id").addText((String) istabuMap.get("ista_chil_id"));
				}
				//在册情况
				Element childheresElement = child.addElement("childheres");
				List<Map<String, Object>> childheresMapList = tChildMoveDao.findChildheres(childCode);
				if (childheresMapList == null || childheresMapList.isEmpty()) {
					LOGGER.info("本地数据库没有查询该儿童未上传变更记录");
					//return "0";
				}
				for (Map<String, Object> childhereMap : childheresMapList) {
					Element childhereElement = childheresElement.addElement("childhere");
					childhereElement.addElement("chhe_here").addText((String) childhereMap.get("chhe_here"));                            //在册情况
					childhereElement.addElement("chhe_prehere").addText( childhereMap.get("chhe_prehere").toString());
					/*childhereElement.addElement("chhe_chil_province").addText("chhe_chil_province");*/
					childhereElement.addElement("chhe_chil_provinceremark").addText(childhereMap.get("chhe_chil_provinceremark")!=null?childhereMap.get("chhe_chil_provinceremark").toString():"");
					childhereElement.addElement("chhe_changedate").addText(childhereMap.get("chhe_changedate").toString());
					childhereElement.addElement("chhe_depa_id").addText(childhereMap.get("chhe_depa_id").toString());
					//childhereElement.addElement("chhe_chil_id").addText(childhereMap.get("chhe_chil_id").toString());
				}
			}

			try{
				String xmlString = XmlUtils.formatXml(doc);
				//String xmlString = "";
				LOGGER.info("输出准备上传的xml文本: " + xmlString);
				byte[] zippedBytes = XmlUtils.zipBytes(xmlString, "Children.xml");
				//byte[] zippedBytes = new byte[1024];
				LOGGER.info("压缩文本zippedBytes: " + zippedBytes);
				result = serviceChild.uploadChildrenInfo(departmentCode, password, zippedBytes);
				if(result.equals("1")){
					provincePlatformDao.updateSyncstatus(childCode);
					LOGGER.info("接口返回值: " + result);
					LOGGER.info("上传成功: "+childCode);
				}else{
					LOGGER.info("接口返回值: "+result);
					//return result;
				}
				//return result;
			}catch (Exception e){
				e.printStackTrace();
				LOGGER.info("上传数据异常: ，请检查：");
				return "0";
			}
		return result;
	}

	@Override
	public String uploadRoutineImmuReport(String departmentCode, String password) {
		return null;
	}

	@Override
	public Map<String, Object> downloadInoculateFromProvinceByTimer(String childCode, String departmentCode) {
		return  null;
	}

	@Override
	public R downloadMigrationChildNo(Map<String, Object> map) {

		String org_id = Constant.GLOBAL_ORG_ID;
		int result = provincePlatformDao.queryMigrations(map);
		if(result == 0){
			provincePlatformDao.saveMigrations(map);
		}
		String childCode = (String) map.get("migr_childcode");
		map.put("departmentCode",org_id);
		map.put("childCode",childCode);
		map.put("state",map.get("migr_vacc_state"));
		Children children = findOnProvincePlatform(map);
		R r =  saveToLocal(children, org_id,map.get("migr_vacc_state").toString());

		return R.ok("保存成功");
	}

	@Override
	public void downNowChildFromProvicnce() {

	}

	/**
	 * 保存儿童时，做地址等相关转换
	 * @author 饶士培
	 * @date 2018-08-03 11:41:00
	 * @param childInfo
	 * @param orgId
	 * @return
	 */
	@Override
	public Child transformerAddress(Child childInfo,String orgId,String state) {
		//获取建档县国标
		String chil_createcounty = (String) childInfo.getChilCreatecounty();
		//家庭地址
		String createcountyDetail=(String)childInfo.getChilAddress();
		//获取户籍县国标
		String chil_habi_id = (String) childInfo.getChilHabiId();
		//户口地址
		String habiDetail=(String)childInfo.getChilHabiaddress();

		//将身份证及电话号码为空字符串的字段设为空null
		String chilFatherno = childInfo.getChilFatherno();
		childInfo.setChilFatherno(chilFatherno!=null?(chilFatherno.trim().length()==0?null:chilFatherno):null);

		String chilMotherno = childInfo.getChilMotherno();
		childInfo.setChilMotherno(chilMotherno!=null?(chilMotherno.trim().length()==0?null:chilMotherno):null);

		String chilNo = childInfo.getChilNo();
		childInfo.setChilNo(chilNo!=null?(chilNo.trim().length()==0?null:chilNo):null);

		String chilTel = childInfo.getChilTel();
		childInfo.setChilTel(chilTel!=null?(chilTel.trim().length()==0?null:chilTel):null);

		String chilMobile = childInfo.getChilMobile();
		childInfo.setChilMobile(chilMobile!=null?(chilMobile.trim().length()==0?null:chilMobile):null);

		//getStringAddress(childInfo, chil_createcounty,createcountyDetail,"chil_address");
		//getStringAddress(childInfo, chil_habi_id,habiDetail,"chil_habiaddress");
		Double childWeight = 0.0;
		if(childInfo.getChilBirthweight().toString()!=null && childInfo.getChilBirthweight().toString().trim().length()>0){
			if(childInfo.getChilBirthweight().toString().indexOf(".")==1 || childInfo.getChilBirthweight().toString().length()<=3){
				childWeight = childInfo.getChilBirthweight();
				childInfo.setChilBirthweight(childWeight);
			}else{
				childWeight = childInfo.getChilBirthweight() /1000;
				childInfo.setChilBirthweight(childWeight);
			}
		}
		//更改在册情况，产生变更记录
		if(childInfo.getChilHere().equals("1") && !orgId.equals(childInfo.getChilCurdepartment())){
			if("1".equals(state)){
				//childInfo.setChilCommittee("AAA");
				childInfo.setChilLeavedate(sdf.format(new Date()));
				TChildMoveEntity moveEntity = new TChildMoveEntity();
				if(ShiroUtils.getUserEntity()!=null){
					moveEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
					moveEntity.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
				}
				moveEntity.setOrgId(Constant.GLOBAL_ORG_ID);
				moveEntity.setChheDepaId(Constant.GLOBAL_ORG_ID);
				moveEntity.setChhePrehere(childInfo.getChilHere());
				childInfo.setChilHere("2");
				moveEntity.setChheHere(childInfo.getChilHere());
				moveEntity.setChheChilProvinceremark("");
				moveEntity.setChilCode(childInfo.getChilCode());
				tChildMoveDao.save(moveEntity);
			}else {
				childInfo.setChilCommittee("AAA");
				childInfo.setChilLeavedate(sdf.format(new Date()));
				TChildMoveEntity moveEntity = new TChildMoveEntity();
				if(ShiroUtils.getUserEntity()!=null){
					moveEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
					moveEntity.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
				}

				moveEntity.setOrgId(Constant.GLOBAL_ORG_ID);
				moveEntity.setChheDepaId(Constant.GLOBAL_ORG_ID);
				moveEntity.setChhePrehere(childInfo.getChilHere());
				childInfo.setChilHere("9");
				moveEntity.setChheHere(childInfo.getChilHere());
				moveEntity.setChheChilProvinceremark("");
				moveEntity.setChilCode(childInfo.getChilCode());
				tChildMoveDao.save(moveEntity);
			}
		}else if(!childInfo.getChilHere().equals("1") && orgId.equals(childInfo.getChilCurdepartment())){
			//childInfo.setChilCommittee("AAA");
			childInfo.setChilLeavedate(sdf.format(new Date()));
			TChildMoveEntity moveEntity = new TChildMoveEntity();
			if(ShiroUtils.getUserEntity()!=null){
				moveEntity.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
				moveEntity.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
			}
			moveEntity.setOrgId(Constant.GLOBAL_ORG_ID);
			moveEntity.setChheDepaId(Constant.GLOBAL_ORG_ID);
			moveEntity.setChhePrehere(childInfo.getChilHere());
			childInfo.setChilHere("1");
			moveEntity.setChheHere(childInfo.getChilHere());
			moveEntity.setChheChilProvinceremark("");
			moveEntity.setChilCode(childInfo.getChilCode());
			tChildMoveDao.save(moveEntity);
		}
		return  childInfo;
	}

	/**
	 * 将下载儿童的地址匹配完整（新版系统废除）
	 * @param childInfo
	 * @param countyId
	 * @param detail
	 * @param targetAddress
	 */
	@Override
	public void getStringAddress(Child childInfo, String countyId, String detail, String targetAddress) {
		HashMap<String, Object> tempMap = new HashMap<>();
		List<Map<String, Object>> retMap=null;
		if(countyId!=null && countyId.trim().length()>0){
			tempMap.put("countyId",countyId);
			//根据建档县国标获取居住地所在省市县
			try {
				retMap = provincePlatformDao.selectByCurrentAddress(tempMap);
				String townName = getTownName(detail);
				Map<String, Object> targetMap=null;//存放目标数组
				if(!org.springframework.util.StringUtils.isEmpty(townName)){
					for(int i=0;i<retMap.size();i++){
						Object town_name = retMap.get(i).get("town_name");
						//如果镇存在，就取出这条数据
						if (town_name.toString().indexOf(townName)>-1) {
							targetMap=retMap.get(i);
							targetMap.put("status","1");
						}
					}
				}
				if (targetMap==null){
					//如果乡镇街道不存在，就取第一条数据
                    if(retMap.size()>0){
                        targetMap=retMap.get(0);
                        targetMap.put("status","0");
                    }else{
						targetMap = new HashMap<>();
                        targetMap.put("status","1");
                    }
				}

				if(targetMap!=null){
					String  address = getAddress(targetMap,detail);
					String currentAddress = JSON.toJSONString(address);
					if(targetAddress.equals("chil_address")){
						childInfo.setChilAddress(address);
					}
					if(targetAddress.equals("chil_habiaddress")){
						childInfo.setChilHabiaddress(address);
					}

					//childInfo.put(targetAddress,currentAddress);
					//System.out.println(currentAddress);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public String getAddress(Map<String, Object> retMap,String dateiled) {
	    if("1".equals(retMap.get("status"))){
	        return "";
        }
		String province_name = (String) retMap.get("province_name");
		String province_id = retMap.get("province_id").toString();
		String city_name = (String) retMap.get("city_name");
		String city_id = retMap.get("city_id").toString();
		String county_name = (String) retMap.get("county_name");
		String county_id = retMap.get("county_id").toString();
		String town_id =null;
		String town_name=null;
		if (retMap.get("status").equals("1")){
			town_name = (String) retMap.get("town_name");
			town_id = retMap.get("town_id").toString();
		}else{
			town_id=" ";
			town_name=" ";
		}
		/*Map<String, Object> tempMap = new HashMap<>();
		tempMap.put("position",province_name+city_name+county_name+town_name+dateiled);
		tempMap.put("town_id",town_id);
		tempMap.put("town_name",town_name);
		tempMap.put("county_id",county_id);
		tempMap.put("county_name",county_name);
		tempMap.put("city_id",city_id);
		tempMap.put("city_name",city_name);
		tempMap.put("province_id",province_id);
		tempMap.put("province_name",province_name);
		tempMap.put("town_id",town_id);
		tempMap.put("dateiled",dateiled);*/
		return  province_name+city_name+county_name+town_name+dateiled;
	}
	@Override
	public String getTownName(String detail) {
		int index=0;
		String result=null;
		if ((index=detail.indexOf("镇"))>=2){
			result = detail.substring(index - 2, index);
		}else if ((index=detail.indexOf("乡"))>=2){
			//System.out.println(detail.indexOf("乡"));
			result = detail.substring(index - 2, index);
		}else if((index=detail.indexOf("街道"))>=2){
			result = detail.substring(index - 2, index);

		}
		return result;
	}
	@Override
	public String uploadModifyChildInoculations(String departmentCode, String password, String[] childCodes) {
		return null;
	}

	@Override
	public void modifyChildInfoXml(Element childElement, Map<String, Object> mapChild) {
		/***********************************过敏反应**************************************/

		List<Map<String, Object>> alleryMapList = tChildAllergyDao.findAlleryForUpload(mapChild.get("chil_code").toString());
		if (alleryMapList == null || alleryMapList.isEmpty()) {

		}
		String allery = "";
		for (Map<String, Object> alleryMap : alleryMapList) {
			allery = allery.concat(alleryMap.get("describes").toString());

		}
		mapChild.put("chil_sensitivity",allery);
		try {
			mapChild.put("chil_habiaddress", mapChild.get("chil_habiaddress") == null ? "" : ((JSONObject) JSON.parse(mapChild.get("chil_habiaddress").toString())).get("position").toString());
		} catch (Exception e) {
			mapChild.put("chil_habiaddress", mapChild.get("chil_habiaddress") == null ? "" : mapChild.get("chil_habiaddress").toString());
		}
		try {
			mapChild.put("chil_address", mapChild.get("chil_address") == null ? "" : ((JSONObject) JSON.parse(mapChild.get("chil_address").toString())).get("position").toString());
		} catch (Exception e) {
			mapChild.put("chil_address", mapChild.get("chil_address") == null ? "" : mapChild.get("chil_address").toString());

		}
		Iterator<Map.Entry<String, Object>> entries = mapChild.entrySet().iterator();
		Element element = null;
		//childChild.addElement("chil_status").addText("C");
		while (entries.hasNext()) {
			Map.Entry<String, Object> entry = entries.next();
			String key = new String(entry.getKey().toString());
			element = childElement.element(key);
			if (element != null && !"".equals(element)) {
				try {
					if (entry.getValue() == null || "".equals(entry.getValue())) {
						//childElement.addElement(entry.getKey()).setText("");
						//element.setText("");//如果为空,就不更新
						continue;
					} else {
						String value = new String(entry.getValue().toString());
						element.setText(value);
						//childElement.addElement(key).setText(value);
					}
				} catch (Exception ex) {
					LOGGER.info("<child>元素添加子元素" + entry.getKey() + "失败");
					ex.printStackTrace();
					//child.addElement(entry.getKey(), "");
				}
			}
		}
	}

	@Override
	public void deleteInoculationsXml(String childCode, Element childElement, Element inoculationsElement, Node inoculations) {

	}

	@Override
	public void modifyInoculationsXml(String childCode, Element inoculationsElement, Element childElement, Node inoculations, List<Node> inoculationNodes, List<Map<String, Object>> inoculationsMapList) {

	}

	@Override
	public Child handleNewborn(TChildDownloadEntity downloadEntity, String orgid) {
		Child child = new Child();
		// 1户籍属性
		child.setChilAccount("1");
		// 2家庭地址 - 现居地址
		child.setChilAddress(downloadEntity.getNeboAddressCommunity());

		// 3.现住居委会
		child.setChilCommittee("CCC");

		// 4.出生日期 - 出生时间
		/*String nebo_birthtime = downloadEntity.getNeboBirthtime().toString();
		String hour = downloadEntity.getNeboHour();
		String BIRTHTIME = nebo_birthtime + " " + hour + ":00:00";
		try {
			child.setChilBirthday(sdf.parse(BIRTHTIME));
		}catch (Exception ex) {
				ex.printStackTrace();
			}*/
		child.setChilBirthday(downloadEntity.getNeboBirthtime());

		// 5出生医院 新生儿只有名字
		child.setChilBirthhospitalname(downloadEntity.getNeboAdduser());

		/*// 6出生证号
		String chilBirthno = (String) childMap.get("chil_birthno");
		localChildMap.put("BIRTHCARD", chilBirthno);*/

		// 6出生重量(kg)---(g)
		child.setChilBirthweight(Double.valueOf(downloadEntity.getNeboWeight()));

		//7 儿童编码
		child.setChilCode(downloadEntity.getNeboCode());

		//8 建档县国标 child_createcounty(补)
		child.setChilCreatecounty(orgid.substring(0, 6));

		//9 建档日期
		/*String adddate = downloadEntity.getNeboAdddate().toString();
		String chilCreatedate = adddate + " " +  "00:00:00";
		try {
			child.setChilCreatedate(sdf.parse(chilCreatedate));
		}catch (Exception ex) {
			ex.printStackTrace();
		}*/
		child.setChilCreatedate(downloadEntity.getNeboAdddate());

		//11 建档单位编码 ISSUEUNIT`,chil_createsite

		child.setChilCreatesite(orgid);
		// 12现管理单位编码 不对应xgldw,chil_curdepartment

		child.setChilCurdepartment(orgid);

		//原管理单位
		child.setChilPredepartment(orgid);
		//localChildMap.put("xgldw", orgid);
		// 13父亲姓名
		child.setChilFather(downloadEntity.getNeboFather());

		// 14父亲身份证
		String chilFatherno = downloadEntity.getNeboFatherno();
		child.setChilFatherno(chilFatherno!=null?(chilFatherno.trim().length()==0?null:chilFatherno):null);

		// 16母亲姓名
		child.setChilMother(downloadEntity.getNeboMother());

		// 17母亲身份证
		String chilMotherno = downloadEntity.getNeboMotherno();
		child.setChilMotherno(chilMotherno!=null?(chilMotherno.trim().length()==0?null:chilMotherno):null);

		// 18母亲电话
		String chilTel = downloadEntity.getNeboMotherTel();
		child.setChilTel(chilTel!=null?(chilTel.trim().length()==0?null:chilTel):downloadEntity.getNeboFatherTel());

		// 19户籍县国标
		child.setChilHabiId(downloadEntity.getNeboHabiId());

		// 20户籍地址
		child.setChilHabiaddress(downloadEntity.getNeboHabiDepaIdName());

		// 21在册状态 - 默认本地
		child.setChilHere("1");

		//22 手机
		String chilMobile = downloadEntity.getNeboMobile();
		child.setChilMobile(chilMobile!=null?(chilMobile.trim().length()==0?null:chilMobile):downloadEntity.getNeboTel());

		// 23母亲HB
		child.setChilMotherhb(downloadEntity.getNeboMhbsag());


		// 24儿童姓名
		child.setChilName(downloadEntity.getNeboName());

		// 25民族
		child.setChilNatiId(downloadEntity.getNeboNatiId());

		// 26居住属性 - 不对应child_residenceclient,chil_residenceclient 居住属性2child_residence,chil_residence 居住属性
		child.setChilResidence(downloadEntity.getNeboResidence());


		//29 性别
		child.setChilSex(downloadEntity.getNeboSex());

		//30 电话 - 家庭电话


		return child;
	}
}