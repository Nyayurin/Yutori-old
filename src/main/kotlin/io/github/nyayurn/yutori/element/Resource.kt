package io.github.nyayurn.yutori.element

abstract class ResourceElement(
    name: String,
    properties: Map<String, Any?>,
) : MessageElement, GenericMessageElement(name, properties)

class Image @JvmOverloads constructor(
    val src: String,
    val cache: Boolean? = null,
    val timeout: String? = null,
    val width: Number? = null,
    val height: Number? = null
) : ResourceElement(
    "img",
    mapOf("src" to src, "cache" to cache, "timeout" to timeout, "width" to width, "height" to height)
)

class Audio @JvmOverloads constructor(
    val src: String,
    val cache: Boolean? = null,
    val timeout: String? = null
) : ResourceElement(
    "audio",
    mapOf("src" to src, "cache" to cache, "timeout" to timeout)
)

class Video @JvmOverloads constructor(
    val src: String,
    val cache: Boolean? = null,
    val timeout: String? = null
) : ResourceElement(
    "video",
    mapOf("src" to src, "cache" to cache, "timeout" to timeout)
)

class File @JvmOverloads constructor(
    val src: String,
    val cache: Boolean? = null,
    val timeout: String? = null
) : ResourceElement(
    "file",
    mapOf("src" to src, "cache" to cache, "timeout" to timeout)
)