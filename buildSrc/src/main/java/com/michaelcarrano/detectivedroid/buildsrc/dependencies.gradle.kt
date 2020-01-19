package com.michaelcarrano.detectivedroid.buildsrc

object Versions {
    const val ktlint = "0.36.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.0-alpha08"
    const val playPublisherPlugin = "com.github.triplet.gradle:play-publisher:2.6.1"
    const val jacoco = "org.jacoco:org.jacoco.core:0.8.5"

    const val desugar = "com.android.tools:desugar_jdk_libs:1.0.4"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val json = "org.json:json:20180813"
    const val junit = "junit:junit:4.12"
    const val robolectric = "org.robolectric:robolectric:4.3.1"
    const val mockK = "io.mockk:mockk:1.9.3"
    const val mockitoKtx = "com.nhaarman:mockito-kotlin:1.6.0"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.1"

    const val roxie = "com.ww:roxie:0.4.0"

    object Google {
        const val material = "com.google.android.material:material:1.2.0-alpha03"
        const val openSourceLicensesPlugin = "com.google.android.gms:oss-licenses-plugin:0.10.1"
        const val openSourceLicensesLibrary =
            "com.google.android.gms:play-services-oss-licenses:17.0.0"
    }

    object Kotlin {
        private const val version = "1.3.70-eap-42"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.2.0-rc01"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"
        const val preference = "androidx.preference:preference:1.1.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.0-alpha01"

        object Navigation {
            private const val version = "2.2.0-rc04"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Fragment {
            private const val version = "1.2.0-rc01"
            const val fragment = "androidx.fragment:fragment:$version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Test {
            private const val version = "1.3.0-alpha03"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            const val junit = "androidx.test.ext:junit:1.1.2-alpha03"
            const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"

            const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0-alpha03"
        }

        object Paging {
            private const val version = "2.1.0"
            const val common = "androidx.paging:paging-common-ktx:$version"
            const val runtime = "androidx.paging:paging-runtime-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.2.0-rc03"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Room {
            private const val version = "2.2.2"
            const val common = "androidx.room:room-common:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }
    }

    object RxJava {
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.17"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    }

    object Dagger {
        private const val version = "2.25.4"
        const val dagger = "com.google.dagger:dagger:$version"
        const val androidSupport = "com.google.dagger:dagger-android-support:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Facebook {
        const val flipper = "com.facebook.flipper:flipper:0.30.1"
        const val soloader = "com.facebook.soloader:soloader:0.8.0"
    }
}
