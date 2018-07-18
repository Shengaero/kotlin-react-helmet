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
package react.helmet.tests

import kotlinx.html.html
import react.helmet.helmet
import react.helmet.tests.utils.createDOM
import org.w3c.dom.Element
import react.buildElement
import react.buildElements
import react.dom.render
import react.dom.unmountComponentAtNode
import kotlin.test.*

// FIXME These tests are very inconsistent, and require
// some actual server code to actually test.
@Ignore
class HelmetTests {
    private val dom = createDOM { html {} }
    private val window get() = dom.window
    private val document get() = window.document

    private val container = document.createElement("div")
    private var headElement = null as Element?

    @BeforeTest fun resetHead() {
        headElement = headElement ?: document.head
            ?: checkNotNull(document.querySelector("head"))

        headElement?.innerHTML = ""
    }

    @AfterTest fun unmount() {
        unmountComponentAtNode(container)
    }

    @Test fun updatesPageTitle() {
        render(buildElement {
            helmet(title = "Test Title")
        }, container)

        window.requestAnimationFrame {
            assertEquals("Test Title", document.title)
        }
    }

    @Test fun updatesPageWithMultipleChildren() {
        render(buildElements {
            helmet(title = "Test Title")
            helmet(title = "Child One Title")
            helmet(title = "Child Two Title")
        }, container)

        window.requestAnimationFrame {
            assertEquals("Child Two Title", document.title)
        }
    }
}