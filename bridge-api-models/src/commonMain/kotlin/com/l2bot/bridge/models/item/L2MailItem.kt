package com.l2bot.bridge.models.item

import com.l2bot.bridge.models.L2Object
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * Интерфейс почтового отправления | Mail item interface
 */
@Serializable
data class L2MailItem(
    /** Отправлено ли письмо нами | Whether the mail was sent by us */
    @SerialName("is_sent")
    val isSent: Boolean,

    /** Тип письма | Mail type */
    @SerialName("mail_type")
    val mailType: Int,

    /** Заголовок письма | Mail title */
    val title: String,

    /** Имя отправителя | Sender name */
    val sender: String,

    /** Заблокировано ли письмо (требует оплаты за получение) | Whether the mail is locked (payment required) */
    @SerialName("is_locked")
    val isLocked: Boolean,

    /** Время истечения срока хранения | Expiration timestamp */
    @SerialName("expiration_time")
    val expirationTime: Long,

    /** Является ли письмо непрочитанным | Whether the mail is unread */
    @SerialName("is_unread")
    val isUnread: Boolean,

    /** Содержит ли письмо прикрепленные предметы | Whether the mail has attached items */
    @SerialName("with_items")
    val withItems: Boolean,

    /** Есть ли не полученные предметы в письме | Presence of unreceived items */
    @SerialName("no_recv_items")
    val noRecvItems: Boolean,

    /** Является ли письмо системной новостью | Whether the mail is news */
    @SerialName("is_news")
    val isNews: Boolean,

    /** Актуальное время (время получения/отправки) | Actual mail timestamp */
    @SerialName("actual_time")
    val actualTime: Long,
) : L2Object() {

}