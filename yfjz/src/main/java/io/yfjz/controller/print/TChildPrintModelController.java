package io.yfjz.controller.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.print.TChildPrintModelEntity;
import io.yfjz.utils.HttpServletRequestToMapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yfjz.service.print.TChildPrintModelService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 儿童打印模板
 *
 * @author饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-08-17 11:27:06
 */
@Controller
@RequestMapping("tchildprintmodel")
public class TChildPrintModelController {
	@Autowired
	private TChildPrintModelService tChildPrintModelService;
	
	@RequestMapping("/tchildprintmodel.html")
	public String list(){
		return "tchildprintmodel/tchildprintmodel.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	/*@RequiresPermissions("tchildprintmodel:list")*/
	public R list(Integer page, Integer limit, HttpServletRequest req){
		Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(req);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TChildPrintModelEntity> tChildPrintModelList = tChildPrintModelService.queryList(map);
		int total = tChildPrintModelService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tChildPrintModelList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tchildprintmodel:info")
	public R info(@PathVariable("id") Long id){
		TChildPrintModelEntity tChildPrintModel = tChildPrintModelService.queryObject(id);
		
		return R.ok().put("tChildPrintModel", tChildPrintModel);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tchildprintmodel:save")
	public R save(@RequestBody TChildPrintModelEntity tChildPrintModel){
		tChildPrintModelService.save(tChildPrintModel);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tchildprintmodel:update")
	public R update(@RequestBody TChildPrintModelEntity tChildPrintModel){
		tChildPrintModelService.update(tChildPrintModel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	/*@RequiresPermissions("tchildprintmodel:delete")*/
	public R delete(@RequestBody Long[] ids){
		tChildPrintModelService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
