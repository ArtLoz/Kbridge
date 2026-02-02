package com.l2bot.bridge.models.entities

import com.l2bot.bridge.models.interfaces.IL2Char
import com.l2bot.bridge.models.item.L2Item
import com.l2bot.bridge.models.skill.L2Buff
import com.l2bot.bridge.models.types.L2Class
import com.l2bot.bridge.models.types.L2Race
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class for player characters.
 * Data class для игровых персонажей.
 */
@Serializable
data class L2Char(
    // IL2Object fields
    override val name: String = "",
    override val id: Int = 0,
    override val oid: Int = 0,
    override val valid: Boolean = false,
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN,

    // IL2Spawn fields
    override val x: Int = 0,
    override val y: Int = 0,
    override val z: Int = 0,
    @SerialName("spawn_time")
    override val spawnTime: Int = 0,
    @SerialName("in_zone")
    override val inZone: Boolean = false,

    // IL2Live fields
    override val title: String = "",
    override val level: Int = 0,
    @SerialName("hp")
    override val hpPercent: Long = 0,
    @SerialName("cur_hp")
    override val curHp: Long = 0,
    @SerialName("cur_mp")
    override val curMp: Long = 0,
    @SerialName("max_hp")
    override val maxHp: Long = 0,
    @SerialName("mp")
    override val mpPercent: Long = 0,
    @SerialName("max_mp")
    override val maxMp: Long = 0,
    override val exp: Long = 0,
    override val sp: Long = 0,
    override val karma: Int = 0,
    override val enchant: Int = 0,
    override val attackable: Boolean = false,
    override val sweepable: Boolean = false,
    @SerialName("is_pk")
    override val isPk: Boolean = false,
    @SerialName("is_pvp")
    override val isPvp: Boolean = false,
    @SerialName("is_running")
    override val isRunning: Boolean = false,
    @SerialName("in_combat")
    override val inCombat: Boolean = false,
    @SerialName("is_sitting")
    override val isSitting: Boolean = false,
    @SerialName("is_dead")
    override val isDead: Boolean = false,
    @SerialName("is_invisible")
    override val isInvisible: Boolean = false,
    @SerialName("to_x")
    override val toX: Int = 0,
    @SerialName("to_y")
    override val toY: Int = 0,
    @SerialName("to_z")
    override val toZ: Int = 0,
    override val target: L2Live? = null,
    @SerialName("cast_info")
    override val castInfo: L2Buff? = null,
    override val buffs: List<L2Buff> = emptyList(),
    override val abnormals: List<L2Buff> = emptyList(),
    override val equips: List<L2Item> = emptyList(),

    // IL2Char fields
    override val cp: Long = 0,
    @SerialName("cur_cp")
    override val curCp: Long = 0,
    @SerialName("max_cp")
    override val maxCp: Long = 0,
    override val sex: Int = 0,
    override val race: L2Race = L2Race.UNKNOWN,
    @SerialName("is_hero")
    override val isHero: Boolean = false,
    @SerialName("is_noble")
    override val isNoble: Boolean = false,
    override val premium: Boolean = false,
    @SerialName("class_id")
    override val classId: Int = 0,
    @SerialName("main_class_id")
    override val mainClassId: Int = 0,
    @SerialName("class_name")
    override val className: String = "",
    @SerialName("class_name_alt")
    override val classNameAlt: String = "",
    @SerialName("class_priority")
    override val classPriority: Int = 0,
    @SerialName("mount_type")
    override val mountType: Int = 0,
    @SerialName("store_type")
    override val storeType: Int = 0,
    @SerialName("cubic_count")
    override val cubicCount: Int = 0,
    override val recom: Int = 0,
    @SerialName("clan_name")
    override val clanName: String = "",
    @SerialName("ally_name")
    override val allyName: String = ""
) : IL2Char
