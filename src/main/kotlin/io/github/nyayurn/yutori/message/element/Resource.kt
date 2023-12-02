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
    src: String,
    cache: Boolean?,
    timeout: String?
) : NodeMessageElement(name) {
    var src: String by super.properties
    var cache: Boolean? by super.properties
    var timeout: String? by super.properties

    init {
        this.src = src
        this.cache = cache
        this.timeout = timeout
    }
}

class Image @JvmOverloads constructor(
    src: String,
    cache: Boolean? = null,
    timeout: String? = null,
    width: Number? = null,
    height: Number? = null
) : ResourceElement("img", src, cache, timeout) {
    var width: Number? by super.properties
    var height: Number? by super.properties

    init {
        this.width = width
        this.height = height
    }
}

class Audio @JvmOverloads constructor(
    src: String,
    cache: Boolean? = null,
    timeout: String? = null
) : ResourceElement("audio", src, cache, timeout)

class Video @JvmOverloads constructor(
    src: String,
    cache: Boolean? = null,
    timeout: String? = null
) : ResourceElement("video", src, cache, timeout)

class File @JvmOverloads constructor(
    src: String,
    cache: Boolean? = null,
    timeout: String? = null
) : ResourceElement("file", src, cache, timeout)