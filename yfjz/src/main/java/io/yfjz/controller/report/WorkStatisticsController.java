package io.yfjz.controller.report;

import io.yfjz.service.report.WorkStatisticsService;
import io.yfjz.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: 工作量统计报表 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/6 16:55
* @Tel  17328595627
*/
@RequestMapping("work")
@Controller
public class WorkStatisticsController {

    @Autowired
    private WorkStatisticsService workStatisticsService;

    @RequestMapping("/list")
    @ResponseBody
    public R getList(Integer page, Integer limit,String startDate,String endDate){

        Map<String, Object> queryMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //默认查询当天数据
        if (StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
            queryMap.put("startDate",sdf.format(new Date()));
            queryMap.put("endDate",sdf.format(new Date()));
        }else{
            Date start=CommonUtils.transformDate(startDate);
            Date end=CommonUtils.transformDate(endDate);
            queryMap.put("startDate",start);
            queryMap.put("endDate",end);
        }


        queryMap.put("page",page);
        queryMap.put("offset", (page - 1) * limit);
        queryMap.put("limit",limit);
        List<Map<String,Object>> list=workStatisticsService.queryList(queryMap);
        PageUtils pageUtil = new PageUtils(list, list.size(), limit, page);
        return R.ok().put("page", pageUtil);

    }
    /** 
    * @Description: 导出excel
    * @Param: [page, limit, startDate, endDate] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/9/7 14:07
    * @Tel  17328595627
    */ 
    @RequestMapping("/excel")
    @ResponseBody
    public void excel(HttpServletResponse response, Integer page, Integer limit, String startDate, String endDate){

        Map<String, Object> queryMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //默认查询当天数据
        if (StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
            queryMap.put("startDate",sdf.format(new Date()));
            queryMap.put("endDate",sdf.format(new Date()));
        }else{
            Date start=CommonUtils.transformDate(startDate);
            Date end=CommonUtils.transformDate(endDate);
            queryMap.put("startDate",start);
            queryMap.put("endDate",end);
        }


        queryMap.put("page",1);
        queryMap.put("limit",-1);
        queryMap.put("offset", 0);
        List<Map<String,Object>> list=workStatisticsService.queryList(queryMap);
        if (list != null && list.size()>0){
            String excelTite = "工作量统计表";
            String[] titles = { "工作人员,username","工作台,towerName", "完成数,finish", "取消数,cancel" , "非常满意数量,verySatisfied","满意数量,satisfied","一般数量,commonly",
                    "不满意数量,dissatisfied", "满意率,satisfiedRate"};
            String manName = ShiroUtils.getUserEntity().getRealName()==null?"":ShiroUtils.getUserEntity().getRealName();
            ExcelUtil.export(response,manName,excelTite,titles,list);
        }

    }
}
