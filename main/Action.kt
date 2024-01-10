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

@file:Suppress("unused")

package io.github.nyayurn.yutori

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
    @JvmField val channel: ChannelResource,
    @JvmField val guild: GuildResource,
    @JvmField val login: LoginResource,
    @JvmField val message: MessageResource,
    @JvmField val reaction: ReactionResource,
    @JvmField val user: UserResource,
    @JvmField val friend: FriendResource
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
            ChannelResource.of(platform, selfId, properties),
            GuildResource.of(platform, selfId, properties),
            LoginResource.of(platform, selfId, properties),
            MessageResource.of(platform, selfId, properties),
            ReactionResource.of(platform, selfId, properties),
            UserResource.of(platform, selfId, properties),
            FriendResource.of(platform, selfId, properties)
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

class ChannelResource private constructor(private val satoriAction: SatoriAction) {
    fun get(channelId: String): Channel {
        return satoriAction.send("get") {
            put("channel_id", channelId)
        }.parseObject<Channel>()
    }

    fun list(guildId: String, next: String? = null): List<PaginatedData<Channel>> {
        return satoriAction.send("list") {
            put("guild_id", guildId)
            put("next", next)
        }.parseArray<PaginatedData<Channel>>()
    }

    fun create(guildId: String, data: Channel): Channel {
        return satoriAction.send("create") {
            put("guild_id", guildId)
            put("data", data)
        }.parseObject<Channel>()
    }

    fun update(channelId: String, data: Channel) {
        satoriAction.send("update") {
            put("channel_id", channelId)
            put("data", data)
        }
    }

    fun delete(channelId: String) {
        satoriAction.send("delete") {
            put("channel_id", channelId)
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            ChannelResource(SatoriAction(platform, selfId, properties, "channel"))
    }
}

class GuildResource private constructor(
    private val satoriAction: SatoriAction,
    @JvmField val member: MemberResource,
    @JvmField val role: RoleResource
) {
    fun get(guildId: String): Guild {
        return satoriAction.send("get") {
            put("guild_id", guildId)
        }.parseObject<Guild>()
    }

    fun list(next: String? = null): List<PaginatedData<Guild>> {
        return satoriAction.send("list") {
            put("next", next)
        }.parseArray<PaginatedData<Guild>>()
    }

    fun approve(messageId: String, approve: Boolean, comment: String) {
        satoriAction.send("approve") {
            put("message_id", messageId)
            put("approve", approve)
            put("comment", comment)
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) = GuildResource(
            SatoriAction(platform, selfId, properties, "guild"), MemberResource.of(platform, selfId, properties),
            RoleResource.of(platform, selfId, properties)
        )
    }

    class MemberResource private constructor(private val satoriAction: SatoriAction, @JvmField val role: RoleResource) {
        fun get(guildId: String, userId: String): GuildMember {
            return satoriAction.send("get") {
                put("guild_id", guildId)
                put("user_id", userId)
            }.parseObject<GuildMember>()
        }

        fun list(guildId: String, next: String? = null): List<PaginatedData<GuildMember>> {
            return satoriAction.send("list") {
                put("guild_id", guildId)
                put("next", next)
            }.parseArray<PaginatedData<GuildMember>>()
        }

        fun kick(guildId: String, userId: String, permanent: Boolean? = null) {
            satoriAction.send("kick") {
                put("guild_id", guildId)
                put("user_id", userId)
                put("permanent", permanent)
            }
        }

        fun approve(messageId: String, approve: Boolean, comment: String? = null) {
            satoriAction.send("approve") {
                put("message_id", messageId)
                put("approve", approve)
                put("comment", comment)
            }
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) = MemberResource(
                SatoriAction(platform, selfId, properties, "guild.member"),
                RoleResource.of(platform, selfId, properties)
            )
        }

        class RoleResource private constructor(private val satoriAction: SatoriAction) {
            fun set(guildId: String, userId: String, roleId: String) {
                satoriAction.send("set") {
                    put("guild_id", guildId)
                    put("user_id", userId)
                    put("role_id", roleId)
                }
            }

            fun unset(guildId: String, userId: String, roleId: String) {
                satoriAction.send("unset") {
                    put("guild_id", guildId)
                    put("user_id", userId)
                    put("role_id", roleId)
                }
            }

            companion object {
                fun of(platform: String, selfId: String, properties: SatoriProperties) =
                    RoleResource(SatoriAction(platform, selfId, properties, "guild.member.role"))
            }
        }
    }

    class RoleResource private constructor(private val satoriAction: SatoriAction) {
        fun list(guildId: String, next: String? = null): List<PaginatedData<GuildRole>> {
            return satoriAction.send("list") {
                put("guild_id", guildId)
                put("next", next)
            }.parseArray<PaginatedData<GuildRole>>()
        }

        fun create(guildId: String, role: GuildRole): GuildRole {
            return satoriAction.send("create") {
                put("guild_id", guildId)
                put("role", role)
            }.parseObject<GuildRole>()
        }

        fun update(guildId: String, roleId: String, role: GuildRole) {
            satoriAction.send("update") {
                put("guild_id", guildId)
                put("role_id", roleId)
                put("role", role)
            }
        }

        fun delete(guildId: String, roleId: String) {
            satoriAction.send("delete") {
                put("guild_id", guildId)
                put("role_id", roleId)
            }
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) =
                RoleResource(SatoriAction(platform, selfId, properties, "guild.role"))
        }
    }
}

class LoginResource private constructor(private val satoriAction: SatoriAction) {
    fun get(): Login = satoriAction.send("get").parseObject<Login>()

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            LoginResource(SatoriAction(platform, selfId, properties, "login"))
    }
}

class MessageResource private constructor(private val satoriAction: SatoriAction) {
    fun create(channelId: String, content: String): List<Message> {
        return satoriAction.send("create") {
            put("channel_id", channelId)
            put("content", content)
        }.parseArray<Message>()
    }

    @JvmSynthetic
    inline fun create(channelId: String, dsl: MessageDSLBuilder.() -> Unit) =
        create(channelId, MessageDSLBuilder().apply(dsl).build())

    fun get(channelId: String, messageId: String): Message {
        return satoriAction.send("get") {
            put("channel_id", channelId)
            put("message_id", messageId)
        }.parseObject<Message>()
    }

    fun delete(channelId: String, messageId: String) {
        satoriAction.send("delete") {
            put("channel_id", channelId)
            put("message_id", messageId)
        }
    }

    fun update(channelId: String, messageId: String, content: String) {
        satoriAction.send("update") {
            put("channel_id", channelId)
            put("message_id", messageId)
            put("content", content)
        }
    }

    @JvmSynthetic
    inline fun update(channelId: String, messageId: String, dsl: MessageDSLBuilder.() -> Unit) =
        update(channelId, messageId, MessageDSLBuilder().apply(dsl).build())

    fun list(channelId: String, next: String? = null): List<PaginatedData<Message>> {
        return satoriAction.send("list") {
            put("channel_id", channelId)
            put("next", next)
        }.parseArray<PaginatedData<Message>>()
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            MessageResource(SatoriAction(platform, selfId, properties, "message"))
    }
}

class ReactionResource private constructor(private val satoriAction: SatoriAction) {
    fun create(channelId: String, messageId: String, emoji: String) {
        satoriAction.send("create") {
            put("channel_id", channelId)
            put("message_id", messageId)
            put("emoji", emoji)
        }
    }

    fun delete(channelId: String, messageId: String, emoji: String, userId: String? = null) {
        satoriAction.send("delete") {
            put("channel_id", channelId)
            put("message_id", messageId)
            put("emoji", emoji)
            put("user_id", userId)
        }
    }

    fun clear(channelId: String, messageId: String, emoji: String? = null) {
        satoriAction.send("clear") {
            put("channel_id", channelId)
            put("message_id", messageId)
            put("emoji", emoji)
        }
    }

    fun list(channelId: String, messageId: String, emoji: String, next: String? = null): List<PaginatedData<User>> {
        return satoriAction.send("list") {
            put("channel_id", channelId)
            put("message_id", messageId)
            put("emoji", emoji)
            put("next", next)
        }.parseArray<PaginatedData<User>>()
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            ReactionResource(SatoriAction(platform, selfId, properties, "reaction"))
    }
}

class UserResource private constructor(private val satoriAction: SatoriAction, @JvmField val channel: ChannelResource) {
    fun get(userId: String): User {
        return satoriAction.send("get") {
            put("user_id", userId)
        }.parseObject<User>()
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) = UserResource(
            SatoriAction(platform, selfId, properties, "user"), ChannelResource.of(platform, selfId, properties)
        )
    }

    class ChannelResource private constructor(private val satoriAction: SatoriAction) {
        fun create(userId: String, guildId: String?): Channel {
            return satoriAction.send("create") {
                put("user_id", userId)
                put("guild_id", guildId)
            }.parseObject<Channel>()
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) =
                ChannelResource(SatoriAction(platform, selfId, properties, "user.channel"))
        }
    }
}

class FriendResource private constructor(private val satoriAction: SatoriAction) {
    fun list(next: String? = null): List<PaginatedData<User>> {
        return satoriAction.send("list") {
            put("next", next)
        }.parseArray<PaginatedData<User>>()
    }

    fun approve(messageId: String, approve: Boolean, comment: String? = null) {
        satoriAction.send("approve") {
            put("message_id", messageId)
            put("approve", approve)
            put("comment", comment)
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            FriendResource(SatoriAction(platform, selfId, properties, "friend"))
    }
}

/**
 * Satori Action 实现
 * @property platform 平台
 * @property selfId 自身的 ID
 * @property properties 配置
 * @property resource 资源路径
 */
class SatoriAction @JvmOverloads constructor(
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
    inline fun send(method: String, dsl: JsonObjectDSLBuilder.() -> Unit) =
        send(method, JsonObjectDSLBuilder().apply(dsl).toString())
}