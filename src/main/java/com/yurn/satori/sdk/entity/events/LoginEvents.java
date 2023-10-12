package com.yurn.satori.sdk.entity.events;

/**
 * 登录的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class LoginEvents {
    /**
     * 登录被创建时触发
     * 必需资源: login
     */
    public static final String LOGIN_ADDED = "login-added";

    /**
     * 登录被删除时触发
     * 必需资源: login
     */
    public static final String LOGIN_REMOVED = "login-removed";

    /**
     * 登录信息更新时触发
     * 必需资源: login
     */
    public static final String LOGIN_UPDATED = "login-updated";

    private LoginEvents() {}
}
