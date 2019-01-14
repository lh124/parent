package io.yfjz.controller.websoket;


import io.yfjz.controller.sys.SysConfigurationController;
import io.yfjz.entity.sys.SysConfigurationEntity;
import io.yfjz.service.sys.SysConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author Administrator -- 张羽丰
 * @Description TODO
 * @Date 16:26 2018/10/21
 */
@ServerEndpoint(value = "/configurationWebsoket", configurator = SpringConfigurator.class)
public class ConfigurationWebsoket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<ConfigurationWebsoket> webSocketSet = new CopyOnWriteArraySet<ConfigurationWebsoket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SysConfigurationController.class);

    @Autowired
    private SysConfigurationService sysConfigurationService;


    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     *
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        sendStartUsingMessage(null);
        log.info("当前打开的登记台有：" + getOnlineCount());
    }

    /**
     * 暂停取号设置触发发送消息
     * @param entityTye
     */
    public void sendStartUsingMessage(SysConfigurationEntity entityTye){
        if(entityTye == null){
            entityTye = sysConfigurationService.configuration("pause_number");
        }
        //给打开的登记台发送暂停取号，还是开始取号
        for (ConfigurationWebsoket item : webSocketSet) {
            try {
                item.sendMessage(net.sf.json.JSONObject.fromObject(entityTye).toString());
            } catch (IOException e) {
                log.info("暂停取号群发消息失败！");
                log.error(this,e);
                continue;
            }
        }
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        //群发消息
        for (ConfigurationWebsoket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                log.info("暂停取号群发消息失败！");
                log.error(this,e);
                continue;
            }
        }
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一登记台关闭！当前打开的登记台有：" + getOnlineCount());

    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("同步登记台页面暂停取号功能发生错误");
        log.error(this, error);
        //error.printStackTrace();
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ConfigurationWebsoket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ConfigurationWebsoket.onlineCount--;
    }
}
