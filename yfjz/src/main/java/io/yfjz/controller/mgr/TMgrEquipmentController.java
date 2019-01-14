package io.yfjz.controller.mgr;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.entity.mgr.TMgrEquipmentEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import io.yfjz.service.mgr.TMgrEquipmentService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @Description: 设备管理Controller 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 15:12
* @Tel  17328595627
*/ 
@Controller
@RequestMapping("tmgrequipment")
public class TMgrEquipmentController {
	@Autowired
	private TMgrEquipmentService tMgrEquipmentService;


	@RequestMapping("/tmgrequipment.html")
	public String list(){
		return "tmgrequipment/tmgrequipment.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("tmgrequipment:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TMgrEquipmentEntity> tMgrEquipmentList = tMgrEquipmentService.queryList(map);
		int total = tMgrEquipmentService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tMgrEquipmentList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("tmgrequipment:info")
	public R info(@PathVariable("id") String id){
		TMgrEquipmentEntity tMgrEquipment = tMgrEquipmentService.queryObject(id);
		
		return R.ok().put("tMgrEquipment", tMgrEquipment);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("tmgrequipment:save")
	public R save(@RequestBody Map paramMap){
		if(paramMap.get("name")!=null&&paramMap.get("code")!=null&paramMap.get("storeid")!=null){
			TMgrEquipmentEntity equip = new TMgrEquipmentEntity();
			equip.setName(paramMap.get("name").toString());
			equip.setCode(paramMap.get("code").toString());
			equip.setRemark(paramMap.get("remark").toString());
			SysUserEntity user = ShiroUtils.getUserEntity();
			equip.setCreateUserId(user.getUserId());
			TMgrStoreEntity store = new TMgrStoreEntity();
			store.setId(paramMap.get("storeid").toString());
			equip.setCreateTime(new Date());
			equip.setStore(store);
			tMgrEquipmentService.save(equip);
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("tmgrequipment:update")
	public R update(@RequestBody Map paramMap){
		if(paramMap.get("name")!=null&&paramMap.get("code")!=null&paramMap.get("storeid")!=null) {
			TMgrEquipmentEntity equip = new TMgrEquipmentEntity();
			equip.setId(paramMap.get("id").toString());
			equip.setName(paramMap.get("name").toString());
			equip.setCode(paramMap.get("code").toString());
			equip.setRemark(paramMap.get("remark").toString());
			TMgrStoreEntity store = new TMgrStoreEntity();
			store.setId(paramMap.get("storeid").toString());
			equip.setStore(store);
			tMgrEquipmentService.update(equip);
		}
		return R.ok();
	}
	/**
	 * 设备停用/启用
	 */
	@ResponseBody
	@RequestMapping("/updateStatus")
	//@RequiresPermissions("tmgrstore:updateStatus")
	public R updateStatus(String id){
		int i = tMgrEquipmentService.updateStatus(id);
		return R.ok();
	}

}
