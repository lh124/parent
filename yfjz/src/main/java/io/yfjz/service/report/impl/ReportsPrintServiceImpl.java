package io.yfjz.service.report.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.yfjz.dao.child.*;
import io.yfjz.dao.statistics.InoculateLosgDao;
import io.yfjz.entity.child.*;
import io.yfjz.entity.sys.SysDepartEntity;
import io.yfjz.service.report.ReportsPrintService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.ShiroUtils;
import io.yfjz.utils.XMLHelper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 打印服务接口实现
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 13:43:13
 */
@Service("ReportsPrintService")
public class ReportsPrintServiceImpl implements ReportsPrintService {

	@Autowired
	private TChildInfoDao tChildInfoDao;
	@Autowired
	private TChildInoculateDao tChildInoculateDao;
	@Autowired
	private TChildTabooDao tChildTabooDao;
	@Autowired
	private TChildAllergyDao tChildAllergyDao;
	@Autowired
	private TChildInfectionDao tChildInfectionDao;
	@Autowired
	private TChildAbnormalDao tChildAbnormalDao;
	@Autowired
	private InoculateLosgDao inoculateLosgDao;
	/**
	 * 查询要打印的儿童数据
	 * @author 饶士培
	 * @date 2018-08-31
	 * @param map
	 * @return
	 */
	public Map<String,Object> queryPrintData(Map<String,Object> map){
		Map<String,Object> childObj = tChildInfoDao.queryForPrint(map);
		List<TChildTabooEntity> tChildTabooList = tChildTabooDao.queryList(map);
		List<TChildAllergyEntity> tChildAllergyList = tChildAllergyDao.queryList(map);
		List<TChildInfectionEntity> tChildInfectionList = tChildInfectionDao.queryList(map);
		List<TChildAbnormalEntity> tChildAbnormalList = tChildAbnormalDao.queryList(map);
		Map<String, Object> params = new HashMap<String, Object>();
		Timestamp birthtime = (Timestamp) childObj.remove("chil_birthday");
		Date addtime =(Date) childObj.get("create_card_time");
		String chilFather = childObj.get("chil_father")==null?"":childObj.get("chil_father").toString();
		String chilMother = childObj.get("chil_mother")==null?"":childObj.get("chil_mother").toString();
		childObj.put("guardianname",chilFather+"  " +chilMother);
		if(addtime==null || "".equals(addtime)){
			addtime = new Date();
		}
		String departname = Constant.GLOBAL_ORG_NAME;
		//身份证解密字段
		String identitycard = (String)childObj.get("chilNo");
		childObj.put("identitycard",identitycard);
		//过敏史
		String allergys = "";
		for(TChildAllergyEntity allergyEntity: tChildAllergyList){
			allergys = allergys.concat(allergyEntity.getDescribes());
		}
		childObj.put("allergichistory",allergys);
		//禁忌
		String taboos = "";
		for(TChildTabooEntity tabooEntity: tChildTabooList){
			taboos = taboos.concat(tabooEntity.getIstaCode());
		}
		childObj.put("contraindication",taboos);
		//异常反应史
		String abnormal = "";
		for(TChildAbnormalEntity abnormalEntity: tChildAbnormalList){
			abnormal = abnormal.concat(abnormalEntity.getAefiCode());
		}
		childObj.put("abnormal",abnormal);
		//传染病
		String infections = "";
		for(TChildInfectionEntity infectionEntity: tChildInfectionList){
			infections = infections.concat(infectionEntity.getInfeCode());
		}
		childObj.put("infections",infections);
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// childObj.put("birthtime", df.format(birthtime));
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		SimpleDateFormat df1 = new SimpleDateFormat("MM");
		SimpleDateFormat df2 = new SimpleDateFormat("dd");
		SimpleDateFormat df3 = new SimpleDateFormat("HH");
		Date date = new Date();

		childObj.put("orgname",departname);
		childObj.put("printYear", df.format(date));
		childObj.put("printMonth", df1.format(date));
		childObj.put("printDay",  df2.format(date));
		childObj.put("year", df.format(birthtime));
		childObj.put("month", df1.format(birthtime));
		childObj.put("day", df2.format(birthtime));
		childObj.put("hours", df3.format(birthtime));

		childObj.put("createYear", df.format(addtime));
		childObj.put("createMonth", df1.format(addtime));
		childObj.put("createDay", df2.format(addtime));
		String user = null;
		user =(String) childObj.remove("create_user_name");
		if(user==null){
			user = ShiroUtils.getUserEntity().getRealName();
		}
		childObj.put("createUser",user);
		Double weight = (Double)childObj.remove("chil_birthweight");
		childObj.put("birthweight",weight);
		String detailaddress =(String)childObj.remove("chil_address");
		String province_name = null;
		String city_name = null;
		String county_name = null;
		String town_name = null;
		String dateiled = null;
		if (!StringUtils.isEmpty(detailaddress)) {
			try {
				if(detailaddress.indexOf("-")>0){
					String[] preAddress = detailaddress.split("-");
					String[] fAddress = preAddress[0].split(" ");
					province_name=fAddress[0].endsWith("省")?fAddress[0].substring(0,fAddress[0].length()-1):fAddress[0];
					city_name=fAddress[1].endsWith("市")?fAddress[1].substring(0,fAddress[1].length()-1):fAddress[1];
					county_name= fAddress[2].endsWith("县")?fAddress[2].substring(0,fAddress[2].length()-1):fAddress[2];
					town_name=fAddress[3].endsWith("乡")||fAddress[3].endsWith("镇")?fAddress[3].substring(0,fAddress[3].length()-1):fAddress[3];
					dateiled=preAddress[1];
					childObj.put("province_name", province_name);
					childObj.put("city_name", city_name);
					childObj.put("county_name",county_name);
					childObj.put("town_name", town_name);
					childObj.put("dateiled", dateiled);

				}else{
					province_name = detailaddress.substring(0,detailaddress.indexOf("省"));
					city_name = detailaddress.substring(detailaddress.indexOf("省")+1,detailaddress.indexOf("市")!=-1?detailaddress.indexOf("市"):detailaddress.length());
					county_name = detailaddress.substring(detailaddress.indexOf("市")+1,detailaddress.indexOf("区")!=-1?detailaddress.indexOf("区"):detailaddress.indexOf("县"));
					town_name = detailaddress.substring(detailaddress.indexOf("县")+1,detailaddress.indexOf("乡")!=-1?detailaddress.indexOf("乡"):detailaddress.indexOf("镇"));
					dateiled = detailaddress.substring(detailaddress.indexOf("乡")+1,detailaddress.length());
					childObj.put("county_name",county_name);
					childObj.put("town_name", town_name);
					childObj.put("province_name", province_name);
					childObj.put("city_name", city_name);
					childObj.put("dateiled", dateiled);
				}

			} catch (Exception e) {
				//childObj.put("addr", "");
				childObj.put("county_name",county_name);
				childObj.put("town_name", town_name);
				childObj.put("province_name", province_name);
				childObj.put("city_name", city_name);
				childObj.put("dateiled",detailaddress);

			}
		}
		//户籍地址
		String  chil_habiaddress =(String)childObj.remove("chil_habiaddress");
		String province_name_h = null;
		String city_name_h = null;
		String county_name_h = null;
		String town_name_h = null;
		String dateiled_h = null;
		if (!StringUtils.isEmpty(chil_habiaddress)) {
			try {
				if (chil_habiaddress.indexOf("-") > 0) {
					String[] preAddress = chil_habiaddress.split("-");
					String[] fAddress = preAddress[0].split(" ");
					province_name_h = fAddress[0].endsWith("省")?fAddress[0].substring(0,fAddress[0].length()-1):fAddress[0];
					city_name_h =fAddress[1].endsWith("市")?fAddress[1].substring(0,fAddress[1].length()-1):fAddress[1];
					county_name_h = fAddress[2].endsWith("县")?fAddress[2].substring(0,fAddress[2].length()-1):fAddress[2];
					town_name_h = fAddress[3].endsWith("乡")||fAddress[3].endsWith("镇")?fAddress[3].substring(0,fAddress[3].length()-1):fAddress[3];
					dateiled_h = preAddress[1];
					childObj.put("province_name_h", province_name_h);
					childObj.put("city_name_h", city_name_h);
					childObj.put("county_name_h", county_name_h);
					childObj.put("town_name_h", town_name_h);
					childObj.put("dateiled", dateiled_h);

				} else {
					province_name_h = chil_habiaddress.substring(0, chil_habiaddress.indexOf("省"));
					city_name_h = chil_habiaddress.substring(chil_habiaddress.indexOf("省") + 1, chil_habiaddress.indexOf("市") != -1 ? chil_habiaddress.indexOf("市") : chil_habiaddress.length());
					county_name_h = chil_habiaddress.substring(chil_habiaddress.indexOf("市") + 1, chil_habiaddress.indexOf("区") != -1 ? chil_habiaddress.indexOf("区") : chil_habiaddress.indexOf("县"));
					town_name_h = chil_habiaddress.substring(chil_habiaddress.indexOf("县") + 1, chil_habiaddress.indexOf("乡") != -1 ? chil_habiaddress.indexOf("乡") : chil_habiaddress.indexOf("镇"));
					dateiled_h = chil_habiaddress.substring(chil_habiaddress.indexOf("乡") + 1, chil_habiaddress.length());
					childObj.put("county_name_h", county_name_h);
					childObj.put("town_name_h", town_name_h);
					childObj.put("province_name_h", province_name_h);
					childObj.put("city_name_h", city_name_h);
					childObj.put("dateiled_h", dateiled_h);
				}

			} catch (Exception e) {
				childObj.put("county_name_h",county_name_h);
				childObj.put("town_name_h", town_name_h);
				childObj.put("province_name_h", province_name_h);
				childObj.put("city_name_h", city_name_h);
				childObj.put("dateiled_h", chil_habiaddress);
			}
		}
		return childObj;
	}
	/**接种证打印儿童信息
	 * @author:饶士培
	 * @date:2018-08-17
	 */
	@Override
	public Map queryForPrint(Map<String,Object> map, HttpServletResponse response) {
		Map<String,Object> childObj = queryPrintData(map);
		String path = map.get("path").toString();
		String viewname = map.get("jrxml_name").toString();
		Map<String, Object> params = new HashMap<String, Object>();
		Integer isprint = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(childObj);
		if (isprint == 1) {
			list = null;
		}
		JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
		// print对象
		// log.info("5.3====="+jasperReport);
		try{
			JasperReport jasperReport = JasperCompileManager.compileReport(path + "reports/" + viewname + ".jrxml");
			JasperPrint print =null;
			print = JasperFillManager.fillReport(jasperReport,params, dataSource);
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf" );

			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}

		return null;
	}

	/**接种卡打印儿童信息
	 * @author:饶士培
	 * @date:2018-08-17
	 */
	@Override
	public Map printCard(Map<String,Object> map, HttpServletResponse response) {
		Map<String,Object> childObj = queryPrintData(map);
		String path = map.get("path").toString();
		String viewname = map.get("jrxml_name").toString();
		String chilCode = childObj.get("childcode").toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int  isprint = 0;
		if (childObj.get("isprint") == null) {
			isprint = 0;
		} else {
			isprint = Integer.valueOf((String) childObj.get("isprint"));
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(childObj);
		if (isprint == 1) {
			list = null;
		}
		//JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
		// print对象
		// log.info("5.3====="+jasperReport);
		Map<String,Object> params = null;
		try {
			params = new HashMap<String, Object>();
			Map<String, Object> data1 = new HashMap<String, Object>();
			Map<String, Object> data2 = new HashMap<String, Object>();
			/*加载reports配置文件*/
			JasperDesign load1 = JRXmlLoader.load(path + "reports/printinfoforcard.jrxml");
			JasperDesign load2 = JRXmlLoader.load(path + "reports/printinoforcard.jrxml");
			/*获取配置文件ColumnHeader区域部分*/
			JRDesignBand columnHeadBand1 = (JRDesignBand) load1.getColumnHeader();
			JRDesignBand columnHeadBand2 = (JRDesignBand) load2.getColumnHeader();
			/*设置ColumnHeader部分尺寸*/
			/*查询当天接种疫苗*/
			map.put("child_id", childObj.get("childcode").toString());
			map.put("version", viewname);
			if (list != null) {
				list.clear();
			}
			if (Integer.valueOf((String) map.get("num")) == 1) {
				list = tChildInoculateDao.queryAllInoculation(map.get("chilCode").toString());
			} else {
				list = tChildInoculateDao.queryCurrentDayInoculation(map.get("chilCode").toString());
			}

			List<Map<String, Object>> newList1 = new ArrayList<>();
			List<Map<String, Object>> newList2 = new ArrayList<>();
			Integer ino_Isprint = 0;//可打印；isprint = 1：不可打印
			XMLHelper xhp = new XMLHelper();
			Document doc = null;
			/*获取配置文件InoculateCard.properties*/
			//ResourceBundle resource = ResourceBundle.getBundle("jaserreport/InoculateCard");
			StringBuffer XmlPath = new StringBuffer(path + "/WEB-INF/classes/jaserreport/");
			/*读取配置文件InoculateCard.properties的内容*/
			XmlPath.append(viewname);
			XmlPath.append(".xml");
			doc = xhp.load(XmlPath.toString());

			/*遍历查询出的当天接种疫苗*/
			for (Map<String, Object> mapin : list) {
				boolean flag = false;
				/*遍历noids数组，与页面传过来的vaccinecode值比较,与数组中相同的疫苗不打印*/
				if (flag) {
					continue;
				}
				ino_Isprint = 0;
				mapin.put("is_print", ino_Isprint);
				String bio_name = (String) mapin.get("bio_name");
				Object agent_times = mapin.get("inoc_time");
				if ("甲肝减毒活疫苗".equals(bio_name) || "甲肝减毒活疫苗(冻干)".equals(bio_name)) {
					agent_times = 1;
				}
				Integer arg_no = 1;
				if (agent_times != null) {
					arg_no = Integer.valueOf(agent_times.toString());
				}
				//获取数值位置
				//获取InoculateCardPrinterModelNew.xml配置文件信息（数值位置）
				Map<String, Object> getloc = getLocFromInoculateCardXML(doc, bio_name, ino_Isprint);
				if (getloc != null) {
					String bacterinclassorder = getloc.get("bacterinclassorder").toString();
					String page = getloc.get("page").toString();
					Object doesNo = getloc.get("dose_no");
					Integer does_no = Integer.valueOf(doesNo == null ? "0" : doesNo.toString());
					if (arg_no > does_no) {
						continue;
					}
					Object[] maparr = new Object[]{
							getloc.get("inoculate_date"),
							getloc.get("text"),
							getloc.get("batchnum"),
							getloc.get("expiration_date"),
							getloc.get("factory_name"),
							getloc.get("doctor")
					};
					//动态创建表
					if(Integer.valueOf((String) map.get("num")) != 5) {
						for (int i = 0; i < maparr.length; i++) {
							Map<String, Object> objectMap = (Map<String, Object>) maparr[i];
							JRDesignField field = new JRDesignField();
							String fieldname = null;
							/*在查出的结果map中获取接种本上对应的疫苗*/
							Object name = mapin.get(objectMap.get("name").toString());
							try {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + arg_no;
								field.setName(fieldname);
							} catch (Exception e) {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + (arg_no + 1);
								field.setName(fieldname);
								e.printStackTrace();
							}
							//System.out.println("name====" + fieldname + "<><>" + name + "<><>" + bio_name);
							field.setValueClass(java.lang.String.class);
							/*设置文本位置*/
							JRDesignTextField textField = new JRDesignTextField();
							textField.setStretchWithOverflow(true);
							Integer x = Integer.valueOf(objectMap.get("x").toString());
							textField.setX(x);
							Integer y = 0;
							y = Integer.valueOf(objectMap.get("y").toString()) + (arg_no - 1) * 15;
							textField.setY(y);
							textField.setHeight(Integer.valueOf(objectMap.get("height").toString()));
							textField.setWidth(Integer.valueOf(objectMap.get("width").toString()));
							textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
							textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
							textField.setFontName("宋体");
							try {
								float fontSize = Float.valueOf((String) map.get("fontSize"));
								textField.setFontSize(fontSize + 0.0f);
							}catch (Exception e){
								textField.setFontSize(8.0f);
							}
							//textField.setFontSize(8.0f);
							textField.setPdfEmbedded(true);
							textField.setPdfFontName("STSong-Light");
							textField.setPdfEncoding("UniGB-UCS2-H");
							textField.setBlankWhenNull(true);
							JRDesignExpression expression = new JRDesignExpression();
							//expression.setValueClass(java.lang.String.class);
							if ("inoculate_date".equals(objectMap.get("name").toString())) {
								expression.setText("$F{" + fieldname + "}.substring(0,11)");
							} else {
								expression.setText("$F{" + fieldname + "}");
							}
							textField.setExpression(expression);
							if ("1".equalsIgnoreCase(page)) {
								try {
									columnHeadBand1.addElement(textField);
									load1.addField(field);
								} catch (Exception e) {
									e.printStackTrace();
								}
								data1.put(fieldname, name);//data1
							} else if ("2".equalsIgnoreCase(page)) {
								try {
									columnHeadBand2.addElement(textField);
									load2.addField(field);
								} catch (Exception e) {
									e.printStackTrace();
								}
								data2.put(fieldname, name);
							} else {


							}
						}
					}
				}
			}
			List<ExporterInputItem> items = new ArrayList<ExporterInputItem>();
			// newList1.add(data1);
			//设置身份证打印格式
			String str = "";
			String str1 = " ";
			Object IDENTITYCARD = childObj.remove("IDENTITYCARD");
			if (IDENTITYCARD != null) {
				String IDENTITYCARD1 = IDENTITYCARD.toString();
				for (int i = 0; i < IDENTITYCARD1.length(); i++) {
					str += IDENTITYCARD1.substring(i, i + 1).concat("  ");
					if (i == 7) {
						str += str1.concat(" ");
					}
					if (i == 14) {
						str += str1.concat(" ");
					}
				}
			}
			childObj.put("IDENTITYCARD", str);
			//如果儿童信息打印状态为0，则儿童信息一起打印
			if (Integer.valueOf((String) map.get("num")) == 1 || Integer.valueOf((String) map.get("num")) == 5) {
				/*if(isprint == 0){*/
				for (String key : childObj.keySet()) {
					if (childObj.get(key) != null) {
						data1.put(key, childObj.get(key).toString());
					}
				}
			}
			newList1.add(data1);
			JasperReport jasperReport1 = JasperCompileManager.compileReport(load1);
			JRDataSource dataSource1 = new JRBeanCollectionDataSource(newList1, true);
			//JRDataSource dataSource1 = new JRMapArrayDataSource(newList1, true);
			JasperPrint print1 = JasperFillManager.fillReport(jasperReport1, params, dataSource1);
			ExporterInputItem exporterInput1 = new SimpleExporterInputItem(print1);

			newList2.add(data2);
			JasperReport jasperReport2 = JasperCompileManager.compileReport(load2);
			JRDataSource dataSource2 = new JRBeanCollectionDataSource(newList2, true);
			JasperPrint print2 = JasperFillManager.fillReport(jasperReport2, params, dataSource2);
			ExporterInputItem exporterInput2 = new SimpleExporterInputItem(print2);

			items.add(exporterInput1);
			if (!data2.isEmpty()) {
				items.add(exporterInput2);
			}
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
           /* response.setHeader("Content-Disposition", "inline; filename=\""
                    + new String(viewname.getBytes("UTF-8")) + ".pdf" + "\"");*/
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf");
			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(items);
			//exporter.setParameter(JRExporterParameter.PAGE_INDEX,2);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();
			map.clear();
			map.put("createCardTime",sdf.format(new Date()));
			if(childObj.get("chil_createman")==null || "".equals(childObj.get("chil_createman"))){
				map.put("chilCreateman",ShiroUtils.getUserEntity().getRealName());
			}
			map.put("chilCode",chilCode);
			if(childObj.get("create_card_time")==null || "".equals(childObj.get("create_card_time"))){
				tChildInfoDao.update(map);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 打印当天接种记录和所有接种记录
	 * @author 饶士培
	 * @date 2018-08-27
	 * @param map
	 * @param response
	 * @return
	 */
	@Override
	public Map queryInoculationForPrint(Map<String, Object> map, HttpServletResponse response) {
		String viewname = "ChildInoculate";
		String path = map.get("path").toString();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> data1 = new HashMap<String, Object>();
			Map<String, Object> data2 = new HashMap<String, Object>();
			JasperDesign load1 = JRXmlLoader.load(path + "reports/ChildInoculate.jrxml");
			JasperDesign load2 = JRXmlLoader.load(path + "reports/ChildInoculate2.jrxml");
			/*获取配置文件ColumnHeader区域部分*/
			JRDesignBand columnHeadBand1 = (JRDesignBand) load1.getColumnHeader();
			JRDesignBand columnHeadBand2 = (JRDesignBand) load2.getColumnHeader();
			/*设置ColumnHeader部分尺寸*/
			columnHeadBand1.setHeight(600);
			columnHeadBand2.setHeight(600);
			/*查询当天接种记录或者所有接种记录*/
			List<Map<String,Object>> list = null;
			if (Integer.valueOf((String) map.get("num")) == 1) {
				list = tChildInoculateDao.queryAllInoculation(map.get("chilCode").toString());
			}else if(Integer.valueOf((String) map.get("num")) == 3){
				list = tChildInoculateDao.queryCurrentDayWaitInocBioName(map.get("chilCode").toString());
			}else{
				list = tChildInoculateDao.queryCurrentDayInoculation(map.get("chilCode").toString());
			}

			List<Map<String, Object>> newList1 = new ArrayList<>();
			List<Map<String, Object>> newList2 = new ArrayList<>();
			Integer isprint = 0;//可打印；isprint = 1：不可打印
			XMLHelper xhp = new XMLHelper();
			Document doc = null;
			/*获取配置文件InoculateCard.properties*/
			// ResourceBundle resource = ResourceBundle.getBundle("jaserreport/InoculateCard");
			StringBuffer XmlPath = new StringBuffer(path + "/WEB-INF/classes/jaserreport/");
			/*读取配置文件InoculateCard.properties的内容*/
			try {
				XmlPath.append(map.get("version"));
				XmlPath.append(".xml");
				doc = xhp.load(XmlPath.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			/*遍历查询出的当天接种疫苗*/
			for (Map<String,Object> ino : list) {
				String bio_name = (String)ino.get("bio_name");
				Object agent_times = ino.get("inoc_time");
				if("甲肝减毒活疫苗".equals(bio_name) || "甲肝减毒活疫苗(冻干)".equals(bio_name)){
					agent_times = 1;
				}
				Integer arg_no = 1;
				if (agent_times != null) {
					arg_no = Integer.valueOf(agent_times.toString());
				}
				//获取数值位置
				//获取InoculateCardPrinterModelNew.xml配置文件信息（数值位置）
				Map<String, Object> getloc = getLocFromInoculateCardXML(doc, bio_name, isprint);
				if (getloc != null) {
					String bacterinclassorder = getloc.get("bacterinclassorder").toString();
					String page = getloc.get("page").toString();
					Object doesNo = getloc.get("dose_no");
					Integer does_no = Integer.valueOf(doesNo == null ? "0" : doesNo.toString());
					if (arg_no > does_no) {
						continue;
					}
					Object[] maparr = new Object[]{
							getloc.get("inoculate_date"),
							getloc.get("text"),
							getloc.get("batchnum"),
							getloc.get("factory_name"),
							getloc.get("expiration_date"),
							getloc.get("departname"),
							getloc.get("doctor")
					};
					//动态创建表
					for (int i = 0; i < maparr.length; i++) {
						if(maparr[i]==null){
							continue;
						}
						Map<String, Object> objectMap = (Map<String, Object>) maparr[i];
						JRDesignField field = new JRDesignField();
						String fieldname = null;
						/*在查出的结果map中获取接种本上对应的疫苗*/
						Object name = ino.get(objectMap.get("name").toString());
						try {
							fieldname = objectMap.get("name").toString() + page + bacterinclassorder + arg_no;
							field.setName(fieldname);
						} catch (Exception e) {
							fieldname = objectMap.get("name").toString() + page + bacterinclassorder + (arg_no + 1);
							field.setName(fieldname);
						}
						//System.out.println("name====" + fieldname + "<><>" + name + "<><>" + bio_name);
						field.setValueClass(java.lang.String.class);
						/*设置文本位置*/
						JRDesignTextField textField = new JRDesignTextField();
						textField.setStretchWithOverflow(true);
						Integer x = Integer.valueOf(objectMap.get("x").toString());
						textField.setX(x);
						Integer y = 0;
						y = Integer.valueOf(objectMap.get("y").toString()) + (arg_no - 1) * 15;
						textField.setY(y);
						textField.setHeight(Integer.valueOf(objectMap.get("height").toString()));
						textField.setWidth(Integer.valueOf(objectMap.get("width").toString()));
						textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
						textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
						textField.setFontName("宋体");
						try {
							float fontSize = Float.valueOf((String) map.get("fontSize"));
							textField.setFontSize(fontSize + 0.0f);
						}catch (Exception e){
							textField.setFontSize(8.0f);
						}
						//textField.setFontSize(8.0f);
						textField.setPdfEmbedded(true);
						textField.setPdfFontName("STSong-Light");
						textField.setPdfEncoding("UniGB-UCS2-H");
						textField.setBlankWhenNull(true);
						JRDesignExpression expression = new JRDesignExpression();
						//expression.setValueClass(java.lang.String.class);
						if ("inoculate_date".equals(objectMap.get("name").toString())) {
							expression.setText("$F{" + fieldname + "}.substring(0,11)");
						} else {
							expression.setText("$F{" + fieldname + "}");
						}
						textField.setExpression(expression);
						if ("1".equalsIgnoreCase(page)) {
							try {
								columnHeadBand1.addElement(textField);
								load1.addField(field);
							} catch (Exception e) {
							}
							data1.put(fieldname, name);
						} else if ("2".equalsIgnoreCase(page)) {
							try {
								columnHeadBand2.addElement(textField);
								load2.addField(field);
							} catch (Exception e) {
							}
							data2.put(fieldname, name);
						}else{

						}
					}
				}
			}
			List<ExporterInputItem> items = new ArrayList<ExporterInputItem>(2);
			newList1.add(data1);
			JasperReport jasperReport1 = JasperCompileManager.compileReport(load1);
			JRDataSource dataSource1 = new JRBeanCollectionDataSource(newList1, true);
			JasperPrint print1 = JasperFillManager.fillReport(jasperReport1, params, dataSource1);
			ExporterInputItem exporterInput1 = new SimpleExporterInputItem(print1);

			newList2.add(data2);
			JasperReport jasperReport2 = JasperCompileManager.compileReport(load2);
			JRDataSource dataSource2 = new JRBeanCollectionDataSource(newList2, true);
			JasperPrint print2 = JasperFillManager.fillReport(jasperReport2, params, dataSource2);
			ExporterInputItem exporterInput2 = new SimpleExporterInputItem(print2);
			if(!data1.isEmpty()) {
				items.add(exporterInput1);
			}
			if(!data2.isEmpty()){
				items.add(exporterInput2);
			}
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
           /* response.setHeader("Content-Disposition", "inline; filename=\""
                    + new String(viewname.getBytes("UTF-8")) + ".pdf" + "\"");*/
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf");
			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());

			ExporterInput exporterInput = new SimpleExporterInput(items);
			//exporter.setParameter(JRExporterParameter.PAGE_INDEX,2);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			if(items.size()==0){
				exporterOutput.close();
			}else{
				exporter.exportReport();
				exporterOutput.close();
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 打印二类苗接种记录
	 * @author 饶士培
	 * @date 2018-10-30
	 * @param map
	 * @param response
	 * @return
	 */
	@Override
	public Map querySecondVaccInocForPrint(Map<String, Object> map, HttpServletResponse response) {
		String inoculate_ids = map.get("inoculate_id").toString();
		String path = map.get("path").toString();
		int lineCount = Integer.parseInt(map.get("lineCount").toString());
		String inoculate_id[] = new String[6];
		if(inoculate_ids!=null){
			for (int i = 0; i < inoculate_ids.length();i++) {
				inoculate_id = inoculate_ids.split(",");
			}
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String xmlName = "printInoculateByLineCount";
			String printType = map.get("printType").toString();
			if(!"2".equals(printType)) {
				String viewname = "ChildInoculate";
				params = new HashMap<String, Object>();
				Map<String, Object> data1 = new HashMap<String, Object>();
				/*加载reports配置文件*/
				JasperDesign load1 = JRXmlLoader.load(path + "reports/ChildInoculate.jrxml");
				JasperDesign load2 = JRXmlLoader.load(path + "reports/ChildInoculate2.jrxml");
				/*获取配置文件ColumnHeader区域部分*/
				JRDesignBand columnHeadBand1 = (JRDesignBand) load1.getColumnHeader();
				JRDesignBand columnHeadBand2 = (JRDesignBand) load2.getColumnHeader();
				/*设置ColumnHeader部分尺寸*/
				/*查询当天接种疫苗*/
				map.put("child_id", map.get("child_id").toString());
				map.put("version", xmlName);
				//通过接种记录id查询接种记录
				List<Map<String, Object>> list = new ArrayList<>();
				for (int i = 0; i < inoculate_id.length; i++) {
					if ("".equals(inoculate_id[i])) {
						continue;
					}
					Map<String, Object> list1 = new HashMap();
					// Map<String,Object> mapList = new HashMap();
					map.put("inoculate_id", inoculate_id[i]);
					if("register".equals(map.get("plag").toString())) {
						list1 = tChildInoculateDao.queryWaitInocVaccById(map);
					}else{
						list1 = tChildInoculateDao.queryInoculationById(map);
					}
					list.add(list1);

				}

				List<Map<String, Object>> newList1 = new ArrayList<>();
				List<Map<String, Object>> newList2 = new ArrayList<>();
				Integer ino_Isprint = 0;//可打印；isprint = 1：不可打印
				XMLHelper xhp = new XMLHelper();
				Document doc = null;
				/*查找具体xml文件*/
				StringBuffer XmlPath = new StringBuffer(path + "/WEB-INF/classes/jaserreport/");
				XmlPath.append(map.get("version"));
				XmlPath.append(".xml");
				doc = xhp.load(XmlPath.toString());
				/*遍历查询出的接种疫苗*/
				for (Map<String, Object> mapin : list) {
					boolean flag = false;

					if (flag) {
						continue;
					}
					if (Integer.valueOf((String) map.get("num")) == 1) {
						ino_Isprint = 0;
						mapin.put("is_print", ino_Isprint);
					}
					//String bio_name = (String) mapin.get("bio_name");
					String bio_name = "bioName";
					Object agent_times = mapin.get("agent_times");
					Integer arg_no = 1;
					if (agent_times != null) {
						arg_no = Integer.valueOf(agent_times.toString());
					}
					//获取数值位置
					//获取InoculateCardPrinterModelNew.xml配置文件信息（数值位置）
					Map<String, Object> getloc = getLocFromInoculateCardXML(doc, bio_name, ino_Isprint);
					if (getloc != null) {
						String bacterinclassorder = getloc.get("bacterinclassorder").toString();
						String page = getloc.get("page").toString();
						Object doesNo = getloc.get("dose_no");
						Integer does_no = Integer.valueOf(doesNo == null ? "0" : doesNo.toString());
						if (arg_no > does_no) {
							continue;
						}
						Object[] maparr = new Object[]{
								getloc.get("bio_name"),
								getloc.get("inoculate_date"),
								getloc.get("text"),
								getloc.get("batchnum"),
								getloc.get("factory_name"),
								getloc.get("departname"),
								getloc.get("doctor")
						};
						//动态创建表
						for (int i = 0; i < maparr.length; i++) {
							Map<String, Object> objectMap = (Map<String, Object>) maparr[i];
							JRDesignField field = new JRDesignField();
							String fieldname = null;
							/*在查出的结果map中获取接种本上对应的疫苗*/
							Object name = mapin.get(objectMap.get("name").toString());
							try {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + arg_no;
								field.setName(fieldname);
							} catch (Exception e) {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + (arg_no + 1);
								field.setName(fieldname);
							}
							field.setValueClass(java.lang.String.class);
							/*设置文本位置*/
							JRDesignTextField textField = new JRDesignTextField();
							textField.setStretchWithOverflow(true);
							Integer x = Integer.valueOf(objectMap.get("x").toString());
							textField.setX(x);
							Integer y = 0;
							y = Integer.valueOf(objectMap.get("y").toString()) + (lineCount - 1) * 15+(arg_no - 1) * 15;

							textField.setY(y);
							textField.setHeight(Integer.valueOf(objectMap.get("height").toString()));
							textField.setWidth(Integer.valueOf(objectMap.get("width").toString()));
							textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
							textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
							textField.setFontName("宋体");
							try {
								float fontSize = Float.valueOf((String) map.get("fontSize"));
								textField.setFontSize(fontSize + 0.0f);
							}catch (Exception e){
								textField.setFontSize(8.0f);
							}
							//textField.setFontSize(8.0f);
							textField.setPdfEmbedded(true);
							textField.setPdfFontName("STSong-Light");
							textField.setPdfEncoding("UniGB-UCS2-H");
							textField.setBlankWhenNull(true);
							JRDesignExpression expression = new JRDesignExpression();
							//expression.setValueClass(java.lang.String.class);
							if ("inoculate_date".equals(objectMap.get("name").toString())) {
								expression.setText("$F{" + fieldname + "}.substring(0,11)");
							} else {
								expression.setText("$F{" + fieldname + "}");
							}
							textField.setExpression(expression);
							if ("1".equalsIgnoreCase(page)) {
								try {
									columnHeadBand1.addElement(textField);
									load1.addField(field);
								} catch (Exception e) {
								}
								data1.put(fieldname, name);//data1
							}
						}
					}
				}
				List<ExporterInputItem> items = new ArrayList<ExporterInputItem>();
				// newList1.add(data1);
				newList1.add(data1);
				JasperReport jasperReport1 = JasperCompileManager.compileReport(load1);
				JRDataSource dataSource1 = new JRBeanCollectionDataSource(newList1, true);
				//JRDataSource dataSource1 = new JRMapArrayDataSource(newList1, true);
				JasperPrint print1 = JasperFillManager.fillReport(jasperReport1, params, dataSource1);
				ExporterInputItem exporterInput1 = new SimpleExporterInputItem(print1);
				items.add(exporterInput1);
				response.setContentType("application/pdf");
				response.setCharacterEncoding("utf-8");
           /* response.setHeader("Content-Disposition", "inline; filename=\""
                    + new String(viewname.getBytes("UTF-8")) + ".pdf" + "\"");*/
				response.setHeader("Content-Disposition", "inline; filename="
						+ new String(viewname.getBytes("UTF-8")) + ".pdf");
				JRAbstractExporter exporter = new JRPdfExporter();
				OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
						response.getOutputStream());
				ExporterInput exporterInput = new SimpleExporterInput(items);
				//exporter.setParameter(JRExporterParameter.PAGE_INDEX,2);
				exporter.setExporterOutput(exporterOutput);
				// 设置输入项
				exporter.setExporterInput(exporterInput);
				exporter.exportReport();
				exporterOutput.close();
			}
			if ("2".equals(printType)) {
				xmlName = "printInoculateCardByLineCount";
				//xmlName = "selfdefinecardprint";
				String viewname = "ChildInoculate";
				params = new HashMap<String, Object>();
				Map<String, Object> data1 = new HashMap<String, Object>();
				Map<String, Object> data2 = new HashMap<String, Object>();
				/*加载reports配置文件*/
				//JasperDesign load1 = JRXmlLoader.load(path + "reports/printinfoforcard.jrxml");
				JasperDesign load1 = JRXmlLoader.load(path + "reports/printinoforcard.jrxml");
				JasperDesign  load2 = JRXmlLoader.load(path + "reports/printinoforcard.jrxml");
				/*获取配置文件ColumnHeader区域部分*/
				JRDesignBand columnHeadBand1 = (JRDesignBand) load1.getColumnHeader();
				JRDesignBand columnHeadBand2 = (JRDesignBand) load2.getColumnHeader();
				/*设置ColumnHeader部分尺寸*/
				/*查询当天接种疫苗*/
				map.put("child_id", map.get("child_id").toString());
				map.put("version", xmlName);
				List<Map<String, Object>> list = new ArrayList<>();
				for (int i = 0; i < inoculate_id.length; i++) {
					if ("".equals(inoculate_id[i])) {
						continue;
					}
					Map<String, Object> list1 = new HashMap();
					// Map<String,Object> mapList = new HashMap();
					map.put("inoculate_id", inoculate_id[i]);
					//map.put("agent_times", agent_timess[i]);
					list1 = tChildInoculateDao.queryInoculationById(map);
					//mapList.put("list1",list1);
					list.add(list1);

				}

				List<Map<String, Object>> newList1 = new ArrayList<>();
				List<Map<String, Object>> newList2 = new ArrayList<>();
				Integer ino_Isprint = 0;//可打印；isprint = 1：不可打印
				XMLHelper xhp = new XMLHelper();
				Document doc = null;
				/*获取配置文件InoculateCard.properties*/
				// ResourceBundle resource = ResourceBundle.getBundle("jaserreport/InoculateCard");
				StringBuffer XmlPath = new StringBuffer(path + "/WEB-INF/classes/jaserreport/");
				try {
					XmlPath.append(xmlName);
					XmlPath.append(".xml");
					doc = xhp.load(XmlPath.toString());

				} catch (Exception e) {
					e.printStackTrace();
				}
				/*遍历查询出的当天接种疫苗*/
				for (Map<String, Object> mapin : list) {


					if (Integer.valueOf((String) map.get("num")) == 1) {
						ino_Isprint = 0;
						mapin.put("is_print", ino_Isprint);
					}
					//String bio_name = (String) mapin.get("bio_name");
					String bio_name = "bioName";
					Object agent_times = mapin.get("agent_times");

					Integer arg_no = 1;
					if (agent_times != null) {
						arg_no = Integer.valueOf(agent_times.toString());
					}
					//获取数值位置
					//获取InoculateCardPrinterModelNew.xml配置文件信息（数值位置）
					Map<String, Object> getloc = getLocFromInoculateCardXML(doc, bio_name, ino_Isprint);
					if (getloc != null) {
						String bacterinclassorder = getloc.get("bacterinclassorder").toString();
						String page = getloc.get("page").toString();
						Object doesNo = getloc.get("dose_no");
						Integer does_no = Integer.valueOf(doesNo == null ? "0" : doesNo.toString());
						if (arg_no > does_no) {
							continue;
						}
						Object[] maparr = new Object[]{
								getloc.get("bio_name"),
								getloc.get("inoculate_date"),
								getloc.get("text"),
								getloc.get("batchnum"),
								getloc.get("expiration_date"),
								getloc.get("factory_name"),
								getloc.get("doctor")
						};
						//动态创建表
						for (int i = 0; i < maparr.length; i++) {
							Map<String, Object> objectMap = (Map<String, Object>) maparr[i];
							JRDesignField field = new JRDesignField();
							String fieldname = null;
							/*在查出的结果map中获取接种本上对应的疫苗*/
							Object name = mapin.get(objectMap.get("name").toString());
							try {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + arg_no;
								field.setName(fieldname);
							} catch (Exception e) {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + (arg_no + 1);
								field.setName(fieldname);
							}

							field.setValueClass(java.lang.String.class);
							/*设置文本位置*/
							JRDesignTextField textField = new JRDesignTextField();
							textField.setStretchWithOverflow(true);
							Integer x = Integer.valueOf(objectMap.get("x").toString());
							textField.setX(x);
							Integer y = 0;
							y = Integer.valueOf(objectMap.get("y").toString()) + (lineCount - 1) * 15+(arg_no - 1) * 15;
							textField.setY(y);
							textField.setHeight(Integer.valueOf(objectMap.get("height").toString()));
							textField.setWidth(Integer.valueOf(objectMap.get("width").toString()));
							textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
							textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
							textField.setFontName("宋体");
							try {
								float fontSize = Float.valueOf((String) map.get("fontSize"));
								textField.setFontSize(fontSize + 0.0f);
							}catch (Exception e){
								textField.setFontSize(8.0f);
							}
							//textField.setFontSize(8.0f);
							textField.setPdfEmbedded(true);
							textField.setPdfFontName("STSong-Light");
							textField.setPdfEncoding("UniGB-UCS2-H");
							textField.setBlankWhenNull(true);
							JRDesignExpression expression = new JRDesignExpression();
							//expression.setValueClass(java.lang.String.class);
							if ("inoculate_date".equals(objectMap.get("name").toString())) {
								expression.setText("$F{" + fieldname + "}.substring(0,11)");
							} else {
								expression.setText("$F{" + fieldname + "}");
							}
							textField.setExpression(expression);
							if ("2".equalsIgnoreCase(page)) {
								try {
									columnHeadBand1.addElement(textField);
									load1.addField(field);
								} catch (Exception e) {
								}
								data1.put(fieldname, name);//data1
							}
						}
					}
				}

				List<ExporterInputItem> items = new ArrayList<ExporterInputItem>();
				// newList1.add(data1);

				newList1.add(data1);
				JasperReport jasperReport1 = JasperCompileManager.compileReport(load1);
				JRDataSource dataSource1 = new JRBeanCollectionDataSource(newList1, true);
				//JRDataSource dataSource1 = new JRMapArrayDataSource(newList1, true);
				JasperPrint print1 = JasperFillManager.fillReport(jasperReport1, params, dataSource1);
				ExporterInputItem exporterInput1 = new SimpleExporterInputItem(print1);

				items.add(exporterInput1);

				response.setContentType("application/pdf");
				response.setCharacterEncoding("utf-8");
           /* response.setHeader("Content-Disposition", "inline; filename=\""
                    + new String(viewname.getBytes("UTF-8")) + ".pdf" + "\"");*/
				response.setHeader("Content-Disposition", "inline; filename="
						+ new String(viewname.getBytes("UTF-8")) + ".pdf");
				JRAbstractExporter exporter = new JRPdfExporter();
				OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
						response.getOutputStream());
				ExporterInput exporterInput = new SimpleExporterInput(items);
				//exporter.setParameter(JRExporterParameter.PAGE_INDEX,2);
				exporter.setExporterOutput(exporterOutput);
				// 设置输入项
				exporter.setExporterInput(exporterInput);
				exporter.exportReport();
				exporterOutput.close();

			}
		} catch (JRException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	@Override
	public List<SysDepartEntity> queryList(Map<String, Object> map) {
		return null;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return 0;
	}

	@Override
	public void save(SysDepartEntity sysDepart) {

	}

	@Override
	public void update(SysDepartEntity sysDepart) {

	}

	@Override
	public void delete(String id) {

	}

	@Override
	public void deleteBatch(String[] ids) {

	}

	public Map<String, Object> getLocFromInoculateCardXML(Document doc, String bio_name, int is_print) {
		if(bio_name==null){
			return null;
		}
		Map<String, Object> result = null;
		/*遍历获取InoculateCardPrinterModelNew.XML文件元素*/
		Element rootElement = doc.getRootElement();
		Iterator iterator = rootElement.elementIterator();
		while (iterator.hasNext()) {
			Element next = (Element) iterator.next();
			/*获取疫苗名称*/
			Element bacterinclasspattern = next.element("bacterinclasspattern");
			if (bacterinclasspattern != null) {
				String reg = bacterinclasspattern.getTextTrim();
				Pattern compile = Pattern.compile(reg);
				if (compile.matcher(bio_name).matches()) {
					result = new HashMap<>();
					result.put("dose_no", Integer.valueOf(next.element("dose_no").getTextTrim()));
					result.put("page", Integer.valueOf(next.element("page").getTextTrim()));
					result.put("bacterinclassorder", Integer.valueOf(next.element("bacterinclassorder").getTextTrim()));
					Iterator it = next.element("showdatas").elementIterator();
					while (it.hasNext()) {
						Map<String, Object> data = new HashMap<>();
						Element nexts = (Element) it.next();
						data.put("name", nexts.element("name").getTextTrim());
						data.put("x", nexts.element("x").getTextTrim());
						data.put("y", nexts.element("y").getTextTrim());
						data.put("width", nexts.element("width").getTextTrim());
						data.put("height", nexts.element("height").getTextTrim());
						result.put(nexts.element("name").getTextTrim(), data);
					}
		/*if (is_print == 0) {
		return result;
		} else {
		return null;
		}*/
				}
			}
		}
		return result;
	}

	@Override
	public Map queryInoculationByIdForPrint(Map<String, Object> map, HttpServletResponse response) {
		String inoculate_ids = null;
		String path = map.get("path").toString();
		String inoculate_id[] = null;
		if(map.get("inoculate_id")!=null){
			inoculate_ids = map.get("inoculate_id").toString();
			inoculate_id = new String[40];
			if(inoculate_ids!=null){
				for (int i = 0; i < inoculate_ids.length();i++) {
					inoculate_id = inoculate_ids.split(",");
				}
			}
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String xmlName = map.get("xmlName").toString();
			String printType = map.get("printType").toString();
			if(!"2".equals(printType)) {
				String viewname = "ChildInoculate";
				params = new HashMap<String, Object>();
				Map<String, Object> data1 = new HashMap<String, Object>();
				Map<String, Object> data2 = new HashMap<String, Object>();
				/*加载reports配置文件*/
				JasperDesign load1 = JRXmlLoader.load(path + "reports/ChildInoculate.jrxml");
				JasperDesign load2 = JRXmlLoader.load(path + "reports/ChildInoculate2.jrxml");
				/*获取配置文件ColumnHeader区域部分*/
				JRDesignBand columnHeadBand1 = (JRDesignBand) load1.getColumnHeader();
				JRDesignBand columnHeadBand2 = (JRDesignBand) load2.getColumnHeader();
				/*设置ColumnHeader部分尺寸*/
				/*查询当天接种疫苗*/
				map.put("child_id", map.get("child_id").toString());
				map.put("version", xmlName);
				//通过接种记录id查询接种记录
				List<Map<String, Object>> list = new ArrayList<>();
				for (int i = 0; i < inoculate_id.length; i++) {
					if ("".equals(inoculate_id[i])) {
						continue;
					}
					Map<String, Object> list1 = new HashMap();
					// Map<String,Object> mapList = new HashMap();
					map.put("inoculate_id", inoculate_id[i]);
					list1 = tChildInoculateDao.queryInoculationById(map);
					list.add(list1);

				}

				List<Map<String, Object>> newList1 = new ArrayList<>();
				List<Map<String, Object>> newList2 = new ArrayList<>();
				Integer ino_Isprint = 0;//可打印；isprint = 1：不可打印
				XMLHelper xhp = new XMLHelper();
				Document doc = null;
				/*查找具体xml文件*/
				StringBuffer XmlPath = new StringBuffer(path + "/WEB-INF/classes/jaserreport/");
				XmlPath.append(map.get("version"));
				XmlPath.append(".xml");
				doc = xhp.load(XmlPath.toString());
				/*遍历查询出的接种疫苗*/
				for (Map<String, Object> mapin : list) {
					boolean flag = false;

					if (flag) {
						continue;
					}
					ino_Isprint = mapin.get("is_print") == null ? 0 : Integer.parseInt(mapin.get("is_print").toString());
					if (Integer.valueOf((String) map.get("num")) == 1) {
						ino_Isprint = 0;
						mapin.put("is_print", ino_Isprint);
					}
					String bio_name = (String) mapin.get("bio_name");
					Object agent_times = mapin.get("agent_times");
					if("甲肝减毒活疫苗".equals(bio_name) || "甲肝减毒活疫苗(冻干)".equals(bio_name)){
						agent_times = 1;
					}
					Integer arg_no = 1;
					if (agent_times != null) {
						arg_no = Integer.valueOf(agent_times.toString());
					}
					//获取数值位置
					//获取InoculateCardPrinterModelNew.xml配置文件信息（数值位置）
					Map<String, Object> getloc = getLocFromInoculateCardXML(doc, bio_name, ino_Isprint);
					if (getloc != null) {
						String bacterinclassorder = getloc.get("bacterinclassorder").toString();
						String page = getloc.get("page").toString();
						Object doesNo = getloc.get("dose_no");
						Integer does_no = Integer.valueOf(doesNo == null ? "0" : doesNo.toString());
						if (arg_no > does_no) {
							continue;
						}
						Object[] maparr = new Object[]{
								getloc.get("inoculate_date"),
								getloc.get("text"),
								getloc.get("batchnum"),
								getloc.get("factory_name"),
								getloc.get("departname"),
								getloc.get("doctor")
						};
						//动态创建表
						for (int i = 0; i < maparr.length; i++) {
							Map<String, Object> objectMap = (Map<String, Object>) maparr[i];
							JRDesignField field = new JRDesignField();
							String fieldname = null;
							/*在查出的结果map中获取接种本上对应的疫苗*/
							Object name = mapin.get(objectMap.get("name").toString());
							try {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + arg_no;
								field.setName(fieldname);
							} catch (Exception e) {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + (arg_no + 1);
								field.setName(fieldname);
							}
							field.setValueClass(java.lang.String.class);
							/*设置文本位置*/
							JRDesignTextField textField = new JRDesignTextField();
							textField.setStretchWithOverflow(true);
							Integer x = Integer.valueOf(objectMap.get("x").toString());
							textField.setX(x);
							Integer y = 0;
							y = Integer.valueOf(objectMap.get("y").toString()) + (arg_no - 1) * 15;
							textField.setY(y);
							textField.setHeight(Integer.valueOf(objectMap.get("height").toString()));
							textField.setWidth(Integer.valueOf(objectMap.get("width").toString()));
							textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
							textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
							textField.setFontName("宋体");
							try {
								float fontSize = Float.valueOf((String) map.get("fontSize"));
								textField.setFontSize(fontSize + 0.0f);
							}catch (Exception e){
								textField.setFontSize(8.0f);
							}
							//textField.setFontSize(8.0f);
							textField.setPdfEmbedded(true);
							textField.setPdfFontName("STSong-Light");
							textField.setPdfEncoding("UniGB-UCS2-H");
							textField.setBlankWhenNull(true);
							JRDesignExpression expression = new JRDesignExpression();
							//expression.setValueClass(java.lang.String.class);
							if ("inoculate_date".equals(objectMap.get("name").toString())) {
								expression.setText("$F{" + fieldname + "}.substring(0,11)");
							} else {
								expression.setText("$F{" + fieldname + "}");
							}
							textField.setExpression(expression);
							if ("1".equalsIgnoreCase(page)) {
								try {
									columnHeadBand1.addElement(textField);
									load1.addField(field);
								} catch (Exception e) {
								}
								data1.put(fieldname, name);//data1
							} else if ("2".equalsIgnoreCase(page)) {
								try {
									columnHeadBand2.addElement(textField);
									load2.addField(field);
								} catch (Exception e) {
								}
								data2.put(fieldname, name);
							} else {

							}
						}
					}
				}
				List<ExporterInputItem> items = new ArrayList<ExporterInputItem>();
				// newList1.add(data1);
				newList1.add(data1);
				JasperReport jasperReport1 = JasperCompileManager.compileReport(load1);
				JRDataSource dataSource1 = new JRBeanCollectionDataSource(newList1, true);
				//JRDataSource dataSource1 = new JRMapArrayDataSource(newList1, true);
				JasperPrint print1 = JasperFillManager.fillReport(jasperReport1, params, dataSource1);
				ExporterInputItem exporterInput1 = new SimpleExporterInputItem(print1);

				newList2.add(data2);
				JasperReport jasperReport2 = JasperCompileManager.compileReport(load2);
				JRDataSource dataSource2 = new JRBeanCollectionDataSource(newList2, true);
				JasperPrint print2 = JasperFillManager.fillReport(jasperReport2, params, dataSource2);
				ExporterInputItem exporterInput2 = new SimpleExporterInputItem(print2);
				items.add(exporterInput1);
				if(!data2.isEmpty()){
					items.add(exporterInput2);
				}
				response.setContentType("application/pdf");
				response.setCharacterEncoding("utf-8");
           /* response.setHeader("Content-Disposition", "inline; filename=\""
                    + new String(viewname.getBytes("UTF-8")) + ".pdf" + "\"");*/
				response.setHeader("Content-Disposition", "inline; filename="
						+ new String(viewname.getBytes("UTF-8")) + ".pdf");
				JRAbstractExporter exporter = new JRPdfExporter();
				OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
						response.getOutputStream());
				ExporterInput exporterInput = new SimpleExporterInput(items);
				//exporter.setParameter(JRExporterParameter.PAGE_INDEX,2);
				exporter.setExporterOutput(exporterOutput);
				// 设置输入项
				exporter.setExporterInput(exporterInput);
				exporter.exportReport();
				exporterOutput.close();
			}
			if ("2".equals(printType)) {
				//xmlName = "selfdefinecardprint";
				String viewname = "ChildInoculate";
				params = new HashMap<String, Object>();
				Map<String, Object> data1 = new HashMap<String, Object>();
				Map<String, Object> data2 = new HashMap<String, Object>();
				/*加载reports配置文件*/
				JasperDesign load1 = JRXmlLoader.load(path + "reports/printinfoforcard.jrxml");
				JasperDesign  load2 = JRXmlLoader.load(path + "reports/printinoforcard.jrxml");
				/*获取配置文件ColumnHeader区域部分*/
				JRDesignBand columnHeadBand1 = (JRDesignBand) load1.getColumnHeader();
				JRDesignBand columnHeadBand2 = (JRDesignBand) load2.getColumnHeader();
				/*设置ColumnHeader部分尺寸*/
				/*查询当天接种疫苗*/
				map.put("child_id", map.get("child_id").toString());
				map.put("version", xmlName);
				List<Map<String, Object>> list = new ArrayList<>();
				if(inoculate_id == null || inoculate_id.length ==0 ) {
					list = tChildInoculateDao.queryCurrentDayInoculation(map.get("child_id").toString());
				}else {
					for (int i = 0; i < inoculate_id.length; i++) {
						if ("".equals(inoculate_id[i])) {
							continue;
						}
						Map<String, Object> list1 = new HashMap();
						// Map<String,Object> mapList = new HashMap();
						map.put("inoculate_id", inoculate_id[i]);
						//map.put("agent_times", agent_timess[i]);
						list1 = tChildInoculateDao.queryInoculationById(map);
						//mapList.put("list1",list1);
						list.add(list1);

					}
				}

				List<Map<String, Object>> newList1 = new ArrayList<>();
				List<Map<String, Object>> newList2 = new ArrayList<>();
				Integer ino_Isprint = 0;//可打印；isprint = 1：不可打印
				XMLHelper xhp = new XMLHelper();
				Document doc = null;
				/*获取配置文件InoculateCard.properties*/
				// ResourceBundle resource = ResourceBundle.getBundle("jaserreport/InoculateCard");
				StringBuffer XmlPath = new StringBuffer(path + "/WEB-INF/classes/jaserreport/");
				try {
					XmlPath.append(xmlName);
					XmlPath.append(".xml");
					doc = xhp.load(XmlPath.toString());

				} catch (Exception e) {
					e.printStackTrace();
				}
				/*遍历查询出的当天接种疫苗*/
				for (Map<String, Object> mapin : list) {
					boolean flag = false;
					/*遍历noids数组，与页面传过来的vaccinecode值比较,与数组中相同的疫苗不打印*/

					if (flag) {
						continue;
					}
					ino_Isprint = mapin.get("is_print") == null ? 0 : Integer.parseInt(mapin.get("is_print").toString());
					if (Integer.valueOf((String) map.get("num")) == 1) {
						ino_Isprint = 0;
						mapin.put("is_print", ino_Isprint);
					}
					String bio_name = (String) mapin.get("bio_name");
					Object agent_times = mapin.get("agent_times");
					if("甲肝减毒活疫苗".equals(bio_name) || "甲肝减毒活疫苗(冻干)".equals(bio_name)){
						agent_times = 1;
					}
					Integer arg_no = 1;
					if (agent_times != null) {
						arg_no = Integer.valueOf(agent_times.toString());
					}
					//获取数值位置
					//获取InoculateCardPrinterModelNew.xml配置文件信息（数值位置）
					Map<String, Object> getloc = getLocFromInoculateCardXML(doc, bio_name, ino_Isprint);
					if (getloc != null) {
						String bacterinclassorder = getloc.get("bacterinclassorder").toString();
						String page = getloc.get("page").toString();
						Object doesNo = getloc.get("dose_no");
						Integer does_no = Integer.valueOf(doesNo == null ? "0" : doesNo.toString());
						if (arg_no > does_no) {
							continue;
						}
						Object[] maparr = new Object[]{
								getloc.get("inoculate_date"),
								getloc.get("text"),
								getloc.get("batchnum"),
								getloc.get("expiration_date"),
								getloc.get("factory_name"),
								getloc.get("doctor")
						};
						//动态创建表
						for (int i = 0; i < maparr.length; i++) {
							Map<String, Object> objectMap = (Map<String, Object>) maparr[i];
							JRDesignField field = new JRDesignField();
							String fieldname = null;
							/*在查出的结果map中获取接种本上对应的疫苗*/
							Object name = mapin.get(objectMap.get("name").toString());
							try {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + arg_no;
								field.setName(fieldname);
							} catch (Exception e) {
								fieldname = objectMap.get("name").toString() + page + bacterinclassorder + (arg_no + 1);
								field.setName(fieldname);
							}

							field.setValueClass(java.lang.String.class);
							/*设置文本位置*/
							JRDesignTextField textField = new JRDesignTextField();
							textField.setStretchWithOverflow(true);
							Integer x = Integer.valueOf(objectMap.get("x").toString());
							textField.setX(x);
							Integer y = 0;
							y = Integer.valueOf(objectMap.get("y").toString()) + (arg_no - 1) * 15;
							textField.setY(y);
							textField.setHeight(Integer.valueOf(objectMap.get("height").toString()));
							textField.setWidth(Integer.valueOf(objectMap.get("width").toString()));
							textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
							textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
							textField.setFontName("宋体");
							try {
								float fontSize = Float.valueOf((String) map.get("fontSize"));
								textField.setFontSize(fontSize + 0.0f);
							}catch (Exception e){
								textField.setFontSize(8.0f);
							}
							textField.setPdfEmbedded(true);
							textField.setPdfFontName("STSong-Light");
							textField.setPdfEncoding("UniGB-UCS2-H");
							textField.setBlankWhenNull(true);
							JRDesignExpression expression = new JRDesignExpression();
							//expression.setValueClass(java.lang.String.class);
							if ("inoculate_date".equals(objectMap.get("name").toString())) {
								expression.setText("$F{" + fieldname + "}.substring(0,11)");
							} else {
								expression.setText("$F{" + fieldname + "}");
							}
							textField.setExpression(expression);
							if ("1".equalsIgnoreCase(page)) {
								try {
									columnHeadBand1.addElement(textField);
									load1.addField(field);
								} catch (Exception e) {
								}
								data1.put(fieldname, name);//data1
							} else if ("2".equalsIgnoreCase(page)) {
								try {
									columnHeadBand2.addElement(textField);
									load2.addField(field);
								} catch (Exception e) {
								}
								data2.put(fieldname, name);
							} else {

							}
						}
					}
				}

				List<ExporterInputItem> items = new ArrayList<ExporterInputItem>();
				// newList1.add(data1);

				newList1.add(data1);
				JasperReport jasperReport1 = JasperCompileManager.compileReport(load1);
				JRDataSource dataSource1 = new JRBeanCollectionDataSource(newList1, true);
				//JRDataSource dataSource1 = new JRMapArrayDataSource(newList1, true);
				JasperPrint print1 = JasperFillManager.fillReport(jasperReport1, params, dataSource1);
				ExporterInputItem exporterInput1 = new SimpleExporterInputItem(print1);

				newList2.add(data2);
				JasperReport jasperReport2 = JasperCompileManager.compileReport(load2);
				JRDataSource dataSource2 = new JRBeanCollectionDataSource(newList2, true);
				JasperPrint print2 = JasperFillManager.fillReport(jasperReport2, params, dataSource2);
				ExporterInputItem exporterInput2 = new SimpleExporterInputItem(print2);
				items.add(exporterInput1);
				if(!data2.isEmpty()) {
					items.add(exporterInput2);
				}

				response.setContentType("application/pdf");
				response.setCharacterEncoding("utf-8");
           /* response.setHeader("Content-Disposition", "inline; filename=\""
                    + new String(viewname.getBytes("UTF-8")) + ".pdf" + "\"");*/
				response.setHeader("Content-Disposition", "inline; filename="
						+ new String(viewname.getBytes("UTF-8")) + ".pdf");
				JRAbstractExporter exporter = new JRPdfExporter();
				OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
						response.getOutputStream());
				ExporterInput exporterInput = new SimpleExporterInput(items);
				//exporter.setParameter(JRExporterParameter.PAGE_INDEX,2);
				exporter.setExporterOutput(exporterOutput);
				// 设置输入项
				exporter.setExporterInput(exporterInput);
				exporter.exportReport();
				exporterOutput.close();

			}
		} catch (JRException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void queryAndPrintchildGather(List<IntegrityRateEntity> integrityRateEntities,String path, HttpServletResponse response) {
		String filepath = path +"reports/ChildGather.jrxml";
		Double nameEntryRate = 0.0;
		Double sexEntryRate = 0.0;
		Double birthTimeEntryRate = 0.0;
		Double createSiteEntryRate = 0.0;
		Double telEntryRate = 0.0;
		Double mothernameEntryRate = 0.0;
		Double habiIdEntryRate = 0.0;
		Double hukouAddressEntryRate = 0.0;
		Double contactAddressEntryRate = 0.0;
		List<Map<String, Object>> lists = new ArrayList();
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			JasperReport jreport = JasperCompileManager.compileReport(filepath);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			params.put("nowDate",sdf.format(new Date()));
			for(IntegrityRateEntity integrityRateEntity:integrityRateEntities){
				Map<String, Object> maps = new HashMap<String,Object>();
				maps.put("year",integrityRateEntity.getYear());
				maps.put("totalChild",integrityRateEntity.getTotalChild());
				maps.put("fullCount",integrityRateEntity.getFullCount());
				maps.put("fullNameCount",integrityRateEntity.getFullNameCount());
				maps.put("fullSexCount",integrityRateEntity.getFullSexCount());
				maps.put("fullBirthTimeCount",integrityRateEntity.getFullBirthTimeCount());
				maps.put("fullMothernameCount",integrityRateEntity.getFullMothernameCount());
				maps.put("fullChild_habi_idCount",integrityRateEntity.getFullHabiIdCount());
				maps.put("fullHabiIdCount",integrityRateEntity.getFullHabiIdCount());
				maps.put("fullHukouAddressCount",integrityRateEntity.getFullHukouAddressCount());
				maps.put("fullCreateSiteCount",integrityRateEntity.getFullCreateSiteCount());

				maps.put("integrityRate",integrityRateEntity.getIntegrityRate());
				maps.put("fullContactAddressCount",integrityRateEntity.getFullContactAddressCount());
				maps.put("fullTelCount",integrityRateEntity.getFullTelCount());
				int total = integrityRateEntity.getTotalChild();
				nameEntryRate = integrityRateEntity.getFullNameCount()*1.0/total*100;
				sexEntryRate = integrityRateEntity.getFullSexCount()*1.0/total*100;
				birthTimeEntryRate = integrityRateEntity.getFullBirthTimeCount()*1.0/total*100;
				createSiteEntryRate = integrityRateEntity.getFullCreateSiteCount()*1.0/total*100;
				telEntryRate = integrityRateEntity.getFullTelCount()*1.0/total*100;
				mothernameEntryRate = integrityRateEntity.getFullMothernameCount()*1.0/total*100;
				habiIdEntryRate = integrityRateEntity.getFullHabiIdCount()*1.0/total*100;
				hukouAddressEntryRate = integrityRateEntity.getFullHukouAddressCount()*1.0/total*100;
				contactAddressEntryRate = integrityRateEntity.getFullContactAddressCount()*1.0/total*100;
				maps.put("nameEntryRate",Double.parseDouble(String.format("%.2f",nameEntryRate)));
				maps.put("sexEntryRate",Double.parseDouble(String.format("%.2f",sexEntryRate)));
				maps.put("birthTimeEntryRate",Double.parseDouble(String.format("%.2f",birthTimeEntryRate)));
				maps.put("createSiteEntryRate",Double.parseDouble(String.format("%.2f",createSiteEntryRate)));
				maps.put("telEntryRate",Double.parseDouble(String.format("%.2f",telEntryRate)));
				maps.put("mothernameEntryRate",Double.parseDouble(String.format("%.2f",mothernameEntryRate)));
				maps.put("habiIdEntryRate",Double.parseDouble(String.format("%.2f",habiIdEntryRate)));
				maps.put("hukouAddressEntryRate",Double.parseDouble(String.format("%.2f",hukouAddressEntryRate)));
				maps.put("contactAddressEntryRate",Double.parseDouble(String.format("%.2f",contactAddressEntryRate)));
				lists.add(maps);
			}
			JRDataSource dataSource = new JRBeanCollectionDataSource(lists, true);
			//log.info("马上进入fillReport编译");
			JasperPrint print = JasperFillManager.fillReport(jreport, params,
					dataSource);
			//log.info("fillReport编译成功");
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String("ChildInfo".getBytes("UTF-8")) + ".pdf" + "");

			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public List<TChildInfoEntity> ImperfectDataChild(List<TChildInfoEntity> listImperfectChild, Map<String,Object> map, HttpServletResponse response){
		String viewname = "ImperfectChildInfo";
		String path=map.get("path").toString();
		for(TChildInfoEntity tChildInfoEntity:listImperfectChild){
			if("1".equals(tChildInfoEntity.getChilSex())){
				tChildInfoEntity.setChilSex("男");
			}if("2".equals(tChildInfoEntity.getChilSex())){
				tChildInfoEntity.setChilSex("女");
			}
			if(null==tChildInfoEntity.getChilFather() || "null".equals(tChildInfoEntity.getChilFather()) || tChildInfoEntity.getChilFather()==""){
				tChildInfoEntity.setChilFather("");
			}
		}
		JRDataSource dataSource = new JRBeanCollectionDataSource(listImperfectChild, true);
		try{
			JasperReport jasperReport = JasperCompileManager.compileReport( path+"reports/" + viewname + ".jrxml");
			JasperPrint print =null;
			Map<String,Object> map1 = new HashMap<>();
			map1.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			map1.put("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			print = JasperFillManager.fillReport(jasperReport,map1, dataSource);
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf" );

			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return listImperfectChild;
	}



	@Override
	public List<Map<String,Object>> InoculateLogs(Map<String,Object> paramMap, HttpServletResponse response) {
		//根据条件四个返回步骤：
		HashMap<String,Object> param = new HashMap<>();
		if (!StringUtils.isEmpty(String.valueOf(paramMap.get("chilBirthdayStart")))) {
			param.put("chilBirthdayStart", paramMap.get("chilBirthdayStart"));
		}
		if (!StringUtils.isEmpty(String.valueOf(paramMap.get("chilBirthdayEnd")))) {
			param.put("chilBirthdayEnd", paramMap.get("chilBirthdayEnd"));
		}
		if (!StringUtils.isEmpty(String.valueOf(paramMap.get("(String)")))) {
			param.put("inoculateStart", paramMap.get("inoculateStart"));
		}
		if (!StringUtils.isEmpty(String.valueOf(paramMap.get("inoculateEnd")))) {
			param.put("inoculateEnd", paramMap.get("inoculateEnd"));
		}

		if(paramMap.get("chilResidences")==null||"".equals(paramMap.get("chilResidences"))){
//			System.out.println("4444444444");
		}else{
			String str1=paramMap.get("chilResidences").toString();
			String chilResidence[]= str1.split(",");
			param.put("chilResidence", chilResidence);
		}
		if(paramMap.get("chilCommittees")==null||"".equals(paramMap.get("chilCommittees"))){
//			System.out.println("333334444444");
		}else{
			String str2=paramMap.get("chilCommittees").toString();
			String chilCommittees[]= str2.split(",");
			param.put("chilCommittees", chilCommittees);
		}
		if(paramMap.get("infostatus")==null||"".equals(paramMap.get("infostatus"))){
//			System.out.println("111111111111");
		}else{
			String str3=paramMap.get("infostatus").toString();
			String infostatus[]= str3.split(",");
			param.put("infostatus", infostatus);
		}
		if(paramMap.get("biotypes")==null||"".equals(paramMap.get("biotypes"))){
//			System.out.println("888888888888888");
		}else{
			String str4=paramMap.get("biotypes").toString();
			String biotypes[]= str4.split(",");
			param.put("biotypes", biotypes);
		}
		if(paramMap.get("bioNos")==null||"".equals(paramMap.get("bioNos"))){
//			System.out.println("3333333333333335555555");
		}else{
			String str5=paramMap.get("bioNos").toString();
			String bioNos[]=str5.split(",");
			param.put("bioNos", bioNos);
		}
		if(paramMap.get("inocDoctors") != null && !"".equals(paramMap.get("inocDoctors"))){
			String inocDoctor=paramMap.get("inocDoctors").toString();
			String inocDoctors[]=inocDoctor.split(",");
			param.put("inocDoctors", inocDoctors);
		}
		if(paramMap.get("schools")==null||"".equals(paramMap.get("schools"))){
//			System.out.println("22222222223");
		}else{
			String str6=paramMap.get("schools").toString();
			String school[]=  str6.split(",");
			param.put("school", school);
		}
		if(paramMap.get("inocbatchno")==null||"".equals(paramMap.get("inocbatchno"))){
		}else{
			String inocbatchno=paramMap.get("inocbatchno").toString();
			String inocbatchnos[]=inocbatchno.split(",");
			param.put("inocbatchno", inocbatchnos);
		}
		param.put("org_id",ShiroUtils.getUserEntity().getOrgId());
		List<Map<String,Object>> historys = inoculateLosgDao.getInoulateLogss(param);
		for(Map<String,Object> maps:historys){
			if(maps.get("chil_committee")==null){
				maps.put("chil_committee", "暂无归属");
			}
			if(maps.get("inoc_validdate")==null){
				maps.put("inoc_validdate", "");
			}
		}

		String viewname = "InoculateLog";
		String path=paramMap.get("path").toString();
		JRDataSource dataSource = new JRBeanCollectionDataSource(historys, true);
		try{
			JasperReport jasperReport = JasperCompileManager.compileReport( path+"reports/" + viewname + ".jrxml");
			JasperPrint print =null;
			Map<String,Object> map1 = new HashMap<>();
			map1.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			map1.put("endDate", paramMap.get("inoculateEnd"));
			map1.put("startDate", paramMap.get("inoculateStart"));
			print = JasperFillManager.fillReport(jasperReport,map1, dataSource);
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf" );
			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return historys;
	}

	@Override
	public List<InoculateIntegrityRateEntity> inoculateGather(List<InoculateIntegrityRateEntity> inocIntegrityRate, Map<String,Object> map, HttpServletResponse response){
		String viewname = "inoculateGather";
		String path=map.get("path").toString();

		Double bioNameEntryRate = 0.0;
		Double inoDateEntryRate = 0.0;
		Double inocBatchnoEntryRate = 0.0;
		Double inocCorpCodeEntryRate = 0.0;
		Double inocRoadEntryRate = 0.0;
		Double inocInplIdEntryRate = 0.0;
		Double inocPlaceEntryRate = 0.0;
		Double inocNurseEntryRate = 0.0;
		Double payStatusEntryRate = 0.0;
		Double TimelyEntryRate = 0.0;
		List<Map<String, Object>> lists = new ArrayList();
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			params.put("nowDate",sdf.format(new Date()));
			for(InoculateIntegrityRateEntity integrityRateEntity:inocIntegrityRate){
				Map<String, Object> maps = new HashMap<String,Object>();
				maps.put("bioName",integrityRateEntity.getBioName());//疫苗名称
				maps.put("totalInoTimes",integrityRateEntity.getTotalInoTimes());//剂次数
				maps.put("fullCount",integrityRateEntity.getFullCount());//完整度
				maps.put("inocIntegrityRate",integrityRateEntity.getInocIntegrityRate());//完整率

				maps.put("fullBioNameCount",integrityRateEntity.getFullBioNameCount());//接种疫苗
				maps.put("fullInoDateCount",integrityRateEntity.getFullInoDateCount());//接种日期
				maps.put("fullInocBatchnoCount",integrityRateEntity.getFullInocBatchnoCount());//疫苗批号
				maps.put("fullInocCorpCodeCount",integrityRateEntity.getFullInocCorpCodeCount());//生产企业

				maps.put("fullInocRoadCount",integrityRateEntity.getFullInocRoadCount());//接种途径
				maps.put("fullInocInplIdCount",integrityRateEntity.getFullInocInplIdCount());//接种部位
				maps.put("fullInocPlaceCount",integrityRateEntity.getFullInocPlaceCount());//接种地点
				maps.put("fullInocNurseCount",integrityRateEntity.getFullInocNurseCount());//接种医生

				maps.put("fullPayStatusCount",integrityRateEntity.getFullPayStatusCount());//收费状态
				maps.put("fullTimelyCount",integrityRateEntity.getFullTimelyCount());//及时录入
				int total = integrityRateEntity.getTotalInoTimes();
				bioNameEntryRate = integrityRateEntity.getFullBioNameCount()*1.0/total*100;
				inoDateEntryRate = integrityRateEntity.getFullInoDateCount()*1.0/total*100;
				inocBatchnoEntryRate = integrityRateEntity.getFullInocBatchnoCount()*1.0/total*100;
				inocCorpCodeEntryRate = integrityRateEntity.getFullInocCorpCodeCount()*1.0/total*100;
				inocRoadEntryRate = integrityRateEntity.getFullInocRoadCount()*1.0/total*100;
				inocInplIdEntryRate = integrityRateEntity.getFullInocInplIdCount()*1.0/total*100;
				inocPlaceEntryRate = integrityRateEntity.getFullInocPlaceCount()*1.0/total*100;
				inocNurseEntryRate = integrityRateEntity.getFullInocNurseCount()*1.0/total*100;
				payStatusEntryRate = integrityRateEntity.getFullPayStatusCount()*1.0/total*100;
				TimelyEntryRate = integrityRateEntity.getFullTimelyCount()*1.0/total*100;
				maps.put("bioNameEntryRate",Double.parseDouble(String.format("%.2f",bioNameEntryRate)));
				maps.put("inoDateEntryRate",Double.parseDouble(String.format("%.2f",inoDateEntryRate)));
				maps.put("inocBatchnoEntryRate",Double.parseDouble(String.format("%.2f",inocBatchnoEntryRate)));
				maps.put("inocCorpCodeEntryRate",Double.parseDouble(String.format("%.2f",inocCorpCodeEntryRate)));
				maps.put("inocRoadEntryRate",Double.parseDouble(String.format("%.2f",inocRoadEntryRate)));
				maps.put("inocInplIdEntryRate",Double.parseDouble(String.format("%.2f",inocInplIdEntryRate)));
				maps.put("inocPlaceEntryRate",Double.parseDouble(String.format("%.2f",inocPlaceEntryRate)));
				maps.put("inocNurseEntryRate",Double.parseDouble(String.format("%.2f",inocNurseEntryRate)));
				maps.put("payStatusEntryRate",Double.parseDouble(String.format("%.2f",payStatusEntryRate)));
				maps.put("TimelyEntryRate",Double.parseDouble(String.format("%.2f",TimelyEntryRate)));
				lists.add(maps);
			}

			JRDataSource dataSource = new JRBeanCollectionDataSource(lists, true);
			JasperReport jasperReport = JasperCompileManager.compileReport( path+"reports/" + viewname + ".jrxml");
			JasperPrint print =null;
			Map<String,Object> map1 = new HashMap<>();
			map1.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			map1.put("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			print = JasperFillManager.fillReport(jasperReport,map1, dataSource);
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf" );

			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void printCurrentDayInocChild(List<TChildInfoEntity> listCurrentDayInocChild,String path, HttpServletResponse response) {
		String viewname = "currentDayInocChild";
		List<Map<String, Object>> lists = new ArrayList();
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			params.put("nowDate",sdf.format(new Date()));

			JRDataSource dataSource = new JRBeanCollectionDataSource(listCurrentDayInocChild, true);
			JasperReport jasperReport = JasperCompileManager.compileReport( path+"reports/" + viewname + ".jrxml");
			JasperPrint print =null;
			Map<String,Object> map1 = new HashMap<>();
			map1.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			map1.put("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			print = JasperFillManager.fillReport(jasperReport,map1, dataSource);
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf" );

			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void printCurrentDayWaitInocChild(List<TChildInfoEntity> listCurrentDayWaitInocChild,String path, HttpServletResponse response) {
		String viewname = "currentDayWaitInocChild";
		List<Map<String, Object>> lists = new ArrayList();
		try {
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			params.put("nowDate",sdf.format(new Date()));

			JRDataSource dataSource = new JRBeanCollectionDataSource(listCurrentDayWaitInocChild, true);
			JasperReport jasperReport = JasperCompileManager.compileReport( path+"reports/" + viewname + ".jrxml");
			JasperPrint print =null;
			Map<String,Object> map1 = new HashMap<>();
			map1.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			map1.put("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			print = JasperFillManager.fillReport(jasperReport,map1, dataSource);
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf" );

			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void printStockControl(List<Map<String, Object>> list, String path, HttpServletResponse response) {
		String viewname = "stockcontrol";
		try {
//			Map<String, Object> params = new HashMap<String,Object>();
//			params.put("doctorName", ShiroUtils.getUserEntity().getRealName());
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			params.put("nowDate",sdf.format(new Date()));
			JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
			System.out.println(dataSource);
			JasperReport jasperReport = JasperCompileManager.compileReport( path+"reports/" + viewname + ".jrxml");
			System.out.println(jasperReport);
			JasperPrint print =null;
			Map<String,Object> map1 = new HashMap<>();
			map1.put("doctorName", ShiroUtils.getUserEntity().getRealName());
			map1.put("nowDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			print = JasperFillManager.fillReport(jasperReport,map1, dataSource);
			response.setContentType("application/pdf");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "inline; filename="
					+ new String(viewname.getBytes("UTF-8")) + ".pdf" );

			JRAbstractExporter exporter = new JRPdfExporter();
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					response.getOutputStream());
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterOutput(exporterOutput);
			// 设置输入项
			exporter.setExporterInput(exporterInput);
			exporter.exportReport();
			exporterOutput.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
