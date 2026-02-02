package com.l2bot.bridge.models.entities

import com.l2bot.bridge.models.interfaces.IL2Pet
import com.l2bot.bridge.models.item.L2Item
import com.l2bot.bridge.models.skill.L2Buff
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Data class for pets.
 * Data class для питомцев.
 */
@Serializable
data class L2Pet(
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

    // IL2Npc fields
    @SerialName("is_pet")
    override val isPet: Boolean = true,
    @SerialName("pet_type")
    override val petType: Int = 0,
    override val owner: L2Live? = null,
    @SerialName("npc_type")
    override val npcType: Int = 0,
    @SerialName("npc_count")
    override val npcCount: Int = 0,

    // IL2Pet fields
    override val fed: Int = 0
) : IL2Pet
