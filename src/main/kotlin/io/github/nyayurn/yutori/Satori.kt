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

interface SatoriEventHandle<in T : Event> : Function2<Bot, T, Unit> {
    override operator fun invoke(bot: Bot, event: T)
}

typealias SatoriMessageEventHandle = Function3<Bot, MessageEvent, String, Unit>

class Satori private constructor(val properties: Properties) {
    private val event = EventListenerContext
    private val guild = GuildListenerContext
    private val member = GuildMemberListenerContext
    private val role = GuildRoleListenerContext
    private val interaction = InteractionListenerContext
    private val login = LoginListenerContext
    private val message = MessageListenerContext
    private val reaction = ReactionListenerContext
    private val user = UserListenerContext

    fun onEvent(handle: SatoriEventHandle<Event>) {
        event.eventDelegate[properties] = handle
    }

    fun onGuildAdded(handle: SatoriEventHandle<GuildEvent>) {
        guild.addedDelegate[properties] = handle
    }

    fun onGuildUpdated(handle: SatoriEventHandle<GuildEvent>) {
        guild.updatedDelegate[properties] = handle
    }

    fun onGuildRemoved(handle: SatoriEventHandle<GuildEvent>) {
        guild.removedDelegate[properties] = handle
    }

    fun onGuildRequest(handle: SatoriEventHandle<GuildEvent>) {
        guild.requestDelegate[properties] = handle
    }

    fun onGuildMemberAdded(handle: SatoriEventHandle<GuildMemberEvent>) {
        member.addedDelegate[properties] = handle
    }

    fun onGuildMemberUpdated(handle: SatoriEventHandle<GuildMemberEvent>) {
        member.updatedDelegate[properties] = handle
    }

    fun onGuildMemberRemoved(handle: SatoriEventHandle<GuildMemberEvent>) {
        member.removedDelegate[properties] = handle
    }

    fun onGuildMemberRequest(handle: SatoriEventHandle<GuildMemberEvent>) {
        member.requestDelegate[properties] = handle
    }

    fun onGuildRoleCreated(handle: SatoriEventHandle<GuildRoleEvent>) {
        role.createdDelegate[properties] = handle
    }

    fun onGuildRoleUpdated(handle: SatoriEventHandle<GuildRoleEvent>) {
        role.updatedDelegate[properties] = handle
    }

    fun onGuildRoleDeleted(handle: SatoriEventHandle<GuildRoleEvent>) {
        role.deletedDelegate[properties] = handle
    }

    fun onInteractionButton(handle: SatoriEventHandle<InteractionButtonEvent>) {
        interaction.buttonDelegate[properties] = handle
    }

    fun onInteractionCommand(handle: SatoriEventHandle<InteractionCommandEvent>) {
        interaction.commandDelegate[properties] = handle
    }

    fun onLoginAdded(handle: SatoriEventHandle<LoginEvent>) {
        login.addedDelegate[properties] = handle
    }

    fun onLoginRemoved(handle: SatoriEventHandle<LoginEvent>) {
        login.removedDelegate[properties] = handle
    }

    fun onLoginUpdated(handle: SatoriEventHandle<LoginEvent>) {
        login.updatedDelegate[properties] = handle
    }

    fun onMessageCreated(handle: SatoriMessageEventHandle) {
        message.createdDelegate[properties] = handle
    }

    fun onMessageUpdated(handle: SatoriMessageEventHandle) {
        message.updatedDelegate[properties] = handle
    }

    fun onMessageDeleted(handle: SatoriMessageEventHandle) {
        message.deleteDelegate[properties] = handle
    }

    fun onReactionAdded(handle: SatoriEventHandle<ReactionEvent>) {
        reaction.addedDelegate[properties] = handle
    }

    fun onReactionRemoved(handle: SatoriEventHandle<ReactionEvent>) {
        reaction.removedDelegate[properties] = handle
    }

    fun onFriendRequest(handle: SatoriEventHandle<UserEvent>) {
        user.friendRequestDelegate[properties] = handle
    }

    fun connect() {
        (SatoriSocketClient.of(this)).connect()
    }

    fun runEvent(event: Event) {
        Executors.defaultThreadFactory().newThread {
            this.event run event
            this.guild run event
            this.member run event
            this.role run event
            this.interaction run event
            this.login run event
            this.message run event
            this.reaction run event
            this.user run event
        }.start()
    }

    companion object {
        @JvmStatic
        fun client(properties: Properties): Satori {
            return Satori(properties)
        }

        @JvmStatic
        fun client(address: String, token: String): Satori {
            return Satori(Properties(address, token))
        }

        // 以下仅 kotlin 使用
        @JvmSynthetic
        fun client(address: String, token: String?, apply: Satori.() -> Unit): Satori {
            return client(Properties(address, token), apply)
        }

        @JvmSynthetic
        fun client(properties: Properties, apply: Satori.() -> Unit): Satori {
            return Satori(properties).apply { apply() }
        }
    }
}

private object EventListenerContext {
    val eventDelegate = mutableMapOf<Properties, SatoriEventHandle<Event>>()
    infix fun run(event: Event) {
        eventDelegate.forEach { it.value(Bot.of(event, it.key), event) }
    }
}

private object GuildListenerContext {
    val addedDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildEvent>>()
    val updatedDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildEvent>>()
    val removedDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildEvent>>()
    val requestDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildEvent>>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildEvents.ADDED -> addedDelegate.forEach { it.value(Bot.of(event, it.key), GuildEvent.parse(event)) }
            GuildEvents.UPDATED -> updatedDelegate.forEach { it.value(Bot.of(event, it.key), GuildEvent.parse(event)) }
            GuildEvents.REMOVED -> removedDelegate.forEach { it.value(Bot.of(event, it.key), GuildEvent.parse(event)) }
            GuildEvents.REQUEST -> requestDelegate.forEach { it.value(Bot.of(event, it.key), GuildEvent.parse(event)) }
        }
    }
}

private object GuildMemberListenerContext {
    val addedDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildMemberEvent>>()
    val updatedDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildMemberEvent>>()
    val removedDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildMemberEvent>>()
    val requestDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildMemberEvent>>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildMemberEvents.ADDED -> addedDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    GuildMemberEvent.parse(event)
                )
            }

            GuildMemberEvents.UPDATED -> updatedDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    GuildMemberEvent.parse(event)
                )
            }

            GuildMemberEvents.REMOVED -> removedDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    GuildMemberEvent.parse(event)
                )
            }

            GuildMemberEvents.REQUEST -> requestDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    GuildMemberEvent.parse(event)
                )
            }
        }
    }
}

private object GuildRoleListenerContext {
    val createdDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildRoleEvent>>()
    val updatedDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildRoleEvent>>()
    val deletedDelegate = mutableMapOf<Properties, SatoriEventHandle<GuildRoleEvent>>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildRoleEvents.CREATED -> createdDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    GuildRoleEvent.parse(event)
                )
            }

            GuildRoleEvents.UPDATED -> updatedDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    GuildRoleEvent.parse(event)
                )
            }

            GuildRoleEvents.DELETED -> deletedDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    GuildRoleEvent.parse(event)
                )
            }
        }
    }
}

private object InteractionListenerContext {
    val buttonDelegate = mutableMapOf<Properties, SatoriEventHandle<InteractionButtonEvent>>()
    val commandDelegate = mutableMapOf<Properties, SatoriEventHandle<InteractionCommandEvent>>()
    infix fun run(event: Event) {
        when (event.type) {
            InteractionEvents.BUTTON -> buttonDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    InteractionButtonEvent.parse(event)
                )
            }

            InteractionEvents.COMMAND -> commandDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    InteractionCommandEvent.parse(event)
                )
            }
        }
    }
}

private object LoginListenerContext {
    val addedDelegate = mutableMapOf<Properties, SatoriEventHandle<LoginEvent>>()
    val removedDelegate = mutableMapOf<Properties, SatoriEventHandle<LoginEvent>>()
    val updatedDelegate = mutableMapOf<Properties, SatoriEventHandle<LoginEvent>>()
    infix fun run(event: Event) {
        when (event.type) {
            LoginEvents.ADDED -> addedDelegate.forEach { it.value(Bot.of(event, it.key), LoginEvent.parse(event)) }
            LoginEvents.REMOVED -> removedDelegate.forEach { it.value(Bot.of(event, it.key), LoginEvent.parse(event)) }
            LoginEvents.UPDATED -> updatedDelegate.forEach { it.value(Bot.of(event, it.key), LoginEvent.parse(event)) }
        }
    }
}

private object MessageListenerContext {
    val createdDelegate = mutableMapOf<Properties, SatoriMessageEventHandle>()
    val updatedDelegate = mutableMapOf<Properties, SatoriMessageEventHandle>()
    val deleteDelegate = mutableMapOf<Properties, SatoriMessageEventHandle>()
    infix fun run(event: Event) {
        when (event.type) {
            MessageEvents.CREATED -> createdDelegate.forEach {
                val messageEvent = MessageEvent.parse(event)
                it.value(
                    Bot.of(event, it.key),
                    messageEvent,
                    Jsoup.parse(messageEvent.message.content).body().text()
                )
            }

            MessageEvents.UPDATED -> updatedDelegate.forEach {
                val messageEvent = MessageEvent.parse(event)
                it.value(
                    Bot.of(event, it.key),
                    messageEvent,
                    Jsoup.parse(messageEvent.message.content).body().text()
                )
            }

            MessageEvents.DELETED -> deleteDelegate.forEach {
                val messageEvent = MessageEvent.parse(event)
                it.value(
                    Bot.of(event, it.key),
                    messageEvent,
                    Jsoup.parse(messageEvent.message.content).body().text()
                )
            }
        }
    }
}

private object ReactionListenerContext {
    val addedDelegate = mutableMapOf<Properties, SatoriEventHandle<ReactionEvent>>()
    val removedDelegate = mutableMapOf<Properties, SatoriEventHandle<ReactionEvent>>()
    infix fun run(event: Event) {
        when (event.type) {
            ReactionEvents.ADDED -> addedDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    ReactionEvent.parse(event)
                )
            }

            ReactionEvents.REMOVED -> removedDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    ReactionEvent.parse(event)
                )
            }
        }
    }
}

private object UserListenerContext {
    val friendRequestDelegate = mutableMapOf<Properties, SatoriEventHandle<UserEvent>>()
    infix fun run(event: Event) {
        when (event.type) {
            UserEvents.FRIEND_REQUEST -> friendRequestDelegate.forEach {
                it.value(
                    Bot.of(event, it.key),
                    UserEvent.parse(event)
                )
            }
        }
    }
}