package io.yfjz.controller.webService;


import io.yfjz.service.webService.MeterFreeUploadPlatformService;
import io.yfjz.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 上传计免本地数据到平台
 * @Date 14:15 2018/11/15
 */
@Controller
@RequestMapping("/webService")
public class MeterFreeUploadPlatformController {

    @Resource
    private MeterFreeUploadPlatformService meterFreeUploadPlatformService;

    @ResponseBody
    @RequestMapping("updatePlatform")
    public R updatePlatform(){
        Map<String,Object> map = meterFreeUploadPlatformService.updatePlatform();
        return R.ok().put("result",map);
    }
}
