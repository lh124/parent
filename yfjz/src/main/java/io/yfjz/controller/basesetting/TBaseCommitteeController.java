package io.yfjz.controller.basesetting;

import java.util.List;
import java.util.Map;

import io.yfjz.entity.basesetting.TBaseCommitteeEntity;
import io.yfjz.utils.HttpServletRequestToMapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.yfjz.service.basesetting.TBaseCommitteeService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 居委会表(居委会/行政村)
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-28 10:45:07
 */
@Controller
@RequestMapping("tbasecommittee")
public class TBaseCommitteeController {
	@Autowired
	private TBaseCommitteeService tBaseCommitteeService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	/*@RequiresPermissions("tbasecommittee:list")*/
	public R list(Integer page, Integer limit, HttpServletRequest req){
		Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(req);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TBaseCommitteeEntity> tBaseCommitteeList = tBaseCommitteeService.queryList(map);
		int total = tBaseCommitteeService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tBaseCommitteeList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{code}")
	public R info(@PathVariable("code") String code){
		TBaseCommitteeEntity tBaseCommittee = tBaseCommitteeService.queryObject(code);
		
		return R.ok().put("tBaseCommittee", tBaseCommittee);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBaseCommitteeEntity tBaseCommittee){
		tBaseCommitteeService.save(tBaseCommittee);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBaseCommitteeEntity tBaseCommittee){
		tBaseCommitteeService.update(tBaseCommittee);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] codes){
		tBaseCommitteeService.deleteBatch(codes);
		
		return R.ok();
	}
	
}
