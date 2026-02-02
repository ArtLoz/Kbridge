package com.l2bot.bridge.models

import com.l2bot.bridge.models.interfaces.IL2Object
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Base data class for all game objects.
 * Базовый data class для всех игровых объектов.
 */
@Serializable
data class L2Object(
    /** Object name | Имя объекта */
    override val name: String = "",

    /** Object ID | Идентификатор объекта */
    override val id: Int = 0,

    /** Unique object ID (OID) | Уникальный идентификатор объекта */
    override val oid: Int = 0,

    /** Object validity | Валидность объекта */
    override val valid: Boolean = false,

    /** Object type | Тип объекта */
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN
) : IL2Object
