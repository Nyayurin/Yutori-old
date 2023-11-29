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

object DispatcherListener {
    private val event = EventListenerContext
    private val guild = GuildListenerContext
    private val member = GuildMemberListenerContext
    private val role = GuildRoleListenerContext
    private val interaction = InteractionListenerContext
    private val login = LoginListenerContext
    private val message = MessageListenerContext
    private val reaction = ReactionListenerContext
    private val user = UserListenerContext
    fun onEvent(properties: Properties, function: (bot: Bot, event: Event) -> Unit) {
        event.eventDelegate[properties] = function
    }

    fun onGuildAdded(properties: Properties, function: (bot: Bot, event: GuildEvent) -> Unit) {
        guild.addedDelegate[properties] = function
    }

    fun onGuildUpdated(properties: Properties, function: (bot: Bot, event: GuildEvent) -> Unit) {
        guild.updatedDelegate[properties] = function
    }

    fun onGuildRemoved(properties: Properties, function: (bot: Bot, event: GuildEvent) -> Unit) {
        guild.removedDelegate[properties] = function
    }

    fun onGuildRequest(properties: Properties, function: (bot: Bot, event: GuildEvent) -> Unit) {
        guild.requestDelegate[properties] = function
    }

    fun onGuildMemberAdded(properties: Properties, function: (bot: Bot, event: GuildMemberEvent) -> Unit) {
        member.addedDelegate[properties] = function
    }

    fun onGuildMemberUpdated(properties: Properties, function: (bot: Bot, event: GuildMemberEvent) -> Unit) {
        member.updatedDelegate[properties] = function
    }

    fun onGuildMemberRemoved(properties: Properties, function: (bot: Bot, event: GuildMemberEvent) -> Unit) {
        member.removedDelegate[properties] = function
    }

    fun onGuildMemberRequest(properties: Properties, function: (bot: Bot, event: GuildMemberEvent) -> Unit) {
        member.requestDelegate[properties] = function
    }

    fun onGuildRoleCreated(properties: Properties, function: (bot: Bot, event: GuildRoleEvent) -> Unit) {
        role.createdDelegate[properties] = function
    }

    fun onGuildRoleUpdated(properties: Properties, function: (bot: Bot, event: GuildRoleEvent) -> Unit) {
        role.updatedDelegate[properties] = function
    }

    fun onGuildRoleDeleted(properties: Properties, function: (bot: Bot, event: GuildRoleEvent) -> Unit) {
        role.deletedDelegate[properties] = function
    }

    fun onInteractionButton(properties: Properties, function: (bot: Bot, event: InteractionButtonEvent) -> Unit) {
        interaction.buttonDelegate[properties] = function
    }

    fun onInteractionCommand(properties: Properties, function: (bot: Bot, event: InteractionCommandEvent) -> Unit) {
        interaction.commandDelegate[properties] = function
    }

    fun onLoginAdded(properties: Properties, function: (bot: Bot, event: LoginEvent) -> Unit) {
        login.addedDelegate[properties] = function
    }

    fun onLoginRemoved(properties: Properties, function: (bot: Bot, event: LoginEvent) -> Unit) {
        login.removedDelegate[properties] = function
    }

    fun onLoginUpdated(properties: Properties, function: (bot: Bot, event: LoginEvent) -> Unit) {
        login.updatedDelegate[properties] = function
    }

    fun onMessageCreated(properties: Properties, function: (bot: Bot, event: MessageEvent, msg: String) -> Unit) {
        message.createdDelegate[properties] = function
    }

    fun onMessageUpdated(properties: Properties, function: (bot: Bot, event: MessageEvent, msg: String) -> Unit) {
        message.updatedDelegate[properties] = function
    }

    fun onMessageDeleted(properties: Properties, function: (bot: Bot, event: MessageEvent, msg: String) -> Unit) {
        message.deleteDelegate[properties] = function
    }

    fun onReactionAdded(properties: Properties, function: (bot: Bot, event: ReactionEvent) -> Unit) {
        reaction.addedDelegate[properties] = function
    }

    fun onReactionRemoved(properties: Properties, function: (bot: Bot, event: ReactionEvent) -> Unit) {
        reaction.removedDelegate[properties] = function
    }

    fun onFriendRequest(properties: Properties, function: (bot: Bot, event: UserEvent) -> Unit) {
        user.friendRequestDelegate[properties] = function
    }

    fun runEvent(event: Event) {
        Executors.defaultThreadFactory().newThread {
            this.event run event
            guild run event
            member run event
            role run event
            interaction run event
            login run event
            message run event
            reaction run event
            user run event
        }.start()
    }
}

private object EventListenerContext {
    val eventDelegate = mutableMapOf<Properties, (Bot, Event) -> Unit>()
    infix fun run(event: Event) {
        eventDelegate.forEach { it.value(Bot.of(event, it.key), event) }
    }
}

private object GuildListenerContext {
    val addedDelegate = mutableMapOf<Properties, (Bot, GuildEvent) -> Unit>()
    val updatedDelegate = mutableMapOf<Properties, (Bot, GuildEvent) -> Unit>()
    val removedDelegate = mutableMapOf<Properties, (Bot, GuildEvent) -> Unit>()
    val requestDelegate = mutableMapOf<Properties, (Bot, GuildEvent) -> Unit>()
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
    val addedDelegate = mutableMapOf<Properties, (Bot, GuildMemberEvent) -> Unit>()
    val updatedDelegate = mutableMapOf<Properties, (Bot, GuildMemberEvent) -> Unit>()
    val removedDelegate = mutableMapOf<Properties, (Bot, GuildMemberEvent) -> Unit>()
    val requestDelegate = mutableMapOf<Properties, (Bot, GuildMemberEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildMemberEvents.ADDED -> addedDelegate.forEach { it.value(Bot.of(event, it.key), GuildMemberEvent.parse(event)) }
            GuildMemberEvents.UPDATED -> updatedDelegate.forEach { it.value(Bot.of(event, it.key), GuildMemberEvent.parse(event)) }
            GuildMemberEvents.REMOVED -> removedDelegate.forEach { it.value(Bot.of(event, it.key), GuildMemberEvent.parse(event)) }
            GuildMemberEvents.REQUEST -> requestDelegate.forEach { it.value(Bot.of(event, it.key), GuildMemberEvent.parse(event)) }
        }
    }
}

private object GuildRoleListenerContext {
    val createdDelegate = mutableMapOf<Properties, (Bot, GuildRoleEvent) -> Unit>()
    val updatedDelegate = mutableMapOf<Properties, (Bot, GuildRoleEvent) -> Unit>()
    val deletedDelegate = mutableMapOf<Properties, (Bot, GuildRoleEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildRoleEvents.CREATED -> createdDelegate.forEach { it.value(Bot.of(event, it.key), GuildRoleEvent.parse(event)) }
            GuildRoleEvents.UPDATED -> updatedDelegate.forEach { it.value(Bot.of(event, it.key), GuildRoleEvent.parse(event)) }
            GuildRoleEvents.DELETED -> deletedDelegate.forEach { it.value(Bot.of(event, it.key), GuildRoleEvent.parse(event)) }
        }
    }
}

private object InteractionListenerContext {
    val buttonDelegate = mutableMapOf<Properties, (Bot, InteractionButtonEvent) -> Unit>()
    val commandDelegate = mutableMapOf<Properties, (Bot, InteractionCommandEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            InteractionEvents.BUTTON -> buttonDelegate.forEach { it.value(Bot.of(event, it.key), InteractionButtonEvent.parse(event)) }
            InteractionEvents.COMMAND -> commandDelegate.forEach { it.value(Bot.of(event, it.key), InteractionCommandEvent.parse(event)) }
        }
    }
}

private object LoginListenerContext {
    val addedDelegate = mutableMapOf<Properties, (Bot, LoginEvent) -> Unit>()
    val removedDelegate = mutableMapOf<Properties, (Bot, LoginEvent) -> Unit>()
    val updatedDelegate = mutableMapOf<Properties, (Bot, LoginEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            LoginEvents.ADDED -> addedDelegate.forEach { it.value(Bot.of(event, it.key), LoginEvent.parse(event)) }
            LoginEvents.REMOVED -> removedDelegate.forEach { it.value(Bot.of(event, it.key), LoginEvent.parse(event)) }
            LoginEvents.UPDATED -> updatedDelegate.forEach { it.value(Bot.of(event, it.key), LoginEvent.parse(event)) }
        }
    }
}

private object MessageListenerContext {
    val createdDelegate = mutableMapOf<Properties, (Bot, MessageEvent, String) -> Unit>()
    val updatedDelegate = mutableMapOf<Properties, (Bot, MessageEvent, String) -> Unit>()
    val deleteDelegate = mutableMapOf<Properties, (Bot, MessageEvent, String) -> Unit>()
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
    val addedDelegate = mutableMapOf<Properties, (Bot, ReactionEvent) -> Unit>()
    val removedDelegate = mutableMapOf<Properties, (Bot, ReactionEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            ReactionEvents.ADDED -> addedDelegate.forEach { it.value(Bot.of(event, it.key), ReactionEvent.parse(event)) }
            ReactionEvents.REMOVED -> removedDelegate.forEach { it.value(Bot.of(event, it.key), ReactionEvent.parse(event)) }
        }
    }
}

private object UserListenerContext {
    val friendRequestDelegate = mutableMapOf<Properties, (Bot, UserEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            UserEvents.FRIEND_REQUEST -> friendRequestDelegate.forEach { it.value(Bot.of(event, it.key), UserEvent.parse(event)) }
        }
    }
}