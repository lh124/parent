package io.yfjz.dao.inoculation;

import io.yfjz.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 接种台和疫苗关联 中间表
 * 
 * @author 田金海
 * @email 895101047@qq.com
 * @tel 17328595627
 * @date 2018-08-09 14:29:59
 */
public interface VaccineWorkbenchDao extends BaseDao<Map> {
    /** 
    * @Description: 保存接种台绑定的疫苗 
    * @Param: [pos, ids] 
    * @return: int 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/9 16:58
    * @Tel  17328595627
    */ 
    int saveVaccineRelation(List<Map<String, Object>> map);
    /**
    * @Description: 取消疫苗绑定
    * @Param: [list]
    * @return: int
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/10 10:13
    * @Tel  17328595627
    */
    int deleteVaccineRelation(Map<String, Object> map);
}
