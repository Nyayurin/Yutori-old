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

object ListenerDispatcher {
    private val event = EventListenerContext
    private val guild = GuildListenerContext
    private val member = GuildMemberListenerContext
    private val role = GuildRoleListenerContext
    private val interaction = InteractionListenerContext
    private val login = LoginListenerContext
    private val message = MessageListenerContext
    private val reaction = ReactionListenerContext
    private val user = UserListenerContext
    infix fun onEvent(function: (Event) -> Unit) = event.eventDelegate.add(function)
    infix fun onGuildAdded(function: (GuildEvent) -> Unit) = guild.addedDelegate.add(function)
    infix fun onGuildUpdated(function: (GuildEvent) -> Unit) = guild.updatedDelegate.add(function)
    infix fun onGuildRemoved(function: (GuildEvent) -> Unit) = guild.removedDelegate.add(function)
    infix fun onGuildRequest(function: (GuildEvent) -> Unit) = guild.requestDelegate.add(function)
    infix fun onGuildMemberAdded(function: (GuildMemberEvent) -> Unit) = member.addedDelegate.add(function)
    infix fun onGuildMemberUpdated(function: (GuildMemberEvent) -> Unit) = member.updatedDelegate.add(function)
    infix fun onGuildMemberRemoved(function: (GuildMemberEvent) -> Unit) = member.removedDelegate.add(function)
    infix fun onGuildMemberRequest(function: (GuildMemberEvent) -> Unit) = member.requestDelegate.add(function)
    infix fun onGuildRoleCreated(function: (GuildRoleEvent) -> Unit) = role.createdDelegate.add(function)
    infix fun onGuildRoleUpdated(function: (GuildRoleEvent) -> Unit) = role.updatedDelegate.add(function)
    infix fun onGuildRoleDeleted(function: (GuildRoleEvent) -> Unit) = role.deletedDelegate.add(function)
    infix fun onInteractionButton(function: (InteractionButtonEvent) -> Unit) = interaction.buttonDelegate.add(function)
    infix fun onInteractionCommand(function: (InteractionCommandEvent) -> Unit) = interaction.commandDelegate.add(function)
    infix fun onLoginAdded(function: (LoginEvent) -> Unit) = login.addedDelegate.add(function)
    infix fun onLoginRemoved(function: (LoginEvent) -> Unit) = login.removedDelegate.add(function)
    infix fun onLoginUpdated(function: (LoginEvent) -> Unit) = login.updatedDelegate.add(function)
    infix fun onMessageCreated(function: (MessageEvent) -> Unit) = message.createdDelegate.add(function)
    infix fun onMessageUpdated(function: (MessageEvent) -> Unit) = message.updatedDelegate.add(function)
    infix fun onMessageDeleted(function: (MessageEvent) -> Unit) = message.deleteDelegate.add(function)
    infix fun onReactionAdded(function: (ReactionEvent) -> Unit) = reaction.addedDelegate.add(function)
    infix fun onReactionRemoved(function: (ReactionEvent) -> Unit) = reaction.removedDelegate.add(function)
    infix fun onFriendRequest(function: (UserEvent) -> Unit) = user.friendRequestDelegate.add(function)
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
    val eventDelegate = mutableListOf<(Event) -> Unit>()
    infix fun run(event: Event) = eventDelegate.forEach { it(event) }
}

private object GuildListenerContext {
    val addedDelegate = mutableListOf<(GuildEvent) -> Unit>()
    val updatedDelegate = mutableListOf<(GuildEvent) -> Unit>()
    val removedDelegate = mutableListOf<(GuildEvent) -> Unit>()
    val requestDelegate = mutableListOf<(GuildEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildEvents.ADDED -> addedDelegate.forEach { it(GuildEvent.parse(event)) }
            GuildEvents.UPDATED -> updatedDelegate.forEach { it(GuildEvent.parse(event)) }
            GuildEvents.REMOVED -> removedDelegate.forEach { it(GuildEvent.parse(event)) }
            GuildEvents.REQUEST -> requestDelegate.forEach { it(GuildEvent.parse(event)) }
        }
    }
}

private object GuildMemberListenerContext {
    val addedDelegate = mutableListOf<(GuildMemberEvent) -> Unit>()
    val updatedDelegate = mutableListOf<(GuildMemberEvent) -> Unit>()
    val removedDelegate = mutableListOf<(GuildMemberEvent) -> Unit>()
    val requestDelegate = mutableListOf<(GuildMemberEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildMemberEvents.ADDED -> addedDelegate.forEach { it(GuildMemberEvent.parse(event)) }
            GuildMemberEvents.UPDATED -> updatedDelegate.forEach { it(GuildMemberEvent.parse(event)) }
            GuildMemberEvents.REMOVED -> removedDelegate.forEach { it(GuildMemberEvent.parse(event)) }
            GuildMemberEvents.REQUEST -> requestDelegate.forEach { it(GuildMemberEvent.parse(event)) }
        }
    }
}

private object GuildRoleListenerContext {
    val createdDelegate = mutableListOf<(GuildRoleEvent) -> Unit>()
    val updatedDelegate = mutableListOf<(GuildRoleEvent) -> Unit>()
    val deletedDelegate = mutableListOf<(GuildRoleEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildRoleEvents.CREATED -> createdDelegate.forEach { it(GuildRoleEvent.parse(event)) }
            GuildRoleEvents.UPDATED -> updatedDelegate.forEach { it(GuildRoleEvent.parse(event)) }
            GuildRoleEvents.DELETED -> deletedDelegate.forEach { it(GuildRoleEvent.parse(event)) }
        }
    }
}

private object InteractionListenerContext {
    val buttonDelegate = mutableListOf<(InteractionButtonEvent) -> Unit>()
    val commandDelegate = mutableListOf<(InteractionCommandEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            InteractionEvents.BUTTON -> buttonDelegate.forEach { it(InteractionButtonEvent.parse(event)) }
            InteractionEvents.COMMAND -> commandDelegate.forEach { it(InteractionCommandEvent.parse(event)) }
        }
    }
}

private object LoginListenerContext {
    val addedDelegate = mutableListOf<(LoginEvent) -> Unit>()
    val removedDelegate = mutableListOf<(LoginEvent) -> Unit>()
    val updatedDelegate = mutableListOf<(LoginEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            LoginEvents.ADDED -> addedDelegate.forEach { it(LoginEvent.parse(event)) }
            LoginEvents.REMOVED -> removedDelegate.forEach { it(LoginEvent.parse(event)) }
            LoginEvents.UPDATED -> updatedDelegate.forEach { it(LoginEvent.parse(event)) }
        }
    }
}

private object MessageListenerContext {
    val createdDelegate = mutableListOf<(MessageEvent) -> Unit>()
    val updatedDelegate = mutableListOf<(MessageEvent) -> Unit>()
    val deleteDelegate = mutableListOf<(MessageEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            MessageEvents.CREATED -> createdDelegate.forEach { it(MessageEvent.parse(event)) }
            MessageEvents.UPDATED -> updatedDelegate.forEach { it(MessageEvent.parse(event)) }
            MessageEvents.DELETED -> deleteDelegate.forEach { it(MessageEvent.parse(event)) }
        }
    }
}

private object ReactionListenerContext {
    val addedDelegate = mutableListOf<(ReactionEvent) -> Unit>()
    val removedDelegate = mutableListOf<(ReactionEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            ReactionEvents.ADDED -> addedDelegate.forEach { it(ReactionEvent.parse(event)) }
            ReactionEvents.REMOVED -> removedDelegate.forEach { it(ReactionEvent.parse(event)) }
        }
    }
}

private object UserListenerContext {
    val friendRequestDelegate = mutableListOf<(UserEvent) -> Unit>()
    infix fun run(event: Event) {
        when (event.type) {
            UserEvents.FRIEND_REQUEST -> friendRequestDelegate.forEach { it(UserEvent.parse(event)) }
        }
    }
}