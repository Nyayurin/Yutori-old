/*
Copyright (c) 2023 Yurn
yutori is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

package io.github.nyayurn.yutori.event;

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
