package io.yfjz.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.sys.SysHash256Entity;
import io.yfjz.service.sys.SysHash256Service;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * @class_name: SysHash256Controller
 * @describe:
 * @author: 刘琪
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date:  2018/1/11 14:50
 **/
@Controller
@RequestMapping("syshash256")
public class SysHash256Controller {
	@Autowired
	private SysHash256Service sysHash256Service;
	
	@RequestMapping("/syshash256.html")
	public String list(){
		return "syshash256/syshash256.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("syshash256:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<SysHash256Entity> sysHash256List = sysHash256Service.queryList(map);
		int total = sysHash256Service.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(sysHash256List, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{hashcode}")
	@RequiresPermissions("syshash256:info")
	public R info(@PathVariable("hashcode") String hashcode){
		SysHash256Entity sysHash256 = sysHash256Service.queryObject(hashcode);
		
		return R.ok().put("sysHash256", sysHash256);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("syshash256:save")
	public R save(@RequestBody SysHash256Entity sysHash256){
		sysHash256Service.save(sysHash256);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("syshash256:update")
	public R update(@RequestBody SysHash256Entity sysHash256){
		sysHash256Service.update(sysHash256);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("syshash256:delete")
	public R delete(@RequestBody String[] hashcodes){
		sysHash256Service.deleteBatch(hashcodes);
		
		return R.ok();
	}
	
}
