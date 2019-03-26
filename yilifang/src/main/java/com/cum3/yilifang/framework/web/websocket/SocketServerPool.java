/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.cum3.yilifang.framework.web.websocket;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.WebSocket;

/**
 * 描述 WebSocket 连接池维护
 * @author Fandy Liu
 * @created 2018年11月12日 下午5:09:02
 */
public class SocketServerPool {
    /**
     * 用户连接池
     */
    private static final Map<SocketServer, String> USERCONNECTIONS = new HashMap<SocketServer, String>();

    /**
     * 获取所有的在线用户
     * 
     * @author Fandy Liu
     * @created 2017年4月7日 下午6:37:30
     * @return
     */
    public static Collection<String> getOnlineUser() {
        Collection<String> setUsers = USERCONNECTIONS.values();
        return setUsers;
    }

    /**
     * 根据连接获取用户名
     * @param conn
     * @return
     */
    public static String getUserByKey(WebSocket conn) {
        return USERCONNECTIONS.get(conn);
    }

    /**
     * 描述 判断用户是否在线
     * @param userId
     * @return
     */
    public static boolean isUserOnline(String userId) {
        Collection<String> onlineUsers = SocketServerPool.getOnlineUser();
        return onlineUsers.contains(userId);

    }

    /**
     * 获取WebSocket
     * @param user
     * @return
     */
    public static SocketServer getWebSocketByUser(String user) {
        Set<SocketServer> keySet = USERCONNECTIONS.keySet();
        synchronized (keySet) {
            for (SocketServer conn : keySet) {
                String cuser = USERCONNECTIONS.get(conn);
                if (cuser.equals(user)) {
                    return conn;
                }
            }
        }
        return null;
    }

    /**
     * 向连接池中添加连接
     * @param user
     * @param conn
     */
    public static void addUser(String user, SocketServer conn) {
        USERCONNECTIONS.put(conn, user); // 添加连接
    }

    /**
     * 移除连接池中的连接
     * @param conn
     * @return
     */
    public static boolean removeUser(SocketServer conn) {
        if (USERCONNECTIONS.containsKey(conn)) {
            USERCONNECTIONS.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向特定的用户发送数据
     * @param conn
     * @param message
     */
    public static void sendMessageToUser(SocketServer conn, String message) {
        if (null != conn && null != USERCONNECTIONS.get(conn)) {
            conn.send(message);
        }
    }

    /**
     * 向所有的用户发送消息
     * @param message
     */
    public static void sendMessage(String message) {
        Set<SocketServer> keySet = USERCONNECTIONS.keySet();
        synchronized (keySet) {
            for (SocketServer conn : keySet) {
                String user = USERCONNECTIONS.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }
}
