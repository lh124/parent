package io.yfjz.controller.mgr;

import io.yfjz.entity.mgr.TMgrStockBaseEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.mgr.TMgrStockBaseService;
import io.yfjz.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 库存产品基础信息表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-07-26 11:13:14
 */
@Controller
@RequestMapping("tmgrstockbase")
public class TMgrStockBaseController {
	@Autowired
	private TMgrStockBaseService tMgrStockBaseService;

	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("tmgrstockbase:list")
	public R list(Integer page, Integer limit, String searchProductName, String searchFactoryName, String searchBatch, String  searchDate, Integer searchType ) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);

		if(!StringUtils.isEmpty(searchProductName)){
		  	map.put("searchProductName", searchProductName);
		}
		if(!StringUtils.isEmpty(searchFactoryName)){
			map.put("searchFactoryName", searchFactoryName);
		}
		if(!StringUtils.isEmpty(searchBatch)){
			map.put("searchBatch", searchBatch);
		}
		if(!StringUtils.isEmpty(searchDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(searchDate);
			Date addDays = DateUtils.addDays(date, 1);
			map.put("searchDate", addDays);
		}
		if(searchType!=null){
			map.put("searchType", searchType);
		}

		//查询列表数据
		List<TMgrStockBaseEntity> tMgrStockBaseList = tMgrStockBaseService.queryList(map);
		int total = tMgrStockBaseService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tMgrStockBaseList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
    /**
     * 查询所有的数据，不分页
     */
    @ResponseBody
    @RequestMapping("/allData")
    //@RequiresPermissions("tmgrstockbase:list")
    public R getAllData(){
        //查询列表数据
        List<TMgrStockBaseEntity> tMgrStockBaseList = tMgrStockBaseService.getAllData();

        PageUtils pageUtil = new PageUtils(tMgrStockBaseList, 1, 1, 1);

        return R.ok().put("page", pageUtil);
    }
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("tmgrstockbase:info")
	public R info(@PathVariable("id") String id){
		TMgrStockBaseEntity tMgrStockBase = tMgrStockBaseService.queryObject(id);
		
		return R.ok().put("tMgrStockBase", tMgrStockBase);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	///@RequiresPermissions("tmgrstockbase:save")
	public R save(@RequestBody TMgrStockBaseEntity tMgrStockBase){
		if (tMgrStockBase!=null){
			tMgrStockBase.setCreateTime(new Date());
			tMgrStockBase.setStatus(0);
            SysUserEntity user = ShiroUtils.getUserEntity();
            tMgrStockBase.setFkCreateUserId(user.getUserId());
//            tMgrStockBase.setOrgId(user.getOrgId());
			tMgrStockBase.setOrgId(Constant.GLOBAL_ORG_ID);
		}
		try {
			tMgrStockBaseService.save(tMgrStockBase);
		} catch (RRException e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常，请联系管理员");
		}

		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("tmgrstockbase:update")
	public R update(@RequestBody TMgrStockBaseEntity tMgrStockBase){

		try {
			tMgrStockBaseService.update(tMgrStockBase);
		} catch (RRException e) {
			e.printStackTrace();
			return R.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return R.error("系统异常，请联系管理员");
		}
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("tmgrstockbase:delete")
	public R delete(@RequestBody String[] ids){
		tMgrStockBaseService.deleteBatch(ids);
		
		return R.ok();
	}
    /**
     * 仓库停用/启用
     */
    @ResponseBody
    @RequestMapping("/updateStatus")
    //@RequiresPermissions("tmgrstore:updateStatus")
    public R updateStatus(String id){
        tMgrStockBaseService.updateStatus(id);
        return R.ok();
    }
	/** 
	* @Description: 查询最近添加的疫苗信息
	* @Param: [code] 
	* @return: io.yfjz.utils.R 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/10/12 16:14
	* @Tel  17328595627
	*/ 
	@ResponseBody
	@RequestMapping("/getCodeInfo")
	//@RequiresPermissions("tmgrstockbase:info")
	public R getCodeInfo( String code){
		TMgrStockBaseEntity tMgrStockBase=null;
		if (!StringUtils.isEmpty(code)){
		    tMgrStockBase = tMgrStockBaseService.getCodeInfo(code);
		}

		return R.ok().put("tMgrStockBase", tMgrStockBase);
	}

	/**
	 * 获取所有疫苗批号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllBatchnum")
	public R getAllBatchnum(){
		 List<TMgrStockBaseEntity> list =tMgrStockBaseService.getAllBatchnum();
		return R.ok().put("data", list);
	}

}
