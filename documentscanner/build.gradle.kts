import io.github.kalinjul.convention.config.configureIosTargets

plugins {
    id("io.github.kalinjul.convention.android.library")
    id("io.github.kalinjul.convention.kotlin.multiplatform.mobile")
    id("io.github.kalinjul.convention.centralPublish")
    id("io.github.kalinjul.convention.compose.multiplatform")
}

description = "Compose Multiplatform Document Scanner for Android/iOS"

kotlin {
    configureIosTargets()
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
//                implementation(compose.material3)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.mlkit.documentscanner)
                implementation(libs.androidx.activity.compose)
            }
        }

        jvmMain {
            dependencies {
            }
        }
    }
}