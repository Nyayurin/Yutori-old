package com.yurn.satori.sdk.api;

import com.yurn.satori.sdk.property.YurnSdkProperties;
import com.yurn.satori.sdk.entity.InternalEventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 一个比较底层的 API
 *
 * @author Yurn
 */
@SuppressWarnings({"UastIncorrectHttpHeaderInspection", "unused"})
@Component
public final class SendMessage {
    private static final String VERSION = "v1";

    private static YurnSdkProperties yurnSdkProperties;

    @Autowired
    public SendMessage(YurnSdkProperties yurnSdkProperties) {
        SendMessage.yurnSdkProperties = yurnSdkProperties;
    }

    public static String sendGenericMessage(String platform, String selfId, String resource, String method, String body) {
        HttpHeaders headers = makeHttpHeaders(platform, selfId);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                String.format("http://%s/%s/%s.%s", yurnSdkProperties.getAddress(), VERSION, resource, method), httpEntity,
                String.class);
    }

    public static InternalEventEntity sendInternalMessage(String platform, String selfId, String method, String body) {
        HttpHeaders headers = makeHttpHeaders(platform, selfId);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                String.format("http://%s/%s/internal/%s", yurnSdkProperties.getAddress(), VERSION, method), httpEntity,
                InternalEventEntity.class);
    }

    private static HttpHeaders makeHttpHeaders(String platform, String selfId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (yurnSdkProperties.getToken() != null) {
            headers.set("Authorization", "Bearer " + yurnSdkProperties.getToken());
        }
        if (platform != null) {
            headers.set("X-Platform", platform);
        }
        if (selfId != null) {
            headers.set("X-Self-ID", selfId);
        }
        return headers;
    }
}
