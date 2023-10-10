package com.yurn.sdk.api;

import com.yurn.sdk.entity.InternalEvent;
import com.yurn.sdk.property.YurnSdkProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Component
@Slf4j
public class SendMessage {
    private final String version = "v1";
    @Resource
    private YurnSdkProperties yurnSdkProperties;

    public String sendGenericMessage(String platform, String selfId, String resource, String method, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (platform != null) {
            headers.set("X-Platform", platform);
        }
        if (selfId != null) {
            headers.set("X-Self-ID", selfId);
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                String.format("http://%s/%s/%s.%s", yurnSdkProperties.getAddress(), version, resource, method), httpEntity,
                String.class);
    }

    public InternalEvent sendInternalMessage(String platform, String selfId, String method, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Platform", platform);
        headers.set("X-Self-ID", selfId);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                String.format("http://%s/%s/internal/%s", yurnSdkProperties.getAddress(), version, method), httpEntity,
                InternalEvent.class);
    }
}
