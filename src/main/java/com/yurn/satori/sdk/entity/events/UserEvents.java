package com.yurn.satori.sdk.entity.events;

/**
 * 用户的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class UserEvents {
    /**
     * 接收到新的好友申请时触发
     * 必需资源: user
     */
    public static final String FRIEND_REQUEST = "friend-request";

    private UserEvents() {}
}
