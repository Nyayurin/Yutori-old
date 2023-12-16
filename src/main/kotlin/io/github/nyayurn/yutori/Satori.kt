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

private fun <T : Event> Array<out Predicate<T>>.filter(event: T) =
    if (this.isEmpty()) true else this.map { it.test(event) }.reduce { acc, unit -> acc && unit }

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
    fun onEvent(handle: SatoriEventHandle<Event>, vararg predicate: Predicate<Event> = arrayOf()) {
        event.eventDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildAdded(handle: SatoriEventHandle<GuildEvent>, vararg predicate: Predicate<GuildEvent> = arrayOf()) {
        guild.addedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildUpdated(handle: SatoriEventHandle<GuildEvent>, vararg predicate: Predicate<GuildEvent> = arrayOf()) {
        guild.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRemoved(handle: SatoriEventHandle<GuildEvent>, vararg predicate: Predicate<GuildEvent> = arrayOf()) {
        guild.removedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRequest(handle: SatoriEventHandle<GuildEvent>, vararg predicate: Predicate<GuildEvent> = arrayOf()) {
        guild.requestDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildMemberAdded(handle: SatoriEventHandle<GuildMemberEvent>, vararg predicate: Predicate<GuildMemberEvent> = arrayOf()) {
        member.addedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildMemberUpdated(handle: SatoriEventHandle<GuildMemberEvent>, vararg predicate: Predicate<GuildMemberEvent> = arrayOf()) {
        member.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildMemberRemoved(handle: SatoriEventHandle<GuildMemberEvent>, vararg predicate: Predicate<GuildMemberEvent> = arrayOf()) {
        member.removedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildMemberRequest(handle: SatoriEventHandle<GuildMemberEvent>, vararg predicate: Predicate<GuildMemberEvent> = arrayOf()) {
        member.requestDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRoleCreated(handle: SatoriEventHandle<GuildRoleEvent>, vararg predicate: Predicate<GuildRoleEvent> = arrayOf()) {
        role.createdDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRoleUpdated(handle: SatoriEventHandle<GuildRoleEvent>, vararg predicate: Predicate<GuildRoleEvent> = arrayOf()) {
        role.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onGuildRoleDeleted(handle: SatoriEventHandle<GuildRoleEvent>, vararg predicate: Predicate<GuildRoleEvent> = arrayOf()) {
        role.deletedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onInteractionButton(
        handle: SatoriEventHandle<InteractionButtonEvent>, vararg predicate: Predicate<InteractionButtonEvent> = arrayOf()
    ) {
        interaction.buttonDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onInteractionCommand(
        handle: SatoriEventHandle<InteractionCommandEvent>, vararg predicate: Predicate<InteractionCommandEvent> = arrayOf()
    ) {
        interaction.commandDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onLoginAdded(handle: SatoriEventHandle<LoginEvent>, vararg predicate: Predicate<LoginEvent> = arrayOf()) {
        login.addedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onLoginRemoved(handle: SatoriEventHandle<LoginEvent>, vararg predicate: Predicate<LoginEvent> = arrayOf()) {
        login.removedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onLoginUpdated(handle: SatoriEventHandle<LoginEvent>, vararg predicate: Predicate<LoginEvent> = arrayOf()) {
        login.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onMessageCreated(handle: SatoriMessageEventHandle, vararg predicate: Predicate<MessageEvent> = arrayOf()) {
        message.createdDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onMessageUpdated(handle: SatoriMessageEventHandle, vararg predicate: Predicate<MessageEvent> = arrayOf()) {
        message.updatedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onMessageDeleted(handle: SatoriMessageEventHandle, vararg predicate: Predicate<MessageEvent> = arrayOf()) {
        message.deleteDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onReactionAdded(handle: SatoriEventHandle<ReactionEvent>, vararg predicate: Predicate<ReactionEvent> = arrayOf()) {
        reaction.addedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onReactionRemoved(handle: SatoriEventHandle<ReactionEvent>, vararg predicate: Predicate<ReactionEvent> = arrayOf()) {
        reaction.removedDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun onFriendRequest(handle: SatoriEventHandle<UserEvent>, vararg predicate: Predicate<UserEvent> = arrayOf()) {
        user.friendRequestDelegate[handle] = Pair(predicate, properties)
    }

    @JvmOverloads
    fun connect(name: String? = null) {
        (SatoriSocketClient.of(this, name)).connect()
    }

    fun runEvent(event: Event) {
        if (event.user?.id == event.selfId && !properties.listenSelfEvent) return
        Executors.defaultThreadFactory().newThread {
            this.event.run(event)
            this.guild.run(event)
            this.member.run(event)
            this.role.run(event)
            this.interaction.run(event)
            this.login.run(event)
            this.message.run(event)
            this.reaction.run(event)
            this.user.run(event)
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
    val eventDelegate = mutableMapOf<SatoriEventHandle<Event>, Pair<Array<out Predicate<Event>>, SatoriProperties>>()
    fun run(event: Event) = eventDelegate.filter { it.value.first.filter(event) }.forEach { it.key(Bot.of(event, it.value.second), event) }

    override fun toString(): String {
        return "EventListenerContext(eventDelegate=$eventDelegate)"
    }
}

private class GuildListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Pair<Array<out Predicate<GuildEvent>>, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Pair<Array<out Predicate<GuildEvent>>, SatoriProperties>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Pair<Array<out Predicate<GuildEvent>>, SatoriProperties>>()
    val requestDelegate = mutableMapOf<SatoriEventHandle<GuildEvent>, Pair<Array<out Predicate<GuildEvent>>, SatoriProperties>>()
    fun run(event: Event) {
        when (event.type) {
            GuildEvents.ADDED -> GuildEvent.parse(event) { newEvent ->
                addedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            GuildEvents.UPDATED -> GuildEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            GuildEvents.REMOVED -> GuildEvent.parse(event) { newEvent ->
                removedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            GuildEvents.REQUEST -> GuildEvent.parse(event) { newEvent ->
                requestDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }
        }
    }

    override fun toString(): String {
        return "GuildListenerContext(addedDelegate=$addedDelegate, updatedDelegate=$updatedDelegate, removedDelegate=$removedDelegate, requestDelegate=$requestDelegate)"
    }
}

private class GuildMemberListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Pair<Array<out Predicate<GuildMemberEvent>>, SatoriProperties>>()
    val updatedDelegate =
        mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Pair<Array<out Predicate<GuildMemberEvent>>, SatoriProperties>>()
    val removedDelegate =
        mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Pair<Array<out Predicate<GuildMemberEvent>>, SatoriProperties>>()
    val requestDelegate =
        mutableMapOf<SatoriEventHandle<GuildMemberEvent>, Pair<Array<out Predicate<GuildMemberEvent>>, SatoriProperties>>()

    fun run(event: Event) {
        when (event.type) {
            GuildMemberEvents.ADDED -> GuildMemberEvent.parse(event) { newEvent ->
                addedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            GuildMemberEvents.UPDATED -> GuildMemberEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            GuildMemberEvents.REMOVED -> GuildMemberEvent.parse(event) { newEvent ->
                removedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            GuildMemberEvents.REQUEST -> GuildMemberEvent.parse(event) { newEvent ->
                requestDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }
        }
    }

    override fun toString(): String {
        return "GuildMemberListenerContext(addedDelegate=$addedDelegate, updatedDelegate=$updatedDelegate, removedDelegate=$removedDelegate, requestDelegate=$requestDelegate)"
    }
}

private class GuildRoleListenerContext {
    val createdDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Pair<Array<out Predicate<GuildRoleEvent>>, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Pair<Array<out Predicate<GuildRoleEvent>>, SatoriProperties>>()
    val deletedDelegate = mutableMapOf<SatoriEventHandle<GuildRoleEvent>, Pair<Array<out Predicate<GuildRoleEvent>>, SatoriProperties>>()
    fun run(event: Event) {
        when (event.type) {
            GuildRoleEvents.CREATED -> GuildRoleEvent.parse(event) { newEvent ->
                createdDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            GuildRoleEvents.UPDATED -> GuildRoleEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            GuildRoleEvents.DELETED -> GuildRoleEvent.parse(event) { newEvent ->
                deletedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }
        }
    }

    override fun toString(): String {
        return "GuildRoleListenerContext(createdDelegate=$createdDelegate, updatedDelegate=$updatedDelegate, deletedDelegate=$deletedDelegate)"
    }
}

private class InteractionListenerContext {
    val buttonDelegate =
        mutableMapOf<SatoriEventHandle<InteractionButtonEvent>, Pair<Array<out Predicate<InteractionButtonEvent>>, SatoriProperties>>()
    val commandDelegate =
        mutableMapOf<SatoriEventHandle<InteractionCommandEvent>, Pair<Array<out Predicate<InteractionCommandEvent>>, SatoriProperties>>()

    fun run(event: Event) {
        when (event.type) {
            InteractionEvents.BUTTON -> InteractionButtonEvent.parse(event) { newEvent ->
                buttonDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            InteractionEvents.COMMAND -> InteractionCommandEvent.parse(event) { newEvent ->
                commandDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }
        }
    }

    override fun toString(): String {
        return "InteractionListenerContext(buttonDelegate=$buttonDelegate, commandDelegate=$commandDelegate)"
    }
}

private class LoginListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Pair<Array<out Predicate<LoginEvent>>, SatoriProperties>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Pair<Array<out Predicate<LoginEvent>>, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriEventHandle<LoginEvent>, Pair<Array<out Predicate<LoginEvent>>, SatoriProperties>>()
    fun run(event: Event) {
        when (event.type) {
            LoginEvents.ADDED -> LoginEvent.parse(event) { newEvent ->
                addedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            LoginEvents.REMOVED -> LoginEvent.parse(event) { newEvent ->
                removedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            LoginEvents.UPDATED -> LoginEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }
        }
    }

    override fun toString(): String {
        return "LoginListenerContext(addedDelegate=$addedDelegate, removedDelegate=$removedDelegate, updatedDelegate=$updatedDelegate)"
    }
}

private class MessageListenerContext {
    val createdDelegate = mutableMapOf<SatoriMessageEventHandle, Pair<Array<out Predicate<MessageEvent>>, SatoriProperties>>()
    val updatedDelegate = mutableMapOf<SatoriMessageEventHandle, Pair<Array<out Predicate<MessageEvent>>, SatoriProperties>>()
    val deleteDelegate = mutableMapOf<SatoriMessageEventHandle, Pair<Array<out Predicate<MessageEvent>>, SatoriProperties>>()
    fun run(event: Event) {
        when (event.type) {
            MessageEvents.CREATED -> MessageEvent.parse(event) { newEvent ->
                createdDelegate.filter { it.value.first.filter(newEvent) }
                    .forEach { it.key(Bot.of(event, it.value.second), newEvent, filterText(newEvent.message.content)) }
            }

            MessageEvents.UPDATED -> MessageEvent.parse(event) { newEvent ->
                updatedDelegate.filter { it.value.first.filter(newEvent) }
                    .forEach { it.key(Bot.of(event, it.value.second), newEvent, filterText(newEvent.message.content)) }
            }

            MessageEvents.DELETED -> MessageEvent.parse(event) { newEvent ->
                deleteDelegate.filter { it.value.first.filter(newEvent) }
                    .forEach { it.key(Bot.of(event, it.value.second), newEvent, filterText(newEvent.message.content)) }
            }
        }
    }

    fun filterText(raw: String): String = Jsoup.parse(raw).body().text()

    override fun toString(): String {
        return "MessageListenerContext(createdDelegate=$createdDelegate, updatedDelegate=$updatedDelegate, deleteDelegate=$deleteDelegate)"
    }
}

private class ReactionListenerContext {
    val addedDelegate = mutableMapOf<SatoriEventHandle<ReactionEvent>, Pair<Array<out Predicate<ReactionEvent>>, SatoriProperties>>()
    val removedDelegate = mutableMapOf<SatoriEventHandle<ReactionEvent>, Pair<Array<out Predicate<ReactionEvent>>, SatoriProperties>>()
    fun run(event: Event) {
        when (event.type) {
            ReactionEvents.ADDED -> ReactionEvent.parse(event) { newEvent ->
                addedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }

            ReactionEvents.REMOVED -> ReactionEvent.parse(event) { newEvent ->
                removedDelegate.filter { it.value.first.filter(newEvent) }.forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }
        }
    }

    override fun toString(): String {
        return "ReactionListenerContext(addedDelegate=$addedDelegate, removedDelegate=$removedDelegate)"
    }
}

private class UserListenerContext {
    val friendRequestDelegate = mutableMapOf<SatoriEventHandle<UserEvent>, Pair<Array<out Predicate<UserEvent>>, SatoriProperties>>()
    fun run(event: Event) {
        when (event.type) {
            UserEvents.FRIEND_REQUEST -> UserEvent.parse(event) { newEvent ->
                friendRequestDelegate.filter { it.value.first.filter(newEvent) }
                    .forEach { it.key(Bot.of(event, it.value.second), newEvent) }
            }
        }
    }

    override fun toString(): String {
        return "UserListenerContext(friendRequestDelegate=$friendRequestDelegate)"
    }
}