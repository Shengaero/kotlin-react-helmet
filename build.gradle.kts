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
import com.moowork.gradle.node.npm.NpmInstallTask
import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.util.Properties
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

plugins {
  id("idea")
  id("kotlin2js")
  id("maven-publish")
  id("com.moowork.node")
  id("com.jfrog.bintray")
  id("me.kgustave.pkg.json")
}

group = "me.kgustave"
version = "1.2.0"

val npmBuildDir = "build/npm"

val kotlinVersion: String by ext
val kotlinExtensionsNpmVersion: String by ext
val reactVersion: String by ext
val kotlinReactVersion: String by ext
val kotlinReactNpmVersion: String by ext
val kotlinCoroutinesVersion: String by ext
val reactHelmetVersion: String by ext
val jestVersion: String by ext

kotlin {
  sourceSets {
    all {
      languageSettings.useExperimentalAnnotation("kotlin.Experimental")
    }
  }
}

node {
  download = false
  npmWorkDir = file("$buildDir/npm")
}

pkg {
  name = project.name
  version = "${project.version}"
  description = "A kotlin wrapper for react-helmet"
  main = "kotlin-react-helmet.js"
  license = "Apache-2.0"
  homepage = "https://github.com/Shengaero/kotlin-react-helmet#readme"
  scripts = mapOf(
    "test" to "karma start karma/build.karma.config.js",
    "test:watch" to "karma start karma/continuous.karma.config.js"
  )

  author {
    name = "Kaidan Gustave"
    url = "https://kgustave.me/"
    email = "kaidangustave@yahoo.com"
  }

  repository {
    type = "git"
    url = "git+https://github.com/Shengaero/kotlin-react-helmet.git"
  }

  bugs {
    url = "https://github.com/Shengaero/kotlin-react-helmet/issues"
  }

  keywords = listOf(
    "kotlin",
    "kotlin2js",
    "kotlin-react",
    "react",
    "react-helmet"
  )

  files = listOf(
    "**/**/*.kjsm",
    "*.js",
    "*.js.map",
    "*.meta.js",
    "LICENSE",
    "package.json",
    "README.md"
  )
}

repositories {
  jcenter()
  maven(url = "https://kotlin.bintray.com/kotlin-js-wrappers")
}

dependencies {
  implementation(kotlin("stdlib-js", kotlinVersion))
  implementation("org.jetbrains:kotlin-react:$kotlinReactVersion")
  implementation("org.jetbrains:kotlin-react-dom:$kotlinReactVersion")

  devDependency("@jetbrains", "kotlin-extensions", kotlinExtensionsNpmVersion)
  devDependency("@jetbrains", "kotlin-react", kotlinReactNpmVersion)
  devDependency("@jetbrains", "kotlin-react-dom", kotlinReactNpmVersion)
  devDependency("core-js", "2.5.7")
  devDependency("kotlin", kotlinVersion)
  devDependency("react", reactVersion)
  devDependency("react-dom", reactVersion)
  devDependency("react-helmet", reactHelmetVersion)

  peerDependency("@jetbrains", "kotlin-react", "^$kotlinReactNpmVersion")
  peerDependency("@jetbrains", "kotlin-react-dom", "^$kotlinReactNpmVersion")
  peerDependency("kotlin", "^$kotlinVersion")
  peerDependency("react-helmet", "^$reactHelmetVersion")

  testImplementation(kotlin("test-js", kotlinVersion))
  testImplementation("org.jetbrains:kotlin-react:$kotlinReactVersion")
  testImplementation("org.jetbrains:kotlin-react-dom:$kotlinReactVersion")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$kotlinCoroutinesVersion")

  devDependency("babelify", "8")
  devDependency("babel-core", "6")
  devDependency("browserify", ">=10 <17")
  devDependency("chai", "^4.2.0")
  devDependency("karma", "^3.1.4")
  devDependency("karma-chai", "^0.1.0")
  devDependency("karma-coverage", "^1.1.1")
  devDependency("karma-babel-preprocessor", "^7.0.0")
  devDependency("karma-browserify", "^6.0.0")
  devDependency("karma-sourcemap-loader", "^0.3.7")
  devDependency("karma-chrome-launcher", "^2.0.0")
  devDependency("karma-html-reporter", "^0.2.7")
  devDependency("karma-mocha", "^1.3.0")
  devDependency("kotlin-test", kotlinVersion)
  devDependency("kotlinx-coroutines-core", kotlinCoroutinesVersion)
  devDependency("lodash", "^4.17.5")
  devDependency("mocha", "^5.2.0")
  devDependency("watchify", ">=3 <4")
}

tasks {
  withType<Kotlin2JsCompile> {
    kotlinOptions {
      moduleKind = "commonjs"
      noStdlib = true
      metaInfo = true
      typedArrays = true
      sourceMap = true
      sourceMapEmbedSources = "always"
      main = "noCall"
    }
  }

  jar {
    baseName = "kotlin-react-helmet"
    version = "1.2.0"
    classifier = ""
    manifest {
      attributes(
        "Automatic-Module-Name" to project.name.replace('-', '.'),
        "Implementation-Version" to "${project.version}",
        "Implementation-Title" to project.name
      )
    }
  }

  val sourcesJar by creating(Jar::class) {
    group = "build"
    description = "Generates a sources jar"

    baseName = "kotlin-react-helmet"
    version = "1.2.0"
    classifier = "sources"

    with(kotlin.sourceSets["main"]) {
      from(kotlin.files)
      from(resources.files)
    }
  }

  val copyNpmPackageFiles by creating(Copy::class) {
    group = "npm-build"

    from(".")
    into(npmBuildDir)
    include(
      "CHANGELOG.md",
      "README.md",
      "LICENSE",
      "package.json"
    )
  }

  val npmBuild by creating(Copy::class) {
    group = "npm-build"

    mustRunAfter(clean)
    dependsOn(copyNpmPackageFiles)
    dependsOn(compileKotlin2Js)

    from(compileKotlin2Js.get().destinationDir)
    into(npmBuildDir)
  }

  build {
    mustRunAfter(clean)
    dependsOn(clean)
    dependsOn(npmBuild)
    dependsOn(sourcesJar)
  }

  npmInstall {
    dependsOn(packageJson)
    mustRunAfter(packageJson)
  }

  val populateTestModules by creating(Copy::class) {
    dependsOn(compileTestKotlin2Js)

    val compile by configurations.compileClasspath
    val testCompile by configurations.testCompileClasspath
    val filter: PatternFilterable.() -> Unit = {
      include("*.js")
      include("*.js.map")
    }

    from(fileTree(compileKotlin2Js.get().destinationDir).matching(filter))

    (compile + testCompile).forEach {
      from(zipTree(it.absolutePath).matching(filter))
    }

    into("${project.buildDir}/node_modules")
  }

  val runJest by creating(NpmTask::class) {
    dependsOn(compileTestKotlin2Js)
    dependsOn(populateTestModules)
    setArgs(listOf("run", "test"))
  }

  test {
    dependsOn(runJest)
  }

  val npmPublish by "npm_publish"(NpmTask::class) {
    group = "publishing"

    setArgs(listOf("--access", "public"))
    setExecOverrides(closureOf<ExecSpec> {
      workingDir = file(npmBuildDir)
    })
  }

  task("publishFullRelease") {
    group = "publishing"
    description = "Releases a version"

    dependsOn(build)
    dependsOn(bintrayPublish)
    dependsOn(npmPublish)

    bintrayPublish.get().mustRunAfter(build)
    npmPublish.mustRunAfter(bintrayPublish)
  }
}

bintray {
  val propFile = file("gradle/bintray.properties")
  if(propFile.exists()) {
    val properties = Properties().apply { propFile.reader().use { load(it) } }
    user = "${properties["bintray.user.name"]}"
    key = "${properties["bintray.api.key"]}"
    publish = "${properties["bintray.publish"]}" == "true"
  }

  setPublications("BintrayRelease")

  pkg.apply {
    repo = "maven"
    name = project.name
    setLicenses("Apache-2.0")
    vcsUrl = "https://github.com/Shengaero/kotlin-react-helmet.git"
    githubRepo = "Shengaero/kotlin-react-helmet"
    githubReleaseNotesFile = "README.md"
    publicDownloadNumbers = true
    version.apply {
      name = "${project.version}"
//      released = OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
  }
}

publishing {
  publications {
    create("BintrayRelease", MavenPublication::class) {
      groupId = "${project.group}"
      artifactId = project.name
      version = "${project.version}"

      from(project.components["java"])
      artifact(project.tasks["sourcesJar"] as Jar)
    }
  }
}

tasks.wrapper {
  gradleVersion = "5.0"
}
