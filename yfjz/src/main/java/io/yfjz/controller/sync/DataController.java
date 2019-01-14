package io.yfjz.controller.sync;

import io.yfjz.service.sync.DataService;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘琪
 * @class_name: ImportDataController
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-03 10:24
 */
@Controller
@RequestMapping("/sync")
public class DataController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataService importDataService;

    @RequestMapping(value="/syncData")
    @ResponseBody
    public R syncData(HttpServletRequest request, HttpServletResponse response,Integer type){
        try {
            /*if (type == 1) {
                return importDataService.childUpdate();
            } else if (type == 2) {
                    return importDataService.synchronizedChildInoculations();
            } else {
                return R.error(-1001, "导入失败,请求参数有误!");
            }*/
            String orgId = ShiroUtils.getUserEntity().getOrgId();
            if (orgId == null || "".equals(orgId)){
                return R.error("操作失败，请退出系统重新登录后重试！");
            }
            return importDataService.syncData(orgId);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return R.error(-1001, "操作失败!,格式化异常!");
        }
    }
}
