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

import jest.*
import org.w3c.dom.Element
import org.w3c.dom.asList
import org.w3c.dom.get
import react.dom.*
import react.helmet.hasHelmetAttribute
import react.helmet.helmet
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.json
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

const val HELMET_ATTRIBUTE = "data-react-helmet"

val KotlinReactHelmet = describe("kotlin-react-helmet") {
    val container = document.createElement("div")
    var headElement = null as Element?

    beforeEach {
        headElement = headElement ?: document.head ?: document.querySelector("head")
        headElement?.innerHTML = ""
    }

    afterEach {
        unmountComponentAtNode(container)
    }

    describe("Title") {
        it("Updates page title") { done ->
            render(container) {
                helmet(title = "Test Title")
            }

            window.requestAnimationFrame {
                assertEquals(expected = "Test Title", actual = document.title)
                done()
            }
        }

        it("Updates page via tag") { done ->
            render(container) {
                helmet {
                    title { + "Test Title" }
                }
            }

            window.requestAnimationFrame {
                assertEquals(expected = "Test Title", actual = document.title)
                done()
            }
        }

        it("Updates page title with multiple children") { done ->
            render(container) {
                div {
                    helmet(title = "Test Title")
                    helmet(title = "Child One Title")
                    helmet(title = "Child Two Title")
                }
            }

            window.requestAnimationFrame {
                assertEquals(expected = "Child Two Title", actual = document.title)
                done()
            }
        }

        it("Updates page title with varying usage of function param and inner tag text") { done ->
            render(container) {
                div {
                    helmet { title { + "Test Title" } }
                    helmet(title = "Child One Title")
                    helmet { title { + "Child Two Title" } }
                }
            }

            window.requestAnimationFrame {
                assertEquals(expected = "Child Two Title", actual = document.title)
                done()
            }
        }

        it("Sets title based on deepest nested component") { done ->
            render(container) {
                div {
                    helmet(title = "Main Title")
                    div {
                        helmet(title = "Nested Title")
                    }
                }
            }

            window.requestAnimationFrame {
                assertEquals(expected = "Nested Title", actual = document.title)
                done()
            }
        }

        it("Sets title using deepest nested component with a defined title") { done ->
            render(container) {
                div {
                    helmet(title = "Main Title")
                    helmet()
                }
            }

            window.requestAnimationFrame {
                assertEquals(expected = "Main Title", actual = document.title)
                done()
            }
        }

        describe("Template") {
            it("Uses a titleTemplate if defined") { done ->
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

            it("Uses a titleTemplate based on deepest nested component") { done ->
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

            it("Uses defaultTitle if no title is defined") { done ->
                render(container) {
                    helmet(
                        defaultTitle = "Fallback",
                        title = "",
                        titleTemplate = "This is a %s of the titleTemplate feature"
                    )
                }

                window.requestAnimationFrame {
                    assertEquals(expected = "Fallback", actual = document.title)
                    done()
                }
            }

            it("Replaces multiple title strings in titleTemplate") { done ->
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

            it("Merges deepest component title with nearest upstream titleTemplate") { done ->
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

            it("Renders dollar characters in a title correctly when titleTemplate present") { done ->
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

        describe("Properties") {
            it("Page title with property") { done ->
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
                        actual = titleTag?.getAttribute("itemprop")
                    )
                    done()
                }
            }
        }
    }

    describe("Meta Tags") {
        it("Appends a meta tag") { done ->
            render(container) {
                helmet {
                    meta(name = "charset", content = "utf-8") {}
                }
            }

            window.requestAnimationFrame {
                val metaTags = document.getElementsByTagName("meta").asList()
                assertTrue(metaTags.isNotEmpty())
                assertEquals(expected = "charset", actual = metaTags[0].getAttribute("name"))
                assertEquals(expected = "utf-8", actual = metaTags[0].getAttribute("content"))
                done()
            }
        }

        it("Appends a meta tag and another one deeper in node tree") { done ->
            render(container) {
                div {
                    helmet {
                        meta(name = "og:title", content = "Test Tag One") {}
                    }

                    div {
                        helmet {
                            meta(name = "og:description", content = "Test Tag Two") {}
                        }
                    }
                }
            }

            window.requestAnimationFrame {
                val metaTags = document.getElementsByTagName("meta").asList()

                assertEquals(expected = 2, actual = metaTags.size)

                val (one, two) = metaTags

                assertEquals(expected = "og:title", actual = one.getAttribute("name"))
                assertEquals(expected = "Test Tag One", actual = one.getAttribute("content"))
                assertEquals(expected = "og:description", actual = two.getAttribute("name"))
                assertEquals(expected = "Test Tag Two", actual = two.getAttribute("content"))

                done()
            }
        }
    }

    describe("Style Tags") {
        it("Creates an inlined style tag") { done ->
            val style = """body { color: red; }"""
            render(container) {
                div {
                    helmet {
                        style(type = "text/css") { + style }
                    }
                }
            }

            window.requestAnimationFrame {
                val element = assertNotNull(
                    headElement?.querySelector("style[$HELMET_ATTRIBUTE]")
                )
                assertTrue(element.hasHelmetAttribute)
                assertEquals(expected = "text/css", actual = element.getAttribute("type"))
                assertEquals(expected = style, actual = element.innerHTML)
                assertEquals(
                    expected = """<style type="text/css" $HELMET_ATTRIBUTE="true">$style</style>""",
                    actual = element.outerHTML
                )
                done()
            }
        }
    }
}
