package com.sample.ui

import app.cash.turbine.test
import com.sample.domain.model.Player
import com.sample.ui.utils.FakeOfflineFirstGolfRepository
import com.sample.ui.utils.MainDispatcherRule
import com.sample.ui.xml.players.PlayerListUiState
import com.sample.ui.xml.players.PlayerListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val players = listOf(

        Player(
            id = "1",
            name = "name 1",
            club = "club 1",
            averageSpeed = 45.0,
            averageDistance = 73.0,
            imageUrl = "https://randomuser.me/api/portraits/men/1.jpg"
        ),
        Player(
            id = "2",
            name = "name 2",
            club = "club 2",
            averageSpeed = 45.0,
            averageDistance = 73.0,
            imageUrl = "https://randomuser.me/api/portraits/men/2.jpg"
        ),
        Player(
            id = "3",
            name = "name 3",
            club = "club 3",
            averageSpeed = 45.0,
            averageDistance = 73.0,
            imageUrl = "https://randomuser.me/api/portraits/men/3.jpg"
        ),
    )
    @Test
    fun `uiState filters players by search query`() = runTest {
        val repository = FakeOfflineFirstGolfRepository()

        repository.emitPlayers(players)

        val viewModel = PlayerListViewModel(repository)

        viewModel.uiState.test {
            // initial state from stateIn
            assertEquals(
                PlayerListUiState(isLoading = true),
                awaitItem()
            )

            // first combined result
            val loadedState = awaitItem()

            assertEquals(false, loadedState.isLoading)
            assertEquals(3, loadedState.players.size)

            viewModel.onSearchQueryChanged("club 1")

            val filteredState = awaitItem()

            assertEquals(false, filteredState.isLoading)
            assertEquals(1, filteredState.players.size)
            assertEquals("name 1", filteredState.players.first().name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState shows all players when search query is empty`() = runTest {
        val repository = FakeOfflineFirstGolfRepository()
        repository.emitPlayers(players)

        val viewModel = PlayerListViewModel(repository)

        viewModel.uiState.test {
            awaitItem() // initial: isLoading = true

            val state = awaitItem() // loaded with empty search query

            assertEquals(false, state.isLoading)
            assertEquals(3, state.players.size)
            assertEquals(players, state.players)

            cancelAndIgnoreRemainingEvents()
        }
    }
}