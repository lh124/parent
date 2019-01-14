package io.yfjz.controller.statistics;

import io.yfjz.entity.statistics.CreateCardRate;
import io.yfjz.managerservice.statistics.CardStatisticsSerivce;
import io.yfjz.utils.ExcelUtil;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 接种率统计
 * @describe:
 * @param
 * @return
 * @author 邓召仕
 * @date: 2018-08-23  16:56
 **/
@Controller
@RequestMapping("createCard")
public class CreateCardRateController {
    @Autowired
    private CardStatisticsSerivce cardStatisticsSerivce;
    /**
     * 儿童建卡率统计
     * @describe:
     * @param createCardDateStart
     * @param createCardDateEnd
     * @param chilCommittees
     * @param infostatus
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-08-27  14:58
     **/
    @RequestMapping("childCardRate")
    @ResponseBody
    public R childCardRate(String createCardDateStart,String createCardDateEnd,
                     @RequestParam(value = "chilCommittees",required=false) String[] chilCommittees,
                     @RequestParam(value = "infostatus",required=false) String[] infostatus,
                          String birthDayStart,String birthDayEnd ){

        Date createDateStart = null;
        Date createDateEnd = null;
        Date birthDayStarts = null;
        Date birthDayEnds = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(createCardDateStart)){
            try {
                createDateStart = sdf.parse(createCardDateStart);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(createCardDateEnd)){
            try {
                createDateEnd = sdf.parse(createCardDateEnd);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(birthDayStart)){
            try {
                birthDayStarts = sdf.parse(birthDayStart);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(birthDayEnd)){
            try {
                birthDayEnds = sdf.parse(birthDayEnd);
            } catch (ParseException e) {
            }
        }


    //服务调用
        List<CreateCardRate> createCardRates = cardStatisticsSerivce.getCreateCardRate(createDateStart,createDateEnd,
                chilCommittees,infostatus,birthDayStarts,birthDayEnds);
        if (createCardRates != null){
            PageUtils pageUtil = new PageUtils(createCardRates, createCardRates.size(), createCardRates.size(), 1);
            return R.ok().put("page",pageUtil);
        }else {
            return R.error("没有查询到数据");
        }
       
    }

    /**
     * 导出Excel
     * @describe:
     * @return void
     * @author 邓召仕
     * @date: 2018-08-30  16:56
     **/
    @RequestMapping("childCardRateExcel")
    public void childCardRateExcel(HttpServletResponse response, String createCardDateStart, String createCardDateEnd,
                             @RequestParam(value = "chilCommittees",required=false) String[] chilCommittees,
                             @RequestParam(value = "infostatus",required=false) String[] infostatus,String birthDayStart,String birthDayEnd){

        Date createDateStart = null;
        Date createDateEnd = null;
        Date birthDayStarts = null;
        Date birthDayEnds = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(createCardDateStart)){
            try {
                createDateStart = sdf.parse(createCardDateStart);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(createCardDateEnd)){
            try {
                createDateEnd = sdf.parse(createCardDateEnd);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(birthDayStart)){
            try {
                birthDayStarts = sdf.parse(birthDayStart);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(birthDayEnd)){
            try {
                birthDayEnds = sdf.parse(birthDayEnd);
            } catch (ParseException e) {
            }
        }
    //服务调用
        List<CreateCardRate> createCardRates = cardStatisticsSerivce.getCreateCardRate(createDateStart,createDateEnd,
                chilCommittees,infostatus,birthDayStarts,birthDayEnds);
        if (createCardRates != null){
            String excelTite = "建卡及时率统计";
            String[] titles = { "区域划分名称,committeeName", "儿童数,childCount", "建卡数,createCardCount" , "建卡率,createCardRate","及时数,timelyNumber","及时率,timelyRate"};
            String manName = ShiroUtils.getUserEntity().getRealName();
            ExcelUtil.export(response,manName,excelTite,titles,createCardRates);
        }

    }

}
