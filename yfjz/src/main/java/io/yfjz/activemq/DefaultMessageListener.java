package io.yfjz.activemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.yfjz.entity.queue.*;
import io.yfjz.service.basesetting.TBaseTowerService;
import io.yfjz.service.inocpointmgr.InoculationManagerService;
import io.yfjz.service.inocpointmgr.TBaseGetnumsService;
import io.yfjz.service.queue.TQueueNoOperateService;
import io.yfjz.service.queue.TQueueNoService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-22 23:59:55
 */

public class DefaultMessageListener implements MessageListener {
    @Autowired
    TQueueNoOperateService tQueueNoOperateService;
    @Autowired
    TQueueNoService tQueueNoService;
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    QueueConfig queueConfig;
    @Autowired
    InoculationManagerService inoculationManagerService;
    @Autowired
    private TBaseTowerService tBaseTowerService;
    //接种点设置
    @Autowired
    TBaseGetnumsService tBaseGetnumsService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof BytesMessage) {
            byte[] b = new byte[1024];
            int len = -1;
            BytesMessage bm = (BytesMessage) message;
            String stringMessage="";
            try{
                while ((len = bm.readBytes(b)) != -1) {
                    stringMessage += new String(b, 0, len,"UTF-8");
                }
                processMessage(stringMessage);
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }



    /**
     * 消息处理
     * @param messageStr
     */
    private void processMessage(String messageStr){
        TQueueNoEntity queueNoEntity  = JSON.parseObject(messageStr,TQueueNoEntity.class);
        //插入操作记录
        if(queueNoEntity.getNoText()!= null){
            insertQueueOperate(queueNoEntity);
        }


        //重置队列状态
        switch(queueNoEntity.getAction()){
            case CALL:
                queueNoEntity.setStatus(0);
                tQueueNoService.update(queueNoEntity);
                break;
            case FINISH:
                StepType nextStep = inoculationManagerService.getNextProcess(StepType.valueOf(queueNoEntity.getStep()).ordinal());
//                System.out.println("=========="+nextStep.toString());
                queueNoEntity.setPosition(null);
                //下一步骤为接种，则获取登记的将接种的疫苗
                if(nextStep.equals(StepType.inoculate)){
                    List<String> vaccineList = tQueueNoService.getRegisterVaccine(queueNoEntity.getChildCode());
                    queueNoEntity.setVaccine(StringUtils.join(vaccineList,","));
                    //分配到指定接种台:邓召仕添加
                    try{
                        //邓：判断是否启用多队列
                        R rerult = tBaseGetnumsService.getSysConType(Constant.QUEUE_DISTINCTION,0);
                        if (rerult != null && ((Integer)rerult.get("typeValue")).intValue() ==1) {
                            List<String> vaccineIds = tQueueNoService.getRegisterVaccineIds(queueNoEntity.getChildCode());
                            R map = tBaseTowerService.synergicPosition(vaccineIds);
                            if ((int)map.get("code") == 1){
                                queueNoEntity.setPosition((String) map.get("position"));
                            }
                        }
                        //邓：ting
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                queueNoEntity.setStep(nextStep.toString());
               // queueNoEntity.setPosition(null);
                tQueueNoService.update(queueNoEntity);
                queueNoEntity.setAction(QueueAction.CREATE);
                jmsTemplate.convertAndSend(queueConfig.getTopicMap().get(nextStep.toString()),queueNoEntity);
                break;
            case CANCEL:
                queueNoEntity.setStatus(-1);
                tQueueNoService.update(queueNoEntity);
                break;
            case CREATE:
                break;
            case BACK:
                if("inoculate".equals(queueNoEntity.getStep())) {
                    queueNoEntity.setStep("register");
                    queueNoEntity.setPosition(null);
                    //queueNoEntity.setStatus(-2);
                    queueNoEntity.setDest("");
                    tQueueNoService.update(queueNoEntity);
                    jmsTemplate.convertAndSend(queueConfig.getTopicMap().get("register"),queueNoEntity);
                }
                break;
            case DELAY:
                queueNoEntity.setStatus(1);
                tQueueNoService.update(queueNoEntity);
                break;
            case TRANSFER:
                queueNoEntity.setPosition(queueNoEntity.getDest());
                tQueueNoService.update(queueNoEntity);
                break;
            default:
                break;

        }
    }

    /**
     * 记录队列操作
     */
    private void insertQueueOperate(@NotNull TQueueNoEntity queueNoEntity){
            TQueueNoOperateEntity operate = new TQueueNoOperateEntity();
            operate.setFkQueueNoId(queueNoEntity.getId());
            operate.setOperateTime(new Date());
            operate.setOperator(queueNoEntity.getOperator());
            operate.setTower(queueNoEntity.getPosition());
            operate.setOperateType(queueNoEntity.getAction().name());
            operate.setRemark(queueNoEntity.getRemark());
            tQueueNoOperateService.save(operate);
    }
}
