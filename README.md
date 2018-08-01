[npm]: 	https://img.shields.io/npm/v/npm.svg
[license]: https://img.shields.io/badge/License-Apache%202.0-lightgrey.svg
[discord]: https://discord.gg/XCmwxy8
[discord-widget]: https://discordapp.com/api/guilds/301012120613552138/widget.png

[ ![npm][] ](https://www.npmjs.com/package/kotlin-react-helmet)
[ ![license][] ](https://github.com/Shengaero/kotlin-react-helmet/tree/master/LICENSE)

[ ![Discord][discord-widget] ][discord]

# kotlin-react-helmet

A Kotlin2js wrapper for [react-helmet](https://github.com/nfl/react-helmet).

## Usage

kotlin-react-helmet uses the same DSL provided by [kotlin-react](https://github.com/JetBrains/kotlin-wrappers/tree/master/kotlin-react),
and simply extends upon it in order to provide the functionality one might expect from the original react-helmet library:

```kotlin
fun main(args: Array<String>) {
    render(document.getElementById("root")) {
        helmet(title = "Hello from kotlin-react-helmet!")
        h1 { + "Hello, World!" }
    }
}
```

When rendered into a typical starter template, the following will be produced:
```html
<html>
  <head>
    <title>Hello from kotlin-react-helmet!</title>
  </head>
  <body>
    <div id="root">
      <h1>Hello, World!</h1>
    </div>
  </body>
</html>
```

For more info on how react-helmet works, see their [github repo's readme](https://github.com/nfl/react-helmet/blob/master/README.md)

## Installation

#### With NPM

`npm i kotlin-react-helmet`

If you are using Intellij IDEA and [`create-react-kotlin-app`](https://github.com/JetBrains/create-react-kotlin-app),
you can run the following to have your IDE resolve the library:

`npm run gen-idea-libs`

#### With Gradle

```gradle
repositories {
    jcenter()
}

dependencies {
    compile "me.kgustave:kotlin-react-helmet:$kotlinReactHelmetVersion"
}
```

## License

kotlin-react-helmet is licensed under the Apache 2.0 License

```
Copyright 2018 Kaidan Gustave

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```