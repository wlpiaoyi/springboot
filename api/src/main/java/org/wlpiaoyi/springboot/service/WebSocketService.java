package org.wlpiaoyi.springboot.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.wlpiaoyi.utile.exception.BusinessException;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 异步WebSocket服务
 */
@Slf4j
@Component
@ServerEndpoint("/test/{sid}")
public class WebSocketService {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int ONLINE_COUNT = 0;

    //concurrent包的线程安全Set,用来存放每个客户端对应的WebSocketServer对象
    private static CopyOnWriteArraySet<WebSocketService> SERVICE_SET = new CopyOnWriteArraySet<WebSocketService>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    @Getter
    private String sid = "";
    /**
     * 建立连接回调
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        WebSocketService.SERVICE_SET.add(this);
        addOnlineCount();
        log.info("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("收到来自窗口"+sid+"的信息:"+message);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendAsynMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 实现服务器主动推送
     * */
    public static void sendAsynMessage(String message, String sid){
        if(sid == null) throw new BusinessException(-1, "WebSocket sid can't be null");
        log.info("推送消息到窗口"+sid+"，推送内容:"+message);
        for (WebSocketService item : SERVICE_SET) {
            try {
                if(item.sid.equals(sid)){
                    item.sendAsynMessage(message);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    /**
     * 实现服务器主动推送
     * */
    public static void sendAsynMessage(String message, List<String> sids){
        log.info("推送消息到" + ((sids == null || sids.size() == 0) ? "所有" : (sids.size() + "个") ) +"窗口，推送内容:"+message);
        List<String> copySids = new ArrayList<String>(sids);
        for (WebSocketService item : SERVICE_SET) {
            try {
                if(copySids.contains(item.sid)){
                    copySids.remove(item.sid);
                    item.sendAsynMessage(message);
                }
                if(copySids.size() == 0) break;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 关闭连接回调
     */
    @OnClose
    public void onClose() {
        WebSocketService.SERVICE_SET.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 异常连接回调
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        if(session.isOpen()){
            try{session.close();} catch (Exception e){e.printStackTrace();}
        }
        error.printStackTrace();

    }


    public static synchronized int getOnlineCount() {
        return ONLINE_COUNT;
    }

    static synchronized void addOnlineCount() {
        WebSocketService.ONLINE_COUNT++;
    }

    static synchronized void subOnlineCount() {
        WebSocketService.ONLINE_COUNT--;
    }
}
