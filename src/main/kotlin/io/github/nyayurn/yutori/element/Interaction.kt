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

class Button @JvmOverloads constructor(
    val id: String? = null,
    val type: String? = null,
    val href: String? = null,
    val text: String? = null,
    val theme: String? = null
) : MessageElement, GenericMessageElement(
    "button",
    mapOf("id" to id, "type" to type, "href" to href, "text" to text, "theme" to theme)
)