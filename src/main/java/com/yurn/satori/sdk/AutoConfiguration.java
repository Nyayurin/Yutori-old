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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

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
@Component
@EnableAsync
@ComponentScan("com.yurn.satori.sdk")
public class AutoConfiguration {
    @Bean
    public WebSocketClient webSocketClient(YurnSdkProperties yurnSdkProperties) {
        try {
            WebSocketClient client = new WebSocketClient(
                    new URI("ws://" + yurnSdkProperties.getAddress() + "/v1/events"), new Draft_6455()) {
                private ScheduledFuture<?> heart;
                private ScheduledFuture<?> reconnect;

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("建立WS连接: {}", serverHandshake);
                    ConnectionEntity connection = new ConnectionEntity();
                    connection.setOp(ConnectionEntity.IDENTIFY);
                    this.send(JSONObject.toJSONString(connection));
                    if (reconnect != null && !reconnect.isCancelled() && !reconnect.isDone()) {
                        reconnect.cancel(true);
                    }
                }

                @Override
                public void onMessage(String msg) {
                    ConnectionEntity connection = ConnectionEntity.parse(msg);
                    switch (connection.getOp()) {
                        case 0 -> {
                            log.info("收到事件: {}", connection.getBody());
                            GlobalEventChannel.INSTANCE.runEvent((EventEntity) connection.getBody());
                        }
                        case 2 -> {}
                        case 4 -> {
                            ConnectionEntity.Ready ready = (ConnectionEntity.Ready) connection.getBody();
                            log.info("成功建立连接: {}", Arrays.toString(ready.getLogins()));
                            BotContainer.setLogins(ready.getLogins());

                            GlobalEventChannel.INSTANCE.runConnect(ready);

                            // 心跳
                            ConnectionEntity sendConnection = new ConnectionEntity();
                            sendConnection.setOp(ConnectionEntity.PING);
                            heart = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("Heart"))
                                    .scheduleAtFixedRate(() -> send(JSONObject.toJSONString(sendConnection)), 10, 10, TimeUnit.SECONDS);
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + connection.getOp());
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
                            .scheduleAtFixedRate(this::connect, 5, 5, TimeUnit.SECONDS);
                }

                @Override
                public void onError(Exception e) {
                    log.error("出现错误: {}", e.toString());
                    GlobalEventChannel.INSTANCE.runError(e);
                }
            };
            client.connect();
            return client;
        } catch (URISyntaxException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }
}
