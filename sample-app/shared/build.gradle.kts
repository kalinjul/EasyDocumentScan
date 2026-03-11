import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import io.github.kalinjul.convention.config.configureIosTargets

plugins {
    id("io.github.kalinjul.convention.kotlin.multiplatform.mobile")
    id("io.github.kalinjul.convention.android.library")
    id("io.github.kalinjul.convention.compose.multiplatform")
}

kotlin {
    configureIosTargets()
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)

                implementation(projects.documentscannerCompose)
            }
        }
        androidMain {
            dependencies {
            }
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.withType<Framework> {
            isStatic = true
            baseName = "shared"
//            export("io.github.kalinjul.kotlin.multiplatform:oidc-appsupport")
        }
    }
    android {
        namespace = "io.github.kalinjul.easydocumentscan.sample.shared"
    }
}