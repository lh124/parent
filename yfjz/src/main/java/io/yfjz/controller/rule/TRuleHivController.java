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

import io.yfjz.entity.rule.TRuleHivEntity;
import io.yfjz.service.rule.TRuleHivService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 母亲感染HIV所生儿童接种规则表
 * @作者：邓召仕
 * 上午9:16:28
 */
@Controller
@RequestMapping("trulehiv")
public class TRuleHivController {
	@Autowired
	private TRuleHivService tRuleHivService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("trulehiv:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TRuleHivEntity> tRuleHivList = tRuleHivService.queryList(map);
		int total = tRuleHivService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tRuleHivList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("trulehiv:info")
	public R info(@PathVariable("id") String id){
		TRuleHivEntity tRuleHiv = tRuleHivService.queryObject(id);
		
		return R.ok().put("tRuleHiv", tRuleHiv);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("trulehiv:save")
	public R save(@RequestBody TRuleHivEntity tRuleHiv){
		tRuleHivService.save(tRuleHiv);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("trulehiv:update")
	public R update(@RequestBody TRuleHivEntity tRuleHiv){
		tRuleHivService.update(tRuleHiv);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("trulehiv:delete")
	public R delete(@RequestBody String[] ids){
		tRuleHivService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
