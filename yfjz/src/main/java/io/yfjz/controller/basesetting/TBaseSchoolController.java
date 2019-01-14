package io.yfjz.controller.basesetting;

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

import io.yfjz.entity.basesetting.TBaseSchoolEntity;
import io.yfjz.service.basesetting.TBaseSchoolService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 入学入托机构表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-08-09 16:30:51
 */
@Controller
@RequestMapping("tbaseschool")
public class TBaseSchoolController {
	@Autowired
	private TBaseSchoolService tBaseSchoolService;
	
	@RequestMapping("/tbaseschool.html")
	public String list(){
		return "tbaseschool/tbaseschool.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	/*@RequiresPermissions("tbaseschool:list")*/
	public R list(Integer page, Integer limit, HttpServletRequest req){
		Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(req);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TBaseSchoolEntity> tBaseSchoolList = tBaseSchoolService.queryList(map);
		int total = tBaseSchoolService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tBaseSchoolList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{code}")
	public R info(@PathVariable("code") String code){
		TBaseSchoolEntity tBaseSchool = tBaseSchoolService.queryObject(code);
		
		return R.ok().put("tBaseSchool", tBaseSchool);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBaseSchoolEntity tBaseSchool){
		tBaseSchoolService.save(tBaseSchool);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBaseSchoolEntity tBaseSchool){
		tBaseSchoolService.update(tBaseSchool);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] codes){
		tBaseSchoolService.deleteBatch(codes);
		
		return R.ok();
	}
	
}
