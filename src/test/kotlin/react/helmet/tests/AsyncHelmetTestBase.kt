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
@file:Suppress("UNUSED_ANONYMOUS_PARAMETER", "UNUSED_VARIABLE", "UnsafeCastFromDynamic")
package react.helmet.tests

import kotlinx.coroutines.*
import org.w3c.dom.Element
import react.dom.unmountComponentAtNode
import kotlin.browser.document
import kotlin.js.Promise

abstract class AsyncHelmetTestBase {
    private lateinit var _container: Element
    protected val container get() = _container

    protected fun runTest(block: AsyncTestContext.() -> Unit): Promise<dynamic> {
        _container = document.createElement("div")
        val headElement = document.head ?: document.querySelector("head")
        headElement?.innerHTML = ""
        return promise(Dispatchers.Unconfined) {
            val context = AsyncTestContextImpl(parent = coroutineContext[Job])
            val deferred = waitOnContext(context)
            val result = runCatching {
                context.block()
                deferred.await()
            }
            //test
            unmountComponentAtNode(container)
            return@promise result.getOrThrow()
        }
    }

    private fun CoroutineScope.waitOnContext(context: AsyncTestContextImpl) =
        async(Dispatchers.Unconfined) {
            withTimeout(2000L) {
                context.await()
            }
        }

    private class AsyncTestContextImpl(parent: Job?): AsyncTestContext {
        private val completion = CompletableDeferred<Unit>(parent = parent)

        suspend fun await() = completion.await()

        override fun done(t: Throwable?) {
            when(t) {
                null -> completion.complete(Unit)
                else -> completion.completeExceptionally(t)
            }
        }
    }

    private companion object Scope: CoroutineScope {
        override val coroutineContext get() = SupervisorJob()
    }
}
