package io.yfjz.controller.bus;

import io.yfjz.dao.bus.TBusRegisterDao;
import io.yfjz.dao.child.TChildHealthcareDao;
import io.yfjz.dao.child.TChildInoculateDao;
import io.yfjz.dao.child.TChildPrecheckDao;
import io.yfjz.dao.inocpointmgr.ProcessSetDao;
import io.yfjz.entity.basesetting.ProcessSetEntity;
import io.yfjz.entity.bus.TBusRegisterEntity;
import io.yfjz.entity.child.TChildHealthcareEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.child.TChildInoculateEntity;
import io.yfjz.entity.child.TChildPrecheckEntity;
import io.yfjz.entity.queue.TQueueNoEntity;
import io.yfjz.service.bus.TBusRegisterService;
import io.yfjz.service.bus.VaccRegisterService;
import io.yfjz.service.child.TChildHealthcareService;
import io.yfjz.service.child.TChildInfoService;
import io.yfjz.service.child.TChildInoculateService;
import io.yfjz.service.child.TChildPrecheckService;
import io.yfjz.utils.*;
import io.yfjz.utils.pdf.PDFUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 儿童接种登记表
 *
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-17 14:25:03
 */
@Controller
@RequestMapping("tbusregister")
public class TBusRegisterController {
	@Autowired
	private TBusRegisterService tBusRegisterService;
	@Autowired
	private TChildInoculateService tChildInoculateService;
	@Autowired
	private ProcessSetDao  processSetDao;
	@Autowired
	private TChildPrecheckService tChildPrecheckService;
	@Autowired
	private TChildHealthcareService tChildHealthcareService;
	//	@Autowired
//	private TBusRegisterDao tBusRegisterDao;
	@Autowired
	TChildInfoService tChildInfoService;
	@Autowired
	VaccRegisterService vaccRegisterService;

	@RequestMapping("/tbusregister.html")
	public String list(){
		return "tbusregister/tbusregister.html";
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("tbusregister:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);

		//查询列表数据
		List<TBusRegisterEntity> tBusRegisterList = tBusRegisterService.queryList(map);
		int total = tBusRegisterService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(tBusRegisterList, total, limit, page);

		return R.ok().put("page", pageUtil);
	}


	@ResponseBody
	@RequestMapping("registeList")
	public R registeList(HttpServletRequest request, HttpServletResponse response,String childCode){

		//查询列表数据
		List<Map> tBusRegisterList = tBusRegisterService.registeList(childCode);

		PageUtils pageUtil = new PageUtils(tBusRegisterList, tBusRegisterList.size(), 1, 10);

		return R.ok().put("page", pageUtil);
	}
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TBusRegisterEntity tBusRegister = tBusRegisterService.queryObject(id);

		return R.ok().put("tBusRegister", tBusRegister);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBusRegisterEntity tBusRegister){
		tBusRegisterService.save(tBusRegister);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBusRegisterEntity tBusRegister){
		tBusRegisterService.update(tBusRegister);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tBusRegisterService.deleteBatch(ids);

		return R.ok();
	}


	/**
	 * @method_name: addRecommendList
	 * @describe: 登记疫苗 可选疫苗 -- 存到推荐疫苗
	 * @param: [childCode]
	 * @return: List<Map<String,String>>
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/17  10:52
	 **/
	@RequestMapping("addRecommendList")
	@ResponseBody
	public R addRecommendList(@RequestBody List<Map> listMap){
		R r=null;
		try {
			r = vaccRegisterService.addRecommendList(listMap);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常，请联系管理员！");
		}
		return r;
	}


	/**
	 * @method_name: deleteAll
	 * @describe: 删除某儿童今日已登记的所有疫苗信息，登记表
	 * @param: [childCode]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/23  10:39
	 **/
	@RequestMapping("deleteAll")
	@ResponseBody
	public R deleteAll(String childCode){
		return vaccRegisterService.deleteAll(childCode);
	}
	/**
	 * @method_name: removeAddRegister
	 * @describe: 点击删除选择的列表  弹框中的推荐疫苗列表
	 * @param: [map]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/17  17:42
	 **/
	@RequestMapping("removeAddRegister/{id}")
	@ResponseBody
	public R removeAddRegister(@PathVariable("id") String id){
		return vaccRegisterService.removeAddRegister(id);
	}


	/**
	 * @method_name: countByChildCodeAndVaccCode
	 * @describe: 根据儿童编码，疫苗编码统计该儿童该疫苗所属大类下的剂次，历史接种记录表中查询
	 * @param: [request, response]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/21  9:26
	 **/
	@RequestMapping("countByChildCodeAndVaccCode")
	@ResponseBody
	public R countByChildCodeAndVaccCode(HttpServletRequest request,HttpServletResponse response){
		Map paramMap = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
		return vaccRegisterService.countByChildCodeAndVaccCode(paramMap);
	}



	/**
	 * @method_name: exist
	 * @describe: 判断某儿童当日是否登记过同类疫苗
	 * @param: [request, response]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/22  16:39
	 **/
	@RequestMapping("exist")
	@ResponseBody
	public R exist(HttpServletRequest request,HttpServletResponse response){
		Map paramMap = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
		return vaccRegisterService.exist(paramMap);
	}

	/**
	 * @method_name: getTodayRegisterList
	 * @describe: 获取某儿童当日登记的疫苗列表
	 * @param: [request, response, childCode]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/23  15:23
	 **/
	@ResponseBody
	@RequestMapping("getTodayRegisterList")
	public R getTodayRegisterList(HttpServletRequest request, HttpServletResponse response,String childCode){
		//查询列表数据
		List<Map> tBusRegisterList = tBusRegisterService.getTodayRegisterList(childCode);
		String motherHb="";
		try{
			if (tBusRegisterList != null && !tBusRegisterList.isEmpty()){
				for (Map regMap :tBusRegisterList){
					String code = regMap.get("bioCode").toString().substring(0,2);
					if ("02".equals(code)){//接种乙肝
						TChildInfoEntity child = tChildInfoService.queryObject(childCode);
						if (child !=null && ("1".equals(child.getChilMotherhb()) || "01".equals(child.getChilMotherhb()))){
							motherHb = "儿童母亲HB为阳性！";
						}
					}
				}
			}
		}catch (Exception e){}
		PageUtils pageUtil = new PageUtils(tBusRegisterList, tBusRegisterList.size(), 10, 1);
		return R.ok().put("page", pageUtil).put("hb",motherHb);
	}



	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/saveCancelInfo")
	public R saveCancelInfo(String childCode,String childName,String sequenceNoId ){
		Map<String, Object> tempMap = new HashMap<>();
		tempMap.put("childCode",childCode);
		tempMap.put("childName",childName);
		tempMap.put("id",sequenceNoId);
		tBusRegisterService.updateQueue(tempMap);
		return R.ok();
	}
	/**
	 * @Description: 获取今日已登记儿童列表
	 * @Param: [request, response, childCode]
	 * @return: io.yfjz.utils.R
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/10/8 18:20
	 * @Tel  17328595627
	 */
	@ResponseBody
	@RequestMapping("queryToDayRegisterList")
	public R queryToDayRegisterList(HttpServletRequest request, HttpServletResponse response,String childCode,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		//查询列表数据
		List<Map> tBusRegisterList = tBusRegisterService.queryToDayRegisterList(map);
		int total=tBusRegisterService.queryToDayRegisterTotal();
		PageUtils pageUtil = new PageUtils(tBusRegisterList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	/**
	 * @Description: 获取待接种的队列
	 * @Param: [request, response, childCode]
	 * @return: io.yfjz.utils.R
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/10/9 10:28
	 * @Tel  17328595627
	 */
	@ResponseBody
	@RequestMapping("queryToDayWaitList")
	public R queryToDayWaitList(HttpServletRequest request, HttpServletResponse response,String childCode,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		//查询列表数据
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Map> tBusRegisterList = tBusRegisterService.queryToDayWaitList(map);
		int total=tBusRegisterService.queryToDayWaitTotal();
		PageUtils pageUtil = new PageUtils(tBusRegisterList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	/**
	 * @Description: 打印今日已接种儿童
	 * @Param: [request, response, childCode]
	 * @return: io.yfjz.utils.R
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/10/9 10:59
	 * @Tel  17328595627
	 */
	@ResponseBody
	@RequestMapping("printRegister")
	public void printRegister(HttpServletRequest request, HttpServletResponse response,String childCode){
		Map<String, Object> map = new HashMap<>();
		//查询列表数据
		List<Map> tBusRegisterList = tBusRegisterService.queryToDayRegisterList(map);
		for (Map map1 : tBusRegisterList){
			Object str=map1.get("remark")==null?"":map1.get("remark").toString();
			map1.put("remark", str);
			System.out.println(map1.get("remark"));
		}

		String[] names={"排队号,number","儿童编号,childCode","儿童姓名,childName","接种部位,inoculate_site","剂次,dose_no","性别,sex","出生日期,birthday","母亲姓名,mother","父亲姓名,father","家庭电话,chilMobile","手机,chilTel","行政村/居委会,committee","户籍地址,habiaddress","家庭地址,address","登记疫苗,bioName","疫苗批号,batchnum","备注,remark"};
		String  tableName="今日已登记列表";
		PDFUtils.commonPrintPDF(request,response,"registerList",names,tBusRegisterList,tableName);

	}
	/**
	 * @Description: 打印今日待登记列表
	 * @Param: [request, response, childCode]
	 * @return: io.yfjz.utils.R
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/10/9 10:59
	 * @Tel  17328595627
	 */
	@ResponseBody
	@RequestMapping("printWait")
	public void printWait(HttpServletRequest request, HttpServletResponse response,String childCode){
		Map<String, Object> map = new HashMap<>();
		//查询列表数据
		List<Map> tBusRegisterList = tBusRegisterService.queryToDayWaitList(map);
		String[] names={"排队号,noText","儿童编号,childCode","儿童姓名,childName","性别,sex","出生日期,birthday"};
		String  tableName="今日待登记列表";
		PDFUtils.commonPrintPDF(request,response,"waitList",names,tBusRegisterList,tableName);

	}

	/**
	 * 登记数据监控
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/RegistrationMonitoring")
	public  R RegistrationMonitoring(){
		Integer sumregister=tBusRegisterService.sumregister();//已登记人数
//		Integer todaysumregister=tBusRegisterService.noregister();//待登记人数
		int todaysumregister=tBusRegisterService.queryToDayWaitTotal();//待登记人数
		String org_id = ShiroUtils.getUserEntity().getOrgId();
		Map<String,Object> map2 = new HashMap<>();
		map2.put("flag", "false");
		map2.put("org_id", org_id);
		int total = tChildInfoService.queryTotalCurrentDayInocChild(map2);

		Integer noinoculate=tBusRegisterService.noinoculate();//未接种总数
		Integer numberss=tBusRegisterService.getNumber();//取号总数
		List<TQueueNoEntity> sumprecheck=tBusRegisterService.waitsumprecheck();//未预检总数
		List<TQueueNoEntity> sumhealthcare=tBusRegisterService.sumhealthcare();//未儿保总数
		List<TChildInoculateEntity> observelist=tChildInoculateService.observelist();//留观完成总数
		List<TChildPrecheckEntity> Prechecklist=tChildPrecheckService.sumprechecks();//预检完成总数
		List<TChildHealthcareEntity> Healthcarelist=tChildHealthcareService.healthcarelists();//儿保完成总数
//		Map<String,Object> map1 =tBusRegisterService.navCount(ShiroUtils.getUserEntity().getOrgId());
		List<ProcessSetEntity> list =processSetDao.sumtower(Constant.GLOBAL_ORG_ID);//获取流程配置
		Map<String,Object> map = new HashMap<>();
		map.put("tower",list );
//		map.put("nav",map1 );
		map.put("sumregister", sumregister);
		map.put("suminoculate", total);
		map.put("noinoculate", noinoculate);
		map.put("todaysumregister",todaysumregister );
		map.put("number",numberss==null?0:numberss);
		map.put("sumnoprecheck", sumprecheck==null?0:sumprecheck.size());
		map.put("sumnohealthcare", sumhealthcare==null?0:sumhealthcare.size());
		map.put("sumobserve", total-(observelist==null?0:observelist.size()));
		map.put("observelist", observelist==null?0:observelist.size());
		map.put("sumprecheck", Prechecklist==null?0:Prechecklist.get(0).getChilCode());
		map.put("sumhealthcare", Healthcarelist==null?0:Healthcarelist.get(0).getChilCode());
		return R.ok().put("data", map);
	}

	@ResponseBody
	@RequestMapping("/listregister")
	public R listregister(){
		List<TQueueNoEntity>  list =tBusRegisterService.noregisterlists();
		List<TQueueNoEntity>  list1 =tBusRegisterService.registerlists();
//		List<TQueueNoEntity>  list3 =tBusRegisterService.noinoculatelist();
		List<TChildInoculateEntity> list4 = tChildInoculateService.suminoculateall();
		List<TChildInfoEntity> list10 = tBusRegisterService.finishInoculate();
		List<TChildInoculateEntity> list5=tChildInoculateService.observelist();//留观完成
		List<TChildInoculateEntity> list6=tChildInoculateService.noobservelist();//留观未完成
		List<TQueueNoEntity> list7=tBusRegisterService.sumprechecks();//未预检总数sumprecheck
		List<TQueueNoEntity> list8=tBusRegisterService.sumhealthcares();//未儿保总数
		List<TChildPrecheckEntity> Prechecklist=tChildPrecheckService.sumprechecks();//预检完成总数
		List<TChildHealthcareEntity> Healthcarelist=tChildHealthcareService.healthcarelists();//儿保完成总数
		HashMap<Object, Object> map = new HashMap<>();
		map.put("list1", list);
		map.put("list2", list1);
		map.put("list3", list10);
		map.put("list4", list4);
		map.put("list5", list5);
		map.put("list6", list6);
		map.put("list7", list7);
		map.put("list8", list8);
		map.put("list9", Prechecklist);
		map.put("list10", Healthcarelist);
		return R.ok().put("data", map);
	}
}
