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

/**
 * 消息元素
 */
interface MessageElement {
    override fun toString(): String

    /**
     * 验证消息元素是否合法
     * @return String? 错误信息, 返回 null 表示合法
     */
    fun validate(): String?

    /**
     * 转义字符串
     * @param str 需要转义的字符串
     * @return 转以后的字符串
     */
    fun encode(str: String) = str.apply {
        replace("&", "&amp;")
        replace("\"", "&quot;")
        replace("<", "&lt;")
        replace(">", "&gt;")
    }
}

/**
 * 节点消息元素
 * @property nodeName 节点名称
 * @property properties 属性
 * @property children 子元素
 */
abstract class NodeMessageElement(
    val nodeName: String
) : MessageElement {
    val properties: MutableMap<String, Any?> = mutableMapOf()
    val children: MutableList<MessageElement> = mutableListOf()

    /**
     * 获取属性
     * @param key 属性名
     * @return 属性值
     */
    operator fun get(key: String) = properties[key]

    /**
     * 获取子元素
     * @param index 子元素索引
     * @return 消息元素
     */
    operator fun get(index: Int) = children[index]

    /**
     * 设置属性
     * @param key 属性名
     * @param value 属性值
     */
    operator fun set(key: String, value: Any) {
        properties[key] = value
    }

    /**
     * 设置子元素
     * @param index 索引
     * @param value 消息元素
     */
    operator fun set(index: Int, value: MessageElement) {
        children[index] = value
    }

    /**
     * 添加子元素
     * @param element 消息元素
     */
    operator fun plusAssign(element: MessageElement) {
        children.add(element)
    }

    override fun toString(): String {
        var result = "<$nodeName"
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
            "$result</$nodeName>"
        }
    }
}

/**
 * 自定义
 * @property text 内容
 */
class Custom @JvmOverloads constructor(var text: String? = null) : MessageElement {
    override fun toString() = text.toString()
    override fun validate() = when {
        text == null -> "text 不能为 null"
        else -> null
    }

    companion object {
        @JvmStatic
        fun of(dsl: Custom.() -> Unit) = Custom().apply(dsl)
    }
}

/**
 * 纯文本
 * @property text 内容
 */
class Text @JvmOverloads constructor(var text: String? = null) : MessageElement {
    override fun toString() = encode(text.toString())
    override fun validate() = when {
        text == null -> "text 不能为 null"
        else -> null
    }

    companion object {
        @JvmStatic
        fun of(dsl: Text.() -> Unit) = Text().apply(dsl)
    }
}

/**
 * 提及用户
 * @property id 目标用户的 ID
 * @property name 目标用户的名称
 * @property role 目标角色
 * @property type 特殊操作，例如 all 表示 @全体成员，here 表示 @在线成员
 */
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

    override fun validate() = when {
        else -> null
    }

    companion object {
        @JvmStatic
        fun of(dsl: At.() -> Unit) = At().apply(dsl)
    }
}

/**
 * 提及频道
 * @property id 目标频道的 ID
 * @property name 目标频道的名称
 */
class Sharp @JvmOverloads constructor(
    id: String? = null,
    name: String? = null
) : NodeMessageElement("sharp") {
    var id: String? by super.properties
    var name: String? by super.properties

    init {
        this.id = id
        this.name = name
    }

    override fun validate() = when {
        id == null -> "id 不能为 null"
        else -> null
    }

    companion object {
        @JvmStatic
        fun of(dsl: Sharp.() -> Unit) = Sharp().apply(dsl)
    }
}

/**
 * 链接
 * @property href 链接的 URL
 */
class Href @JvmOverloads constructor(href: String? = null) : NodeMessageElement("a") {
    var href: String? by super.properties

    init {
        this.href = href
    }

    override fun validate() = when {
        href == null -> "href 不能为 null"
        else -> null
    }

    companion object {
        @JvmStatic
        fun of(dsl: Href.() -> Unit) = Href().apply(dsl)
    }
}