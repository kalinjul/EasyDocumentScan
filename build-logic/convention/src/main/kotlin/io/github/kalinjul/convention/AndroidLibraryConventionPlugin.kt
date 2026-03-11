package io.github.kalinjul.convention

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import io.github.kalinjul.convention.config.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
                    configureKotlinAndroid(this)

                    minSdk = libs.versions.minSdk.get().toInt()
                    compileSdk = libs.versions.compileSdk.get().toInt()


                    optimization {
                        consumerKeepRules.file(target.layout.projectDirectory.file("consumer-rules.pro").asFile)
                        consumerKeepRules.publish = true
                    }

                    namespace = "org.publicvalue.multiplatform.${project.name.replace("-", ".")}"
                }
            }
        }
    }
}