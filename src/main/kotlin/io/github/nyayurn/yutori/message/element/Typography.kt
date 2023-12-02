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

class Br : NodeMessageElement("br")
class Paragraph : NodeMessageElement("p")
class Message @JvmOverloads constructor(
    text: String? = null,
    id: String? = null,
    forward: Boolean? = null
) : NodeMessageElement("message") {
    var text: String?
        get() = (super.children[0] as Text).text
        set(value) {
            if (value == null) {
                super.children.clear()
                return
            }
            if (super.children.isEmpty()) {
                super.children += Text(value)
            } else {
                (super.children[0] as Text).text = value
            }
        }
    var id: String? by super.properties
    var forward: Boolean? by super.properties

    init {
        this.text = text
        this.id = id
        this.forward = forward
    }
}