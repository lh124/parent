package io.yfjz.controller.basesetting;

import io.yfjz.entity.basesetting.TMvRelevanceEntity;
import io.yfjz.service.basesetting.TMvRelevanceService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 保存app上传的文件基本信息表，每个Android盒子需要播放视频的路径
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-23 17:24:51
 */
@Controller
@RequestMapping("tmvrelevance")
public class TMvRelevanceController {
	@Autowired
	private TMvRelevanceService tMvRelevanceService;
	
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
		List<TMvRelevanceEntity> tMvRelevanceList = tMvRelevanceService.queryList(map);
		int total = tMvRelevanceService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tMvRelevanceList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TMvRelevanceEntity tMvRelevance = tMvRelevanceService.queryObject(id);
		
		return R.ok().put("tMvRelevance", tMvRelevance);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TMvRelevanceEntity tMvRelevance){
		tMvRelevanceService.save(tMvRelevance);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TMvRelevanceEntity tMvRelevance){
		tMvRelevanceService.update(tMvRelevance);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tMvRelevanceService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
