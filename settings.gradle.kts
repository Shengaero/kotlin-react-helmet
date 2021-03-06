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
enableFeaturePreview("STABLE_PUBLISHING")
pluginManagement {
  val kotlinVersion: String by extra
  val nodePluginVersion: String by extra
  val bintrayVersion: String by extra
  val gradlePackageJsonVersion: String by extra

  repositories {
    jcenter()
    maven(url = "https://plugins.gradle.org/m2/")
    maven(url = "https://kotlin.bintray.com/kotlinx")
  }

  resolutionStrategy {
    eachPlugin {
      when(requested.id.id) {
        "kotlin2js" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        "com.moowork.node" -> useModule("com.moowork.gradle:gradle-node-plugin:$nodePluginVersion")
        "com.jfrog.bintray" -> useModule("com.jfrog.bintray.gradle:gradle-bintray-plugin:$bintrayVersion")
        "me.kgustave.pkg.json" -> useModule("me.kgustave:gradle-package-json-plugin:$gradlePackageJsonVersion")
      }
    }
  }
}

rootProject.name = "kotlin-react-helmet"
