<br>
<h3 align="center">
  <a href="https://github.com/FH-Joanneum-Iron-Road-for-Children/.github/blob/develop/profile/images/logo.png">
  <img src="https://github.com/FH-Joanneum-Iron-Road-for-Children/.github/blob/develop/profile/images/logo.png" alt="IRFC Logo" width="250" style="border-radius: 50px;">
  </a>
</h3>
<br>

# Iron Road For Children Android App (IRFC)

## Getting started

The project uses libraries which make use of code generation with `ksp` (Ktorfit, Room,
ComposeDestinations). When building the project the needed files will be generated automatically.
To initially generate all the files without building the whole project you can also run
`./gradlew kspDebugKotlin`. This may also be necessary after you updated the git repository
(e.g. pull, switch branch, ...). In case some classes/functions etc. can not be found run this
command to be sure that all the generated files are up to date.

## Code Style

The project uses ktlint and detekt to ensure a consistent code style. On first gradle sync git
pre-commit and pre-push hooks are generated. When committing ktlintFormat is run and the formatted
files are added back to the commit. When pushing ktlintCheck and detekt is executed. If there is a
issue with formatting then the push gets rejected. The issues are listed in the console and also can
be found in the build/reports folder. The listed issues must be fixed before you can push to remote.

The hooks can be skipped by using the `--no-verify` flag with the git commands. However the CI will
validate the style again and merging will not be allowed if the checks do not pass.

The tasks can also be executed manually using `./gradlew ktlintCheck` (or `./gradlew ktlintFormat`
to auto format all files) and `./gradlew detekt`.

## Create test build

The gradle `releaseTest` task does all the magic for you. It will create and push a test tag 
(e.g. `v2.0.0-test-12345`). This will trigger a CI/CD workflow which creates a release for testing. 
The signed apk and aab will be uploaded to [GitHub-Releases](https://github.com/FH-Joanneum-Iron-Road-for-Children/iron-road-for-children-android/releases).
From there it can be downloaded and manually installed.

> Make sure that the working tree is clean, otherwise the command will fail.

> Test releases will use the test deployment of the backend.

```shell
./gradlew releaseTest
```

## Create build for internal Testing (Google PlayStore)

The gradle `release` task does all the magic for you. If no parameters provided it will keep the
version and increases the build number by one.

> Make sure that the working tree is clean, otherwise the command will fail.

> Builds will use the production deployment of the backend.

```shell
./gradlew release
```

If you only want to update the version and automatically increase the build number use following
command:

```shell
./gradlew release -Pv=2.0.0
```

If you want to override both use

```shell
./gradlew release -Pv=2.0.0 -Pb=123
```

### Steps

1. Create a new release branch (can be skipped when already releasing from a non protected branch)
2. Run the release gradle task (optionally specify the version and/or build number). For current
   version have a look at the tags or the `gradle.properties` file.
    - Will increase the version specified in `gradle.properties` and commit this change
    - Create and push a version tag (e.g. `v2.0.0-b10`)
    - Trigger the publish to internal Testing CI/CD workflow
3. Go and grab a coffee while waiting till the CI/CD pipeline builds and publishes the app
4. Download the new version from Google Play (you have to be part of the internal testers group)
5. Test if the app works properly
6. If the app should be released to the public then go to the Google Play console and promote the
   just released version from `internalTesting` to `release` track
    - Do not forget to add a Version note!
7. Depending on the release type:
    - **Production release**: Merge the branch where the release was made from into main and main
      back into develop.
    - **Otherwise**: Merge the branch where the release was made into develop.

## Further References: Language, Libraries & Tools

- [Gradle](https://gradle.org/) Build tool
    - [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) Fixed Gradle
      version in all environments
    - [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) Type safe
      Gradle build configs
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
- [Ktlint](https://pinterest.github.io/ktlint/) Kotlin linter
    - [ktlint-gradle](https://github.com/JLLeitschuh/ktlint-gradle) Ktlint gradle plugin
- [Detekt](https://detekt.dev/) Kotlin static code analyzer
- [GGP](https://github.com/Triple-T/gradle-play-publisher) Gradle Play Publisher Plugin

## Room Database
Local data on a device are stored in a Room database.

### Increase version
After making changes to the database schema, such as adding a new column or table, upgrade the database version.

Under: package at.irfc.app.data.local
IrfcDatabase

This version number is used by Room to detect changes in the database schema. If Room notices that the version number has changed, it knows that it needs to migrate the database from the old version to the new one.

If you don’t update the version number, Room will assume the schema hasn’t changed and may throw a runtime error when it finds inconsistencies between the expected schema and the actual database structure.

By increasing the version number and providing a corresponding migration strategy, you ensure that:
- The user's existing data is preserved.
- Room can safely adapt the database to the new structure.
- The app avoids crashes due to schema mismatches.

## Navigation with com.ramcosta.composedestinations
This project uses the com.ramcosta.composedestinations library to simplify and strongly-type navigation in Jetpack Compose.

### What is Compose Destinations?
Compose Destinations is a navigation library built on top of Jetpack Compose Navigation. It reduces boilerplate and eliminates hardcoded route strings by generating code for navigation targets (aka "destinations").

You annotate your Composable screens with @Destination, and the library generates all required navigation boilerplate, like route strings, argument handling, deep links, and more.

### How It Works
#### 1. Annotate Your Composables
To create a navigation destination, annotate a Composable with @Destination:
```kotlin
@Destination
@Composable
fun ProgramScreen(navController: NavHostController) {
    // Screen UI
}
```

#### 2. Generated Destination Object
For each @Destination, a corresponding object is generated, such as:
```kotlin
object ProgramScreenDestination : DirectionDestination {
    override val route = "program_screen"

    @Composable
    override fun DestinationScope<Unit>.Content() {
        ProgramScreen(navController = navController)
    }
}
```

#### 3. Type-Safe Navigation
Use the generated destination objects to navigate without worrying about route strings:
```kotlin
navController.navigate(ProgramScreenDestination())
```

No more "program_screen" strings or manual argument parsing.

## Home Screen Overview
The HomeScreen is the app’s landing screen and serves as the starting point of the navigation graph (@RootNavGraph(start = true)). It combines dynamic backend-driven content (video, countdown, social media links) with a clean, responsive UI built in Jetpack Compose.

### Features
#### Intro Video
A promotional intro video is loaded dynamically from the backend and played in a loop using ExoPlayer. The video is muted by default but can be toggled via an on-screen icon.

#### Countdown Timer
A real-time countdown to a specified event date is displayed prominently. It updates every second and visually adapts to both portrait and landscape modes.

#### Social Media Integration
Facebook and Instagram links are fetched dynamically and displayed as clickable icons, which open the respective profiles in the user's browser.

#### Background Imagery
Static images (e.g., banner and countdown background) enhance visual appeal and provide branding support.

### Data Architecture
The HomeScreen relies on three repositories, each using a shared cachedRemoteResource pattern that ensures efficiency and offline support:

VideoRepository - 	Fetches a single IntroVideo object from the backend.

CountdownRepository -	Loads countdown data indicating the target event time.

SocialMediaRepository -	Retrieves a list of SocialMedia entries (e.g. Facebook, Instagram).

All repositories:
- Fetch from local cache (Room) by default.
- Optionally force a network refresh with the force: Boolean parameter.
- Update the local database if remote data has changed.

### Lifecycle Behavior
- All data is fetched in parallel when the screen is first composed via LaunchedEffect.
- DisposableEffect ensures ExoPlayer is properly released on screen disposal.
- The countdown timer uses a coroutine loop to stay updated every second.
- Muting behavior is tied to a remember state and can be toggled by the user.

### Responsive Design
The layout adjusts according to screen width and orientation:
- Social icons and video adapt their size based on the current screen configuration.
- The countdown and background image scale appropriately across devices.

### Developer Notes
- The HomeScreen uses koinInject() to access repositories, which allows easy testing and swapping of implementations.
- Additional social platforms can be added by extending the socialMedia list handling and updating the UI layout accordingly.
- The cachedRemoteResource utility simplifies reactive data flows and is a good candidate for reuse in future features.

## Event Reminder Notifications
This app provides users with a helpful notification 15 minutes before an event begins, ensuring they stay informed and prepared.

### How It Works
The notification system is implemented using Android’s AlarmManager and a BroadcastReceiver, and consists of two main components:

#### NotificationScheduler
This object is responsible for scheduling and canceling event reminder notifications.
When an event is created or updated, the app uses the event’s start time to calculate a trigger time exactly 15 minutes before the event begins.
Using AlarmManager.setExactAndAllowWhileIdle(...), it sets an exact alarm to fire at the calculated time, even when the device is idle (Doze mode).
A PendingIntent is created to deliver the event’s title and ID to the EventReminderReceiver when the alarm goes off.
The cancelNotification function allows previously scheduled notifications to be removed using the same PendingIntent.

#### EventReminderReceiver
This is a BroadcastReceiver that responds when the alarm is triggered.
It extracts the event title and description from the Intent.
If running on Android 8.0 (API level 26) or above, it creates a notification channel (if not already present).
On Android 13+ (API 33+), it checks whether the app has the required POST_NOTIFICATIONS permission before attempting to show a notification.
It builds and displays a high-priority notification with the event information, using NotificationCompat.

