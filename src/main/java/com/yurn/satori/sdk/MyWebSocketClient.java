package com.yurn.satori.sdk;

import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.ConnectionEntity;
import com.yurn.satori.sdk.entity.EventEntity;
import com.yurn.satori.sdk.entity.PropertiesEntity;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
class MyWebSocketClient extends WebSocketListener {
    private final PropertiesEntity properties;
    private ScheduledFuture<?> heart;
    private ScheduledFuture<?> reconnect;
    private Integer sequence;

    public MyWebSocketClient(PropertiesEntity properties) {
        this.properties = properties;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        log.info("建立WS连接: {}", response);
        sendIdentify(webSocket);
        if (reconnect != null && !reconnect.isCancelled() && !reconnect.isDone()) {
            reconnect.cancel(true);
        }
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        ConnectionEntity connection = ConnectionEntity.parse(text);
        switch (connection.getOp()) {
            case ConnectionEntity.EVENT -> {
                log.info("收到事件: {}", connection.getBody());
                EventEntity body = (EventEntity) connection.getBody();
                sequence = body.getId();
                GlobalEventChannel.getINSTANCE().runEvent(body);
            }
            case ConnectionEntity.PONG -> {}
            case ConnectionEntity.READY -> {
                ConnectionEntity.Ready ready = (ConnectionEntity.Ready) connection.getBody();
                log.info("成功建立连接: {}", Arrays.toString(ready.getLogins()));
                BotContainer.getINSTANCE().setLogins(ready.getLogins());

                GlobalEventChannel.getINSTANCE().runConnect(ready);

                // 心跳
                ConnectionEntity sendConnection = new ConnectionEntity();
                sendConnection.setOp(ConnectionEntity.PING);
                heart = new ScheduledThreadPoolExecutor(1, r -> new Thread(r)).scheduleAtFixedRate(
                        () -> webSocket.send(JSONObject.toJSONString(sendConnection)), 10, 10, TimeUnit.SECONDS);
            }
            default -> log.error("Unsupported {}", connection);
        }
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        log.info("断开连接, code: {}, reason: {}", code, reason);
        BotContainer.getINSTANCE().setLogins(null);
        if (heart != null && !heart.isCancelled() && !heart.isDone()) {
            heart.cancel(true);
        }

        GlobalEventChannel.getINSTANCE().runDisconnect(reason);

        // TODO: 断线重练
        /*reconnect = new ScheduledThreadPoolExecutor(1, r -> new Thread(r))
                .scheduleAtFixedRate(() -> {
                    log.info("尝试重新连接");
                    webSocket.();
                }, 3, 3, TimeUnit.SECONDS);*/
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        log.error("出现错误: {}, response: {}", t, response);
        GlobalEventChannel.getINSTANCE().runError(t);
    }

    private void sendIdentify(WebSocket webSocket) {
        ConnectionEntity connection = new ConnectionEntity();
        connection.setOp(ConnectionEntity.IDENTIFY);
        if (properties.getToken() != null || sequence != null) {
            ConnectionEntity.Identify body = new ConnectionEntity.Identify();
            connection.setBody(body);
            if (properties.getToken() != null) {
                body.setToken(properties.getToken());
            }
            if (sequence != null) {
                body.setSequence(sequence);
            }
        }
        webSocket.send(JSONObject.toJSONString(connection));
    }
}
