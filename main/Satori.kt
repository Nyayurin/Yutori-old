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

import com.alibaba.fastjson2.JSONObject
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*

fun interface Listener<T : Event> {
    operator fun invoke(bot: Bot, event: T)
}

class Satori private constructor(
    val properties: SatoriProperties,
    private val logger: Logger
) {
    val onEvent = mutableListOf<ListenerContext<Event>>()
    val onGuild = mutableListOf<ListenerContext<GuildEvent>>()
    val onMember = mutableListOf<ListenerContext<GuildMemberEvent>>()
    val onRole = mutableListOf<ListenerContext<GuildRoleEvent>>()
    val onButton = mutableListOf<ListenerContext<InteractionButtonEvent>>()
    val onCommand = mutableListOf<ListenerContext<InteractionCommandEvent>>()
    val onLogin = mutableListOf<ListenerContext<LoginEvent>>()
    val onMessage = mutableListOf<ListenerContext<MessageEvent>>()
    val onReaction = mutableListOf<ListenerContext<ReactionEvent>>()
    val onUser = mutableListOf<ListenerContext<UserEvent>>()

    fun onEvent(listener: Listener<Event>) = ListenerContext(listener).apply { onEvent += this }

    fun onGuildAdded(listener: Listener<GuildEvent>) = ListenerContext(listener).apply {
        onGuild += this
        withFilter(eventTypeFilter(GuildEvents.ADDED))
    }

    fun onGuildUpdated(listener: Listener<GuildEvent>) = ListenerContext(listener).apply {
        onGuild += this
        withFilter(eventTypeFilter(GuildEvents.UPDATED))
    }

    fun onGuildRemoved(listener: Listener<GuildEvent>) = ListenerContext(listener).apply {
        onGuild += this
        withFilter(eventTypeFilter(GuildEvents.REMOVED))
    }

    fun onGuildRequest(listener: Listener<GuildEvent>) = ListenerContext(listener).apply {
        onGuild += this
        withFilter(eventTypeFilter(GuildEvents.REQUEST))
    }

    fun onGuildMemberAdded(listener: Listener<GuildMemberEvent>) = ListenerContext(listener).apply {
        onMember += this
        withFilter(eventTypeFilter(GuildMemberEvents.ADDED))
    }

    fun onGuildMemberUpdated(listener: Listener<GuildMemberEvent>) = ListenerContext(listener).apply {
        onMember += this
        withFilter(eventTypeFilter(GuildMemberEvents.UPDATED))
    }

    fun onGuildMemberRemoved(listener: Listener<GuildMemberEvent>) = ListenerContext(listener).apply {
        onMember += this
        withFilter(eventTypeFilter(GuildMemberEvents.REMOVED))
    }

    fun onGuildMemberRequest(listener: Listener<GuildMemberEvent>) = ListenerContext(listener).apply {
        onMember += this
        withFilter(eventTypeFilter(GuildMemberEvents.REQUEST))
    }

    fun onGuildRoleCreated(listener: Listener<GuildRoleEvent>) = ListenerContext(listener).apply {
        onRole += this
        withFilter(eventTypeFilter(GuildRoleEvents.CREATED))
    }

    fun onGuildRoleUpdated(listener: Listener<GuildRoleEvent>) = ListenerContext(listener).apply {
        onRole += this
        withFilter(eventTypeFilter(GuildRoleEvents.UPDATED))
    }

    fun onGuildRoleDeleted(listener: Listener<GuildRoleEvent>) = ListenerContext(listener).apply {
        onRole += this
        withFilter(eventTypeFilter(GuildRoleEvents.DELETED))
    }

    fun onInteractionButton(listener: Listener<InteractionButtonEvent>) = ListenerContext(listener).apply {
        onButton += this
        withFilter(eventTypeFilter(InteractionEvents.BUTTON))
    }

    fun onInteractionCommand(listener: Listener<InteractionCommandEvent>) = ListenerContext(listener).apply {
        onCommand += this
        withFilter(eventTypeFilter(InteractionEvents.COMMAND))
    }

    fun onLoginAdded(listener: Listener<LoginEvent>) = ListenerContext(listener).apply {
        onLogin += this
        withFilter(eventTypeFilter(LoginEvents.ADDED))
    }

    fun onLoginRemoved(listener: Listener<LoginEvent>) = ListenerContext(listener).apply {
        onLogin += this
        withFilter(eventTypeFilter(LoginEvents.REMOVED))
    }

    fun onLoginUpdated(listener: Listener<LoginEvent>) = ListenerContext(listener).apply {
        onLogin += this
        withFilter(eventTypeFilter(LoginEvents.UPDATED))
    }

    fun onMessageCreated(listener: Listener<MessageEvent>) = ListenerContext(listener).apply {
        onMessage += this
        withFilter(eventTypeFilter(MessageEvents.CREATED))
    }

    fun onMessageUpdated(listener: Listener<MessageEvent>) = ListenerContext(listener).apply {
        onMessage += this
        withFilter(eventTypeFilter(MessageEvents.UPDATED))
    }

    fun onMessageDeleted(listener: Listener<MessageEvent>) = ListenerContext(listener).apply {
        onMessage += this
        withFilter(eventTypeFilter(MessageEvents.DELETED))
    }

    fun onReactionAdded(listener: Listener<ReactionEvent>) = ListenerContext(listener).apply {
        onReaction += this
        withFilter(eventTypeFilter(ReactionEvents.ADDED))
    }

    fun onReactionRemoved(listener: Listener<ReactionEvent>) = ListenerContext(listener).apply {
        onReaction += this
        withFilter(eventTypeFilter(ReactionEvents.REMOVED))
    }

    fun onFriendRequest(listener: Listener<UserEvent>) = ListenerContext(listener).apply {
        onUser += this
        withFilter(eventTypeFilter(UserEvents.FRIEND_REQUEST))
    }

    /**
     * 与 Satori Server 建立 Websocket 连接
     * @param name Websocket 客户端的名称, 用于在日志打印时区分
     * @param scope 协程作用域
     * @param onWebSocketException 连接异常时的回调函数
     * @param onEventException 消息异常时的回调函数
     */
    @JvmOverloads
    fun connect(
        name: String = "Satori",
        scope: CoroutineScope? = null,
        onWebSocketException: (SatoriWebSocketClient.(Throwable) -> Unit) = {},
        onEventException: (SatoriWebSocketClient.(Throwable) -> Unit) = {},
    ) = SatoriWebSocketClient(this, name, onWebSocketException, onEventException, logger).apply { connect(scope) }

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

    fun runEvent(event: Event, scope: CoroutineScope) {
        try {
            val bot = Bot.of(event, properties, scope, logger)
            val newEvent = parseEvent(event)
            scope.launch {
                runEvent(onEvent, bot, newEvent)
                when (newEvent) {
                    is GuildEvent -> runEvent(onGuild, bot, newEvent)
                    is GuildMemberEvent -> runEvent(onMember, bot, newEvent)
                    is GuildRoleEvent -> runEvent(onRole, bot, newEvent)
                    is InteractionButtonEvent -> runEvent(onButton, bot, newEvent)
                    is InteractionCommandEvent -> runEvent(onCommand, bot, newEvent)
                    is LoginEvent -> runEvent(onLogin, bot, newEvent)
                    is MessageEvent -> runEvent(onMessage, bot, newEvent)
                    is ReactionEvent -> runEvent(onReaction, bot, newEvent)
                    is UserEvent -> runEvent(onUser, bot, newEvent)
                }
            }
        } catch (e: Exception) {
            logger.warn("Parse event exception: ${e.message}, event: $event", this::class.java)
        }
    }

    private fun <T : Event> runEvent(list: List<ListenerContext<T>>, bot: Bot, event: T) {
        for (context in list) context.run(bot, event)
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun of(properties: SatoriProperties, logger: Logger = Slf4jLogger()) = Satori(properties, logger)

        @JvmStatic
        @JvmOverloads
        fun of(
            host: String = "127.0.0.1",
            port: Int = 5500,
            path: String = "",
            token: String? = null,
            version: String = "v1",
            logger: Logger = Slf4jLogger()
        ) = Satori(SimpleSatoriProperties(host, port, path, token, version), logger)

        @JvmSynthetic
        @JvmOverloads
        inline fun of(properties: SatoriProperties, logger: Logger = Slf4jLogger(), apply: Satori.() -> Unit) =
            of(properties, logger).apply { apply() }

        @JvmSynthetic
        @JvmOverloads
        inline fun of(
            host: String = "127.0.0.1",
            port: Int = 5500,
            path: String = "",
            token: String? = null,
            version: String = "v1",
            logger: Logger = Slf4jLogger(),
            apply: Satori.() -> Unit
        ) = of(host, port, path, token, version, logger).apply { apply() }
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
    private val name: String,
    private val onWebSocketException: (SatoriWebSocketClient.(Throwable) -> Unit),
    private val onEventException: (SatoriWebSocketClient.(Throwable) -> Unit),
    private val logger: Logger
) : AutoCloseable {
    private var sequence: Number? = null
    private lateinit var coroutineScope: CoroutineScope
    private val client = HttpClient {
        install(WebSockets)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun connect(scope: CoroutineScope?) {
        (scope ?: GlobalScope).launch {
            coroutineScope = this
            run()
        }
    }

    override fun close() {
        client.close()
        coroutineScope.cancel()
    }

    private suspend fun run() = try {
        client.webSocket(
            HttpMethod.Get,
            satori.properties.host,
            satori.properties.port,
            "${satori.properties.path}/${satori.properties.version}/events"
        ) {
            logger.info("[$name]: 成功建立 WebSocket 连接", this::class.java)
            launch { sendIdentity(this@webSocket) }
            for (frame in incoming) try {
                frame as? Frame.Text ?: continue
                val signaling = Signaling.parse(frame.readText())
                onEvent(signaling)
            } catch (e: Exception) {
                logger.warn("[$name]: 处理事件时出错: ${e.localizedMessage}", this::class.java)
                onEventException(e)
            }
        }
    } catch (e: Exception) {
        client.close()
        logger.warn("[$name]: WebSocket 连接断开: ${e.localizedMessage}", this::class.java)
        onWebSocketException(e)
    }

    private suspend fun sendIdentity(session: DefaultClientWebSocketSession) {
        val connection = Signaling(Signaling.IDENTIFY)
        val token = satori.properties.token
        if (token != null || sequence != null) {
            val body = Identify()
            body.token = token
            body.sequence = sequence
            connection.body = body
        }
        val content = JSONObject.toJSONString(connection)
        logger.info("[$name]: 发送身份验证: $content", this::class.java)
        session.send(content)
    }

    private fun DefaultClientWebSocketSession.onEvent(signaling: Signaling) {
        when (signaling.op) {
            Signaling.READY -> {
                val ready = signaling.body as Ready
                logger.info("[$name]: 成功建立事件推送(${ready.logins.size}): \n${
                    ready.logins.joinToString(
                        "\n"
                    ) { "{platform: ${it.platform}, selfId: ${it.selfId}}" }
                }", this::class.java)
                // 心跳
                val sendSignaling = Signaling(Signaling.PING)
                launch {
                    while (true) {
                        delay(10000)
                        send(JSONObject.toJSONString(sendSignaling))
                    }
                }
            }

            Signaling.EVENT -> launch {
                sendEvent(this, signaling)
            }

            Signaling.PONG -> logger.debug("[$name]: 收到 PONG", this::class.java)
            else -> logger.error("Unsupported $signaling", this::class.java)
        }
    }

    private fun sendEvent(scope: CoroutineScope, signaling: Signaling) {
        val body = signaling.body as Event
        logger.info(
            "[$name]: 接收事件: platform: ${body.platform}, selfId: ${body.selfId}, type: ${body.type}",
            this::class.java
        )
        logger.debug("[$name]: 事件详细信息: $body", this::class.java)
        sequence = body.id
        satori.runEvent(body, scope)
    }
}