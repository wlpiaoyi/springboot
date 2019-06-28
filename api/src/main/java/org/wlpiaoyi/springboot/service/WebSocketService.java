package org.wlpiaoyi.springboot.service;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.wlpiaoyi.utile.UUIDUtile;
import org.wlpiaoyi.utile.websocket.WebSocketListener;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 异步WebSocket服务
 */
@Slf4j
@Component
@ServerEndpoint("/test/{sid}")
public class WebSocketService {

    public static int TIME_OUT_SECONDS = 500000;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int ONLINE_COUNT = 0;

    //concurrent包的线程安全Set,用来存放每个客户端对应的WebSocketServer对象
    private static CopyOnWriteArraySet<WebSocketService> SERVICE_SET = new CopyOnWriteArraySet<WebSocketService>();

    private static WeakReference<WebSocketListener> wsListener;

    private Map<String, Map<String, Object>> resultMap = new HashMap<>();

    private Object lock = new Object();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    @Getter
    private Session session;
    //接收sid
    @Getter
    private String sid = "";

    /**
     * 实现服务器主动推送
     * @param message
     * @param uuid
     * @return
     */
    public String sendSyncMessage(String message, String uuid){

        if(uuid == null) uuid = UUIDUtile.getUUID();

        String sendArg = uuid + ":" + message;
        CountDownLatch downLatch = new CountDownLatch(1);
        Map<String, Object> data;
        try {
            data = new HashMap<String, Object>(){{
                put("downLatch", downLatch);
            }};
            this.sendASyncMessage(sendArg);
            resultMap.put(uuid, data);
            if(!downLatch.await(TIME_OUT_SECONDS, TimeUnit.MILLISECONDS)){
                data = null;
            }
        } catch (Exception e) {
            data = null;
            e.printStackTrace();
        } finally {
            synchronized (lock){
                resultMap.remove(uuid);
            }
        }

        if(data == null) return null;
        String result = (String)data.get("result");
        return result;
    }


    /**
     * 实现服务器主动推送
     * @param message
     * @param uuid
     * @return
     * @throws IOException
     */
    public String sendASyncMessage(String message, String uuid) throws IOException {
        if(uuid == null) uuid = UUIDUtile.getUUID();
        String sendArg = uuid + ":" + message;
        this.sendASyncMessage(sendArg);
        return uuid;
    }

    /**
     * 实现服务器主动推送
     * @param message
     * @throws IOException
     */
    public void sendASyncMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 建立连接回调
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        log.info("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
        this.session = session;
        this.sid = sid;
        WebSocketService.SERVICE_SET.add(this);
        addOnlineCount();
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("收到来自窗口"+sid+"的信息:"+message);
        StringBuilder uuid = new StringBuilder();
        try{
            int index = 0;
            try{
                for (char c : message.toCharArray()) {
                    if(c == ':')break;
                    uuid.append(c);
                    index ++;
                    if(index > 36){
                        index = -1;
                        break;
                    }
                }
            }catch (Exception e){e.printStackTrace();}
            if(index == -1){
                uuid = null;
            }else{
                Map<String, Object> data;
                synchronized (lock){
                    data = resultMap.get(uuid.toString());
                }
                String result = message.substring(index + 1);
                message = result;
                if(data != null){
                    data.put("result", result);
                    CountDownLatch downLatch = (CountDownLatch) data.get("downLatch");
                    if(downLatch != null){downLatch.countDown();}
                }
            }
        }catch (Exception e){e.printStackTrace();}
        if(uuid == null){
            try{
                if(WebSocketService.wsListener != null){
                    WebSocketService.wsListener.get().onMessage(this, message);
                }
            }catch (Exception e){e.printStackTrace();}
        }else{
            try{
                if(WebSocketService.wsListener != null){
                    WebSocketService.wsListener.get().onMessage(this, message, uuid.toString());
                }
            }catch (Exception e){e.printStackTrace();}
        }

    }

    /**
     * 关闭连接回调
     */
    @OnClose
    public void onClose() {
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        WebSocketService.SERVICE_SET.remove(this);  //从set中删除
        subOnlineCount();//在线数减1
        if(WebSocketService.wsListener != null){
            WebSocketService.wsListener.get().onClose(this);
        }
    }

    /**
     * 异常连接回调
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
        if(session.isOpen()){
            try{session.close();} catch (Exception e){e.printStackTrace();}
        }
        if(WebSocketService.wsListener != null){
            WebSocketService.wsListener.get().onError(this, error);
        }

    }

    public static void setWsListener(WebSocketListener wsListener){
        WebSocketService.wsListener = new WeakReference<>(wsListener);
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
