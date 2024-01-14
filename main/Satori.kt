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

@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.nyayurn.yutori

import com.alibaba.fastjson2.JSONObject
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.slf4j.LoggerFactory
import java.util.logging.Logger

fun interface Listener<T : Event> {
    operator fun invoke(bot: Bot, event: T)
}

class Satori private constructor(val properties: SatoriProperties) {
    val event = mutableListOf<ListenerContext<Event>>()
    val guild = mutableListOf<ListenerContext<GuildEvent>>()
    val member = mutableListOf<ListenerContext<GuildMemberEvent>>()
    val role = mutableListOf<ListenerContext<GuildRoleEvent>>()
    val button = mutableListOf<ListenerContext<InteractionButtonEvent>>()
    val command = mutableListOf<ListenerContext<InteractionCommandEvent>>()
    val login = mutableListOf<ListenerContext<LoginEvent>>()
    val message = mutableListOf<ListenerContext<MessageEvent>>()
    val reaction = mutableListOf<ListenerContext<ReactionEvent>>()
    val user = mutableListOf<ListenerContext<UserEvent>>()

    fun onEvent(listener: Listener<Event>) = ListenerContext(listener).apply { event += this }

    fun onGuildAdded(listener: Listener<GuildEvent>) = ListenerContext(listener).apply {
        guild += this
        withFilter(eventTypeFilter(GuildEvents.ADDED))
    }

    fun onGuildUpdated(listener: Listener<GuildEvent>) = ListenerContext(listener).apply {
        guild += this
        withFilter(eventTypeFilter(GuildEvents.UPDATED))
    }

    fun onGuildRemoved(listener: Listener<GuildEvent>) = ListenerContext(listener).apply {
        guild += this
        withFilter(eventTypeFilter(GuildEvents.REMOVED))
    }

    fun onGuildRequest(listener: Listener<GuildEvent>) = ListenerContext(listener).apply {
        guild += this
        withFilter(eventTypeFilter(GuildEvents.REQUEST))
    }

    fun onGuildMemberAdded(listener: Listener<GuildMemberEvent>) = ListenerContext(listener).apply {
        member += this
        withFilter(eventTypeFilter(GuildMemberEvents.ADDED))
    }

    fun onGuildMemberUpdated(listener: Listener<GuildMemberEvent>) = ListenerContext(listener).apply {
        member += this
        withFilter(eventTypeFilter(GuildMemberEvents.UPDATED))
    }

    fun onGuildMemberRemoved(listener: Listener<GuildMemberEvent>) = ListenerContext(listener).apply {
        member += this
        withFilter(eventTypeFilter(GuildMemberEvents.REMOVED))
    }

    fun onGuildMemberRequest(listener: Listener<GuildMemberEvent>) = ListenerContext(listener).apply {
        member += this
        withFilter(eventTypeFilter(GuildMemberEvents.REQUEST))
    }

    fun onGuildRoleCreated(listener: Listener<GuildRoleEvent>) = ListenerContext(listener).apply {
        role += this
        withFilter(eventTypeFilter(GuildRoleEvents.CREATED))
    }

    fun onGuildRoleUpdated(listener: Listener<GuildRoleEvent>) = ListenerContext(listener).apply {
        role += this
        withFilter(eventTypeFilter(GuildRoleEvents.UPDATED))
    }

    fun onGuildRoleDeleted(listener: Listener<GuildRoleEvent>) = ListenerContext(listener).apply {
        role += this
        withFilter(eventTypeFilter(GuildRoleEvents.DELETED))
    }

    fun onInteractionButton(listener: Listener<InteractionButtonEvent>) = ListenerContext(listener).apply {
        button += this
        withFilter(eventTypeFilter(InteractionEvents.BUTTON))
    }

    fun onInteractionCommand(listener: Listener<InteractionCommandEvent>) = ListenerContext(listener).apply {
        command += this
        withFilter(eventTypeFilter(InteractionEvents.COMMAND))
    }

    fun onLoginAdded(listener: Listener<LoginEvent>) = ListenerContext(listener).apply {
        login += this
        withFilter(eventTypeFilter(LoginEvents.ADDED))
    }

    fun onLoginRemoved(listener: Listener<LoginEvent>) = ListenerContext(listener).apply {
        login += this
        withFilter(eventTypeFilter(LoginEvents.REMOVED))
    }

    fun onLoginUpdated(listener: Listener<LoginEvent>) = ListenerContext(listener).apply {
        login += this
        withFilter(eventTypeFilter(LoginEvents.UPDATED))
    }

    fun onMessageCreated(listener: Listener<MessageEvent>) = ListenerContext(listener).apply {
        message += this
        withFilter(eventTypeFilter(MessageEvents.CREATED))
    }

    fun onMessageUpdated(listener: Listener<MessageEvent>) = ListenerContext(listener).apply {
        message += this
        withFilter(eventTypeFilter(MessageEvents.UPDATED))
    }

    fun onMessageDeleted(listener: Listener<MessageEvent>) = ListenerContext(listener).apply {
        message += this
        withFilter(eventTypeFilter(MessageEvents.DELETED))
    }

    fun onReactionAdded(listener: Listener<ReactionEvent>) = ListenerContext(listener).apply {
        reaction += this
        withFilter(eventTypeFilter(ReactionEvents.ADDED))
    }

    fun onReactionRemoved(listener: Listener<ReactionEvent>) = ListenerContext(listener).apply {
        reaction += this
        withFilter(eventTypeFilter(ReactionEvents.REMOVED))
    }

    fun onFriendRequest(listener: Listener<UserEvent>) = ListenerContext(listener).apply {
        user += this
        withFilter(eventTypeFilter(UserEvents.FRIEND_REQUEST))
    }


    /**
     * 与 Satori Server 建立 Websocket 连接
     * @param name Websocket 客户端的名称, 用于在日志打印时区分
     */
    @JvmOverloads
    fun connect(
        name: String? = null,
        logger: Logger = Logger.getLogger(LoggerFactory.getLogger(KotlinLogging.logger { }.javaClass).name)
    ) = SatoriWebSocketClient(this@Satori, name, logger).apply { connect() }

    private fun parseEvent(event: Event) = when (event.type) {
        GuildEvents.ADDED, GuildEvents.UPDATED, GuildEvents.REMOVED, GuildEvents.REQUEST -> GuildEvent.parse(event)
        GuildMemberEvents.ADDED, GuildMemberEvents.UPDATED, GuildMemberEvents.REMOVED, GuildMemberEvents.REQUEST -> GuildMemberEvent.parse(
            event
        )

        GuildRoleEvents.CREATED, GuildRoleEvents.UPDATED, GuildRoleEvents.DELETED -> GuildRoleEvent.parse(event)
        InteractionEvents.BUTTON -> InteractionButtonEvent.parse(event)
        InteractionEvents.COMMAND -> InteractionCommandEvent.parse(event)
        LoginEvents.ADDED, LoginEvents.REMOVED, LoginEvents.UPDATED -> LoginEvent.parse(event)
        MessageEvents.CREATED, MessageEvents.UPDATED, MessageEvents.DELETED -> MessageEvent.parse(event)
        ReactionEvents.ADDED, ReactionEvents.REMOVED -> ReactionEvent.parse(event)
        UserEvents.FRIEND_REQUEST -> UserEvent.parse(event)
        else -> event
    }

    fun runEvent(event: Event, coroutineScope: CoroutineScope) {
        val bot = Bot.of(event, properties, coroutineScope)
        val newEvent = parseEvent(event)
        runEvent(this.event, bot, newEvent)
        when (newEvent) {
            is GuildEvent -> runEvent(this.guild, bot, newEvent)
            is GuildMemberEvent -> runEvent(this.member, bot, newEvent)
            is GuildRoleEvent -> runEvent(this.role, bot, newEvent)
            is InteractionButtonEvent -> runEvent(this.button, bot, newEvent)
            is InteractionCommandEvent -> runEvent(this.command, bot, newEvent)
            is LoginEvent -> runEvent(this.login, bot, newEvent)
            is MessageEvent -> runEvent(this.message, bot, newEvent)
            is ReactionEvent -> runEvent(this.reaction, bot, newEvent)
            is UserEvent -> runEvent(this.user, bot, newEvent)
        }
    }

    private fun <T : Event> runEvent(list: List<ListenerContext<T>>, bot: Bot, event: T) {
        for (context in list) context.run(bot, event)
    }

    companion object {
        @JvmStatic
        fun of(properties: SatoriProperties) = Satori(properties)

        @JvmStatic
        @JvmOverloads
        fun of(
            host: String = "127.0.0.1",
            port: Int = 5500,
            path: String = "",
            token: String? = null,
            version: String = "v1"
        ) = Satori(SimpleSatoriProperties(host, port, path, token, version))

        @JvmSynthetic
        inline fun of(properties: SatoriProperties, apply: Satori.() -> Unit) = of(properties).apply { apply() }

        @JvmSynthetic
        inline fun of(
            host: String = "127.0.0.1",
            port: Int = 5500,
            path: String = "",
            token: String? = null,
            version: String = "v1",
            apply: Satori.() -> Unit
        ) = of(host, port, path, token, version).apply { apply() }
    }
}

class ListenerContext<T : Event>(private val listener: Listener<T>) {
    private val filters = mutableListOf<(Bot, Event) -> Boolean>()

    fun withFilter(filter: (Bot, Event) -> Boolean) = this.apply { filters += filter }
    fun run(bot: Bot, event: T) {
        for (filter in filters) if (!filter(bot, event)) return
        listener(bot, event)
    }
}

class SatoriWebSocketClient(
    private val satori: Satori,
    private val name: String? = null,
    private val log: Logger
) : AutoCloseable {
    private var sequence: Number? = null
    private val client = HttpClient {
        install(WebSockets)
    }

    fun connect() = runBlocking {
        client.webSocket(
            HttpMethod.Get,
            satori.properties.host,
            satori.properties.port,
            "${satori.properties.path}/${satori.properties.version}/events"
        ) {
            log.info("[$name]: 成功建立 WebSocket 连接")

            val connection = Signaling(Signaling.Op.IDENTIFY)
            val token = satori.properties.token
            if (token != null || sequence != null) {
                val body = Identify()
                body.token = token
                body.sequence = sequence
                connection.body = body
            }
            send(JSONObject.toJSONString(connection))

            while (true) {
                val message = (incoming.receive() as? Frame.Text ?: continue).readText()
                val signaling = Signaling.parse(message)
                onEvent(signaling)
            }
        }
    }

    override fun close() = client.close()

    private fun DefaultClientWebSocketSession.onEvent(signaling: Signaling) {
        when (signaling.op) {
            Signaling.Op.READY -> {
                val ready = signaling.body as Ready
                log.info("[$name]: 成功建立事件推送(${ready.logins.size}): \n${
                    ready.logins.joinToString(
                        "\n"
                    ) { "{platform: ${it.platform}, selfId: ${it.selfId}}" }
                }")
                // 心跳
                val sendSignaling = Signaling(Signaling.Op.PING)
                launch {
                    while (true) {
                        delay(10000) // 10 seconds delay
                        send(JSONObject.toJSONString(sendSignaling))
                    }
                }
            }

            Signaling.Op.EVENT -> launch {
                sendEvent(this, signaling)
            }

            Signaling.Op.PONG -> log.fine("[$name]: 收到 PONG")
            else -> log.severe("Unsupported $signaling")
        }
    }

    private fun sendEvent(scope: CoroutineScope, signaling: Signaling) {
        val body = signaling.body as Event
        log.info("[$name]: 接收事件: platform: ${body.platform}, selfId: ${body.selfId}, type: ${body.type}")
        log.fine("[$name]: 事件详细信息: $body")
        sequence = body.id
        satori.runEvent(body, scope)
    }
}