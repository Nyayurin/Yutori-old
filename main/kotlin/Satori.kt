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

import java.util.concurrent.Executors

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
        for (context in this.event) context.run(bot, newEvent)
        when (newEvent) {
            is GuildEvent -> for (context in guild) context.run(bot, newEvent)
            is GuildMemberEvent -> for (context in member) context.run(bot, newEvent)
            is GuildRoleEvent -> for (context in role) context.run(bot, newEvent)
            is InteractionButtonEvent -> for (context in button) context.run(bot, newEvent)
            is InteractionCommandEvent -> for (context in command) context.run(bot, newEvent)
            is LoginEvent -> for (context in login) context.run(bot, newEvent)
            is MessageEvent -> for (context in message) context.run(bot, newEvent)
            is ReactionEvent -> for (context in reaction) context.run(bot, newEvent)
            is UserEvent -> for (context in user) context.run(bot, newEvent)
        }
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
        Executors.defaultThreadFactory().newThread { listener(bot, event) }.start()
    }
}