pluginManagement {
    includeBuild("build-logic")

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name="EasyDocumentScan"

include(":documentscanner")
include(":sample-app:shared")
include(":sample-app:android-app")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
// https://docs.gradle.org/8.3/userguide/configuration_cache.html#config_cache:stable
// enableFeaturePreview("STABLE_CONFIGURATION_CACHE")