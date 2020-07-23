package org.wlpiaoyi.springboot.service.ws;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.wlpiaoyi.framework.utils.websocket.service.WsBootService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint("/test/{scheme}")
public class WebSocketService extends WsBootService {

    @Getter
    private String scheme = "";

    @Data
    private static class WSData{
        private int code;
        private String message;
    }

    /**
     * 建立连接回调
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("scheme") String scheme) {
        this.scheme = scheme;
        super.onWsOpen(session);
        try {
            this.sendMessage("ping");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            if(message.startsWith("test:")){
                WSData data = this.onWsMessage("test:",message, WSData.class);
                data.code += 1;
                data.message = System.currentTimeMillis() + "";
                this.sendMessage("test:", data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接回调
     */
    @OnClose
    public void onClose() {
        super.onWsClose();
    }

    /**
     * 异常连接回调
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        super.onWsError(session, error);
    }

}
