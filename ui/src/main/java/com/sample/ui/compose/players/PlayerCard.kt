package com.sample.ui.compose.players

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sample.domain.model.Player
import com.sample.golfperformancetracker.ui.R
import kotlinx.coroutines.launch

@Composable
fun PlayerCard(
    player: Player,
    onClick: () -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable {
                expanded = !expanded
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(
                    R.string.club,
                    player.club
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (expanded) {
                Text(
                    text = stringResource(
                        R.string.average_speed,
                        player.averageSpeed
                    )
                )
                Text(
                    text = stringResource(
                        R.string.average_distance,
                        player.averageDistance
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        scope.launch {
                            expanded = false
                            onClick()
                        }
                    }
                ) {
                    Text(
                        text = stringResource(
                            R.string.view_details
                        )
                    )
                }
            }
        }
    }
}