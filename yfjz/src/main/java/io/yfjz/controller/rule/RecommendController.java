package io.yfjz.controller.rule;

import io.yfjz.entity.rule.ChildData;
import io.yfjz.entity.rule.TRulePlanEntity;
import io.yfjz.managerservice.rule.RecommendService;
import io.yfjz.service.bus.BatchInoculateService;
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
import java.util.*;

/**
 * 登记疫苗推荐
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-08-16 14:01
 */
@Controller
@RequestMapping("recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private BatchInoculateService batchInoculateService;

    /**
     * 登记疫苗推荐
     * @describe:
     * @param childCode 儿童编码
     * @return java.util.List<java.util.Map < java.lang.String , java.lang.String>> json数组，包含1、code（疫苗编码）；2、fk_store_id （库存批号）
     * @author 邓召仕
     * @date: 2018-08-17  13:50
     **/
    @RequestMapping("recommend")
    @ResponseBody
    public List<Map<String,String>> getRecommendByChild(String childCode){
        List<TRulePlanEntity> list = recommendService.getrecommend(childCode);
        List<Map<String,String>> resultList = new ArrayList<>();
        if (list != null){
            for (TRulePlanEntity planEntity : list){
                String recommendStr = planEntity.getInoculateId();
                if (recommendStr != null){
                    String[] results = recommendStr.split(",");
                    Map<String,String> map = new HashMap<>();
                    map.put("code",results[0]);
                    map.put("batchnum",results[1]);
                    resultList.add(map);
                }
            }
        }
        return resultList;
    }

    /**
     * 判断疫苗是否可以接种
     * @describe:
     * @param childCode 儿童编码
     * @param bioCode 疫苗编码
     * @return io.yfjz.utils.R code为500时表示提前或间短（具体看提示消息msg）
     * @author 邓召仕
     * @date: 2018-08-17  15:23
     **/
    @RequestMapping("judge")
    @ResponseBody
    public R judgeOkIno(String childCode,String bioCode){
        return  recommendService.judgeOkIno(childCode,bioCode);
    }

    /**
     * 应种通知
     * @describe:
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-08-20  9:45
     **/
    @RequestMapping("inoNote")
    @ResponseBody
    public R inoNote(@RequestParam Map<String,Object> param,
                     @RequestParam(value = "chilCommittees",required=false) String[] chilCommittees,
                     @RequestParam(value = "infostatus",required=false) String[] infostatus,
                     @RequestParam(value = "biotypes",required=false) String[] biotypes){
        String selectType = (String) param.get("selectType");
        String limitType = (String) param.get("limitType");
        String monthLimitStr = (String) param.get("monthLimit");
        String chilBirthdayStartStr = (String) param.get("chilBirthdayStart");
        String chilBirthdayEndStr = (String) param.get("chilBirthdayEnd");
        String selectDateStr = (String) param.get("selectDate");
        String planDateStr = (String) param.get("planDate");

        String chilResidence = (String) param.get("chilResidence");
        String chilAccount = (String) param.get("chilAccount");
        String chilSchool = (String) param.get("chilSchool");
        //添加条件根据儿童性别查询
        String chilSex = String.valueOf(param.get("chilSex"));

        //参数类型转换
        Integer monthLimit = 0;
        Date chilBirthdayStart = null;
        Date chilBirthdayEnd = null;
        Date selectDate = new Date();
        Date planDate = null;
        if (!StringUtils.isEmpty(monthLimitStr)){
            monthLimit = Integer.parseInt(monthLimitStr);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(chilBirthdayStartStr)){
            try {
                chilBirthdayStart = sdf.parse(chilBirthdayStartStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(chilBirthdayEndStr)){
            try {
                chilBirthdayEnd = sdf.parse(chilBirthdayEndStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(selectDateStr)){
            try {
                selectDate = sdf.parse(selectDateStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(planDateStr)){
            try {
                planDate = sdf.parse(planDateStr);
            } catch (ParseException e) {
            }
        }
//服务调用
        List<ChildData> list = recommendService.inoNoteTwoOne(selectType,limitType,monthLimit,chilBirthdayStart,chilBirthdayEnd,
                selectDate,planDate,chilCommittees,infostatus,biotypes,chilResidence,chilAccount,chilSchool,chilSex);
        /**
         * 保存查询结果
         * @author 田金海
         * @date 2018/09/05
         */
        //判断查询出来的儿童信息是否为空，如果不为空，就不保存查询记录
        if(list != null && list.size() != 0){
            //保存查询条件
            batchInoculateService.saveQueryResult(list,param);
        }

        if (list != null && !list.isEmpty()){
            PageUtils pageUtil = new PageUtils(list, list.size(), list.size(), 1);
            List<ChildData> countDatas = recommendService.countPlanTimes(list);//行政村疫苗剂次汇总
            List<ChildData> inoTotals = recommendService.countPlanNameTimes(list);//疫苗名称汇总
            return R.ok().put("page",pageUtil).put("counList",countDatas).put("inototals",inoTotals);
        }else {
            //return R.error("没有查询到数据");
            return R.ok(100,"没有查询到数据");
        }
    }
    @RequestMapping("inoNoteExcel")
    @ResponseBody
    public void inoNoteExcel(HttpServletResponse response, @RequestParam Map<String,Object> param,
                             @RequestParam(value = "chilCommittees",required=false) String[] chilCommittees,
                             @RequestParam(value = "infostatus",required=false) String[] infostatus,
                             @RequestParam(value = "biotypes",required=false) String[] biotypes){
        String selectType = (String) param.get("selectType");
        String limitType = (String) param.get("limitType");
        String monthLimitStr = (String) param.get("monthLimit");
        String chilBirthdayStartStr = (String) param.get("chilBirthdayStart");
        String chilBirthdayEndStr = (String) param.get("chilBirthdayEnd");
        String selectDateStr = (String) param.get("selectDate");
        String planDateStr = (String) param.get("planDate");

        String chilResidence = (String) param.get("chilResidence");
        String chilAccount = (String) param.get("chilAccount");
        String chilSchool = (String) param.get("chilSchool");
        //添加条件根据儿童性别查询
        String chilSex = String.valueOf(param.get("chilSex"));
        //参数类型转换
        Integer monthLimit = 0;
        Date chilBirthdayStart = null;
        Date chilBirthdayEnd = null;
        Date selectDate = new Date();
        Date planDate = null;
        if (!StringUtils.isEmpty(monthLimitStr)){
            monthLimit = Integer.parseInt(monthLimitStr);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(chilBirthdayStartStr)){
            try {
                chilBirthdayStart = sdf.parse(chilBirthdayStartStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(chilBirthdayEndStr)){
            try {
                chilBirthdayEnd = sdf.parse(chilBirthdayEndStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(selectDateStr)){
            try {
                selectDate = sdf.parse(selectDateStr);
            } catch (ParseException e) {
            }
        }
        if (!StringUtils.isEmpty(planDateStr)){
            try {
                planDate = sdf.parse(planDateStr);
            } catch (ParseException e) {
            }
        }
        //服务调用
        List<ChildData> list = recommendService.inoNoteTwoOne(selectType,limitType,monthLimit,chilBirthdayStart,chilBirthdayEnd,
                selectDate,planDate,chilCommittees,infostatus,biotypes,chilResidence,chilAccount,chilSchool,chilSex);

        /**
         * 保存查询结果
         * @author 田金海
         * @date 2018/09/05
         */
        //判断查询出来的儿童信息是否为空，如果不为空，就不保存查询记录
        if(list != null && list.size() != 0){
            //保存查询条件
            batchInoculateService.saveQueryResult(list,param);
        }

        if (list != null && !list.isEmpty()){
            List<ChildData> countDatas = recommendService.countPlanTimes(list);
            List<ChildData> inoTotals = recommendService.countPlanNameTimes(list);//疫苗名称汇总
            String excelTite = "未种通知统计";
            String[] titles = { "行政村,committee","儿童编码,chilCode", "姓名,chilName", "性别,chilSex" , "出生日期,chilBirthday","父亲姓名,fatherName","母亲姓名,matherName",
                    "家庭电话,chilTel", "手机,chilMobile", "联系地址,address" , "疫苗,planName","剂次,times","日期,inoDate"};
            String[] titles1 = { "行政村,committee","疫苗剂次,planName", "人数,times"};
            String[] titles2 = { "疫苗名称,planName", "人数,times"};
            String manName = ShiroUtils.getUserEntity().getRealName()==null?"":ShiroUtils.getUserEntity().getRealName();
            ExcelUtil.export1(response,manName,excelTite,titles,titles1,titles2,list,countDatas,list.size(),inoTotals,countDatas.size());
        }
    }

    /**
     * 下次预约接口
     * @describe:
     * @param childId
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-09-11  11:12
     **/
    @RequestMapping("/nextIno")
    @ResponseBody
    public R nextIno(String childId){
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return recommendService.nextIno(childId,orgId);
    }
    /**
     * 全程预约接口
     * @describe:
     * @param childId
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-09-11  11:12
     **/
    @RequestMapping("/childAllPlan")
    @ResponseBody
    public R childAllPlan(String childId){
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return recommendService.childAllPlan(childId,orgId);
    }
}
