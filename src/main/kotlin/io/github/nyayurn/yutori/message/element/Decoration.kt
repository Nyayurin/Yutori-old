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

abstract class DecorationElement(name: String, text: String) : NodeMessageElement(name) {
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

class Bold(text: String) : DecorationElement("b", text)
class Strong(text: String) : DecorationElement("strong", text)
class Idiomatic(text: String) : DecorationElement("i", text)
class Em(text: String) : DecorationElement("em", text)
class Underline(text: String) : DecorationElement("u", text)
class Ins(text: String) : DecorationElement("ins", text)
class Strikethrough(text: String) : DecorationElement("s", text)
class Delete(text: String) : DecorationElement("del", text)
class Spl(text: String) : DecorationElement("spl", text)
class Code(text: String) : DecorationElement("code", text)
class Sup(text: String) : DecorationElement("sup", text)
class Sub(text: String) : DecorationElement("sub", text)
