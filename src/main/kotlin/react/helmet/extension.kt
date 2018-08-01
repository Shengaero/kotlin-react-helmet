/*
 * Copyright 2018 Kaidan Gustave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package react.helmet

import org.w3c.dom.Element
import react.RBuilder
import react.RHandler
import kotlin.js.Json

fun RBuilder.helmet(block: RHandler<HelmetProps>) = child(Helmet::class, block)

fun RBuilder.helmet(
    title: String? = null,
    defaultTitle: String? = null,
    titleTemplate: String? = null,
    titleAttributes: Json? = null,
    block: RHandler<HelmetProps> = {}
) = child(Helmet::class) {
    attrs {
        title?.let { this.title = it }
        defaultTitle?.let { this.defaultTitle = it }
        titleTemplate?.let { this.titleTemplate = it }
        titleAttributes?.let { this.titleAttributes = it }
    }
    block()
}

/**
 * Sets a callback that tracks DOM changes.
 */
inline fun HelmetProps.onChangeClientState(noinline handle: (Any) -> Unit) {
    this.asDynamic().onChangeClientState = handle
}

/**
 * `true` if this [Element] is created by the rendering of
 * a [helmet] into the DOM tree.
 */
inline val Element.hasHelmetAttribute get() = hasAttribute("data-react-helmet")
