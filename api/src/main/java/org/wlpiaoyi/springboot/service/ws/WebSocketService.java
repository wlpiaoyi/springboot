package org.wlpiaoyi.springboot.service.ws;

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
    /**
     * 建立连接回调
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("scheme") String scheme) {
        this.scheme = scheme;
        super.onWsOpen(session);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        super.onWsMessage(message);
        try {
            this.sendAsyncMessage(message + "---");
        } catch (IOException e) {
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
