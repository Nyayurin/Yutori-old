package io.github.nyayurn.yutori.element

class Quote(val text: String) : GenericMessageElement(
    "quote",
    subElement = listOf(Text(text))
)

class Author @JvmOverloads constructor(
    val id: String? = null,
    val name: String? = null,
    val avatar: String? = null
) : GenericMessageElement(
    "author",
    mapOf("id" to id, "name" to name, "avatar" to avatar)
)