package io.yfjz.controller.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.service.rule.TRulePlanService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 每位儿童应接种计划保存，根据国家免疫规划表
 * @作者：邓召仕
 * 上午9:17:33
 */
@Controller
@RequestMapping("truleplan")
public class TRulePlanController {
	@Autowired
	private TRulePlanService tRulePlanService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("truleplan:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TRulePlanEntity> tRulePlanList = tRulePlanService.queryList(map);
		int total = tRulePlanService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tRulePlanList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("truleplan:info")
	public R info(@PathVariable("id") String id){
		TRulePlanEntity tRulePlan = tRulePlanService.queryObject(id);
		
		return R.ok().put("tRulePlan", tRulePlan);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("truleplan:save")
	public R save(@RequestBody TRulePlanEntity tRulePlan){
		tRulePlanService.save(tRulePlan);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("truleplan:update")
	public R update(@RequestBody TRulePlanEntity tRulePlan){
		tRulePlanService.update(tRulePlan);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("truleplan:delete")
	public R delete(@RequestBody String[] ids){
		tRulePlanService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
