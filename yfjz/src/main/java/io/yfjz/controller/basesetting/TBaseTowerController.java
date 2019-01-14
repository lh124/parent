package io.yfjz.controller.basesetting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.service.basesetting.TBaseTowerService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;


/**
 * 工作台基本信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-24 11:01:00
 */
@Controller
@RequestMapping("tbasetower")
public class TBaseTowerController {
	@Autowired
	private TBaseTowerService tBaseTowerService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("tbasetower:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TBaseTowerEntity> tBaseTowerList = tBaseTowerService.queryList(map);
		int total = tBaseTowerService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tBaseTowerList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/listAll")
    public R listAll(){
        Map<String, Object> map = new HashMap<>();
        //查询列表数据
        List<TBaseTowerEntity> tBaseTowerList = tBaseTowerService.queryList(map);
		/*暂时使用“村”字判断村台，不推送队列*/
		if (tBaseTowerList != null && !tBaseTowerList.isEmpty()){
			Iterator<TBaseTowerEntity> towerIterator = tBaseTowerList.iterator();
			while (towerIterator.hasNext()){
				TBaseTowerEntity tower = towerIterator.next();
				String towerName =  tower.getTowerName();
				if (towerName != null && (towerName.contains("村") || towerName.contains("社区"))){
					towerIterator.remove();
				}
			}
		}
        return R.ok().put("tower", tBaseTowerList);
    }
    /**
     * 获取已经登录的工作台
     */
    @ResponseBody
    @RequestMapping("/listLoginAll")
    public R listLoginAll(){
        //查询列表数据
        List<TBaseTowerEntity> tBaseTowerList = tBaseTowerService.getYetLoginTowers();
        return R.ok().put("tower", tBaseTowerList);
    }
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id){
		TBaseTowerEntity tBaseTower = tBaseTowerService.queryObject(id);
		
		return R.ok().put("tBaseTower", tBaseTower);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody TBaseTowerEntity tBaseTower){
		tBaseTowerService.save(tBaseTower);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody TBaseTowerEntity tBaseTower){
		tBaseTowerService.update(tBaseTower);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody String[] ids){
		tBaseTowerService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
