package com.l2bot.bridge.models.interfaces

import com.l2bot.bridge.models.types.L2Class

/**
 * Base interface for all game objects.
 * Базовый интерфейс для всех игровых объектов.
 */
interface IL2Object {
    /** Object name | Имя объекта */
    val name: String
    /** Object ID | Идентификатор объекта */
    val id: Int
    /** Unique object ID (OID) | Уникальный идентификатор объекта */
    val oid: Int
    /** Object validity | Валидность объекта */
    val valid: Boolean
    /** Object type | Тип объекта */
    val l2Class: L2Class
}
