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
@file:Suppress("NOTHING_TO_INLINE", "UnsafeCastFromDynamic", "unused")
package jest

// This is a temporary DSL until kotlin maintainers make their own.
// If that doesn't happen eventually, you'll be seeing one from me, kek.

@DslMarker
annotation class JestDsl


// Callbacks

typealias ProvidesCallback = (cb: DoneCallback) -> Unit

external interface DoneCallback {
    fun fail(error: String): Any
}

inline operator fun DoneCallback.invoke(vararg args: Any): Any {
    return if(args.isNotEmpty()) asDynamic()(args) else asDynamic()()
}


// Describe

external val describe: Describe

external interface Describe {
    val only: Describe
    val skip: Describe
}

@JestDsl inline operator fun Describe.invoke(name: String, noinline fn: () -> Unit) {
    asDynamic()(name, fn)
}

@JestDsl inline fun describe(name: String, noinline fn: () -> Unit) {
    describe.asDynamic()(name, fn)
}


// It

external val it: It

external interface It {
    val only: It
    val skip: It
}

@JestDsl inline fun it(name: String, noinline fn: ProvidesCallback) = it.invoke(name, fn)

@JestDsl inline fun it(name: String, noinline fn: ProvidesCallback, timeout: Int) = it.invoke(name, fn, timeout)

@JestDsl inline operator fun It.invoke(name: String, noinline fn: ProvidesCallback) {
    asDynamic()(name, fn)
}

@JestDsl inline operator fun It.invoke(name: String, noinline fn: ProvidesCallback, timeout: Int) {
    asDynamic()(name, fn, timeout)
}


// Lifecycle

@JestDsl external fun beforeEach(fn: ProvidesCallback, timeout: Int = definedExternally)
@JestDsl external fun afterEach(fn: ProvidesCallback, timeout: Int = definedExternally)

@JestDsl inline fun beforeEach(crossinline fn: () -> Unit) = beforeEach({ done -> fn(); done() })
@JestDsl inline fun afterEach(crossinline fn: () -> Unit) = afterEach({ done -> fn(); done() })
