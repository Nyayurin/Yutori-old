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

package io.github.nyayurn.yutori.message

import io.github.nyayurn.yutori.Slf4j
import io.github.nyayurn.yutori.message.element.*

/**
 * 消息 DSL 构造器, 供 Kotlin 使用者使用
 * @param dsl DSL
 * @return 消息
 */
fun message(dsl: MessageDSLBuilder.() -> Unit) = MessageDSLBuilder().apply(dsl).toString()

@DslMarker
annotation class MessageDSL

@Slf4j
@MessageDSL
class MessageDSLBuilder {
    private val list = mutableListOf<MessageElement>()

    operator fun String.unaryPlus() = list.add(Custom(this))

    fun custom(text: String) {
        list.add(Custom(text))
    }

    fun custom(dsl: () -> String) {
        list.add(Custom(dsl()))
    }

    fun text(text: String) {
        list.add(Text(text))
    }

    fun text(dsl: () -> String) {
        list.add(Text(dsl()))
    }

    fun at(
        id: String? = null,
        name: String? = null,
        role: String? = null,
        type: String? = null
    ) {
        list.add(At(id, name, role, type))
    }

    fun at(dsl: AtBuilder.() -> Unit) {
        list.add(AtBuilder().apply(dsl).build())
    }

    fun sharp(id: String, name: String? = null) {
        list.add(Sharp(id, name))
    }

    fun sharp(dsl: SharpBuilder.() -> Unit) {
        list.add(SharpBuilder().apply(dsl).build())
    }

    fun a(href: String) {
        list.add(Href(href))
    }

    fun a(dsl: () -> String) {
        list.add(Href(dsl()))
    }

    fun img(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null,
        width: Number? = null,
        height: Number? = null
    ) {
        list.add(Image(src, cache, timeout, width, height))
    }

    fun img(dsl: ImageBuilder.() -> Unit) {
        list.add(ImageBuilder().apply(dsl).build())
    }

    fun audio(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(Audio(src, cache, timeout))
    }

    fun audio(dsl: AudioBuilder.() -> Unit) {
        list.add(AudioBuilder().apply(dsl).build())
    }

    fun video(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(Video(src, cache, timeout))
    }

    fun video(dsl: VideoBuilder.() -> Unit) {
        list.add(VideoBuilder().apply(dsl).build())
    }

    fun file(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(File(src, cache, timeout))
    }

    fun file(dsl: FileBuilder.() -> Unit) {
        list.add(FileBuilder().apply(dsl).build())
    }

    fun b(text: String) {
        list.add(Bold(text))
    }

    fun b(dsl: () -> String) {
        list.add(Bold(dsl()))
    }

    fun strong(text: String) {
        list.add(Strong(text))
    }

    fun strong(dsl: () -> String) {
        list.add(Strong(dsl()))
    }

    fun i(text: String) {
        list.add(Idiomatic(text))
    }

    fun i(dsl: () -> String) {
        list.add(Idiomatic(dsl()))
    }

    fun em(text: String) {
        list.add(Em(text))
    }

    fun em(dsl: () -> String) {
        list.add(Em(dsl()))
    }

    fun u(text: String) {
        list.add(Underline(text))
    }

    fun u(dsl: () -> String) {
        list.add(Underline(dsl()))
    }

    fun ins(text: String) {
        list.add(Ins(text))
    }

    fun ins(dsl: () -> String) {
        list.add(Ins(dsl()))
    }

    fun s(text: String) {
        list.add(Strikethrough(text))
    }

    fun s(dsl: () -> String) {
        list.add(Strikethrough(dsl()))
    }

    fun del(text: String) {
        list.add(Delete(text))
    }

    fun del(dsl: () -> String) {
        list.add(Delete(dsl()))
    }

    fun spl(text: String) {
        list.add(Spl(text))
    }

    fun spl(dsl: () -> String) {
        list.add(Spl(dsl()))
    }

    fun code(text: String) {
        list.add(Code(text))
    }

    fun code(dsl: () -> String) {
        list.add(Code(dsl()))
    }

    fun sup(text: String) {
        list.add(Sup(text))
    }

    fun sup(dsl: () -> String) {
        list.add(Sup(dsl()))
    }

    fun sub(text: String) {
        list.add(Sub(text))
    }

    fun sub(dsl: () -> String) {
        list.add(Sub(dsl()))
    }

    fun br() {
        list.add(Br())
    }

    fun p() {
        list.add(Paragraph())
    }

    fun message(
        text: String? = null,
        id: String? = null,
        forward: Boolean? = null
    ) {
        list.add(Message(text, id, forward))
    }

    fun message(dsl: MessageBuilder.() -> Unit) {
        list.add(MessageBuilder().apply(dsl).build())
    }

    fun quote(text: String) {
        list.add(Quote(text))
    }

    fun quote(dsl: () -> String) {
        list.add(Quote(dsl()))
    }

    fun author(
        id: String? = null,
        name: String? = null,
        avatar: String? = null
    ) {
        list.add(Author(id, name, avatar))
    }

    fun author(dsl: AuthorBuilder.() -> Unit) {
        list.add(AuthorBuilder().apply(dsl).build())
    }

    fun button(
        id: String? = null,
        type: String? = null,
        href: String? = null,
        text: String? = null,
        theme: String? = null
    ) {
        list.add(Button(id, type, href, text, theme))
    }

    fun button(dsl: ButtonBuilder.() -> Unit) {
        list.add(ButtonBuilder().apply(dsl).build())
    }

    fun build() = toString()

    override fun toString(): String {
        var result = ""
        for (item in list) {
            result += item
        }
        return result
    }

    @MessageDSL
    class AtBuilder {
        var id: String? = null
        var name: String? = null
        var role: String? = null
        var type: String? = null

        fun id(lambda: () -> String) {
            this.id = lambda()
        }

        fun name(lambda: () -> String) {
            this.name = lambda()
        }

        fun role(lambda: () -> String) {
            this.role = lambda()
        }

        fun type(lambda: () -> String) {
            this.type = lambda()
        }

        fun build() = At(id, name, role, type)
    }

    @MessageDSL
    class SharpBuilder {
        var id: String = ""
        var name: String? = null

        fun id(lambda: () -> String) {
            this.id = lambda()
        }

        fun name(lambda: () -> String) {
            this.name = lambda()
        }

        fun build() = Sharp(id, name)
    }

    @MessageDSL
    class ImageBuilder {
        var src: String = ""
        var cache: Boolean? = null
        var timeout: String? = null
        var width: Number? = null
        var height: Number? = null

        fun src(lambda: () -> String) {
            this.src = lambda()
        }

        fun cache(lambda: () -> Boolean) {
            this.cache = lambda()
        }

        fun timeout(lambda: () -> String) {
            this.timeout = lambda()
        }

        fun width(lambda: () -> Number) {
            this.width = lambda()
        }

        fun height(lambda: () -> Number) {
            this.height = lambda()
        }

        fun build() = Image(src, cache, timeout, width, height)
    }

    @MessageDSL
    class AudioBuilder {
        var src: String = ""
        var cache: Boolean? = null
        var timeout: String? = null

        fun src(lambda: () -> String) {
            this.src = lambda()
        }

        fun cache(lambda: () -> Boolean) {
            this.cache = lambda()
        }

        fun timeout(lambda: () -> String) {
            this.timeout = lambda()
        }

        fun build() = Audio(src, cache, timeout)
    }

    @MessageDSL
    class VideoBuilder {
        var src: String = ""
        var cache: Boolean? = null
        var timeout: String? = null

        fun src(lambda: () -> String) {
            this.src = lambda()
        }

        fun cache(lambda: () -> Boolean) {
            this.cache = lambda()
        }

        fun timeout(lambda: () -> String) {
            this.timeout = lambda()
        }

        fun build() = Video(src, cache, timeout)
    }

    @MessageDSL
    class FileBuilder {
        var src: String = ""
        var cache: Boolean? = null
        var timeout: String? = null

        fun src(lambda: () -> String) {
            this.src = lambda()
        }

        fun cache(lambda: () -> Boolean) {
            this.cache = lambda()
        }

        fun timeout(lambda: () -> String) {
            this.timeout = lambda()
        }

        fun build() = File(src, cache, timeout)
    }

    @MessageDSL
    class MessageBuilder {
        var text: String? = null
        var id: String? = null
        var forward: Boolean? = null
        fun text(lambda: () -> String) {
            text = lambda()
        }

        fun id(lambda: () -> String) {
            id = lambda()
        }

        fun forward(lambda: () -> Boolean) {
            forward = lambda()
        }

        fun build() = Message(text, id, forward)
    }

    @MessageDSL
    class AuthorBuilder {
        var id: String? = null
        var name: String? = null
        var avatar: String? = null
        fun id(lambda: () -> String) {
            id = lambda()
        }

        fun name(lambda: () -> String) {
            name = lambda()
        }

        fun avatar(lambda: () -> String) {
            avatar = lambda()
        }

        fun build() = Author(id, name, avatar)
    }

    @MessageDSL
    class ButtonBuilder {
        var id: String? = null
        var type: String? = null
        var href: String? = null
        var text: String? = null
        var theme: String? = null
        fun id(lambda: () -> String) {
            id = lambda()
        }

        fun type(lambda: () -> String) {
            type = lambda()
        }

        fun href(lambda: () -> String) {
            href = lambda()
        }

        fun text(lambda: () -> String) {
            text = lambda()
        }

        fun theme(lambda: () -> String) {
            theme = lambda()
        }

        fun build() = Button(id, type, href, text, theme)
    }
}

/**
 * 消息链式构造器, 供 Java 使用者使用
 * @property list 消息列表
 */
class MessageChainBuilder {
    private val list = mutableListOf<MessageElement>()

    fun custom(text: String): MessageChainBuilder {
        list.add(Custom(text))
        return this
    }

    fun text(text: String): MessageChainBuilder {
        list.add(Text(text))
        return this
    }

    @JvmOverloads
    fun at(
        id: String? = null,
        name: String? = null,
        role: String? = null,
        type: String? = null
    ): MessageChainBuilder {
        list.add(At(id, name, role, type))
        return this
    }

    @JvmOverloads
    fun sharp(id: String, name: String? = null): MessageChainBuilder {
        list.add(Sharp(id, name))
        return this
    }

    fun a(href: String): MessageChainBuilder {
        list.add(Href(href))
        return this
    }

    @JvmOverloads
    fun img(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null,
        width: Number? = null,
        height: Number? = null
    ): MessageChainBuilder {
        list.add(Image(src, cache, timeout, width, height))
        return this
    }

    @JvmOverloads
    fun audio(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ): MessageChainBuilder {
        list.add(Audio(src, cache, timeout))
        return this
    }

    @JvmOverloads
    fun video(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ): MessageChainBuilder {
        list.add(Video(src, cache, timeout))
        return this
    }

    @JvmOverloads
    fun file(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ): MessageChainBuilder {
        list.add(File(src, cache, timeout))
        return this
    }

    fun b(text: String): MessageChainBuilder {
        list.add(Bold(text))
        return this
    }

    fun strong(text: String): MessageChainBuilder {
        list.add(Strong(text))
        return this
    }

    fun i(text: String): MessageChainBuilder {
        list.add(Idiomatic(text))
        return this
    }

    fun em(text: String): MessageChainBuilder {
        list.add(Em(text))
        return this
    }

    fun u(text: String): MessageChainBuilder {
        list.add(Underline(text))
        return this
    }

    fun ins(text: String): MessageChainBuilder {
        list.add(Ins(text))
        return this
    }

    fun s(text: String): MessageChainBuilder {
        list.add(Strikethrough(text))
        return this
    }

    fun del(text: String): MessageChainBuilder {
        list.add(Delete(text))
        return this
    }

    fun spl(text: String): MessageChainBuilder {
        list.add(Spl(text))
        return this
    }

    fun code(text: String): MessageChainBuilder {
        list.add(Code(text))
        return this
    }

    fun sup(text: String): MessageChainBuilder {
        list.add(Sup(text))
        return this
    }

    fun sub(text: String): MessageChainBuilder {
        list.add(Sub(text))
        return this
    }

    fun br(): MessageChainBuilder {
        list.add(Br())
        return this
    }

    fun p(): MessageChainBuilder {
        list.add(Paragraph())
        return this
    }

    @JvmOverloads
    fun message(
        text: String? = null,
        id: String? = null,
        forward: Boolean? = null
    ): MessageChainBuilder {
        list.add(Message(text, id, forward))
        return this
    }

    fun quote(text: String): MessageChainBuilder {
        list.add(Quote(text))
        return this
    }

    @JvmOverloads
    fun author(
        id: String? = null,
        name: String? = null,
        avatar: String? = null
    ): MessageChainBuilder {
        list.add(Author(id, name, avatar))
        return this
    }

    @JvmOverloads
    fun button(
        id: String? = null,
        type: String? = null,
        href: String? = null,
        text: String? = null,
        theme: String? = null
    ): MessageChainBuilder {
        list.add(Button(id, type, href, text, theme))
        return this
    }

    fun build() = toString()

    override fun toString(): String {
        var result = ""
        for (item in list) {
            result += item
        }
        return result
    }

    companion object {
        @JvmStatic
        fun of() = MessageChainBuilder()
    }
}