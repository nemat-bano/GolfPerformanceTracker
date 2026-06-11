package com.sample.ui.xml.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.GolfRepository

@HiltViewModel
class PlayerListViewModel @Inject constructor(
    private val repository: GolfRepository
) : ViewModel() {
    private var allPlayers: List<Player> = emptyList()
    private val _uiState = MutableStateFlow(PlayerListUiState())
    val uiState: StateFlow<PlayerListUiState> = _uiState.asStateFlow()

    init {
        getPlayers()
    }

    fun getPlayers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                allPlayers = repository.getPlayers()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    players = allPlayers
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun searchPlayers(query: String) {
        val filteredPlayers = if (query.isBlank()) {
            allPlayers
        } else {
            allPlayers.filter { player ->
                player.name.contains(query, ignoreCase = true) ||
                        player.club.contains(query, ignoreCase = true)
            }
        }

        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            players = filteredPlayers
        )
    }
}

data class PlayerListUiState(
    val players: List<Player> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)