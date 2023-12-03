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

class Channel(
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

class Guild(
    val id: String,
    val name: String?,
    val avatar: String?,
)

class GuildMember(
    val user: User?,
    val nick: String?,
    val avatar: String?,
    @JSONField(name = "joined_at") val joinedAt: Number?
)

class GuildRole(
    val id: String,
    val name: String?
)

interface Interaction

class Argv(
    val name: String,
    val arguments: List<Any>,
    val options: Any
) : Interaction

class Button(
    val id: String
) : Interaction

class Login(
    val user: User?,
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

class Message(
    val id: String,
    val content: String,
    val channel: Channel?,
    val guild: Guild?,
    val member: GuildMember?,
    val user: User?,
    @JSONField(name = "created_at") val createdAt: Number?,
    @JSONField(name = "updated_at") val updatedAt: Number?
)

class User(
    val id: String,
    val name: String?,
    val nick: String?,
    val avatar: String?,
    @JSONField(name = "is_bot") val isBot: Boolean?
)

class Signaling(val op: Int, var body: Body? = null) {
    interface Body
    class Ready(val logins: List<Login>) : Body
    companion object {
        fun parse(json: String?): Signaling {
            val jsonObject = json.parseObject()
            return when (val op = jsonObject.getIntValue("op")) {
                EVENT -> {
                    // val event = jsonObject.getJSONObject("body").to<Event>()
                    val body = jsonObject.getJSONObject("body")
                    val event = Event(
                        body["id"] as Number,
                        body["type"] as String,
                        body["platform"] as String,
                        body["self_id"] as String,
                        body["timestamp"] as Number,
                        body.getJSONObject("argv")?.to<Argv>(),
                        body.getJSONObject("button")?.to<Button>(),
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

        const val EVENT = 0
        const val PING = 1
        const val PONG = 2
        const val IDENTIFY = 3
        const val READY = 4
    }
}

class Identify(
    var token: String? = null,
    var sequence: Number? = null
) : Signaling.Body

open class Event @JvmOverloads constructor(
    val id: Number,
    val type: String,
    val platform: String,
    @JSONField(name = "self_id") val selfId: String,
    val timestamp: Number,
    open val argv: Argv? = null,
    open val button: Button? = null,
    open val channel: Channel? = null,
    open val guild: Guild? = null,
    open val login: Login? = null,
    open val member: GuildMember? = null,
    open val message: Message? = null,
    open val operator: User? = null,
    open val role: GuildRole? = null,
    open val user: User? = null
) : Signaling.Body

class InternalEvent(
    val id: Number,
    val type: String,
    val platform: String,
    @JSONField(name = "self_id") val selfId: String,
    val timestamp: Number,
    @JSONField(name = "_type") val internalType: String,
    @JSONField(name = "_data") val internalData: Any
)

class PageResponse<T> @JvmOverloads constructor(
    val data: List<T>,
    val next: String? = null
)

@JvmRecord
data class Properties @JvmOverloads constructor(
    val address: String,
    val token: String? = null
)