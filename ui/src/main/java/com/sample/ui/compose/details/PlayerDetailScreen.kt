package com.sample.ui.compose.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sample.ui.compose.GolfTopBar
import com.sample.golfperformancetracker.ui.R

@OptIn(ExperimentalGlideComposeApi::class)
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
                title = player?.name ?: stringResource(R.string.player_detail),
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item {
                        if (player != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                GlideImage(
                                    model = player.imageUrl,
                                    contentDescription = player.name,
                                    modifier = Modifier.size(110.dp)
                                ) {
                                    it.centerCrop()
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = player.name,
                                        style = MaterialTheme.typography.headlineSmall
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = stringResource(R.string.club, player.club),
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Text(
                                        text = stringResource(R.string.average_speed, player.averageSpeed),
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Text(
                                        text = stringResource(R.string.average_distance, player.averageDistance),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))

                            if (state.shots.isNotEmpty()) {
                                Text(
                                    text = stringResource(R.string.shots),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.no_shots_available),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            Text(
                                text = stringResource(R.string.player_not_found),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    items(state.shots) { shot ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = stringResource(
                                        R.string.club,
                                        shot.clubUsed
                                    )
                                )

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
    }
}