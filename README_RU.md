# L2Bot Kotlin Bridge

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-blue.svg)](https://kotlinlang.org)
[![Platform](https://img.shields.io/badge/Platform-JVM-green.svg)](https://www.java.com)

Kotlin Multiplatform SDK для интеграции с L2 Adrenaline через Named Pipes.

[English version](README.md)

## Быстрый старт

```kotlin
// Инициализация
L2Adrenaline.setTransportProvider(JvmTransportProvider())

// Подключение
val bot = L2Adrenaline.getAvailableBots().first()
bot.connect()

// Использование API
val user = bot.user()
println("${user.name} HP: ${user.curHp}/${user.maxHp}")

// Подписка на события
bot.actionEvents.collect { println("Action: ${it.actionId}") }
```

## Установка

```kotlin
maven { url = uri("https://jitpack.io") }

dependencies {
    implementation("com.github.ArtLoz.Kbridge:bridge-api-core:1.0.0")
    implementation("com.github.ArtLoz.Kbridge:bridge-transport-jvm:1.0.0")
}
```

## Архитектура

```
bridge-protocol      → Абстракция транспорта
bridge-api-models    → Модели данных (L2User, L2Npc, L2Item...)
bridge-api-core      → Бизнес-логика (L2Bot, RpcClient)
bridge-transport-jvm → JVM реализация через Named Pipes
sample               → Пример использования
```

## Основное API

### Методы L2Bot

| Категория | Методы |
|-----------|--------|
| **Информация** | `user()`, `npcList()`, `charList()`, `inventory()`, `skillList()`, `dropList()` |
| **Движение** | `moveTo()`, `moveToTarget()`, `stand()`, `unstuck()` |
| **Бой** | `attack()`, `setTarget()`, `cancelTarget()`, `autoTarget()`, `assist()` |
| **Скиллы** | `useSkill()`, `useSkillGround()`, `stopCasting()`, `dispel()` |
| **Предметы** | `useItem()`, `pickUp()`, `destroyItem()`, `dropItem()` |
| **Социальное** | `say()`, `inviteParty()`, `leaveParty()`, `joinParty()` |
| **Диалоги** | `dlgOpen()`, `dlgSel()`, `bypassToServer()`, `confirmDialog()` |

### События (Flow)

```kotlin
bot.actionEvents      // Боевые/движение события
bot.packetEvents      // Пакеты сервер → клиент
bot.cliPacketEvents   // Пакеты клиент → сервер
bot.connectionStatus  // Изменения статуса подключения
bot.errors            // Ошибки RPC
```

## Протокол связи

**Named Pipes:**
```
l2bot_commands_{char}   → RPC запросы
l2bot_responses_{char}  → RPC ответы
l2bot_actions_{char}    → События действий
l2bot_packets_{char}    → Серверные пакеты
l2bot_clipackets_{char} → Клиентские пакеты
```

**Формат RPC:**
```json
// Запрос
{"id": 1, "method": "Engine.GetMe", "params": {}}

// Ответ
{"id": 1, "status": "success", "result": {...}}
```

## Требования

- Kotlin 2.3.0+
- JDK 17+
- Windows (Named Pipes)
- L2 Adrenaline с активным плагином

## Сборка и запуск

```bash
./gradlew build
./gradlew :sample:run
```

## Лицензия

MIT
