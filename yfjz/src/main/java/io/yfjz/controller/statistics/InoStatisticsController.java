package io.yfjz.controller.statistics;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.entity.statistics.InoculateRate;
import io.yfjz.managerservice.rule.RecommendService;
import io.yfjz.managerservice.statistics.InoStatisticsSerivce;
import io.yfjz.utils.ExcelUtil;
import io.yfjz.utils.PageUtils;
import io.yfjz.utils.R;
import io.yfjz.utils.ShiroUtils;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 接种率统计
 *
 * @param
 * @author 邓召仕
 * @describe:
 * @return
 * @date: 2018-08-23  16:56
 **/
@Controller
@RequestMapping("inoStatistics")
public class InoStatisticsController {
    @Autowired
    private InoStatisticsSerivce inoStatisticsSerivce;

    /**
     * 接种率统计
     *
     * @return io.yfjz.utils.R
     * @describe:
     * @author 邓召仕
     * @date: 2018-08-23  17:00
     **/
    @RequestMapping("inoculationRate")
    @ResponseBody
    public R inoNote(@RequestParam Map<String, Object> param,
                     @RequestParam(value = "chilCommittees", required = false) String[] chilCommittees,
                     @RequestParam(value = "infostatus", required = false) String[] infostatus,
                     @RequestParam(value = "biotypes", required = false) String[] biotypes) {
        String chilBirthdayStartStr = (String) param.get("chilBirthdayStart");
        String chilBirthdayEndStr = (String) param.get("chilBirthdayEnd");
//        接种日期
        String inoculateStart = (String) param.get("inoculateStart");
        String inoculateEnd = (String) param.get("inoculateEnd");
        String chilResidence = (String) param.get("chilResidence");
        String chilAccount = (String) param.get("chilAccount");
        Integer limit = param.get("random") == null || "".equals(param.get("random")) ? null : Integer.parseInt((String) param.get("random"));//随机数

        //参数类型转换
        Date chilBirthdayStart = null;
        Date chilBirthdayEnd = null;
        Date inoculateStartstr = null;
        Date inoculateEndstr = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(chilBirthdayStartStr)) {
            try {
                chilBirthdayStart = sdf.parse(chilBirthdayStartStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(chilBirthdayEndStr)) {
            try {
                chilBirthdayEnd = sdf.parse(chilBirthdayEndStr);
            } catch (ParseException e) {
            }
        }
//        接种日期
        if (!StringUtils.isEmpty(inoculateStart)) {
            try {
                inoculateStartstr = sdf.parse(inoculateStart);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(inoculateEnd)) {
            try {
                inoculateEndstr = sdf.parse(inoculateEnd);
            } catch (ParseException e) {
            }
        }

        //服务调用
        List<InoculateRate> inoculateRates = inoStatisticsSerivce.getInoRates(chilBirthdayStart, chilBirthdayEnd,inoculateStartstr,inoculateEndstr ,chilResidence,
                chilCommittees, infostatus, biotypes, chilAccount, limit);
        //接种率统计加汇总（通过疫苗类型汇总）
        List<InoculateRate> collect = inoculateRateCollect(inoculateRates);

        if (inoculateRates != null) {
            PageUtils pageUtil = new PageUtils(collect, collect.size(), collect.size(), 1);
            return R.ok().put("page", pageUtil);
        } else {
            return R.error("没有查询到数据");
        }
    }

    /**
     * 接种率导出Excel
     *
     * @return io.yfjz.utils.R
     * @describe:
     * @author 邓召仕
     * @date: 2018-08-30  14:00
     **/
    @RequestMapping("inoculationRateExcel")
    public void inoNoteExcel(HttpServletResponse response, @RequestParam Map<String, Object> param,
                             @RequestParam(value = "chilCommittees", required = false) String[] chilCommittees,
                             @RequestParam(value = "infostatus", required = false) String[] infostatus,
                             @RequestParam(value = "biotypes", required = false) String[] biotypes) {
        String chilBirthdayStartStr = (String) param.get("chilBirthdayStart");
        String chilBirthdayEndStr = (String) param.get("chilBirthdayEnd");
        //        接种日期
        String inoculateStart = (String) param.get("inoculateStart");
        String inoculateEnd = (String) param.get("inoculateEnd");

        String chilResidence = (String) param.get("chilResidence");
        String chilAccount = (String) param.get("chilAccount");
        Integer limit = param.get("random") == null || "".equals(param.get("random")) ? null : Integer.parseInt((String) param.get("random"));//随机数
        //参数类型转换
        Date chilBirthdayStart = null;
        Date chilBirthdayEnd = null;
        Date inoculateStartstr = null;
        Date inoculateEndstr = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(chilBirthdayStartStr)) {
            try {
                chilBirthdayStart = sdf.parse(chilBirthdayStartStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(chilBirthdayEndStr)) {
            try {
                chilBirthdayEnd = sdf.parse(chilBirthdayEndStr);
            } catch (ParseException e) {
            }
        }

//接种日期
        if (!StringUtils.isEmpty(inoculateStart)) {
            try {
                inoculateStartstr = sdf.parse(inoculateStart);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(inoculateEnd)) {
            try {
                inoculateEndstr = sdf.parse(inoculateEnd);
            } catch (ParseException e) {
            }
        }


        //服务调用
        List<InoculateRate> inoculateRates = inoStatisticsSerivce.getInoRates(chilBirthdayStart, chilBirthdayEnd,inoculateStartstr,inoculateEndstr, chilResidence,
                chilCommittees, infostatus, biotypes, chilAccount, limit);


        //接种率统计加汇总（通过疫苗类型汇总）
        List<InoculateRate> collect = inoculateRateCollect(inoculateRates);

        System.out.println("接种率统计加汇总数据：" + collect);

        if (inoculateRates != null) {
            String excelTite = "儿童接种率";
            String[] titles = {"疫苗,planName", "剂次,agentTimes", "应种数,yinCount", "实种数,factCount", "接种率,inoculateRate", "及时数,timelyCount", "及时率,timelyRate", "合格数,qualifiedCount",
                    "合格率,qualifiedRate", "间短接种数,shortCount", "间短接种率,shortRate", "超期数,outQualifiedCount", "超期率,outQualifiedRate", "应种未种数,yinweiCount", "应种未种率,yinweiRate"};
            String manName = ShiroUtils.getUserEntity().getRealName();
            ExcelUtil.export(response, manName, excelTite, titles, collect == null ? inoculateRates : collect);
        }

    }

    //接种率统计加汇总（通过疫苗类型汇总）
    public List<InoculateRate> inoculateRateCollect(List<InoculateRate> inoculateRate) {
        try {
            List<InoculateRate> inoculateRates = new ArrayList<>();
            Map<String, InoculateRate> map = new HashMap<>();
            String planName = null;
            int size = inoculateRate.size();
            for (int i = 0; i < inoculateRate.size(); i++) {
                InoculateRate ilr = new InoculateRate();
                BeanUtils.copyProperties(ilr, inoculateRate.get(i));

                String planNames = ilr.getPlanName() + "（汇总）";
                if (!map.containsKey(planNames)) {
                    map.put(planNames, ilr);
                    InoculateRate rate = map.get(planNames);
                    rate.setAgentTimes(null);
                    rate.setPlanName(planNames);
                } else {
                    InoculateRate rate = map.get(planNames);
                    rate.setFactCount((rate.getFactCount() == null ? 0 : rate.getFactCount()) + (ilr.getFactCount() == null ? 0 : ilr.getFactCount()));
                    //rate.setInoculateRate(decimals2((rate.getInoculateRate() == null ? 0 : rate.getInoculateRate()) + (ilr.getInoculateRate() == null ? 0 : ilr.getInoculateRate())));
                    rate.setOutQualifiedCount((rate.getOutQualifiedCount() == null ? 0 : rate.getOutQualifiedCount()) + (ilr.getOutQualifiedCount() == null ? 0 : ilr.getOutQualifiedCount()));
                    //rate.setOutQualifiedRate(decimals2((rate.getOutQualifiedRate() == null ? 0 : rate.getOutQualifiedRate()) + (ilr.getOutQualifiedRate() == null ? 0 : ilr.getOutQualifiedRate())));
                    rate.setQualifiedCount((rate.getQualifiedCount() == null ? 0 : rate.getQualifiedCount()) + (ilr.getQualifiedCount() == null ? 0 : ilr.getQualifiedCount()));
                    //rate.setQualifiedRate(decimals2((rate.getQualifiedRate() == null ? 0 : rate.getQualifiedRate()) + (ilr.getQualifiedRate() == null ? 0 : ilr.getQualifiedRate())));
                    rate.setShortCount((rate.getShortCount() == null ? 0 : rate.getShortCount()) + (ilr.getShortCount() == null ? 0 : ilr.getShortCount()));
                    //rate.setShortRate(decimals2((rate.getShortRate() == null ? 0 : rate.getShortRate()) + (ilr.getShortRate() == null ? 0 : ilr.getShortRate())));
                    rate.setTimelyCount((rate.getTimelyCount() == null ? 0 : rate.getTimelyCount()) + (ilr.getTimelyCount() == null ? 0 : ilr.getTimelyCount()));
                    //rate.setTimelyRate(decimals2((rate.getTimelyRate() == null ? 0 : rate.getTimelyRate()) + (ilr.getTimelyRate() == null ? 0 : ilr.getTimelyRate())));
                    rate.setYinCount((rate.getYinCount() == null ? 0 : rate.getYinCount()) + (ilr.getYinCount() == null ? 0 : ilr.getYinCount()));
                    rate.setYinweiCount((rate.getYinweiCount() == null ? 0 : rate.getYinweiCount()) + (ilr.getYinweiCount() == null ? 0 : ilr.getYinweiCount()));
                    //rate.setYinweiRate(decimals2((rate.getYinweiRate() == null ? 0 : rate.getYinweiRate()) + (ilr.getYinweiRate() == null ? 0 : ilr.getYinweiRate())));
                }
                if (planName == null) {
                    planName = inoculateRate.get(i).getPlanName();
                }

                if (!inoculateRate.get(i).getPlanName().equals(planName)) {
                    InoculateRate rate = map.get(planName + "（汇总）");
                    if(rate.getFactCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0){
                        rate.setInoculateRate(decimals2(rate.getFactCount().doubleValue()/rate.getYinCount().doubleValue()*100.00));//邓
                    }else {
                        rate.setInoculateRate(0.00);
                    }
                    if(rate.getOutQualifiedCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0){
                        rate.setOutQualifiedRate(decimals2(rate.getOutQualifiedCount().doubleValue()/rate.getYinCount().doubleValue()*100.00));//邓
                    }else {
                        rate.setOutQualifiedRate(0.00);
                    }
                    if(rate.getQualifiedCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0){
                        rate.setQualifiedRate(decimals2(rate.getQualifiedCount().doubleValue()/rate.getYinCount().doubleValue()*100.00));//邓
                    }else {
                        rate.setQualifiedRate(0.00);
                    }
                    if(rate.getShortCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0) {
                        rate.setShortRate(decimals2(rate.getShortCount().doubleValue() / rate.getYinCount().doubleValue() * 100.00));//邓
                    }else {
                        rate.setShortRate(0.00);
                    }
                    if(rate.getTimelyCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0) {
                        rate.setTimelyRate(decimals2(rate.getTimelyCount().doubleValue() / rate.getYinCount().doubleValue() * 100.00));//邓
                    }else {
                        rate.setTimelyRate(0.00);
                    }
                    if(rate.getYinweiCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0) {
                        rate.setYinweiRate(decimals2(rate.getYinweiCount().doubleValue() / rate.getYinCount().doubleValue() * 100.00));//邓
                    }else {
                        rate.setYinweiRate(0.00);
                    }
                    inoculateRates.add(rate);
                    planName = inoculateRate.get(i).getPlanName();
                }
                inoculateRates.add(inoculateRate.get(i));
                if (i == (size - 1)) {
                    InoculateRate rate = map.get(planName + "（汇总）");
                    if(rate.getFactCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0){
                        rate.setInoculateRate(decimals2(rate.getFactCount().doubleValue()/rate.getYinCount().doubleValue()*100.00));//邓
                    }else {
                        rate.setInoculateRate(0.00);
                    }
                    if(rate.getOutQualifiedCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0){
                        rate.setOutQualifiedRate(decimals2(rate.getOutQualifiedCount().doubleValue()/rate.getYinCount().doubleValue()*100.00));//邓
                    }else {
                        rate.setOutQualifiedRate(0.00);
                    }
                    if(rate.getQualifiedCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0){
                        rate.setQualifiedRate(decimals2(rate.getQualifiedCount().doubleValue()/rate.getYinCount().doubleValue()*100.00));//邓
                    }else {
                        rate.setQualifiedRate(0.00);
                    }
                    if(rate.getShortCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0) {
                        rate.setShortRate(decimals2(rate.getShortCount().doubleValue() / rate.getYinCount().doubleValue() * 100.00));//邓
                    }else {
                        rate.setShortRate(0.00);
                    }
                    if(rate.getTimelyCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0) {
                        rate.setTimelyRate(decimals2(rate.getTimelyCount().doubleValue() / rate.getYinCount().doubleValue() * 100.00));//邓
                    }else {
                        rate.setTimelyRate(0.00);
                    }
                    if(rate.getYinweiCount() !=null && rate.getYinCount()!= null && rate.getYinCount().doubleValue()>0) {
                        rate.setYinweiRate(decimals2(rate.getYinweiCount().doubleValue() / rate.getYinCount().doubleValue() * 100.00));//邓
                    }else {
                        rate.setYinweiRate(0.00);
                    }
                    inoculateRates.add(rate);
                }

            }

            return inoculateRates;
        } catch (Exception e) {
            e.printStackTrace();
            return inoculateRate;
        }
    }
    /**
     * 保留两位小数
     * @param value
     * @return
     */
    public Double decimals2(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 接种率超期接种或应种未种儿童信息查询
     *
     * @param param
     * @param chilCommittees
     * @param infostatus
     * @param biotypes
     * @param page
     * @param limit
     * @return io.yfjz.utils.R
     * @describe:
     * @author 邓召仕
     * @date: 2018-08-24  15:59
     **/
    @RequestMapping("rateChildInfo")
    @ResponseBody
    public R rateChildInfo(@RequestParam Map<String, Object> param,
                           @RequestParam(value = "chilCommittees[]", required = false) String[] chilCommittees,
                           @RequestParam(value = "infostatus[]", required = false) String[] infostatus,
                           @RequestParam(value = "biotypes[]", required = false) String[] biotypes, Integer page, Integer limit) {
        String selectType = (String) param.get("selectType");
        String classId = (String) param.get("classId");
        Integer agentTime = Integer.parseInt((String) param.get("agentTime"));
        String chilBirthdayStartStr = (String) param.get("chilBirthdayStart");
        String chilBirthdayEndStr = (String) param.get("chilBirthdayEnd");
        String chilResidence = (String) param.get("chilResidence");
        String chilAccount = (String) param.get("chilAccount");
        String random = param.get("random") == null? null : (String) param.get("random");//随机数
        //参数类型转换
        Date chilBirthdayStart = null;
        Date chilBirthdayEnd = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(chilBirthdayStartStr)) {
            try {
                chilBirthdayStart = sdf.parse(chilBirthdayStartStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(chilBirthdayEndStr)) {
            try {
                chilBirthdayEnd = sdf.parse(chilBirthdayEndStr);
            } catch (ParseException e) {
            }
        }
        //服务调用
        List<InoculateRate> inoculateRates = inoStatisticsSerivce.getRateChildInfo(selectType, classId, agentTime, chilBirthdayStart, chilBirthdayEnd, chilResidence,
                chilCommittees, infostatus, biotypes, chilAccount,random, page, limit);
        int total = inoStatisticsSerivce.getRateChildInfoCount(selectType, classId, agentTime, chilBirthdayStart, chilBirthdayEnd, chilResidence,
                chilCommittees, infostatus, biotypes, chilAccount,random);

        PageUtils pageUtil = new PageUtils(inoculateRates, total, limit, page);
        return R.ok().put("page", pageUtil);

    }
}
