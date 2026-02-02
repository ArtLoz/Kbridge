package com.l2bot.bridge.models.item

import com.l2bot.bridge.models.interfaces.IL2ShopItem
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Data class for items in NPC shop.
 * Data class для предметов в магазине НПЦ.
 */
@Serializable
data class L2ShopItem(
    // IL2Object fields
    override val name: String = "",
    override val id: Int = 0,
    override val oid: Int = 0,
    override val valid: Boolean = false,
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN,

    // IL2Item fields
    override val slot: Int = 0,
    override val count: Long = 0,
    override val equipped: Boolean = false,
    @SerialName("item_type")
    override val itemType: Int = 0,
    override val grade: Int = 0,
    @SerialName("grade_name")
    override val gradeName: String = "",
    @SerialName("is_named")
    override val isNamed: Boolean = false,
    @SerialName("body_part")
    override val bodyPart: Int = 0,
    override val enchant: Int = 0,
    @SerialName("augment_id")
    override val augmentId: Int = 0,
    @SerialName("augment_id2")
    override val augmentId2: Int = 0,
    @SerialName("remain_time")
    override val remainTime: Int = 0,
    @SerialName("atk_elem")
    override val atkElem: Int = 0,
    @SerialName("elem_power")
    override val elemPower: Int = 0,
    @SerialName("p_water")
    override val waterPower: Int = 0,
    @SerialName("p_fire")
    override val firePower: Int = 0,
    @SerialName("p_earth")
    override val earthPower: Int = 0,
    @SerialName("p_wind")
    override val windPower: Int = 0,
    @SerialName("p_unholy")
    override val unholyPower: Int = 0,
    @SerialName("p_holy")
    override val holyPower: Int = 0,

    // IL2ShopItem fields
    override val price: Long = 0
) : IL2ShopItem
