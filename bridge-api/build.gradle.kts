plugins {
    alias(libs.plugins.kotlin.multiplatform)
    `maven-publish`
}

group = libs.versions.sdkGroup.get()
version = libs.versions.sdkVersion.get()

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":bridge-protocol"))
                api(project(":bridge-api-models"))

                api(libs.kotlinx.coroutines.core)
            }
        }
    }
}
