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
import io.github.nyayurn.yutori.Slf4j.Companion.log
import io.github.nyayurn.yutori.message.element.*

/**
 * 消息 DSL 构造器, 供 Kotlin 使用者使用
 * @param dsl DSL
 * @return 消息
 */
fun message(dsl: MessageDSLBuilder.() -> Unit) = MessageDSLBuilder().apply(dsl).toString()

@Slf4j
class MessageDSLBuilder {
    private val list = mutableListOf<MessageElement>()

    operator fun String.unaryPlus() = list.add(Custom(this))

    fun custom(text: String) {
        list.add(Custom(text))
    }

    fun custom(dsl: Custom.() -> Unit) {
        list.add(Custom().apply(dsl))
    }

    fun text(text: String) {
        list.add(Text(text))
    }

    fun text(dsl: Text.() -> Unit) {
        list.add(Text().apply(dsl))
    }

    fun at(
        id: String? = null,
        name: String? = null,
        role: String? = null,
        type: String? = null
    ) {
        list.add(At(id, name, role, type))
    }

    fun at(dsl: At.() -> Unit) {
        list.add(At.of(dsl))
    }

    fun sharp(id: String, name: String? = null) {
        list.add(Sharp(id, name))
    }

    fun sharp(dsl: Sharp.() -> Unit) {
        list.add(Sharp.of(dsl))
    }

    fun a(href: String) {
        list.add(Href(href))
    }

    fun a(dsl: Href.() -> Unit) {
        list.add(Href.of(dsl))
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

    fun img(dsl: Image.() -> Unit) {
        list.add(Image.of(dsl))
    }

    fun audio(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(Audio(src, cache, timeout))
    }

    fun audio(dsl: Audio.() -> Unit) {
        list.add(Audio.of(dsl))
    }

    fun video(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(Video(src, cache, timeout))
    }

    fun video(dsl: Video.() -> Unit) {
        list.add(Video.of(dsl))
    }

    fun file(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(File(src, cache, timeout))
    }

    fun file(dsl: File.() -> Unit) {
        list.add(File.of(dsl))
    }

    fun b(text: String) {
        list.add(Bold(text))
    }

    fun b(dsl: Bold.() -> Unit) {
        list.add(Bold.of(dsl))
    }

    fun strong(text: String) {
        list.add(Strong(text))
    }

    fun strong(dsl: Strong.() -> Unit) {
        list.add(Strong.of(dsl))
    }

    fun i(text: String) {
        list.add(Idiomatic(text))
    }

    fun i(dsl: Idiomatic.() -> Unit) {
        list.add(Idiomatic.of(dsl))
    }

    fun em(text: String) {
        list.add(Em(text))
    }

    fun em(dsl: Em.() -> Unit) {
        list.add(Em.of(dsl))
    }

    fun u(text: String) {
        list.add(Underline(text))
    }

    fun u(dsl: Underline.() -> Unit) {
        list.add(Underline.of(dsl))
    }

    fun ins(text: String) {
        list.add(Ins(text))
    }

    fun ins(dsl: Ins.() -> Unit) {
        list.add(Ins.of(dsl))
    }

    fun s(text: String) {
        list.add(Strikethrough(text))
    }

    fun s(dsl: Strikethrough.() -> Unit) {
        list.add(Strikethrough.of(dsl))
    }

    fun del(text: String) {
        list.add(Delete(text))
    }

    fun del(dsl: Delete.() -> Unit) {
        list.add(Delete.of(dsl))
    }

    fun spl(text: String) {
        list.add(Spl(text))
    }

    fun spl(dsl: Spl.() -> Unit) {
        list.add(Spl.of(dsl))
    }

    fun code(text: String) {
        list.add(Code(text))
    }

    fun code(dsl: Code.() -> Unit) {
        list.add(Code.of(dsl))
    }

    fun sup(text: String) {
        list.add(Sup(text))
    }

    fun sup(dsl: Sup.() -> Unit) {
        list.add(Sup.of(dsl))
    }

    fun sub(text: String) {
        list.add(Sub(text))
    }

    fun sub(dsl: Sub.() -> Unit) {
        list.add(Sub.of(dsl))
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

    fun message(dsl: Message.() -> Unit) {
        list.add(Message.of(dsl))
    }

    fun quote(text: String) {
        list.add(Quote(text))
    }

    fun quote(dsl: Quote.() -> Unit) {
        list.add(Quote.of(dsl))
    }

    fun author(
        id: String? = null,
        name: String? = null,
        avatar: String? = null
    ) {
        list.add(Author(id, name, avatar))
    }

    fun author(dsl: Author.() -> Unit) {
        list.add(Author.of(dsl))
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

    fun button(dsl: Button.() -> Unit) {
        list.add(Button.of(dsl))
    }

    fun build() = toString()

    override fun toString(): String {
        var result = ""
        for (item in list) {
            val validate = item.validate()
            if (validate != null) log.warn(validate)
            else result += item
        }
        return result
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