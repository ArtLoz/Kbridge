package com.l2bot.bridge.models

import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
open class L2Object {
    /** Имя объекта | Object name */
    var name: String = ""

    /** Идентификатор объекта | Object ID */
    var id: Int = 0

    /** Уникальный идентификатор | Unique ID */
    var oid: Int = 0

    /** Валидность объекта | Object validity */
    var valid: Boolean = false

    /** Тип объекта | Object type */
    @SerialName("l2_class")
    var l2Class: L2Class = L2Class.UNKNOWN
}