package com.l2bot.bridge.models.types

enum class LootType(val id: Int) {
    LOOTER(0),        // ldLooter
    RANDOM(1),        // ldRandom
    RANDOM_SPOIL(2),  // ldRandomSpoil
    ORDER(3)          // ldOrder
}