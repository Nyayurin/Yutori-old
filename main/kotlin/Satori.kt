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

import com.alibaba.fastjson2.JSONObject
import io.github.nyayurn.yutori.Slf4j.Companion.log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.util.NamedThreadFactory
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Satori private constructor(val properties: SatoriProperties) {
    private val event = mutableListOf<ListenerContext<Event>>()
    private val guild = mutableListOf<ListenerContext<GuildEvent>>()
    private val member = mutableListOf<ListenerContext<GuildMemberEvent>>()
    private val role = mutableListOf<ListenerContext<GuildRoleEvent>>()
    private val button = mutableListOf<ListenerContext<InteractionButtonEvent>>()
    private val command = mutableListOf<ListenerContext<InteractionCommandEvent>>()
    private val login = mutableListOf<ListenerContext<LoginEvent>>()
    private val message = mutableListOf<ListenerContext<MessageEvent>>()
    private val reaction = mutableListOf<ListenerContext<ReactionEvent>>()
    private val user = mutableListOf<ListenerContext<UserEvent>>()

    fun onEvent(listener: Listener<Event>) = ListenerContext(listener) { event += this }

    fun onGuildAdded(listener: Listener<GuildEvent>) = ListenerContext(listener) {
        guild += this
        withFilter(eventTypeFilter(GuildEvents.ADDED))
    }

    fun onGuildUpdated(listener: Listener<GuildEvent>) = ListenerContext(listener) {
        guild += this
        withFilter(eventTypeFilter(GuildEvents.UPDATED))
    }

    fun onGuildRemoved(listener: Listener<GuildEvent>) = ListenerContext(listener) {
        guild += this
        withFilter(eventTypeFilter(GuildEvents.REMOVED))
    }

    fun onGuildRequest(listener: Listener<GuildEvent>) = ListenerContext(listener) {
        guild += this
        withFilter(eventTypeFilter(GuildEvents.REQUEST))
    }

    fun onGuildMemberAdded(listener: Listener<GuildMemberEvent>) = ListenerContext(listener) {
        member += this
        withFilter(eventTypeFilter(GuildMemberEvents.ADDED))
    }

    fun onGuildMemberUpdated(listener: Listener<GuildMemberEvent>) = ListenerContext(listener) {
        member += this
        withFilter(eventTypeFilter(GuildMemberEvents.UPDATED))
    }

    fun onGuildMemberRemoved(listener: Listener<GuildMemberEvent>) = ListenerContext(listener) {
        member += this
        withFilter(eventTypeFilter(GuildMemberEvents.REMOVED))
    }

    fun onGuildMemberRequest(listener: Listener<GuildMemberEvent>) = ListenerContext(listener) {
        member += this
        withFilter(eventTypeFilter(GuildMemberEvents.REQUEST))
    }

    fun onGuildRoleCreated(listener: Listener<GuildRoleEvent>) = ListenerContext(listener) {
        role += this
        withFilter(eventTypeFilter(GuildRoleEvents.CREATED))
    }

    fun onGuildRoleUpdated(listener: Listener<GuildRoleEvent>) = ListenerContext(listener) {
        role += this
        withFilter(eventTypeFilter(GuildRoleEvents.UPDATED))
    }

    fun onGuildRoleDeleted(listener: Listener<GuildRoleEvent>) = ListenerContext(listener) {
        role += this
        withFilter(eventTypeFilter(GuildRoleEvents.DELETED))
    }

    fun onInteractionButton(listener: Listener<InteractionButtonEvent>) = ListenerContext(listener) {
        button += this
        withFilter(eventTypeFilter(InteractionEvents.BUTTON))
    }

    fun onInteractionCommand(listener: Listener<InteractionCommandEvent>) = ListenerContext(listener) {
        command += this
        withFilter(eventTypeFilter(InteractionEvents.COMMAND))
    }

    fun onLoginAdded(listener: Listener<LoginEvent>) = ListenerContext(listener) {
        login += this
        withFilter(eventTypeFilter(LoginEvents.ADDED))
    }

    fun onLoginRemoved(listener: Listener<LoginEvent>) = ListenerContext(listener) {
        login += this
        withFilter(eventTypeFilter(LoginEvents.REMOVED))
    }

    fun onLoginUpdated(listener: Listener<LoginEvent>) = ListenerContext(listener) {
        login += this
        withFilter(eventTypeFilter(LoginEvents.UPDATED))
    }

    fun onMessageCreated(listener: Listener<MessageEvent>) = ListenerContext(listener) {
        message += this
        withFilter(eventTypeFilter(MessageEvents.CREATED))
    }

    fun onMessageUpdated(listener: Listener<MessageEvent>) = ListenerContext(listener) {
        message += this
        withFilter(eventTypeFilter(MessageEvents.UPDATED))
    }

    fun onMessageDeleted(listener: Listener<MessageEvent>) = ListenerContext(listener) {
        message += this
        withFilter(eventTypeFilter(MessageEvents.DELETED))
    }

    fun onReactionAdded(listener: Listener<ReactionEvent>) = ListenerContext(listener) {
        reaction += this
        withFilter(eventTypeFilter(ReactionEvents.ADDED))
    }

    fun onReactionRemoved(listener: Listener<ReactionEvent>) = ListenerContext(listener) {
        reaction += this
        withFilter(eventTypeFilter(ReactionEvents.REMOVED))
    }

    fun onFriendRequest(listener: Listener<UserEvent>) = ListenerContext(listener) {
        user += this
        withFilter(eventTypeFilter(UserEvents.FRIEND_REQUEST))
    }

    /**
     * 与 Satori Server 建立 Websocket 连接
     * @param name Websocket 客户端的名称, 用于在日志打印时区分
     */
    @JvmOverloads
    fun connect(name: String? = null) {
        SatoriSocketClient(this, name).connect()
    }

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

    fun runEvent(event: Event) {
        val bot = Bot.of(event, properties)
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
        fun of(address: String, token: String? = null, version: String = "v1") =
            Satori(SimpleSatoriProperties(address, token, version))

        @JvmSynthetic
        inline fun of(properties: SatoriProperties, apply: Satori.() -> Unit) = of(properties).apply { apply() }

        @JvmSynthetic
        inline fun of(address: String, token: String? = null, version: String = "v1", apply: Satori.() -> Unit) =
            of(address, token, version).apply { apply() }
    }
}

class ListenerContext<T : Event>(private val listener: Listener<T>, init: (ListenerContext<T>.() -> Unit)) {
    private val filters = mutableListOf<(Bot, Event) -> Boolean>()

    init {
        init()
    }

    fun withFilter(filter: (Bot, Event) -> Boolean) = this.apply { filters += filter }
    fun run(bot: Bot, event: T) {
        for (filter in filters) if (!filter(bot, event)) return
        Executors.defaultThreadFactory().newThread { listener.invoke(bot, event) }.start()
    }
}


@Slf4j
class SatoriSocketClient @JvmOverloads constructor(
    private val client: Satori, private val name: String? = null
) : WebSocketClient(URI("ws://${client.properties.address}/${client.properties.version}/events"), Draft_6455()) {
    private var sequence: Number? = null
    private var heartbeat: ScheduledFuture<*>? = null
    private var reconnect: ScheduledFuture<*>? = null

    override fun onOpen(handshake: ServerHandshake) {
        log.info("[$name]: 成功建立 WebSocket 连接")
        reconnect?.cancel(false)
        sendIdentify()
    }

    override fun onMessage(message: String) {
        val signaling = Signaling.parse(message)
        when (signaling.op) {
            Signaling.READY -> {
                val ready = signaling.body as Ready
                log.info("[$name]: 成功建立事件推送(${ready.logins.size}): \n${
                    ready.logins.joinToString("\n") { "{platform: ${it.platform}, selfId: ${it.selfId}}" }
                }")
                // 心跳
                heartbeat?.cancel(false)
                val sendSignaling = Signaling(Signaling.PING)
                heartbeat = ScheduledThreadPoolExecutor(1, NamedThreadFactory("Heart")).scheduleAtFixedRate(
                    {
                        if (this.isOpen) send(
                            JSONObject.toJSONString(
                                sendSignaling
                            )
                        )
                    }, 10, 10, TimeUnit.SECONDS
                )
            }

            Signaling.EVENT -> sendEvent(signaling)
            Signaling.PONG -> log.debug("[$name]: 收到 PONG")
            else -> log.error("Unsupported $signaling")
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        log.info("[$name]: 断开连接, code: $code, reason: $reason, remote: $remote")
        heartbeat?.cancel(false)
        reconnect?.cancel(false)
        setReconnect()
    }

    override fun onError(e: Exception) {
        log.error("[$name]: 出现错误: $e")
        heartbeat?.cancel(false)
        reconnect?.cancel(false)
        setReconnect()
    }

    private fun sendIdentify() {
        val connection = Signaling(Signaling.IDENTIFY)
        val token = client.properties.token
        if (token != null || sequence != null) {
            val body = Identify()
            body.token = token
            body.sequence = sequence
            connection.body = body
        }
        this.send(JSONObject.toJSONString(connection))
    }

    private fun sendEvent(signaling: Signaling) {
        val body = signaling.body as Event
        log.info("[$name]: 接收事件: platform: ${body.platform}, selfId: ${body.selfId}, type: ${body.type}")
        log.debug("[$name]: 事件详细信息: $body")
        sequence = body.id
        client.runEvent(body)
    }

    private fun setReconnect() {
        reconnect = ScheduledThreadPoolExecutor(1, NamedThreadFactory("Reconnect")).scheduleAtFixedRate(
            {
                log.info(
                    "[$name]: 尝试重新连接"
                )
                connect()
            }, 3, 3, TimeUnit.SECONDS
        )
    }
}