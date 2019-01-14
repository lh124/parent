package io.yfjz.controller.sys;


import com.alibaba.fastjson.JSONObject;
import io.yfjz.controller.websoket.ConfigurationWebsoket;
import io.yfjz.entity.sys.SysConfigurationEntity;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.bus.impl.ChildServiceImpl;
import io.yfjz.service.sys.SysConfigurationService;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 系统相关功能是否启用配置表
 * @Date 16:59 2018/10/19
 */
@Controller
@RequestMapping("configuration")

public class SysConfigurationController {

    @Autowired
    private SysConfigurationService sysConfigurationService;

    /**
     * 查询type的功能是否开启
     *
     * @param type
     * @return
     */
    @RequestMapping("queryConfiguration")
    @ResponseBody
    public R queryConfiguration(String type) {
        if (StringUtils.isBlank(type)) {
            return R.error("功能类型“type”不能为空！");
        }
        SysConfigurationEntity entity = sysConfigurationService.configuration(type);
        return R.ok().put("data", entity);
    }

    @RequestMapping("offernumberStatus")
    @ResponseBody
    public R offernumberStatus(SysConfigurationEntity entity) {
        if (StringUtils.isBlank(entity.getType())) {
            return R.error("功能类型“type”不能为空！");
        }
        if (entity.getStartUsing() == null) {
            return R.error("功能状态“Start_using”不能为空！");
        }
        sysConfigurationService.offernumberStatus(entity);
        ConfigurationWebsoket web = new ConfigurationWebsoket();
        web.sendStartUsingMessage(entity);
        return R.ok();
    }



}
