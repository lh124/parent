package io.yfjz.controller.provinceplatform;


import io.yfjz.controller.sys.SysConfigurationController;
import io.yfjz.entity.child.TChildInfoEntity;
import io.yfjz.entity.provinceplatform.Child;
import io.yfjz.entity.provinceplatform.Children;
import io.yfjz.managerservice.provinceplatform.ProvincePlatformServiceManager;
import io.yfjz.service.child.TChildInfoService;
import io.yfjz.service.provinceplatform.ProvincePlatformService;
import io.yfjz.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 省平台交互
 * @author 饶士培
 * @date 2018-08-02
 */
@Controller
@RequestMapping("/provincePlatform")
public class ProvincePlatformController {
    @Autowired
    ProvincePlatformService provincePlatformService;
    @Autowired
    ProvincePlatformServiceManager provincePlatformServiceManager;
    @Autowired
    private TChildInfoService tChildInfoService;

    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ProvincePlatformController.class);

    @RequestMapping("/findChildOnProvincePlatform")
    @ResponseBody
    public R dispatcher(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> requestMap = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String departmentCode =Constant.GLOBAL_ORG_ID;
        if (!StringUtils.isEmpty(departmentCode)) {
            requestMap.put("departmentCode", Constant.GLOBAL_ORG_ID);
        }
        Children children = provincePlatformService.findOnProvincePlatform(requestMap);
        List<Child> childList = new ArrayList<>();
        if(children!=null){
            for(Child child:children.getChilds()) {
                childList.add(child);
            }
        }else{
            return R.error();
        }
        PageUtils pageUtil = new PageUtils(childList, 1, 100, 1);
        return R.ok().put("page", pageUtil);
        //return R.ok().put("children", children);
    }

    @RequestMapping("/saveToLocal")
    @ResponseBody
    public R saveToLocal(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> requestMap = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String departmentCode = Constant.GLOBAL_ORG_ID;
        if (!StringUtils.isEmpty(departmentCode)) {
            requestMap.put("departmentCode", departmentCode);
        }
        Children children  = provincePlatformService.findOnProvincePlatform(requestMap);
        try {
            if(children != null && children.getChilds() != null){
                for(int i = 0;i < children.getChilds().size();i++) {
                    //判断传的key是否为空，如果为空就赋值
                    if (requestMap.containsKey("chilAccount")) {
                        children.getChilds().get(i).setChilAccount((String) requestMap.get("chilAccount"));
                    }
                    if (requestMap.containsKey("chilHere")) {
                        children.getChilds().get(i).setChilHere((String) requestMap.get("chilHere"));
                    }
                    if (requestMap.containsKey("chilResidence")) {
                        children.getChilds().get(i).setChilResidence((String) requestMap.get("chilResidence"));
                    }
                }
            }
        }catch (Exception e){
            log.info("下载新生儿时，修改在册情况、居住属性和户籍属性修改错误！");
            log.error(this,e);
        }

        R r =  provincePlatformService.saveToLocal(children, departmentCode,"0");
        return r;

    }

    /**
     * 下载新生儿
     * @author 饶士培
     * @date 2018-09-06
     * @param request
     * @param count
     * @return
     */
    @RequestMapping("/saveNewBorn")
    @ResponseBody
    public R downLoadNewBorn(HttpServletRequest request,@RequestParam Integer count) {
        Map<String, Object> requestMap = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String departmentCode = Constant.GLOBAL_ORG_ID;
        requestMap.put("departmentCode", departmentCode);
        //requestMap.put("count", count);
        //requestMap.put("departmentCode", departmentCode);
        String result = null;
        result = provincePlatformService.downNewbornOnProvincePlatform(requestMap,count);
        while ("1".equals(result)){
            count++;
           /* if(count>4){
                return R.ok().put("count",count);
            }*/
            result = provincePlatformService.downNewbornOnProvincePlatform(requestMap,count);
        }
        if("9".equals(result)&& count==0){
            return R.error("未查找到记录");
        }
        if(result ==null){
            return R.error();
        }
        return R.ok().put("count",count);
    }

    /**
     * 上传儿童信息
     * @author 饶士培
     * @date 2018-09-06
     * @param request
     * @return
     */
    @RequestMapping("/uploadPlat")
    @ResponseBody
    public R uploadPlat(HttpServletRequest request) {
        Map<String, Object> requestMap = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        String departmentCode = Constant.GLOBAL_ORG_ID;
        requestMap.put("departmentCode", departmentCode);
        String password = departmentCode;
        String childCodes = (String) requestMap.get("childCode");
        String[] child = new String[100];
        for (int i  = 0;i < childCodes.length();i++) {
            child = childCodes.split(",");
        }
        String result = "0";
        try{
             result = provincePlatformService.uploadChildInoculations(departmentCode,password,child);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.ok(result);
    }

    /**
     * 上传全部儿童信息
     * @author 饶士培
     * @date 2018-09-06
     * @param request
     * @return
     */
    @RequestMapping("/uploadPlatAll")
    @ResponseBody
    public R uploadPlatAll(HttpServletRequest request) {
        Map<String, Object> requestMap = HttpServletRequestToMapUtils.getParamMapByHttpServletRequest(request);
        //Query param = new Query(requestMap);
        String departmentCode = Constant.GLOBAL_ORG_ID;
        String password = departmentCode;
        requestMap.put("org_id",departmentCode);
        List<TChildInfoEntity> getListUnSyncstatusInocChild = null;
        if(requestMap.get("allChild")!=null){
             getListUnSyncstatusInocChild= tChildInfoService.getListUnSyncstatusInocChild(requestMap);
        }else {
             getListUnSyncstatusInocChild = tChildInfoService.currentDayInocChild(requestMap);
        }
        String result = "0";
        if(getListUnSyncstatusInocChild!=null&&getListUnSyncstatusInocChild.size()>0){
            String[] childId = new String[getListUnSyncstatusInocChild.size()];
            for(int i = 0;i < getListUnSyncstatusInocChild.size();i++){
                childId[i] = getListUnSyncstatusInocChild.get(i).getChilCode();
//                System.out.println("===="+i+"===="+childId[i]);
            }
            try{
                result = provincePlatformService.uploadChildInoculations(departmentCode,password,childId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            return R.ok("暂无未同步数据");
        }

        return R.ok(result);
    }

    /**
     * 下载已在异地接种儿童
     * @author 饶士培
     * @date 2018-09-06
     * @param
     * @return
     */
    @RequestMapping("/downloadChild")
    @ResponseBody
    public R downloadChild() {
        int days = -7;
        R r = null;
        try{
             r = provincePlatformServiceManager.downloadMigrationChildNo(days);
        }catch (Exception e){
            e.printStackTrace();
        }
        return r;
    }

}