package io.yfjz.service.child;

import io.yfjz.entity.child.IntegrityRateEntity;
import io.yfjz.entity.child.TChildInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 儿童基本信息表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 15:02:10
 */
public interface TChildInfoService {
	
	TChildInfoEntity queryObject(String chilCode);
	
	List<TChildInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildInfoEntity tChildInfo);
	
	void update(TChildInfoEntity tChildInfo);

	void updatainfo(TChildInfoEntity var1);

	void delete(String chilCode);
	
	void deleteBatch(String[] chilCodes);

	List<IntegrityRateEntity> listIntegrityRate(Map<String,Object> map);

	List<TChildInfoEntity> listImperfectChild(Map<String,Object> map);

	int queryTotalImperfectChild(Map<String,Object> map);

	List<TChildInfoEntity> currentDayInocChild(Map<String,Object> map);

	List<TChildInfoEntity> getListUnSyncstatusInocChild(Map<String,Object> map);
	int queryTotalCurrentDayInocChild(Map<String,Object> map);

	List<TChildInfoEntity> currentDayWaitInocChild (Map<String,Object> map);
	int queryTotalCurrentDayWaitInocChild(Map<String,Object> map);

	List<Map<String,Object>> listImperfectInocChild(Map<String,Object> map);

	int queryTotalImperfectInocChild(Map<String,Object> map);

	/**
	 * @method_name: queryAllTotal
	 * @describe: 查询儿童表中有多少条数据
	 * @param: []
	 * @return: int
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/9/15  11:57
	 **/
	int queryAllTotal();

	/**
	 * 根据儿童编码绑定条形码
	 * @describe:
	 * @param childCode
	 * @param barCode
	 * @return void
	 * @author 邓召仕
	 * @date: 2018-10-15  16:47
	 **/
    void updateBarCode(String childCode, String barCode) throws Exception;

    String getCurrentAddress();
}
