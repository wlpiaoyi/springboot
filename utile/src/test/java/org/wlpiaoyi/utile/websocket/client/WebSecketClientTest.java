package org.wlpiaoyi.utile.websocket.client;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wlpiaoyi.utile.websocket.WebSocketListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSecketClientTest implements WebSocketListener {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void test() throws IOException {
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
        System.out.println("=============>:"+ message);
        String result =  wsClient.sendSyncMessage(message);
        System.out.println("<=============:"+ result);

        while (true){
            dataMap = gson.fromJson(result, Map.class);
            dataMap.put("type", "send-receive");
            dataMap.put("count", count ++);
            message =  gson.toJson(dataMap);
            System.out.println("=============>:"+ message);
            result =  wsClient.sendSyncMessage(message);
            System.out.println("<=============:"+ result);
            if(count > 5) break;
        }

        wsClient.close();

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
