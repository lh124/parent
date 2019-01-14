package io.yfjz.controller.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.utils.Query;
import io.yfjz.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.sys.SysDepartEntity;
import io.yfjz.service.sys.SysDepartService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 机构、部门信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-16 23:03:13
 */
@Controller
@RequestMapping("/sys/depart")
public class SysDepartController {
	@Autowired
	private SysDepartService sysDepartService;
	
	@RequestMapping("/sysdepart.html")
	public String list(){
		return "sysdepart/sysdepart.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		//查询列表数据
		List<SysDepartEntity> sysDepartList = sysDepartService.queryList(query);
		int total = sysDepartService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysDepartList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/listDepart")
	public R listDepart(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		//查询列表数据
		List<SysDepartEntity> sysDepartList = sysDepartService.queryListDepart(query);
		int total = sysDepartService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(sysDepartList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * @method_name: getDepartMenuTree
	 * @describe: 获取所有机构组织树
	 * @param: [request, response]
	 * @return: io.yfjz.utils.R
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/16  23:09
	 **/
	@ResponseBody
	@RequestMapping({"/departMenuTree"})
	public R getDepartMenuTree(HttpServletRequest request,HttpServletResponse response) {
		Long pid = null;
		try {
			pid = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			pid = 0L;
		}
		Map<String, Object> map = new HashMap<>();
//		map.put("pid",pid);
		List departMenuList = this.sysDepartService.queryList(map);
		return R.ok().put("departMenuList", departMenuList);
	}

	@ResponseBody
	@RequestMapping({"/queryDepart"})
	public R queryDepart(String orgId){

		if(StringUtils.isBlank(orgId)) {
			throw new RRException("机构编号不能为空");
		}
		List<SysDepartEntity> depart =  sysDepartService.queryDepart(orgId);
		return R.ok().put("departMenuList", depart);
	}

	@ResponseBody
	@RequestMapping({"/getAsyncDepartMenuTree"})
	public R getAsyncDepartMenuTree(HttpServletRequest request,HttpServletResponse response){
		Long pid = null;
		try {
			pid = Long.parseLong(request.getParameter("pid"));
		} catch (NumberFormatException e) {
			pid = 0L;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("pid",pid);
		List departMenuList = this.sysDepartService.getAsyncDepartMenuTree(map);
		return R.ok().put("departMenuList", departMenuList);
	}

	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		SysDepartEntity sysDepart = sysDepartService.queryObject(id);

		return R.ok().put("sysDepart", sysDepart);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody SysDepartEntity sysDepart){
		sysDepart.setCreateTime(new Date());
		sysDepart.setStatus(0);//0启用，1禁用
		sysDepartService.save(sysDepart);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody SysDepartEntity sysDepart){
		sysDepartService.update(sysDepart);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		sysDepartService.deleteBatch(ids);
//		sysDepartService.deleteBatch(ids);
		return R.ok();
	}

	@ResponseBody
	@RequestMapping("/updateStatus")
	public R updateStatus(@RequestBody String[] ids){
		sysDepartService.updateStatus(ids);

		return R.ok();
	}
}
