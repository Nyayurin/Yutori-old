package com.yurn.sdk.boot;

import com.alibaba.fastjson2.JSONObject;
import com.yurn.sdk.Bots;
import com.yurn.sdk.entity.Connection;
import com.yurn.sdk.entity.Event;
import com.yurn.sdk.GlobalEventChannel;
import com.yurn.sdk.property.YurnSdkProperties;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 * @author Yurn
 */
@Slf4j
@Component
@EnableAsync
@ComponentScan("com.yurn.sdk")
public class AutoConfiguration {
    @Bean
    public WebSocketClient webSocketClient(YurnSdkProperties yurnSdkProperties, Bots bots) {
        try {
            WebSocketClient client = new WebSocketClient(
                    new URI("ws://" + yurnSdkProperties.getAddress() + "/v1/events"), new Draft_6455()) {
                private boolean isConnected;

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("建立WS连接: {}", serverHandshake);
                    Connection connection = new Connection();
                    connection.setOp(Connection.IDENTIFY);
                    this.send(JSONObject.toJSONString(connection));
                    isConnected = true;
                    Executors.newSingleThreadExecutor().submit(() -> {
                        while (isConnected) {
                            try {
                                Thread.sleep(10 * 1000);
                                connection.setOp(Connection.PING);
                                send(JSONObject.toJSONString(connection));
                            } catch (InterruptedException ignored) {}
                        }
                    });
                }

                @Override
                public void onMessage(String msg) {
                    Connection connection = Connection.parse(msg);
                    switch (connection.getOp()) {
                        case 0 -> {
                            log.info("收到事件: {}", connection.getBody());
                            GlobalEventChannel.INSTANCE.run((Event) connection.getBody());
                        }
                        case 2 -> {}
                        case 4 -> {
                            Connection.Ready body = (Connection.Ready) connection.getBody();
                            log.info("成功建立连接: {}", Arrays.toString(body.getLogins()));
                            bots.setLogins(body.getLogins());
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + connection.getOp());
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    log.info("断开连接, i: {}, s: {}, b: {}", i, s, b);
                    isConnected = false;
                    bots.setLogins(null);
                }

                @Override
                public void onError(Exception e) {
                    log.error("出现错误: {}", e.toString());
                }
            };
            client.connect();
            return client;
        } catch (URISyntaxException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }
}
