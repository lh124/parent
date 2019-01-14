package io.yfjz.service.mgr;



import io.yfjz.entity.mgr.TMgrStockInfoEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/** 
* @Description: 库存信息表 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/7/23 16:47
* @Tel  17328595627
*/ 
public interface TMgrStockInfoService {
	/** 
	* @Description: 根据id查询某个疫苗库存信息 
	* @Param: [id] 
	* @return: io.yfjz.entity.mgr.TMgrStockInfoEntity 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/24 9:02
	* @Tel  17328595627
	*/ 
	TMgrStockInfoEntity queryObject(String id);
	/** 
	* @Description: 查询所有的库存疫苗信息 
	* @Param: [map] 
	* @return: java.util.List<io.yfjz.entity.mgr.TMgrStockInfoEntity> 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/24 9:02
	* @Tel  17328595627
	*/
	List<Map<String, Object>> queryList(Map<String, Object> map);
	/** 
	* @Description: 查询疫苗总数量 
	* @Param: [map] 
	* @return: int 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/24 9:03
	* @Tel  17328595627
	*/ 
	int queryTotal(Map<String, Object> map);
	/** 
	* @Description: 新增某个库存疫苗
	* @Param: [tMgrStockInfo] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/24 9:04
	* @Tel  17328595627
	*/ 
	int save(Map<String,Object> map) throws ParseException;
	/** 
	* @Description: 更新库存疫苗
	* @Param: [tMgrStockInfo] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/7/24 9:04
	* @Tel  17328595627
	*/ 
	void update(TMgrStockInfoEntity tMgrStockInfo);

	/** 
	* @Description: 产品的报损操作
	* @Param: [param] 
	* @return: int 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/4 9:59
	* @Tel  17328595627
	*/ 
    int damage(Map param);
	/** 
	* @Description: 登记台显示库存列表 
	* @Param: [map] 
	* @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/16 16:16
	* @Tel  17328595627
	*/ 
	List<Map<String,Object>> queryRegisterList(Map<String, Object> map);
	/** 
	* @Description: 登记条数 
	* @Param: [map] 
	* @return: int 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/8/16 16:16
	* @Tel  17328595627
	*/
	int queryRegisterTotal(Map<String, Object> map);
	/** 
	* @Description: 退货操作
	* @Param: [param] 
	* @return: int 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/9/8 11:25
	* @Tel  17328595627
	*/ 
	int returnGoods(Map param);
	/** 
	* @Description: 生成库存的流水单号 ，如入库单号，盘点单号
	* @Param: [type] 
	* @return: java.lang.String 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/10/12 14:25
	* @Tel  17328595627
	*/ 
    String getOrderNumber(String type);
	/** 
	* @Description: 每月结转库存量，作为月初库存
	* @Param: [params] 
	* @return: void 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/10/26 17:24
	* @Tel  17328595627
	*/ 
    int carryOverStock();
	/** 
	* @Description: 查询根据设备id和仓库Id 查询库存信息
	* @Param: [map] 
	* @return: io.yfjz.entity.mgr.TMgrStockInfoEntity 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/11/5 11:01
	* @Tel  17328595627
	*/ 
	TMgrStockInfoEntity queryStockInfoByEquipmentAndStore(Map map);
	/** 
	* @Description: 查询收发记录 
	* @Param: [map] 
	* @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
	* @Author: 田金海
	* @Email: 895101047@qq.com
	* @Date: 2018/11/26 19:08
	* @Tel  17328595627
	*/ 
    List<Map<String,Object>> queryDispatchList(Map<String, Object> map);
}
