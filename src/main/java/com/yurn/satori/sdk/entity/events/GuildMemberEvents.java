package com.yurn.satori.sdk.entity.events;

/**
 * 群组成员的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class GuildMemberEvents {
    /**
     * 群组成员增加时触发
     * 必需资源: guild, member, user
     */
    public static final String GUILD_MEMBER_ADDED = "guild-member-added";

    /**
     * 群组成员信息更新时触发
     * 必需资源: guild, member, user
     */
    public static final String GUILD_MEMBER_UPDATED = "guild-member-updated";

    /**
     * 群组成员移除时触发
     * 必需资源: guild, member, user
     */
    public static final String GUILD_MEMBER_REMOVED = "guild-member-removed";

    /**
     * 接收到新的加群请求时触发
     * 必需资源: guild, member, user
     */
    public static final String GUILD_MEMBER_REQUEST = "guild-member-request";

    private GuildMemberEvents() {}
}
