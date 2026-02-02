package com.l2bot.bridge.models.entities

import com.l2bot.bridge.models.types.L2Race
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class L2Char : L2Live() {
    /** Текущее значение CP | Current CP value */
    var cp: Long = 0

    /** Текущее значение CP (дубликат для совместимости) | Current CP value */
    @SerialName("cur_cp")
    var curCp: Long = 0

    /** Максимальное значение CP | Maximum CP value */
    @SerialName("max_cp")
    var maxCp: Long = 0

    /** Пол персонажа (0 - муж, 1 - жен) | Character sex (0 - male, 1 - female) */
    var sex: Int = 0

    /** Раса персонажа | Character race */
    var race: L2Race = L2Race.UNKNOWN

    /** Является ли персонаж Героем | Whether the character is a Hero */
    @SerialName("is_hero")
    var isHero: Boolean = false

    /** Является ли персонаж Дворянином (Noobless) | Whether the character is a Noble */
    @SerialName("is_noble")
    var isNoble: Boolean = false

    /** Наличие премиум аккаунта | Premium account status */
    var premium: Boolean = false

    /** Идентификатор текущего класса | Current class ID */
    @SerialName("class_id")
    var classId: Int = 0

    /** Идентификатор основного класса (мейна) | Main class ID */
    @SerialName("main_class_id")
    var mainClassId: Int = 0

    /** Название текущего класса | Current class name */
    @SerialName("class_name")
    var className: String = ""

    /** Альтернативное название класса | Alternative class name */
    @SerialName("class_name_alt")
    var classNameAlt: String = ""

    /** Приоритет класса | Class priority */
    @SerialName("class_priority")
    var classPriority: Int = 0

    /** Тип ездового животного | Mount type */
    @SerialName("mount_type")
    var mountType: Int = 0

    /** Тип торговой лавки (0-None, 1-Sell, 2-Buy, и т.д.) | Store type */
    @SerialName("store_type")
    var storeType: Int = 0

    /** Количество активных кубиков | Number of active cubics */
    @SerialName("cubic_count")
    var cubicCount: Int = 0

    /** Количество рекомендаций | Number of recommendations */
    var recom: Int = 0

    /** Название клана | Clan name */
    @SerialName("clan_name")
    var clanName: String = ""

    /** Название альянса | Ally name */
    @SerialName("ally_name")
    var allyName: String = ""
}