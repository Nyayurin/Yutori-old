package io.github.nyayurn.yutori.element

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