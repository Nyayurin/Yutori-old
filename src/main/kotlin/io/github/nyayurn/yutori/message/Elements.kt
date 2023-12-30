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
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

/**
 * 为了减少 Event.toMsgChain() 方法复杂度而分离出来
 */
@Slf4j
object Elements {
    /**
     * 解析消息元素
     * @param node 节点
     * @return 消息元素
     */
    fun parseMessageElement(node: Node): MessageElement? {
        if (node is TextNode) {
            return Text(node.text())
        } else if (node is Element) {
            return when (node.tagName()) {
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
                "br" -> Br()
                "p" -> Paragraph()
                "message" -> message(node)
                "quote" -> quote(node)
                "author" -> author(node)
                "button" -> button(node)
                else -> null
            }
        }
        return null
    }

    private fun at(node: Node) = At().apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun sharp(node: Node) = Sharp(node.attr("id")).apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun href(node: Node) = Href(node.attr("href")).apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun image(node: Node) = Image(node.attr("src")).apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun audio(node: Node) = Audio(node.attr("src")).apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun video(node: Node) = Video(node.attr("src")).apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun file(node: Node) = File(node.attr("src")).apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun message(node: Node) = Message().apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun quote(node: Node) = Quote((node.childNode(0) as TextNode).text()).apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun author(node: Node) = Author().apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }

    private fun button(node: Node) = Button().apply {
        for (attr in node.attributes()) {
            this[attr.key] = attr.value
        }
    }
}