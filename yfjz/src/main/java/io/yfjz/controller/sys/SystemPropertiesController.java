package io.yfjz.controller.sys;

import io.yfjz.utils.PropertyUtils;
import io.yfjz.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘琪
 * @class_name: SystemPropertiesController
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-07-16 13:40
 */
@RestController
@RequestMapping({"/sys/properties"})
public class SystemPropertiesController {

    /**
     * @method_name: reloadMap
     * @describe: 属性系统的properties属性文件中的value值
     * 注：修改之后，需要调用这个方法来重新加班config.properties中的值
     * @param: [request, response]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/7/13  15:54
     **/
    @RequestMapping({"/reloadMap"})
    public R reloadMap(HttpServletRequest request, HttpServletResponse response) {
        PropertyUtils.reloadMap();
        return R.ok();
    }
}
