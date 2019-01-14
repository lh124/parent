package io.yfjz.websocket;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.utils.CommonUtils;
import io.yfjz.utils.ShiroUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.Console;
import java.net.URI;
import java.util.Map;

/** 
* @Description: 处理在线工作台 
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/28 11:39
* @Tel  17328595627
*/ 
public class OnlineWebSocketHandler extends TextWebSocketHandler {

    /**
    * @Description:链接成功触发
    * @Param: [session]
    * @return: void
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/28 11:39
    * @Tel  17328595627
    */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String uri = session.getUri().toString();
        int towerIdIndex = uri.lastIndexOf("=");
        String towerId = uri.substring(towerIdIndex + 1);
        String[] towers = towerId.split(",");
        for (String tower : towers) {
            TowerSessionUtils.remove(tower);
        }

//        System.out.println(TowerSessionUtils.towers);
    }
   
    /** 
    * @Description: 发生错误触发 
    * @Param: [session, exception] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/28 11:40
    * @Tel  17328595627
    */ 
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        String uri = session.getUri().toString();
        int towerIdIndex = uri.lastIndexOf("=");
        String towerId = uri.substring(towerIdIndex + 1);
        String[] towers = towerId.split(",");
        for (String tower : towers) {
            TowerSessionUtils.add(tower);
        }
//        System.out.println(TowerSessionUtils.towers);
    }
    /** 
    * @Description: 关闭连接触发 
    * @Param: [session, status] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/28 11:40
    * @Tel  17328595627
    */ 
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        String uri = session.getUri().toString();
        int towerIdIndex = uri.lastIndexOf("=");
        String towerId = uri.substring(towerIdIndex + 1);
        String[] towers = towerId.split(",");
        for (String tower : towers) {
            TowerSessionUtils.add(tower);
        }
//        System.out.println(TowerSessionUtils.towers);
    }
    /** 
    * @Description: 接受心跳 
    * @Param: [session, message] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/10/23 23:33
    * @Tel  17328595627
    */ 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String type = message.getPayload();
//        System.out.println(type);
        TextMessage textMessage = new TextMessage("[" + type + "]:" + message.getPayload());
        session.sendMessage(textMessage);
//        WebSocketSessionUtils.broadcastSendMessageToTarget(type,textMessage);//批量发送
    }
}
