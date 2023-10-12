package com.yurn.satori.sdk.entity.events;

/**
 * 登录的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class MessageEvents {
    /**
     * 当消息被创建时触发
     * 必需资源: channel, message, user
     */
    public static final String MESSAGE_CREATED = "message-created";

    /**
     * 当消息被编辑时触发
     * 必需资源: channel, message, user
     */
    public static final String MESSAGE_UPDATED = "message-updated";

    /**
     * 当消息被删除时触发
     * 必需资源: channel, message, user
     */
    public static final String MESSAGE_DELETED = "message-deleted";

    private MessageEvents() {}
}
