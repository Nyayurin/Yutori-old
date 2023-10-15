package com.yurn.satori.sdk;

import com.yurn.satori.sdk.api.SendMessage;
import com.yurn.satori.sdk.entity.PropertiesEntity;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.concurrent.TimeUnit;

/**
 * @author Yurn
 */
public class Boot implements Runnable {
    private final PropertiesEntity properties;

    public Boot(PropertiesEntity properties) {
        SendMessage.properties = properties;
        this.properties = properties;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().get().url("ws://" + properties.getAddress() + "/v1/events").build();
        client.newWebSocket(request, new MyWebSocketClient(properties));
    }
}
