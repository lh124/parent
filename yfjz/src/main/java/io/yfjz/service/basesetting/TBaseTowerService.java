package io.yfjz.service.basesetting;

import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 工作台基本信息表
 * 
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-24 11:01:00
 */
public interface TBaseTowerService {
	
	TBaseTowerEntity queryObject(String id);
	
	List<TBaseTowerEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TBaseTowerEntity tBaseTower);
	
	void update(TBaseTowerEntity tBaseTower);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	List<String> getAllTowers();

	/**
	 * @method_name: getTowerList
	 * @describe: 根据工作台的类型获取工作台
	 * @param: [towerTypes]
	 * @return: java.util.List<java.util.Map>
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/27  17:08
	 **/
    List<Map> getTowerList();

	List<TBaseTowerEntity> getLoginTowerList(Integer type);

	/**
	 * 查询接种台，条件：已启动的、可接种该疫苗、排队人数最少的台。
	 * @describe:
	 * @param bioIds 接种疫苗id集合
	 * @return java.lang.String
	 * @author 邓召仕
	 * @date: 2018-10-06  12:12
	 **/
	R synergicPosition(List<String> bioIds);

	/**
     * 根据工作台类型查询已经登录了的工作台
     * @describe:
     * @param type 工作台类型
     * @return java.util.List<io.yfjz.entity.basesetting.TBaseTowerEntity>
     * @author 邓召仕
     * @date: 2018-10-08  15:37
     **/
    List<TBaseTowerEntity> getYetLoginTowers(Integer type);
    /**
	 * 获取所以已经登录了的工作台
	 * @describe:
	 * @return java.util.List<io.yfjz.entity.basesetting.TBaseTowerEntity>
	 * @author 邓召仕
	 * @date: 2018-10-13  14:44
	 **/
    List<TBaseTowerEntity> getYetLoginTowers();
}
