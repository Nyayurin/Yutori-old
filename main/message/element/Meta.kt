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

@file:Suppress("MemberVisibilityCanBePrivate")

package io.github.nyayurn.yutori.message.element

/**
 * 引用
 * @property text 被引用的文本
 */
class Quote(text: String) : NodeMessageElement("quote") {
    var text: String
        get() = (super.children[0] as Text).text
        set(value) {
            if (super.children.isEmpty()) {
                super.children += Text(value)
            } else {
                (super.children[0] as Text).text = value
            }
        }

    init {
        this.text = text
    }
}

/**
 * 作者
 * @property id 用户 ID
 * @property name 昵称
 * @property avatar 头像 URL
 */
class Author @JvmOverloads constructor(
    id: String? = null,
    name: String? = null,
    avatar: String? = null
) : NodeMessageElement("author") {
    var id: String? by super.properties
    var name: String? by super.properties
    var avatar: String? by super.properties

    init {
        this.id = id
        this.name = name
        this.avatar = avatar
    }
}