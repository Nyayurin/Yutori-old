package com.yurn.sdk.entity.events;

/**
 * 群组角色的事件列表
 *
 * @author Yurn
 */
public final class GuildRoleEvents {
    /**
     * 群组角色被创建时触发
     * 必需资源: guild, role
     */
    public static final String GUILD_ROLE_CREATED = "guild-role-created";

    /**
     * 群组角色被修改时触发
     * 必需资源: guild, role
     */
    public static final String GUILD_ROLE_UPDATED = "guild-role-updated";

    /**
     * 群组角色被删除时触发
     * 必需资源: guild, role
     */
    public static final String GUILD_ROLE_DELETED = "guild-role-deleted";

    private GuildRoleEvents() {}
}
