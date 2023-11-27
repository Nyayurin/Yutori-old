package io.github.nyayurn.yutori

import io.github.nyayurn.yutori.element.*
import io.github.nyayurn.yutori.element.Button
import io.github.nyayurn.yutori.element.Message


fun message(init: DslMessage.() -> Unit): String {
    return DslMessage().apply {
        init()
    }.toString()
}

class DslMessage {
    private val children = mutableListOf<MessageElement>()

    operator fun String.unaryPlus() = text(this)

    fun text(text: String) {
        children.add(Text(text))
    }

    fun at(
        id: String? = null,
        name: String? = null,
        role: String? = null,
        type: String? = null
    ) {
        children.add(At(id, name, role, type))
    }

    fun sharp(id: String, name: String? = null) {
        children.add(Sharp(id, name))
    }

    fun a(href: String) {
        children.add(Href(href))
    }

    fun img(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null,
        width: Number? = null,
        height: Number? = null
    ) {
        children.add(Image(src, cache, timeout, width, height))
    }

    fun audio(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        children.add(Audio(src, cache, timeout))
    }

    fun video(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        children.add(Video(src, cache, timeout))
    }

    fun file(
        src: String,
        cache: Boolean? = null,
        timeout: String? = null
    ) {
        children.add(File(src, cache, timeout))
    }

    fun b(text: String) {
        children.add(Bold(text))
    }

    fun strong(text: String) {
        children.add(Strong(text))
    }

    fun i(text: String) {
        children.add(Idiomatic(text))
    }

    fun em(text: String) {
        children.add(Em(text))
    }

    fun u(text: String) {
        children.add(Underline(text))
    }

    fun ins(text: String) {
        children.add(Ins(text))
    }

    fun s(text: String) {
        children.add(Strikethrough(text))
    }

    fun del(text: String) {
        children.add(Delete(text))
    }

    fun spl(text: String) {
        children.add(Spl(text))
    }

    fun code(text: String) {
        children.add(Code(text))
    }

    fun sup(text: String) {
        children.add(Sup(text))
    }

    fun sub(text: String) {
        children.add(Sub(text))
    }

    fun br() {
        children.add(Br())
    }

    fun p() {
        children.add(Paragraph())
    }

    fun message(
        text: String? = null,
        id: String? = null,
        forward: Boolean? = null
    ) {
        children.add(Message(text, id, forward))
    }

    fun quote(text: String) {
        children.add(Quote(text))
    }

    fun author(
        id: String? = null,
        name: String? = null,
        avatar: String? = null
    ) {
        children.add(Author(id, name, avatar))
    }

    fun button(
        id: String? = null,
        type: String? = null,
        href: String? = null,
        text: String? = null,
        theme: String? = null
    ) {
        children.add(Button(id, type, href, text, theme))
    }

    override fun toString(): String {
        var result = ""
        for (item in children) {
            result += item
        }
        return result
    }
}