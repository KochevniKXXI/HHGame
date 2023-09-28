package ru.nomad.hhgame.data

import ru.nomad.hhgame.model.Monster
import ru.nomad.hhgame.model.Player

object DataSource {
    val listCreatures = listOf(
        Player(
            attack = 20,
            defense = 20,
            startHealth = 50,
            damage = 5..10
        ),
        Monster(
            attack = 30,
            defense = 30,
            startHealth = 50,
            damage = 10..20
        ),
        Monster(
            attack = 10,
            defense = 10,
            startHealth = 20,
            damage = 10..15
        ),
        Monster(
            attack = 10,
            defense = 10,
            startHealth = 20,
            damage = 10..15
        )
    )
}