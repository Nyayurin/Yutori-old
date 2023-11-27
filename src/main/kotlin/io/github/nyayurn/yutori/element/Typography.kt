package io.github.nyayurn.yutori.element

class Br : GenericMessageElement("br")
class Paragraph : GenericMessageElement("p")
class Message @JvmOverloads constructor(
    val text: String? = null,
    val id: String? = null,
    val forward: Boolean? = null
) : GenericMessageElement(
    "message",
    mapOf("id" to id, "forward" to forward),
    listOf(Text(text ?: ""))
)