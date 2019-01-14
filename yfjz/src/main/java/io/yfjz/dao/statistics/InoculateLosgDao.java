package io.yfjz.dao.statistics;

import io.yfjz.entity.statistics.InoculateRate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @class_name: InoculateLosgDao
 * @describe: 接种日志报表
 * @author: 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2018/9/5  16:17
 **/
public interface InoculateLosgDao {
    /**
     * @method_name: getInoulateLogs
     * @describe: 获取接种日志报表
     * @param: [param]
     * @return: java.util.List<java.util.HashMap>
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/5  16:19
     **/
    List<Map<String,Object>> getInoulateLogs(Map<String, Object> param);
    /**
     * @method_name: getInoulateLogs
     * @describe: 获取接种日志报表
     * @param: [param]
     * @return: java.util.List<java.util.HashMap>
     * @author: 廖欢
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/5  16:19
     **/
    List<Map<String,Object>> getInoulateLogss(Map<String, Object> param);

    /**
     * @method_name: getInoulateLogsGroupInocProperty
     * @describe: 按照疫苗接种属性来分组统计
     * @param: [param]
     * @return: java.util.List<java.util.HashMap> 
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/6  10:26
     **/
    List<Map<String,Object>> getInoulateLogsGroupInocProperty(Map<String, Object> param);

    /**
     * @method_name: getInoulateLogsGroupDose
     * @describe: 按照疫苗接种剂次来分组统计
     * @param: [param]
     * @return: java.util.List<java.util.HashMap> 
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/6  10:29
     **/
    List<Map<String,Object>> getInoulateLogsGroupDose(HashMap<String, Object> param);
}
