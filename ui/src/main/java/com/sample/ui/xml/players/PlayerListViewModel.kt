package com.sample.ui.xml.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import repository.OfflineFirstGolfRepository
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PlayerListViewModel @Inject constructor(
    private val repository: OfflineFirstGolfRepository
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")

    val uiState: StateFlow<PlayerListUiState> =
        combine(
            repository.observePlayers(),
            searchQuery
        ) { players, query ->

            val filteredPlayers = if (query.isBlank()) {
                players
            } else {
                players.filter { player ->
                    player.name.contains(query, ignoreCase = true) ||
                            player.club.contains(query, ignoreCase = true)
                }
            }

            PlayerListUiState(
                players = filteredPlayers,
                isLoading = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlayerListUiState(isLoading = true)
        )

    init {
        syncPlayers()
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    private fun syncPlayers() {
        viewModelScope.launch {
            try {
                repository.syncPlayers()
            } catch (e: Exception) {
                Timber.e(
                    e,
                    "Failed to sync players. Using cached data."
                )
            }
        }
    }
}

data class PlayerListUiState(
    val players: List<Player> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)