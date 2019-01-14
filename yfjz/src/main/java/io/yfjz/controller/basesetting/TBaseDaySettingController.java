package io.yfjz.controller.basesetting;

import io.yfjz.entity.basesetting.TBaseCommitteeEntity;
import io.yfjz.entity.basesetting.TBaseDaySettingEntity;
import io.yfjz.service.basesetting.TBaseCommitteeService;
import io.yfjz.service.basesetting.TBaseDaySettingService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * 机构接种日设置表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-26 17:45:18
 */
@Controller
@RequestMapping("tbasedaysetting")
public class TBaseDaySettingController {
	@Autowired
	private TBaseDaySettingService tBaseDaySettingService;

	@Autowired
	TBaseCommitteeService tBaseCommitteeService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TBaseDaySettingEntity> tBaseDaySettingList = tBaseDaySettingService.queryList(map);
		int total = tBaseDaySettingService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tBaseDaySettingList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TBaseDaySettingEntity tBaseDaySetting = tBaseDaySettingService.queryObject(id);
		
		return R.ok().put("tBaseDaySetting", tBaseDaySetting);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBaseDaySettingEntity tBaseDaySetting){
		tBaseDaySettingService.save(tBaseDaySetting);
		
		return R.ok();
	}

	/**
	 * @method_name: weekdaysave
	 * @describe: 周门诊-接种点 保存点
	 * @param: [tBaseDaySetting]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/26  21:18
	 **/
	@ResponseBody
	@RequestMapping("/weekdaysave")
	public R weekdaysave(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map){
		return tBaseDaySettingService.saveInoculationDaySetting(map);
	}

	/**
	 * @method_name: monthdaysave
	 * @describe: 月门诊-接种点 保存点
	 * @param: [request, response, map]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/28  0:21
	 **/
	@ResponseBody
	@RequestMapping("/monthdaysave")
	public R monthdaysave(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map){
		return tBaseDaySettingService.saveInoculationDaySetting(map);
	}



	@ResponseBody
	@RequestMapping("/getCurrentStartDays")
	public R getCurrentStartDays(HttpServletRequest request, HttpServletResponse response){
		return tBaseDaySettingService.getCurrentStartDays();
	}

	/**
	 * @method_name: start
	 * @describe: 设置启用 根据settingtype类设置启用那个门诊类型
	 * 注：接种日设置类型 1. 接种点-周设置，2.接种点-居委会/行政村-周设置，3.接种点-疫苗-周设置，
	 * 4.接种点-月设置，5.接种点-居委会/行政村-月设置，6.接种点-疫苗-月设置'
	 * @param: [request, response, settingType]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/26  23:20
	 **/
	@ResponseBody
	@RequestMapping("/start")
	public R start(HttpServletRequest request, HttpServletResponse response,@RequestBody String settingType){
		return tBaseDaySettingService.updateBySettingType(settingType);
	}

	/**
	 * @method_name: getCommitteeInfo
	 * @describe: 获取行政村/居委会
	 * @param: [request, response]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/28  10:25
	 **/
	@ResponseBody
	@RequestMapping("/getCommitteeInfo")
	public R getCommitteeInfo(HttpServletRequest request, HttpServletResponse response){
		Map paramMap = new HashMap<>();
		List<TBaseCommitteeEntity> tBaseCommitteeEntities = tBaseCommitteeService.queryList(paramMap);
		//查询列表数据
		int total = tBaseCommitteeService.queryTotal(paramMap);

		PageUtils pageUtil = new PageUtils(tBaseCommitteeEntities, total, 100, 1);

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBaseDaySettingEntity tBaseDaySetting){
		tBaseDaySettingService.update(tBaseDaySetting);

		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tBaseDaySettingService.deleteBatch(ids);
		
		return R.ok();
	}


	@ResponseBody
	@RequestMapping("/list2")
	public R lists(){
		Date date  = new Date();
		return  tBaseDaySettingService.getAnInoculationTime("5224240101",null,null,date);
	}

	/**
	 * 返回符合时间段的接种日
	 */
	@ResponseBody
	@RequestMapping("/listSettingDays")
	public R listSettingDays(String orgId,String CommitteeCode,Date startTime,Date endTime,String bioCode){

		Calendar cal = Calendar.getInstance();
		startTime = cal.getTime();
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, 50);
		endTime = cal2.getTime();
		orgId = ShiroUtils.getUserEntity().getOrgId();
		CommitteeCode = "007";
		//查询列表数据
		Map inoDateMap =  tBaseDaySettingService.queryListDaySettings(orgId,CommitteeCode,startTime,endTime,"0101");

		return R.ok().put("inoDateMap", inoDateMap);
	}
}
