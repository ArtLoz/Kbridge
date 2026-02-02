import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.l2bot.bridge.api.L2Adrenaline
import com.l2bot.bridge.api.L2Bot
import com.l2bot.bridge.models.entities.L2User
import com.l2bot.bridge.transport.jvm.JvmTransportProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.l2bot.bridge.models.events.ActionEvent
import kotlinx.coroutines.flow.Flow

fun main() = runBlocking {
    // Инициализируем JVM транспорт
    L2Adrenaline.setTransportProvider(JvmTransportProvider())
    
    // Получаем список доступных ботов
    println("Сканирование доступных ботов...")
    val bots = L2Adrenaline.getAvailableBots()
    
    if (bots.isEmpty()) {
        println("Боты не найдены!")
        return@runBlocking
    }
    
    println("Найдено ботов: ${bots.size}")
    bots.forEach { bot ->
        println("  - ${bot.charName}")
    }
    
    // Подключаемся к первому боту
    val bot = bots.first()
    println("\nПодключение к боту: ${bot.charName}")
    bot.connect()
    
    // Подписываемся на события

    
    bot.packetEvents.onEach { packet ->
      // println("Packet: ${packet.header} | ${packet.data}")
    }.launchIn(this)

    bot.cliPacketEvents.onEach { packet ->
     //   println("CliPacket: ${packet.header} | ${packet.data}")
    }.launchIn(this)

    bot.connectionStatus.onEach { status ->
     //   println("Status: $status")
    }.launchIn(this)

    val list = bot.user()
    println("LIST" + list)
//        testFun(bot,0)
    application {
        // Инициализируем транспорт один раз
        remember { L2Adrenaline.setTransportProvider(JvmTransportProvider()) }

        Window(onCloseRequest = ::exitApplication, title = "L2Bot Bridge: Auto-Connect Mode") {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainDashboard(bot, bot.actionEvents)
                }
            }
        }
    }
}

suspend fun testFun(bot: L2Bot, index:Int){
    val user = bot.user()
    println("index: $index")
    println("Имя: ${user.name}")
    println("Позиция: (${user.x}, ${user.y}, ${user.z})")
    println("Сидит: ${user.isSitting}")
    println("Dist To ${user.distTo(-116879, 46591,360)}")
    println("inRange ${user.inRange(x=-116879, y=46591, z=360, 200)}")
}
suspend fun testEho(bot: L2Bot, index:Int){
    val echoResult = bot.echo("Test")
}


@Composable
fun MainDashboard(firstBot: L2Bot,
                  action: Flow<ActionEvent>
) {
    val scope = rememberCoroutineScope()

    // Состояния только для отображения данных
    var connectedBotName by remember { mutableStateOf<String?>(null) }
    var userData by remember { mutableStateOf<L2User?>(null) }
    val logs = remember { mutableStateListOf<String>() }
    var status by remember { mutableStateOf("Инициализация...") }


    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm:ss") }

    // Вспомогательная функция логов
    fun addLog(msg: String) {
        val time = LocalTime.now().format(timeFormatter)
        logs.add(0, "[$time] $msg")
        if (logs.size > 100) logs.removeAt(logs.size - 1)
    }

    // АВТО-КОННЕКТ ПРИ СТАРТЕ
    LaunchedEffect(Unit) {
        status = "Поиск бота..."
        try {
            // Ищем ботов в фоновом потоке


                status = "Подключение к ${firstBot?.charName}..."

                connectedBotName = firstBot?.charName
                status = "Работает"
                addLog("Автоматически подключено к: ${firstBot?.charName}")
                val user =  firstBot?.user()
                addLog(user?.name.toString())
                addLog(firstBot.toString())

                // Сразу подписываемся на экшены (бесконечный сбор данных)
                launch(Dispatchers.IO) {
                    action.collectLatest { event ->
                        withContext(Dispatchers.Main) {
                            addLog("Action: ${event.action} -> ${event}")
                        }
                    }

            }
        } catch (e: Exception) {
            status = "Ошибка"
            addLog("Критическая ошибка: ${e.message}")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        // Верхняя панель статуса
        StatusHeader(connectedBotName, status)

        Spacer(modifier = Modifier.height(12.dp))

        // Панель данных персонажа
        CharacterStatsPanel(
            user = userData,
            onMove = {
                scope.launch {
                    println("${firstBot.moveGpsPoint("NPC_Km_Tp")}")

                }
            },
            npcList = {
                scope.launch {
                    println("${firstBot.loadGpsMap("C:\\softt\\Scripts\\EO_path\\PathEOGPS.db3")}")
                }
            },
            onRefresh = {
                scope.launch {
                    println("${firstBot.getGpsPoint("NPC_Nika")}")
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Консоль
        Text("События в реальном времени:", style = MaterialTheme.typography.caption)
        EventConsole(logs)
    }
}

// --- РАЗДЕЛЕННАЯ ВЕРСТКА ---

@Composable
fun StatusHeader(botName: String?, status: String) {
    Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Статус системы: $status", style = MaterialTheme.typography.subtitle2)
                Text(
                    text = if (botName != null) "Активен: $botName" else "Нет активного подключения",
                    color = if (botName != null) MaterialTheme.colors.primary else Color.Gray,
                    style = MaterialTheme.typography.body2
                )
            }
            if (botName == null) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
            }
        }
    }
}

@Composable
fun CharacterStatsPanel(user: L2User?, onRefresh: () -> Unit, onMove: () -> Unit, npcList:() -> Unit) {
    Card(elevation = 2.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Данные персонажа", style = MaterialTheme.typography.h6)
                Button(onClick = onRefresh) {
                    Text("Обновить статы")
                }
            }
            Row {
                Button(onClick = onMove) {
                    Text("двигаться")
                }
                Button(onClick = npcList) {
                    Text("NpcList")
                }
            }



            Spacer(modifier = Modifier.height(8.dp))

            if (user != null) {
                Column {
                    Text("Ник: ${user.name}", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text("HP: ${user.curHp} / ${user.maxHp}", color = Color(0xFFD32F2F))
                    Text("MP: ${user.curMp} / ${user.maxMp}", color = Color(0xFF1976D2))
                    Text("Координаты: ${user.x.toInt()}, ${user.y.toInt()}, ${user.z.toInt()}")
                    Text("Target ${user.target?.name}")
                }
            } else {
                Text("Нажмите кнопку выше для загрузки статов", color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun EventConsole(logs: List<String>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(8.dp)
    ) {
        LazyColumn {
            items(logs) { log ->
                Text(
                    text = log,
                    color = if (log.contains("Ошибка")) Color(0xFFCF6679) else Color(0xFF03DAC6),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp
                )
            }
        }
    }
}