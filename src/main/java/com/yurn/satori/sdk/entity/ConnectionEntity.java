package com.yurn.satori.sdk.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

/**
 * 信令
 *
 * @author Yurn
 */
@Data
public class ConnectionEntity {
    /**
     * 信令类型
     * 不可为 null
     */
    private Integer op;

    /**
     * 信令数据
     * 可为 null
     */
    private Body body;

    public static ConnectionEntity parse(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        ConnectionEntity connection = new ConnectionEntity();
        connection.setOp(jsonObject.getIntValue("op"));
        switch (jsonObject.getIntValue("op")) {
            case 0 -> {
                EventEntity event = jsonObject.getObject("body", EventEntity.class);
                connection.setBody(event);
            }
            case 4 -> {
                Ready body = new Ready();
                body.setLogins(jsonObject.getJSONObject("body").getJSONArray("logins").toArray(LoginEntity.class));
                connection.setBody(body);
            }
            default -> {}
        }
        return connection;
    }

    /**
     * 接受 事件
     */
    public static final Integer EVENT = 0;
    /**
     * 发送 心跳
     */
    public static final Integer PING = 1;
    /**
     * 接收	心跳回复
     */
    public static final Integer PONG = 2;
    /**
     * 发送	鉴权
     */
    public static final Integer IDENTIFY = 3;
    /**
     * 接收	鉴权回复
     */
    public static final Integer READY = 4;

    public interface Body {}

    @Data
    public static class Identify implements Body {
        /**
         * 鉴权令牌
         * 可为 null
         */
        private String token;

        /**
         * 序列号
         * 可为 null
         */
        private Integer sequence;
    }

    @Data
    public static class Ready implements Body {
        /**
         * 登录信息
         * 不可为 null
         */
        private LoginEntity[] logins;
    }
}
