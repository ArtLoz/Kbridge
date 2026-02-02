plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.l2bot.bridge"
version = "1.0.0"
group = "com.github.artloz"

dependencies {
    api(project(":bridge-protocol"))
    api(project(":bridge-api-core"))
    
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("net.java.dev.jna:jna:5.13.0")
    implementation("net.java.dev.jna:jna-platform:5.13.0")
}

kotlin {
    jvmToolchain(11)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "bridge-transport-jvm"
        }
    }
}