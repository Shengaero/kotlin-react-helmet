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
package react.helmet

import org.w3c.dom.Element
import react.RBuilder
import react.RClass
import react.RHandler
import react.ReactElement
import kotlin.js.Json

inline fun RBuilder.helmet(
    title: String? = undefined,
    defaultTitle: String? = undefined,
    titleTemplate: String? = undefined,
    titleAttributes: Json? = undefined,
    crossinline block: RHandler<HelmetProps> = {}
): ReactElement = getHelmetClass().invoke {
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
inline val Element.hasHelmetAttribute inline get() = hasAttribute("data-react-helmet")

/*
 * This is used to force the JS kotlin compiler to import react-helmet via
 * kotlin-react-helmet's module exports.
 *
 * If this isn't used, then it seems to cause a bug in the kotlin2js compiler when
 * inlining that causes it to forget that the symbol representing react-helmet's
 * module doesn't exist in the current context:
 *
 *     ReferenceError: $module$react_helmet is not defined
 *
 *       769 |       titleTemplate = undefined;
 *       770 |       titleAttributes = undefined;
 *     > 771 |       $receiver_0.invoke_eb8iu4$($module$react_helmet.Helmet, helmet$lambda_0(title, defaultTitle, titleTemplate, titleAttributes, block));
 *           |                                  ^
 *       772 |       $receiver.child_2usv9w$($receiver_0.create());
 *       773 |       return Unit;
 */
@PublishedApi internal fun getHelmetClass(): RClass<HelmetProps> = helmet

// Deprecated

@Deprecated(
    "To simplify conventions, this has been replaced with the 'helmet' RClass. " +
    "This will be removed in a future release to shrink bundle size.",
    ReplaceWith("helmet(block)", imports = ["react.helmet.helmet"]),
    level = DeprecationLevel.HIDDEN
)
fun RBuilder.helmet(block: RHandler<HelmetProps>): ReactElement = getHelmetClass().invoke { block() }
