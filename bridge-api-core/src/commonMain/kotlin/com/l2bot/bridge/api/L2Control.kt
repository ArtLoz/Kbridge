package com.l2bot.bridge.api

import com.l2bot.bridge.models.L2GPSPoint
import com.l2bot.bridge.models.dialogs.L2ConfirmDlg
import com.l2bot.bridge.models.entities.L2Char
import com.l2bot.bridge.models.entities.L2Live
import com.l2bot.bridge.models.entities.L2Npc
import com.l2bot.bridge.models.entities.L2Pet
import com.l2bot.bridge.models.entities.L2Spawn
import com.l2bot.bridge.models.entities.L2User
import com.l2bot.bridge.models.events.ActionEvent
import com.l2bot.bridge.models.events.CliPacketEvent
import com.l2bot.bridge.models.events.ConnectionStatus
import com.l2bot.bridge.models.events.PacketEvent
import com.l2bot.bridge.models.item.L2Drop
import com.l2bot.bridge.models.item.L2Item
import com.l2bot.bridge.models.skill.L2Skill
import com.l2bot.bridge.models.types.ChatType
import com.l2bot.bridge.models.types.L2Action
import com.l2bot.bridge.models.types.L2Status
import com.l2bot.bridge.models.types.LootType
import com.l2bot.bridge.models.types.RestartType
import com.l2bot.bridge.models.types.WaitResult
import com.l2bot.bridge.models.types.ZoneType
import kotlinx.coroutines.flow.Flow

abstract class L2Control {
    abstract suspend fun user(): L2User
    abstract suspend fun echo(text: String): String
    abstract suspend fun npcList(): List<L2Npc>
    abstract suspend fun petList(): List<L2Pet>
    abstract suspend fun inventory(): List<L2Item>
    abstract suspend fun skillList(): List<L2Skill>
    abstract suspend fun charList(): List<L2Char>
    abstract suspend fun dropList(): List<L2Drop>
    abstract suspend fun moveTo(x:Int, y:Int, z:Int, timeout:Int = 8000) : Boolean
    abstract suspend fun moveTo(obj: L2Spawn, delta:Int = -70) : Boolean
    abstract suspend fun moveToTarget(delta: Int = -100): Boolean
    abstract suspend fun unstuck(): Boolean
    abstract suspend fun goHome(type: RestartType = RestartType.TOWN): Boolean
    abstract suspend fun teleport(id: Long): Boolean
    abstract suspend fun useAction(id: Long, force: Boolean = false, shift: Boolean = false): Boolean
    abstract suspend fun attack(pauseTime: Long = 2000L, force: Boolean = false): Boolean
    abstract suspend fun pickUp(item: L2Drop, byPet: Boolean = false): Boolean
    abstract suspend fun stand(): Boolean
    abstract suspend fun setTarget(name: String): Boolean
    abstract suspend fun setTargetId(id: Long): Boolean
    abstract suspend fun setTarget(obj: L2Live): Boolean
    abstract suspend fun action(oid: Long, force: Boolean = false): Boolean
    abstract suspend fun cancelTarget(): Boolean
    abstract suspend fun assist(name: String): Boolean
    abstract suspend fun findEnemy(obj: L2Live, range: Long = 2000L, zLimit: Long = 300L): L2Live?
    abstract suspend fun autoTarget(range: Long = 2000L, zLimit: Long = 300L, notBusy: Boolean = true): Boolean
    abstract suspend fun ignore(obj: L2Spawn)
    abstract suspend fun clearIgnore()
    abstract suspend fun isBusy(obj: L2Npc): Boolean
    abstract suspend fun useSkill(id: Long, force: Boolean = false, shift: Boolean = false): Boolean
    abstract suspend fun dUseSkill(id: Long, force: Boolean = false, shift: Boolean = false): Boolean
    abstract suspend fun useSkillGround(id: Long, x: Int, y: Int, z: Int, force: Boolean = false, shift: Boolean = false): Boolean
    abstract suspend fun stopCasting(): Boolean
    abstract suspend fun dispel(id: Long): Boolean
    abstract suspend fun learnSkill(id: Long): Boolean
    abstract suspend fun updateSkillList(): Boolean
    abstract suspend fun useItem(obj: L2Item, byPet: Boolean = false, force: Boolean = false): Boolean
    abstract suspend fun useItem(name: String, byPet: Boolean = false, force: Boolean = false): Boolean
    abstract suspend fun useItem(id: Long, byPet: Boolean = false, force: Boolean = false): Boolean
    abstract suspend fun useItemOid(oid: Long, byPet: Boolean = false, force: Boolean = false): Boolean
    abstract suspend fun destroyItem(name: String, count: Long): Boolean
    abstract suspend fun destroyItem(id: Int, count: Long): Boolean
    abstract suspend fun destroyItem(item: L2Item, count: Long): Boolean
    abstract suspend fun dropItem(id: Long, count: Long, x: Int, y: Int, z: Int): Boolean
    abstract suspend fun makeItem(index: Long): Boolean
    abstract suspend fun crystalItem(id: Long): Boolean
    abstract suspend fun crystalItem(item: L2Item): Boolean
    abstract suspend fun moveItem(name: String, count: Long, toPet: Boolean): Boolean
    abstract suspend fun loadItems(toWh: Boolean, ids: List<Long>): Boolean
    abstract suspend fun autoSoulShot(name: String, active: Boolean): Boolean
    abstract suspend fun dAutoSoulShot(id: Long, active: Boolean): Boolean
    abstract suspend fun equipped(name: String): Int
    abstract suspend fun dismissPet(): Boolean
    abstract suspend fun dismissSum(): Boolean
    abstract suspend fun say(text: String, chatType: ChatType, playerName: String = ""): Boolean
    abstract suspend fun inviteParty(name: String, lootMode: LootType = LootType.LOOTER): Boolean
    abstract suspend fun dismissParty(name: String): Boolean
    abstract suspend fun joinParty(accept: Boolean): Boolean
    abstract suspend fun leaveParty(): Boolean
    abstract suspend fun setPartyLeader(name: String): Boolean
    abstract suspend fun getMentor(): String
    abstract suspend fun kickMentor(): Boolean
    abstract suspend fun closeRoom(): Boolean
    abstract suspend fun createRoom(caption: String, minLevel: Int = 1, maxLevel: Int = 110): Boolean
    abstract suspend fun autoAcceptClan(players: List<String>): Boolean
    abstract suspend fun autoAcceptCC(players: List<String>): Boolean
    abstract suspend fun autoAcceptMentors(players: List<String>): Boolean
    abstract suspend fun questStatus(id: Long): Long
    abstract suspend fun questStatus(id: Long, stage: Long): Boolean
    abstract suspend fun cancelQuest(id: Int): Boolean
    abstract suspend fun openQuestion(): Boolean
    abstract suspend fun getDailyItems(): Boolean
    abstract suspend fun getDailyItem(id: Long): Boolean
    abstract suspend fun updateDailyList(): Boolean
    abstract suspend fun dlgOpen(timeout: Long = 5000): Boolean
    abstract suspend fun dlgSel(index: Int): Boolean
    abstract suspend fun dlgSel(caption: String, timeout: Int = 1000): Boolean
    abstract suspend fun bypassToServer(text: String): Boolean
    abstract suspend fun dlgText(): String
    abstract suspend fun dlgTime(): Long
    abstract suspend fun cbText(): String
    abstract suspend fun cbTime(): Long
    abstract suspend fun hlpText(): String
    abstract suspend fun hlpTime(): Long
    abstract suspend fun getConfirmDlg(): L2ConfirmDlg?
    abstract suspend fun confirmDialog(accept: Boolean): Boolean
    abstract suspend fun openPrivateStore(items: List<Long>, storeType: Int, caption: String): Boolean
    abstract suspend fun npcTrade(sell: Boolean, items: List<Long>): Boolean
    abstract suspend fun npcExchange(idOrIndex: Long, count: Long = 1, byIndex: Boolean = false): Boolean
    abstract suspend fun castleTax(townId: Long): Int
    abstract suspend fun sendMail(receiver: String, topic: String, text: String, items: List<Long> = emptyList(), price: Long = 0): Boolean
    abstract suspend fun getMailItems(maxLoad: Long = 65, maxCount: Long = 1000): Boolean
    abstract suspend fun getZoneType(): ZoneType
    abstract suspend fun getZoneName(x: Int, y: Int, z: Int): String
    abstract suspend fun getZoneID(x: Int, y: Int, z: Int): Long
    abstract suspend fun inZone(x: Int, y: Int, z: Int): Boolean
    abstract suspend fun inZone(obj: L2Spawn): Boolean
    abstract suspend fun gameTime(): Long
    abstract suspend fun isDay(): Boolean
    abstract suspend fun getStatus(): L2Status
    abstract suspend fun loginStatus(): Int
    abstract suspend fun authLogin(login: String, password: String): Boolean
    abstract suspend fun gameStart(charIndex: Int = -1): Boolean
    abstract suspend fun restart(): Boolean
    abstract suspend fun dRestart(): Boolean
    abstract suspend fun setFaceControl(id: Int, active: Boolean): Boolean
    abstract suspend fun getFaceState(id: Int): Boolean
    abstract suspend fun updateCfg(wait: Boolean = true): Boolean
    abstract suspend fun loadConfig(filePath: String): Boolean
    abstract suspend fun loadZone(filePath: String): Boolean
    abstract suspend fun clearZone(): Boolean
    abstract suspend fun setPerform(level: Long): Boolean
    abstract suspend fun setMapKeepDist(dist: Int): Boolean
    abstract suspend fun gamePrint(text: String, author: String = "", chatType: Int = 0): Boolean
    abstract suspend fun gameClose(): Boolean
    abstract suspend fun blinkWindow(game: Boolean = true): Boolean
    abstract suspend fun setGameWindow(show: Boolean): Boolean
    abstract suspend fun useKey(keyName: String, downUp: Int = 0): Boolean
    abstract suspend fun useKey(keyCode: Int, downUp: Int = 0): Boolean
    abstract suspend fun enterText(text: String): Boolean
    abstract suspend fun postMessage(msg: Long, wParam: Int, lParam: Int): Int
    abstract suspend fun sendMessage(msg: Long, wParam: Int, lParam: Int): Int
    abstract suspend fun getGamePath(): String
    abstract suspend fun getGameWindowHandle(): Long
    abstract suspend fun getGameHash(): Long
    abstract suspend fun getGameProtocol(): Int
    abstract suspend fun getGameVersion(): Int
    abstract suspend fun getServerIP(): String
    abstract suspend fun getServerName(): String
    abstract suspend fun getServerID(): Int
    abstract suspend fun isClassicServer(): Boolean
    abstract suspend fun getServerTime(): Long
    abstract suspend fun msg(title: String, text: String, color: Int = 0xFFFFFF): Boolean
    abstract suspend fun blinkWindowBot(game: Boolean = true): Boolean
    abstract suspend fun setScriptPause(enable: Boolean): Boolean
    abstract suspend fun sendActID(level: Long): Boolean
    abstract suspend fun sendToServer(text: String): Boolean
    abstract suspend fun sendToClient(text: String): Boolean
    abstract suspend fun blockPacket(id: Int, id2: Int = 0, isServerPacket: Boolean, timeMs: Long = 0xFFFFFFFFL): Boolean
    abstract suspend fun waitAction(action: L2Action, timeout: Long = 5000): WaitResult
    abstract suspend fun loadGpsMap(filePath: String):Int
    abstract suspend fun moveGpsPoint(gpsPointName: String): Boolean
    abstract suspend fun getGpsPoint(gpsPointName: String): L2GPSPoint
    abstract suspend fun moveGpsPointRandom(gpsPointName: String, range:Int =100): Boolean
    abstract val actionEvents: Flow<ActionEvent>
    abstract val packetEvents: Flow<PacketEvent>
    abstract val cliPacketEvents: Flow<CliPacketEvent>
    abstract val connectionStatus: Flow<ConnectionStatus>
}
