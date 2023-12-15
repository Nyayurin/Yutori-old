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
import io.github.nyayurn.yutori.message.element.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode


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

    override fun toString(): String {
        return "Channel(id='$id', type=$type, name=$name, parentId=$parentId)"
    }
}

class Guild(
    val id: String,
    val name: String?,
    val avatar: String?,
) {
    override fun toString(): String {
        return "Guild(id='$id', name=$name, avatar=$avatar)"
    }
}

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

class GuildRole(
    val id: String,
    val name: String?
) {
    override fun toString(): String {
        return "GuildRole(id='$id', name=$name)"
    }
}

interface Interaction

class Argv(
    val name: String,
    val arguments: List<Any>,
    val options: Any
) : Interaction {
    override fun toString(): String {
        return "Argv(name='$name', arguments=$arguments, options=$options)"
    }
}

class Button(
    val id: String
) : Interaction {
    override fun toString(): String {
        return "Button(id='$id')"
    }
}

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

    override fun toString(): String {
        return "Login(user=$user, selfId=$selfId, platform=$platform, status=$status)"
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
) {
    override fun toString(): String {
        return "Message(id='$id', content='$content', channel=$channel, guild=$guild, member=$member, user=$user, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}

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

class Ready(val logins: List<Login>) : Signaling.Body {
    override fun toString(): String {
        return "Ready(logins=$logins)"
    }
}

class Identify(
    var token: String? = null,
    var sequence: Number? = null
) : Signaling.Body {
    override fun toString(): String {
        return "Identify(token=$token, sequence=$sequence)"
    }
}

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
) : Signaling.Body {
    fun toMsgChain(): List<MessageElement> {
        val msgChain = mutableListOf<MessageElement>()
        val childNodes = Jsoup.parse(message!!.content).body().childNodes()
        for (node in childNodes) {
            if (node is TextNode) {
                msgChain.add(Text(node.text()))
            } else if (node is Element) {
                when (node.tagName()) {
                    "at" -> {
                        val element = At()
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "sharp" -> {
                        val element = Sharp(node.attr("id"))
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "a" -> {
                        val element = Href(node.attr("href"))
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "img" -> {
                        val element = Image(node.attr("src"))
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "audio" -> {
                        val element = Audio(node.attr("src"))
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "video" -> {
                        val element = Video(node.attr("src"))
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "file" -> {
                        val element = File(node.attr("src"))
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "b" -> msgChain.add(Bold((node.childNode(0) as TextNode).text()))
                    "strong" -> msgChain.add(Strong((node.childNode(0) as TextNode).text()))
                    "i" -> msgChain.add(Idiomatic((node.childNode(0) as TextNode).text()))
                    "em" -> msgChain.add(Em((node.childNode(0) as TextNode).text()))
                    "u" -> msgChain.add(Underline((node.childNode(0) as TextNode).text()))
                    "ins" -> msgChain.add(Ins((node.childNode(0) as TextNode).text()))
                    "s" -> msgChain.add(Strikethrough((node.childNode(0) as TextNode).text()))
                    "del" -> msgChain.add(Delete((node.childNode(0) as TextNode).text()))
                    "spl" -> msgChain.add(Spl((node.childNode(0) as TextNode).text()))
                    "code" -> msgChain.add(Code((node.childNode(0) as TextNode).text()))
                    "sup" -> msgChain.add(Sup((node.childNode(0) as TextNode).text()))
                    "sub" -> msgChain.add(Sub((node.childNode(0) as TextNode).text()))
                    "br" -> msgChain.add(Br())
                    "p" -> msgChain.add(Paragraph())
                    "message" -> {
                        val element = io.github.nyayurn.yutori.message.element.Message()
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "quote" -> {
                        val element = Quote((node.childNode(0) as TextNode).text())
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "author" -> {
                        val element = Author()
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    "button" -> {
                        val element = io.github.nyayurn.yutori.message.element.Button()
                        for (attr in node.attributes()) {
                            element[attr.key] = attr.value
                        }
                        msgChain.add(element)
                    }

                    else -> throw IllegalStateException("Unsupported tag: " + node.tagName())
                }
            }
        }
        return msgChain
    }

    override fun toString(): String {
        return "Event(id=$id, type='$type', platform='$platform', selfId='$selfId', timestamp=$timestamp, argv=$argv, button=$button, channel=$channel, guild=$guild, login=$login, member=$member, message=$message, operator=$operator, role=$role, user=$user)"
    }
}

class InternalEvent(
    val id: Number,
    val type: String,
    val platform: String,
    @JSONField(name = "self_id") val selfId: String,
    val timestamp: Number,
    @JSONField(name = "_type") val internalType: String,
    @JSONField(name = "_data") val internalData: Any
) {
    override fun toString(): String {
        return "InternalEvent(id=$id, type='$type', platform='$platform', selfId='$selfId', timestamp=$timestamp, internalType='$internalType', internalData=$internalData)"
    }
}

class PageResponse<T> @JvmOverloads constructor(
    val data: List<T>,
    val next: String? = null
) {
    override fun toString(): String {
        return "PageResponse(data=$data, next=$next)"
    }
}

interface SatoriProperties {
    val address: String
    val token: String?
    var sequence: Number?
    val listenSelfEvent: Boolean
}

class SimpleSatoriProperties @JvmOverloads constructor(
    override val address: String,
    override val token: String? = null,
    override var sequence: Number? = null,
    override val listenSelfEvent: Boolean = false
) : SatoriProperties {
    override fun toString(): String {
        return "SimpleSatoriProperties(address='$address', token=$token, sequence=$sequence, listenSelfEvent=$listenSelfEvent)"
    }
}