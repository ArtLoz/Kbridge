import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.l2bot.bridge.api.L2Adrenaline
import com.l2bot.bridge.api.L2Bot
import com.l2bot.bridge.models.entities.L2User
import com.l2bot.bridge.transport.jvm.JvmTransportProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun main() = application {
    remember { L2Adrenaline.setTransportProvider(JvmTransportProvider()) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "L2Bot Bridge Sample"
    ) {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val scope = rememberCoroutineScope()

    var bot by remember { mutableStateOf<L2Bot?>(null) }
    var user by remember { mutableStateOf<L2User?>(null) }
    var status by remember { mutableStateOf("Waiting...") }
    val logs = remember { mutableStateListOf<String>() }

    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm:ss") }

    fun log(message: String) {
        val time = LocalTime.now().format(timeFormatter)
        logs.add(0, "[$time] $message")
        if (logs.size > 100) logs.removeLast()
    }

    // Auto-connect on launch
    LaunchedEffect(Unit) {
        status = "Searching bots..."
        log("Scanning...")

        try {
            val bots = L2Adrenaline.getAvailableBots()

            if (bots.isEmpty()) {
                status = "No bots found"
                log("No bots found")
                return@LaunchedEffect
            }

            val firstBot = bots.first()
            status = "Connecting to ${firstBot.charName}..."
            log("Found bot: ${firstBot.charName}")

            firstBot.connect()
            bot = firstBot
            status = "Connected: ${firstBot.charName}"
            log("Connected successfully")

            // Subscribe to all events
            firstBot.actionEvents
                .onEach { event ->
                    withContext(Dispatchers.Main) {
                        log("[ACTION] id=${event} p1=${event.p1} p2=${event.p2}")
                    }
                }
                .launchIn(this)

            firstBot.packetEvents
                .onEach { event ->
                    withContext(Dispatchers.Main) {
                        log("[PACKET] ${event.header}")
                    }
                }
                .launchIn(this)

            firstBot.cliPacketEvents
                .onEach { event ->
                    withContext(Dispatchers.Main) {
                        log("[CLI_PKT] ${event.header}")
                    }
                }
                .launchIn(this)

            firstBot.connectionStatus
                .onEach { connStatus ->
                    withContext(Dispatchers.Main) {
                        log("[STATUS] $connStatus")
                        status = if (connStatus.name == "CONNECTED") {
                            "Connected: ${firstBot.charName}"
                        } else {
                            "Disconnected"
                        }
                    }
                }
                .launchIn(this)

            firstBot.errors
                .onEach { error ->
                    withContext(Dispatchers.Main) {
                        log("[ERROR] ${error.message}")
                    }
                }
                .launchIn(this)

        } catch (e: Exception) {
            status = "Error: ${e.message}"
            log("Error: ${e.message}")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Status card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Status", style = MaterialTheme.typography.caption)
                    Text(status, style = MaterialTheme.typography.subtitle1)
                }
                if (bot == null && status.contains("Searching")) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Character data card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Character", style = MaterialTheme.typography.h6)
                    Button(
                        onClick = {
                            scope.launch {
                                bot?.let {
                                    user = it.user()
                                    log("User data refreshed")
                                }
                            }
                        },
                        enabled = bot != null
                    ) {
                        Text("Refresh")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (user != null) {
                    UserInfo(user!!)
                } else {
                    Text(
                        "Press 'Refresh' to load character data",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Event console
        Text("Event Log:", style = MaterialTheme.typography.caption)
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1E1E))
                .padding(8.dp)
        ) {
            LazyColumn {
                items(logs) { logItem ->
                    val color = when {
                        logItem.contains("[ERROR]") -> Color(0xFFFF6B6B)
                        logItem.contains("[ACTION]") -> Color(0xFF4ECDC4)
                        logItem.contains("[PACKET]") -> Color(0xFFFFE66D)
                        logItem.contains("[CLI_PKT]") -> Color(0xFFA8E6CF)
                        logItem.contains("[STATUS]") -> Color(0xFFDDA0DD)
                        else -> Color(0xFFB0B0B0)
                    }
                    Text(
                        text = logItem,
                        color = color,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun UserInfo(user: L2User) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(user.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("HP: ${user.curHp}/${user.maxHp}", color = Color(0xFFE57373))
            Text("MP: ${user.curMp}/${user.maxMp}", color = Color(0xFF64B5F6))
        }

        Text("Position: ${user.x.toInt()}, ${user.y.toInt()}, ${user.z.toInt()}", fontSize = 13.sp)

        user.target?.let { target ->
            Text("Target: ${target.name}", fontSize = 13.sp, color = Color(0xFFFFB74D))
        }
    }
}
