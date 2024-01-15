/*
Copyright (c) 2023 Yurn
Yutori is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */

@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.nyayurn.yutori

import com.alibaba.fastjson2.parseArray
import com.alibaba.fastjson2.parseObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 封装所有 Action, 应通过本类对 Satori Server 发送事件
 * @property channel 频道 API
 * @property guild 群组 API
 * @property login 登录信息 API
 * @property message 消息 API
 * @property reaction 表态 API
 * @property user 用户 API
 * @property friend 好友 API
 * @property properties 配置信息, 供使用者获取
 */
class Bot private constructor(
    @JvmField val channel: ChannelResource,
    @JvmField val guild: GuildResource,
    @JvmField val login: LoginResource,
    @JvmField val message: MessageResource,
    @JvmField val reaction: ReactionResource,
    @JvmField val user: UserResource,
    @JvmField val friend: FriendResource,
    val properties: SatoriProperties
) {
    companion object {
        /**
         * 工厂方法
         * @param platform 平台
         * @param selfId 自己 ID
         * @param properties 配置
         * @param scope 协程作用域
         * @return Bot 实例
         */
        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            Bot(
                ChannelResource.of(platform, selfId, properties, scope, logger),
                GuildResource.of(platform, selfId, properties, scope, logger),
                LoginResource.of(platform, selfId, properties, scope, logger),
                MessageResource.of(platform, selfId, properties, scope, logger),
                ReactionResource.of(platform, selfId, properties, scope, logger),
                UserResource.of(platform, selfId, properties, scope, logger),
                FriendResource.of(platform, selfId, properties, scope, logger),
                properties
            )

        /**
         * 工厂方法
         * @param event 事件
         * @param properties 配置
         * @param scope 协程作用域
         * @return Bot 实例
         */
        @JvmStatic
        fun of(event: Event, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            of(event.platform, event.selfId, properties, scope, logger)
    }
}

class ChannelResource private constructor(
    private val satoriAction: SatoriAction,
    private val coroutineScope: CoroutineScope
) {
    /**
     * 获取群组频道
     * @param channelId 频道 ID
     * @param callback 回调
     */
    @JvmOverloads
    fun get(channelId: String, callback: (Channel) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("get") {
                put("channel_id", channelId)
            }.parseObject<Channel>())
        }
    }

    /**
     * 获取群组频道列表
     * @param guildId 群组 ID
     * @param next 分页令牌
     * @param callback 回调
     */
    @JvmOverloads
    fun list(guildId: String, next: String? = null, callback: (List<PaginatedData<Channel>>) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("list") {
                put("guild_id", guildId)
                put("next", next)
            }.parseArray<PaginatedData<Channel>>())
        }
    }

    /**
     * 创建群组频道
     * @param guildId 群组 ID
     * @param data 频道数据
     * @param callback 回调
     */
    @JvmOverloads
    fun create(guildId: String, data: Channel, callback: (Channel) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("create") {
                put("guild_id", guildId)
                put("data", data)
            }.parseObject<Channel>())
        }
    }

    /**
     * 修改群组频道
     * @param channelId 频道 ID
     * @param data 频道数据
     * @param callback 回调
     */
    @JvmOverloads
    fun update(channelId: String, data: Channel, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("update") {
                put("channel_id", channelId)
                put("data", data)
            }
            callback()
        }
    }

    /**
     * 删除群组频道
     * @param channelId 频道 ID
     * @param callback 回调
     */
    @JvmOverloads
    fun delete(channelId: String, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("delete") {
                put("channel_id", channelId)
            }
            callback()
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            ChannelResource(SatoriAction(platform, selfId, properties, scope, "channel", logger), scope)
    }
}

class GuildResource private constructor(
    @JvmField val member: MemberResource,
    @JvmField val role: RoleResource,
    private val satoriAction: SatoriAction,
    private val coroutineScope: CoroutineScope
) {
    /**
     * 获取群组
     * @param guildId 群组 ID
     * @param callback 回调
     */
    fun get(guildId: String, callback: (Guild) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("get") {
                put("guild_id", guildId)
            }.parseObject<Guild>())
        }
    }

    /**
     * 获取群组列表
     * @param next 分页令牌
     * @param callback 回调
     */
    @JvmOverloads
    fun list(next: String? = null, callback: (List<PaginatedData<Guild>>) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("list") {
                put("next", next)
            }.parseArray<PaginatedData<Guild>>())
        }
    }

    /**
     * 处理群组邀请
     * @param messageId 请求 ID
     * @param approve 是否通过请求
     * @param comment 备注信息
     * @param callback 回调
     */
    @JvmOverloads
    fun approve(messageId: String, approve: Boolean, comment: String, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("approve") {
                put("message_id", messageId)
                put("approve", approve)
                put("comment", comment)
            }
            callback()
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            GuildResource(
                MemberResource.of(platform, selfId, properties, scope, logger),
                RoleResource.of(platform, selfId, properties, scope, logger),
                SatoriAction(platform, selfId, properties, scope, "guild", logger),
                scope
            )
    }

    class MemberResource private constructor(
        @JvmField val role: RoleResource,
        private val satoriAction: SatoriAction,
        private val coroutineScope: CoroutineScope
    ) {
        /**
         * 获取群组成员
         * @param guildId 群组 ID
         * @param userId 用户 ID
         * @param callback 回调
         */
        fun get(guildId: String, userId: String, callback: (GuildMember) -> Unit = {}) {
            coroutineScope.launch {
                callback(satoriAction.send("get") {
                    put("guild_id", guildId)
                    put("user_id", userId)
                }.parseObject<GuildMember>())
            }
        }

        /**
         * 获取群组成员列表
         * @param guildId 群组 ID
         * @param next 分页令牌
         * @param callback 回调
         */
        @JvmOverloads
        fun list(guildId: String, next: String? = null, callback: (List<PaginatedData<GuildMember>>) -> Unit = {}) {
            coroutineScope.launch {
                callback(satoriAction.send("list") {
                    put("guild_id", guildId)
                    put("next", next)
                }.parseArray<PaginatedData<GuildMember>>())
            }
        }

        /**
         * 踢出群组成员
         * @param guildId 群组 ID
         * @param userId 用户 ID
         * @param permanent 是否永久踢出 (无法再次加入群组)
         * @param callback 回调
         */
        @JvmOverloads
        fun kick(guildId: String, userId: String, permanent: Boolean? = null, callback: () -> Unit = {}) {
            coroutineScope.launch {
                satoriAction.send("kick") {
                    put("guild_id", guildId)
                    put("user_id", userId)
                    put("permanent", permanent)
                }
                callback()
            }
        }

        /**
         * 通过群组成员申请
         * @param messageId 请求 ID
         * @param approve 是否通过请求
         * @param comment 备注信息
         * @param callback 回调
         */
        @JvmOverloads
        fun approve(messageId: String, approve: Boolean, comment: String? = null, callback: () -> Unit = {}) {
            coroutineScope.launch {
                satoriAction.send("approve") {
                    put("message_id", messageId)
                    put("approve", approve)
                    put("comment", comment)
                }
                callback()
            }
        }

        companion object {
            fun of(
                platform: String,
                selfId: String,
                properties: SatoriProperties,
                scope: CoroutineScope,
                logger: Logger
            ) = MemberResource(
                RoleResource.of(platform, selfId, properties, scope, logger),
                SatoriAction(platform, selfId, properties, scope, "guild.member", logger),
                scope
            )
        }

        class RoleResource private constructor(
            private val satoriAction: SatoriAction,
            private val coroutineScope: CoroutineScope
        ) {
            /**
             * 设置群组成员角色
             * @param guildId 群组 ID
             * @param userId 用户 ID
             * @param roleId 角色 ID
             * @param callback 回调
             */
            @JvmOverloads
            fun set(guildId: String, userId: String, roleId: String, callback: () -> Unit = {}) {
                coroutineScope.launch {
                    satoriAction.send("set") {
                        put("guild_id", guildId)
                        put("user_id", userId)
                        put("role_id", roleId)
                    }
                    callback()
                }
            }

            /**
             * 取消群组成员角色
             * @param guildId 群组 ID
             * @param userId 用户 ID
             * @param roleId 角色 ID
             * @param callback 回调
             */
            @JvmOverloads
            fun unset(guildId: String, userId: String, roleId: String, callback: () -> Unit = {}) {
                coroutineScope.launch {
                    satoriAction.send("unset") {
                        put("guild_id", guildId)
                        put("user_id", userId)
                        put("role_id", roleId)
                    }
                    callback()
                }
            }

            companion object {
                fun of(
                    platform: String,
                    selfId: String,
                    properties: SatoriProperties,
                    scope: CoroutineScope,
                    logger: Logger
                ) = RoleResource(SatoriAction(platform, selfId, properties, scope, "guild.member.role", logger), scope)
            }
        }
    }

    class RoleResource private constructor(
        private val satoriAction: SatoriAction,
        private val coroutineScope: CoroutineScope
    ) {
        /**
         * 获取群组角色列表
         * @param guildId 群组 ID
         * @param next 分页令牌
         * @param callback 回调
         */
        @JvmOverloads
        fun list(guildId: String, next: String? = null, callback: (List<PaginatedData<GuildRole>>) -> Unit = {}) {
            coroutineScope.launch {
                callback(satoriAction.send("list") {
                    put("guild_id", guildId)
                    put("next", next)
                }.parseArray<PaginatedData<GuildRole>>())
            }
        }

        /**
         * 创建群组角色
         * @param guildId 群组 ID
         * @param role 角色数据
         * @param callback 回调
         */
        @JvmOverloads
        fun create(guildId: String, role: GuildRole, callback: (GuildRole) -> Unit = {}) {
            coroutineScope.launch {
                callback(satoriAction.send("create") {
                    put("guild_id", guildId)
                    put("role", role)
                }.parseObject<GuildRole>())
            }
        }

        /**
         * 修改群组角色
         * @param guildId 群组 ID
         * @param roleId 角色 ID
         * @param role 角色数据
         * @param callback 回调
         */
        @JvmOverloads
        fun update(guildId: String, roleId: String, role: GuildRole, callback: () -> Unit = {}) {
            coroutineScope.launch {
                satoriAction.send("update") {
                    put("guild_id", guildId)
                    put("role_id", roleId)
                    put("role", role)
                }
                callback()
            }
        }

        /**
         * 删除群组角色
         * @param guildId 群组 ID
         * @param roleId 角色 ID
         * @param callback 回调
         */
        @JvmOverloads
        fun delete(guildId: String, roleId: String, callback: () -> Unit = {}) {
            coroutineScope.launch {
                satoriAction.send("delete") {
                    put("guild_id", guildId)
                    put("role_id", roleId)
                }
                callback()
            }
        }

        companion object {
            fun of(
                platform: String,
                selfId: String,
                properties: SatoriProperties,
                scope: CoroutineScope,
                logger: Logger
            ) = RoleResource(SatoriAction(platform, selfId, properties, scope, "guild.role", logger), scope)
        }
    }
}

class LoginResource private constructor(
    private val satoriAction: SatoriAction,
    private val coroutineScope: CoroutineScope
) {
    /**
     * 获取登录信息
     * @param callback 回调
     */
    @JvmOverloads
    fun get(callback: (Login) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("get").parseObject<Login>())
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            LoginResource(SatoriAction(platform, selfId, properties, scope, "login", logger), scope)
    }
}

class MessageResource private constructor(
    private val satoriAction: SatoriAction,
    private val coroutineScope: CoroutineScope
) {
    /**
     * 发送消息
     * @param channelId 频道 ID
     * @param content 消息内容
     * @param callback 回调
     */
    @JvmOverloads
    fun create(channelId: String, content: String, callback: (List<Message>) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("create") {
                put("channel_id", channelId)
                put("content", content)
            }.parseArray<Message>())
        }
    }

    /**
     * 获取消息
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param callback 回调
     */
    @JvmOverloads
    fun get(channelId: String, messageId: String, callback: (Message) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("get") {
                put("channel_id", channelId)
                put("message_id", messageId)
            }.parseObject<Message>())
        }
    }

    /**
     * 撤回消息
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param callback 回调
     */
    @JvmOverloads
    fun delete(channelId: String, messageId: String, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("delete") {
                put("channel_id", channelId)
                put("message_id", messageId)
            }
            callback()
        }
    }

    /**
     * 编辑消息
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param content 消息内容
     * @param callback 回调
     */
    @JvmOverloads
    fun update(channelId: String, messageId: String, content: String, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("update") {
                put("channel_id", channelId)
                put("message_id", messageId)
                put("content", content)
            }
            callback()
        }
    }

    /**
     * 获取消息列表
     * @param channelId 频道 ID
     * @param next 分页令牌
     * @param callback 回调
     */
    @JvmOverloads
    fun list(channelId: String, next: String? = null, callback: (List<PaginatedData<Message>>) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("list") {
                put("channel_id", channelId)
                put("next", next)
            }.parseArray<PaginatedData<Message>>())
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            MessageResource(SatoriAction(platform, selfId, properties, scope, "message", logger), scope)
    }
}

class ReactionResource private constructor(
    private val satoriAction: SatoriAction,
    private val coroutineScope: CoroutineScope
) {
    /**
     * 添加表态
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji 表态名称
     * @param callback 回调
     */
    @JvmOverloads
    fun create(channelId: String, messageId: String, emoji: String, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("create") {
                put("channel_id", channelId)
                put("message_id", messageId)
                put("emoji", emoji)
            }
            callback()
        }
    }

    /**
     * 删除表态
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji 表态名称
     * @param userId 用户 ID
     * @param callback 回调
     */
    @JvmOverloads
    fun delete(channelId: String, messageId: String, emoji: String, userId: String? = null, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("delete") {
                put("channel_id", channelId)
                put("message_id", messageId)
                put("emoji", emoji)
                put("user_id", userId)
            }
            callback()
        }
    }

    /**
     * 清除表态
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji 表态名称
     * @param callback 回调
     */
    @JvmOverloads
    fun clear(channelId: String, messageId: String, emoji: String? = null, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("clear") {
                put("channel_id", channelId)
                put("message_id", messageId)
                put("emoji", emoji)
            }
            callback()
        }
    }

    /**
     * 获取表态列表
     * @param channelId 频道 ID
     * @param messageId 消息 ID
     * @param emoji 表态名称
     * @param next 分页令牌
     * @param callback 回调
     */
    @JvmOverloads
    fun list(
        channelId: String,
        messageId: String,
        emoji: String,
        next: String? = null,
        callback: (List<PaginatedData<User>>) -> Unit = {}
    ) {
        coroutineScope.launch {
            callback(satoriAction.send("list") {
                put("channel_id", channelId)
                put("message_id", messageId)
                put("emoji", emoji)
                put("next", next)
            }.parseArray<PaginatedData<User>>())
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            ReactionResource(SatoriAction(platform, selfId, properties, scope, "reaction", logger), scope)
    }
}

class UserResource private constructor(
    @JvmField val channel: ChannelResource,
    private val satoriAction: SatoriAction,
    private val coroutineScope: CoroutineScope
) {
    /**
     * 获取用户信息
     * @param userId 用户 ID
     * @param callback 回调
     */
    @JvmOverloads
    fun get(userId: String, callback: (User) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("get") {
                put("user_id", userId)
            }.parseObject<User>())
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            UserResource(
                ChannelResource.of(platform, selfId, properties, scope, logger),
                SatoriAction(platform, selfId, properties, scope, "user", logger),
                scope
            )
    }

    class ChannelResource private constructor(
        private val satoriAction: SatoriAction,
        private val coroutineScope: CoroutineScope
    ) {
        /**
         * 创建私聊频道
         * @param userId 用户 ID
         * @param guildId 群组 ID
         * @param callback 回调
         */
        @JvmOverloads
        fun create(userId: String, guildId: String? = null, callback: (Channel) -> Unit = {}) {
            coroutineScope.launch {
                callback(satoriAction.send("create") {
                    put("user_id", userId)
                    put("guild_id", guildId)
                }.parseObject<Channel>())
            }
        }

        companion object {
            fun of(
                platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger
            ) = ChannelResource(SatoriAction(platform, selfId, properties, scope, "user.channel", logger), scope)
        }
    }
}

class FriendResource private constructor(
    private val satoriAction: SatoriAction,
    private val coroutineScope: CoroutineScope
) {
    /**
     * 获取好友列表
     * @param next 分页令牌
     * @param callback 回调
     */
    @JvmOverloads
    fun list(next: String? = null, callback: (List<PaginatedData<User>>) -> Unit = {}) {
        coroutineScope.launch {
            callback(satoriAction.send("list") {
                put("next", next)
            }.parseArray<PaginatedData<User>>())
        }
    }

    /**
     * 处理好友申请
     * @param messageId 请求 ID
     * @param approve 是否通过请求
     * @param comment 备注信息
     * @param callback 回调
     */
    @JvmOverloads
    fun approve(messageId: String, approve: Boolean, comment: String? = null, callback: () -> Unit = {}) {
        coroutineScope.launch {
            satoriAction.send("approve") {
                put("message_id", messageId)
                put("approve", approve)
                put("comment", comment)
            }
            callback()
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties, scope: CoroutineScope, logger: Logger) =
            FriendResource(SatoriAction(platform, selfId, properties, scope, "friend", logger), scope)
    }
}

/**
 * Satori Action 实现
 * @property platform 平台
 * @property selfId 自身的 ID
 * @property properties 配置
 * @property coroutineScope 协程作用域
 * @property resource 资源路径
 */
class SatoriAction @JvmOverloads constructor(
    private val platform: String? = null,
    private val selfId: String? = null,
    private val properties: SatoriProperties,
    private val coroutineScope: CoroutineScope,
    private val resource: String,
    private val logger: Logger
) {
    suspend fun send(method: String, body: String? = null): String {
        HttpClient(CIO).use { client ->
            val response = client.post {
                url {
                    host = properties.host
                    port = properties.port
                    appendPathSegments(properties.path, properties.version, "$resource.$method")
                }
                contentType(ContentType.Application.Json)
                headers {
                    properties.token?.let { append(HttpHeaders.Authorization, "Bearer $it") }
                    platform?.let { append("X-Platform", it) }
                    selfId?.let { append("X-Self-ID", selfId) }
                }
                body?.let { setBody(it) }
                logger.debug(
                """
                Satori Action: url: ${this.url},
                    headers: ${this.headers.build()},
                    body: ${this.body}
                """.trimIndent(), this::class.java
                )
            }
            logger.debug("Satori Action Response: $response", this::class.java)
            return response.body()
        }
    }

    @JvmSynthetic
    suspend inline fun send(method: String, dsl: JsonObjectDSLBuilder.() -> Unit) =
        send(method, JsonObjectDSLBuilder().apply(dsl).build().toString())
}