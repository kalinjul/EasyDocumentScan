import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "org.publicvalue.multiplatform.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17 // hardcode to default android studio embedded jdk version JavaVersion.toVersion(libs.versions.jvmTarget.get())
    targetCompatibility = JavaVersion.VERSION_17 // hardcode to default android studio embedded jdk version  JavaVersion.toVersion(libs.versions.jvmTarget.get())
}
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvmTarget.get()))
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.nexusPublish.gradlePlugin)
    compileOnly(libs.dokka.gradlePlugin)

    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "io.github.kalinjul.convention.android.library"
            implementationClass = "io.github.kalinjul.convention.AndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "io.github.kalinjul.convention.android.application"
            implementationClass = "io.github.kalinjul.convention.AndroidApplicationConventionPlugin"
        }
        register("kotlinMultiplatform") {
            id = "io.github.kalinjul.convention.kotlin.multiplatform"
            implementationClass = "io.github.kalinjul.convention.KotlinMultiplatformConventionPlugin"
        }
        register("kotlinMultiplatformMobile") {
            id = "io.github.kalinjul.convention.kotlin.multiplatform.mobile"
            implementationClass = "io.github.kalinjul.convention.KotlinMultiplatformMobileConventionPlugin"
        }

        register("composeMultiplatform") {
            id = "io.github.kalinjul.convention.compose.multiplatform"
            implementationClass = "io.github.kalinjul.convention.ComposeMultiplatformConventionPlugin"
        }
        register("centralPublish") {
            id = "io.github.kalinjul.convention.centralPublish"
            implementationClass = "io.github.kalinjul.convention.MavenCentralPublishConventionPlugin"
        }
    }
}