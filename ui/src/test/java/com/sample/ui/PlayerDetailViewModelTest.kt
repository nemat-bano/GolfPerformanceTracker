package com.sample.ui

import app.cash.turbine.test
import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import com.sample.ui.utils.FakeOfflineFirstGolfRepository
import com.sample.ui.utils.MainDispatcherRule
import com.sample.ui.xml.details.PlayerDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerDetailsViewModelTest {

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
        )
    )


    private val shots = listOf(
        Shot(
            id = "shot1", playerId = "1", ballSpeed = 67.0,
            launchAngle = 86.0,
            distance = 79.0,
            spinRate = 14,
            clubUsed = "club 1",
        )
    )

    @Test
    fun `player emits selected player after loadPlayer`() = runTest {
        val repository = FakeOfflineFirstGolfRepository()

        repository.emitPlayers(players)

        val viewModel = PlayerDetailViewModel(repository)

        viewModel.player.test {
            assertEquals(null, awaitItem())

            viewModel.loadPlayer("1")

            val result = awaitItem()

            assertEquals("1", result?.id)
            assertEquals("name 1", result?.name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shots emits selected player's shots after loadPlayer`() = runTest {
        val repository = FakeOfflineFirstGolfRepository()

        repository.emitShots(shots)

        val viewModel = PlayerDetailViewModel(repository)

        viewModel.shots.test {
            assertEquals(emptyList<Shot>(), awaitItem())

            viewModel.loadPlayer("1")

            val result = awaitItem()

            assertEquals(1, result.size)
            assertEquals("shot1", result[0].id)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadPlayer calls syncPlayerDetails`() = runTest {
        val repository = FakeOfflineFirstGolfRepository()
        val viewModel = PlayerDetailViewModel(repository)

        viewModel.loadPlayer("1")

        advanceUntilIdle()

        assertTrue(repository.syncPlayerDetailsCalled)
        assertEquals("1", repository.syncedPlayerId)
    }
}