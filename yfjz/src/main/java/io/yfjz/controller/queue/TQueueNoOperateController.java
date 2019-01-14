package io.yfjz.controller.queue;

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

import io.yfjz.entity.queue.TQueueNoOperateEntity;
import io.yfjz.service.queue.TQueueNoOperateService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-25 02:25:55
 */
@Controller
@RequestMapping("queue")
public class TQueueNoOperateController {
	@Autowired
	private TQueueNoOperateService tQueueNoOperateService;
	
	@RequestMapping("/tqueuenooperate.html")
	public String list(){
		return "tqueuenooperate/tqueuenooperate.html";
	}
	
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
		List<TQueueNoOperateEntity> tQueueNoOperateList = tQueueNoOperateService.queryList(map);
		int total = tQueueNoOperateService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tQueueNoOperateList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TQueueNoOperateEntity tQueueNoOperate = tQueueNoOperateService.queryObject(id);
		
		return R.ok().put("tQueueNoOperate", tQueueNoOperate);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TQueueNoOperateEntity tQueueNoOperate){
		tQueueNoOperateService.save(tQueueNoOperate);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TQueueNoOperateEntity tQueueNoOperate){
		tQueueNoOperateService.update(tQueueNoOperate);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tQueueNoOperateService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
