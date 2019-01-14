package io.yfjz.controller.queue;

import com.alibaba.fastjson.JSONObject;
import io.yfjz.dao.sys.SysDictDao;
import io.yfjz.entity.basesetting.TBaseTowerEntity;
import io.yfjz.entity.queue.QueueConfig;
import io.yfjz.entity.queue.TQueueNoEntity;
import io.yfjz.service.basesetting.TMvRelevanceService;
import io.yfjz.service.queue.TQueueNoService;
import io.yfjz.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


/**
 * 
 * 
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-22 23:59:55
 */
@RestController
@RequestMapping("queue")
public class TQueueNoController {
	@Autowired
	private TQueueNoService tQueueNoService;
    @Autowired
    private TMvRelevanceService tMvRelevanceService;
    @Autowired
    private SysDictDao sysDictDao;

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private QueueConfig queueConfig;
	@GetMapping("/createNo")
    public R createNo(String childCode){
        return tQueueNoService.cateateNo(childCode);
    }

    /*@GetMapping("/finished/{queueId}")
    public void finished(@PathVariable("queueId") String queueId){
	    tQueueNoService.finishedCurrentStep("register",queueId,"001","linhai");
    }*/

   /* @GetMapping("isDisableQueue")
    public String isDisableQueue(){
	    return sysDictDao.isDisableQueue();
    }*/

    /**
     * 从数据库获取队列
      * @param step 当前步骤
     * @param position 当前桌台
     * @return
     */
    @GetMapping("getOwnQueueList/{step}/{position}")
    public List<TQueueNoEntity> getOwnQueueList(@PathVariable("step") String step,@PathVariable("position") String position){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("step",step);
        paramMap.put("position",position);
	    return  tQueueNoService.getOwnQueueList(paramMap);
    }
    /**
     * 从数据库获取延迟队列
     * @param step 当前步骤
     * @param position 当前桌台
     * @return
     */
    @GetMapping("getOwnDelayQueueList/{step}/{position}")
    public List<TQueueNoEntity> getOwnDelayQueueList(@PathVariable("step") String step,@PathVariable("position") String position){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("step",step);
        paramMap.put("position",position);
        return  tQueueNoService.getOwnDelayQueueList(paramMap);
    }

    /**
     * 从数据库获取特定步骤的未处理队列
     * @param step 当前步骤
     * @return
     */
    @GetMapping("getStepQueueList/{step}")
    public List<TQueueNoEntity> getStepQueueList(@PathVariable("step") String step){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("step",step);
        return  tQueueNoService.getStepQueueList(paramMap);
    }

    @GetMapping("getLoginTowers/{towerType}")
    public List<TBaseTowerEntity> getLoginTowers(@PathVariable("towerType") Integer towerType){
        return tQueueNoService.getLoginedTower(towerType);
    }


    /**
     * @method_name: queryListByPlayerAreaId
     * @describe: 大厅视频、留观视频 获取视频列表接口
     * @param [plaerAreaId]
     * @return io.yfjz.utils.R
     * @author 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/9/25  11:30
     **/
    @ResponseBody
    @RequestMapping("/queryListByPlayerAreaId/{plaerAreaId}")
    public R queryListByPlayerAreaId(@PathVariable("plaerAreaId") String plaerAreaId){
        List<HashMap> fileurls = tMvRelevanceService.queryListByPlayerAreaId(plaerAreaId);
        return R.ok().put("fileurls", fileurls);
    }

    /**
     *
     */
    @GetMapping("/video/{topic}/{command}")
    public void videoCtrl(@PathVariable("topic") String topic,@PathVariable("command") String command){
        JSONObject order = new JSONObject();
        order.put("action",command);
        jmsTemplate.convertAndSend(queueConfig.getTopicMap().get(topic),order);
    }

    @GetMapping("/video/notice/{id}")
    public String getNotice(@PathVariable("id") String id){
        return "";
    }

    /**
     * @method_name: 获取正在留观儿童号
     * @describe:
     * @return java.util.List<io.yfjz.entity.queue.TQueueNoEntity>
     * @author 邓召仕
     * @date: 2018-11-17  17:55
     **/
    @GetMapping("/getObserveNo")
    @ResponseBody
    public List<TQueueNoEntity> getObserveNo(){
        return tQueueNoService.getObserveNo();
    }
}
