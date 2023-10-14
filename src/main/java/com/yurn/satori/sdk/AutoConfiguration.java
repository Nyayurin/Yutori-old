package com.yurn.satori.sdk;

import com.yurn.satori.sdk.property.YurnSdkProperties;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

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
            WebSocketClient client = new MyWebSocketClient(yurnSdkProperties);
            client.connect();
            return client;
        } catch (URISyntaxException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }
}
