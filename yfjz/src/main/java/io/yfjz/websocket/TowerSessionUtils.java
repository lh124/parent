package io.yfjz.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 
* @Description: 存放所有工作台信息 
* @Param:
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/8/28 11:25
* @Tel  17328595627
*/ 
public class TowerSessionUtils {
    /*******存放可选工作台信息 ******/
    public static List<String> towers = new ArrayList<>();

    /** 
    * @Description: 把接种台添加到集合
    * @Param: [userId, webSocketSession] 
    * @return: void 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/28 11:27
    * @Tel  17328595627
    */ 
    public static void add(String towerId){
        if (!towers.contains(towerId)) {
            towers.add(towerId);
        }
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
    public static void remove(String towerId){

        towers.remove(towerId);
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
