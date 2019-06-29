package org.wlpiaoyi.utile.websocket.client;

import lombok.Getter;
import lombok.NonNull;
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

    /**
     * 实现服务器异步线程安全主动推送
     * @param message
     * @param uuid
     * @return
     */
    public boolean sendASyncMessage(@NonNull String message, @NonNull String uuid){
        String sendArg = uuid + ":" + message;
        return this.sendASyncMessage(sendArg);
    }

    /**
     * 实现服务器异步线程安全主动推送
     * @param text
     * @return
     */
    public boolean sendASyncMessage(@NonNull String text){
        return this.webSocket.send(text);
    }


    /**
     * 实现客户端同步线程安全主动推送
     * @param message
     * @return
     */
    public String sendSyncMessage(@NonNull String message){
        String uuid = UUIDUtile.getUUID64();
        return this.sendSyncMessage(message, uuid);
    }

    /**
     * 实现客户端同步线程安全主动推送
     * @param message
     * @param uuid
     * @return
     */
    public String sendSyncMessage(@NonNull String message, @NonNull String uuid){
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
        this.webSocket = webSocket;
        this.downLatch.countDown();

        if(this.listener != null){
            try{
                this.listener.get().onOpen(this.client.get());
            }catch (Exception e){e.printStackTrace();}
        }

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
                    if(index > UUIDUtile.UUID64_LENGHT){
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

        if (this.listener != null){
            try{
                if(uuid == null){
                    this.listener.get().onMessage(this, message);
                }else{
                    this.listener.get().onMessage(this, message, uuid.toString());
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        if(this.listener != null){
            try{
                this.listener.get().onClose(this.client.get());
            }catch (Exception e){e.printStackTrace();}
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        if(this.listener != null){
            try{
                this.listener.get().onError(this.client, t.getCause());
            }catch (Exception e){e.printStackTrace();}
        }
    }
}
