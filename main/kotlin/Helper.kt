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

import io.github.nyayurn.yutori.message.element.*
import io.github.nyayurn.yutori.message.element.Button
import io.github.nyayurn.yutori.message.element.Message
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.slf4j.Logger

/**
 * 供框架内部和 Kotlin 使用方使用, 提供类似于 Java 的 @Slf4j 注解
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Slf4j {
    companion object {
        val <reified T> T.log: Logger
            @JvmSynthetic inline get() = KotlinLogging.logger { T::class.java.name }
    }
}

/**
 * 辅助类
 */
@Slf4j
object MessageUtil {
    /**
     * 提取出 Satori 消息字符串中的纯文本消息元素
     * @param raw 字符串
     * @return 元素链
     */
    @JvmStatic
    fun extractTextChain(raw: String) = mutableListOf<Text>().apply {
        for (node in Jsoup.parse(raw).body().childNodes()) if (node is TextNode) this.add(Text(node.text()))
    }

    /**
     * 将 Satori 消息字符串转换为元素链
     * @param raw 字符串
     * @return 元素链
     */
    @JvmStatic
    fun parseElementChain(raw: String) = mutableListOf<MessageElement>().apply {
        for (node in Jsoup.parse(raw).body().childNodes()) this.add(
            parseMessageElement(node) ?: throw IllegalStateException("unknown node: ${node.nodeName()}")
        )
    }

    /**
     * 解析消息元素
     * @param node 节点
     * @return 消息元素
     */
    private fun parseMessageElement(node: Node) = when (node) {
        is TextNode -> Text(node.text())
        is Element -> when (node.tagName()) {
            "at" -> at(node)
            "sharp" -> sharp(node)
            "a" -> href(node)
            "img" -> image(node)
            "audio" -> audio(node)
            "video" -> video(node)
            "file" -> file(node)
            "b" -> Bold((node.childNode(0) as TextNode).text())
            "strong" -> Strong((node.childNode(0) as TextNode).text())
            "i" -> Idiomatic((node.childNode(0) as TextNode).text())
            "em" -> Em((node.childNode(0) as TextNode).text())
            "u" -> Underline((node.childNode(0) as TextNode).text())
            "ins" -> Ins((node.childNode(0) as TextNode).text())
            "s" -> Strikethrough((node.childNode(0) as TextNode).text())
            "del" -> Delete((node.childNode(0) as TextNode).text())
            "spl" -> Spl((node.childNode(0) as TextNode).text())
            "code" -> Code((node.childNode(0) as TextNode).text())
            "sup" -> Sup((node.childNode(0) as TextNode).text())
            "sub" -> Sub((node.childNode(0) as TextNode).text())
            "br" -> Br
            "p" -> Paragraph
            "message" -> message(node)
            "quote" -> quote(node)
            "author" -> author(node)
            "button" -> button(node)
            else -> null
        }

        else -> null
    }

    private fun at(node: Node) = At().apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun sharp(node: Node) = Sharp(node.attr("id")).apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }


    private fun href(node: Node) = Href(node.attr("href")).apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun image(node: Node) = Image(node.attr("src")).apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun audio(node: Node) = Audio(node.attr("src")).apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun video(node: Node) = Video(node.attr("src")).apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun file(node: Node) = File(node.attr("src")).apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun message(node: Node) = Message().apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun quote(node: Node) = Quote((node.childNode(0) as TextNode).text()).apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun author(node: Node) = Author().apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }

    private fun button(node: Node) = Button().apply {
        for (attr in node.attributes()) this[attr.key] = attr.value
    }
}