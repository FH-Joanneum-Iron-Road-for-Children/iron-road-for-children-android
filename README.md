# Iron Road For Children Android App (IRFC)

## Getting started

The project uses libraries which make use of code generation with `ksp` (Ktorfit, Room,
ComposeDestinations). When building the project the needed files will be generated automatically.
To initially generate all the files without building the whole project you can also run
`./gradlew kspDebugKotlin`. This may also be necessary after you updated the git repository
(e.g. pull, switch branch, ...). In case some classes/functions etc. can not be found run this
command to be sure that all the generated files are up to date.

## Further References: Language, Libraries & Tools

- [Gradle](https://gradle.org/) Build tool
  - [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) Fixed Gradle
    version in all environments
  - [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) Type safe Gradle
    build configs
- [Kotlin](https://kotlinlang.org/) Main language
  - [Coroutines, Suspending Functions & Flow](https://kotlinlang.org/docs/coroutines-guide.html)
  - [KSP](https://kotlinlang.org/docs/ksp-overview.html) Kotlin Symbol Processing
- [Ktorfit](https://foso.github.io/Ktorfit/) HttpClient abstraction
  - [Ktor](https://ktor.io/) HttpClient
  - [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html) JSON serializer
- [Room](https://developer.android.com/training/data-storage/room) Database abstraction
- [Koin](https://insert-koin.io/) Dependency Injection
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
  - [Compose Destinations](https://composedestinations.rafaelcosta.xyz/) Type safe navigation
  - [Coil](https://coil-kt.github.io/coil/) Async image loading and caching
  - [Material 3](https://developer.android.com/jetpack/compose/designsystems/material3)