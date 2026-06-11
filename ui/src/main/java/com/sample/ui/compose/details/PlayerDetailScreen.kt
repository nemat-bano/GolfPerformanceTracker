package com.sample.ui.compose.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.ui.compose.GolfTopBar
import com.sample.golfperformancetracker.ui.R

@Composable
fun PlayerDetailScreen(
    playerId: String,
    onBackClick: () -> Unit,
    viewModel: PlayerDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(playerId) {
        viewModel.loadPlayer(playerId)
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val player = state.player

    Scaffold(
        topBar = {
            GolfTopBar(
                title = player?.name ?: "Player Detail",
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                if (player != null) {
                    Text(
                        text = player.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(text = "Club: ${player.club}")
                    Text(text = "Average speed: ${player.averageSpeed} mph")
                    Text(text = "Average distance: ${player.averageDistance} yards")

                    Spacer(modifier = Modifier.height(24.dp))

                    if (state.shots.isNotEmpty()) {
                        Text(
                            text = "Shots",
                            style = MaterialTheme.typography.titleLarge
                        )
                    } else {
                        Text(
                            text = "No shots available",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            items(state.shots) { shot ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text( text = stringResource(
                            R.string.club,
                            shot.clubUsed
                        ))
                        Text(
                            text = stringResource(
                                R.string.ball_speed,
                                shot.ballSpeed
                            )
                        )
                        Text(
                            text = stringResource(
                                R.string.launch_angle,
                                shot.launchAngle
                            )
                        )

                        Text(
                            text = stringResource(
                                R.string.spin_rate,
                                shot.spinRate
                            )
                        )
                        Text(
                            text = stringResource(
                                R.string.distance,
                                shot.distance
                            )
                        )

                    }
                }
            }
        }
    }
}
