@file:Suppress("UnstableApiUsage")
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.1.5"
}
rootProject.name = "Iron Road For Children"
include(":app")

gitHooks {
    preCommit {
        tasks("ktlintFormat")
    }
    createHooks()
}
