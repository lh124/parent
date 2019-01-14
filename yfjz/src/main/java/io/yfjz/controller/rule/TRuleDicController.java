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

import io.yfjz.entity.rule.TRuleDicEntity;
import io.yfjz.service.rule.TRuleDicService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 免疫规划字典表
 * @作者：邓召仕
 * 上午9:16:08
 */
@Controller
@RequestMapping("truledic")
public class TRuleDicController {
	@Autowired
	private TRuleDicService tRuleDicService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("truledic:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TRuleDicEntity> tRuleDicList = tRuleDicService.queryList(map);
		int total = tRuleDicService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tRuleDicList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("truledic:info")
	public R info(@PathVariable("id") String id){
		TRuleDicEntity tRuleDic = tRuleDicService.queryObject(id);
		
		return R.ok().put("tRuleDic", tRuleDic);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("truledic:save")
	public R save(@RequestBody TRuleDicEntity tRuleDic){
		tRuleDicService.save(tRuleDic);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("truledic:update")
	public R update(@RequestBody TRuleDicEntity tRuleDic){
		tRuleDicService.update(tRuleDic);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("truledic:delete")
	public R delete(@RequestBody String[] ids){
		tRuleDicService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
