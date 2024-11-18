plugins {
    id("io.github.kalinjul.convention.android.application")
    id("io.github.kalinjul.convention.kotlin.multiplatform.mobile")
    id("io.github.kalinjul.convention.compose.multiplatform")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.appcompat)

                implementation(projects.sampleApp.shared)
            }
        }
    }
}

android {
    namespace = "io.github.kalinjul.easydocumentscan.sample"

    defaultConfig {
        applicationId = "io.github.kalinjul.easydocumentscan.sample"
        versionCode = 1
        versionName = "1.0"
    }
}
