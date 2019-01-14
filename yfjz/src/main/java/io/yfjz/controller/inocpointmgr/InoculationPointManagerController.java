package io.yfjz.controller.inocpointmgr;

import com.alibaba.fastjson.JSONObject;
import io.yfjz.entity.queue.QueueConfig;
import io.yfjz.entity.queue.StepType;
import io.yfjz.entity.queue.TQueueNoEntity;
import io.yfjz.entity.sys.SysConfigurationEntity;
import io.yfjz.entity.sys.SysDictEntity;
import io.yfjz.service.inocpointmgr.InoculationManagerService;
import io.yfjz.service.inocpointmgr.TBaseGetnumsService;
import io.yfjz.service.queue.TQueueNoService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘琪
 * @class_name: InoculationPointManagerController
 * @Description: 接种点管理控制器类
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-07-25 14:10
 */
@Controller
@RequestMapping("/inocpointmgr")
public class InoculationPointManagerController {
    @Autowired
    private TBaseGetnumsService tBaseGetnumsService;
    @Autowired
    private InoculationManagerService inoculationManagerService;
    @Autowired
    private TQueueNoService tQueueNoService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private QueueConfig queueConfig;
    /**
     * 获取本接种点取号设置
     * @describe:
     * @param request
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-08-31  14:22
     **/
    @ResponseBody
    @RequestMapping("/offernumberInfo")
    public R offernumberInfo(HttpServletRequest request){
        HttpSession session = request.getSession();
        String orgId = (String) session.getAttribute("GLOBAL_ORG_ID");
        Map tBaseGetnums = tBaseGetnumsService.queryObject(orgId);
        if (tBaseGetnums != null){
            return R.ok().put("tBaseGetnums", tBaseGetnums);
        }else {
            return R.error();
        }
    }

    /**
     * 保存接种点取号设置
     * @describe:
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-08-31  14:23
     **/
    @ResponseBody
    @RequestMapping("/offernumberSave")
    public R offernumberSave(@RequestParam Map<String,Object> param,HttpServletRequest request){
        HttpSession session = request.getSession();
        String orgId = (String) session.getAttribute("GLOBAL_ORG_ID");
        tBaseGetnumsService.saveOffernumber(param,orgId);
        return R.ok();
    }
    @ResponseBody
    @RequestMapping("/offernumberStatus")
    public R offernumberState(Integer status,HttpServletRequest request){
        HttpSession session = request.getSession();
        String orgId = (String) session.getAttribute("GLOBAL_ORG_ID");
        tBaseGetnumsService.offernumberState(status,orgId);
        return R.ok();
    }
    /** 
    * @Description: 更新条码打印机IP地址
    * @Param: [ipAddress, request] 
    * @return: io.yfjz.utils.R 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/31 19:02
    * @Tel  17328595627
    */ 
    @ResponseBody
    @RequestMapping("/updateBarCodeIp")
    public R updateBarCodeIp(String ipAddress,String dictId,HttpServletRequest request){
        SysConfigurationEntity entity = new SysConfigurationEntity();
        if (!StringUtils.isEmpty(dictId)){
            entity.setId(dictId);
        }
        entity.setCreateTime(new Date());
//        entity.setText("条码打印机IP地址");
        entity.setType("barCodeIpAddress");
        entity.setRemark(ipAddress);
        entity.setState(0);
        entity.setStartUsing(0);
//        entity.setRemark("条码打印机IP地址");
        String msg="";
        try {
            tBaseGetnumsService.updateBarCodeIp(entity);
            msg="更新成功！";
        } catch (Exception e) {
            msg="更新失败，系统错误！";
            e.printStackTrace();
            return R.error().put("msg",msg);
        }
        return R.ok().put("msg",msg);
    }
    @RequestMapping("processSet")
    @ResponseBody
    public R processSet(String param){
        inoculationManagerService.processSet(param);
        return R.ok();
    }
    @RequestMapping("processList")
    @ResponseBody
    public Map processList(){

        Map<String,Object> map=inoculationManagerService.processList();
        return map;
    }

    //设置----------------------------------------------
    /**
     * @method_name: 是否启用排队叫号
     * @describe:
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-11-23  11:13
     **/
    @ResponseBody
    @RequestMapping("/setQueuesSatus")
    public R setQueuesSatus(Integer status){
        try{
            tBaseGetnumsService.setSysConType(status,Constant.QUEUE_TYPE,"是否启用排队叫号");
            return R.ok();
        }catch (Exception e){
            return R.error("设置失败");
        }

    }
    /**
     * @method_name: 获取是否启用排队叫号
     * @describe:
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-11-23  11:13
     **/
    @ResponseBody
    @RequestMapping("/getQueuesSatus")
    public R getQueuesSatus(){
        return tBaseGetnumsService.getSysConType(Constant.QUEUE_TYPE,1);
    }
    /**
     * @method_name: 是否启用分队列
     * @describe:
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-11-23  11:14
     **/
    @ResponseBody
    @RequestMapping("/setQueuesDistinction")
    public R setQueuesDistinction(Integer status){
        try{
            if(status != null && status.intValue() ==1){//启用分队列
                HashMap<String,Object> paramMap = new HashMap<>();
                paramMap.put("step","inoculate");
                List<TQueueNoEntity> inoList = tQueueNoService.getStepQueueList(paramMap);
                if (inoList != null && !inoList.isEmpty()){
                    return R.error("请先接种完公共队列的儿童！");
                }
            }
            tBaseGetnumsService.setSysConType(status,Constant.QUEUE_DISTINCTION,"是否启用多队列");
            //刷新接种队列
            JSONObject order = new JSONObject();
            order.put("action","QUEUEREFRESH");
            order.put("src",status);
            jmsTemplate.convertAndSend(queueConfig.getTopicMap().get("all"),order);
            return R.ok();
        }catch (Exception e){
            return R.error("设置失败");
        }
    }
    /**
     * @method_name: 获取是否启用分队列
     * @describe:
     * @return io.yfjz.utils.R
     * @author 邓召仕
     * @date: 2018-11-23  11:13
     **/
    @ResponseBody
    @RequestMapping("/getQueuesDistinction")
    public R getQueuesDistinction(){
        return tBaseGetnumsService.getSysConType(Constant.QUEUE_DISTINCTION,0);
    }
}
