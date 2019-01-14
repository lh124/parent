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

import io.yfjz.entity.rule.TRuleReplaceEntity;
import io.yfjz.service.rule.TRuleReplaceService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 接种规划映射关系表
 * @作者：邓召仕
 * 上午9:17:49
 */
@Controller
@RequestMapping("trulereplace")
public class TRuleReplaceController {
	@Autowired
	private TRuleReplaceService tRuleReplaceService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list/{id}")
	//@RequiresPermissions("trulereplace:list")
	public R list(Integer page, Integer limit,@PathVariable("id") String id){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("consultId",id);
		//查询列表数据
		List<TRuleReplaceEntity> tRuleReplaceList = tRuleReplaceService.queryList(map);
		int total = tRuleReplaceService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tRuleReplaceList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("trulereplace:info")
	public R info(@PathVariable("id") String id){
		TRuleReplaceEntity tRuleReplace = tRuleReplaceService.queryObject(id);
		
		return R.ok().put("tRuleReplace", tRuleReplace);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("trulereplace:save")
	public R save(@RequestBody TRuleReplaceEntity tRuleReplace){
		tRuleReplaceService.save(tRuleReplace);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("trulereplace:update")
	public R update(@RequestBody TRuleReplaceEntity tRuleReplace){
		tRuleReplaceService.update(tRuleReplace);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete/{id}")
	//@RequiresPermissions("trulereplace:delete")
	public R delete(@PathVariable("id") String id){
//		System.out.println(id);
		tRuleReplaceService.delete(id);
		
		return R.ok();
	}
	
}
