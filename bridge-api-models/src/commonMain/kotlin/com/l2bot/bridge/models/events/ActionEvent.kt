package com.l2bot.bridge.models.events

/**
 * Action события от Delphi плагина
 * Соответствует TL2Action из PluginConst.pas
 */
enum class ActionType(val id: Int) {
    NULL(0),
    SPAWN(1),
    DELETE(2),
    PET_SPAWN(3),
    PET_DELETE(4),
    PET_JOIN(5),
    PET_LEAVE(6),
    CHAR_JOIN(7),
    INVITE(8),
    DIE(9),
    REVIVE(10),
    MY_REVIVE(11),
    STATS(12),
    MY_TARGET(13),
    MY_UN_TARGET(14),
    TARGET(15),
    UN_TARGET(16),
    IN_GAME(17),
    BUFFS(18),
    PARTY_BUFFS(19),
    SKILLS(20),
    CONFIRM_DLG(21),
    DLG(22),
    SYS_MSG(23),
    MOVE_TYPE(24),
    WAIT_TYPE(25),
    MY_WAIT_TYPE(26),
    START(27),
    STOP(28),
    START_ATTACK(29),
    STOP_ATTACK(30),
    CAST(31),
    CANCEL_CAST(32),
    MY_CANCEL_CAST(33),
    CAST_FAILED(34),
    MY_CAST_FAILED(35),
    TELEPORT(36),
    INV_UPDATE(37),
    AUTO_SOUL_SHOT(38),
    NPC_TRADE(39),
    CHAT(40),
    KEY(41),
    CHAR_SELECT(42),
    LEAVE_PARTY(43),
    POST(44),
    LEARN(45),
    ALL(46),
    MY_CAST(47),
    DELAY(48),
    STATUS(49),
    AUCTION(50),
    AUCTION_SL(51),
    CAPTCHA(52),
    MAIL(53),
    TAX_RATE(54),
    LOGIN_STATE(55);
    
    companion object {
        private val map = entries.associateBy { it.id }
        fun fromId(id: Int): ActionType? = map[id]
    }
}

/**
 * Action событие
 */
data class ActionEvent(
    val action: ActionType,
    val p1: Int,
    val p2: Int
) {
    constructor(actionId: Int, p1: Int, p2: Int) : this(
        ActionType.fromId(actionId) ?: ActionType.NULL,
        p1,
        p2
    )
}
