package io.yfjz.service.inocpointmgr;


import io.yfjz.entity.basesetting.TBaseCommitteeEntity;
import io.yfjz.entity.basesetting.TBaseHospitalEntity;
import io.yfjz.entity.basesetting.TBaseSchoolEntity;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.queue.StepType;
import io.yfjz.utils.R;
import io.yfjz.utils.page.PageBean;
import io.yfjz.utils.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 接种点管理
 * @Date 9:47 2018/07/26
 */

public interface InoculationManagerService {

    /** 
    * @Description: 保存流程配置
    * @Param: [param] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/13 10:22
    * @Tel  17328595627
     * @param param
     */
    void processSet(String param);

    Map<String,Object> processList();
    /** 
    * @Description: 获取流程第一步
    * @Param:  
    * @return:  
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/13 14:33
    * @Tel  17328595627
    */
    StepType getProcessFirst();
    /** 
    * @Description: 获取下一步工作台
    * @Param: [] 
    * @return: io.yfjz.entity.queue.StepType 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/13 14:41
    * @Tel  17328595627
    */ 
    StepType getNextProcess(Integer type);

}
