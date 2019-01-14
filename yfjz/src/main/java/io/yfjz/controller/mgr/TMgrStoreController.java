package io.yfjz.controller.mgr;

import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.mgr.TMgrEquipmentEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import io.yfjz.service.mgr.TMgrStoreService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.websocket.TowerSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @Description: 仓库管理
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 14:08
* @Tel  17328595627
*/ 
@Controller
@RequestMapping("tmgrstore")
public class TMgrStoreController {
	@Autowired
	private TMgrStoreService tMgrStoreService;
	
	@RequestMapping("/tmgrstore.html")
	public String list(){
		return "/mgr/tmgrstore.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("tmgrstore:list")
	public R list(Integer page, Integer limit,Integer type,Integer status){
		if(StringUtils.isEmpty(page)&&StringUtils.isEmpty(limit)){
			page=1;
			limit=10;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("status", status);
		if (type!=null){
			map.put("type",type);
		}
		
		//查询列表数据
		List<TMgrStoreEntity> tMgrStoreList = tMgrStoreService.queryList(map);
		int total = tMgrStoreService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tMgrStoreList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("tmgrstore:info")
	public R info(@PathVariable("id") String id){
		TMgrStoreEntity tMgrStore = tMgrStoreService.queryObject(id);
		
		return R.ok().put("tMgrStore", tMgrStore);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("tmgrstore:save")
	public R save(@RequestBody TMgrStoreEntity tMgrStore){
		tMgrStoreService.save(tMgrStore);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("tmgrstore:update")
	public R update(@RequestBody TMgrStoreEntity tMgrStore){
		tMgrStoreService.update(tMgrStore);
		
		return R.ok();
	}
	/**
	 * 仓库停用/启用
	 */
	@ResponseBody
	@RequestMapping("/updateStatus")
	//@RequiresPermissions("tmgrstore:updateStatus")
	public R updateStatus(String id){
		tMgrStoreService.updateStatus(id);
		return R.ok();
	}
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("tmgrstore:delete")
	public R delete(@RequestBody String[] ids){
		tMgrStoreService.deleteBatch(ids);
		
		return R.ok();
	}
	/**
	 * 查询机构绑定的接种台
	 */
	@ResponseBody
	@RequestMapping("/getTowerList")
	public R getTowerList(HttpServletRequest request){
		List<TBaseTowerEntity> list=tMgrStoreService.getToweSrList();
		Map<String, Object> map = new HashMap<>();
		map.put("page",list);
		return R.ok(map);
	}
	/**
	 * 查询所有启用的仓库
	 */
	@ResponseBody
	@RequestMapping("/getAllStore")
	public R getAllStore(){
		List<TMgrStoreEntity> list=tMgrStoreService.getAllStore(0);
		Map<String, Object> map = new HashMap<>();
		map.put("page",list);
		return R.ok(map);
	}
	/** 
	* @Description: 查询仓库关联的设备
	* @Param: [] 
	* @return: io.yfjz.utils.R 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/6 11:15
	* @Tel  17328595627
	*/ 
	@ResponseBody
	@RequestMapping("/getEquipmentById")
	public String  getEquipmentById(String storeId){

		String list=tMgrStoreService.getEquipmentById(storeId);
		return list;
	}
}
