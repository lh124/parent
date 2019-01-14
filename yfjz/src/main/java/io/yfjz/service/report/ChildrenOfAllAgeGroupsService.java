package io.yfjz.service.report;


import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 14:43 2018/12/19
 */

public interface ChildrenOfAllAgeGroupsService {

    /**
     *  查询统计各年龄组儿童构成情况统计表（免疫规划来源）
     * @return
     */
    List<Map<String,Object>> queryChildrenOfAllAgeGroups(List committee , Date data);

    /**
     * 导出相关数据
     */
    void exportChildrenOfAllAgeGroups(String path,HttpServletResponse response,List<String> committee,String[] address);
    /**
     * 查询建卡儿童出生医院分布统计表
     * @param startDate
     * @param end
     * @return
     */
    List<Map<String,Object>> queryJiankaChilBirthHospitalDistributionTable(Date startDate,Date end);

    /**
     * 导出建卡儿童出生医院分布统计表相关数据
     */
    void exportJiankaChilBirthHospital(String path,HttpServletResponse response,Date startDate,Date end);
}
