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
package react.helmet.tests.utils

import kotlinx.html.HtmlTagMarker
import kotlinx.html.TagConsumer
import kotlinx.html.stream.createHTML
import org.w3c.dom.Window

// JSDOM

@JsModule("jsdom")
external class JSDOM(html: dynamic, options: dynamic = definedExternally) {
    val window: Window
}

@HtmlTagMarker fun createDOM(block: TagConsumer<*>.() -> Unit): JSDOM {
    val html = createHTML(true).apply(block).finalize()
    return JSDOM(html)
}