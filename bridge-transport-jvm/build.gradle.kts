plugins {
    alias(libs.plugins.kotlin.jvm)
    `maven-publish`
}

group = libs.versions.sdkGroup.get()
version = libs.versions.sdkVersion.get()

dependencies {
    api(project(":bridge-protocol"))
    api(project(":bridge-api-core"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.jna)
    implementation(libs.jna.platform)
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "bridge-transport-jvm"
        }
    }
}
