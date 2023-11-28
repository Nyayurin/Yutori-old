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

import io.github.nyayurn.yutori.message.element.*

class MessageBuilder private constructor() {
    private val list = mutableListOf<MessageElement>()

    fun custom(text: String): MessageBuilder {
        list.add(Custom(text))
        return this
    }

    fun text(text: String): MessageBuilder {
        list.add(Text(text))
        return this
    }

    @JvmOverloads
    fun at(
        id: String? = null,
        name: String? = null,
        role: String? = null,
        type: String? = null
    ): MessageBuilder {
        list.add(At(id, name, role, type))
        return this
    }

    @JvmOverloads
    fun sharp(id: String, name: String? = null): MessageBuilder {
        list.add(Sharp(id, name))
        return this
    }

    fun a(href: String): MessageBuilder {
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
    ): MessageBuilder {
        list.add(Image(src, cache, timeout, width, height))
        return this
    }

    @JvmOverloads
    fun audio(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ): MessageBuilder {
        list.add(Audio(src, cache, timeout))
        return this
    }

    @JvmOverloads
    fun video(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ): MessageBuilder {
        list.add(Video(src, cache, timeout))
        return this
    }

    @JvmOverloads
    fun file(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ): MessageBuilder {
        list.add(File(src, cache, timeout))
        return this
    }

    fun b(text: String): MessageBuilder {
        list.add(Bold(text))
        return this
    }

    fun strong(text: String): MessageBuilder {
        list.add(Strong(text))
        return this
    }

    fun i(text: String): MessageBuilder {
        list.add(Idiomatic(text))
        return this
    }

    fun em(text: String): MessageBuilder {
        list.add(Em(text))
        return this
    }

    fun u(text: String): MessageBuilder {
        list.add(Underline(text))
        return this
    }

    fun ins(text: String): MessageBuilder {
        list.add(Ins(text))
        return this
    }

    fun s(text: String): MessageBuilder {
        list.add(Strikethrough(text))
        return this
    }

    fun del(text: String): MessageBuilder {
        list.add(Delete(text))
        return this
    }

    fun spl(text: String): MessageBuilder {
        list.add(Spl(text))
        return this
    }

    fun code(text: String): MessageBuilder {
        list.add(Code(text))
        return this
    }

    fun sup(text: String): MessageBuilder {
        list.add(Sup(text))
        return this
    }

    fun sub(text: String): MessageBuilder {
        list.add(Sub(text))
        return this
    }

    fun br(): MessageBuilder {
        list.add(Br())
        return this
    }

    fun p(): MessageBuilder {
        list.add(Paragraph())
        return this
    }

    @JvmOverloads
    fun message(
        text: String? = null,
        id: String? = null,
        forward: Boolean? = null
    ): MessageBuilder {
        list.add(Message(text, id, forward))
        return this
    }

    fun quote(text: String): MessageBuilder {
        list.add(Quote(text))
        return this
    }

    @JvmOverloads
    fun author(
        id: String? = null,
        name: String? = null,
        avatar: String? = null
    ): MessageBuilder {
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
    ): MessageBuilder {
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
        fun of() = MessageBuilder()
    }
}