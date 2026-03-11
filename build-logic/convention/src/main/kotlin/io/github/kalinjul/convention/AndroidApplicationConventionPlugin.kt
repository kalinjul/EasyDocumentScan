package io.github.kalinjul.convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import io.github.kalinjul.convention.config.configureKotlinAndroid
import org.gradle.api.JavaVersion

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    targetSdk = libs.versions.targetSdk.get().toInt()
                    compileSdk = libs.versions.compileSdk.get().toInt()
                    minSdk = libs.versions.minSdk.get().toInt()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }


                // Can remove this once https://issuetracker.google.com/issues/260059413 is fixed.
                // See https://kotlinlang.org/docs/gradle-configure-project.html#gradle-java-toolchains-support
                compileOptions {
                    sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
                    targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
                }
            }
        }
    }
}