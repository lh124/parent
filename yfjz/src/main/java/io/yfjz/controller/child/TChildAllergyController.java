package io.yfjz.controller.child;

import io.yfjz.entity.child.TChildAllergyEntity;
import io.yfjz.service.child.TChildAllergyService;
import io.yfjz.utils.HttpServletRequestToMapUtils;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.Query;
import io.yfjz.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 儿童过敏表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-28 13:43:42
 */
@Controller
@RequestMapping("tchildallergy")
public class TChildAllergyController {
	@Autowired
	private TChildAllergyService tChildAllergyService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
//		Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(req);
//		/*Map<String, Object> map = new HashMap<>();*/
//		map.put("offset", (page - 1) * limit);
//		map.put("limit", limit);
		Query query = new Query(params);
		//查询列表数据
		List<TChildAllergyEntity> tChildAllergyList = tChildAllergyService.queryList(query);
		int total = tChildAllergyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tChildAllergyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TChildAllergyEntity tChildAllergy = tChildAllergyService.queryObject(id);
		
		return R.ok().put("tChildAllergy", tChildAllergy);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TChildAllergyEntity tChildAllergy){

		tChildAllergyService.save(tChildAllergy);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TChildAllergyEntity tChildAllergy,HttpServletRequest req){
		tChildAllergyService.update(tChildAllergy);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tChildAllergyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
