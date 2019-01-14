package io.yfjz.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @class_name: HandshakeInterceptor
 * @describe: spring整合websocket时，需要添加拦截器，保存登录用的基本信息
 * @author: 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2018/5/18  13:45
 **/
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
        String userId = serverRequest.getServletRequest().getParameter("userId");
        attributes.put("userId", userId);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
