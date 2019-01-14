package io.yfjz.controller.child;

import io.yfjz.entity.child.TChildHealthcareEntity;
import io.yfjz.service.child.TChildHealthcareService;
import io.yfjz.service.queue.TQueueNoService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.Query;
import io.yfjz.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


/**
 * 儿童体检记录表
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-24 15:22:38
 */
@Controller
@RequestMapping("tchildhealthcare")
public class TChildHealthcareController {
	@Autowired
	private TChildHealthcareService tChildHealthcareService;
	@Autowired
	private TQueueNoService tQueueNoService;
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map) throws InvocationTargetException, IllegalAccessException {
		Query query = new Query(map);
		//查询列表数据
		List<TChildHealthcareEntity> tChildHealthcareList = tChildHealthcareService.queryList(query);


		int total = tChildHealthcareService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tChildHealthcareList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TChildHealthcareEntity tChildHealthcare = tChildHealthcareService.queryObject(id);
		
		return R.ok().put("tChildHealthcare", tChildHealthcare);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TChildHealthcareEntity tChildHealthcare, HttpServletRequest request){

		tChildHealthcareService.save(tChildHealthcare);
		//调用队列通知接口
		tQueueNoService.finishedCurrentStep("healthcare",request.getParameter("sequenceNoId"),request.getParameter("chilCode"),request.getParameter("chilName"));
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TChildHealthcareEntity tChildHealthcare){
		tChildHealthcareService.update(tChildHealthcare);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tChildHealthcareService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
