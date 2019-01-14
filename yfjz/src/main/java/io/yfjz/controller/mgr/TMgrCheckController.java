package io.yfjz.controller.mgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.mgr.TMgrCheckEntity;
import io.yfjz.entity.mgr.TMgrCheckItemEntity;
import io.yfjz.service.mgr.TMgrCheckItemService;
import io.yfjz.service.mgr.TMgrCheckService;
import io.yfjz.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/** 
* @Description: 盘点表
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/8 15:38
* @Tel  17328595627
*/ 
@Controller
@RequestMapping("tmgrcheck")
public class TMgrCheckController {
	@Autowired
	private TMgrCheckService tMgrCheckService;

	@Autowired
	private TMgrCheckItemService itemService;

	@RequestMapping("/tmgrcheck.html")
	public String list(){
		return "tmgrcheck/tmgrcheck.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("tmgrcheck:list")
	public R list(Integer page, Integer limit,String searchOrder,String searchUser,String startDate,String endDate){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("searchOrder",searchOrder);
		map.put("searchUser",searchUser);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (!org.springframework.util.StringUtils.isEmpty(startDate)){
				Date start = sdf.parse(startDate);
				map.put("startDate",start);
			}
			if (!org.springframework.util.StringUtils.isEmpty(endDate)){
				Date end = sdf.parse(endDate);
				Date date = DateUtils.addDays(end, 1);
				map.put("endDate",date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//查询列表数据
		List<TMgrCheckEntity> tMgrCheckList = tMgrCheckService.queryList(map);
		int total = tMgrCheckService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tMgrCheckList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 盘点明细查询
	 */
	@ResponseBody
	@RequestMapping("/itemList/{totalId}")
	//@RequiresPermissions("tmgrcheck:list")
	public R list(Integer page, Integer limit,@PathVariable("totalId") String totalId){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		if (!StringUtils.isEmpty(totalId)){
			map.put("totalId",totalId);
		}
		//查询列表数据
		List<Map<String,Object>> tMgrCheckList = itemService.queryListMap(map);
		int total = itemService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(tMgrCheckList, total, limit, page);

		return R.ok().put("page", pageUtil);
	}


	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tmgrcheck:info")
	public R info(@PathVariable("id") String id){
		TMgrCheckEntity tMgrCheck = tMgrCheckService.queryObject(id);
		
		return R.ok().put("tMgrCheck", tMgrCheck);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("tmgrcheck:save")
	public R save(@RequestBody Map param ){
		try {
			tMgrCheckService.saveResult(param);
		} catch (RRException e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常，请联系管理员！");
		}

		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tmgrcheck:update")
	public R update(@RequestBody TMgrCheckEntity tMgrCheck){
		tMgrCheckService.update(tMgrCheck);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tmgrcheck:delete")
	public R delete(@RequestBody String[] ids){
		tMgrCheckService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
