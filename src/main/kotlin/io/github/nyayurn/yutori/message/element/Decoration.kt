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
 * 修饰元素
 * @property text 被修饰的文本
 */
abstract class DecorationElement @JvmOverloads constructor(
    nodeName: String,
    text: String? = null
) : NodeMessageElement(nodeName) {
    var text: String?
        get() = (super.children[0] as Text).text
        set(value) {
            if (value == null) return
            if (super.children.isEmpty()) {
                super.children += Text(value)
            } else {
                (super.children[0] as Text).text = value
            }
        }

    init {
        this.text = text
    }

    override fun validate() = when {
        text == null -> "text 不能为 null"
        else -> null
    }
}

/**
 * 粗体
 */
class Bold @JvmOverloads constructor(text: String? = null) : DecorationElement("b", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Bold.() -> Unit) = Bold().apply(dsl)
    }
}

/**
 * 粗体
 */
class Strong @JvmOverloads constructor(text: String? = null) : DecorationElement("strong", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Strong.() -> Unit) = Strong().apply(dsl)
    }
}

/**
 * 斜体
 */
class Idiomatic @JvmOverloads constructor(text: String? = null) : DecorationElement("i", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Idiomatic.() -> Unit) = Idiomatic().apply(dsl)
    }
}

/**
 * 斜体
 */
class Em @JvmOverloads constructor(text: String? = null) : DecorationElement("em", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Em.() -> Unit) = Em().apply(dsl)
    }
}

/**
 * 下划线
 */
class Underline @JvmOverloads constructor(text: String? = null) : DecorationElement("u", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Underline.() -> Unit) = Underline().apply(dsl)
    }
}

/**
 * 下划线
 */
class Ins @JvmOverloads constructor(text: String? = null) : DecorationElement("ins", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Ins.() -> Unit) = Ins().apply(dsl)
    }
}

/**
 * 删除线
 */
class Strikethrough @JvmOverloads constructor(text: String? = null) : DecorationElement("s", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Strikethrough.() -> Unit) = Strikethrough().apply(dsl)
    }
}

/**
 * 删除线
 */
class Delete @JvmOverloads constructor(text: String? = null) : DecorationElement("del", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Delete.() -> Unit) = Delete().apply(dsl)
    }
}

/**
 * 剧透
 */
class Spl @JvmOverloads constructor(text: String? = null) : DecorationElement("spl", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Spl.() -> Unit) = Spl().apply(dsl)
    }
}

/**
 * 代码
 */
class Code @JvmOverloads constructor(text: String? = null) : DecorationElement("code", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Code.() -> Unit) = Code().apply(dsl)
    }
}

/**
 * 上标
 */
class Sup @JvmOverloads constructor(text: String? = null) : DecorationElement("sup", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Sup.() -> Unit) = Sup().apply(dsl)
    }
}

/**
 * 下标
 */
class Sub @JvmOverloads constructor(text: String? = null) : DecorationElement("sub", text) {
    companion object {
        @JvmStatic
        fun of(dsl: Sub.() -> Unit) = Sub().apply(dsl)
    }
}
