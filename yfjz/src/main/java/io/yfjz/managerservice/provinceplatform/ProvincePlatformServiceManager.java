package io.yfjz.managerservice.provinceplatform;


import io.yfjz.entity.provinceplatform.Child;
import io.yfjz.entity.provinceplatform.Children;
import io.yfjz.entity.provinceplatform.TChildDownloadEntity;
import io.yfjz.utils.R;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ProvincePlatformServiceManager {



	 R downloadMigrationChildNo(int days);

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
	 void modifyChildInfoXml(Element childElement, Map<String, Object> mapChild);

	/**
	 * 删除接种记录
	 * @author rsp
	 * @time  2018-06-25
	 * @param childCode
	 * @param childElement
	 * @param inoculationsElement
	 * @param inoculations
	 */
	 void deleteInoculationsXml(String childCode, Element childElement, Element inoculationsElement, Node inoculations);


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
	 void modifyInoculationsXml(String childCode, Element inoculationsElement, Element childElement, Node inoculations, List<Node> inoculationNodes, List<Map<String, Object>> inoculationsMapList);


	/**
	 * 上传未上传过的儿童信息
	 * @author rsp
	 * @time  2018-06-25
	 * @param departmentCode
	 * @param password
	 * @param childCodes
	 * @return
	 * @throws Exception
	 */
	 String uploadChildInoculationsNotOnplat(String departmentCode, String password, String[] childCodes);



}