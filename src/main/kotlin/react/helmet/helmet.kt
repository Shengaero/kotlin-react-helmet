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
@file:Suppress("unused")
@file:JsModule("react-helmet")
package react.helmet

import react.Component
import react.RProps
import react.RState
import kotlin.js.Json

external interface HelmetProps: RProps {
    var base: Any

    /**
     * Set to `false` to disable string encoding (server only).
     */
    var encodeSpecialCharacters: Boolean

    /**
     * Inner HTML text value of the [title][react.dom.title] tag.
     */
    var title: String

    /**
     * Sets a formattable string template which takes the provided
     * [title] attribute or title tag value as an argument:
     *
     * ```kotlin
     * helmet {
     *     attrs.titleTemplate = "%s | MyAwesomeWebsite.com"
     *
     *     title { + "My Title" }
     * }
     * ```
     *
     * Outputs:
     *
     * ```html
     * <head>
     *     <title>My Title | MyAwesomeWebsite.com</title>
     * </head>
     * ```
     *
     * If no [title] attribute or tag is specified in a particular
     * helmet instance, this defaults to the [defaultTitle] value.
     */
    var titleTemplate: String

    /**
     * Sets the value to use when the [titleTemplate] does not have
     * a value specified:
     *
     * ```kotlin
     * helmet {
     *     attrs.defaultTitle = "My Site"
     *     attrs.titleTemplate = "My Site - %s"
     * }
     * ```
     *
     * Outputs:
     *
     * ```html
     * <head>
     *     <title>My Site</title>
     * </head>
     * ```
     */
    var defaultTitle: String

    /**
     * [body][react.dom.body] tag attributes in a [Json] form.
     */
    var htmlAttributes: Json

    /**
     * [title][react.dom.title] tag attributes in a [Json] form.
     */
    var titleAttributes: Json

    /**
     * [body][react.dom.body] tag attributes in a [Json] form.
     */
    var bodyAttributes: Json
}

external interface HelmetData {
    val base: HelmetDatum
    val bodyAttributes: HelmetHTMLBodyDatum
    val htmlAttributes: HelmetHTMLElementDatum
    val link: HelmetDatum
    val meta: HelmetDatum
    val noscript: HelmetDatum
    val script: HelmetDatum
    val style: HelmetDatum
    val title: HelmetDatum
    val titleAttributes: HelmetDatum
}

external interface HelmetDatum {
    fun toComponent(): Component<*, *>
    override fun toString(): String
}

external interface HelmetHTMLBodyDatum {
    // FIXME This needs to be React.HTMLAttributes if and when kotlin-react decides to add it.
    fun toComponent(): dynamic
    override fun toString(): String
}

external interface HelmetHTMLElementDatum {
    // FIXME This needs to be React.HTMLAttributes if and when kotlin-react decides to add it.
    fun toComponent(): dynamic
    override fun toString(): String
}

external class Helmet: Component<HelmetProps, RState> {
    override fun render(): dynamic

    companion object {
        val canUseDOM: Boolean
        fun peek(): HelmetData
        fun rewind(): HelmetData
        fun renderStatic(): HelmetData
    }
}

external val canUseDOM: Boolean
external fun peek(): HelmetData
external fun rewind(): HelmetData
external fun renderStatic(): HelmetData
