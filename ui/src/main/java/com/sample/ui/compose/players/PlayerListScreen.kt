package com.sample.ui.compose.players

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.ui.compose.GolfTopBar
import com.sample.golfperformancetracker.ui.R
import androidx.compose.ui.res.stringResource

@Composable
fun PlayerListScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayerListViewModel = hiltViewModel(),
    onPlayerClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val filteredPlayers = remember(searchQuery, state.players) {
        if (searchQuery.isBlank()) {
            state.players
        } else {
            state.players.filter { player ->
                player.name.contains(searchQuery, ignoreCase = true) ||
                        player.club.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            GolfTopBar(
                title = stringResource(R.string.app_bar_title),
                showBackButton = false
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = stringResource(
                            R.string.search_player_or_club
                        )
                    )
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPlayers) { player ->
                    PlayerCard(
                        player = player,
                        onClick = {
                            onPlayerClick(player.id)
                        }
                    )
                }
            }
        }
    }
}