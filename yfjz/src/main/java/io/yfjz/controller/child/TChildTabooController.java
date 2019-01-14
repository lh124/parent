package io.yfjz.controller.child;

import java.util.List;
import java.util.Map;

import io.yfjz.utils.HttpServletRequestToMapUtils;
import io.yfjz.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.child.TChildTabooEntity;
import io.yfjz.service.child.TChildTabooService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 儿童禁忌表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:47:59
 */
@Controller
@RequestMapping("tchildtaboo")
public class TChildTabooController {
	@Autowired
	private TChildTabooService tChildTabooService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	/*@RequiresPermissions("tchildtaboo:list")*/
	public R list(@RequestParam Map<String, Object> params){
//		Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(req);
//		/*Map<String, Object> map = new HashMap<>();*/
//		map.put("offset", (page - 1) * limit);
//		map.put("limit", limit);
		Query query = new Query(params);
		//查询列表数据
		List<TChildTabooEntity> tChildTabooList = tChildTabooService.queryList(query);
		int total = tChildTabooService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tChildTabooList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	/*@RequiresPermissions("tchildtaboo:info")*/
	public R info(@PathVariable("id") String id){
		TChildTabooEntity tChildTaboo = tChildTabooService.queryObject(id);
		
		return R.ok().put("tChildTaboo", tChildTaboo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	/*@RequiresPermissions("tchildtaboo:save")*/
	public R save(@RequestBody TChildTabooEntity tChildTaboo){
		tChildTabooService.save(tChildTaboo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	/*@RequiresPermissions("tchildtaboo:update")*/
	public R update(@RequestBody TChildTabooEntity tChildTaboo){
		tChildTabooService.update(tChildTaboo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	/*@RequiresPermissions("tchildtaboo:delete")*/
	public R delete(@RequestBody String[] ids){
		tChildTabooService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
