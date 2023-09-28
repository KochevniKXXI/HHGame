package ru.nomad.hhgame.model

import androidx.annotation.IntRange

class Player(
    @IntRange(from = 1, to = 30)
    attack: Int,
    @IntRange(from = 1, to = 30)
    defense: Int,
    @IntRange(from = 0)
    startHealth: Int,
    damage: kotlin.ranges.IntRange
) : Creature(
    attack,
    defense,
    startHealth,
    damage
) {
    var countHealing = 4
        private set

    fun healing() {
        currentHealth = minOf(startHealth, currentHealth + (startHealth * 0.3).toInt())
        countHealing--
    }
}