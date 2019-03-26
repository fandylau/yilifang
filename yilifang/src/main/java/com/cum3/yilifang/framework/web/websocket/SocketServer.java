package com.cum3.yilifang.framework.web.websocket;

import java.util.Collection;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONUtils;

/**
 * 
 * 描述
 * 
 * @author Fandy Liu
 * @created 2018年11月12日 下午2:49:41
 */
@Component
@ServerEndpoint("/websocket")
public class SocketServer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 会话
     */
    private Session session;
    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        logger.info("现在来连接的客户id：" + session.getId());
        this.session = session;
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("服务端发生了错误" + error.getMessage());
     
    }
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        SocketServerPool.removeUser(this);// 在连接池中移除连接
        Collection<String> onlineUsers = SocketServerPool.getOnlineUser();
        // 通知所有用户更新在线信息
        SocketServerPool.sendMessage(Constant.ONLINEALLSTATUS + JSONUtils.toJSONString(onlineUsers));
    }
    /**
     * 收到客户端的消息
     *
     * @param message
     *            消息
     * @param session
     *            会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        
    }
    /**
     * 发送消息
     * @author Fandy Liu
     * @created 2018年11月12日 下午4:34:53
     * @param message
     */
    public void send(String message){
        this.session.getAsyncRemote().sendText(message);
    }
}
