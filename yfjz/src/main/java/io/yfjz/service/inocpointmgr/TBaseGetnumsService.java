package io.yfjz.service.inocpointmgr;

import io.yfjz.entity.sys.SysConfigurationEntity;
import io.yfjz.entity.sys.SysDictEntity;
import io.yfjz.utils.R;

import java.util.Map;

/**
 * 取号设置
 * @describe:
 * @param
 * @return
 * @author 邓召仕
 * @date: 2018-08-31  14:08
 **/
public interface TBaseGetnumsService {


	Map<String,Object> queryObject(String orgId);

    void saveOffernumber(Map<String, Object> param, String orgId);

    void offernumberState(Integer status, String orgId);

    /**
     * 查询当前取号设置，看是否可以取号，R.code为0可以取号，为1不可以取号，msg为不可以取号原因
     * @describe:
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-08-31  16:46
     **/
    R  offerNumberCheck();
    /** 
    * @Description: 更新条码打印机链接的IP
    * @Param: [ipAddress, orgId] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/31 19:03
    * @Tel  17328595627
    */ 
    void updateBarCodeIp(SysConfigurationEntity ipAddress);

    /**
     * @method_name: 设置门诊端配置项
     * @describe:
     * @return void
     * @author 邓召仕
     * @date: 2018-11-23  14:13
     **/
    void setSysConType(Integer status, String type,String remark);


    /**
     * @method_name: 根据设置类型获取设置状态
     * @describe:
     * @param sysType 设置类型
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-11-23  14:52
     **/
    R getSysConType(String sysType,Integer defaultValue);
}
