package com.sample.ui.xml.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import repository.OfflineFirstGolfRepository
import javax.inject.Inject

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    private val repository: OfflineFirstGolfRepository
) : ViewModel() {
    private val _playerId = MutableStateFlow<String?>(null)

    val player =
        _playerId.filterNotNull()
            .flatMapLatest {
                repository.observePlayer(it)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )

    val shots =
        _playerId.filterNotNull()
            .flatMapLatest {
                repository.observeShots(it)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun loadPlayer(playerId: String) {

        _playerId.value = playerId

        viewModelScope.launch {
            repository.syncPlayerDetails(playerId)
        }
    }
}
