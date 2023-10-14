package com.yurn.satori.sdk;

import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.ConnectionEntity;
import com.yurn.satori.sdk.entity.EventEntity;
import com.yurn.satori.sdk.property.YurnSdkProperties;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.util.NamedThreadFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
class MyWebSocketClient extends WebSocketClient {
    private final YurnSdkProperties yurnSdkProperties;
    private ScheduledFuture<?> heart;
    private ScheduledFuture<?> reconnect;
    private Integer sequence;

    public MyWebSocketClient(YurnSdkProperties yurnSdkProperties) throws URISyntaxException {
        super(new URI("ws://" + yurnSdkProperties.getAddress() + "/v1/events"), new Draft_6455());
        this.yurnSdkProperties = yurnSdkProperties;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("建立WS连接: {}", serverHandshake);
        sendIdentify();
        if (reconnect != null && !reconnect.isCancelled() && !reconnect.isDone()) {
            reconnect.cancel(true);
        }
    }

    @Override
    public void onMessage(String msg) {
        ConnectionEntity connection = ConnectionEntity.parse(msg);
        switch (connection.getOp()) {
            case ConnectionEntity.EVENT -> {
                log.info("收到事件: {}", connection.getBody());
                EventEntity body = (EventEntity) connection.getBody();
                sequence = body.getId();
                GlobalEventChannel.INSTANCE.runEvent(body);
            }
            case ConnectionEntity.PONG -> {}
            case ConnectionEntity.READY -> {
                ConnectionEntity.Ready ready = (ConnectionEntity.Ready) connection.getBody();
                log.info("成功建立连接: {}", Arrays.toString(ready.getLogins()));
                BotContainer.setLogins(ready.getLogins());

                GlobalEventChannel.INSTANCE.runConnect(ready);

                // 心跳
                ConnectionEntity sendConnection = new ConnectionEntity();
                sendConnection.setOp(ConnectionEntity.PING);
                heart = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("Heart")).scheduleAtFixedRate(
                        () -> send(JSONObject.toJSONString(sendConnection)), 10, 10, TimeUnit.SECONDS);
            }
            default -> log.error("Unsupported {}", connection);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("断开连接, i: {}, s: {}, b: {}", i, s, b);
        BotContainer.setLogins(null);
        if (heart != null && !heart.isCancelled() && !heart.isDone()) {
            heart.cancel(true);
        }

        GlobalEventChannel.INSTANCE.runDisconnect(s);

        // 断线重练
        reconnect = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("Reconnect"))
                .scheduleAtFixedRate(() -> {
                    log.info("尝试重新连接");
                    this.connect();
                }, 3, 3, TimeUnit.SECONDS);
    }

    @Override
    public void onError(Exception e) {
        log.error("出现错误: {}", e.toString());
        GlobalEventChannel.INSTANCE.runError(e);
    }

    private void sendIdentify() {
        ConnectionEntity connection = new ConnectionEntity();
        connection.setOp(ConnectionEntity.IDENTIFY);
        if (yurnSdkProperties.getToken() != null || sequence != null) {
            ConnectionEntity.Identify body = new ConnectionEntity.Identify();
            connection.setBody(body);
            if (yurnSdkProperties.getToken() != null) {
                body.setToken(yurnSdkProperties.getToken());
            }
            if (sequence != null) {
                body.setSequence(sequence);
            }
        }
        this.send(JSONObject.toJSONString(connection));
    }
}
