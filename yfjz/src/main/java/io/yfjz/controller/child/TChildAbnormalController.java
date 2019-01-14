package io.yfjz.controller.child;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.yfjz.controller.AbstractController;
import io.yfjz.utils.HttpServletRequestToMapUtils;
import io.yfjz.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.child.TChildAbnormalEntity;
import io.yfjz.service.child.TChildAbnormalService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 儿童异常反应表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */
@Controller
@RequestMapping("tchildabnormal")
public class TChildAbnormalController extends AbstractController {
	@Autowired
	private TChildAbnormalService tChildAbnormalService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		//查询列表数据
		List<TChildAbnormalEntity> tChildAbnormalList = tChildAbnormalService.queryList(query);
		int total = tChildAbnormalService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tChildAbnormalList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TChildAbnormalEntity tChildAbnormal = tChildAbnormalService.queryObject(id);
		
		return R.ok().put("tChildAbnormal", tChildAbnormal);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TChildAbnormalEntity tChildAbnormal){
		//tChildAbnormal.setChilCode(req.getParameter("chilCode"));
		tChildAbnormalService.save(tChildAbnormal);

		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TChildAbnormalEntity tChildAbnormal,HttpServletRequest req){
		tChildAbnormal.setChilCode(req.getParameter("chilCode"));
		tChildAbnormalService.update(tChildAbnormal);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tChildAbnormalService.deleteBatch(ids);
		
		return R.ok();
	}

}
