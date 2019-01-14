package io.yfjz.controller.child;

import java.util.List;
import java.util.Map;

import io.yfjz.utils.HttpServletRequestToMapUtils;
import io.yfjz.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.child.TChildInfectionEntity;
import io.yfjz.service.child.TChildInfectionService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 儿童传染病表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:37:19
 */
@Controller
@RequestMapping("tchildinfection")
public class TChildInfectionController {
	@Autowired
	private TChildInfectionService tChildInfectionService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
//		Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(req);
//		/*Map<String, Object> map = new HashMap<>();*/
//		map.put("offset", (page - 1) * limit);
//		map.put("limit", limit);
		Query query = new Query(params);
		//查询列表数据
		List<TChildInfectionEntity> tChildInfectionList = tChildInfectionService.queryList(query);
		int total = tChildInfectionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tChildInfectionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TChildInfectionEntity tChildInfection = tChildInfectionService.queryObject(id);
		
		return R.ok().put("tChildInfection", tChildInfection);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TChildInfectionEntity tChildInfection){
		tChildInfectionService.save(tChildInfection);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TChildInfectionEntity tChildInfection){
		tChildInfectionService.update(tChildInfection);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tChildInfectionService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
