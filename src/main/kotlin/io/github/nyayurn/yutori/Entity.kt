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
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.to
import com.alibaba.fastjson2.toList

data class Channel(
    val id: String,
    val type: Type,
    val name: String?,
    @JSONField(name = "parent_id") val parentId: String?
) {
    enum class Type(type: Number) {
        TEXT(0),
        VOICE(1),
        CATEGORY(2),
        DIRECT(3)
    }
}

data class Guild(
    var id: String,
    val name: String?,
    val avatar: String?,
)

data class GuildMember(
    var user: User?,
    val nick: String?,
    val avatar: String?,
    @JSONField(name = "joined_at") val joinedAt: Number?
)

data class GuildRole(
    var id: String,
    val name: String?
)

interface Interaction

data class Argv(
    var name: String,
    var arguments: List<Any>,
    var options: Any
) : Interaction

data class Button(
    var id: String
) : Interaction

data class Login(
    var user: User?,
    @JSONField(name = "self_id") val selfId: String?,
    val platform: String?,
    val status: Status
) {
    enum class Status(status: Number) {
        OFFLINE(0),
        ONLINE(1),
        CONNECT(2),
        DISCONNECT(3),
        RECONNECT(4)
    }
}

data class Message(
    var id: String,
    val content: String,
    val channel: Channel?,
    val guild: Guild?,
    val member: GuildMember?,
    val user: User?,
    @JSONField(name = "created_at") val createdAt: Number?,
    @JSONField(name = "updated_at") val updatedAt: Number?
)

data class User(
    var id: String,
    val name: String?,
    val nick: String?,
    val avatar: String?,
    @JSONField(name = "is_bot") val isBot: Boolean?
)

data class Signaling(val op: Int, var body: Body? = null) {
    interface Body
    class Ready(val logins: List<Login>) : Body
    companion object {
        fun parse(json: String?): Signaling {
            val jsonObject = json.parseObject()
            return when (val op = jsonObject.getIntValue("op")) {
                EVENT -> {
                    val event = jsonObject.getJSONObject("body").to<Event>()
                    Signaling(op, event)
                }

                READY -> {
                    val body = Ready(jsonObject.getJSONObject("body").getJSONArray("logins").toList<Login>())
                    Signaling(op, body)
                }

                else -> throw NoSuchElementException()
            }
        }

        const val EVENT = 0
        const val PING = 1
        const val PONG = 2
        const val IDENTIFY = 3
        const val READY = 4
    }
}

data class Identify(
    var token: String? = null,
    var sequence: Number? = null
) : Signaling.Body

data class Event(
    var id: Number,
    val type: String,
    val platform: String,
    @JSONField(name = "self_id") val selfId: String,
    val timestamp: Number,
    val argv: Argv?,
    val button: Button?,
    val channel: Channel?,
    val guild: Guild?,
    val login: Login?,
    val member: GuildMember?,
    val message: Message?,
    val operator: User?,
    val role: GuildRole?,
    val user: User?
) : Signaling.Body

data class InternalEvent(
    var id: Number,
    val type: String,
    val platform: String,
    @JSONField(name = "self_id") val selfId: String,
    val timestamp: Number,
    @JSONField(name = "_type") val internalType: String,
    @JSONField(name = "_data") val internalData: Any
)

data class PageResponse<T> @JvmOverloads constructor(
    val data: List<T>,
    val next: String? = null
)

data class Properties @JvmOverloads constructor(
    val address: String,
    val token: String? = null
)