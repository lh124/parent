package io.yfjz.service.sys;


import io.yfjz.entity.sys.SysConfigurationEntity;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 17:00 2018/10/19
 */

public interface SysConfigurationService {

    /**
     * 查询type的功能是否开启
     * @param type 不能为空
     * @return
     */
   SysConfigurationEntity configuration(String type);

    /**
     * 更改取号状态
     */
   void offernumberStatus(SysConfigurationEntity entity);
}
