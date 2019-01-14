package io.yfjz.websocket;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @class_name: AnalysisWebSocketHandler
 * @describe: spring整合websocket 处理消息的尸体类
 * @author: 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2018/5/18  13:45
 **/
public class AnalysisWebSocketHandler extends TextWebSocketHandler {

    /**
     * @method_name: afterConnectionEstablished
     * @describe: websocket连接成功
     * @param:
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/5/18  13:58
     **/
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String userId = MapUtils.getString(session.getAttributes(),"userId");
        WebSocketSessionUtils.add(userId,session);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String userId = MapUtils.getString(session.getAttributes(), "userId");
        TextMessage textMessage = new TextMessage("[" + userId + "]:" + message.getPayload());
        WebSocketSessionUtils.broadcastSendMessageToTarget(userId,textMessage);//批量发送
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        String userId = MapUtils.getString(session.getAttributes(),"userId");
        WebSocketSessionUtils.remove(userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        String userId = MapUtils.getString(session.getAttributes(),"userId");
        WebSocketSessionUtils.remove(userId);
    }
}
