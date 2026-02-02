package com.l2bot.bridge.models.types

enum class L2Action {
    laNull, laSpawn, laDelete, laPetSpawn, laPetDelete, laPetJoin,
    laPetLeave, laCharJoin, laInvite, laDie, laRevive, laMyRevive, laStats,
    laMyTarget, laMyUnTarget, laTarget, laUnTarget, laInGame, laBuffs, laPartyBuffs,
    laSkills, laConfirmDlg, laDlg, laSysMsg,
    laMoveType, laWaitType, laMyWaitType, laStart, laStop, laStartAttack,
    laStopAttack, laCast, laCancelCast, laMyCancelCast, laCastFailed, laMyCastFailed,
    laTeleport, laInvUpdate, laAutoSoulShot, laNpcTrade, laChat, laKey, laCharSelect,
    laLeaveParty, laPost, laLearn, laAll, laMyCast, laDelay, laStatus, laAuction,
    laAuctionSL, atCaptcha, atMail, atTaxRate, laLoginState
}