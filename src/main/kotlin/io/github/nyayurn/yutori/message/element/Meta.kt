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

class Quote(val text: String) : MessageElement, GenericMessageElement(
    "quote",
    children = listOf(Text(text))
)

class Author @JvmOverloads constructor(
    val id: String? = null,
    val name: String? = null,
    val avatar: String? = null
) : MessageElement, GenericMessageElement(
    "author",
    mapOf("id" to id, "name" to name, "avatar" to avatar)
)