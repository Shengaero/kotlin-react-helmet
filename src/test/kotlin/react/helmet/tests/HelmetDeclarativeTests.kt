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
@file:Suppress("FunctionName", "unused")
package react.helmet.tests

import org.w3c.dom.get
import react.dom.div
import react.dom.render
import react.helmet.helmet
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class HelmetDeclarativeTests: AsyncHelmetTestBase() {
    private companion object {
        private const val HELMET_ATTRIBUTE = "data-react-helmet"
    }

    inner class Title {
        @[Test JsName("a")] fun `updates page title`() = runTest {
            render(container) {
                helmet(title = "Test Title")
            }

            window.requestAnimationFrame {
                assertEquals(
                    expected = "Test Title",
                    actual = document.title
                )
                done()
            }
        }

        @[Test JsName("b")] fun `updates page title with multiple children`() = runTest {
            render(container) {
                helmet(title = "Test Title")
                helmet(title = "Child One Title")
                helmet(title = "Child Two Title")
            }

            window.requestAnimationFrame {
                assertEquals(
                    expected = "Child Two Title",
                    actual = document.title
                )
                done()
            }
        }

        @[Test JsName("c")] fun `sets title based on deepest nested component`() = runTest {
            render(container) {
                helmet(title = "Main Title A")
                div {
                    helmet(title = "Nested Title")
                }
            }

            window.requestAnimationFrame {
                assertEquals(
                    expected = "Nested Title",
                    actual = document.title
                )
                done()
            }
        }

        @[Test JsName("d")] fun `sets title using deepest nested component with a defined title`() = runTest {
            render(container) {
                helmet(title = "Main Title")
                helmet()
            }

            window.requestAnimationFrame {
                assertEquals(
                    expected = "Main Title",
                    actual = document.title
                )
                done()
            }
        }

        inner class Template {
            @[Test JsName("a")] fun `uses a titleTemplate if defined`() = runTest {
                render(container) {
                    helmet(
                        defaultTitle = "Fallback",
                        title = "Test",
                        titleTemplate = "This is a %s of the titleTemplate feature"
                    )
                }

                window.requestAnimationFrame {
                    assertEquals(
                        expected = "This is a Test of the titleTemplate feature",
                        actual = document.title
                    )
                    done()
                }
            }

            @[Test JsName("b")] fun `uses a titleTemplate based on deepest nested component`() = runTest {
                render(container) {
                    div {
                        helmet(
                            title = "Test",
                            titleTemplate = "This is a %s of the titleTemplate feature"
                        )
                        helmet(
                            title = "Second Test",
                            titleTemplate = "A %s using nested titleTemplate attributes"
                        )
                    }
                }

                window.requestAnimationFrame {
                    assertEquals(
                        expected = "A Second Test using nested titleTemplate attributes",
                        actual = document.title
                    )
                    done()
                }
            }

            @[Test JsName("c")] fun `uses defaultTitle if no title is defined`() = runTest {
                render(container) {
                    helmet(
                        defaultTitle = "Fallback",
                        title = "",
                        titleTemplate = "This is a %s of the titleTemplate feature"
                    )
                }

                window.requestAnimationFrame {
                    assertEquals(
                        expected = "Fallback",
                        actual = document.title
                    )
                    done()
                }
            }

            @[Test JsName("d")] fun `replaces multiple title strings in titleTemplate`() = runTest {
                render(container) {
                    helmet(
                        title = "Test",
                        titleTemplate = "This is a %s of the titleTemplate feature. Another %s."
                    )
                }

                window.requestAnimationFrame {
                    assertEquals(
                        expected = "This is a Test of the titleTemplate feature. Another Test.",
                        actual = document.title
                    )
                    done()
                }
            }

            @[Test JsName("e")] fun `merges deepest component title with nearest upstream titleTemplate`() = runTest {
                render(container) {
                    div {
                        helmet(
                            title = "Test",
                            titleTemplate = "This is a %s of the titleTemplate feature"
                        )
                        helmet(title = "Second Test")
                    }
                }

                window.requestAnimationFrame {
                    assertEquals(
                        expected = "This is a Second Test of the titleTemplate feature",
                        actual = document.title
                    )
                    done()
                }
            }

            @[Test JsName("f")] fun `renders dollar characters in a title correctly when titleTemplate present`() = runTest {
                val dollarTitle = "te\$t te\$\$t te\$\$\$t te\$\$\$\$t"

                render(container) {
                    helmet(
                        title = dollarTitle,
                        titleTemplate = "This is a %s"
                    )
                }

                window.requestAnimationFrame {
                    assertEquals(
                        expected = "This is a $dollarTitle",
                        actual = document.title
                    )
                    done()
                }
            }
        }

        inner class Properties {
            @[Test JsName("a")] fun `page title with property`() = runTest {
                render(container) {
                    helmet(
                        title = "Test Title with itemProp",
                        defaultTitle = "Fallback",
                        titleAttributes = json("itemprop" to "name")
                    )
                }

                window.requestAnimationFrame {
                    val titleTag = document.getElementsByTagName("title")[0]
                    assertNotNull(titleTag)
                    assertEquals(
                        expected = "Test Title with itemProp",
                        actual = document.title
                    )
                    assertEquals(
                        expected = "name",
                        actual = titleTag.getAttribute("itemprop")
                    )

                    done()
                }
            }
        }
    }
}
