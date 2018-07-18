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
    var title: String
    var defaultTitle: String
    var encodeSpecialCharacters: Boolean
    var onChangeClientState: ((Any) -> Unit)
    var link: Array<Any>
    var meta: Array<Any>
    var noscript: Array<Any>
    var script: Array<Any>
    var style: Array<Any>
    var titleAttributes: Json
    var titleTemplate: String
}

external interface HelmetData {
    val base: HelmetDatum
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
