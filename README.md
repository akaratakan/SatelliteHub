# Satellite Hub

## Architecture
**Satellite Hub** is based on the clean architecture(as modular) and the MVVM pattern, which follows the [Google's official architecture guidance](https://developer.android.com/topic/architecture).

## Tech stack & Open-source libraries
- Minimum/Target SDK level: 21/34
- [Kotlin](https://kotlinlang.org/) based.
- [ksp](https://github.com/google/ksp): Kotlin Symbol Processing API.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/): for asynchronous.
- [Hilt](https://dagger.dev/hilt/): for dependency injection.
- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle): Observe Android lifecycles and handle UI states upon the lifecycle changes.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
- [Compose](https://developer.android.com/jetpack/compose): Jetpack Compose is the modern toolkit for building native Android UI. 
- [Room](https://developer.android.com/jetpack/androidx/releases/room): Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- [Moshi](https://github.com/square/moshi/): A modern JSON library for Kotlin and Java.
- [Material-Components-3](https://github.com/material-components/material-components-android): Build Jetpack Compose UIs with Material Design 3 Components, the next evolution of Material Design. 
- [Timber](https://github.com/JakeWharton/timber): A logger with a small, extensible API.
