package org.wlpiaoyi.utile.websocket.client;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.wlpiaoyi.utile.websocket.WebSocketListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebSocketClient {

    public static int TIME_OUT_SECONDS = 500000;

    private WebSocketEcho socketListener;

    private CountDownLatch downLatch = new CountDownLatch(1);

    @Getter
    private String url;

    public WebSocketClient(String url, WebSocketListener listener){
        this.url = url;
        this.socketListener = new WebSocketEcho(TIME_OUT_SECONDS, this, listener, downLatch);
    }

    //"ws://echo.websocket.org"
    public void asyncOpen() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT_SECONDS, TimeUnit.MILLISECONDS)//设置读取超时时间
                .writeTimeout(TIME_OUT_SECONDS, TimeUnit.MILLISECONDS)//设置写的超时时间
                .connectTimeout(TIME_OUT_SECONDS, TimeUnit.MILLISECONDS)//设置连接超时时间
                .build();
        Request request = new Request.Builder().url(this.url).build();
        client.newWebSocket(request, this.socketListener);
    }
    public boolean syncOpen() {
        this.asyncOpen();
        try{
            if(!downLatch.await(TIME_OUT_SECONDS, TimeUnit.MILLISECONDS)){
                this.close();
                return false;
            }
        }catch (Exception e){
            this.close();
            return false;
        }
        return true;
    }


    public String sendSyncMessage(String message, String uuid){
        return this.socketListener.sendSyncMessage(message, uuid);
    }

    public String sendSyncMessage(String message){
        return this.sendSyncMessage(message, null);
    }

    public String sendASyncMessage(String message, String uuid){
        return this.socketListener.sendASyncMessage(message, uuid);
    }

    public boolean sendASyncMessage(String message){
        return this.socketListener.sendASyncMessage(message);
    }

    public void close(){
        this.socketListener.close();
    }

}
