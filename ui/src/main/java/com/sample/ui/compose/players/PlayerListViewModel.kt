package com.sample.ui.compose.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.domain.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import repository.GolfRepository
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor(private val repository: GolfRepository) :
    ViewModel() {

    val players: Flow<PagingData<Player>> =
        repository.getPagedPlayers()
            .cachedIn(viewModelScope)
}