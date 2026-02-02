# L2Bot Kotlin Bridge

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-blue.svg)](https://kotlinlang.org)
[![Platform](https://img.shields.io/badge/Platform-JVM-green.svg)](https://www.java.com)

Kotlin Multiplatform SDK for L2 Adrenaline bot integration via Named Pipes.

[Русская версия](README_RU.md)

## Quick Start

```kotlin
// Initialize
L2Adrenaline.setTransportProvider(JvmTransportProvider())

// Connect
val bot = L2Adrenaline.getAvailableBots().first()
bot.connect()

// Use API
val user = bot.user()
println("${user.name} HP: ${user.curHp}/${user.maxHp}")

// Subscribe to events
bot.actionEvents.collect { println("Action: ${it.actionId}") }
```

## Installation

```kotlin
maven { url = uri("https://jitpack.io") }

dependencies {
    implementation("com.github.ArtLoz.Kbridge:bridge-api-core:1.0.0")
    implementation("com.github.ArtLoz.Kbridge:bridge-transport-jvm:1.0.0")
}
```

## Architecture

```
bridge-protocol      → Transport abstraction
bridge-api-models    → Data models (L2User, L2Npc, L2Item...)
bridge-api-core      → Business logic (L2Bot, RpcClient)
bridge-transport-jvm → JVM Named Pipes implementation
sample               → Usage example
```

## Core API

### L2Bot Methods

| Category | Methods |
|----------|---------|
| **Info** | `user()`, `npcList()`, `charList()`, `inventory()`, `skillList()`, `dropList()` |
| **Movement** | `moveTo()`, `moveToTarget()`, `stand()`, `unstuck()` |
| **Combat** | `attack()`, `setTarget()`, `cancelTarget()`, `autoTarget()`, `assist()` |
| **Skills** | `useSkill()`, `useSkillGround()`, `stopCasting()`, `dispel()` |
| **Items** | `useItem()`, `pickUp()`, `destroyItem()`, `dropItem()` |
| **Social** | `say()`, `inviteParty()`, `leaveParty()`, `joinParty()` |
| **Dialog** | `dlgOpen()`, `dlgSel()`, `bypassToServer()`, `confirmDialog()` |

### Events (Flow)

```kotlin
bot.actionEvents      // Combat/movement actions
bot.packetEvents      // Server → Client packets
bot.cliPacketEvents   // Client → Server packets
bot.connectionStatus  // Connection state changes
bot.errors            // RPC errors
```

## Communication Protocol

**Named Pipes:**
```
l2bot_commands_{char}   → RPC requests
l2bot_responses_{char}  → RPC responses
l2bot_actions_{char}    → Action events
l2bot_packets_{char}    → Server packets
l2bot_clipackets_{char} → Client packets
```

**RPC Format:**
```json
// Request
{"id": 1, "method": "Engine.GetMe", "params": {}}

// Response
{"id": 1, "status": "success", "result": {...}}
```

## Requirements

- Kotlin 2.3.0+
- JDK 17+
- Windows (Named Pipes)
- L2 Adrenaline with active plugin

## Build & Run

```bash
./gradlew build
./gradlew :sample:run
```

## License

MIT
