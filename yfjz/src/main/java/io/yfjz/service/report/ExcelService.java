package io.yfjz.service.report;

import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.IntegrityRateEntity;
import io.yfjz.entity.child.TChildInfoEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: 饶士培
 * @Date: 2018-09-10 17:56
 * @Description:
 * @tel:18798010686
 * @qq:1013147559
 */
public interface ExcelService {

    void IntegrityRate(List<IntegrityRateEntity> integrityRateEntities, String path, HttpServletResponse response);

    void imperfectChild(List<TChildInfoEntity> integrityRateEntities, String path, HttpServletResponse response);

    void ExcelInoLog(Map<String,Object> paramMap,String path, HttpServletResponse response);

    void inoculateGather( List<InoculateIntegrityRateEntity> inoculateIntegrityRateEntity,String path, HttpServletResponse response);

    void childrenList( List<TChildInfoEntity> childrenList,Map<String,Object> map, HttpServletResponse response);

    /**
     * 6-1 国家免疫规划疫苗常规接种情况报表
     * @author 张羽丰
     * @time 2018-10-25
     * @param map
     * @param response
     */
    void exportNiprvsr(Map<String,Object> map, HttpServletResponse response);

    /**
     * 贵州省免疫规划目标儿童动态管理一览表
     * @author 张羽丰
     * @time 2018-10-25
     * @param map
     * @param response
     */
    void exportAlodmotcigip(String year,String month,Map<String,Object> map,String [] chilCommittees, HttpServletResponse response);


    void vacTransportTemp(HttpServletResponse response);

    void vacScheduleVillag(HttpServletResponse response);

    void exportChildAndInoculate(List<TChildInfoEntity> childrenList,Map<String,Object> map, HttpServletResponse response);
}
