buildscript {
    dependencies {
        classpath(libs.androidGradlePlugin)

        classpath(libs.kotlinGradlePlugin)

        classpath(libs.daggerHiltGradlePlugin)

        classpath(libs.navigationSafeArgsGradlePlugin)

        classpath(libs.openSourceLicensesGradlePlugin) {
            exclude(group = "com.google.protobuf")
        }

        classpath(libs.jacoco)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.cacheFixPlugin) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.versions) apply false
}

subprojects {
        apply(plugin = "com.diffplug.spotless")
        configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("$buildDir/**/*.kt")

                ktlint()
                trimTrailingWhitespace()
                endWithNewline()
            }

            kotlinGradle {
                target("*.gradle.kts")
                ktlint()
            }
        }

//    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).all {
//        kotlinOptions {
//            // Treat all Kotlin warnings as errors
//            allWarningsAsErrors = true
//
//            // Set JVM target to 1.8
//            jvmTarget = JavaVersion.VERSION_17
//        }
//    }
}
