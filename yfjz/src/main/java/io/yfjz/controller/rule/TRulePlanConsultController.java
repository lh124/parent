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

import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.service.rule.TRulePlanConsultService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 儿童应接种计划参照表
 * @作者：邓召仕
 * 上午9:17:18
 */
@Controller
@RequestMapping("truleplanconsult")
public class TRulePlanConsultController {
	@Autowired
	private TRulePlanConsultService tRulePlanConsultService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("truleplanconsult:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TRulePlanConsultEntity> tRulePlanConsultList = tRulePlanConsultService.queryList(map);
		int total = tRulePlanConsultService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tRulePlanConsultList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("truleplanconsult:info")
	public R info(@PathVariable("id") String id){
		TRulePlanConsultEntity tRulePlanConsult = tRulePlanConsultService.queryObject(id);
		
		return R.ok().put("tRulePlanConsult", tRulePlanConsult);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("truleplanconsult:save")
	public R save(@RequestBody TRulePlanConsultEntity tRulePlanConsult){
		tRulePlanConsultService.save(tRulePlanConsult);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("truleplanconsult:update")
	public R update(@RequestBody TRulePlanConsultEntity tRulePlanConsult){
		tRulePlanConsultService.update(tRulePlanConsult);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("truleplanconsult:delete")
	public R delete(@RequestBody String[] ids){
		tRulePlanConsultService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
