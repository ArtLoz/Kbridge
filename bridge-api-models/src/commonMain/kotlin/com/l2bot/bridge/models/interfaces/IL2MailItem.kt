package com.l2bot.bridge.models.interfaces

/**
 * Interface for mail items.
 * Интерфейс для почтовых отправлений.
 */
interface IL2MailItem : IL2Object {
    /** Was sent by us | Отправлено нами */
    val isSent: Boolean
    /** Mail type | Тип письма */
    val mailType: Int
    /** Mail title | Заголовок письма */
    val title: String
    /** Sender name | Имя отправителя */
    val sender: String
    /** Is locked (requires payment) | Заблокировано */
    val isLocked: Boolean
    /** Expiration timestamp | Время истечения */
    val expirationTime: Long
    /** Is unread | Непрочитанное */
    val isUnread: Boolean
    /** Has attached items | Есть вложения */
    val withItems: Boolean
    /** Has unreceived items | Есть неполученные предметы */
    val noRecvItems: Boolean
    /** Is news | Системная новость */
    val isNews: Boolean
    /** Actual timestamp | Актуальное время */
    val actualTime: Long
}
