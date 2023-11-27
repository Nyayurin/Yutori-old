package io.github.nyayurn.yutori.element

class Br : MessageElement, GenericMessageElement("br")
class Paragraph : MessageElement, GenericMessageElement("p")
class Message @JvmOverloads constructor(
    val text: String? = null,
    val id: String? = null,
    val forward: Boolean? = null
) : MessageElement, GenericMessageElement(
    "message",
    mapOf("id" to id, "forward" to forward),
    listOf(Text(text ?: ""))
)