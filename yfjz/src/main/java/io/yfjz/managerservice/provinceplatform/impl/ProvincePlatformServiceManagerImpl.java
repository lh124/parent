package io.yfjz.managerservice.provinceplatform.impl;


import io.yfjz.dao.child.*;
import io.yfjz.dao.provinceplatform.ProvincePlatformDao;
import io.yfjz.dao.provinceplatform.TChildDownloadDao;
import io.yfjz.entity.provinceplatform.*;
import io.yfjz.managerservice.provinceplatform.ProvincePlatformServiceManager;
import io.yfjz.service.jwxplat.ServiceChild;
import io.yfjz.service.jwxplat.ServiceChildServiceLocator;
import io.yfjz.service.provinceplatform.ProvincePlatformService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.CustomDateConverter;
import io.yfjz.utils.R;
import io.yfjz.utils.XmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 贵州省免疫规划信息系统对接的Service
 * @author 研发部 饶士培
 * @date 2018年7月24日10:31:48
 */
@Service("provincePlatformServiceManager")
public class ProvincePlatformServiceManagerImpl implements ProvincePlatformServiceManager {

	private static 	ServiceChildServiceLocator serviceChildServiceLocator = new ServiceChildServiceLocator();
	private static Logger LOGGER= Logger.getLogger(ProvincePlatformServiceManagerImpl.class);
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
	ProvincePlatformService provincePlatformService;

	/**
	 * 下载异地接种儿童
	 * @author 饶士培
	 * @date 2018-09-13
	 * @param days 下载天数，往前推迟天数
	 * @return
	 */
	@Override
	public R downloadMigrationChildNo(int days) {
		ServiceChild serviceChild = null;
		try{
			serviceChild = serviceChildServiceLocator.getChildService();
			String org_id = Constant.GLOBAL_ORG_ID;//获取配置文件的org_id
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, days);
			String d =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
//			System.out.println(d);
			byte[] results = serviceChild.downloadMigrationChildNo(org_id, org_id, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
			if (results == null) {
//				System.out.println("调用接口返回: " + results);
				return R.ok("暂时没有儿童异地接种");
			}
			results = XmlUtils.unzipBytes(results);
			Map<String, Object> resultMap = XmlUtils.xmlBytesToMap(results);
			if(resultMap!=null){
				List<HashMap<String,Object>> resultListMap = (List<HashMap<String, Object>>) ((HashMap) resultMap.get("migrations")).get("migration");
				for (HashMap map :resultListMap){
					try{
						provincePlatformService.downloadMigrationChildNo(map);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}else{
				return R.ok("暂时没有儿童异地接种");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return R.error("网络出现错误");
		}
		return R.ok("保存成功");
	}




	@Override
	public String uploadModifyChildInoculations(String departmentCode, String password, String[] childCodes) {
		return null;
	}

	@Override
	public void modifyChildInfoXml(Element childElement, Map<String, Object> mapChild) {

	}

	@Override
	public void deleteInoculationsXml(String childCode, Element childElement, Element inoculationsElement, Node inoculations) {

	}

	@Override
	public void modifyInoculationsXml(String childCode, Element inoculationsElement, Element childElement, Node inoculations, List<Node> inoculationNodes, List<Map<String, Object>> inoculationsMapList) {

	}

	@Override
	public String uploadChildInoculationsNotOnplat(String departmentCode, String password, String[] childCodes) {
		return null;
	}

}