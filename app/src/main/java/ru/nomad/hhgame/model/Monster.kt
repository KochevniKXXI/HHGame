package ru.nomad.hhgame.model

import androidx.annotation.IntRange

class Monster(
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
)