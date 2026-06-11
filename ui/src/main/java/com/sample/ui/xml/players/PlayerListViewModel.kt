package com.sample.ui.xml.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
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
                val players = repository.getPlayers()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    players = players
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }
}

data class PlayerListUiState(
    val players: List<Player> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)