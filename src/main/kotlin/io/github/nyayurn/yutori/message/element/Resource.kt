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