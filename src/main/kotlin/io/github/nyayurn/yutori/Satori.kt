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
import java.util.function.Predicate

typealias SatoriEventHandle<T> = (Bot, T) -> Unit

typealias SatoriMessageEventHandle = (Bot, MessageEvent, String) -> Unit

private fun Predicate<Event>?.test(event: Event): Boolean = this?.test(event) ?: true

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
    fun onEvent(handle: SatoriEventHandle<Event>, predicate: Predicate<Event>? = null) {
        event.eventDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildAdded(handle: SatoriEventHandle<GuildEvent>, predicate: Predicate<Event>? = null) {
        guild.addedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildUpdated(handle: SatoriEventHandle<GuildEvent>, predicate: Predicate<Event>? = null) {
        guild.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRemoved(handle: SatoriEventHandle<GuildEvent>, predicate: Predicate<Event>? = null) {
        guild.removedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRequest(handle: SatoriEventHandle<GuildEvent>, predicate: Predicate<Event>? = null) {
        guild.requestDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildMemberAdded(handle: SatoriEventHandle<GuildMemberEvent>, predicate: Predicate<Event>? = null) {
        member.addedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildMemberUpdated(handle: SatoriEventHandle<GuildMemberEvent>, predicate: Predicate<Event>? = null) {
        member.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildMemberRemoved(handle: SatoriEventHandle<GuildMemberEvent>, predicate: Predicate<Event>? = null) {
        member.removedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildMemberRequest(handle: SatoriEventHandle<GuildMemberEvent>, predicate: Predicate<Event>? = null) {
        member.requestDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRoleCreated(handle: SatoriEventHandle<GuildRoleEvent>, predicate: Predicate<Event>? = null) {
        role.createdDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRoleUpdated(handle: SatoriEventHandle<GuildRoleEvent>, predicate: Predicate<Event>? = null) {
        role.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRoleDeleted(handle: SatoriEventHandle<GuildRoleEvent>, predicate: Predicate<Event>? = null) {
        role.deletedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onInteractionButton(handle: SatoriEventHandle<InteractionButtonEvent>, predicate: Predicate<Event>? = null) {
        interaction.buttonDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onInteractionCommand(handle: SatoriEventHandle<InteractionCommandEvent>, predicate: Predicate<Event>? = null) {
        interaction.commandDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onLoginAdded(handle: SatoriEventHandle<LoginEvent>, predicate: Predicate<Event>? = null) {
        login.addedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onLoginRemoved(handle: SatoriEventHandle<LoginEvent>, predicate: Predicate<Event>? = null) {
        login.removedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onLoginUpdated(handle: SatoriEventHandle<LoginEvent>, predicate: Predicate<Event>? = null) {
        login.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onMessageCreated(handle: SatoriMessageEventHandle, predicate: Predicate<Event>? = null) {
        message.createdDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onMessageUpdated(handle: SatoriMessageEventHandle, predicate: Predicate<Event>? = null) {
        message.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onMessageDeleted(handle: SatoriMessageEventHandle, predicate: Predicate<Event>? = null) {
        message.deleteDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onReactionAdded(handle: SatoriEventHandle<ReactionEvent>, predicate: Predicate<Event>? = null) {
        reaction.addedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onReactionRemoved(handle: SatoriEventHandle<ReactionEvent>, predicate: Predicate<Event>? = null) {
        reaction.removedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onFriendRequest(handle: SatoriEventHandle<UserEvent>, predicate: Predicate<Event>? = null) {
        user.friendRequestDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun connect(name: String? = null) {
        (SatoriSocketClient.of(this, name)).connect()
    }

    fun runEvent(event: Event) {
        if (event.user?.id == event.selfId && !properties.listenSelfEvent) return
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

    override fun toString(): String {
        return "Satori(properties=$properties, event=$event, guild=$guild, member=$member, role=$role, interaction=$interaction, login=$login, message=$message, reaction=$reaction, user=$user)"
    }

    companion object {
        @JvmStatic
        fun client(properties: SatoriProperties) = Satori(properties)

        @JvmStatic
        @JvmOverloads
        fun client(
            address: String,
            token: String? = null,
            sequence: Number? = null,
            listenSelfEvent: Boolean = false
        ) = Satori(SimpleSatoriProperties(address, token, sequence, listenSelfEvent))

        // 以下仅 kotlin 使用
        @JvmSynthetic
        fun client(properties: SatoriProperties, apply: Satori.() -> Unit) = Satori(properties).apply { apply() }

        @JvmSynthetic
        fun client(
            address: String,
            token: String? = null,
            sequence: Number? = null,
            listenSelfEvent: Boolean = false,
            apply: Satori.() -> Unit
        ) = Satori(SimpleSatoriProperties(address, token, sequence, listenSelfEvent)).apply { apply() }
    }
}

private class EventListenerContext {
    val eventDelegate = mutableMapOf<SatoriEventHandle<Event>, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        eventDelegate
            .filter { it.value.first.test(event) }
            .forEach { it.key(Bot.of(event, it.value.second), event) }
    }

    override fun toString(): String {
        return "EventListenerContext(eventDelegate=$eventDelegate)"
    }
}

private class GuildListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val requestDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildEvents.ADDED -> addedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildEvent.parse(event)) }

            GuildEvents.UPDATED -> updatedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildEvent.parse(event)) }

            GuildEvents.REMOVED -> removedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildEvent.parse(event)) }

            GuildEvents.REQUEST -> requestDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildEvent.parse(event)) }
        }
    }

    override fun toString(): String {
        return "GuildListenerContext(addedDelegate=$addedDelegate, updatedDelegate=$updatedDelegate, removedDelegate=$removedDelegate, requestDelegate=$requestDelegate)"
    }
}

private class GuildMemberListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val requestDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildMemberEvents.ADDED -> addedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildMemberEvent.parse(event)) }

            GuildMemberEvents.UPDATED -> updatedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildMemberEvent.parse(event)) }

            GuildMemberEvents.REMOVED -> removedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildMemberEvent.parse(event)) }

            GuildMemberEvents.REQUEST -> requestDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildMemberEvent.parse(event)) }
        }
    }

    override fun toString(): String {
        return "GuildMemberListenerContext(addedDelegate=$addedDelegate, updatedDelegate=$updatedDelegate, removedDelegate=$removedDelegate, requestDelegate=$requestDelegate)"
    }
}

private class GuildRoleListenerContext {
    val createdDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val deletedDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        when (event.type) {
            GuildRoleEvents.CREATED -> createdDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildRoleEvent.parse(event)) }

            GuildRoleEvents.UPDATED -> updatedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildRoleEvent.parse(event)) }

            GuildRoleEvents.DELETED -> deletedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), GuildRoleEvent.parse(event)) }
        }
    }

    override fun toString(): String {
        return "GuildRoleListenerContext(createdDelegate=$createdDelegate, updatedDelegate=$updatedDelegate, deletedDelegate=$deletedDelegate)"
    }
}

private class InteractionListenerContext {
    val buttonDelegate = mutableMapOf<SatoriEventHandle<InteractionButtonEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val commandDelegate = mutableMapOf<SatoriEventHandle<InteractionCommandEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        when (event.type) {
            InteractionEvents.BUTTON -> buttonDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), InteractionButtonEvent.parse(event)) }

            InteractionEvents.COMMAND -> commandDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), InteractionCommandEvent.parse(event)) }
        }
    }

    override fun toString(): String {
        return "InteractionListenerContext(buttonDelegate=$buttonDelegate, commandDelegate=$commandDelegate)"
    }
}

private class LoginListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        when (event.type) {
            LoginEvents.ADDED -> addedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), LoginEvent.parse(event)) }

            LoginEvents.REMOVED -> removedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), LoginEvent.parse(event)) }

            LoginEvents.UPDATED -> updatedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), LoginEvent.parse(event)) }
        }
    }

    override fun toString(): String {
        return "LoginListenerContext(addedDelegate=$addedDelegate, removedDelegate=$removedDelegate, updatedDelegate=$updatedDelegate)"
    }
}

private class MessageListenerContext {
    val createdDelegate = mutableMapOf<SatoriMessageEventHandle, Pair<Predicate<Event>?, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriMessageEventHandle, Pair<Predicate<Event>?, SatoriProperties>>()
    val deleteDelegate = mutableMapOf<SatoriMessageEventHandle, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        when (event.type) {
            MessageEvents.CREATED -> createdDelegate
                .filter { it.value.first.test(event) }
                .forEach {
                    MessageEvent.parse(event).let { messageEvent ->
                        it.key(Bot.of(event, it.value.second), messageEvent, filterText(messageEvent.message.content))
                    }
                }

            MessageEvents.UPDATED -> updatedDelegate
                .filter { it.value.first.test(event) }
                .forEach {
                    MessageEvent.parse(event).let { messageEvent ->
                        it.key(Bot.of(event, it.value.second), messageEvent, filterText(messageEvent.message.content))
                    }
                }

            MessageEvents.DELETED -> deleteDelegate
                .filter { it.value.first.test(event) }
                .forEach {
                    MessageEvent.parse(event).let { messageEvent ->
                        it.key(Bot.of(event, it.value.second), messageEvent, filterText(messageEvent.message.content))
                    }
                }
        }
    }

    fun filterText(raw: String): String = Jsoup.parse(raw).body().text()

    override fun toString(): String {
        return "MessageListenerContext(createdDelegate=$createdDelegate, updatedDelegate=$updatedDelegate, deleteDelegate=$deleteDelegate)"
    }
}

private class ReactionListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<ReactionEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<ReactionEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        when (event.type) {
            ReactionEvents.ADDED -> addedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), ReactionEvent.parse(event)) }

            ReactionEvents.REMOVED -> removedDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), ReactionEvent.parse(event)) }
        }
    }

    override fun toString(): String {
        return "ReactionListenerContext(addedDelegate=$addedDelegate, removedDelegate=$removedDelegate)"
    }
}

private class UserListenerContext {
    val friendRequestDelegate = mutableMapOf<SatoriEventHandle<UserEvent>, Pair<Predicate<Event>?, SatoriProperties>>()
    infix fun run(event: Event) {
        when (event.type) {
            UserEvents.FRIEND_REQUEST -> friendRequestDelegate
                .filter { it.value.first.test(event) }
                .forEach { it.key(Bot.of(event, it.value.second), UserEvent.parse(event)) }
        }
    }

    override fun toString(): String {
        return "UserListenerContext(friendRequestDelegate=$friendRequestDelegate)"
    }
}