package io.yfjz.controller.webService;


import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.webService.DataSynchronizationService;
import io.yfjz.utils.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 11:27 2018/09/12
 */
@Controller
@RequestMapping("/webService")
public class DataSynchronizationController {

    @Resource
    private DataSynchronizationService dataSynchronizationService;

    @RequestMapping("/updateTableData")
    @ResponseBody
    public Map<String,Object> updateTableData(String tableName,@RequestParam Map<String,Object> where){
        SysUserEntity user = ShiroUtils.getUserEntity();
        return dataSynchronizationService.updateTableData(user.getOrgId(),tableName,where);
    }

    @RequestMapping("updateFullData")
    @ResponseBody
    public Map<String,Object> updateFullData(String orgId,String pId){
        if(orgId == null || "".equals(orgId.trim())){
            SysUserEntity user = ShiroUtils.getUserEntity();
            orgId = user.getOrgId();
        }
        return dataSynchronizationService.updateFullData(orgId,pId,"表全量数据");
    }
}
