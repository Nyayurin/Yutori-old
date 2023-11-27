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

abstract class DecorationElement(name: String, text: String) : GenericMessageElement(name, subElement = listOf(Text(text)))
class Bold(val text: String) : DecorationElement("b", text)
class Strong(val text: String) : DecorationElement("strong", text)
class Idiomatic(val text: String) : DecorationElement("i", text)
class Em(val text: String) : DecorationElement("em", text)
class Underline(val text: String) : DecorationElement("u", text)
class Ins(val text: String) : DecorationElement("ins", text)
class Strikethrough(val text: String) : DecorationElement("s", text)
class Delete(val text: String) : DecorationElement("del", text)
class Spl(val text: String) : DecorationElement("spl", text)
class Code(val text: String) : DecorationElement("code", text)
class Sup(val text: String) : DecorationElement("sup", text)
class Sub(val text: String) : DecorationElement("sub", text)
