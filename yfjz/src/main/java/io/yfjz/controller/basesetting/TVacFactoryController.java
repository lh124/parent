package io.yfjz.controller.basesetting;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import io.yfjz.entity.basesetting.TVacFactoryEntity;
import io.yfjz.service.basesetting.TVacFactoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;


import io.yfjz.utils.R;


/**
 * 疫苗生产厂家
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 14:10:28
 */
@Controller
@RequestMapping("tvacfactory")
public class TVacFactoryController {
	@Autowired
	private TVacFactoryService tVacFactoryService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(Integer page, Integer limit){

		
		//查询列表数据
		List<TVacFactoryEntity> tVacFactoryList = tVacFactoryService.queryList();

		
		return R.ok().put("page", tVacFactoryList);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{factoryCode}")
	public R info(@PathVariable("factoryCode") String factoryCode){
		TVacFactoryEntity tVacFactory = tVacFactoryService.queryObject(factoryCode);
		
		return R.ok().put("tVacFactory", tVacFactory);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TVacFactoryEntity tVacFactory){
		tVacFactoryService.save(tVacFactory);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TVacFactoryEntity tVacFactory){
		tVacFactoryService.update(tVacFactory);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] factoryCodes){
		tVacFactoryService.deleteBatch(factoryCodes);
		
		return R.ok();
	}
	/**
	 * @Description: 查询所有疫苗厂家的信息
	 * @Param: []
	 * @return: io.yfjz.utils.R
	 * @Author: 田金海
	 * @Email: 895101047@qq.com
	 * @Date: 2018/8/2 9:43
	 * @Tel  17328595627
	 */
	@RequestMapping("/getAllData")
	@ResponseBody
	public JSONObject getAllData() {

		List<TVacFactoryEntity> tVacInfoList = tVacFactoryService.getAllData();

		JSONObject json = new JSONObject();
		json.put("page",tVacInfoList);
		return json;
	}

}
