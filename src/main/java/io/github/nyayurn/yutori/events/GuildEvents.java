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

package io.github.nyayurn.yutori.events;

/**
 * 群组的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
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
