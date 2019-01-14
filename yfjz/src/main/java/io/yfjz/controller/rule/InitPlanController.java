package io.yfjz.controller.rule;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.InitPlanService;
import io.yfjz.managerservice.rule.impl.InitPlanServiceImpl;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 初始化儿童规划表
 * @作者：邓召仕
 * 上午9:17:33
 */
@Controller
@RequestMapping("initplan")
public class InitPlanController {
	@Autowired
	private InitPlanService initPlanService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/noPlanChilds")
	//@RequiresPermissions("truleplan:list")
	public R list(Integer page, Integer limit){
		//查询列表数据
		List<ChildData> tRulePlanList = initPlanService.queryNoPlanChildsList(page,limit);
		int total = initPlanService.queryNoPlanChildsTotal();
		
		PageUtils pageUtil = new PageUtils(tRulePlanList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}

	@ResponseBody
	@RequestMapping("init")
	public R init(@RequestBody String[] ids){
		initPlanService.initChildsPlan(ids);
		return R.ok();
	}
	@ResponseBody
	@RequestMapping("initAll")
	public R initAll(){
		initPlanService.initAllChildsPlan();
		return R.ok();
	}

	@ResponseBody
	@RequestMapping("childTotal")
	public R childTotal(){
		int childNumber = initPlanService.queryChildsTotal();
		return R.ok().put("number",childNumber);
	}

	@ResponseBody
	@RequestMapping("childNoPlanTotal")
	public R childNoPlanTotal(){
		int childNumber = initPlanService.queryNoPlanChildsTotal();
		return R.ok().put("number",childNumber);
	}

	@ResponseBody
	@RequestMapping("progressNumber")
	public String progressNumber(){
		return InitPlanServiceImpl.childNumber +"";
	}
}
