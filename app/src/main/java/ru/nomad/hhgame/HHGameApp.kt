package ru.nomad.hhgame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Adb
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.nomad.hhgame.data.DataSource
import ru.nomad.hhgame.model.Creature
import ru.nomad.hhgame.model.Monster
import ru.nomad.hhgame.model.Player

@Composable
fun HHGameApp(
    listCreatures: List<Creature>,
    modifier: Modifier = Modifier
) {
    var aggressorIndex: Int? by rememberSaveable { mutableStateOf(null) }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 136.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(listCreatures) { creature ->
            ElevatedButton(
                onClick = {
                    aggressorIndex = if (aggressorIndex == null) {
                        listCreatures.indexOf(creature)
                    } else {
                        listCreatures[aggressorIndex!!].hit(creature)
                        null
                    }
                },
                shape = RectangleShape,
                contentPadding = PaddingValues(4.dp),
                enabled = aggressorIndex != listCreatures.indexOf(creature) && !creature.isDead
            ) {
                CreatureImage(creature = creature)
            }
        }
    }
}

@Composable
fun CreatureImage(
    creature: Creature,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(128.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = creature.currentHealth.toString(),
                    style = MaterialTheme.typography.titleSmall
                )
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }
            if (creature is Player) {
                var countHealing by remember { mutableIntStateOf(creature.countHealing) }

                IconButton(
                    onClick = {
                        creature.healing()
                        countHealing = creature.countHealing
                    },
                    enabled = countHealing > 0 && creature.currentHealth < creature.startHealth && !creature.isDead
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart_plus_24px),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = countHealing.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Icon(
            imageVector = when (creature) {
                is Player -> {
                    if (creature.isDead) {
                        Icons.Outlined.SentimentVeryDissatisfied
                    } else {
                        Icons.Outlined.Mood
                    }
                }

                is Monster -> {
                    if (creature.isDead) {
                        Icons.Outlined.Android
                    } else {
                        Icons.Outlined.Adb
                    }
                }

                else -> {
                    Icons.Outlined.Circle
                }
            },
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = creature.attack.toString(),
                    style = MaterialTheme.typography.titleSmall
                )
                Icon(
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
            Box(
                modifier = Modifier.height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = creature.damage.toString(),
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = creature.defense.toString(),
                    style = MaterialTheme.typography.titleSmall
                )
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PlayerPreview() {
    CreatureImage(
        Player(
            attack = 20,
            defense = 20,
            startHealth = 50,
            damage = 5..10
        )
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun MonsterPreview() {
    CreatureImage(
        Monster(
            attack = 30,
            defense = 30,
            startHealth = 50,
            damage = 10..20
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun HHGameAppPreview() {
    HHGameApp(
        listCreatures = DataSource.listCreatures
    )
}