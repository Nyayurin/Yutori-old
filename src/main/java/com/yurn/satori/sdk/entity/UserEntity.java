package com.yurn.satori.sdk.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 用户
 *
 * @author Yurn
 */
@Data
public class UserEntity {
    /**
     * 用户 ID
     * 不可为 null
     */
    private String id;

    /**
     * 用户名称
     * 可为 null
     */
    private String name;

    /**
     * 用户头像
     * 可为 null
     */
    private String avatar;

    /**
     * 是否为机器人
     * 可为 null
     */
    @JSONField(name = "is_bot")
    private Boolean isBot;
}
