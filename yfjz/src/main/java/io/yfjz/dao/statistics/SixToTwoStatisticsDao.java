package io.yfjz.dao.statistics;

import java.util.List;
import java.util.Map; /**
 * create by tianjinhai on 2018/10/16 16:08
 */
public interface SixToTwoStatisticsDao {
    List<Map<String,Object>> queryList(Map<String, Object> queryMap);

    List<Map<String,Object>> queryAllVaccine();

    /**
     * @method_name: 6-2报表上传查询
     * @describe:
     * @param queryMap
     * @return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @author 邓召仕
     * @date: 2018-11-13  21:46
     **/
    List<Map<String,Object>> queryUploadList(Map<String, Object> queryMap);
}
