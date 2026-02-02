package com.l2bot.bridge.models.interfaces

/**
 * Interface for NPCs.
 * Интерфейс для НПЦ.
 */
interface IL2Npc : IL2Live {
    /** Is pet or summon | Является ли питомцем или саммоном */
    val isPet: Boolean
    /** Pet type | Тип питомца */
    val petType: Int
    /** Owner object (for pets/summons) | Владелец */
    val owner: IL2Live?
    /** NPC type | Тип НПЦ */
    val npcType: Int
    /** NPC count in group | Количество НПЦ в группе */
    val npcCount: Int
}
