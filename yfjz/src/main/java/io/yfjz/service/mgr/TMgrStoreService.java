package io.yfjz.service.mgr;


import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.mgr.TMgrStoreEntity;
import org.aspectj.apache.bcel.classfile.ConstantNameAndType;

import java.util.List;
import java.util.Map;

/** 
* @Description: 仓库管理接口
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 13:44
* @Tel  17328595627
*/ 
public interface TMgrStoreService {
	/** 
	* @Description: 根据ID查询某个仓库 
	* @Param: [id] 
	* @return: io.yfjz.entity.mgr.TMgrStoreEntity 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 13:45
	* @Tel  17328595627
	*/ 
	TMgrStoreEntity queryObject(String id);
	/** 
	* @Description: 查询所有的仓库 
	* @Param: [map] 
	* @return: java.util.List<io.yfjz.entity.mgr.TMgrStoreEntity> 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 13:45
	* @Tel  17328595627
	*/ 
	List<TMgrStoreEntity> queryList(Map<String, Object> map);
	/** 
	* @Description: 查询仓库的总数量 
	* @Param: [map] 
	* @return: int 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 13:46
	* @Tel  17328595627
	*/ 
	int queryTotal(Map<String, Object> map);
	/** 
	* @Description: 新增仓库 
	* @Param: [tMgrStore] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 13:46
	* @Tel  17328595627
	*/ 
	void save(TMgrStoreEntity tMgrStore);
	/** 
	* @Description: 更新仓库信息
	* @Param: [tMgrStore] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 13:46
	* @Tel  17328595627
	*/ 
	void update(TMgrStoreEntity tMgrStore);
	/** 
	* @Description: 根据ID删除某个仓库
	* @Param: [id] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 13:47
	* @Tel  17328595627
	*/ 
	void delete(String id);
	/** 
	* @Description: 批量删除 
	* @Param: [ids] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 13:53
	* @Tel  17328595627
	*/ 
	void deleteBatch(String[] ids);
	/** 
	* @Description: 停用设备 
	* @Param: [id] 
	* @return: int 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 14:09
	* @Tel  17328595627
	*/ 
	int updateStatus(String id);
    /** 
    * @Description: 仓库关联设备 
    * @Param: [storeId, equipmentIds] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/7/23 15:45
    * @Tel  17328595627
    */ 
	int relationEquipment(String storeId, String[] equipmentIds);
	/** 
	* @Description: 根据仓库ID删除关联的设备 
	* @Param:
	* @return:  
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/23 16:21
	* @Tel  17328595627
	*/
	int deleteRelation(String storeId);
	/**
	 * 查询工作台
	 * @return
	 */
	List<TBaseTowerEntity> getToweSrList();

	/**
	 * @method_name: getToweSrListByIds
	 * @describe: 查询工作台根据id来获取
	 * @param: [towers]
	 * @return: java.util.List<io.yfjz.entity.basesetting.TBaseTowerEntity> 
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/8/29  17:37
	 **/
	List<TBaseTowerEntity> getToweSrListByIds(List<String> towers);

	/**
	 * 查询所有启用的仓库
	 * @return
	 */
    List<TMgrStoreEntity> getAllStore(int type);
	/** 
	* @Description: 查询仓库绑定的设备 
	* @Param: [storeId] 
	* @return: String
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/6 11:19
	* @Tel  17328595627
	*/ 
	String getEquipmentById(String storeId);

}
