package com.sample.ui.compose.players

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sample.golfperformancetracker.ui.R
import com.sample.ui.compose.GolfTopBar

@Composable
fun PlayerListScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayerListViewModel = hiltViewModel(),
    onPlayerClick: (String) -> Unit
) {
    val players = viewModel.players.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            GolfTopBar(
                title = stringResource(R.string.app_bar_title),
                showBackButton = false
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = players.itemCount
            ) { index ->
                val player = players[index]

                if (player != null) {
                    PlayerCard(
                        player = player,
                        onClick = {
                            onPlayerClick(player.id)
                        }
                    )
                }
            }

            when {
                players.loadState.refresh is LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }

                players.loadState.append is LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }

                players.loadState.refresh is LoadState.Error -> {
                    val error =
                        players.loadState.refresh as LoadState.Error

                    item {
                        ErrorItem(
                            message = error.error.message ?: "Something went wrong",
                            onRetryClick = {
                                players.retry()
                            }
                        )
                    }
                }

                players.loadState.append is LoadState.Error -> {
                    val error =
                        players.loadState.append as LoadState.Error

                    item {
                        ErrorItem(
                            message = error.error.message ?: "Something went wrong",
                            onRetryClick = {
                                players.retry()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorItem(
    message: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRetryClick
        ) {
            Text(text = "Retry")
        }
    }
}
@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}