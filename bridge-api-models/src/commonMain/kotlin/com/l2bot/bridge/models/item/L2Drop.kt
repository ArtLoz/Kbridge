package com.l2bot.bridge.models.item

import com.l2bot.bridge.models.entities.L2Spawn
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс предметов, лежащих на земле (дроп) | Interface for items on the ground (drop)
 */
@Serializable
class L2Drop : L2Spawn() {
    /** Количество предметов | Item count */
    var count: Long = 0

    /** OID владельца предмета (кому принадлежит дроп) | Owner's OID of the item */
    @SerialName("owner_oid")
    var ownerOid: Int = 0

    /** Является ли предмет стопкуемым | Whether the item is stackable */
    var stackable: Boolean = false

    /** Принадлежит ли предмет нашему персонажу | Whether the item belongs to us */
    @SerialName("is_my")
    var isMy: Boolean = false
}