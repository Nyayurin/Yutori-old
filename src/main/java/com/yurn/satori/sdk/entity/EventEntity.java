package com.yurn.satori.sdk.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 事件
 *
 * @author Yurn
 */
@Data
public class EventEntity implements ConnectionEntity.Body {
    /**
     * 事件 ID
     * 不可为 null
     */
    private Integer id;

    /**
     * 事件类型
     * 不可为 null
     */
    private String type;

    /**
     * 接收者的平台名称
     * 不可为 null
     */
    private String platform;

    /**
     * 接收者的平台账号
     * 不可为 null
     */
    @JSONField(name = "self_id")
    private String selfId;

    /**
     * 事件的时间戳
     * 不可为 null
     */
    private Long timestamp;

    /**
     * 事件所属的频道
     * 可为 null
     */
    private ChannelEntity channel;

    /**
     * 事件所属的群组
     * 可为 null
     */
    private GuildEntity guild;

    /**
     * 事件的登录信息
     * 可为 null
     */
    private LoginEntity login;

    /**
     * 事件的目标成员
     * 可为 null
     */
    private GuildMemberEntity member;

    /**
     * 事件的消息
     * 可为 null
     */
    private MessageEntity message;

    /**
     * 事件的操作者
     * 可为 null
     */
    private UserEntity operator;

    /**
     * 事件的目标角色
     * 可为 null
     */
    private GuildRoleEntity role;

    /**
     * 事件的目标用户
     * 可为 null
     */
    private UserEntity user;
}
