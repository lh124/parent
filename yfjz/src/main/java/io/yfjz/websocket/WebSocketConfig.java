package io.yfjz.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;


/**
 * @class_name: WebSocketConfig
 * @describe: websocket 配置类，添加拦截器功能，访问路径
 * @author: 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2018/5/19  22:43
 **/
@Configuration
@EnableWebSocket
@EnableWebMvc
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(websockeHandler(),"/websockeHandler").addInterceptors(new HandshakeInterceptor());
        webSocketHandlerRegistry.addHandler(onlineHandler(),"/onlineWebsockeHandler");
        webSocketHandlerRegistry.addHandler(backup(),"/backupWebSocketHandler");
        webSocketHandlerRegistry.addHandler(websockeHandler(),"/sockjs/websockeHandler").addInterceptors(new HandshakeInterceptor()).withSockJS();
    }
    
    /**
     * @method_name: websockeHandler
     * @describe: 使用注解的方式，将系统处理websocket的连接，断开功能独立AnalysisWebSocketHandler，注入到配置器类中
     * @param:
     * @return: org.springframework.web.socket.WebSocketHandler
     * @author: 刘琪
     * @QQ: 1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/5/19  22:44
     **/
    @Bean
    public WebSocketHandler websockeHandler(){
        return new AnalysisWebSocketHandler();
    }
    @Bean
    public WebSocketHandler backup(){
        return new AnnotationWebSocketHandler();
    }
    /** 
    * @Description: 处理工作台独占问题
    * @Param: [] 
    * @return: org.springframework.web.socket.WebSocketHandler 
    * @Author: 田金海
    * @Email: 895101047@qq.com
    * @Date: 2018/8/28 14:03
    * @Tel  17328595627
    */ 
    @Bean
    public WebSocketHandler onlineHandler(){
        return  new OnlineWebSocketHandler();
    }
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxSessionIdleTimeout(900000);
        container.setAsyncSendTimeout(5000);
        return container;
    }

}
