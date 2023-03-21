@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.dsl.NdkOptions.DebugSymbolLevel
import com.github.triplet.gradle.androidpublisher.ReleaseStatus
import de.jensklingenberg.ktorfit.gradle.KtorfitGradleConfiguration
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    kotlin("android")
    id("com.android.application")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("de.jensklingenberg.ktorfit") version "1.0.0"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("com.github.triplet.play") version "3.8.1"
}

android {
    namespace = "at.irfc.app"
    compileSdk = 33

    defaultConfig {
        applicationId = "at.irfc.app"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "2.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        val keyPassword: String? = project.findProperty("keyPassword")?.toString()
        val storePassword: String? = project.findProperty("storePassword")?.toString()
        val keyAlias: String? = project.findProperty("keyAlias")?.toString()
        val keyStoreFile: File = rootDir.resolve(".keys/app_sign.jks")

        // Only create signingConfig, when all needed configs are available
        if (
            keyPassword != null &&
            storePassword != null &&
            keyAlias != null &&
            keyStoreFile.exists()
        ) {
            create("release") {
                this.storeFile = keyStoreFile
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
                this.storePassword = storePassword
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // TODO enable proguard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
            ndk.debugSymbolLevel = DebugSymbolLevel.FULL.name
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val ktorfitVersion = "1.0.0"

configure<KtorfitGradleConfiguration> {
    version = ktorfitVersion
}

ksp {
    arg("compose-destinations.codeGenPackageName", "at.irfc.app.generated.navigation")
    arg("room.schemaLocation", projectDir.resolve("schemas").absolutePath)
}

detekt {
    config = files(rootDir.resolve("detekt.yml"))
    buildUponDefaultConfig = true
    basePath = rootDir.path
}

tasks.withType<Detekt>().configureEach {
    this.jvmTarget = "1.8"
    jdkHome.set(file(System.getProperty("java.home")))
}

play {
    defaultToAppBundles.set(true)
    serviceAccountCredentials.set(rootDir.resolve(".keys/service-account.json"))
    track.set("internal")
    // TODO use COMPLETED when first internal test was published
    releaseStatus.set(ReleaseStatus.DRAFT)
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")

    // Room DB
    val roomVersion = "2.5.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Ktorfit HTTP client
    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfitVersion")
    ksp("de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")

    val ktorVersion = "2.2.4"
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    // Koin DI
    val koinVersion = "3.3.3"
    compileOnly("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    // Version is not aligned to other koin modules
    implementation("io.insert-koin:koin-androidx-compose:3.4.2")

    // Coil async image loader and caching
    val coilVersion = "2.2.2"
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-compose:$coilVersion")

    // Compose Destinations Navigation
    val composeDestinationsVersion = "1.8.36-beta"
    implementation("io.github.raamcosta.compose-destinations:core:$composeDestinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion")

    // Compose UI
    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation(composeBom)
    debugImplementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // Material 3 does not have swipe to refresh yet TODO remove once added to material 3
    implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Test tooling
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
