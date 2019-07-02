package org.wlpiaoyi.utile.websocket.client;


import com.google.gson.Gson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wlpiaoyi.utile.websocket.WebSocketListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSecketClientTest implements WebSocketListener, Runnable {

    private int count = 20;
    private int count2 = 0;

    @Before
    public void setUp() throws Exception {}

    @Test
    public void test() throws IOException {

        int count = this.count;
        while (count -- > 0){
            new Thread(this).start();
        }
        while (this.count > 0){
            try{
                Thread.sleep(10);
            }catch (Exception e){}
        }

    }

    @Override
    public void run() {

        String obj1 = null;
        synchronized (WebSecketClientTest.class){
            obj1 = "uuid"+this.count2;
            this.count2 ++;
        }
        WebSocketClient wsClient = new WebSocketClient("ws://127.0.0.1:8001/wlpiaoyi/test/sid01", this);
        if(!wsClient.syncOpen()){
            System.out.println("ws open faild");
            return;
        }
        int count = 0;
        Gson gson = new Gson();
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("type", "send-receive");
        dataMap.put("count", count ++);
        String message =  gson.toJson(dataMap);
        String obj2 =  wsClient.sendSyncMessage(obj1, message);
        String result[] = new String[]{obj1, obj2};
        String uuid = result[0];
        dataMap = gson.fromJson(result[1], Map.class);
        dataMap.put("type", "send-receive");
        dataMap.put("count", count ++);
        dataMap.put("uuid", result[0]);
        while (true){
            try{
                message =  gson.toJson(dataMap);
                message =  wsClient.sendSyncMessage(((String)dataMap.get("uuid")), message);
                dataMap = gson.fromJson(message, Map.class);
                dataMap.put("type", "send-receive");
                dataMap.put("count", count ++);
                Assert.assertFalse("org-uuid:" + uuid + " ret-uuid:" + ((String)dataMap.get("uuid")),
                        !((String)dataMap.get("uuid")).equals(uuid));
                Thread.sleep(10);
            }catch (Exception e){}
            if(count > 4) break;

        }
        dataMap.put("type", "send-close");
        dataMap.put("count", count ++);
        message =  gson.toJson(dataMap);
        wsClient.sendASyncMessage(uuid, message);
        synchronized (WebSecketClientTest.class){
            this.count--;
        }
        System.out.println(this.count + "<============uuid:"+ uuid);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Override
    public void onOpen(Object target) {

    }

    @Override
    public void onMessage(Object target, String message, String uuid) {

    }

    @Override
    public void onMessage(Object target, String message) {

    }

    @Override
    public void onClose(Object target) {

    }

    @Override
    public void onError(Object target, Throwable error) {

    }
}
