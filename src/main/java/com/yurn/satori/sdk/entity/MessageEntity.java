package com.yurn.satori.sdk.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.lang.reflect.Member;

/**
 * 消息
 *
 * @author Yurn
 */
@Data
public class MessageEntity {
    /**
     * 消息 ID
     * 不可为 null
     */
    private String id;

    /**
     * 消息内容
     * 不可为 null
     */
    private String content;

    /**
     * 频道对象
     * 可为 null
     */
    private ChannelEntity channel;

    /**
     * 群组对象
     * 可为 null
     */
    private GuildEntity guild;

    /**
     * 成员对象
     * 可为 null
     */
    private Member member;

    /**
     * 用户对象
     * 可为 null
     */
    private UserEntity user;

    /**
     * 消息发送的时间戳
     * 可为 null
     */
    @JSONField(name = "created_at")
    private Long createdAt;

    /**
     * 消息修改的时间戳
     * 可为 null
     */
    @JSONField(name = "updated_at")
    private Long updatedAt;
}
