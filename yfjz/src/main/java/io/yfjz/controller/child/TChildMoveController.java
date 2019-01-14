package io.yfjz.controller.child;

import java.util.List;
import java.util.Map;

import io.yfjz.utils.HttpServletRequestToMapUtils;
import io.yfjz.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.child.TChildMoveEntity;
import io.yfjz.service.child.TChildMoveService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 儿童迁移记录表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 15:56:11
 */
@Controller
@RequestMapping("tchildmove")
public class TChildMoveController {
	@Autowired
	private TChildMoveService tChildMoveService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	/*@RequiresPermissions("tchildmove:list")*/
	public R list(@RequestParam Map<String, Object> params){
//		Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(req);
//		/*Map<String, Object> map = new HashMap<>();*/
//		map.put("offset", (page - 1) * limit);
//		map.put("limit", limit);
		Query query = new Query(params);
		//查询列表数据
		List<TChildMoveEntity> tChildMoveList = tChildMoveService.queryList(query);
		int total = tChildMoveService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tChildMoveList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	/*@RequiresPermissions("tchildmove:info")*/
	public R info(@PathVariable("id") String id){
		TChildMoveEntity tChildMove = tChildMoveService.queryObject(id);
		
		return R.ok().put("tChildMove", tChildMove);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	/*@RequiresPermissions("tchildmove:save")*/
	public R save(@RequestBody TChildMoveEntity tChildMove){
		tChildMoveService.save(tChildMove);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	/*@RequiresPermissions("tchildmove:update")*/
	public R update(@RequestBody TChildMoveEntity tChildMove){
		tChildMoveService.update(tChildMove);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	/*@RequiresPermissions("tchildmove:delete")*/
	public R delete(@RequestBody String[] ids){
		tChildMoveService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
