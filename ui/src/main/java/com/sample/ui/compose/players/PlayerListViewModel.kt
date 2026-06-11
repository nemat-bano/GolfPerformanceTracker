package com.sample.ui.compose.players

import androidx.lifecycle.ViewModel
import com.sample.domain.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.GolfRepository
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor(private val repository: GolfRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(PlayerListUiState())
    val uiState: StateFlow<PlayerListUiState> = _uiState

    init {
        getPlayerList()
    }

    fun getPlayerList() {
        viewModelScope.launch {
            val players = repository.getPlayers()

            _uiState.update {
                it.copy(
                    players = players
                )
            }
        }
    }
}

data class PlayerListUiState(
    val players: List<Player> = emptyList(),
)