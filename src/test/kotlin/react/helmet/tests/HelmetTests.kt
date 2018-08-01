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

// Deprecated testing unit.
// kotlin.test, it's not smart enough, seriously.
// I cannot use javascript unit testing features that are so well recognized
//in the javascript ecosystem, it just doesn't work, and it probably never will tbh.

//import jest.expect
//import jest.invoke
//import jest.toBe
//import org.w3c.dom.Element
//import react.buildElement
//import react.buildElements
//import react.dom.render
//import react.dom.unmountComponentAtNode
//import react.helmet.helmet
//import kotlin.browser.document
//import kotlin.browser.window
//import kotlin.test.AfterTest
//import kotlin.test.BeforeTest
//import kotlin.test.Ignore
//import kotlin.test.Test
//
//@Ignore
//class HelmetTests {
//    private val container = document.createElement("div")
//    private var headElement = null as Element?
//
//    @BeforeTest fun resetHead() {
//        headElement = headElement ?: document.head
//            ?: checkNotNull(document.querySelector("head"))
//
//        headElement?.innerHTML = ""
//    }
//
//    @AfterTest fun unmount() {
//        unmountComponentAtNode(container)
//    }
//
//    @Test fun updatesPageTitle() {
//        render(buildElement {
//            helmet(title = "Test Title")
//        }, container)
//
//        window.requestAnimationFrame {
//            expect(document.title) toBe "Test Title"
//            println("test")
//        }
//    }
//
//    @Test fun updatesPageCorrectlyWithMultipleHelmets() {
//        render(buildElements {
//            helmet(title = "Test Title")
//            helmet(title = "Child One Title")
//            helmet(title = "Child Two Title")
//        }, container)
//
//        window.requestAnimationFrame {
//            expect(document.title) toBe "Child Two Title"
//            println("test2")
//        }
//    }
//}
