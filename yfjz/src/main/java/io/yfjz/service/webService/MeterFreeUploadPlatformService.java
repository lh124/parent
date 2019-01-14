package io.yfjz.service.webService;


import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 12:03 2018/11/15
 */

public interface MeterFreeUploadPlatformService {

    /**
     * 上传行政村和学校的数据到平台
     * @return
     */
   Map<String,Object> updatePlatform();
}
