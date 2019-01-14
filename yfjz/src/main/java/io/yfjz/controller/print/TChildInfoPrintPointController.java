package io.yfjz.controller.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.print.TChildInfoPrintPointEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yfjz.service.print.TChildInfoPrintPointService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 儿童打印基本信息打印坐标
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-08-17 11:27:06
 */
@Controller
@RequestMapping("tchildinfoprintpoint")
public class TChildInfoPrintPointController {
	@Autowired
	private TChildInfoPrintPointService tChildInfoPrintPointService;
	
	@RequestMapping("/tchildinfoprintpoint.html")
	public String list(){
		return "tchildinfoprintpoint/tchildinfoprintpoint.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tchildinfoprintpoint:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TChildInfoPrintPointEntity> tChildInfoPrintPointList = tChildInfoPrintPointService.queryList(map);
		int total = tChildInfoPrintPointService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tChildInfoPrintPointList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tchildinfoprintpoint:info")
	public R info(@PathVariable("id") String id){
		TChildInfoPrintPointEntity tChildInfoPrintPoint = tChildInfoPrintPointService.queryObject(id);
		
		return R.ok().put("tChildInfoPrintPoint", tChildInfoPrintPoint);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tchildinfoprintpoint:save")
	public R save(@RequestBody TChildInfoPrintPointEntity tChildInfoPrintPoint){
		tChildInfoPrintPointService.save(tChildInfoPrintPoint);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tchildinfoprintpoint:update")
	public R update(@RequestBody TChildInfoPrintPointEntity tChildInfoPrintPoint){
		tChildInfoPrintPointService.update(tChildInfoPrintPoint);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tchildinfoprintpoint:delete")
	public R delete(@RequestBody String[] ids){
		tChildInfoPrintPointService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
