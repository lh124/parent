package io.yfjz.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @class_name: WebSocketSessionUtils
 * @describe: 保存WebSocketSession对象；发送消息
 * @author: 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2018/5/18  13:56
 **/
public class WebSocketSessionUtils {
    /*******存放所有连接上来的客户端WebSocketSession对象的hashmap******/
    private static Map<String,WebSocketSession> clients = new ConcurrentHashMap<>();

    /**
     * @method_name: add
     * @describe: 添加客户useriId 对应的WebSocketSession到集合中
     *  注：先删除userID对应的WebSocketSession ，然后再保存新的WebSocketSession到集合中
     * @param:
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/5/18  22:18
     **/
    public static void add(String userId,WebSocketSession webSocketSession){
        if (clients.containsKey(userId)){
            clients.remove(userId);
        }
        clients.put(userId, webSocketSession);
    }

    /**
     * @method_name: remove
     * @describe: 移除指定userId的WebSocketSession连接
     * @param:
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/5/18  22:19
     **/
    public static void remove(String userId){
        clients.remove(userId);
    }

    /**
     * @method_name: broadcastSendMessageToTarget
     * @describe: 以广播的方式发布消息
     * 注：所有在线的用户都会接收到其中某个用户发送的消息
     * @param:
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/5/18  22:20
     **/
    public static void broadcastSendMessageToTarget(String userId, TextMessage message){
        for (String key:clients.keySet()){
            if (key.equals(userId)) continue;
            sendMessageToTarget(key,message);
        }
    }
    /**
     * @method_name: sendMessageToTarget
     * @describe: 给指定的用户发送消息
     * @param:
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/5/18  22:21
     **/
    public static void sendMessageToTarget(String userId,TextMessage message){
        WebSocketSession webSocketSession = clients.get(userId);
        sendMessage(message, webSocketSession);
    }


    /**
     * @method_name: sendMessage
     * @describe:  调用webSocketSession发送消息
     * 注：类的内部方法，不对外提供访问
     * @param:
     * @return: void
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/5/18  22:22
     **/
    private static void sendMessage(TextMessage message, WebSocketSession webSocketSession) {
        if (webSocketSession != null){
            if (webSocketSession.isOpen()){
                try {
                    webSocketSession.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
