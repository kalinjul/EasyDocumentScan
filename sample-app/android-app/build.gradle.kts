plugins {
    id("io.github.kalinjul.convention.android.application")
    id("io.github.kalinjul.convention.compose.multiplatform")
}

android {
    namespace = "io.github.kalinjul.easydocumentscan.sample"

    defaultConfig {
        applicationId = "io.github.kalinjul.easydocumentscan.sample"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
//            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), ("proguard-rules.pro")))
        }
    }
}


dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(projects.sampleApp.shared)
}