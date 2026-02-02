plugins {
    kotlin("jvm")
    // Удаляем или комментируем строку application, чтобы не было конфликта задач 'run'
    // application
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

dependencies {
    implementation(project(":bridge-api-core"))
    implementation(project(":bridge-transport-jvm"))

    // Зависимости Compose
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.9.0")
}

// Удаляем старый блок application { ... }
// Вместо него используем блок compose.desktop

compose.desktop {
    application {
        // Указываем главный класс здесь
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Exe)
            packageName = "L2BotSample"
            packageVersion = "1.0.0"
        }
    }
}

kotlin {
    jvmToolchain(11)
}