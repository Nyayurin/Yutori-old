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

package io.github.nyayurn.yutori.element

private fun String.encode(): String {
    return this.replace("&", "&amp;")
        .replace("\"", "&quot;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
}

interface MessageElement {
    override fun toString(): String
}

abstract class GenericMessageElement(
    private val elementName: String,
    private val properties: Map<String, Any?> = mapOf(),
    private val children: List<MessageElement> = listOf()
) {
    override fun toString(): String {
        var result = "<$elementName"
        for (item in properties) {
            val key = item.key
            val value = item.value ?: continue
            result += " "
            result += when (value) {
                is String -> "${key}=\"${value.encode()}\""
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

class Text(val text: String) : MessageElement {
    override fun toString(): String = text.encode()
}

class At @JvmOverloads constructor(
    val id: String? = null,
    val name: String? = null,
    val role: String? = null,
    val type: String? = null
) : MessageElement, GenericMessageElement(
    "at",
    mapOf("id" to id, "name" to name, "role" to role, "type" to type)
)

class Sharp @JvmOverloads constructor(
    val id: String,
    val name: String? = null
) : MessageElement, GenericMessageElement(
    "sharp",
    mapOf("id" to id, "name" to name)
)

class Href(val href: String) : MessageElement, GenericMessageElement(
    "a",
    mapOf("href" to href)
)