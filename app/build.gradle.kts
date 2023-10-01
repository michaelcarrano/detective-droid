plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.android.gms.oss-licenses-plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")

    alias(libs.plugins.ksp)
}

apply(from = "../gradle/scripts/jacoco.gradle")

android {
    namespace = "com.michaelcarrano.detectivedroid"

    compileSdk = 34
    defaultConfig {
        applicationId = "com.michaelcarrano.detectivedroid"
        minSdk = 24
        targetSdk = 34

        versionCode = 18
        versionName = "2.1.9"

        resourceConfigurations += setOf("en")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {
            compose = false
            buildConfig = true
            viewBinding = true
            dataBinding = false
        }
    }

    bundle {
        language {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)

    implementation(libs.bundles.kotlin)

    implementation(libs.coreKtx)
    implementation(libs.constraintLayout)
    implementation(libs.preference)
    implementation(libs.recyclerview)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)

    implementation(libs.material)
    implementation(libs.openSourceLicenses)

    implementation(libs.roxie)

    implementation(libs.daggerHilt)
    ksp(libs.daggerHiltCompiler)
    testImplementation(libs.daggerHiltTesting)

    implementation(libs.bundles.rx)

    implementation(libs.timber)

    debugImplementation(libs.bundles.facebook)

    debugImplementation(libs.leakCanary)
    implementation(libs.plumber)

    testImplementation(libs.bundles.unitTests)
    androidTestImplementation(libs.bundles.androidTests)
}
