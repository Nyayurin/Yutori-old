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
import org.apache.hc.client5.http.fluent.Request
import org.apache.hc.core5.http.io.entity.StringEntity
import java.nio.charset.StandardCharsets

/**
 * 封装所有 Action, 应通过本类对 Satori Server 发送事件
 * @property channel 频道 API
 * @property guild 群组 API
 * @property login 登录信息 API
 * @property message 消息 API
 * @property reaction 表态 API
 * @property user 用户 API
 * @property friend 好友 API
 */
class Bot private constructor(
    @JvmField val channel: ChannelApi,
    @JvmField val guild: GuildApi,
    @JvmField val login: LoginApi,
    @JvmField val message: MessageApi,
    @JvmField val reaction: ReactionApi,
    @JvmField val user: UserApi,
    @JvmField val friend: FriendApi
) {
    companion object {
        /**
         * 工厂方法
         * @param platform 平台
         * @param selfId 自身 ID
         * @param properties 配置
         * @return Bot
         */
        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) = Bot(
            ChannelApi.of(platform, selfId, properties), GuildApi.of(platform, selfId, properties),
            LoginApi.of(platform, selfId, properties), MessageApi.of(platform, selfId, properties),
            ReactionApi.of(platform, selfId, properties), UserApi.of(platform, selfId, properties),
            FriendApi.of(platform, selfId, properties)
        )

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

class ChannelApi private constructor(private val sendMessage: SendMessage) {
    fun get(channelId: String): Channel {
        return sendMessage.send("get") {
            this["channel_id"] = channelId
        }.parseObject<Channel>()
    }

    fun list(guildId: String, next: String? = null): List<PageResponse<Channel>> {
        return sendMessage.send("list") {
            this["guild_id"] = guildId
            this["next"] = next
        }.parseArray<PageResponse<Channel>>()
    }

    fun create(guildId: String, data: Channel): Channel {
        return sendMessage.send("create") {
            this["guild_id"] = guildId
            this["data"] = data
        }.parseObject<Channel>()
    }

    fun update(channelId: String, data: Channel) {
        sendMessage.send("update") {
            this["channel_id"] = channelId
            this["data"] = data
        }
    }

    fun delete(channelId: String) {
        sendMessage.send("delete") {
            this["channel_id"] = channelId
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            ChannelApi(SendMessage(platform, selfId, properties, "channel"))
    }
}

class GuildApi private constructor(
    private val sendMessage: SendMessage,
    @JvmField val member: Member,
    @JvmField val role: Role
) {
    fun get(guildId: String): Guild {
        return sendMessage.send("get") {
            this["guild_id"] = guildId
        }.parseObject<Guild>()
    }

    fun list(next: String? = null): List<PageResponse<Guild>> {
        return sendMessage.send("list") {
            this["next"] = next
        }.parseArray<PageResponse<Guild>>()
    }

    fun approve(messageId: String, approve: Boolean, comment: String) {
        sendMessage.send("approve") {
            this["message_id"] = messageId
            this["approve"] = approve
            this["comment"] = comment
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) = GuildApi(
            SendMessage(platform, selfId, properties, "guild"), Member.of(platform, selfId, properties),
            Role.of(platform, selfId, properties)
        )
    }

    class Member private constructor(private val sendMessage: SendMessage, @JvmField val role: Role) {
        fun get(guildId: String, userId: String): GuildMember {
            return sendMessage.send("get") {
                this["guild_id"] = guildId
                this["user_id"] = userId
            }.parseObject<GuildMember>()
        }

        fun list(guildId: String, next: String? = null): List<PageResponse<GuildMember>> {
            return sendMessage.send("list") {
                this["guild_id"] = guildId
                this["next"] = next
            }.parseArray<PageResponse<GuildMember>>()
        }

        fun kick(guildId: String, userId: String, permanent: Boolean? = null) {
            sendMessage.send("kick") {
                this["guild_id"] = guildId
                this["user_id"] = userId
                this["permanent"] = permanent
            }
        }

        fun approve(messageId: String, approve: Boolean, comment: String? = null) {
            sendMessage.send("approve") {
                this["message_id"] = messageId
                this["approve"] = approve
                this["comment"] = comment
            }
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) = Member(
                SendMessage(platform, selfId, properties, "guild.member"), Role.of(platform, selfId, properties)
            )
        }

        class Role private constructor(private val sendMessage: SendMessage) {
            fun set(guildId: String, userId: String, roleId: String) {
                sendMessage.send("set") {
                    this["guild_id"] = guildId
                    this["user_id"] = userId
                    this["role_id"] = roleId
                }
            }

            fun unset(guildId: String, userId: String, roleId: String) {
                sendMessage.send("unset") {
                    this["guild_id"] = guildId
                    this["user_id"] = userId
                    this["role_id"] = roleId
                }
            }

            companion object {
                fun of(platform: String, selfId: String, properties: SatoriProperties) =
                    Role(SendMessage(platform, selfId, properties, "guild.member.role"))
            }
        }
    }

    class Role private constructor(private val sendMessage: SendMessage) {
        fun list(guildId: String, next: String? = null): List<PageResponse<GuildRole>> {
            return sendMessage.send("list") {
                this["guild_id"] = guildId
                this["next"] = next
            }.parseArray<PageResponse<GuildRole>>()
        }

        fun create(guildId: String, role: GuildRole): GuildRole {
            return sendMessage.send("create") {
                this["guild_id"] = guildId
                this["role"] = role
            }.parseObject<GuildRole>()
        }

        fun update(guildId: String, roleId: String, role: GuildRole) {
            sendMessage.send("update") {
                this["guild_id"] = guildId
                this["role_id"] = roleId
                this["role"] = role
            }
        }

        fun delete(guildId: String, roleId: String) {
            sendMessage.send("delete") {
                this["guild_id"] = guildId
                this["role_id"] = roleId
            }
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) =
                Role(SendMessage(platform, selfId, properties, "guild.role"))
        }
    }
}

class LoginApi private constructor(private val sendMessage: SendMessage) {
    fun get(): Login = sendMessage.send("get").parseObject<Login>()

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            LoginApi(SendMessage(platform, selfId, properties, "login"))
    }
}

class MessageApi private constructor(private val sendMessage: SendMessage) {
    fun create(channelId: String, content: String): List<Message> {
        return sendMessage.send("create") {
            this["channel_id"] = channelId
            this["content"] = content
        }.parseArray<Message>()
    }

    @JvmSynthetic
    inline fun create(channelId: String, dsl: MessageDSLBuilder.() -> Unit) =
        create(channelId, MessageDSLBuilder().apply(dsl).build())

    fun get(channelId: String, messageId: String): Message {
        return sendMessage.send("get") {
            this["channel_id"] = channelId
            this["message_id"] = messageId
        }.parseObject<Message>()
    }

    fun delete(channelId: String, messageId: String) {
        sendMessage.send("delete") {
            this["channel_id"] = channelId
            this["message_id"] = messageId
        }
    }

    fun update(channelId: String, messageId: String, content: String) {
        sendMessage.send("update") {
            this["channel_id"] = channelId
            this["message_id"] = messageId
            this["content"] = content
        }
    }

    @JvmSynthetic
    inline fun update(channelId: String, messageId: String, dsl: MessageDSLBuilder.() -> Unit) =
        update(channelId, messageId, MessageDSLBuilder().apply(dsl).build())

    fun list(channelId: String, next: String? = null): List<PageResponse<Message>> {
        return sendMessage.send("list") {
            this["channel_id"] = channelId
            this["next"] = next
        }.parseArray<PageResponse<Message>>()
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            MessageApi(SendMessage(platform, selfId, properties, "message"))
    }
}

class ReactionApi private constructor(private val sendMessage: SendMessage) {
    fun create(channelId: String, messageId: String, emoji: String) {
        sendMessage.send("create") {
            this["channel_id"] = channelId
            this["message_id"] = messageId
            this["emoji"] = emoji
        }
    }

    fun delete(channelId: String, messageId: String, emoji: String, userId: String? = null) {
        sendMessage.send("delete") {
            this["channel_id"] = channelId
            this["message_id"] = messageId
            this["emoji"] = emoji
            this["user_id"] = userId
        }
    }

    fun clear(channelId: String, messageId: String, emoji: String? = null) {
        sendMessage.send("clear") {
            this["channel_id"] = channelId
            this["message_id"] = messageId
            this["emoji"] = emoji
        }
    }

    fun list(channelId: String, messageId: String, emoji: String, next: String? = null): List<PageResponse<User>> {
        return sendMessage.send("list") {
            this["channel_id"] = channelId
            this["message_id"] = messageId
            this["emoji"] = emoji
            this["next"] = next
        }.parseArray<PageResponse<User>>()
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            ReactionApi(SendMessage(platform, selfId, properties, "reaction"))
    }
}

class UserApi private constructor(private val sendMessage: SendMessage, @JvmField val channel: Channel) {
    fun get(userId: String): User {
        return sendMessage.send("get") {
            this["user_id"] = userId
        }.parseObject<User>()
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) = UserApi(
            SendMessage(platform, selfId, properties, "user"), Channel.of(platform, selfId, properties)
        )
    }

    class Channel private constructor(private val sendMessage: SendMessage) {
        fun create(userId: String, guildId: String?): io.github.nyayurn.yutori.Channel {
            return sendMessage.send("create") {
                this["user_id"] = userId
                this["guild_id"] = guildId
            }.parseObject<io.github.nyayurn.yutori.Channel>()
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) =
                Channel(SendMessage(platform, selfId, properties, "user.channel"))
        }
    }
}

class FriendApi private constructor(private val sendMessage: SendMessage) {
    fun list(next: String? = null): List<PageResponse<User>> {
        return sendMessage.send("list") {
            this["next"] = next
        }.parseArray<PageResponse<User>>()
    }

    fun approve(messageId: String, approve: Boolean, comment: String? = null) {
        sendMessage.send("approve") {
            this["message_id"] = messageId
            this["approve"] = approve
            this["comment"] = comment
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            FriendApi(SendMessage(platform, selfId, properties, "friend"))
    }
}

/**
 * 实现 HTTP 交互的底层类
 * @property platform 平台
 * @property selfId 自身的 ID
 * @property properties 配置
 * @property resource 资源路径
 */
class SendMessage @JvmOverloads constructor(
    private val platform: String? = null,
    private val selfId: String? = null,
    private val properties: SatoriProperties,
    private val resource: String
) {
    fun send(method: String, body: String? = null): String {
        return Request.post("http://${properties.address}/${properties.version}/$resource.$method").apply {
            body?.let { body(StringEntity(it, StandardCharsets.UTF_8)) }
            setHeader("Content-Type", "application/json")
            properties.token?.let { setHeader("Authorization", "Bearer $it") }
            setHeader("X-Platform", platform)
            setHeader("X-Self-ID", selfId)
        }.execute().returnContent().asString()
    }

    @JvmSynthetic
    inline fun send(method: String, dsl: JSONObject.() -> Unit) = send(method, JSONObject().apply(dsl).toString())
}