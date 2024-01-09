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

import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.parseArray
import com.alibaba.fastjson2.parseObject
import io.github.nyayurn.yutori.message.MessageDSLBuilder
import io.github.nyayurn.yutori.message.message
import org.apache.hc.client5.http.fluent.Request
import org.apache.hc.core5.http.io.entity.StringEntity
import java.nio.charset.StandardCharsets

/**
 * 封装所有 Action, 应通过本类对 Satori Server 发送事件
 * @property channelApi 频道 API
 * @property guildApi 群组 API
 * @property memberApi 群组成员 API
 * @property roleApi 群组角色 API
 * @property loginApi 登录信息 API
 * @property messageApi 消息 API
 * @property reactionApi 表态 API
 * @property userApi 用户 API
 * @property sendMessage SendMessage 底层
 */
class Bot private constructor(
    private val channelApi: ChannelApi,
    private val guildApi: GuildApi,
    private val memberApi: GuildMemberApi,
    private val roleApi: GuildRoleApi,
    private val loginApi: LoginApi,
    private val messageApi: MessageApi,
    private val reactionApi: ReactionApi,
    private val userApi: UserApi,
    private val sendMessage: SendMessage
) {
    fun getChannel(channelId: String) = channelApi.getChannel(channelId)

    @JvmOverloads
    fun listChannel(guildId: String, next: String? = null) = channelApi.listChannel(guildId, next)
    fun createChannel(guildId: String, data: Channel) = channelApi.createChannel(guildId, data)
    fun updateChannel(channelId: String, data: Channel) = channelApi.updateChannel(channelId, data)
    fun deleteChannel(channelId: String) = channelApi.deleteChannel(channelId)
    fun createUserChannel(userId: String, guildId: String?) = channelApi.createUserChannel(userId, guildId)
    fun getGuild(guildId: String) = guildApi.getGuild(guildId)

    @JvmOverloads
    fun listGuild(next: String? = null) = guildApi.listGuild(next)
    fun approveGuild(messageId: String, approve: Boolean, comment: String) =
        guildApi.approveGuild(messageId, approve, comment)

    fun getGuildMember(guildId: String, userId: String) = memberApi.getGuildMember(guildId, userId)

    @JvmOverloads
    fun listGuildMember(guildId: String, next: String? = null) = memberApi.listGuildMember(guildId, next)

    @JvmOverloads
    fun kickGuildMember(guildId: String, userId: String, permanent: Boolean? = null) =
        memberApi.kickGuildMember(guildId, userId, permanent)

    @JvmOverloads
    fun approveGuildMember(messageId: String, approve: Boolean, comment: String? = null) =
        memberApi.approveGuildMember(messageId, approve, comment)

    fun setGuildRole(guildId: String, userId: String, roleId: String) = roleApi.setGuildRole(guildId, userId, roleId)
    fun unsetGuildRole(guildId: String, userId: String, roleId: String) =
        roleApi.unsetGuildRole(guildId, userId, roleId)

    @JvmOverloads
    fun listGuildRole(guildId: String, next: String? = null) = roleApi.listGuildRole(guildId, next)
    fun createGuildRole(guildId: String, role: GuildRole) = roleApi.createGuildRole(guildId, role)
    fun updateGuildRole(guildId: String, roleId: String, role: GuildRole) =
        roleApi.updateGuildRole(guildId, roleId, role)

    fun deleteGuildRole(guildId: String, roleId: String) = roleApi.deleteGuildRole(guildId, roleId)
    fun getLogin() = loginApi.getLogin()
    fun createMessage(channelId: String, content: String) = messageApi.createMessage(channelId, content)
    fun createMessage(channelId: String, content: MessageDSLBuilder.() -> Unit) =
        messageApi.createMessage(channelId, message(content))

    fun getMessage(channelId: String, messageId: String) = messageApi.getMessage(channelId, messageId)
    fun deleteMessage(channelId: String, messageId: String) = messageApi.deleteMessage(channelId, messageId)
    fun updateMessage(channelId: String, messageId: String, content: String) =
        messageApi.updateMessage(channelId, messageId, content)

    fun updateMessage(channelId: String, messageId: String, content: MessageDSLBuilder.() -> Unit) =
        messageApi.updateMessage(channelId, messageId, message(content))

    @JvmOverloads
    fun listMessage(channelId: String, next: String? = null) = messageApi.listMessage(channelId, next)
    fun createReaction(channelId: String, messageId: String, emoji: String) =
        reactionApi.createReaction(channelId, messageId, emoji)

    @JvmOverloads
    fun deleteReaction(channelId: String, messageId: String, emoji: String, userId: String? = null) =
        reactionApi.deleteReaction(channelId, messageId, emoji, userId)

    @JvmOverloads
    fun clearReaction(channelId: String, messageId: String, emoji: String? = null) =
        reactionApi.clearReaction(channelId, messageId, emoji)

    @JvmOverloads
    fun listReaction(channelId: String, messageId: String, emoji: String, next: String? = null) =
        reactionApi.listReaction(channelId, messageId, emoji, next)

    fun getUser(userId: String) = userApi.getUser(userId)

    @JvmOverloads
    fun listFriend(next: String? = null) = userApi.listFriend(next)

    @JvmOverloads
    fun approveFriend(messageId: String, approve: Boolean, comment: String? = null) =
        userApi.approveFriend(messageId, approve, comment)

    @JvmOverloads
    fun sendGenericMessage(resource: String, method: String, body: String? = null) =
        sendMessage.sendGenericMessage(resource, method, body)

    companion object {
        /**
         * 工厂方法
         * @param sendMessage SendMessage
         * @return Bot
         */
        @JvmStatic
        fun of(sendMessage: SendMessage) = Bot(
            ChannelApi.of(sendMessage), GuildApi.of(sendMessage), GuildMemberApi.of(sendMessage),
            GuildRoleApi.of(sendMessage), LoginApi.of(sendMessage), MessageApi.of(sendMessage),
            ReactionApi.of(sendMessage), UserApi.of(sendMessage), sendMessage
        )

        /**
         * 工厂方法
         * @param platform 平台
         * @param selfId 自身 ID
         * @param properties 配置
         * @return Bot
         */
        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            of(SendMessage.of(platform, selfId, properties))

        /**
         * 工厂方法
         * @param event 事件
         * @param properties 配置
         * @return Bot
         */
        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

/**
 * 频道 API
 * @property sendMessage SendMessage
 */
class ChannelApi private constructor(private val sendMessage: SendMessage) {
    fun getChannel(channelId: String): Channel {
        val map = JSONObject()
        map["channel_id"] = channelId
        val response = sendMessage.sendGenericMessage("channel", "get", map.toString())
        return response.parseObject<Channel>()
    }

    fun listChannel(guildId: String, next: String? = null): List<PageResponse<Channel>> {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["next"] = next
        val response = sendMessage.sendGenericMessage("channel", "list", map.toString())
        return response.parseArray<PageResponse<Channel>>()
    }

    fun createChannel(guildId: String, data: Channel): Channel {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["data"] = data
        val response = sendMessage.sendGenericMessage("channel", "create", map.toString())
        return response.parseObject<Channel>()
    }

    fun updateChannel(channelId: String, data: Channel) {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["data"] = data
        sendMessage.sendGenericMessage("channel", "update", map.toString())
    }

    fun deleteChannel(channelId: String) {
        val map = JSONObject()
        map["channel_id"] = channelId
        sendMessage.sendGenericMessage("channel", "delete", map.toString())
    }

    fun createUserChannel(userId: String, guildId: String?): Channel {
        val map = JSONObject()
        map["user_id"] = userId
        map["guild_id"] = guildId
        val response = sendMessage.sendGenericMessage("user.channel", "create", map.toString())
        return response.parseObject<Channel>()
    }

    companion object {
        @JvmStatic
        fun of(sendMessage: SendMessage) = ChannelApi(sendMessage)

        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            of(SendMessage.of(platform, selfId, properties))

        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

/**
 * 群组 API
 * @property sendMessage SendMessage
 */
class GuildApi private constructor(private val sendMessage: SendMessage) {
    fun getGuild(guildId: String): Guild {
        val map = JSONObject()
        map["guild_id"] = guildId
        val response = sendMessage.sendGenericMessage("guild", "get", map.toString())
        return response.parseObject<Guild>()
    }

    fun listGuild(next: String? = null): List<PageResponse<Guild>> {
        val map = JSONObject()
        map["next"] = next
        val response = sendMessage.sendGenericMessage("guild", "list", map.toString())
        return response.parseArray<PageResponse<Guild>>()
    }

    fun approveGuild(messageId: String, approve: Boolean, comment: String) {
        val map = JSONObject()
        map["message_id"] = messageId
        map["approve"] = approve
        map["comment"] = comment
        sendMessage.sendGenericMessage("guild", "approve", map.toString())
    }

    companion object {
        @JvmStatic
        fun of(sendMessage: SendMessage) = GuildApi(sendMessage)

        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            of(SendMessage.of(platform, selfId, properties))

        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

/**
 * 群组成员 API
 * @property sendMessage SendMessage
 */
class GuildMemberApi private constructor(private val sendMessage: SendMessage) {
    fun getGuildMember(guildId: String, userId: String): GuildMember {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["user_id"] = userId
        val response = sendMessage.sendGenericMessage("guild.member", "get", map.toString())
        return response.parseObject<GuildMember>()
    }

    fun listGuildMember(guildId: String, next: String? = null): List<PageResponse<GuildMember>> {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["next"] = next
        val response = sendMessage.sendGenericMessage("guild.member", "list", map.toString())
        return response.parseArray<PageResponse<GuildMember>>()
    }

    fun kickGuildMember(guildId: String, userId: String, permanent: Boolean? = null) {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["user_id"] = userId
        map["permanent"] = permanent
        sendMessage.sendGenericMessage("guild.member", "kick", map.toString())
    }

    fun approveGuildMember(messageId: String, approve: Boolean, comment: String? = null) {
        val map = JSONObject()
        map["message_id"] = messageId
        map["approve"] = approve
        map["comment"] = comment
        sendMessage.sendGenericMessage("guild.member", "approve", map.toString())
    }

    companion object {
        @JvmStatic
        fun of(sendMessage: SendMessage) = GuildMemberApi(sendMessage)

        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            of(SendMessage.of(platform, selfId, properties))

        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

/**
 * 群组角色 API
 * @property sendMessage SendMessage
 */
class GuildRoleApi private constructor(private val sendMessage: SendMessage) {
    fun setGuildRole(guildId: String, userId: String, roleId: String) {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["user_id"] = userId
        map["role_id"] = roleId
        sendMessage.sendGenericMessage("guild.member.role", "set", map.toString())
    }

    fun unsetGuildRole(guildId: String, userId: String, roleId: String) {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["user_id"] = userId
        map["role_id"] = roleId
        sendMessage.sendGenericMessage("guild.member.role", "unset", map.toString())
    }

    fun listGuildRole(guildId: String, next: String? = null): List<PageResponse<GuildRole>> {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["next"] = next
        val response = sendMessage.sendGenericMessage("guild.role", "list", map.toString())
        return response.parseArray<PageResponse<GuildRole>>()
    }

    fun createGuildRole(guildId: String, role: GuildRole): GuildRole {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["role"] = role
        val response = sendMessage.sendGenericMessage("guild.role", "create", map.toString())
        return response.parseObject<GuildRole>()
    }

    fun updateGuildRole(guildId: String, roleId: String, role: GuildRole) {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["role_id"] = roleId
        map["role"] = role
        sendMessage.sendGenericMessage("guild.role", "update", map.toString())
    }

    fun deleteGuildRole(guildId: String, roleId: String) {
        val map = JSONObject()
        map["guild_id"] = guildId
        map["role_id"] = roleId
        sendMessage.sendGenericMessage("guild.role", "delete", map.toString())
    }

    companion object {
        @JvmStatic
        fun of(sendMessage: SendMessage) = GuildRoleApi(sendMessage)

        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            of(SendMessage.of(platform, selfId, properties))

        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

/**
 * 登录信息 API
 * @property sendMessage SendMessage
 */
class LoginApi private constructor(private val sendMessage: SendMessage) {
    fun getLogin(): Login {
        val response = sendMessage.sendGenericMessage("login", "get", null)
        return response.parseObject<Login>()
    }

    companion object {
        @JvmStatic
        fun of(sendMessage: SendMessage) = LoginApi(sendMessage)

        @JvmStatic
        fun of(properties: SatoriProperties) = of(SendMessage.of(null, null, properties))
    }
}

/**
 * 消息 API
 * @property sendMessage SendMessage
 */
class MessageApi(private val sendMessage: SendMessage) {
    fun createMessage(channelId: String, content: String): List<Message> {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["content"] = content
        val response = sendMessage.sendGenericMessage("message", "create", map.toString())
        return response.parseArray<Message>()
    }

    fun getMessage(channelId: String, messageId: String): Message {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["message_id"] = messageId
        val response = sendMessage.sendGenericMessage("message", "get", map.toString())
        return response.parseObject<Message>()
    }

    fun deleteMessage(channelId: String, messageId: String) {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["message_id"] = messageId
        sendMessage.sendGenericMessage("message", "delete", map.toString())
    }

    fun updateMessage(channelId: String, messageId: String, content: String) {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["message_id"] = messageId
        map["content"] = content
        sendMessage.sendGenericMessage("message", "update", map.toString())
    }

    fun listMessage(channelId: String, next: String? = null): List<PageResponse<Message>> {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["next"] = next
        val response = sendMessage.sendGenericMessage("message", "list", map.toString())
        return response.parseArray<PageResponse<Message>>()
    }

    companion object {
        @JvmStatic
        fun of(sendMessage: SendMessage) = MessageApi(sendMessage)

        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            of(SendMessage.of(platform, selfId, properties))

        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

/**
 * 表态 API
 * @property sendMessage SendMessage
 */
class ReactionApi private constructor(private val sendMessage: SendMessage) {
    fun createReaction(channelId: String, messageId: String, emoji: String) {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["message_id"] = messageId
        map["emoji"] = emoji
        sendMessage.sendGenericMessage("reaction", "create", map.toString())
    }

    fun deleteReaction(channelId: String, messageId: String, emoji: String, userId: String? = null) {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["message_id"] = messageId
        map["emoji"] = emoji
        map["user_id"] = userId
        sendMessage.sendGenericMessage("reaction", "delete", map.toString())
    }

    fun clearReaction(channelId: String, messageId: String, emoji: String? = null) {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["message_id"] = messageId
        map["emoji"] = emoji
        sendMessage.sendGenericMessage("reaction", "clear", map.toString())
    }

    fun listReaction(
        channelId: String, messageId: String, emoji: String, next: String? = null
    ): List<PageResponse<User>> {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["message_id"] = messageId
        map["emoji"] = emoji
        map["next"] = next
        val response = sendMessage.sendGenericMessage("reaction", "list", map.toString())
        return response.parseArray<PageResponse<User>>()
    }

    companion object {
        @JvmStatic
        fun of(sendMessage: SendMessage) = ReactionApi(sendMessage)

        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            of(SendMessage.of(platform, selfId, properties))

        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

/**
 * 用户 API
 * @property sendMessage SendMessage
 */
class UserApi private constructor(private val sendMessage: SendMessage) {
    fun getUser(userId: String): User {
        val map = JSONObject()
        map["user_id"] = userId
        val response = sendMessage.sendGenericMessage("user", "get", map.toString())
        return response.parseObject<User>()
    }

    fun listFriend(next: String? = null): List<PageResponse<User>> {
        val map = JSONObject()
        map["next"] = next
        val response = sendMessage.sendGenericMessage("friend", "list", map.toString())
        return response.parseArray<PageResponse<User>>()
    }

    fun approveFriend(messageId: String, approve: Boolean, comment: String? = null) {
        val map = JSONObject()
        map["message_id"] = messageId
        map["approve"] = approve
        map["comment"] = comment
        sendMessage.sendGenericMessage("friend", "approve", map.toString())
    }

    companion object {
        @JvmStatic
        fun of(sendMessage: SendMessage) = UserApi(sendMessage)

        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            of(SendMessage.of(platform, selfId, properties))

        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

/**
 * 实现 HTTP 交互的底层类
 * @property platform 平台
 * @property selfId 自身的 ID
 * @property properties 配置
 * @property version Satori 协议版本
 */
class SendMessage private constructor(
    private val platform: String?,
    private val selfId: String?,
    private val properties: SatoriProperties,
    private val version: String
) {
    fun sendGenericMessage(resource: String, method: String, body: String?): String {
        return (Request.post("http://${properties.address}/$version/$resource.$method").apply {
            body(StringEntity(body, StandardCharsets.UTF_8))
            setHeader("Content-Type", "application/json")
            setHeader("Authorization", "Bearer ${properties.token}")
            setHeader("X-Platform", platform)
            setHeader("X-Self-ID", selfId)
        }).execute().returnContent().asString()
    }

    companion object {
        /**
         * 工厂方法
         * @param platform 平台
         * @param selfId 自身的 ID
         * @param properties 配置
         * @param version Satori 协议版本
         * @return SendMessage
         */
        @JvmStatic
        @JvmOverloads
        fun of(
            platform: String? = null, selfId: String? = null, properties: SatoriProperties, version: String = "v1"
        ) = SendMessage(platform, selfId, properties, version)
    }
}