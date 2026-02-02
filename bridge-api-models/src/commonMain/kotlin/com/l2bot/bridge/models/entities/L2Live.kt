package com.l2bot.bridge.models.entities

import com.l2bot.bridge.models.item.L2Item
import com.l2bot.bridge.models.skill.L2Buff
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Полноценная модель живого существа
 * Full model of a live entity
 */
@Serializable
open class L2Live : L2Spawn() {
    /** Титул объекта | Object title */
    var title: String = ""

    /** Уровень | Level */
    var level: Int = 0

    /** Текущее HP | Current HP */
    @SerialName("hp")
    var hpPercent: Long = 0


    @SerialName("cur_hp")
    var curHp: Long = 0

    @SerialName("cur_mp")
    var  curMp: Long = 0

    /** Максимальное HP | Maximum HP */
    @SerialName("max_hp")
    var maxHp: Long = 0

    /** Текущее MP | Current MP */
    @SerialName("mp")
    var mpPercent: Long = 0

    /** Максимальное MP | Maximum MP */
    @SerialName("max_mp")
    var maxMp: Long = 0

    /** Опыт | Experience */
    var exp: Long = 0

    /** Очки умений | Skill points */
    var sp: Long = 0

    /** Карма | Karma */
    var karma: Int = 0

    /** Уровень заточки оружия | Weapon enchant level */
    var enchant: Int = 0

    /** Можно ли атаковать | Is attackable */
    var attackable: Boolean = false

    /** Можно ли использовать Sweep | Is sweepable */
    var sweepable: Boolean = false

    /** Является ли ПК | Is PK */
    @SerialName("is_pk")
    var isPk: Boolean = false

    /** Находится ли в PvP режиме | Is in PvP mode */
    @SerialName("is_pvp")
    var isPvp: Boolean = false

    /** Бежит ли | Is running */
    @SerialName("is_running")
    var isRunning: Boolean = false

    /** Находится ли в бою | Is in combat */
    @SerialName("in_combat")
    var inCombat: Boolean = false

    @SerialName("is_sitting")
    var isSitting: Boolean = false

    /** Мертв ли | Is dead */
    @SerialName("is_dead")
    var isDead: Boolean = false

    /** Невидимый ли | Is invisible */
    @SerialName("is_invisible")
    var isInvisible: Boolean = false

    /** Координата X точки назначения | Movement target X */
    @SerialName("to_x")
    var toX: Int = 0

    /** Координата Y точки назначения | Movement target Y */
    @SerialName("to_y")
    var toY: Int = 0

    /** Координата Z точки назначения | Movement target Z */
    @SerialName("to_z")
    var toZ: Int = 0

    /** Текущая цель (рекурсивная модель, глубина ограничена на сервере) | Current target */
    var target: L2Live? = null

    /** Информация о текущем касте | Current cast info */
    @SerialName("cast_info")
    var castInfo: L2Buff? = null

    /** Список баффов | List of buffs */
    var buffs: List<L2Buff> = emptyList()

    /** Список особых состояний / дебаффов | List of abnormals / debuffs */
    var abnormals: List<L2Buff> = emptyList()

    /** Список экипировки | List of equipped items */
    var equips: List<L2Item> = emptyList()
}