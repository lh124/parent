package io.yfjz.controller.child;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.tools.doclets.internal.toolkit.NestedClassWriter;
import freemarker.core.StringArraySequence;
import io.yfjz.dao.child.TChildInoculateDao;
import io.yfjz.entity.basesetting.TVacInfoEntity;
import io.yfjz.entity.child.InoculateIntegrityRateEntity;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.child.TChildInoculateEntity;
import io.yfjz.entity.rule.TRulePlanConsultEntity;
import io.yfjz.managerservice.rule.InitPlanService;
import io.yfjz.managerservice.rule.RecommendService;
import io.yfjz.service.child.TChildInfoService;
import io.yfjz.service.child.TChildInoculateService;
import io.yfjz.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 儿童接种记录表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-27 14:02:29
 */
@Controller
@RequestMapping("tchildinoculate")
public class TChildInoculateController {
    @Autowired
    private  TChildInoculateService tChildInoculateService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private TChildInfoService tChildInfoService;
    @Autowired
    InitPlanService initPlanService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<TChildInoculateEntity> tChildInoculateList = tChildInoculateService.queryList(query);

        int total = tChildInoculateService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(tChildInoculateList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
    @ResponseBody
    @RequestMapping("/jzbl")
    public R jzbl(@RequestParam Map<String, Object> map){
        List<TChildInoculateEntity> tChildInoculateList=tChildInoculateService.querylistjzbl(map);
        return R.ok().put("page", tChildInoculateList);
    }
    /**
     * 上传记录列表
     */
    @ResponseBody
    @RequestMapping("/uploadRecord")
    public R uploadRecord(@RequestParam Map<String, Object> params,Integer page,Integer limit,HttpServletRequest request) {
        //查询列表数据
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);

        List<Map<String,Object>> tChildInoculateList = tChildInoculateService.queryUploadRecord(map);

        int total = tChildInoculateService.queryTotalUploadRecord(map);

        PageUtils pageUtil = new PageUtils(tChildInoculateList, total,limit,page);

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{id}")
    /*@RequiresPermissions("tchildinoculate:info")*/
    public R info(@PathVariable("id") String id){
        TChildInoculateEntity tChildInoculate = tChildInoculateService.queryObject(id);

        return R.ok().put("tChildInoculate", tChildInoculate);
    }

    /**
     * 获取最后一次接种信息
     */
    @ResponseBody
    @RequestMapping("/LastInoInfo/{id}")
    public R LastInoInfo(@PathVariable("id") String id){
        TChildInoculateEntity tChildInoculate = tChildInoculateService.queryLastInoObject(id);

        return R.ok().put("tChildInoculate", tChildInoculate);
    }

    /**
     * 批量补录
     */
    @RequestMapping("/batchInsertAccountInfo")
    @ResponseBody
    public R batchInsertAccountInfo(@RequestParam(value = "inoculate",required = false) String inoculate){
        System.out.println("=================");
        System.out.println(inoculate);
        System.out.println("=================");
        JSONArray jsonArray  = JSONArray.parseArray(inoculate);
        for (Object jsonObject : jsonArray) {
            TChildInoculateEntity platformModel = JSONObject.parseObject(jsonObject.toString(), TChildInoculateEntity.class);
            TChildInfoEntity tChildInfoEntity= tChildInfoService.queryObject(platformModel.getChilCode());
            if(tChildInfoEntity.getChilBirthday().after(platformModel.getInocDate())){
                return R.error("接种时间不小于出生时间");
            }
            R r = tChildInoculateService.batchInsertAccountInfo(platformModel);
            if(r.get("code").toString().equals("500")){
                return R.error("该疫苗记录的剂次已存在");
            }
        }
        return R.ok();
    }
    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@RequestBody TChildInoculateEntity tChildInoculate,HttpServletRequest req){
//		tChildInoculate.setChilCode(req.getParameter("chilCode"));
        tChildInoculateService.save(tChildInoculate);


        return R.ok();
    }

    /**
     * 修改接种记录并将原数据另存备份
     * @author 饶士培
     * @time 2018-09-26
     * @param tChildInoculate
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveAsBackUp")
    public R saveAsBackUp(@RequestBody TChildInoculateEntity tChildInoculate,HttpServletRequest req){
        TChildInoculateEntity tChildInoculateBackUp = tChildInoculateService.queryObject(tChildInoculate.getId());
        tChildInoculateBackUp.setRemark(tChildInoculate.getRemark());
        tChildInoculateBackUp.setCreateUserId(ShiroUtils.getUserEntity().getRealName());
        Map<String,Object> map = new HashMap<>();
        map.put("childCode", tChildInoculate.getChilCode());
        map.put("inoculateSiteCode",tChildInoculate.getInocInplId());
        map.put("inocDate",tChildInoculate.getInocDate());
        map.put("Id",tChildInoculate.getId());
        int count= tChildInoculateService.queryUpdateInoculateNumByMap(map);
        TChildInfoEntity tChildInfoEntity= tChildInfoService.queryObject(tChildInoculate.getChilCode());
        if(tChildInfoEntity.getChilBirthday().after(tChildInoculate.getInocDate())){
            return R.error("接种时间不小于出生时间");
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("chilCode",  tChildInoculate.getChilCode());
        map1.put("inocBactCode", tChildInoculate.getInocBactCode());
        map1.put("Id", tChildInoculate.getId());
        List<TChildInoculateEntity> list = tChildInoculateService.queryUpdateInoculateDate(map1);
        for (TChildInoculateEntity entity: list ){
            if(entity.getInocTime()==tChildInoculate.getInocTime() && !tChildInoculate.getInocProperty().equals("2")
                    ||(tChildInoculate.getInocTime()>entity.getInocTime()
                    && tChildInoculate.getInocDate().before(entity.getInocDate()))){
                return R.error("同类疫苗剂次不能等于或小于上一剂次时间");
            }
        }

        if(count>0 || (count  > 0 && tChildInoculate.getInocProperty().equals("2"))){//接种属性是强化 可以保存
            return R.error("同一接种部位同一天不能重复接种");
        }else{
            tChildInoculateService.saveAsBackUp(tChildInoculateBackUp);
            tChildInoculateService.update(tChildInoculate);
        }
        return R.ok();
    }



    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(@RequestBody TChildInoculateEntity tChildInoculate){
        tChildInoculateService.update(tChildInoculate);

        return R.ok();
    }

    /**
     * 删除并备份
     */
    @ResponseBody
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids,@RequestParam Map<String, Object> params){
        TChildInoculateEntity tChildInoculateBackUp = tChildInoculateService.queryObject(ids[0]);
        tChildInoculateBackUp.setRemark(params.get("remark").toString());
        tChildInoculateService.saveAsBackUp(tChildInoculateBackUp);
        tChildInoculateService.deleteBatch(ids);
        try{
            initPlanService.initChildPlan(tChildInoculateBackUp.getChilCode());
        }catch (Exception e){
            e.printStackTrace();
        }

        return R.ok();
    }


    /**
     * @method_name: saveInoculate
     * @describe: 完成注射
     * @param: [request, response]
     * @return: io.yfjz.utils.R
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/30  15:53
     **/
    @ResponseBody
    @RequestMapping("finished")
    public R finished(HttpServletRequest request, HttpServletResponse response,@RequestBody List<Map> mapList){
        //查询列表数据
        try {
            return tChildInoculateService.finished(mapList);
        } catch (Exception e) {
            return R.error("系统异常，请联系管理员");
        }
    }

    /**儿童接种信息完整性统计-搜索
     * @method_name: childGather
     * @describe:
     * @param param, request, response]
     * @return void
     * @author 饶士培
     * @QQ: 1013147559@qq.com
     * @tel:18798010686
     * @date: 2018-09-14  9:19
     **/
    @RequestMapping("/inoculateGather")
    @ResponseBody
    public R inoculateGather(@RequestParam Map<String,Object> param,Integer page, Integer limit, HttpServletRequest request) {
        final Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        final List<InoculateIntegrityRateEntity>integrityRate = tChildInoculateService.listInocIntegrityRate(map);
        if(integrityRate!=null){
            PageUtils pageUtil = new PageUtils(integrityRate, integrityRate.size(), limit, page);
            return R.ok().put("page", pageUtil);
        }else{
            return R.error("没有查找到数据");
        }
    }

    /**入园证明，接种记录查询
     * @method_name: choolAdmission
     * @describe:
     * @param param, request, response]
     * @return void
     * @author 饶士培
     * @QQ: 1013147559@qq.com
     * @tel:18798010686
     * @date: 2018-09-14  9:19
     **/
    @RequestMapping("/choolAdmission")
    @ResponseBody
    public R choolAdmission(@RequestParam Map<String,Object> param,Integer page, Integer limit, HttpServletRequest request) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        List<Map<String,Object>>inoList = tChildInoculateService.getchoolAdmission(map.get("chilCode").toString());
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        R r = recommendService.childAllPlan(map.get("chilCode").toString(),orgId);
        if(inoList!=null){
            PageUtils pageUtil = new PageUtils(inoList, inoList.size(), 1000, 1);
            return R.ok().put("page", pageUtil).put("plan",r);
        }else{
            return R.error("没有查找到数据");
        }

    }


    /**处方单查询
     * @method_name: prescription
     * @describe:
     * @param param, request, response]
     * @return void
     * @author 饶士培
     * @QQ: 1013147559@qq.com
     * @tel:18798010686
     * @date: 2018-09-21  9:19
     **/
    @RequestMapping("/prescription")
    @ResponseBody
    public R prescription(@RequestParam Map<String,Object> param,Integer page, Integer limit, HttpServletRequest request) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        List<Map<String,Object>>inoList = tChildInoculateService.getPrescription(map.get("chilCode").toString());
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        if(inoList!=null){
            PageUtils pageUtil = new PageUtils(inoList, inoList.size(), 1000, 1);
            return R.ok().put("page", pageUtil);
        }else{
            return R.error("没有查找到数据");
        }

    }


    /**登记疫苗查询
     * @method_name: getCurrentRegisterVacc
     * @describe:
     * @param param, request, response]
     * @return void
     * @author 饶士培
     * @QQ: 1013147559@qq.com
     * @tel:18798010686
     * @date: 2018-10-30  9:19
     **/
    @RequestMapping("/getCurrentRegisterVacc")
    @ResponseBody
    public R getCurrentRegisterVacc(@RequestParam Map<String,Object> param,Integer page, Integer limit, HttpServletRequest request) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        List<Map<String,Object>>inoList = tChildInoculateService.getCurrentRegisterVacc(map.get("chilCode").toString());
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        if(inoList!=null){
            PageUtils pageUtil = new PageUtils(inoList, inoList.size(), 1000, 1);
            return R.ok().put("page", pageUtil);
        }else{
            return R.error("没有查找到数据");
        }

    }

    /**今日接种记录查询
     * @method_name: getCurrentRegisterVacc
     * @describe:
     * @param param, request, response]
     * @return void
     * @author 饶士培
     * @QQ: 1013147559@qq.com
     * @tel:18798010686
     * @date: 2018-10-31  9:19
     **/
    @RequestMapping("/getCurrentInoculateVacc")
    @ResponseBody
    public R getCurrentInoculateVacc(@RequestParam Map<String,Object> param,Integer page, Integer limit, HttpServletRequest request) {
        Map<String, Object> map = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        List<Map<String,Object>>inoList = tChildInoculateService.getCurrentInoculateVacc(map.get("chilCode").toString());
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        if(inoList!=null){
            PageUtils pageUtil = new PageUtils(inoList, inoList.size(), 1000, 1);
            return R.ok().put("page", pageUtil);
        }else{
            return R.error("没有查找到数据");
        }

    }


    /**
     * 留观查询
     * @param request
     * @param response
     */
    @RequestMapping("/queryobservation")
    @ResponseBody
    public void queryobservation(HttpServletRequest request,HttpServletResponse response){
        String string = request.getParameter("jsoncallback");

        String code=request.getParameter("notext").trim();
//		System.out.println("=========="+code);
        String str = "";
        if(!code.contains("A")){
            str="A"+code;
        }else if(code.contains("code")){
            System.out.println(code);
        }
        else{
            str=code.substring(1);
//			str=code;
        }
        List<Map<String, Object>> notext = tChildInoculateService.queryobservation(str);
        if(notext!=null && notext.size()!=0){
            TChildInoculateEntity tChildInoculateEntity= new TChildInoculateEntity();
            tChildInoculateEntity.setChilCode(notext.get(0).get("child_code").toString());
            tChildInoculateEntity.setLeaveTime(new Date());
            tChildInoculateService.observeupdate(tChildInoculateEntity);
        }
        List<Map<String, Object>> list = null ;
        for (Map<String,Object> map:notext){

            list = tChildInoculateService.queryhistoryobservation(map.get("child_code").toString());
        }
        Map<String,Object> maps = new HashMap<>();
        maps.put("data", notext);
        maps.put("list", list);
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().write(string + "("+JSON.toJSONString(maps)+")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取常规疫苗
     * @return
     */
    @RequestMapping("/outsideinoculatebio")
    @ResponseBody
    public R outsideinoculatebio(HttpServletRequest request,HttpServletResponse response){
        List<TVacInfoEntity> list =tChildInoculateService.outsideinoculatebio();
        return R.ok().put("data", list);
    }
    /**
     * 外省补录
     * @return
     */
    @RequestMapping("/outsideinoculate")
    @ResponseBody
    public R outsideinoculate(@RequestParam(value = "inoculate",required = false) String inoculate){
        System.out.println(inoculate);
        JSONArray jsonArray  = JSONArray.parseArray(inoculate);
        for (Object jsonObject : jsonArray) {
            TChildInoculateEntity platformModel = JSONObject.parseObject(jsonObject.toString(), TChildInoculateEntity.class);
            TChildInfoEntity tChildInfoEntity= tChildInfoService.queryObject(platformModel.getChilCode());
            if(tChildInfoEntity.getChilBirthday().after(platformModel.getInocDate())){
                return R.error("接种时间不小于出生时间");
            }
            R r = tChildInoculateService.batchInsertAccountInfos(platformModel);
            if(r.get("code").toString().equals("500")){
                return R.error("该疫苗记录的剂次已存在");
            }
        }
        return R.ok();
    }

    @RequestMapping("/getbioClassIdType")
    @ResponseBody
    public R getbioClassIdType(HttpServletResponse response,HttpServletRequest request){
        List<TVacInfoEntity> list =tChildInoculateService.getBioClassIdType(request.getParameter("bioClasssId"));
        return  R.ok().put("data", list);
    }
}
