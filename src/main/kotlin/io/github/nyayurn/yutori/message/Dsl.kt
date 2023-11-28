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

fun message(init: DslMessage.() -> Unit): String {
    return DslMessage().apply {
        init()
    }.toString()
}

class DslMessage {
    private val list = mutableListOf<MessageElement>()

    operator fun String.unaryPlus() = custom(this)

    fun custom(text: String) {
        list.add(Custom(text))
    }

    fun text(text: String) {
        list.add(Text(text))
    }

    fun at(
        id: String? = null,
        name: String? = null,
        role: String? = null,
        type: String? = null
    ) {
        list.add(At(id, name, role, type))
    }

    fun sharp(id: String, name: String? = null) {
        list.add(Sharp(id, name))
    }

    fun a(href: String) {
        list.add(Href(href))
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

    fun audio(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(Audio(src, cache, timeout))
    }

    fun video(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(Video(src, cache, timeout))
    }

    fun file(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        list.add(File(src, cache, timeout))
    }

    fun b(text: String) {
        list.add(Bold(text))
    }

    fun strong(text: String) {
        list.add(Strong(text))
    }

    fun i(text: String) {
        list.add(Idiomatic(text))
    }

    fun em(text: String) {
        list.add(Em(text))
    }

    fun u(text: String) {
        list.add(Underline(text))
    }

    fun ins(text: String) {
        list.add(Ins(text))
    }

    fun s(text: String) {
        list.add(Strikethrough(text))
    }

    fun del(text: String) {
        list.add(Delete(text))
    }

    fun spl(text: String) {
        list.add(Spl(text))
    }

    fun code(text: String) {
        list.add(Code(text))
    }

    fun sup(text: String) {
        list.add(Sup(text))
    }

    fun sub(text: String) {
        list.add(Sub(text))
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

    fun quote(text: String) {
        list.add(Quote(text))
    }

    fun author(
        id: String? = null,
        name: String? = null,
        avatar: String? = null
    ) {
        list.add(Author(id, name, avatar))
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

    override fun toString(): String {
        var result = ""
        for (item in list) {
            result += item
        }
        return result
    }
}