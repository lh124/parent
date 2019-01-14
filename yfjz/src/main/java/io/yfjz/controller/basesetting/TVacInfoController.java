package io.yfjz.controller.basesetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.yfjz.entity.basesetting.TVacInfoEntity;
import io.yfjz.service.basesetting.TVacInfoService;
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
 * 疫苗信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
@Controller
@RequestMapping("tvacinfo")
public class TVacInfoController {
	@Autowired
	private TVacInfoService tVacInfoService;
	
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
		List<TVacInfoEntity> tVacInfoList = tVacInfoService.queryList(map);
		int total = tVacInfoService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(tVacInfoList, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{bioCode}")
	public R info(@PathVariable("bioCode") String bioCode){
		TVacInfoEntity tVacInfo = tVacInfoService.queryObject(bioCode);
		
		return R.ok().put("tVacInfo", tVacInfo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TVacInfoEntity tVacInfo){
		tVacInfoService.save(tVacInfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TVacInfoEntity tVacInfo){
		tVacInfoService.update(tVacInfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] bioCodes){
		tVacInfoService.deleteBatch(bioCodes);
		
		return R.ok();
	}
	/** 
	* @Description: 查询所有的疫苗信息 
	* @Param: [] 
	* @return: com.alibaba.fastjson.JSONObject 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/15 10:02
	* @Tel  17328595627
	*/ 
	@RequestMapping("/getAllData")
	@ResponseBody
	public JSONObject getAllData() {

		List<TVacInfoEntity> allData = tVacInfoService.getAllData();

		JSONObject json = new JSONObject();
		json.put("page",allData);
		return json;
	}
}
