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
import org.apache.hc.client5.http.fluent.Request
import org.apache.hc.core5.http.io.entity.StringEntity
import java.nio.charset.StandardCharsets

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
    fun listChannel(guildId: String, next: String? = null) = channelApi.listChannel(guildId, next)
    fun createChannel(guildId: String, data: Channel) = channelApi.createChannel(guildId, data)
    fun updateChannel(channelId: String, data: Channel) = channelApi.updateChannel(channelId, data)
    fun deleteChannel(channelId: String) = channelApi.deleteChannel(channelId)
    fun createUserChannel(userId: String, guildId: String?) = channelApi.createUserChannel(userId, guildId)
    fun getGuild(guildId: String) = guildApi.getGuild(guildId)
    fun listGuild(next: String? = null) = guildApi.listGuild(next)
    fun approveGuild(messageId: String, approve: Boolean, comment: String) = guildApi.approveGuild(messageId, approve, comment)
    fun getGuildMember(guildId: String, userId: String) = memberApi.getGuildMember(guildId, userId)
    fun listGuildMember(guildId: String, next: String? = null) = memberApi.listGuildMember(guildId, next)
    fun kickGuildMember(guildId: String, userId: String, permanent: Boolean? = null) = memberApi.kickGuildMember(guildId, userId, permanent)
    fun approveGuildMember(messageId: String, approve: Boolean, comment: String? = null) =
        memberApi.approveGuildMember(messageId, approve, comment)

    fun setGuildRole(guildId: String, userId: String, roleId: String) = roleApi.setGuildRole(guildId, userId, roleId)
    fun unsetGuildRole(guildId: String, userId: String, roleId: String) = roleApi.unsetGuildRole(guildId, userId, roleId)
    fun listGuildRole(guildId: String, next: String? = null) = roleApi.listGuildRole(guildId, next)
    fun createGuildRole(guildId: String, role: GuildRole) = roleApi.createGuildRole(guildId, role)
    fun updateGuildRole(guildId: String, roleId: String, role: GuildRole) = roleApi.updateGuildRole(guildId, roleId, role)
    fun deleteGuildRole(guildId: String, roleId: String) = roleApi.deleteGuildRole(guildId, roleId)
    fun getLogin() = loginApi.getLogin()
    fun createMessage(channelId: String, content: String) = messageApi.createMessage(channelId, content)
    fun getMessage(channelId: String, messageId: String) = messageApi.getMessage(channelId, messageId)
    fun deleteMessage(channelId: String, messageId: String) = messageApi.deleteMessage(channelId, messageId)
    fun updateMessage(channelId: String, messageId: String, content: String) = messageApi.updateMessage(channelId, messageId, content)
    fun listMessage(channelId: String, next: String? = null) = messageApi.listMessage(channelId, next)
    fun createReaction(channelId: String, messageId: String, emoji: String) = reactionApi.createReaction(channelId, messageId, emoji)
    fun deleteReaction(channelId: String, messageId: String, emoji: String, userId: String? = null) =
        reactionApi.deleteReaction(channelId, messageId, emoji, userId)

    fun clearReaction(channelId: String, messageId: String, emoji: String? = null) = reactionApi.clearReaction(channelId, messageId, emoji)
    fun listReaction(channelId: String, messageId: String, emoji: String, next: String? = null) =
        reactionApi.listReaction(channelId, messageId, emoji, next)

    fun getUser(userId: String) = userApi.getUser(userId)
    fun listFriend(next: String? = null) = userApi.listFriend(next)
    fun approveFriend(messageId: String, approve: Boolean, comment: String? = null) = userApi.approveFriend(messageId, approve, comment)
    fun sendGenericMessage(resource: String, method: String, body: String?) = sendMessage.sendGenericMessage(resource, method, body)
    fun sendInternalMessage(method: String, body: String) = sendMessage.sendInternalMessage(method, body)

    companion object {
        fun of(sendMessage: SendMessage) = Bot(
            ChannelApi.of(sendMessage),
            GuildApi.of(sendMessage),
            GuildMemberApi.of(sendMessage),
            GuildRoleApi.of(sendMessage),
            LoginApi.of(sendMessage),
            MessageApi.of(sendMessage),
            ReactionApi.of(sendMessage),
            UserApi.of(sendMessage),
            sendMessage
        )

        fun of(platform: String, selfId: String, properties: Properties) = of(SendMessage.of(platform, selfId, properties))
        fun of(event: Event, properties: Properties) = of(event.platform, event.selfId, properties)
    }
}

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
        fun of(sendMessage: SendMessage) = ChannelApi(sendMessage)

        fun of(platform: String, selfId: String, properties: Properties) = of(SendMessage.of(platform, selfId, properties))
        fun of(event: Event, properties: Properties) = of(event.platform, event.selfId, properties)
    }
}

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
        fun of(sendMessage: SendMessage) = GuildApi(sendMessage)

        fun of(platform: String, selfId: String, properties: Properties) = of(SendMessage.of(platform, selfId, properties))
        fun of(event: Event, properties: Properties) = of(event.platform, event.selfId, properties)
    }
}

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
        fun of(sendMessage: SendMessage) = GuildMemberApi(sendMessage)

        fun of(platform: String, selfId: String, properties: Properties) = of(SendMessage.of(platform, selfId, properties))
        fun of(event: Event, properties: Properties) = of(event.platform, event.selfId, properties)
    }
}

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
        fun of(sendMessage: SendMessage) = GuildRoleApi(sendMessage)

        fun of(platform: String, selfId: String, properties: Properties) = of(SendMessage.of(platform, selfId, properties))
        fun of(event: Event, properties: Properties) = of(event.platform, event.selfId, properties)
    }
}

class LoginApi private constructor(private val sendMessage: SendMessage) {
    fun getLogin(): Login {
        val response = sendMessage.sendGenericMessage("login", "get", null)
        return response.parseObject<Login>()
    }

    companion object {
        fun of(sendMessage: SendMessage) = LoginApi(sendMessage)

        fun of(properties: Properties) = of(SendMessage.of(null, null, properties))
    }
}

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
        fun of(sendMessage: SendMessage) = MessageApi(sendMessage)

        fun of(platform: String, selfId: String, properties: Properties) = of(SendMessage.of(platform, selfId, properties))
        fun of(event: Event, properties: Properties) = of(event.platform, event.selfId, properties)
    }
}

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

    fun listReaction(channelId: String, messageId: String, emoji: String, next: String? = null): List<PageResponse<User>> {
        val map = JSONObject()
        map["channel_id"] = channelId
        map["message_id"] = messageId
        map["emoji"] = emoji
        map["next"] = next
        val response = sendMessage.sendGenericMessage("reaction", "list", map.toString())
        return response.parseArray<PageResponse<User>>()
    }

    companion object {
        fun of(sendMessage: SendMessage) = ReactionApi(sendMessage)

        fun of(platform: String, selfId: String, properties: Properties) = of(SendMessage.of(platform, selfId, properties))
        fun of(event: Event, properties: Properties) = of(event.platform, event.selfId, properties)
    }
}

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
        fun of(sendMessage: SendMessage) = UserApi(sendMessage)

        fun of(platform: String, selfId: String, properties: Properties) = of(SendMessage.of(platform, selfId, properties))
        fun of(event: Event, properties: Properties) = of(event.platform, event.selfId, properties)
    }
}

class SendMessage private constructor(
    private val platform: String?,
    private val selfId: String?,
    private val properties: Properties
) {
    private val version = "v1"

    fun sendGenericMessage(resource: String, method: String, body: String?): String {
        val request = Request.post("http://${properties.address}/$version/$resource.$method")
        request initHttpPost body
        return this send request
    }

    fun sendInternalMessage(method: String, body: String): String {
        val request = Request.post("http://${properties.address}/$version/internal/$method")
        request initHttpPost body
        return this send request
    }

    private infix fun Request.initHttpPost(body: String?) {
        this.body(StringEntity(body, StandardCharsets.UTF_8))
        this.setHeader("Content-Type", "application/json")
        this.setHeader("Authorization", "Bearer ${properties.token}")
        this.setHeader("X-Platform", platform)
        this.setHeader("X-Self-ID", selfId)
    }

    private infix fun send(request: Request): String {
        return request.execute().returnContent().asString()
    }

    companion object {
        fun of(platform: String? = null, selfId: String? = null, properties: Properties) = SendMessage(platform, selfId, properties)
    }
}