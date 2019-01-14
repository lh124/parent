package io.yfjz.controller.child;

import io.yfjz.entity.child.TBasePositionEntity;
import io.yfjz.entity.child.TSPositionEntity;
import io.yfjz.service.child.TBasePositionService;

import io.yfjz.service.child.TSPositionService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.Query;
import io.yfjz.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 省市县镇村数据
 *
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-30 13:41:54
 */
@Controller
@RequestMapping("tbaseposition")
public class TBasePositionController {
	@Autowired
	private TBasePositionService tBasePositionService;
	@Autowired
	private TSPositionService tsPositionService;

	@RequestMapping("/tbaseposition.html")
	public String list(){
		return "tbaseposition/tbaseposition.html";
	}

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);
		List<TSPositionEntity> tBasePositionList = tsPositionService.queryList(query);
		int total = tsPositionService.queryTotal(query);
//		List<TBasePositionEntity> tBasePositionList = tBasePositionService.queryList(query);
//		int total = tBasePositionService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(tBasePositionList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	@RequestMapping("/listprovince")
	@ResponseBody
	public R listprovince(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("province_id",request.getParameter("province_id"));
		map.put("page",1);
		map.put("limit",38);
		List<Map<String,Object>> tBasePositionList = tsPositionService.queryprovince(map);
//		List<Map<String,Object>> tBasePositionList = tBasePositionService.queryprovince(map);
		return R.ok().put("data",tBasePositionList);
	}
	@RequestMapping("/listcity")
	@ResponseBody
	public R listcity(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("provinceId",request.getParameter("provinceId"));
		map.put("city_id",request.getParameter("city_id"));
		List<Map<String,Object>> tBasePositionList = tsPositionService.querycity(map);
//		List<Map<String,Object>> tBasePositionList = tBasePositionService.querycity(map);
		return R.ok().put("data",tBasePositionList);
	}
	@RequestMapping("/listcounty")
	@ResponseBody
	public R listcounty(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("cityId",request.getParameter("cityId"));
		map.put("country_id",request.getParameter("country_id"));
		List<Map<String,Object>> tBasePositionList = tsPositionService.querycounty(map);
//		List<Map<String,Object>> tBasePositionList = tBasePositionService.querycounty(map);
		return R.ok().put("data",tBasePositionList);
	}
	@RequestMapping("/listtown")
	@ResponseBody
	public R listtown(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("countyId",request.getParameter("countyId"));
		map.put("town_id",request.getParameter("town_id"));
		List<Map<String,Object>> tBasePositionList = tsPositionService.querytown(map);
//		List<Map<String,Object>> tBasePositionList = tBasePositionService.querytown(map);
		return R.ok().put("data",tBasePositionList);
	}
	@RequestMapping("/gethospital")
	@ResponseBody
	public R gethospital(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("page", 0);
		map.put("limit", 3000);
		List<Map<String,Object>> tBasePositionList = tBasePositionService.gethospital(map);
		return R.ok().put("data",tBasePositionList);
	}



	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TBasePositionEntity tBasePosition = tBasePositionService.queryObject(id);

		return R.ok().put("tBasePosition", tBasePosition);
	}


	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBasePositionEntity tBasePosition){
		tBasePositionService.save(tBasePosition);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBasePositionEntity tBasePosition){
		tBasePositionService.update(tBasePosition);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tBasePositionService.deleteBatch(ids);

		return R.ok();
	}

}
