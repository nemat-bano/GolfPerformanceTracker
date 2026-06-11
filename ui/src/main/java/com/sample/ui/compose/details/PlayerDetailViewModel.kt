package com.sample.ui.compose.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import repository.GolfRepository

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    private val repository: GolfRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlayerDetailUiState())
    val uiState: StateFlow<PlayerDetailUiState> = _uiState

    fun loadPlayer(playerId: String) {
        viewModelScope.launch {
            val player = repository.getPlayer(playerId)
            val shots = repository.getShots(playerId)

            _uiState.value = _uiState.value.copy(
                player = player,
                shots = shots
            )
        }
    }
}

data class PlayerDetailUiState(
    val player: Player? = null,
    val shots: List<Shot> = emptyList()
)
