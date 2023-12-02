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

package io.github.nyayurn.yutori.message.element

interface MessageElement {
    override fun toString(): String
    fun encode(str: String): String {
        return str.replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
    }
}

open class NodeMessageElement(
    val elementName: String
) : MessageElement {
    val properties: MutableMap<String, Any?> = mutableMapOf()
    val children: MutableList<MessageElement> = mutableListOf()
    operator fun get(key: String) = properties[key]
    operator fun get(index: Int) = children[index]
    operator fun set(key: String, value: Any) {
        properties[key] = value
    }

    operator fun set(index: Int, value: MessageElement) {
        children[index] = value
    }

    operator fun plusAssign(element: MessageElement) {
        children.add(element)
    }

    override fun toString(): String {
        var result = "<$elementName"
        for (item in properties) {
            val key = item.key
            val value = item.value ?: continue
            result += " "
            result += when (value) {
                is String -> "${key}=\"${encode(value)}\""
                is Number -> "${key}=${value}"
                is Boolean -> if (value) key else ""
                else -> throw Exception("Invalid type")
            }
        }
        return if (children.isEmpty()) {
            "$result/>"
        } else {
            result += ">"
            for (item in children) {
                result += item.toString()
            }
            "$result</$elementName>"
        }
    }
}

class Custom(var text: String) : MessageElement {
    override fun toString(): String = text
}

class Text(var text: String) : MessageElement {
    override fun toString(): String = encode(text)
}

class At @JvmOverloads constructor(
    id: String? = null,
    name: String? = null,
    role: String? = null,
    type: String? = null
) : NodeMessageElement("at") {
    var id: String? by super.properties
    var name: String? by super.properties
    var role: String? by super.properties
    var type: String? by super.properties

    init {
        this.id = id
        this.name = name
        this.role = role
        this.type = type
    }
}

class Sharp @JvmOverloads constructor(
    id: String,
    name: String? = null
) : NodeMessageElement("sharp") {
    var id: String by super.properties
    var name: String? by super.properties

    init {
        this.id = id
        this.name = name
    }
}

class Href(href: String) : NodeMessageElement("a") {
    var href: String by super.properties

    init {
        this.href = href
    }
}