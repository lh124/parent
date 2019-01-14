package io.yfjz.controller.queue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.queue.TQueueNoticeEntity;
import io.yfjz.service.queue.TQueueNoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

@Controller
@RequestMapping("queue/notice")
public class TQueueNoticeController {
	@Autowired
	private TQueueNoticeService tQueueNoticeService;

	@RequestMapping("/tqueuenotice.html")
	public String list(){
		return "tqueuenotice/tqueuenotice.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", 0);
		map.put("limit", 10);

		//查询列表数据
		List<TQueueNoticeEntity> tQueueNoticeList = tQueueNoticeService.queryList(null);
		int total = tQueueNoticeService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(tQueueNoticeList, total, 10, 0);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TQueueNoticeEntity tQueueNotice = tQueueNoticeService.queryObject(id);
		
		return R.ok().put("tQueueNotice", tQueueNotice);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TQueueNoticeEntity tQueueNotice){
		tQueueNoticeService.save(tQueueNotice);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TQueueNoticeEntity tQueueNotice){
		tQueueNoticeService.update(tQueueNotice);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tQueueNoticeService.deleteBatch(ids);
		return R.ok();
	}
	
}
