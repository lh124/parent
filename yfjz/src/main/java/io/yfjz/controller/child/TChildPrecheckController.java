package io.yfjz.controller.child;

import io.yfjz.entity.child.TChildPrecheckEntity;
import io.yfjz.service.child.TChildPrecheckService;
import io.yfjz.service.queue.TQueueNoService;
import io.yfjz.utils.HttpServletRequestToMapUtils;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.Query;
import io.yfjz.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 预检信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-23 09:41:20
 */
@Controller
@RequestMapping("tchildprecheck")
public class TChildPrecheckController {
	@Autowired
	private TChildPrecheckService tChildPrecheckService;
	@Autowired
	private TQueueNoService tQueueNoService;
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("tchildprecheck:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		
		//查询列表数据
		List<TChildPrecheckEntity> tChildPrecheckList = tChildPrecheckService.queryList(query);
		int total = tChildPrecheckService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tChildPrecheckList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("tchildprecheck:info")
	public R info(@PathVariable("id") String id){
		TChildPrecheckEntity tChildPrecheck = tChildPrecheckService.queryObject(id);
		
		return R.ok().put("tChildPrecheck", tChildPrecheck);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions("tchildprecheck:save")
	public R save(@RequestBody TChildPrecheckEntity tChildPrecheck, HttpServletRequest request){
		tChildPrecheckService.save(tChildPrecheck);
//		//调用队列通知接口
//		tQueueNoService.finishedCurrentStep("precheck",request.getParameter("sequenceNoId"),request.getParameter("chilCode"),request.getParameter("chilName"));
		return R.ok();
	}
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/saveCheckNotice")
	public R saveCheckNotice (HttpServletRequest request){
		Map<String, Object> param = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
		TChildPrecheckEntity tChildPrecheck = new TChildPrecheckEntity();
		tChildPrecheck.setChilCode(param.get("chilCode").toString());
		tChildPrecheck.setCreateUserName(param.get("createUserName").toString());
		tChildPrecheck.setTemp(param.get("temp")==null?"":param.get("temp").toString());
		tChildPrecheckService.saveCheckNotice(tChildPrecheck);
//		//调用队列通知接口
//		tQueueNoService.finishedCurrentStep("precheck",request.getParameter("sequenceNoId"),request.getParameter("chilCode"),request.getParameter("chilName"));
		return R.ok();
	}


	/**
	 * 完成预检
	 */
	@ResponseBody
	@RequestMapping("/precheck")
	public R precheck( HttpServletRequest request){
		tQueueNoService.finishedCurrentStep("precheck",request.getParameter("sequenceNoId"),request.getParameter("childCode"),request.getParameter("childName"));
		return R.ok();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("tchildprecheck:update")
	public R update(@RequestBody TChildPrecheckEntity tChildPrecheck){
		tChildPrecheckService.update(tChildPrecheck);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions("tchildprecheck:delete")
	public R delete(@RequestBody String[] ids){
		tChildPrecheckService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
