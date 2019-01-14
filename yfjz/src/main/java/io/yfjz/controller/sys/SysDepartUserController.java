package io.yfjz.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.sys.SysDepartUserEntity;
import io.yfjz.service.sys.SysDepartUserService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 角色与菜单对应关系
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-17 13:57:20
 */
@Controller
@RequestMapping("sysdepartuser")
public class SysDepartUserController {
	@Autowired
	private SysDepartUserService sysDepartUserService;
	
	@RequestMapping("/sysdepartuser.html")
	public String list(){
		return "sysdepartuser/sysdepartuser.html";
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
		List<SysDepartUserEntity> sysDepartUserList = sysDepartUserService.queryList(map);
		int total = sysDepartUserService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(sysDepartUserList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 根据用户编号查询角色信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDepartList")
	public R getDepartList(String userId){
	    if(StringUtils.isBlank(userId)){
            userId = ShiroUtils.getUserEntity().getUserId();
        }
		List<SysDepartUserEntity> depart = sysDepartUserService.getDepartList(userId);
		return R.ok().put("depart", depart);
	}
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		SysDepartUserEntity sysDepartUser = sysDepartUserService.queryObject(id);
		
		return R.ok().put("sysDepartUser", sysDepartUser);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody SysDepartUserEntity sysDepartUser){
		sysDepartUserService.save(sysDepartUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody SysDepartUserEntity sysDepartUser){
		sysDepartUserService.update(sysDepartUser);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		sysDepartUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
