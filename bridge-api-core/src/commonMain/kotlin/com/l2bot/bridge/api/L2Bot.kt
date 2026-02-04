package com.l2bot.bridge.api

import com.l2bot.bridge.models.L2GPSPoint
import com.l2bot.bridge.models.L2Object
import com.l2bot.bridge.models.dialogs.L2ConfirmDlg
import com.l2bot.bridge.models.entities.L2Char
import com.l2bot.bridge.models.entities.L2Live
import com.l2bot.bridge.models.entities.L2Npc
import com.l2bot.bridge.models.entities.L2Pet
import com.l2bot.bridge.models.entities.L2Spawn
import com.l2bot.bridge.protocol.Transport
import com.l2bot.bridge.models.entities.L2User
import com.l2bot.bridge.models.events.ActionEvent
import com.l2bot.bridge.models.events.CliPacketEvent
import com.l2bot.bridge.models.events.ConnectionStatus
import com.l2bot.bridge.models.events.PacketEvent
import com.l2bot.bridge.models.item.L2Drop
import com.l2bot.bridge.models.item.L2Item
import com.l2bot.bridge.models.rpc.L2RpcException
import com.l2bot.bridge.models.skill.L2Skill
import com.l2bot.bridge.models.types.ChatType
import com.l2bot.bridge.models.types.L2Action
import com.l2bot.bridge.models.types.L2Status
import com.l2bot.bridge.models.types.LootType
import com.l2bot.bridge.models.types.RestartType
import com.l2bot.bridge.models.types.WaitResult
import com.l2bot.bridge.models.types.ZoneType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.*

class L2Bot internal constructor(
    private val transport: Transport,
    val charName: String
) : L2Control() {

    private val rpcClient = RpcClient(transport).apply {
        onGlobalError = { ex ->
            println("LOG [${charName}]: Критическая ошибка RPC -> ${ex.message}")
        }
    }
    private val _errors = MutableSharedFlow<L2RpcException>()
    val errors = _errors.asSharedFlow()

    override val actionEvents: Flow<ActionEvent> =
        transport.receiveActions()
            .mapNotNull { line ->
                try {
                    val parts = line.split("|")
                    if (parts.size == 3) {
                        ActionEvent(
                            actionId = parts[0].toInt(),
                            p1 = parts[1].toInt(),
                            p2 = parts[2].toInt()
                        )
                    } else null
                } catch (e: Exception) {
                    null
                }
            }

    override val packetEvents: Flow<PacketEvent> =
        transport.receivePackets()
            .mapNotNull { line ->
                try {
                    val parts = line.split("|", limit = 2)
                    if (parts.size == 2) {
                        PacketEvent(
                            header = parts[0],
                            data = parts[1]
                        )
                    } else null
                } catch (e: Exception) {
                    null
                }
            }

    override val cliPacketEvents: Flow<CliPacketEvent> =
        transport.receiveCliPackets()
            .mapNotNull { line ->
                try {
                    val parts = line.split("|", limit = 2)
                    if (parts.size == 2) {
                        CliPacketEvent(
                            header = parts[0],
                            data = parts[1]
                        )
                    } else null
                } catch (e: Exception) {
                    null
                }
            }

    override val connectionStatus: Flow<ConnectionStatus> =
        transport.isConnected.map { connected ->
            if (connected) ConnectionStatus.CONNECTED
            else ConnectionStatus.DISCONNECTED
        }

    suspend fun connect() {
        transport.connect(charName)
    }

    suspend fun disconnect() {
        transport.disconnect()
    }

    override suspend fun user(): L2User {
        return rpcClient.call("Engine.GetMe")
    }

    override suspend fun echo(text: String): String {
        val params = buildJsonObject {
            put("text", text)
        }
        val result: JsonObject = rpcClient.call("System1.Echo", params)
        return result["text"]?.jsonPrimitive?.content ?: ""
    }

    override suspend fun npcList(): List<L2Npc> {
        return rpcClient.call("Engine.GetNpcList")
    }

    override suspend fun moveTo(x: Int, y: Int, z: Int, timeout: Int) : Boolean {
        return rpcClient.call(
            method = "Engine.MoveTo",
            params = buildJsonObject {
                put("x", x)
                put("y", y)
                put("z", z)
                put("timeout", timeout)
            }
        )
    }

    override suspend fun moveTo(obj: L2Spawn, delta: Int ): Boolean {
        return rpcClient.call(
            method = "Engine.MoveToByOid",
            params = buildJsonObject {
                put("oid", obj.oid)
                put("delta", delta)
            }
        )
    }
    override suspend fun moveToTarget(delta: Int): Boolean {
        return rpcClient.call(
            method = "Engine.MoveToTarget",
            params = buildJsonObject {
                put("delta", delta)
            }
        )
    }

    override suspend fun unstuck(): Boolean {
        val started = rpcClient.call<Boolean>(
            method = "Engine.Unstuck",
            params = buildJsonObject { }
        )
        return started
    }
    override suspend fun goHome(type: RestartType): Boolean {
        return rpcClient.call(
            method = "Engine.GoHome",
            params = buildJsonObject {
                put("res_type", type.value)
            }
        )
    }
    override suspend fun teleport(id: Long): Boolean {
        return rpcClient.call(
            method = "Engine.Teleport",
            params = buildJsonObject {
                put("id", id)
            }
        )
    }
    override suspend fun useAction(id: Long, force: Boolean, shift: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.UseAction",
            params = buildJsonObject {
                put("id", id)
                put("force", force)
                put("shift", shift)
            }
        )
    }

    override suspend fun attack(pauseTime: Long, force: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.Attack",
            params = buildJsonObject {
                put("pause_time", pauseTime)
                put("force", force)
            }
        )
    }
    override suspend fun pickUp(item: L2Drop, byPet: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.PickUp",
            params = buildJsonObject {
                put("oid", item.oid)
                put("by_pet", byPet)
            }
        )
    }
    override suspend fun stand(): Boolean {
        return rpcClient.call(
            method = "Engine.Stand",
            params = buildJsonObject { }
        )
    }
    override suspend fun setTarget(name: String): Boolean {
        return rpcClient.call(
            method = "Engine.SetTarget",
            params = buildJsonObject { put("name", name) }
        )
    }

    override suspend fun setTargetId(id: Long): Boolean {
        return rpcClient.call(
            method = "Engine.SetTargetID",
            params = buildJsonObject { put("id", id) }
        )
    }

    override suspend fun setTarget(obj: L2Live): Boolean {
        return rpcClient.call(
            method = "Engine.SetTargetByOid",
            params = buildJsonObject { put("oid", obj.oid) }
        )
    }
    override suspend fun action(oid: Long, force: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.Action",
            params = buildJsonObject {
                put("oid", oid)
                put("force", force)
            }
        )
    }
    override suspend fun cancelTarget(): Boolean {
        return rpcClient.call(
            method = "Engine.CancelTarget",
            params = buildJsonObject { }
        )
    }
    override suspend fun assist(name: String): Boolean {
        return rpcClient.call(
            method = "Engine.Assist",
            params = buildJsonObject {
                put("name", name)
            }
        )
    }
    override suspend fun findEnemy(obj: L2Live, range: Long, zLimit: Long): L2Live? {
        return rpcClient.call<L2Live?>(
            method = "Engine.FindEnemy",
            params = buildJsonObject {
                put("oid", obj.oid)
                put("range", range)
                put("z_limit", zLimit)
            }
        )
    }
    override suspend fun autoTarget(range: Long, zLimit: Long, notBusy: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.AutoTarget",
            params = buildJsonObject {
                put("range", range)
                put("z_limit", zLimit)
                put("not_busy", notBusy)
            }
        )
    }
    override suspend fun ignore(obj: L2Spawn) {
        rpcClient.call<Unit>(
            method = "Engine.Ignore",
            params = buildJsonObject {
                put("oid", obj.oid)
            }
        )
    }
    override suspend fun clearIgnore() {
        rpcClient.call<Unit>(
            method = "Engine.ClearIgnore",
            params = buildJsonObject { }
        )
    }
    override suspend fun isBusy(obj: L2Npc): Boolean {
        return rpcClient.call(
            method = "Engine.IsBusy",
            params = buildJsonObject {
                put("oid", obj.oid)
            }
        )
    }
    override suspend fun useSkill(id: Long, force: Boolean, shift: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.UseSkill",
            params = buildJsonObject {
                put("id", id)
                put("force", force)
                put("shift", shift)
            }
        )
    }
    override suspend fun dUseSkill(id: Long, force: Boolean, shift: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.DUseSkill",
            params = buildJsonObject {
                put("id", id)
                put("force", force)
                put("shift", shift)
            }
        )
    }
    override suspend fun useSkillGround(
        id: Long,
        x: Int,
        y: Int,
        z: Int,
        force: Boolean,
        shift: Boolean
    ): Boolean {
        return rpcClient.call(
            method = "Engine.UseSkillGround",
            params = buildJsonObject {
                put("id", id)
                put("x", x)
                put("y", y)
                put("z", z)
                put("force", force)
                put("shift", shift)
            }
        )
    }

    override suspend fun stopCasting(): Boolean {
        return rpcClient.call(
            method = "Engine.StopCasting",
            params = buildJsonObject { }
        )
    }
    override suspend fun dispel(id: Long): Boolean {
        return rpcClient.call(
            method = "Engine.Dispel",
            params = buildJsonObject {
                put("id", id)
            }
        )
    }

    override suspend fun learnSkill(id: Long): Boolean {
        return rpcClient.call(
            method = "Engine.LearnSkill",
            params = buildJsonObject {
                put("id", id)
            }
        )
    }
    override suspend fun updateSkillList(): Boolean {
        return rpcClient.call(
            method = "Engine.UpdateSkillList",
            params = buildJsonObject { }
        )
    }
    override suspend fun useItem(obj: L2Item, byPet: Boolean, force: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.UseItemByOid",
            params = buildJsonObject {
                put("oid", obj.oid)
                put("by_pet", byPet)
                put("force", force)
            }
        )
    }
    override suspend fun useItem(name: String, byPet: Boolean, force: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.UseItemByName",
            params = buildJsonObject {
                put("name", name)
                put("by_pet", byPet)
                put("force", force)
            }
        )
    }

    override suspend fun useItem(id: Long, byPet: Boolean, force: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.UseItemByID",
            params = buildJsonObject {
                put("id", id)
                put("by_pet", byPet)
                put("force", force)
            }
        )
    }
    override suspend fun useItemOid(oid: Long, byPet: Boolean, force: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.UseItemOID",
            params = buildJsonObject {
                put("oid", oid)
                put("by_pet", byPet)
                put("force", force)
            }
        )
    }
    override suspend fun destroyItem(name: String, count: Long): Boolean {
        return rpcClient.call(
            method = "Engine.DestroyItemByName",
            params = buildJsonObject {
                put("name", name)
                put("count", count)
            }
        )
    }

    override suspend fun destroyItem(id: Int, count: Long): Boolean {
        return rpcClient.call(
            method = "Engine.DestroyItemByID",
            params = buildJsonObject {
                put("id", id)
                put("count", count)
            }
        )
    }

    override suspend fun destroyItem(item: L2Item, count: Long): Boolean {
        return rpcClient.call(
            method = "Engine.DestroyItemByOid",
            params = buildJsonObject {
                put("oid", item.oid)
                put("count", count)
            }
        )
    }
    override suspend fun dropItem(id: Long, count: Long, x: Int, y: Int, z: Int): Boolean {
        return rpcClient.call(
            method = "Engine.DropItem",
            params = buildJsonObject {
                put("id", id)
                put("count", count)
                put("x", x)
                put("y", y)
                put("z", z)
            }
        )
    }
    override suspend fun makeItem(index: Long): Boolean {
        return rpcClient.call(
            method = "Engine.MakeItem",
            params = buildJsonObject {
                put("index", index)
            }
        )
    }
    override suspend fun crystalItem(id: Long): Boolean {
        return rpcClient.call(
            method = "Engine.CrystalItemByID",
            params = buildJsonObject {
                put("id", id)
            }
        )
    }

    override suspend fun crystalItem(item: L2Item): Boolean {
        return rpcClient.call(
            method = "Engine.CrystalItemByOid",
            params = buildJsonObject {
                put("oid", item.oid)
            }
        )
    }
    override suspend fun moveItem(name: String, count: Long, toPet: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.MoveItem",
            params = buildJsonObject {
                put("name", name)
                put("count", count)
                put("to_pet", toPet)
            }
        )
    }
    override suspend fun loadItems(toWh: Boolean, ids: List<Long>): Boolean {
        return rpcClient.call(
            method = "Engine.LoadItems",
            params = buildJsonObject {
                put("to_wh", toWh)
                put("list", buildJsonArray {
                    ids.forEach { add(it) }
                })
            }
        )
    }
    override suspend fun autoSoulShot(name: String, active: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.AutoSoulShot",
            params = buildJsonObject {
                put("name", name)
                put("active", active)
            }
        )
    }
    override suspend fun dAutoSoulShot(id: Long, active: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.DAutoSoulShot",
            params = buildJsonObject {
                put("id", id)
                put("active", active)
            }
        )
    }

    override suspend fun equipped(name: String): Int {
        return rpcClient.call<Int>(
            method = "Engine.Equipped",
            params = buildJsonObject {
                put("name", name)
            }
        )
    }
    override suspend fun dismissPet(): Boolean {
        return rpcClient.call(
            method = "Engine.DismissPet",
            params = buildJsonObject { }
        )
    }
    override suspend fun dismissSum(): Boolean {
        return rpcClient.call(
            method = "Engine.DismissSum",
            params = buildJsonObject { }
        )
    }
    override suspend fun say(text: String, chatType: ChatType, playerName: String): Boolean {
        return rpcClient.call(
            method = "Engine.Say",
            params = buildJsonObject {
                put("text", text)
                put("chat_type", chatType.id)
                put("player_name", playerName)
            }
        )
    }
    override suspend fun inviteParty(name: String, lootMode: LootType): Boolean {
        return rpcClient.call(
            method = "Engine.InviteParty",
            params = buildJsonObject {
                put("name", name)
                put("loot_mode", lootMode.id)
            }
        )
    }
    override suspend fun dismissParty(name: String): Boolean {
        return rpcClient.call(
            method = "Engine.DismissParty",
            params = buildJsonObject {
                put("name", name)
            }
        )
    }
    override suspend fun joinParty(accept: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.JoinParty",
            params = buildJsonObject {
                put("accept", accept)
            }
        )
    }
    override suspend fun leaveParty(): Boolean {
        return rpcClient.call(
            method = "Engine.LeaveParty",
            params = buildJsonObject { }
        )
    }
    override suspend fun setPartyLeader(name: String): Boolean {
        return rpcClient.call(
            method = "Engine.SetPartyLeader",
            params = buildJsonObject {
                put("name", name)
            }
        )
    }
    override suspend fun getMentor(): String {
        return rpcClient.call<String>(
            method = "Engine.GetMentor",
            params = buildJsonObject { }
        )
    }
    override suspend fun kickMentor(): Boolean {
        return rpcClient.call(
            method = "Engine.KickMentor",
            params = buildJsonObject { }
        )
    }
    override suspend fun closeRoom(): Boolean {
        return rpcClient.call(
            method = "Engine.CloseRoom",
            params = buildJsonObject { }
        )
    }
    override suspend fun createRoom(caption: String, minLevel: Int, maxLevel: Int): Boolean {
        return rpcClient.call(
            method = "Engine.CreateRoom",
            params = buildJsonObject {
                put("caption", caption)
                put("min_level", minLevel)
                put("max_level", maxLevel)
            }
        )
    }
    override suspend fun autoAcceptClan(players: List<String>): Boolean {
        val playersString = players.joinToString(";")
        return rpcClient.call(
            method = "Engine.AutoAcceptClan",
            params = buildJsonObject {
                put("players_list", playersString)
            }
        )
    }
    override suspend fun autoAcceptCC(players: List<String>): Boolean {
        val playersString = players.joinToString(";")

        return rpcClient.call(
            method = "Engine.AutoAcceptCC",
            params = buildJsonObject {
                put("players_list", playersString)
            }
        )
    }
    override suspend fun autoAcceptMentors(players: List<String>): Boolean {
        val playersString = players.joinToString(";")

        return rpcClient.call(
            method = "Engine.AutoAcceptMentors",
            params = buildJsonObject {
                put("players_list", playersString)
            }
        )
    }
    override suspend fun questStatus(id: Long): Long {
        return rpcClient.call<Long>(
            method = "Engine.QuestStatusGetStage",
            params = buildJsonObject {
                put("id", id)
            }
        )
    }

    override suspend fun questStatus(id: Long, stage: Long): Boolean {
        return rpcClient.call(
            method = "Engine.QuestStatusCheckStage",
            params = buildJsonObject {
                put("id", id)
                put("stage", stage)
            }
        )
    }
    override suspend fun cancelQuest(id: Int): Boolean {
        return rpcClient.call(
            method = "Engine.CancelQuest",
            params = buildJsonObject {
                put("id", id)
            }
        )
    }
    override suspend fun openQuestion(): Boolean {
        return rpcClient.call(
            method = "Engine.OpenQuestion",
            params = buildJsonObject { }
        )
    }
    override suspend fun getDailyItems(): Boolean {
        return rpcClient.call(
            method = "Engine.GetDailyItems",
            params = buildJsonObject { }
        )
    }
    override suspend fun getDailyItem(id: Long): Boolean {
        return rpcClient.call(
            method = "Engine.GetDailyItem",
            params = buildJsonObject {
                put("id", id)
            }
        )
    }
    override suspend fun updateDailyList(): Boolean {
        return rpcClient.call(
            method = "Engine.UpdateDailyList",
            params = buildJsonObject { }
        )
    }
    override suspend fun dlgOpen(timeout: Long): Boolean {
        return rpcClient.call(
            method = "Engine.DlgOpen",
            params = buildJsonObject {
                put("timeout", timeout)
            }
        )
    }
    override suspend fun dlgSel(caption: String, timeout: Int): Boolean {
        return rpcClient.call(
            method = "Engine.DlgSelText",
            params = buildJsonObject {
                put("caption", caption)
                put("timeout", timeout)
            }
        )
    }
    override suspend fun dlgSel(index: Int): Boolean {
        return rpcClient.call(
            method = "Engine.DlgSel",
            params = buildJsonObject {
                put("index", index)
            }
        )
    }
    override suspend fun bypassToServer(text: String): Boolean {
        return rpcClient.call(
            method = "Engine.BypassToServer",
            params = buildJsonObject {
                put("text", text)
            }
        )
    }
    override suspend fun dlgText(): String {
        return rpcClient.call(
            method = "Engine.DlgText",
            params = buildJsonObject { }
        )
    }
    override suspend fun dlgTime(): Long {
        return rpcClient.call(
            method = "Engine.DlgTime",
            params = buildJsonObject { }
        )
    }
    override suspend fun cbText(): String {
        return rpcClient.call(
            method = "Engine.CBText",
            params = buildJsonObject { }
        )
    }
    override suspend fun cbTime(): Long {
        return rpcClient.call(
            method = "Engine.CBTime",
            params = buildJsonObject { }
        )
    }
    override suspend fun hlpText(): String {
        return rpcClient.call(
            method = "Engine.HlpText",
            params = buildJsonObject { }
        )
    }
    override suspend fun hlpTime(): Long {
        return rpcClient.call(
            method = "Engine.HlpTime",
            params = buildJsonObject { }
        )
    }
    override suspend fun getConfirmDlg(): L2ConfirmDlg? {
        return  rpcClient.call<L2ConfirmDlg?>(
            method = "Engine.ConfirmDlg",
            params = buildJsonObject { }
        )
    }

    override suspend fun confirmDialog(accept: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.ConfirmDialog",
            params = buildJsonObject {
                put("accept", accept)
            }
        )
    }
    override suspend fun openPrivateStore(items: List<Long>, storeType: Int, caption: String): Boolean {
        return rpcClient.call(
            method = "Engine.OpenPrivateStore",
            params = buildJsonObject {
                put("list", buildJsonArray { items.forEach { add(it) } })
                put("store_type", storeType)
                put("caption", caption)
            }
        )
    }
    override suspend fun npcTrade(sell: Boolean, items: List<Long>): Boolean {
        return rpcClient.call(
            method = "Engine.NpcTrade",
            params = buildJsonObject {
                put("sell", sell)
                put("list", buildJsonArray {
                    items.forEach { add(it) }
                })
            }
        )
    }
    override suspend fun npcExchange(idOrIndex: Long, count: Long, byIndex: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.NpcExchange",
            params = buildJsonObject {
                put("id_or_index", idOrIndex)
                put("count", count)
                put("by_index", byIndex)
            }
        )
    }
    override suspend fun castleTax(townId: Long): Int {
        return rpcClient.call<Int>(
            method = "Engine.CastleTax",
            params = buildJsonObject {
                put("town_id", townId)
            }
        )
    }
    override suspend fun sendMail(
        receiver: String,
        topic: String,
        text: String,
        items: List<Long>,
        price: Long
    ): Boolean {
        return rpcClient.call(
            method = "Engine.SendMail",
            params = buildJsonObject {
                put("receiver", receiver)
                put("topic", topic)
                put("text", text)
                put("items", buildJsonArray { items.forEach { add(it) } })
                put("price", price)
            }
        )
    }
    override suspend fun getMailItems(maxLoad: Long, maxCount: Long): Boolean {
        return rpcClient.call(
            method = "Engine.GetMailItems",
            params = buildJsonObject {
                put("max_load", maxLoad)
                put("max_count", maxCount)
            }
        )
    }
    override suspend fun getZoneType(): ZoneType {
        val index = rpcClient.call<Int>(
            method = "Engine.GetZoneType",
            params = buildJsonObject { }
        )
        return ZoneType.entries.toTypedArray().getOrElse(index) { ZoneType.UNKNOWN }
    }
    override suspend fun getZoneName(x: Int, y: Int, z: Int): String {
        return rpcClient.call(
            method = "Engine.GetZoneName",
            params = buildJsonObject {
                put("x", x)
                put("y", y)
                put("z", z)
            }
        )
    }
    override suspend fun getZoneID(x: Int, y: Int, z: Int): Long {
        return rpcClient.call(
            method = "Engine.GetZoneID",
            params = buildJsonObject {
                put("x", x)
                put("y", y)
                put("z", z)
            }
        )
    }
    override suspend fun inZone(x: Int, y: Int, z: Int): Boolean {
        return rpcClient.call(
            method = "Engine.InZoneXYZ",
            params = buildJsonObject {
                put("x", x)
                put("y", y)
                put("z", z)
            }
        )
    }

    override suspend fun inZone(obj: L2Spawn): Boolean {
        return rpcClient.call(
            method = "Engine.InZoneObj",
            params = buildJsonObject {
                put("oid", obj.oid)
            }
        )
    }
    override suspend fun gameTime(): Long {
        return rpcClient.call(
            method = "Engine.GameTime",
            params = buildJsonObject { }
        )
    }
    override suspend fun isDay(): Boolean {
        return rpcClient.call(
            method = "Engine.IsDay",
            params = buildJsonObject { }
        )
    }
    override suspend fun getStatus(): L2Status {
        val statusName = rpcClient.call<String>(
            method = "Engine.Status",
            params = buildJsonObject { }
        )

        return try {
            L2Status.valueOf(statusName)
        } catch (e: IllegalArgumentException) {
            L2Status.lsOff
        }
    }
    override suspend fun loginStatus(): Int {
        return rpcClient.call(
            method = "Engine.LoginStatus",
            params = buildJsonObject { }
        )
    }
    override suspend fun authLogin(login: String, password: String): Boolean {
        return rpcClient.call(
            method = "Engine.AuthLogin",
            params = buildJsonObject {
                put("login", login)
                put("password", password)
            }
        )
    }
    override suspend fun gameStart(charIndex: Int): Boolean {
        return rpcClient.call(
            method = "Engine.GameStart",
            params = buildJsonObject {
                put("char_index", charIndex)
            }
        )
    }
    override suspend fun restart(): Boolean {
        return rpcClient.call(
            method = "Engine.Restart",
            params = buildJsonObject { }
        )
    }
    override suspend fun dRestart(): Boolean {
        return rpcClient.call(
            method = "Engine.DRestart",
            params = buildJsonObject { }
        )
    }
    override suspend fun setFaceControl(id: Int, active: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.FaceControl",
            params = buildJsonObject {
                put("id", id)
                put("active", active)
            }
        )
    }
    override suspend fun getFaceState(id: Int): Boolean {
        return rpcClient.call(
            method = "Engine.GetFaceState",
            params = buildJsonObject {
                put("id", id)
            }
        )
    }
    override suspend fun updateCfg(wait: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.UpdateCfg",
            params = buildJsonObject {
                put("wait", wait)
            }
        )
    }
    override suspend fun loadConfig(filePath: String): Boolean {
        return rpcClient.call(
            method = "Engine.LoadConfig",
            params = buildJsonObject {
                put("file_path", filePath)
            }
        )
    }
    override suspend fun loadZone(filePath: String): Boolean {
        return rpcClient.call(
            method = "Engine.LoadZone",
            params = buildJsonObject {
                put("file_path", filePath)
            }
        )
    }
    override suspend fun clearZone(): Boolean {
        return rpcClient.call(
            method = "Engine.ClearZone",
            params = buildJsonObject { }
        )
    }
    override suspend fun setPerform(level: Long): Boolean {
        return rpcClient.call(
            method = "Engine.SetPerform",
            params = buildJsonObject {
                put("level", level)
            }
        )
    }
    override suspend fun setMapKeepDist(dist: Int): Boolean {
        return rpcClient.call(
            method = "Engine.SetMapKeepDist",
            params = buildJsonObject {
                put("dist", dist)
            }
        )
    }
    override suspend fun gamePrint(text: String, author: String, chatType: Int): Boolean {
        return rpcClient.call(
            method = "Engine.GamePrint",
            params = buildJsonObject {
                put("text", text)
                put("author", author)
                put("chat_type", chatType)
            }
        )
    }
    override suspend fun gameClose(): Boolean {
        return rpcClient.call(
            method = "Engine.GameClose",
            params = buildJsonObject { }
        )
    }
    override suspend fun blinkWindow(game: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.BlinkWindow",
            params = buildJsonObject {
                put("game", game)
            }
        )
    }
    override suspend fun setGameWindow(show: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.SetGameWindow",
            params = buildJsonObject {
                put("show", show)
            }
        )
    }
    override suspend fun useKey(keyName: String, downUp: Int): Boolean {
        return rpcClient.call("Engine.UseKey", buildJsonObject {
            put("key", keyName)
            put("down_up", downUp)
        })
    }

    override suspend fun useKey(keyCode: Int, downUp: Int): Boolean {
        return rpcClient.call("Engine.UseKey", buildJsonObject {
            put("key", keyCode)
            put("down_up", downUp)
        })
    }
    override suspend fun enterText(text: String): Boolean {
        return rpcClient.call(
            method = "Engine.EnterText",
            params = buildJsonObject {
                put("text", text)
            }
        )
    }
    override suspend fun postMessage(msg: Long, wParam: Int, lParam: Int): Int {
        return rpcClient.call("Engine.PostMessage", buildJsonObject {
            put("msg", msg)
            put("w_param", wParam)
            put("l_param", lParam)
        })
    }
    override suspend fun sendMessage(msg: Long, wParam: Int, lParam: Int): Int {
        return rpcClient.call("Engine.SendMessage", buildJsonObject {
            put("msg", msg)
            put("w_param", wParam)
            put("l_param", lParam)
        })
    }
    override suspend fun getGamePath(): String {
        return rpcClient.call(
            method = "Engine.GamePath",
            params = buildJsonObject { }
        )
    }
    override suspend fun getGameWindowHandle(): Long {
        return rpcClient.call(
            method = "Engine.GameWindow",
            params = buildJsonObject { }
        )
    }
    override suspend fun getGameHash(): Long {
        return rpcClient.call(
            method = "Engine.GameHash",
            params = buildJsonObject { }
        )
    }
    override suspend fun getGameProtocol(): Int {
        return rpcClient.call(
            method = "Engine.GameProtocol",
            params = buildJsonObject { }
        )
    }
    override suspend fun getGameVersion(): Int {
        return rpcClient.call(
            method = "Engine.GameVersion",
            params = buildJsonObject { }
        )
    }
    override suspend fun getServerIP(): String {
        return rpcClient.call(
            method = "Engine.GetServerIP",
            params = buildJsonObject { }
        )
    }
    override suspend fun getServerName(): String {
        return rpcClient.call(
            method = "Engine.GetServerName",
            params = buildJsonObject { }
        )
    }
    override suspend fun getServerID(): Int {
        return rpcClient.call(
            method = "Engine.GetServerID",
            params = buildJsonObject { }
        )
    }
    override suspend fun isClassicServer(): Boolean {
        return rpcClient.call(
            method = "Engine.IsClassicServer",
            params = buildJsonObject { }
        )
    }
    override suspend fun getServerTime(): Long {
        return rpcClient.call(
            method = "Engine.ServerTime",
            params = buildJsonObject { }
        )
    }
    override suspend fun msg(title: String, text: String, color: Int): Boolean {
        return rpcClient.call("Engine.Msg", buildJsonObject {
            put("title", title)
            put("text", text)
            put("color", color)
        })
    }
    override suspend fun blinkWindowBot(game: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.BlinkWindow2",
            params = buildJsonObject {
                put("game", game)
            }
        )
    }
    override suspend fun setScriptPause(enable: Boolean): Boolean {
        return rpcClient.call(
            method = "Engine.HKPauseScript",
            params = buildJsonObject {
                put("enable", enable)
            }
        )
    }
    override suspend fun sendActID(level: Long): Boolean {
        return rpcClient.call(
            method = "Engine.SendActID",
            params = buildJsonObject {
                put("level", level)
            }
        )
    }
    override suspend fun sendToServer(text: String): Boolean {
        return rpcClient.call("Engine.SendToServer", buildJsonObject {
            put("text", text)
        })
    }
    override suspend fun sendToClient(text: String): Boolean {
        return rpcClient.call("Engine.SendToClient", buildJsonObject {
            put("text", text)
        })
    }
    override suspend fun blockPacket(id: Int, id2: Int, isServerPacket: Boolean, timeMs: Long): Boolean {
        return rpcClient.call("Engine.BlockPacket", buildJsonObject {
            put("id", id)
            put("id2", id2)
            put("is_server", isServerPacket)
            put("time", timeMs)
        })
    }
    override suspend fun waitAction(action: L2Action, timeout: Long): WaitResult {
        return rpcClient.call<WaitResult>("Engine.WaitAction", buildJsonObject {
            put("action", action.ordinal)
            put("timeout", timeout)
        })
    }

    override suspend fun loadGpsMap(filePath: String): Int {
        return rpcClient.call(
            method = "Engine.LoadGPSPoint",
            params = buildJsonObject {
                put("file_path", filePath)
            }
        )
    }

    override suspend fun moveGpsPoint(gpsPointName: String, timeoutMs: Long): Boolean {
        return rpcClient.call(
            method = "Engine.GPSMove",
            params = buildJsonObject {
                put("gps_name", gpsPointName)
            },
            timeoutMs = timeoutMs
        )
    }

    override suspend fun getGpsPoint(gpsPointName: String): L2GPSPoint {
       return rpcClient.call(
           method = "Engine.GPSPoint",
           params = buildJsonObject {
               put("gps_name", gpsPointName)
           }
       )
    }

    override suspend fun moveGpsPointRandom(gpsPointName: String, range: Int, timeoutMs: Long): Boolean {
        return rpcClient.call(
            method = "Engine.GPSMove",
            params = buildJsonObject {
                put("gps_name", gpsPointName)
                put("gps_range", range)
            },
            timeoutMs = timeoutMs
        )
    }


    override suspend fun petList(): List<L2Pet> {
        return rpcClient.call("Engine.GetPetList")
    }

    override suspend fun inventory(): List<L2Item> {
        return rpcClient.call("Engine.GetInventoryList")
    }
    override suspend fun skillList(): List<L2Skill> {
        return rpcClient.call("Engine.GetSkillList")
    }

    override suspend fun charList(): List<L2Char> {
        return rpcClient.call("Engine.GetCharList")
    }

    override suspend fun dropList(): List<L2Drop> {
        return rpcClient.call("Engine.GetDropList")
    }
}
