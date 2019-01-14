package io.yfjz.controller.bus;

import io.yfjz.entity.bus.TBusCancelEntity;
import io.yfjz.service.bus.TBusCancelService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.Query;
import io.yfjz.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


/**
 * 取消原因表
 * 
 * @author 廖欢
 * @email 1215077166@qq.com
 * @tel 16685005812
 * @date 2018-07-26 09:54:38
 */
@Controller
@RequestMapping("tbuscancel")
public class TBusCancelController {
	@Autowired
	private TBusCancelService tBusCancelService;

    /**
    * 列表
    */
    @RequestMapping("/list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        //查询列表数据
        Query query = new Query(params);
        List<TBusCancelEntity> tBusCancelList = tBusCancelService.queryList(query);

        int total = tBusCancelService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(tBusCancelList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }



    /**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TBusCancelEntity tBusCancel = tBusCancelService.queryObject(id);
		
		return R.ok().put("tBusCancel", tBusCancel);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBusCancelEntity tBusCancel,HttpServletRequest request){
		tBusCancelService.saveRelsult(tBusCancel,request.getParameter("chilCode"));
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBusCancelEntity tBusCancel){
		tBusCancelService.update(tBusCancel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tBusCancelService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
