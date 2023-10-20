/*
Copyright (c) 2023 Yurn
YurnSatoriSdk is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

package com.yurn.satori.sdk;

import com.alibaba.fastjson2.JSONObject;
import com.yurn.satori.sdk.entity.ConnectionEntity;
import com.yurn.satori.sdk.entity.EventEntity;
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

/**
 * @author Yurn
 */
@Slf4j
public class MyWebSocketClient extends WebSocketClient {
    private final String token;
    private final ListenerContainer listenerContainer;
    private ScheduledFuture<?> heart;
    private ScheduledFuture<?> reconnect;
    private Integer sequence;

    public MyWebSocketClient(String address, ListenerContainer listenerContainer) throws URISyntaxException {
        this(address, null, listenerContainer);
    }

    public MyWebSocketClient(String address, String token, ListenerContainer listenerContainer) throws URISyntaxException {
        super(new URI("ws://" + address + "/v1/events"), new Draft_6455());
        this.token = token;
        this.listenerContainer = listenerContainer;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("成功建立 WebSocket 连接: {}", serverHandshake);
        if (reconnect != null && !reconnect.isCancelled() && !reconnect.isDone()) {
            reconnect.cancel(true);
        }
        listenerContainer.runOnOpenListeners(serverHandshake);
        sendIdentify();
    }

    @Override
    public void onMessage(String message) {
        ConnectionEntity connection = ConnectionEntity.parse(message);
        listenerContainer.runOnMessageListeners(connection);
        switch (connection.getOp()) {
            case ConnectionEntity.EVENT -> {
                EventEntity body = (EventEntity) connection.getBody();
                sequence = body.getId();
                listenerContainer.runOnEventListeners(body);
            }
            case ConnectionEntity.PONG -> {}
            case ConnectionEntity.READY -> {
                ConnectionEntity.Ready ready = (ConnectionEntity.Ready) connection.getBody();
                log.info("成功建立事件推送: {}", Arrays.toString(ready.getLogins()));
                // 心跳
                ConnectionEntity sendConnection = new ConnectionEntity();
                sendConnection.setOp(ConnectionEntity.PING);
                heart = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("Heart")).scheduleAtFixedRate(
                        () -> send(JSONObject.toJSONString(sendConnection)), 10, 10, TimeUnit.SECONDS);

                listenerContainer.runOnConnectListeners(ready);
            }
            default -> log.error("Unsupported {}", connection);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("断开连接, i: {}, s: {}, b: {}", i, s, b);
        if (heart != null && !heart.isCancelled() && !heart.isDone()) {
            heart.cancel(true);
        }

        listenerContainer.runOnDisconnectListeners(s);

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
        listenerContainer.runOnErrorListeners(e);
    }

    private void sendIdentify() {
        ConnectionEntity connection = new ConnectionEntity();
        connection.setOp(ConnectionEntity.IDENTIFY);
        if (token != null || sequence != null) {
            ConnectionEntity.Identify body = new ConnectionEntity.Identify();
            connection.setBody(body);
            if (token != null) {
                body.setToken(token);
            }
            if (sequence != null) {
                body.setSequence(sequence);
            }
        }
        this.send(JSONObject.toJSONString(connection));
    }
}
