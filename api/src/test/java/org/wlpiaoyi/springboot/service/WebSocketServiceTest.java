package org.wlpiaoyi.springboot.service;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;;
import org.wlpiaoyi.springboot.service.ws.WebSocketService;
import org.wlpiaoyi.framework.utils.websocket.WebSocketListener;

import java.io.IOException;
import java.util.Map;

public class WebSocketServiceTest implements WebSocketListener {

    class WsRunnable implements Runnable{
        private WebSocketService wsService;
        private Map<String, Object> mapData;
        private String uuid;
        WsRunnable(WebSocketService wsService, Map<String, Object> mapData, String uuid){
            this.wsService = wsService;
            this.mapData = mapData;
            this.uuid = uuid;
        }
        @Override
        public void run() {
            String message = new Gson().toJson(mapData);
            System.out.println("sendSyncMessage 发送来自窗口" + wsService.getScheme() + "的返回数据 uuid:" + uuid + " message:" + message);
            String result =  wsService.sendSyncMessage(uuid, message);
            System.out.println("sendSyncMessage 收到来自窗口" + wsService.getScheme() + "的返回数据 uuid:" + uuid + " result:" + result);
        }
    }

    @Before
    public void setUp() throws Exception {
//        WebSocketService.setWsListener(this);
    }

    @Test
    public void test() {
//        String[] args = new String[]{};
//        SpringApplication.run(ApplicationLoader.class, args);
//        while (true){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Override
    public void onOpen(Object target) {
        WebSocketService wsService = (WebSocketService) target;
        System.out.println("有新窗口开始监听:"+wsService.getScheme()+",当前在线人数为" + WebSocketService.getOnlineCount());

    }
    @Override
    public void onMessage(Object target, String message, String uuid) {
        WebSocketService wsService = (WebSocketService) target;
        System.out.println("onMessage 收到来自窗口" + wsService.getScheme() + "的信息 uuid:" + uuid + " message:" + message);
        Map<String, Object> dataMap = new Gson().fromJson(message, Map.class);
        String type = (String) dataMap.get("type");
        if (type.equals("send-receive")) {
            WsRunnable runnable = new WsRunnable(wsService, dataMap, uuid);
            new Thread(runnable).start();
        } else if (type.equals("send-close")) {
            try {
                wsService.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("onMessage 关闭窗口:" + wsService.getScheme() + " uuid:" + uuid);
        } else if (type.equals("send-exit")) {
            System.exit(0);
        }
    }

    @Override
    public void onMessage(Object target, String message) {
        WebSocketService wsService = (WebSocketService) target;
        System.out.println("收到来自窗口" + wsService.getScheme() + "的信息 message:" + message);
    }

    @Override
    public void onClose(Object target) {
        System.out.println("有一连接关闭！当前在线人数为" + WebSocketService.getOnlineCount());
    }

    @Override
    public void onError(Object target, Throwable error) {
    }
}
