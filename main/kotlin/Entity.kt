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

@file:Suppress("MemberVisibilityCanBePrivate", "unused", "UNUSED_PARAMETER")

package io.github.nyayurn.yutori

import com.alibaba.fastjson2.annotation.JSONField
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.to
import com.alibaba.fastjson2.toList

/**
 * 频道, 参考 https://satori.chat/zh-CN/resources/channel.html#channel
 * @property id 频道 ID
 * @property type 频道类型
 * @property name 频道名称
 * @property parentId 父频道 ID
 */
class Channel(
    val id: String,
    val type: Type,
    val name: String?,
    @JSONField(name = "parent_id") val parentId: String?
) {
    /**
     * Channel.Type, 参考 https://satori.chat/zh-CN/resources/channel.html#channel-type
     */
    enum class Type(type: Number) {
        /**
         * 文本频道
         */
        TEXT(0),

        /**
         * 语音频道
         */
        VOICE(1),

        /**
         * 分类频道
         */
        CATEGORY(2),

        /**
         * 私聊频道
         */
        DIRECT(3)
    }

    override fun toString(): String {
        return "Channel(id='$id', type=$type, name=$name, parentId=$parentId)"
    }
}

/**
 * 群组, 参考 https://satori.chat/zh-CN/resources/guild.html#guild
 * @property id 群组 ID
 * @property name 群组名称
 * @property avatar 群组头像
 */
class Guild(
    val id: String,
    val name: String?,
    val avatar: String?,
) {
    override fun toString(): String {
        return "Guild(id='$id', name=$name, avatar=$avatar)"
    }
}

/**
 * 群组成员, 参考 https://satori.chat/zh-CN/resources/member.html#guildmember
 * @property user 用户对象
 * @property nick 用户在群组中的名称
 * @property avatar 用户在群组中的头像
 * @property joinedAt 加入时间
 */
class GuildMember(
    val user: User?,
    val nick: String?,
    val avatar: String?,
    @JSONField(name = "joined_at") val joinedAt: Number?
) {
    override fun toString(): String {
        return "GuildMember(user=$user, nick=$nick, avatar=$avatar, joinedAt=$joinedAt)"
    }
}

/**
 * 群组角色, 参考 https://satori.chat/zh-CN/resources/role.html#guildrole
 * @property id 角色 ID
 * @property name 角色名称
 */
class GuildRole(
    val id: String,
    val name: String?
) {
    override fun toString(): String {
        return "GuildRole(id='$id', name=$name)"
    }
}

/**
 * 交互, 参考 https://satori.chat/zh-CN/resources/interaction.html
 */
interface Interaction {
    /**
     * Argv, 参考 https://satori.chat/zh-CN/resources/interaction.html#argv
     * @property name 指令名称
     * @property arguments 参数
     * @property options 选项
     */
    class Argv(
        val name: String,
        val arguments: List<Any>,
        val options: Any
    ) : Interaction {
        override fun toString(): String {
            return "Argv(name='$name', arguments=$arguments, options=$options)"
        }
    }

    /**
     * Button, 参考 https://satori.chat/zh-CN/resources/interaction.html#button
     * @property id 按钮 ID
     */
    class Button(val id: String) : Interaction {
        override fun toString(): String {
            return "Button(id='$id')"
        }
    }
}

/**
 * 登录信息, 参考 https://satori.chat/zh-CN/resources/login.html#login
 * @property user 用户对象
 * @property selfId 平台账号
 * @property platform 平台名称
 * @property status 登录状态
 */
class Login(
    val user: User?,
    @JSONField(name = "self_id") val selfId: String?,
    val platform: String?,
    val status: Status
) {
    /**
     * Status, 参考 https://satori.chat/zh-CN/resources/login.html#status
     */
    enum class Status(status: Number) {
        /**
         * 离线
         */
        OFFLINE(0),

        /**
         * 在线
         */
        ONLINE(1),

        /**
         * 连接中
         */
        CONNECT(2),

        /**
         * 断开连接
         */
        DISCONNECT(3),

        /**
         * 重新连接
         */
        RECONNECT(4)
    }

    override fun toString(): String {
        return "Login(user=$user, selfId=$selfId, platform=$platform, status=$status)"
    }
}

/**
 * 消息, 参考 https://satori.chat/zh-CN/resources/message.html#message
 * @property id 消息 ID
 * @property content 消息内容
 * @property channel 频道对象
 * @property guild 群组对象
 * @property member 成员对象
 * @property user 用户对象
 * @property createdAt 消息发送的时间戳
 * @property updatedAt 消息修改的时间戳
 */
class Message(
    val id: String,
    val content: String,
    val channel: Channel?,
    val guild: Guild?,
    val member: GuildMember?,
    val user: User?,
    @JSONField(name = "created_at") val createdAt: Number?,
    @JSONField(name = "updated_at") val updatedAt: Number?
) {
    override fun toString(): String {
        return "Message(id='$id', content='$content', channel=$channel, guild=$guild, member=$member, user=$user, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}

/**
 * 用户, 参考 https://satori.chat/zh-CN/resources/user.html#user
 * @property id 用户 ID
 * @property name 用户名称
 * @property nick 用户昵称
 * @property avatar 用户头像
 * @property isBot 是否为机器人
 */
class User(
    val id: String,
    val name: String?,
    val nick: String?,
    val avatar: String?,
    @JSONField(name = "is_bot") val isBot: Boolean?
) {
    override fun toString(): String {
        return "User(id='$id', name=$name, nick=$nick, avatar=$avatar, isBot=$isBot)"
    }
}

/**
 * 信令, 参考 https://satori.chat/zh-CN/protocol/events.html#websocket
 * @property op 信令类型
 * @property body 信令数据
 */
class Signaling(val op: Int, var body: Body? = null) {
    interface Body

    override fun toString(): String {
        return "Signaling(op=$op, body=$body)"
    }

    companion object {
        @JvmStatic
        fun parse(json: String?): Signaling {
            val jsonObject = json.parseObject()
            return when (val op = jsonObject.getIntValue("op")) {
                EVENT -> {
                    val body = jsonObject.getJSONObject("body")
                    if (body["user"] != null && body.getJSONObject("user")["id"] == null) {
                        throw NullPointerException("event.user.id is null")
                    }
                    val event = Event(
                        body["id"] as Number,
                        body["type"] as String,
                        body["platform"] as String,
                        body["self_id"] as String,
                        body["timestamp"] as Number,
                        body.getJSONObject("argv")?.to<Interaction.Argv>(),
                        body.getJSONObject("button")?.to<Interaction.Button>(),
                        body.getJSONObject("channel")?.to<Channel>(),
                        body.getJSONObject("guild")?.to<Guild>(),
                        body.getJSONObject("login")?.to<Login>(),
                        body.getJSONObject("member")?.to<GuildMember>(),
                        body.getJSONObject("message")?.to<Message>(),
                        body.getJSONObject("operator")?.to<User>(),
                        body.getJSONObject("role")?.to<GuildRole>(),
                        body.getJSONObject("user")?.to<User>()
                    )
                    Signaling(op, event)
                }

                READY -> {
                    val body = Ready(jsonObject.getJSONObject("body").getJSONArray("logins").toList<Login>())
                    Signaling(op, body)
                }

                PONG -> Signaling(op)
                else -> throw NoSuchElementException()
            }
        }

        /**
         * 事件
         */
        const val EVENT = 0

        /**
         * 心跳
         */
        const val PING = 1

        /**
         * 心跳回复
         */
        const val PONG = 2

        /**
         * 鉴权
         */
        const val IDENTIFY = 3

        /**
         * 鉴权回复
         */
        const val READY = 4
    }
}

/**
 * 鉴权回复
 * @property logins 登录信息
 */
class Ready(val logins: List<Login>) : Signaling.Body {
    override fun toString(): String {
        return "Ready(logins=$logins)"
    }
}

/**
 * 鉴权
 * @property token 鉴权令牌
 * @property sequence 序列号
 */
class Identify(
    var token: String? = null,
    var sequence: Number? = null
) : Signaling.Body {
    override fun toString(): String {
        return "Identify(token=$token, sequence=$sequence)"
    }
}

/**
 * 事件, 参考 https://satori.chat/zh-CN/protocol/events.html#event
 * @property id 事件 ID
 * @property type 事件类型
 * @property platform 接收者的平台名称
 * @property selfId 接收者的平台账号
 * @property timestamp 事件的时间戳
 * @property argv 交互指令
 * @property button 交互按钮
 * @property channel 事件所属的频道
 * @property guild 事件所属的群组
 * @property login 事件的登录信息
 * @property member 事件的目标成员
 * @property message 事件的消息
 * @property operator 事件的操作者
 * @property role 事件的目标角色
 * @property user 事件的目标用户
 */
open class Event @JvmOverloads constructor(
    val id: Number,
    val type: String,
    val platform: String,
    @JSONField(name = "self_id") val selfId: String,
    val timestamp: Number,
    open val argv: Interaction.Argv? = null,
    open val button: Interaction.Button? = null,
    open val channel: Channel? = null,
    open val guild: Guild? = null,
    open val login: Login? = null,
    open val member: GuildMember? = null,
    open val message: Message? = null,
    open val operator: User? = null,
    open val role: GuildRole? = null,
    open val user: User? = null
) : Signaling.Body {
    override fun toString(): String {
        return "Event(id=$id, type='$type', platform='$platform', selfId='$selfId', timestamp=$timestamp, argv=$argv, button=$button, channel=$channel, guild=$guild, login=$login, member=$member, message=$message, operator=$operator, role=$role, user=$user)"
    }
}

/**
 * 分页数据, 参考 https://satori.chat/zh-CN/protocol/api.html#%E5%88%86%E9%A1%B5
 * @param T 数据类型
 * @property data 数据
 * @property next 下一页的令牌
 */
class PaginatedData<T> @JvmOverloads constructor(
    val data: List<T>,
    val next: String? = null
) {
    override fun toString() = "PageResponse(data=$data, next=$next)"
}

/**
 * Satori 配置
 * @property address 连接地址
 * @property token Token
 * @property version 协议版本
 */
interface SatoriProperties {
    val address: String
    val token: String?
    val version: String
}

/**
 * 简易 Satori 配置实现类
 * @property address 连接地址
 * @property token Token
 * @property version 协议版本
 */
class SimpleSatoriProperties @JvmOverloads constructor(
    override val address: String,
    override val token: String? = null,
    override val version: String = "v1"
) : SatoriProperties {
    override fun toString() = "SimpleSatoriProperties(address='$address', token=$token, version='$version')"
}