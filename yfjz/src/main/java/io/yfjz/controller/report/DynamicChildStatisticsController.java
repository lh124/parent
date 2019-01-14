package io.yfjz.controller.report;

import io.yfjz.service.report.DynamicChildService;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @Description: 儿童动态一栏表 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/11/1 12:36
* @Tel  17328595627
*/ 
@Controller
@RequestMapping("dynamic")
public class DynamicChildStatisticsController {
    
    @Autowired
    private DynamicChildService dynamicChild;

    @RequestMapping("/list")
    @ResponseBody
    public R getList(String year, String month, @RequestParam(value = "chilCommittees[]", required = false) String[] chilCommittees){

        Map<String, Object> queryMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if (!StringUtils.isEmpty(year)&&!StringUtils.isEmpty(month)){
            queryMap.put("year",year);
            queryMap.put("month",month);
            queryMap.put("chilCommittees",chilCommittees);
        }
        List<Map<String,Object>> list=dynamicChild.queryList(queryMap);
        return R.ok().put("data", list);

    }
}
