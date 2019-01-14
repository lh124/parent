package io.yfjz.controller.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.print.TChildPrintModelPointEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yfjz.service.print.TChildPrintModelPointService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 儿童打印接种记录模板坐标
 *
 * @author饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-08-17 11:27:06
 */
@Controller
@RequestMapping("tchildprintmodelpoint")
public class TChildPrintModelPointController {
	@Autowired
	private TChildPrintModelPointService tChildPrintModelPointService;
	
	@RequestMapping("/tchildprintmodelpoint.html")
	public String list(){
		return "tchildprintmodelpoint/tchildprintmodelpoint.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	/*@RequiresPermissions("tchildprintmodelpoint:list")*/
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TChildPrintModelPointEntity> tChildPrintModelPointList = tChildPrintModelPointService.queryList(map);
		int total = tChildPrintModelPointService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tChildPrintModelPointList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tchildprintmodelpoint:info")
	public R info(@PathVariable("id") String id){
		TChildPrintModelPointEntity tChildPrintModelPoint = tChildPrintModelPointService.queryObject(id);
		
		return R.ok().put("tChildPrintModelPoint", tChildPrintModelPoint);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tchildprintmodelpoint:save")
	public R save(@RequestBody TChildPrintModelPointEntity tChildPrintModelPoint){
		tChildPrintModelPointService.save(tChildPrintModelPoint);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tchildprintmodelpoint:update")
	public R update(@RequestBody TChildPrintModelPointEntity tChildPrintModelPoint){
		tChildPrintModelPointService.update(tChildPrintModelPoint);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tchildprintmodelpoint:delete")
	public R delete(@RequestBody String[] ids){
		tChildPrintModelPointService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
