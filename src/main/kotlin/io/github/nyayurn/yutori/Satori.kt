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

typealias SatoriEventHandle<T> = (Bot, T) -> Unit

typealias SatoriMessageEventHandle = (Bot, MessageEvent, String) -> Unit

class Satori private constructor(val properties: SatoriProperties) {
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
        event.eventDelegate[handle] = properties
    }

    fun onGuildAdded(handle: SatoriEventHandle<GuildEvent>) {
        guild.addedDelegate[handle] = properties
    }

    fun onGuildUpdated(handle: SatoriEventHandle<GuildEvent>) {
        guild.updatedDelegate[handle] = properties
    }

    fun onGuildRemoved(handle: SatoriEventHandle<GuildEvent>) {
        guild.removedDelegate[handle] = properties
    }

    fun onGuildRequest(handle: SatoriEventHandle<GuildEvent>) {
        guild.requestDelegate[handle] = properties
    }

    fun onGuildMemberAdded(handle: SatoriEventHandle<GuildMemberEvent>) {
        member.addedDelegate[handle] = properties
    }

    fun onGuildMemberUpdated(handle: SatoriEventHandle<GuildMemberEvent>) {
        member.updatedDelegate[handle] = properties
    }

    fun onGuildMemberRemoved(handle: SatoriEventHandle<GuildMemberEvent>) {
        member.removedDelegate[handle] = properties
    }

    fun onGuildMemberRequest(handle: SatoriEventHandle<GuildMemberEvent>) {
        member.requestDelegate[handle] = properties
    }

    fun onGuildRoleCreated(handle: SatoriEventHandle<GuildRoleEvent>) {
        role.createdDelegate[handle] = properties
    }

    fun onGuildRoleUpdated(handle: SatoriEventHandle<GuildRoleEvent>) {
        role.updatedDelegate[handle] = properties
    }

    fun onGuildRoleDeleted(handle: SatoriEventHandle<GuildRoleEvent>) {
        role.deletedDelegate[handle] = properties
    }

    fun onInteractionButton(handle: SatoriEventHandle<InteractionButtonEvent>) {
        interaction.buttonDelegate[handle] = properties
    }

    fun onInteractionCommand(handle: SatoriEventHandle<InteractionCommandEvent>) {
        interaction.commandDelegate[handle] = properties
    }

    fun onLoginAdded(handle: SatoriEventHandle<LoginEvent>) {
        login.addedDelegate[handle] = properties
    }

    fun onLoginRemoved(handle: SatoriEventHandle<LoginEvent>) {
        login.removedDelegate[handle] = properties
    }

    fun onLoginUpdated(handle: SatoriEventHandle<LoginEvent>) {
        login.updatedDelegate[handle] = properties
    }

    fun onMessageCreated(handle: SatoriMessageEventHandle) {
        message.createdDelegate[handle] = properties
    }

    fun onMessageUpdated(handle: SatoriMessageEventHandle) {
        message.updatedDelegate[handle] = properties
    }

    fun onMessageDeleted(handle: SatoriMessageEventHandle) {
        message.deleteDelegate[handle] = properties
    }

    fun onReactionAdded(handle: SatoriEventHandle<ReactionEvent>) {
        reaction.addedDelegate[handle] = properties
    }

    fun onReactionRemoved(handle: SatoriEventHandle<ReactionEvent>) {
        reaction.removedDelegate[handle] = properties
    }

    fun onFriendRequest(handle: SatoriEventHandle<UserEvent>) {
        user.friendRequestDelegate[handle] = properties
    }

    fun connect(client: SatoriSocketClient) {
        client.connect()
    }

    fun connect() {
        (SimpleSatoriSocketClient.of(this)).connect()
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
        fun client(properties: SatoriProperties): Satori {
            return Satori(properties)
        }

        @JvmStatic
        fun client(address: String, token: String? = null, sequence: Number? = null): Satori {
            return Satori(SimpleSatoriProperties(address, token, sequence))
        }

        // 以下仅 kotlin 使用
        @JvmSynthetic
        fun client(properties: SatoriProperties, apply: Satori.() -> Unit): Satori {
            return Satori(properties).apply { apply() }
        }

        @JvmSynthetic
        fun client(address: String, token: String? = null, sequence: Number? = null, apply: Satori.() -> Unit): Satori {
            return client(SimpleSatoriProperties(address, token, sequence), apply)
        }
    }
}

private object EventListenerContext {
    val eventDelegate = mutableMapOf<SatoriEventHandle<Event>, SatoriProperties>()
    infix fun run(event: Event) {
        eventDelegate.forEach { it.key(Bot.of(event, it.value), event) }
    }
}

private object GuildListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, SatoriProperties>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, SatoriProperties>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, SatoriProperties>()
    val requestDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, SatoriProperties>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildEvents.ADDED -> addedDelegate.forEach { it.key(Bot.of(event, it.value), GuildEvent.parse(event)) }
            GuildEvents.UPDATED -> updatedDelegate.forEach { it.key(Bot.of(event, it.value), GuildEvent.parse(event)) }
            GuildEvents.REMOVED -> removedDelegate.forEach { it.key(Bot.of(event, it.value), GuildEvent.parse(event)) }
            GuildEvents.REQUEST -> requestDelegate.forEach { it.key(Bot.of(event, it.value), GuildEvent.parse(event)) }
        }
    }
}

private object GuildMemberListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, SatoriProperties>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, SatoriProperties>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, SatoriProperties>()
    val requestDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, SatoriProperties>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildMemberEvents.ADDED -> addedDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    GuildMemberEvent.parse(event)
                )
            }

            GuildMemberEvents.UPDATED -> updatedDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    GuildMemberEvent.parse(event)
                )
            }

            GuildMemberEvents.REMOVED -> removedDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    GuildMemberEvent.parse(event)
                )
            }

            GuildMemberEvents.REQUEST -> requestDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    GuildMemberEvent.parse(event)
                )
            }
        }
    }
}

private object GuildRoleListenerContext {
    val createdDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, SatoriProperties>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, SatoriProperties>()
    val deletedDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, SatoriProperties>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildRoleEvents.CREATED -> createdDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    GuildRoleEvent.parse(event)
                )
            }

            GuildRoleEvents.UPDATED -> updatedDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    GuildRoleEvent.parse(event)
                )
            }

            GuildRoleEvents.DELETED -> deletedDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    GuildRoleEvent.parse(event)
                )
            }
        }
    }
}

private object InteractionListenerContext {
    val buttonDelegate = mutableMapOf<SatoriEventHandle<InteractionButtonEvent>, SatoriProperties>()
    val commandDelegate = mutableMapOf<SatoriEventHandle<InteractionCommandEvent>, SatoriProperties>()
    infix fun run(event: Event) {
        when (event.type) {
            InteractionEvents.BUTTON -> buttonDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    InteractionButtonEvent.parse(event)
                )
            }

            InteractionEvents.COMMAND -> commandDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    InteractionCommandEvent.parse(event)
                )
            }
        }
    }
}

private object LoginListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, SatoriProperties>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, SatoriProperties>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, SatoriProperties>()
    infix fun run(event: Event) {
        when (event.type) {
            LoginEvents.ADDED -> addedDelegate.forEach { it.key(Bot.of(event, it.value), LoginEvent.parse(event)) }
            LoginEvents.REMOVED -> removedDelegate.forEach { it.key(Bot.of(event, it.value), LoginEvent.parse(event)) }
            LoginEvents.UPDATED -> updatedDelegate.forEach { it.key(Bot.of(event, it.value), LoginEvent.parse(event)) }
        }
    }
}

private object MessageListenerContext {
    val createdDelegate = mutableMapOf<SatoriMessageEventHandle, SatoriProperties>()
    val updatedDelegate = mutableMapOf<SatoriMessageEventHandle, SatoriProperties>()
    val deleteDelegate = mutableMapOf<SatoriMessageEventHandle, SatoriProperties>()
    infix fun run(event: Event) {
        when (event.type) {
            MessageEvents.CREATED -> createdDelegate.forEach {
                val messageEvent = MessageEvent.parse(event)
                it.key(
                    Bot.of(event, it.value),
                    messageEvent,
                    Jsoup.parse(messageEvent.message.content).body().text()
                )
            }

            MessageEvents.UPDATED -> updatedDelegate.forEach {
                val messageEvent = MessageEvent.parse(event)
                it.key(
                    Bot.of(event, it.value),
                    messageEvent,
                    Jsoup.parse(messageEvent.message.content).body().text()
                )
            }

            MessageEvents.DELETED -> deleteDelegate.forEach {
                val messageEvent = MessageEvent.parse(event)
                it.key(
                    Bot.of(event, it.value),
                    messageEvent,
                    Jsoup.parse(messageEvent.message.content).body().text()
                )
            }
        }
    }
}

private object ReactionListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<ReactionEvent>, SatoriProperties>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<ReactionEvent>, SatoriProperties>()
    infix fun run(event: Event) {
        when (event.type) {
            ReactionEvents.ADDED -> addedDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    ReactionEvent.parse(event)
                )
            }

            ReactionEvents.REMOVED -> removedDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    ReactionEvent.parse(event)
                )
            }
        }
    }
}

private object UserListenerContext {
    val friendRequestDelegate = mutableMapOf<SatoriEventHandle<UserEvent>, SatoriProperties>()
    infix fun run(event: Event) {
        when (event.type) {
            UserEvents.FRIEND_REQUEST -> friendRequestDelegate.forEach {
                it.key(
                    Bot.of(event, it.value),
                    UserEvent.parse(event)
                )
            }
        }
    }
}