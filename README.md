# L2Bot Kotlin Bridge

Kotlin Multiplatform библиотека для взаимодействия с L2 Adrenaline ботом через Named Pipes.

## Архитектура

```
┌─────────────────────┐
│  bridge-protocol    │  ← Transport интерфейс (String-based)
└──────────┬──────────┘
           │
     ┌─────┴────────────────┐
     │                      │
┌────▼──────────┐   ┌───────▼────────────┐
│ bridge-api-   │   │ bridge-transport-  │
│   models      │   │      jvm           │
└────┬──────────┘   └───────┬────────────┘
     │                      │
     │              ┌───────┘
     │              │
┌────▼──────────────▼─┐
│  bridge-api-core    │  ← L2Bot, RpcClient
└──────────┬──────────┘
           │
      ┌────▼────┐
      │ sample  │  ← Пример использования
      └─────────┘
```

### Модули

- **bridge-protocol** - Абстрактный транспортный слой (работает со строками)
- **bridge-api-models** - Модели данных (L2User, события, RPC)
- **bridge-api-core** - Бизнес-логика (L2Bot, RpcClient, L2Adrenaline)
- **bridge-transport-jvm** - JVM реализация через Named Pipes
- **sample** - Пример использования

## Быстрый старт

### 1. Добавить зависимости

```kotlin
dependencies {
    implementation(project(":bridge-api-core"))
    implementation(project(":bridge-transport-jvm"))
}
```

### 2. Пример использования

```kotlin
import com.l2bot.bridge.api.L2Adrenaline
import com.l2bot.bridge.transport.jvm.JvmTransportProvider
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // Инициализируем JVM транспорт
    L2Adrenaline.setTransportProvider(JvmTransportProvider())
    
    // Получаем список доступных ботов
    val bots = L2Adrenaline.getAvailableBots()
    
    // Подключаемся к первому боту
    val bot = bots.first()
    bot.connect()
    
    // Подписываемся на события
    bot.actionEvents.collect { event ->
        println("Action: ${event.action} | p1=${event.p1} | p2=${event.p2}")
    }
    
    bot.packetEvents.collect { packet ->
        println("Packet: ${packet.header} | ${packet.data}")
    }
    
    // Вызываем RPC методы
    val user = bot.user()
    println("Имя: ${user.name}")
    println("HP: ${user.hp}/${user.maxHp}")
    
    val echoResult = bot.echo("Hello from Kotlin!")
    println("Echo: $echoResult")
    
    bot.disconnect()
}
```

## API

### L2Adrenaline

Главная точка входа для работы с ботами.

```kotlin
// Установить провайдер транспорта
L2Adrenaline.setTransportProvider(JvmTransportProvider())

// Получить список доступных ботов
val bots: List<L2Bot> = L2Adrenaline.getAvailableBots()

// Подключиться к конкретному боту
val bot: L2Bot = L2Adrenaline.connectToBot("CharName")
```

### L2Bot

Представляет подключение к одному боту.

```kotlin
// Подключение/отключение
suspend fun connect()
suspend fun disconnect()

// RPC методы
suspend fun user(): L2User
suspend fun echo(text: String): String

// События
val actionEvents: Flow<ActionEvent>
val packetEvents: Flow<PacketEvent>
val cliPacketEvents: Flow<CliPacketEvent>
val connectionStatus: Flow<ConnectionStatus>
```

### События

#### ActionEvent
```kotlin
data class ActionEvent(
    val actionId: Int,
    val p1: Int,
    val p2: Int
)

val action: ActionType  // Enum с типом действия (DIE, ATTACK, etc.)
```

#### PacketEvent / CliPacketEvent
```kotlin
data class PacketEvent(
    val header: String,  // Hex ID пакета
    val data: String     // Hex данные
)
```

#### ConnectionStatus
```kotlin
enum class ConnectionStatus {
    CONNECTED,
    DISCONNECTED
}
```

### Модели

#### L2User
```kotlin
data class L2User(
    val name: String,
    val id: Int,
    val oid: Int,
    val x: Int,
    val y: Int,
    val z: Int,
    val hp: Int,
    val maxHp: Int,
    val mp: Int,
    val maxMp: Int,
    val isDead: Boolean,
    val targetOid: Int,
    val isSitting: Boolean,
    // ...
)
```

## Named Pipes

Библиотека взаимодействует через 5 Named Pipes:

```
l2bot_commands_{charName}    → Отправка RPC команд
l2bot_responses_{charName}   → Получение RPC ответов
l2bot_actions_{charName}     → События действий
l2bot_packets_{charName}     → Пакеты сервер→клиент
l2bot_clipackets_{charName}  → Пакеты клиент→сервер
```

## RPC Protocol

### Запрос
```json
{
  "id": 1,
  "method": "Engine.GetMe",
  "params": {}
}
```

### Ответ
```json
{
  "id": 1,
  "status": "success",
  "result": {
    "name": "CharName",
    "hp": 100,
    "maxHp": 4819
  }
}
```

### Ошибка
```json
{
  "id": 1,
  "status": "error",
  "error": {
    "code": 404,
    "message": "Method not found"
  }
}
```

## Обработка ошибок

```kotlin
try {
    val user = bot.user()
} catch (e: RpcException) {
    println("RPC error: ${e.message}")
} catch (e: TransportException) {
    println("Transport error: ${e.message}")
}

// Автоматическое отключение при ошибках
bot.connectionStatus.collect { status ->
    when (status) {
        ConnectionStatus.CONNECTED -> println("Connected")
        ConnectionStatus.DISCONNECTED -> println("Disconnected")
    }
}
```

## Особенности

- ✅ **Multiplatform** - готово к расширению на Native/JS
- ✅ **Coroutines** - асинхронная работа из коробки
- ✅ **Flow** - реактивные события
- ✅ **Thread-safe** - Mutex для RPC команд
- ✅ **Type-safe** - строгая типизация всех данных
- ✅ **Error handling** - автоматическое обнаружение отключений
- ✅ **Clean architecture** - разделение слоёв (protocol → models → api → transport)

## Требования

- Kotlin 2.1.0+
- JDK 11+
- Windows (для Named Pipes)
- L2 Adrenaline с активным плагином

## Сборка

```bash
./gradlew build
```

## Запуск примера

```bash
./gradlew :sample:run
```

## Лицензия

MIT
