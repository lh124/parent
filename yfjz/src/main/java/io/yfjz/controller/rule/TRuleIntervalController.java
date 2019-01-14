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

import io.yfjz.entity.rule.TRuleIntervalEntity;
import io.yfjz.service.rule.TRuleIntervalService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 计划针剂间隔表
 * @作者：邓召仕
 * 上午9:16:47
 */
@Controller
@RequestMapping("truleinterval")
public class TRuleIntervalController {
	@Autowired
	private TRuleIntervalService tRuleIntervalService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("truleinterval:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TRuleIntervalEntity> tRuleIntervalList = tRuleIntervalService.queryList(map);
		int total = tRuleIntervalService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tRuleIntervalList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("truleinterval:info")
	public R info(@PathVariable("id") String id){
		TRuleIntervalEntity tRuleInterval = tRuleIntervalService.queryObject(id);
		
		return R.ok().put("tRuleInterval", tRuleInterval);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("truleinterval:save")
	public R save(@RequestBody TRuleIntervalEntity tRuleInterval){
		tRuleIntervalService.save(tRuleInterval);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("truleinterval:update")
	public R update(@RequestBody TRuleIntervalEntity tRuleInterval){
		tRuleIntervalService.update(tRuleInterval);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("truleinterval:delete")
	public R delete(@RequestBody String[] ids){
		tRuleIntervalService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
