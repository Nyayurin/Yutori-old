package com.yurn.sdk.entity.events;

/**
 * 群组的事件列表
 *
 * @author Yurn
 */
public final class GuildEvents {
    /**
     * 加入群组时触发
     * 必需资源: guild
     */
    public static final String GUILD_ADDED = "guild-added";

    /**
     * 群组被修改时触发
     * 必需资源: guild
     */
    public static final String GUILD_UPDATED = "guild-updated";

    /**
     * 退出群组时触发
     * 必需资源: guild
     */
    public static final String GUILD_REMOVED = "guild-removed";

    /**
     * 接收到新的入群邀请时触发
     * 必需资源: guild
     */
    public static final String GUILD_REQUEST = "guild-request";

    private GuildEvents() {}
}
