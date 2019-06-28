package org.wlpiaoyi.utile.websocket.client;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wlpiaoyi.utile.websocket.WebSocketListener;

import java.io.IOException;

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
        String message =  wsClient.sendSyncMessage("test");
        System.out.println(message);
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
