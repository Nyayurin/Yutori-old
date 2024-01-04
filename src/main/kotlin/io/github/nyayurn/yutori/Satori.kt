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

import org.jsoup.Jsoup
import java.util.concurrent.Executors

/**
 * Satori 事件处理器
 */
typealias SatoriEventHandle<T> = (Bot, T) -> Unit

/**
 * Satori 消息事件处理器
 */
typealias SatoriMessageEventHandle = (Bot, MessageEvent, String) -> Unit

private fun <T : Event> Array<out (T) -> Boolean>.filter(event: T) =
    this.isEmpty() || this.map { it(event) }.reduce { acc, unit -> acc && unit }

/**
 * Satori 事件处理器上下文
 * @property properties 配置
 * @property event 事件监听器上下文
 * @property guild 群组事件监听器上下文
 * @property member 群组成员事件监听器上下文
 * @property role 群组角色事件监听器上下文
 * @property interaction 交互事件监听器上下文
 * @property login 登录信息事件监听器上下文
 * @property message 消息事件监听器上下文
 * @property reaction 表态事件监听器上下文
 * @property user 用户事件监听器上下文
 */
class Satori private constructor(val properties: SatoriProperties) {
    private val event = EventListenerContext()
    private val guild = GuildListenerContext()
    private val member = GuildMemberListenerContext()
    private val role = GuildRoleListenerContext()
    private val interaction = InteractionListenerContext()
    private val login = LoginListenerContext()
    private val message = MessageListenerContext()
    private val reaction = ReactionListenerContext()
    private val user = UserListenerContext()

    @JvmOverloads
    fun onEvent(
        vararg predicate: (Event) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<Event>
    ) {
        event.eventDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildAdded(
        vararg predicate: (GuildEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildEvent>
    ) {
        guild.addedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildUpdated(
        vararg predicate: (GuildEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildEvent>
    ) {
        guild.updatedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildRemoved(
        vararg predicate: (GuildEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildEvent>
    ) {
        guild.removedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildRequest(
        vararg predicate: (GuildEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildEvent>
    ) {
        guild.requestDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildMemberAdded(
        vararg predicate: (GuildMemberEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildMemberEvent>
    ) {
        member.addedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildMemberUpdated(
        vararg predicate: (GuildMemberEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildMemberEvent>
    ) {
        member.updatedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildMemberRemoved(
        vararg predicate: (GuildMemberEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildMemberEvent>
    ) {
        member.removedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildMemberRequest(
        vararg predicate: (GuildMemberEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildMemberEvent>
    ) {
        member.requestDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildRoleCreated(
        vararg predicate: (GuildRoleEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildRoleEvent>
    ) {
        role.createdDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildRoleUpdated(
        vararg predicate: (GuildRoleEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildRoleEvent>
    ) {
        role.updatedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onGuildRoleDeleted(
        vararg predicate: (GuildRoleEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<GuildRoleEvent>
    ) {
        role.deletedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onInteractionButton(
        vararg predicate: (InteractionButtonEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<InteractionButtonEvent>
    ) {
        interaction.buttonDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onInteractionCommand(
        vararg predicate: (InteractionCommandEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<InteractionCommandEvent>,
    ) {
        interaction.commandDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onLoginAdded(
        vararg predicate: (LoginEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<LoginEvent>
    ) {
        login.addedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onLoginRemoved(
        vararg predicate: (LoginEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<LoginEvent>
    ) {
        login.removedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onLoginUpdated(
        vararg predicate: (LoginEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<LoginEvent>
    ) {
        login.updatedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onMessageCreated(
        vararg predicate: (MessageEvent) -> Boolean = arrayOf(),
        handle: SatoriMessageEventHandle
    ) {
        message.createdDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onMessageUpdated(
        vararg predicate: (MessageEvent) -> Boolean = arrayOf(),
        handle: SatoriMessageEventHandle
    ) {
        message.updatedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onMessageDeleted(
        vararg predicate: (MessageEvent) -> Boolean = arrayOf(),
        handle: SatoriMessageEventHandle
    ) {
        message.deleteDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onReactionAdded(
        vararg predicate: (ReactionEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<ReactionEvent>
    ) {
        reaction.addedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onReactionRemoved(
        vararg predicate: (ReactionEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<ReactionEvent>
    ) {
        reaction.removedDelegate[handle] = predicate
    }

    @JvmOverloads
    fun onFriendRequest(
        vararg predicate: (UserEvent) -> Boolean = arrayOf(),
        handle: SatoriEventHandle<UserEvent>
    ) {
        user.friendRequestDelegate[handle] = predicate
    }

    @JvmOverloads
    fun connect(name: String? = null) {
        (SatoriSocketClient.of(this, name)).connect()
    }

    fun runEvent(event: Event) {
        if (event.user?.id == event.selfId && !properties.listenSelfEvent) return
        Executors.defaultThreadFactory().newThread {
            val bot = properties.botDirect() ?: Bot.of(event, properties)
            this.event.run(bot, event)
            this.guild.run(bot, event)
            this.member.run(bot, event)
            this.role.run(bot, event)
            this.interaction.run(bot, event)
            this.login.run(bot, event)
            this.message.run(bot, event)
            this.reaction.run(bot, event)
            this.user.run(bot, event)
        }.start()
    }

    companion object {
        @JvmStatic
        fun client(properties: SatoriProperties) = Satori(properties)

        @JvmStatic
        @JvmOverloads
        fun client(
            address: String,
            token: String? = null,
            version: String = "v1",
            sequence: Number? = null,
            listenSelfEvent: Boolean = false,
            botDirect: () -> Bot?
        ) = Satori(SimpleSatoriProperties(address, token, version, sequence, listenSelfEvent, botDirect))

        // 以下仅 kotlin 使用
        @JvmSynthetic
        fun client(properties: SatoriProperties, apply: Satori.() -> Unit) = Satori(properties).apply { apply() }

        @JvmSynthetic
        fun client(
            address: String,
            token: String? = null,
            version: String = "v1",
            sequence: Number? = null,
            listenSelfEvent: Boolean = false,
            botDirect: () -> Bot?,
            apply: Satori.() -> Unit
        ) = Satori(SimpleSatoriProperties(address, token, version, sequence, listenSelfEvent, botDirect)).apply { apply() }
    }
}

private class EventListenerContext {
    val eventDelegate = mutableMapOf<SatoriEventHandle<Event>, Array<out (Event) -> Boolean>>()
    fun run(bot: Bot, event: Event) = eventDelegate.filter { it.value.filter(event) }.forEach { it.key(bot, event) }
}

private class GuildListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Array<out (GuildEvent) -> Boolean>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Array<out (GuildEvent) -> Boolean>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Array<out (GuildEvent) -> Boolean>>()
    val requestDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Array<out (GuildEvent) -> Boolean>>()
    fun run(bot: Bot, event: Event) {
        when (event.type) {
            GuildEvents.ADDED -> GuildEvent.parse(event) { newEvent ->
                addedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            GuildEvents.UPDATED -> GuildEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            GuildEvents.REMOVED -> GuildEvent.parse(event) { newEvent ->
                removedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            GuildEvents.REQUEST -> GuildEvent.parse(event) { newEvent ->
                requestDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }
        }
    }
}

private class GuildMemberListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Array<out (GuildMemberEvent) -> Boolean>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Array<out (GuildMemberEvent) -> Boolean>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Array<out (GuildMemberEvent) -> Boolean>>()
    val requestDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Array<out (GuildMemberEvent) -> Boolean>>()

    fun run(bot: Bot, event: Event) {
        when (event.type) {
            GuildMemberEvents.ADDED -> GuildMemberEvent.parse(event) { newEvent ->
                addedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            GuildMemberEvents.UPDATED -> GuildMemberEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            GuildMemberEvents.REMOVED -> GuildMemberEvent.parse(event) { newEvent ->
                removedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            GuildMemberEvents.REQUEST -> GuildMemberEvent.parse(event) { newEvent ->
                requestDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }
        }
    }
}

private class GuildRoleListenerContext {
    val createdDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Array<out (GuildRoleEvent) -> Boolean>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Array<out (GuildRoleEvent) -> Boolean>>()
    val deletedDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Array<out (GuildRoleEvent) -> Boolean>>()
    fun run(bot: Bot, event: Event) {
        when (event.type) {
            GuildRoleEvents.CREATED -> GuildRoleEvent.parse(event) { newEvent ->
                createdDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            GuildRoleEvents.UPDATED -> GuildRoleEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            GuildRoleEvents.DELETED -> GuildRoleEvent.parse(event) { newEvent ->
                deletedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }
        }
    }
}

private class InteractionListenerContext {
    val buttonDelegate = mutableMapOf<SatoriEventHandle<InteractionButtonEvent>, Array<out (InteractionButtonEvent) -> Boolean>>()
    val commandDelegate = mutableMapOf<SatoriEventHandle<InteractionCommandEvent>, Array<out (InteractionCommandEvent) -> Boolean>>()

    fun run(bot: Bot, event: Event) {
        when (event.type) {
            InteractionEvents.BUTTON -> InteractionButtonEvent.parse(event) { newEvent ->
                buttonDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            InteractionEvents.COMMAND -> InteractionCommandEvent.parse(event) { newEvent ->
                commandDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }
        }
    }
}

private class LoginListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Array<out (LoginEvent) -> Boolean>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Array<out (LoginEvent) -> Boolean>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Array<out (LoginEvent) -> Boolean>>()
    fun run(bot: Bot, event: Event) {
        when (event.type) {
            LoginEvents.ADDED -> LoginEvent.parse(event) { newEvent ->
                addedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            LoginEvents.REMOVED -> LoginEvent.parse(event) { newEvent ->
                removedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            LoginEvents.UPDATED -> LoginEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }
        }
    }
}

private class MessageListenerContext {
    val createdDelegate = mutableMapOf<SatoriMessageEventHandle, Array<out (MessageEvent) -> Boolean>>()
    val updatedDelegate = mutableMapOf<SatoriMessageEventHandle, Array<out (MessageEvent) -> Boolean>>()
    val deleteDelegate = mutableMapOf<SatoriMessageEventHandle, Array<out (MessageEvent) -> Boolean>>()
    fun run(bot: Bot, event: Event) {
        when (event.type) {
            MessageEvents.CREATED -> MessageEvent.parse(event) { newEvent ->
                createdDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent, filterText(newEvent.message.content)) }
            }

            MessageEvents.UPDATED -> MessageEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent, filterText(newEvent.message.content)) }
            }

            MessageEvents.DELETED -> MessageEvent.parse(event) { newEvent ->
                deleteDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent, filterText(newEvent.message.content)) }
            }
        }
    }

    fun filterText(raw: String): String = Jsoup.parse(raw).body().text()
}

private class ReactionListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<ReactionEvent>, Array<out (ReactionEvent) -> Boolean>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<ReactionEvent>, Array<out (ReactionEvent) -> Boolean>>()
    fun run(bot: Bot, event: Event) {
        when (event.type) {
            ReactionEvents.ADDED -> ReactionEvent.parse(event) { newEvent ->
                addedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }

            ReactionEvents.REMOVED -> ReactionEvent.parse(event) { newEvent ->
                removedDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }
        }
    }
}

private class UserListenerContext {
    val friendRequestDelegate = mutableMapOf<SatoriEventHandle<UserEvent>, Array<out (UserEvent) -> Boolean>>()
    fun run(bot: Bot, event: Event) {
        when (event.type) {
            UserEvents.FRIEND_REQUEST -> UserEvent.parse(event) { newEvent ->
                friendRequestDelegate.filter { it.value.filter(newEvent) }.forEach { it.key(bot, newEvent) }
            }
        }
    }
}