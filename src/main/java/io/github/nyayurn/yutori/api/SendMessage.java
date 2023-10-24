/*
Copyright (c) 2023 Yurn
yutori is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

package io.github.nyayurn.yutori.api;

import io.github.nyayurn.yutori.entity.PropertiesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 一个比较底层的 API
 *
 * @author Yurn
 */
@Data
@Slf4j
@AllArgsConstructor
public class SendMessage {
    /**
     * 平台名称
     */
    private String platform;

    /**
     * 机器人 ID
     */
    private String selfId;

    /**
     * Satori API 版本
     */
    private String version = "v1";

    /**
     * Satori 对接相关设置
     */
    private PropertiesEntity properties;

    public SendMessage(String platform, String selfId, PropertiesEntity properties) {
        this.platform = platform;
        this.selfId = selfId;
        this.properties = properties;
    }

    public String sendGenericMessage(String resource, String method, String body) {
        Request request = Request.post(String.format("http://%s/%s/%s.%s", properties.getAddress(), version, resource, method));
        initHttpPost(request, body);
        return send(request);
    }

    public String sendInternalMessage(String method, String body) {
        Request request = Request.post(String.format("http://%s/%s/internal/%s", properties.getAddress(), version, method));
        initHttpPost(request, body);
        return send(request);
    }

    private void initHttpPost(Request request, String body) {
        request.body(new StringEntity(body, StandardCharsets.UTF_8));
        request.setHeader("Content-Type", "application/json");
        if (properties.getToken() != null) {
            request.setHeader("Authorization", "Bearer " + properties.getToken());
        }
        if (platform != null) {
            request.setHeader("X-Platform", platform);
        }
        if (selfId != null) {
            request.setHeader("X-Self-ID", selfId);
        }
    }

    private String send(Request request) {
        try {
            return request.execute().returnContent().asString();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }
}
