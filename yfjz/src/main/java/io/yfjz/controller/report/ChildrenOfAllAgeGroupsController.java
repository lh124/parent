package io.yfjz.controller.report;


import io.yfjz.service.child.TChildInfoService;
import io.yfjz.service.report.ChildrenOfAllAgeGroupsService;
import io.yfjz.utils.R;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO 各年龄组儿童构成情况统计表（免疫规划来源）
 * @Date 14:03 2018/12/19
 */
@RequestMapping("childrenOfAllAgeGroups")
@Controller
public class ChildrenOfAllAgeGroupsController {

    @Resource
    private ChildrenOfAllAgeGroupsService childrenOfAllAgeGroupsService;

    @Resource
    private TChildInfoService tChildInfoService;

    @RequestMapping(value = "query")
    @ResponseBody
    public R queryChildrenOfAllAgeGroups(@RequestBody(required = false) @RequestParam(name = "committee[]") List<String> committee , Date data){
        if(committee.size() == 1 && committee.get(0).equals("无选择")){
            committee = null;
        }
        List<Map<String,Object>> list = childrenOfAllAgeGroupsService.queryChildrenOfAllAgeGroups(committee,data);
        String orgAddress = tChildInfoService.getCurrentAddress();
        return R.ok().put("data",list).put("org",orgAddress);
    }

    /**
     * 导出各年龄组儿童构成情况统计表（免疫规划来源）
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = {"/export"})
    public void exportChildrenOfAllAgeGroups (HttpServletRequest request, HttpServletResponse response,@RequestBody(required = false) @RequestParam(name = "committee") List<String> committee) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/");
        if(committee.size() == 1 && committee.get(0).equals("无选择")){
            committee = null;
        }
        //获取当前机构的详细地址
        String orgAddress = tChildInfoService.getCurrentAddress();
        String[] address = orgAddress.split("/");
        childrenOfAllAgeGroupsService.exportChildrenOfAllAgeGroups(path,response,committee,address);
    }

    @RequestMapping(value = "queryJiankaChilBirthHospital")
    @ResponseBody
    public R queryJiankaChilBirthHospitalDistributionTable(@DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate){

        if(startDate == null){
            return R.ok(100,"开始时间不能为空");
        }
        if(endDate == null){
            endDate = new Date();
        }

        List<Map<String,Object>> list = childrenOfAllAgeGroupsService.queryJiankaChilBirthHospitalDistributionTable(startDate,endDate);
        return R.ok().put("data",list);
    }

    @RequestMapping(value = "queryJiankaChilBirthHospital/export")
    public void exportJiankaChilBirthHospital(HttpServletRequest request, HttpServletResponse response,@DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate)throws Exception{
        if(startDate == null){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out =  response.getWriter();
            out.print("<h1>建卡开始时间不能为空</h1>");
            out.flush();
            out.close();
            return ;
            //throw new RuntimeException(R.ok(100,"开始时间不能为空").toString());
        }
        if(endDate == null){
            endDate = new Date();
        }
        String path = request.getSession().getServletContext().getRealPath("/");
        childrenOfAllAgeGroupsService.exportJiankaChilBirthHospital(path,response,startDate,endDate);
    }

}
