import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
}

// Make sure that the format pre commit hook exist (executed with each gradle sync)
tasks.getByPath(":prepareKotlinBuildScriptModel").dependsOn(":addKtlintFormatGitPreCommitHook")
ktlint {
    android.set(true)
    reporters {
        reporter(ReporterType.SARIF)
    }
    disabledRules.add("no-wildcard-imports")
}
