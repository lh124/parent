package io.yfjz.dao.rule;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.ChildInoData;

import java.util.List;
import java.util.Map;


/**
 * 规则与业务逻辑相关联的数据库访问接口
 * @作者：邓召仕
 * 2018年7月26日上午10:05:00
 */
public interface TRuleUnionDao {

	/**
	 * 根据闭区间疫苗ID集合与左闭右开接种时间段，查询统计接种数 
	 * @作者：邓召仕
	 * 2018年7月26日
	 * @param parameters map类型，必须包含childId 儿童ID，List<String> bios 疫苗ID集合，startTime 查询开始时间，endTime 查询结束时间
	 * @return
	 */
	 int queryTotalByBioTime(Map<String, Object> parameters);
	 
	 /**
	  * 按儿童接种时间排序，根据查询条件与左闭右开接种时间段取出第n+1条接种记录
	  * @作者：邓召仕
	  * 2018年7月27日
	  * @param parameters map类型，必须包含childId 儿童ID，List<String> bios 疫苗ID集合，int n 第n+1条记录，startTime 查询开始时间，endTime 查询结束时间
	  * @return
	  */
	 ChildInoData queryInoByBioTime(Map<String, Object> parameters);

	 /**
	  * 根据儿童编码获取规则需要的儿童信息
	  * @作者：邓召仕
	  * 2018年7月27日
	  * @param childCode
	  * @return
	  */
	 ChildData getChildByCode(String childCode);
	 /**
	  * 获取全部儿童
	  * @作者：邓召仕
	  * 2018年7月27日
	  * @return
	  */
	 List<ChildData> getAllChild();
	 /**
	  * @method_name: 分页获取没有初始化接种计划的儿童信息
	  * @describe:
	  * @return java.util.List<io.yfjz.managerservice.rule.common.data.ChildData>
	  * @author 邓召仕
	  * @date: 2018-08-02  11:58
	  **/
	 List<ChildData> getNoPlanChildsByPage(Map<String, Object> parameters);
	 /**
	  * @method_name: 获取没有被初始化的儿童总数
	  * @describe:
	  * @return int
	  * @author 邓召仕
	  * @date: 2018-08-02  14:23
	  **/
	 int queryNoPlanChildsTotal();
	 /**
	  * @method_name: 查询数据库儿童数
	  * @describe:
	  * @return int
	  * @author 邓召仕
	  * @date: 2018-08-03  11:20
	  **/
	 int queryChildsTotal();

	 /**
	  * 根据条件获取满足该条件的所有非删除儿童
	  * @describe:
	  * @param parameters
	  * @return java.util.List<io.yfjz.entity.rule.ChildData>
	  * @author 邓召仕
	  * @date: 2018-08-21  14:33
	  **/
	List<ChildData> getAllChildsByCondition(Map<String, Object> parameters);

	/**
	 * @method_name: 最后一次添加进数据库的儿童信息
	 * @describe:
	 * @param
	 * @return io.yfjz.entity.rule.ChildData
	 * @author 邓召仕
	 * @date: 2018-09-13  15:54
	 **/
	ChildData getLastCreateChild();

	List<String> getRandomChildCode(Map<String, Object> parameters);

	/**
	 * @method_name: 查询库存有的，儿童没有接种过的二类苗
	 * @describe:
	 * @return java.util.Map<java.lang.String , java.lang.Object>
	 * @author 邓召仕
	 * @date: 2018-11-21  17:26
	 **/
    Map<String,Object> getBio2ByStock(Map<String, Object> map);
}
