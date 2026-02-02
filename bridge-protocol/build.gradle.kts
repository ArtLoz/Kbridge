plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "com.l2bot.bridge"
version = "1.0.0"
group = "com.github.artloz"
kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
    }
}
