package org.wlpiaoyi.utile.websocket.client;

import lombok.Getter;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;
import org.wlpiaoyi.utile.UUIDUtile;
import org.wlpiaoyi.utile.websocket.WebSocketListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

final class WebSocketEcho extends okhttp3.WebSocketListener {

    private Map<String,Map<String, Object>> resultMap = new HashMap<>();

    private Object lock = new Object();

    @Getter
    private WebSocket webSocket;

    private int timeout;

    private WeakReference<WebSocketListener> listener;

    private WeakReference<WebSocketClient> client;

    private CountDownLatch downLatch;

    WebSocketEcho(int timeout, WebSocketClient client, WebSocketListener listener, CountDownLatch downLatch){
        this.timeout = timeout;
        this.client = new WeakReference<>(client);
        this.downLatch = downLatch;
        if(listener != null) this.listener = new WeakReference<>(listener);
    }


    public String sendASyncMessage(String message, String uuid){
        if(uuid == null) uuid = UUIDUtile.getUUID();
        String sendArg = uuid + ":" + message;
        if(this.sendASyncMessage(sendArg)){return uuid;}
        else {return null;}
    }

    public boolean sendASyncMessage(String text){
        return this.webSocket.send(text);
    }

    public String sendSyncMessage(String message, String uuid){
        if(uuid == null){
            uuid = UUIDUtile.getUUID();
        }
        String sendArg = uuid + ":" + message;
        CountDownLatch downLatch = new CountDownLatch(1);
        Map<String, Object> data = null;
        try {
            data = new HashMap<String, Object>(){{
                put("downLatch", downLatch);
            }};
            if(!this.sendASyncMessage(sendArg)){
                return null;
            }
            resultMap.put(uuid, data);
            downLatch.await(this.timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
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

    public void close(){
        if(this.webSocket == null) return;
        this.webSocket.cancel();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        if(this.listener != null){
            this.listener.get().onOpen(this.client.get());
        }
        this.webSocket = webSocket;
        this.downLatch.countDown();
        System.out.println("连接成功！");

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        super.onMessage(webSocket, message);
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
                if(this.listener != null){
                    this.listener.get().onMessage(this, message);
                }
            }catch (Exception e){e.printStackTrace();}
        }else{
            try{
                if(this.listener != null){
                    this.listener.get().onMessage(this, message, uuid.toString());
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        if(this.listener != null){
            this.listener.get().onClose(this.client.get());
        }
        System.out.println("closed:" + reason);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        System.out.println("closing:" + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        if(this.listener != null){
            this.listener.get().onError(this.client, t.getCause());
        }
        System.out.println("failure:" + t.getMessage());
    }
}
