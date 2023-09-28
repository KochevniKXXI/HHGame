package ru.nomad.hhgame.model

import androidx.annotation.IntRange
import kotlin.properties.Delegates

abstract class Creature(
    @IntRange(from = 1, to = 30)
    val attack: Int,
    @IntRange(from = 1, to = 30)
    val defense: Int,
    @IntRange(from = 0)
    val startHealth: Int,
    val damage: kotlin.ranges.IntRange
) {
    var isDead = startHealth <= 0
        private set

    var currentHealth by Delegates.observable(startHealth) { _, _, newValue ->
        isDead = newValue <= 0
    }

    fun hit(defender: Creature) {
        @IntRange(from = 1) val attackModifier = maxOf(1, attack - defender.defense + 1)

        val dice = buildList {
            repeat(attackModifier) {
                add((1..6).random())
            }
        }

        if (dice.contains(5) || dice.contains(6)) {
            defender.currentHealth -= damage.random()
        }
    }
}