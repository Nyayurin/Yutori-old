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
         * @return Bot 实例
         */
        @JvmStatic
        fun of(platform: String, selfId: String, properties: SatoriProperties) = Bot(
            ChannelResource.of(platform, selfId, properties),
            GuildResource.of(platform, selfId, properties),
            LoginResource.of(platform, selfId, properties),
            MessageResource.of(platform, selfId, properties),
            ReactionResource.of(platform, selfId, properties),
            UserResource.of(platform, selfId, properties),
            FriendResource.of(platform, selfId, properties),
            properties
        )

        /**
         * 工厂方法
         * @param event 事件
         * @param properties 配置
         * @return Bot 实例
         */
        @JvmStatic
        fun of(event: Event, properties: SatoriProperties) = of(event.platform, event.selfId, properties)
    }
}

class ChannelResource private constructor(
    private val satoriAction: SatoriAction,
    private val properties: SatoriProperties
) {
    /**
     * 获取群组频道
     * @param channelId 频道 ID
     * @param callback 回调
     */
    @JvmOverloads
    fun get(channelId: String, callback: (Channel) -> Unit = {}) {
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
            satoriAction.send("delete") {
                put("channel_id", channelId)
            }
            callback()
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            ChannelResource(SatoriAction(platform, selfId, properties, "channel"), properties)
    }
}

class GuildResource private constructor(
    @JvmField val member: MemberResource,
    @JvmField val role: RoleResource,
    private val satoriAction: SatoriAction,
    private val properties: SatoriProperties
) {
    /**
     * 获取群组
     * @param guildId 群组 ID
     * @param callback 回调
     */
    fun get(guildId: String, callback: (Guild) -> Unit = {}) {
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
            satoriAction.send("approve") {
                put("message_id", messageId)
                put("approve", approve)
                put("comment", comment)
            }
            callback()
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) = GuildResource(
            MemberResource.of(platform, selfId, properties),
            RoleResource.of(platform, selfId, properties),
            SatoriAction(platform, selfId, properties, "guild"),
            properties
        )
    }

    class MemberResource private constructor(
        @JvmField val role: RoleResource,
        private val satoriAction: SatoriAction,
        private val properties: SatoriProperties
    ) {
        /**
         * 获取群组成员
         * @param guildId 群组 ID
         * @param userId 用户 ID
         * @param callback 回调
         */
        fun get(guildId: String, userId: String, callback: (GuildMember) -> Unit = {}) {
            properties.executorService.submit {
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
            properties.executorService.submit {
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
            properties.executorService.submit {
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
            properties.executorService.submit {
                satoriAction.send("approve") {
                    put("message_id", messageId)
                    put("approve", approve)
                    put("comment", comment)
                }
                callback()
            }
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) = MemberResource(
                RoleResource.of(platform, selfId, properties),
                SatoriAction(platform, selfId, properties, "guild.member"),
                properties
            )
        }

        class RoleResource private constructor(
            private val satoriAction: SatoriAction,
            private val properties: SatoriProperties
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
                properties.executorService.submit {
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
                properties.executorService.submit {
                    satoriAction.send("unset") {
                        put("guild_id", guildId)
                        put("user_id", userId)
                        put("role_id", roleId)
                    }
                    callback()
                }
            }

            companion object {
                fun of(platform: String, selfId: String, properties: SatoriProperties) =
                    RoleResource(SatoriAction(platform, selfId, properties, "guild.member.role"), properties)
            }
        }
    }

    class RoleResource private constructor(
        private val satoriAction: SatoriAction,
        private val properties: SatoriProperties
    ) {
        /**
         * 获取群组角色列表
         * @param guildId 群组 ID
         * @param next 分页令牌
         * @param callback 回调
         */
        @JvmOverloads
        fun list(guildId: String, next: String? = null, callback: (List<PaginatedData<GuildRole>>) -> Unit = {}) {
            properties.executorService.submit {
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
            properties.executorService.submit {
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
            properties.executorService.submit {
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
            properties.executorService.submit {
                satoriAction.send("delete") {
                    put("guild_id", guildId)
                    put("role_id", roleId)
                }
                callback()
            }
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) =
                RoleResource(SatoriAction(platform, selfId, properties, "guild.role"), properties)
        }
    }
}

class LoginResource private constructor(
    private val satoriAction: SatoriAction,
    private val properties: SatoriProperties
) {
    /**
     * 获取登录信息
     * @param callback 回调
     */
    @JvmOverloads
    fun get(callback: (Login) -> Unit = {}) {
        properties.executorService.submit {
            callback(satoriAction.send("get").parseObject<Login>())
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            LoginResource(SatoriAction(platform, selfId, properties, "login"), properties)
    }
}

class MessageResource private constructor(
    private val satoriAction: SatoriAction,
    private val properties: SatoriProperties
) {
    /**
     * 发送消息
     * @param channelId 频道 ID
     * @param content 消息内容
     * @param callback 回调
     */
    @JvmOverloads
    fun create(channelId: String, content: String, callback: (List<Message>) -> Unit = {}) {
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
            callback(satoriAction.send("list") {
                put("channel_id", channelId)
                put("next", next)
            }.parseArray<PaginatedData<Message>>())
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            MessageResource(SatoriAction(platform, selfId, properties, "message"), properties)
    }
}

class ReactionResource private constructor(
    private val satoriAction: SatoriAction,
    private val properties: SatoriProperties
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
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
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
        properties.executorService.submit {
            callback(satoriAction.send("list") {
                put("channel_id", channelId)
                put("message_id", messageId)
                put("emoji", emoji)
                put("next", next)
            }.parseArray<PaginatedData<User>>())
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            ReactionResource(SatoriAction(platform, selfId, properties, "reaction"), properties)
    }
}

class UserResource private constructor(
    @JvmField val channel: ChannelResource,
    private val satoriAction: SatoriAction,
    private val properties: SatoriProperties
) {
    /**
     * 获取用户信息
     * @param userId 用户 ID
     * @param callback 回调
     */
    @JvmOverloads
    fun get(userId: String, callback: (User) -> Unit = {}) {
        properties.executorService.submit {
            callback(satoriAction.send("get") {
                put("user_id", userId)
            }.parseObject<User>())
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) = UserResource(
            ChannelResource.of(platform, selfId, properties),
            SatoriAction(platform, selfId, properties, "user"),
            properties
        )
    }

    class ChannelResource private constructor(
        private val satoriAction: SatoriAction,
        private val properties: SatoriProperties
    ) {
        /**
         * 创建私聊频道
         * @param userId 用户 ID
         * @param guildId 群组 ID
         * @param callback 回调
         */
        @JvmOverloads
        fun create(userId: String, guildId: String? = null, callback: (Channel) -> Unit = {}) {
            properties.executorService.submit {
                callback(satoriAction.send("create") {
                    put("user_id", userId)
                    put("guild_id", guildId)
                }.parseObject<Channel>())
            }
        }

        companion object {
            fun of(platform: String, selfId: String, properties: SatoriProperties) =
                ChannelResource(SatoriAction(platform, selfId, properties, "user.channel"), properties)
        }
    }
}

class FriendResource private constructor(
    private val satoriAction: SatoriAction,
    private val properties: SatoriProperties
) {
    /**
     * 获取好友列表
     * @param next 分页令牌
     * @param callback 回调
     */
    @JvmOverloads
    fun list(next: String? = null, callback: (List<PaginatedData<User>>) -> Unit = {}) {
        properties.executorService.submit {
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
        properties.executorService.submit {
            satoriAction.send("approve") {
                put("message_id", messageId)
                put("approve", approve)
                put("comment", comment)
            }
            callback()
        }
    }

    companion object {
        fun of(platform: String, selfId: String, properties: SatoriProperties) =
            FriendResource(SatoriAction(platform, selfId, properties, "friend"), properties)
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
        send(method, JsonObjectDSLBuilder().apply(dsl).build().toString())
}