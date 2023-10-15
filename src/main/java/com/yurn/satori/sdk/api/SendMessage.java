package com.yurn.satori.sdk.api;

import com.yurn.satori.sdk.entity.PropertiesEntity;
import okhttp3.*;

import java.io.IOException;
import java.util.Optional;

/**
 * 一个比较底层的 API
 *
 * @author Yurn
 */
public final class SendMessage {
    private static final String VERSION = "v1";
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static PropertiesEntity properties;

    public static String sendGenericMessage(String platform, String selfId, String resource, String method, String body) {
        RequestBody requestBody = RequestBody.create(Optional.ofNullable(body).orElse(""),
                MediaType.parse("application/json;charset=utf-8"));
        Request request = new Request.Builder()
                .url(String.format("http://%s/%s/%s.%s", properties.getAddress(), VERSION, resource, method))
                .headers(makeHttpHeaders(platform, selfId))
                .post(requestBody)
                .build();
        return send(request);
    }

    public static String sendInternalMessage(String platform, String selfId, String method, String body) {
        RequestBody requestBody = RequestBody.create(Optional.ofNullable(body).orElse(""),
                MediaType.parse("application/json;charset=utf-8"));
        Request request = new Request.Builder()
                .url(String.format("http://%s/%s/internal/%s", properties.getAddress(), VERSION, method))
                .headers(makeHttpHeaders(platform, selfId))
                .post(requestBody)
                .build();
        return send(request);
    }

    private static Headers makeHttpHeaders(String platform, String selfId) {
        Headers.Builder builder = new Headers.Builder();
        builder.add("Content-Type", "application/json");
        if (properties.getToken() != null) {
            builder.add("Authorization", "Bearer " + properties.getToken());
        }
        if (platform != null) {
            builder.add("X-Platform", platform);
        }
        if (selfId != null) {
            builder.add("X-Self-ID", selfId);
        }
        return builder.build();
    }

    private static String send(Request request) {
        Call call = CLIENT.newCall(request);
        try (Response response = call.execute()) {
            if (response.body() != null) {
                return response.body().string();
            }
            return response.message();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
