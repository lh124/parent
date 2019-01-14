package io.yfjz.controller.provinceplatform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.utils.HttpServletRequestToMapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yfjz.entity.provinceplatform.TChildElsewhereEntity;
import io.yfjz.service.provinceplatform.TChildElsewhereService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 异地儿童接种信息
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685413726
 * @date 2018-09-12 11:22:13
 */
@Controller
@RequestMapping("tchildelsewhere")
public class TChildElsewhereController {
	@Autowired
	private TChildElsewhereService tChildElsewhereService;
	
	@RequestMapping("/tchildelsewhere.html")
	public String list(){
		return "tchildelsewhere/tchildelsewhere.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(Integer page, Integer limit, HttpServletRequest request){
		Map<String,Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TChildElsewhereEntity> tChildElsewhereList = tChildElsewhereService.queryList(map);
		int total = tChildElsewhereService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tChildElsewhereList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TChildElsewhereEntity tChildElsewhere = tChildElsewhereService.queryObject(id);
		
		return R.ok().put("tChildElsewhere", tChildElsewhere);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TChildElsewhereEntity tChildElsewhere){
		tChildElsewhereService.save(tChildElsewhere);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TChildElsewhereEntity tChildElsewhere){
		tChildElsewhereService.update(tChildElsewhere);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tChildElsewhereService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
