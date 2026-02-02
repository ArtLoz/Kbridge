package com.l2bot.bridge.models.interfaces

/**
 * Interface for live entities (characters, NPCs, pets).
 * Интерфейс для живых существ (персонажи, НПЦ, питомцы).
 */
interface IL2Live : IL2Spawn {
    /** Title | Титул */
    val title: String
    /** Level | Уровень */
    val level: Int
    /** HP percent | Процент HP */
    val hpPercent: Long
    /** Current HP | Текущее HP */
    val curHp: Long
    /** Current MP | Текущее MP */
    val curMp: Long
    /** Maximum HP | Максимальное HP */
    val maxHp: Long
    /** MP percent | Процент MP */
    val mpPercent: Long
    /** Maximum MP | Максимальное MP */
    val maxMp: Long
    /** Experience | Опыт */
    val exp: Long
    /** Skill points | Очки умений */
    val sp: Long
    /** Karma | Карма */
    val karma: Int
    /** Weapon enchant level | Уровень заточки оружия */
    val enchant: Int
    /** Is attackable | Можно ли атаковать */
    val attackable: Boolean
    /** Is sweepable | Можно ли использовать Sweep */
    val sweepable: Boolean
    /** Is PK | Является ли ПК */
    val isPk: Boolean
    /** Is in PvP mode | Находится ли в PvP режиме */
    val isPvp: Boolean
    /** Is running | Бежит ли */
    val isRunning: Boolean
    /** Is in combat | Находится ли в бою */
    val inCombat: Boolean
    /** Is sitting | Сидит ли */
    val isSitting: Boolean
    /** Is dead | Мертв ли */
    val isDead: Boolean
    /** Is invisible | Невидимый ли */
    val isInvisible: Boolean
    /** Movement target X | Координата X точки назначения */
    val toX: Int
    /** Movement target Y | Координата Y точки назначения */
    val toY: Int
    /** Movement target Z | Координата Z точки назначения */
    val toZ: Int
    /** Current target | Текущая цель */
    val target: IL2Live?
    /** Current cast info | Информация о текущем касте */
    val castInfo: IL2Buff?
    /** List of buffs | Список баффов */
    val buffs: List<IL2Buff>
    /** List of abnormals/debuffs | Список дебаффов */
    val abnormals: List<IL2Buff>
    /** List of equipped items | Список экипировки */
    val equips: List<IL2Item>
}
