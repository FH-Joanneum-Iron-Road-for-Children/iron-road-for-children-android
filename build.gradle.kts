import org.jlleitschuh.gradle.ktlint.KtlintExtension
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

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint.configure()
}

ktlint.configure()

fun KtlintExtension.configure() {
    android.set(true)
    reporters {
        reporter(ReporterType.SARIF)
        reporter(ReporterType.HTML)
    }
    disabledRules.add("no-wildcard-imports")
}

tasks.register("setupGitHooks") {
    dependsOn(tasks.getByPath(":addKtlintFormatGitPreCommitHook"))

    doLast {
        val prePushHookVersionMarker = "### KTLINT CHECK AND DETEKT GRADLE PRE PUSH HOOK v1.0 ###"
        val prePushHookFile = file(rootProject.projectDir.resolve(".git/hooks/pre-push"))

        if (!prePushHookFile.exists() || !prePushHookFile.endsWith(prePushHookVersionMarker)) {
            val script = """
                #!/bin/bash -e
    
                # Prevents pushing any code to the server that does not comply with klint or detekt
                # It ignores any unchecked files.
    
                # Ignore any unchecked file
                echo "Stashing changes before doing checks"
                git stash push -q -u --keep-index
    
                # Pop the stash once the scripts finishes, fails or gets cancelled
                function pop_stash() {
                    echo "Checks completed, popping stash"
                    git stash pop -q
                }
    
                trap "exit" INT TERM ERR
                trap pop_stash EXIT
    
                # Do the actual check ups
                ./gradlew ktlintCheck detekt
            """.trimIndent()
            prePushHookFile.writeText("$script\n$prePushHookVersionMarker")
            prePushHookFile.setExecutable(true)
        }
    }
}

// Make sure that the git hooks are setup (executed with each gradle sync)
tasks.getByPath(":prepareKotlinBuildScriptModel").dependsOn(":setupGitHooks")
