package io.yfjz.controller.inocpointmgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.entity.mgr.TBaseOrgInfoEntity;
import io.yfjz.service.inocpointmgr.TBaseOrgInfoService;
import io.yfjz.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 卫生院基本信息表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-09-04 17:03:45
 */
@Controller
@RequestMapping("tbaseorginfo")
public class TBaseOrgInfoController {
	@Autowired
	private TBaseOrgInfoService tBaseOrgInfoService;
	
	@RequestMapping("/tbaseorginfo.html")
	public String list(){
		return "tbaseorginfo/tbaseorginfo.html";
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
		List<TBaseOrgInfoEntity> tBaseOrgInfoList = tBaseOrgInfoService.queryList(map);
		int total = tBaseOrgInfoService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tBaseOrgInfoList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TBaseOrgInfoEntity tBaseOrgInfo = tBaseOrgInfoService.queryObject(id);
		
		return R.ok().put("tBaseOrgInfo", tBaseOrgInfo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBaseOrgInfoEntity tBaseOrgInfo){
		tBaseOrgInfo.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
		tBaseOrgInfo.setCreateUserName(ShiroUtils.getUserEntity().getRealName());
		tBaseOrgInfoService.save(tBaseOrgInfo);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBaseOrgInfoEntity tBaseOrgInfo){
		tBaseOrgInfoService.update(tBaseOrgInfo);
		
		return R.ok();
	}
	@ResponseBody
	@RequestMapping("/updatephone")
	public R updatephone(HttpServletRequest request){
		TBaseOrgInfoEntity  tBaseOrgInfo =tBaseOrgInfoService.queryObject(request.getParameter("id"));
		tBaseOrgInfo.setId(request.getParameter("id"));
		tBaseOrgInfo.setPhone(request.getParameter("phone"));
		tBaseOrgInfoService.update(tBaseOrgInfo);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tBaseOrgInfoService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
