pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "Kbridge"

include(":bridge-protocol")
include(":bridge-api-models")
include(":bridge-api-core")
include(":bridge-transport-jvm")
include(":sample")