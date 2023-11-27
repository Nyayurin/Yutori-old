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

package io.github.nyayurn.yutori

object GuildEvents {
    const val GUILD_ADDED = "guild-added"
    const val GUILD_UPDATED = "guild-updated"
    const val GUILD_REMOVED = "guild-removed"
    const val GUILD_REQUEST = "guild-request"
}

object GuildMemberEvents {
    const val GUILD_MEMBER_ADDED = "guild-member-added"
    const val GUILD_MEMBER_UPDATED = "guild-member-updated"
    const val GUILD_MEMBER_REMOVED = "guild-member-removed"
    const val GUILD_MEMBER_REQUEST = "guild-member-request"
}

object GuildRoleEvents {
    const val GUILD_ROLE_CREATED = "guild-role-created"
    const val GUILD_ROLE_UPDATED = "guild-role-updated"
    const val GUILD_ROLE_DELETED = "guild-role-deleted"
}

object InteractionEvents {
    const val BUTTON = "interaction/button"
    const val COMMAND = "interaction/command"
}

object LoginEvents {
    const val LOGIN_ADDED = "login-added"
    const val LOGIN_REMOVED = "login-removed"
    const val LOGIN_UPDATED = "login-updated"
}

object MessageEvents {
    const val MESSAGE_CREATED = "message-created"
    const val MESSAGE_UPDATED = "message-updated"
    const val MESSAGE_DELETED = "message-deleted"
}

object ReactionEvents {
    const val REACTION_ADDED = "reaction-added"
    const val REACTION_REMOVED = "reaction-removed"
}

object UserEvents {
    const val FRIEND_REQUEST = "friend-request"
}
