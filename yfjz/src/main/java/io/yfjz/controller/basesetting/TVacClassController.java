package io.yfjz.controller.basesetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.basesetting.TVacClassEntity;
import io.yfjz.service.basesetting.TVacClassService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;


import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 疫苗分类种类表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
@Controller
@RequestMapping("tvacclass")
public class TVacClassController {
	@Autowired
	private TVacClassService tVacClassService;
	
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
		List<TVacClassEntity> tVacClassList = tVacClassService.queryList(map);
		int total = tVacClassService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tVacClassList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	@ResponseBody
	@RequestMapping("/queryAllVaccClassList")
	public R queryAllVaccClassList(){
		List<TVacClassEntity> tVacClassList = tVacClassService.queryAllVaccClassList();
		return R.ok().put("list", tVacClassList);
	}

	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{classCode}")
	public R info(@PathVariable("classCode") String classCode){
		TVacClassEntity tVacClass = tVacClassService.queryObject(classCode);
		
		return R.ok().put("tVacClass", tVacClass);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TVacClassEntity tVacClass){
		tVacClassService.save(tVacClass);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TVacClassEntity tVacClass){
		tVacClassService.update(tVacClass);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] classCodes){
		tVacClassService.deleteBatch(classCodes);
		
		return R.ok();
	}
	
}
