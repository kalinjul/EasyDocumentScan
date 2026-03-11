@file:OptIn(ExperimentalWasmDsl::class)

import io.github.kalinjul.convention.config.configureIosTargets
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("io.github.kalinjul.convention.kotlin.multiplatform.mobile")
    id("io.github.kalinjul.convention.android.library")
    id("io.github.kalinjul.convention.centralPublish")
    id("io.github.kalinjul.convention.compose.multiplatform")
}

description = "Compose Multiplatform Document Scanner for Android/iOS"

kotlin {
    configureIosTargets()
    jvm()
    wasmJs() {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }

        jvmMain {
            dependencies {
            }
        }
    }
}