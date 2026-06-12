package com.sample.ui.compose.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val player = repository.getPlayer(playerId)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    player = player
                )
                loadShots(playerId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }

    private suspend fun loadShots(playerId: String) {
        try {
            val shots = repository.getShots(playerId)

            _uiState.value = _uiState.value.copy(
                shots = shots,
                shotsError = null
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                shots = emptyList(),
                shotsError = e.message ?: "No shots available"
            )
        }
    }
}

data class PlayerDetailUiState(
    val player: Player? = null,
    val shots: List<Shot> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val shotsError: String? = null
)
