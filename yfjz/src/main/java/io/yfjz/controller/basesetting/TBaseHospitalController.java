package io.yfjz.controller.basesetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yfjz.entity.basesetting.TBaseHospitalEntity;
import io.yfjz.service.basesetting.TBaseHospitalService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 儿童出生医院
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-09 17:05:08
 */
@Controller
@RequestMapping("tbasehospital")
public class TBaseHospitalController {
	@Autowired
	private TBaseHospitalService tBaseHospitalService;
	
	@RequestMapping("/tbasehospital.html")
	public String list(){
		return "tbasehospital/tbasehospital.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	/*@RequiresPermissions("tbasehospital:list")*/
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TBaseHospitalEntity> tBaseHospitalList = tBaseHospitalService.queryList(map);
		int total = tBaseHospitalService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tBaseHospitalList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{hospitalCode}")
	public R info(@PathVariable("hospitalCode") String hospitalCode){
		TBaseHospitalEntity tBaseHospital = tBaseHospitalService.queryObject(hospitalCode);
		
		return R.ok().put("tBaseHospital", tBaseHospital);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBaseHospitalEntity tBaseHospital){
		tBaseHospitalService.save(tBaseHospital);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBaseHospitalEntity tBaseHospital){
		tBaseHospitalService.update(tBaseHospital);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] hospitalCodes){
		tBaseHospitalService.deleteBatch(hospitalCodes);
		
		return R.ok();
	}
	
}
