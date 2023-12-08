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

import com.alibaba.fastjson2.annotation.JSONField

object GuildEvents {
    const val ADDED = "guild-added"
    const val UPDATED = "guild-updated"
    const val REMOVED = "guild-removed"
    const val REQUEST = "guild-request"
}

class GuildEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    button: Button? = null,
    channel: Channel? = null,
    override val guild: Guild,
    login: Login? = null,
    member: GuildMember? = null,
    message: Message? = null,
    operator: User? = null,
    role: GuildRole? = null,
    user: User? = null
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): GuildEvent = GuildEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button,
            event.channel,
            event.guild!!,
            event.login,
            event.member,
            event.message,
            event.operator,
            event.role,
            event.user
        )
    }
}

object GuildMemberEvents {
    const val ADDED = "guild-member-added"
    const val UPDATED = "guild-member-updated"
    const val REMOVED = "guild-member-removed"
    const val REQUEST = "guild-member-request"
}

class GuildMemberEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    button: Button? = null,
    channel: Channel? = null,
    override val guild: Guild,
    login: Login? = null,
    override val member: GuildMember,
    message: Message? = null,
    operator: User? = null,
    role: GuildRole? = null,
    override val user: User
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): GuildMemberEvent = GuildMemberEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button,
            event.channel,
            event.guild!!,
            event.login,
            event.member!!,
            event.message,
            event.operator,
            event.role,
            event.user!!
        )
    }
}

object GuildRoleEvents {
    const val CREATED = "guild-role-created"
    const val UPDATED = "guild-role-updated"
    const val DELETED = "guild-role-deleted"
}

class GuildRoleEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    button: Button? = null,
    channel: Channel? = null,
    override val guild: Guild,
    login: Login? = null,
    member: GuildMember? = null,
    message: Message? = null,
    operator: User? = null,
    override val role: GuildRole,
    user: User? = null
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): GuildRoleEvent = GuildRoleEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button,
            event.channel,
            event.guild!!,
            event.login,
            event.member,
            event.message,
            event.operator,
            event.role!!,
            event.user
        )
    }
}

object InteractionEvents {
    const val BUTTON = "interaction/button"
    const val COMMAND = "interaction/command"
}

class InteractionButtonEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    override val button: Button,
    channel: Channel? = null,
    guild: Guild? = null,
    login: Login? = null,
    member: GuildMember? = null,
    message: Message? = null,
    operator: User? = null,
    role: GuildRole? = null,
    user: User? = null
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): InteractionButtonEvent = InteractionButtonEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button!!,
            event.channel,
            event.guild,
            event.login,
            event.member,
            event.message,
            event.operator,
            event.role,
            event.user
        )
    }
}

class InteractionCommandEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    button: Button? = null,
    channel: Channel? = null,
    guild: Guild? = null,
    login: Login? = null,
    member: GuildMember? = null,
    message: Message? = null,
    operator: User? = null,
    role: GuildRole? = null,
    user: User? = null
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): InteractionCommandEvent = InteractionCommandEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button,
            event.channel,
            event.guild,
            event.login,
            event.member,
            event.message,
            event.operator,
            event.role,
            event.user
        )
    }
}

object LoginEvents {
    const val ADDED = "login-added"
    const val REMOVED = "login-removed"
    const val UPDATED = "login-updated"
}

class LoginEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    button: Button? = null,
    channel: Channel? = null,
    guild: Guild? = null,
    override val login: Login,
    member: GuildMember? = null,
    message: Message? = null,
    operator: User? = null,
    role: GuildRole? = null,
    user: User? = null
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): LoginEvent = LoginEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button,
            event.channel,
            event.guild,
            event.login!!,
            event.member,
            event.message,
            event.operator,
            event.role,
            event.user
        )
    }
}

object MessageEvents {
    const val CREATED = "message-created"
    const val UPDATED = "message-updated"
    const val DELETED = "message-deleted"
}

class MessageEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    button: Button? = null,
    override val channel: Channel,
    guild: Guild? = null,
    login: Login? = null,
    member: GuildMember? = null,
    override val message: Message,
    operator: User? = null,
    role: GuildRole? = null,
    override val user: User
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): MessageEvent = MessageEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button,
            event.channel!!,
            event.guild,
            event.login,
            event.member,
            event.message!!,
            event.operator,
            event.role,
            event.user!!
        )
    }
}

object ReactionEvents {
    const val ADDED = "reaction-added"
    const val REMOVED = "reaction-removed"
}

class ReactionEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    button: Button? = null,
    channel: Channel? = null,
    guild: Guild? = null,
    login: Login? = null,
    member: GuildMember? = null,
    message: Message? = null,
    operator: User? = null,
    role: GuildRole? = null,
    user: User? = null
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): ReactionEvent = ReactionEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button,
            event.channel,
            event.guild,
            event.login,
            event.member,
            event.message,
            event.operator,
            event.role,
            event.user
        )
    }
}

object UserEvents {
    const val FRIEND_REQUEST = "friend-request"
}

class UserEvent @JvmOverloads constructor(
    id: Number,
    type: String,
    platform: String,
    @JSONField(name = "self_id") selfId: String,
    timestamp: Number,
    argv: Argv? = null,
    button: Button? = null,
    channel: Channel? = null,
    guild: Guild? = null,
    login: Login? = null,
    member: GuildMember? = null,
    message: Message? = null,
    operator: User? = null,
    role: GuildRole? = null,
    override val user: User
) : Event(id, type, platform, selfId, timestamp, argv, button, channel, guild, login, member, message, operator, role, user) {
    companion object {
        @JvmStatic
        infix fun parse(event: Event): UserEvent = UserEvent(
            event.id,
            event.type,
            event.platform,
            event.selfId,
            event.timestamp,
            event.argv,
            event.button,
            event.channel,
            event.guild,
            event.login,
            event.member,
            event.message,
            event.operator,
            event.role,
            event.user!!
        )
    }
}