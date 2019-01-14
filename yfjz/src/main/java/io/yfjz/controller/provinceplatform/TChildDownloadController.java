package io.yfjz.controller.provinceplatform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.yfjz.utils.HttpServletRequestToMapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.provinceplatform.TChildDownloadEntity;
import io.yfjz.service.provinceplatform.TChildDownloadService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 省平台下载新生儿表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 14:33:28
 */
@Controller
@RequestMapping("tchilddownload")
public class TChildDownloadController {
	@Autowired
	private TChildDownloadService tChildDownloadService;
	
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
		List<TChildDownloadEntity> tChildDownloadList = tChildDownloadService.queryList(map);
		int total = tChildDownloadService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tChildDownloadList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TChildDownloadEntity tChildDownload = tChildDownloadService.queryObject(id);
		
		return R.ok().put("tChildDownload", tChildDownload);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TChildDownloadEntity tChildDownload){
		tChildDownloadService.save(tChildDownload);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TChildDownloadEntity tChildDownload){
		tChildDownloadService.update(tChildDownload);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tChildDownloadService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
