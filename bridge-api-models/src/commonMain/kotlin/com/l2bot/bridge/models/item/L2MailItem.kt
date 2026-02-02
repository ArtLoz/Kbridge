package com.l2bot.bridge.models.item

import com.l2bot.bridge.models.interfaces.IL2MailItem
import com.l2bot.bridge.models.types.L2Class
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Data class for mail items.
 * Data class для почтовых отправлений.
 */
@Serializable
data class L2MailItem(
    // IL2Object fields
    override val name: String = "",
    override val id: Int = 0,
    override val oid: Int = 0,
    override val valid: Boolean = false,
    @SerialName("l2_class")
    override val l2Class: L2Class = L2Class.UNKNOWN,

    // IL2MailItem fields
    @SerialName("is_sent")
    override val isSent: Boolean = false,
    @SerialName("mail_type")
    override val mailType: Int = 0,
    override val title: String = "",
    override val sender: String = "",
    @SerialName("is_locked")
    override val isLocked: Boolean = false,
    @SerialName("expiration_time")
    override val expirationTime: Long = 0,
    @SerialName("is_unread")
    override val isUnread: Boolean = false,
    @SerialName("with_items")
    override val withItems: Boolean = false,
    @SerialName("no_recv_items")
    override val noRecvItems: Boolean = false,
    @SerialName("is_news")
    override val isNews: Boolean = false,
    @SerialName("actual_time")
    override val actualTime: Long = 0
) : IL2MailItem
