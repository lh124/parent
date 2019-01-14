package io.yfjz.service.report;

import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.IntegrityRateEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.sys.SysDepartEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打印服务接口
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 13:43:13
 */
public interface ReportsPrintService {

	//儿童基本信息打印
	Map queryForPrint(Map<String, Object> map, HttpServletResponse response);

	//儿童基本信息打印
	Map printCard(Map<String, Object> map, HttpServletResponse response);

	//儿童接种记录打印
	Map queryInoculationForPrint(Map<String, Object> map, HttpServletResponse response);

	Map querySecondVaccInocForPrint (Map<String, Object> map, HttpServletResponse response);

	//儿童接种记录打印
	Map queryInoculationByIdForPrint(Map<String, Object> map, HttpServletResponse response);
	//儿童不完整记录打印
	List<TChildInfoEntity> ImperfectDataChild(List<TChildInfoEntity> listImperfectChild,Map<String,Object> map, HttpServletResponse response);
	//	接种信息完整性统计打印
	List<InoculateIntegrityRateEntity>  inoculateGather(  List<InoculateIntegrityRateEntity> integrityRate, Map<String,Object> map, HttpServletResponse response);

	List<SysDepartEntity> queryList(Map<String, Object> map);
	//接种日志打印
	List<Map<String,Object>> InoculateLogs(Map<String,Object> paramMap,HttpServletResponse response);

	int queryTotal(Map<String, Object> map);

	void save(SysDepartEntity sysDepart);

	void update(SysDepartEntity sysDepart);

	void delete(String id);

	void deleteBatch(String[] ids);

	void queryAndPrintchildGather(List<IntegrityRateEntity> integrityRateEntities,String path, HttpServletResponse response);

	void printCurrentDayInocChild(List<TChildInfoEntity> listCurrentDayInocChild,String path, HttpServletResponse response);

	void printCurrentDayWaitInocChild(List<TChildInfoEntity> listCurrentDayInocChild,String path, HttpServletResponse response);

	void printStockControl( List<Map<String, Object>> list ,String path, HttpServletResponse response);

}
