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
@file:JsModule("react-dom/test-utils")
@file:Suppress("Unused")
package react.helmet.tests.utils

import react.*

external interface ShallowRenderer {
    fun <E: ReactElement> getRenderOutput(): E
    fun getRenderOutput(): ReactElement
    fun render(element: ReactElement, context: Any = definedExternally)
    fun unmount()
}