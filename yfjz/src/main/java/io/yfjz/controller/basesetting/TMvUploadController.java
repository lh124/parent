package io.yfjz.controller.basesetting;

import io.yfjz.entity.basesetting.TMvUploadEntity;
import io.yfjz.service.basesetting.TMvUploadService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 保存app上传的文件基本信息表，每个Android盒子需要播放视频的路径
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-23 15:50:15
 */
@Controller
@RequestMapping("tmvupload")
public class TMvUploadController {
	@Autowired
	private TMvUploadService tMvUploadService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("tmvupload:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TMvUploadEntity> tMvUploadList = tMvUploadService.queryList(map);
		int total = tMvUploadService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tMvUploadList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}


	/**
	 * @method_name: queryListAndPlayerArea
	 * @describe: 视频关联播放台列表
	 * @param [page, limit]
	 * @return io.yfjz.utils.R
	 * @author 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/9/23  15:22
	 **/
	@ResponseBody
	@RequestMapping("/queryListAndPlayerArea")
	public R queryListAndPlayerArea(){
		//查询列表数据
		List<HashMap> tMvUploadList = tMvUploadService.queryListAndPlayerArea();
		PageUtils pageUtil = new PageUtils(tMvUploadList, 100, 100, 1);
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("tmvupload:info")
	public R info(@PathVariable("id") String id){
		TMvUploadEntity tMvUpload = tMvUploadService.queryObject(id);
		
		return R.ok().put("tMvUpload", tMvUpload);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions("tmvupload:save")
	public R save(@RequestBody TMvUploadEntity tMvUpload){
		tMvUploadService.save(tMvUpload);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("tmvupload:update")
	public R update(@RequestBody TMvUploadEntity tMvUpload){
		tMvUploadService.update(tMvUpload);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions("tmvupload:delete")
	public R delete(@RequestBody String[] ids){
		tMvUploadService.deleteBatch(ids);
		
		return R.ok();
	}


	/**
	 * @method_name: saveRelevance
	 * @describe: 视频关联播放台
	 * @param: [request, response, paramMap]
	 * @return: void
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/7/9  16:25
	 **/
	@ResponseBody
	@RequestMapping("/saveRelevance")
	public R saveRelevance(HttpServletRequest request, HttpServletResponse response, @RequestBody List<Map> paramMap) {
		return tMvUploadService.saveRelevace(paramMap);
	}

	@ResponseBody
	@RequestMapping("/lisByMvid/{id}")
	public R lisByMvid(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		return tMvUploadService.getTowerListByMvId(id);
	}


}
