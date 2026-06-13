package com.sample.data.repo

import app.cash.turbine.test
import com.sample.data.room.AppDatabase
import com.sample.data.room.entities.PlayerEntity
import com.sample.data.room.entities.ShotEntity
import com.sample.data.service.GolfService
import com.sample.domain.model.Player
import com.sample.domain.model.Shot
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class OfflineFirstGolfRepositoryImplTest {

    private lateinit var repository: OfflineFirstGolfRepositoryImpl
    private lateinit var playerDao: FakePlayerDao
    private lateinit var shotDao: FakeShotDao

    @Before
    fun setup() {
        val database = mockk<AppDatabase>(relaxed = true)
        val api = mockk<GolfService>(relaxed = true)

        playerDao = FakePlayerDao()
        shotDao = FakeShotDao()

        repository = OfflineFirstGolfRepositoryImpl(
            database = database,
            api = api,
            playerDao = playerDao,
            shotDao = shotDao
        )
    }

    @Test
    fun `observePlayers emits domain players`() = runTest {
        repository.observePlayers().test {
            assertEquals(emptyList<Player>(), awaitItem())

            playerDao.emitPlayers(
                listOf(
                    PlayerEntity(
                        id = "1",
                        name = "name 1",
                        club = "club 1",
                        averageSpeed = 45.0,
                        averageDistance = 73.0,
                        imageUrl = "https://randomuser.me/api/portraits/men/1.jpg"
                    )
                )
            )

            val result = awaitItem()

            assertEquals(1, result.size)
            assertEquals("1", result.first().id)
            assertEquals("name 1", result.first().name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `observePlayer emits selected domain player`() = runTest {
        repository.observePlayer("1").test {
            assertEquals(null, awaitItem())

            playerDao.emitPlayers(
                listOf(
                    PlayerEntity(
                        id = "1",
                        name = "name 1",
                        club = "club 1",
                        averageSpeed = 45.0,
                        averageDistance = 73.0,
                        imageUrl = "https://randomuser.me/api/portraits/men/1.jpg"
                    ),
                    PlayerEntity(
                        id = "2",
                        name = "name 2",
                        club = "club 2",
                        averageSpeed = 45.0,
                        averageDistance = 73.0,
                        imageUrl = "https://randomuser.me/api/portraits/men/2.jpg"
                    )
                )
            )

            val result = awaitItem()

            assertEquals("1", result?.id)
            assertEquals("name 1", result?.name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `observeShots emits selected player's domain shots`() = runTest {
        repository.observeShots("1").test {
            assertEquals(emptyList<Shot>(), awaitItem())

            shotDao.emitShots(
                listOf(
                    ShotEntity(
                        id = "shot1",
                        playerId = "1",
                        ballSpeed = 67.0,
                        launchAngle = 86.0,
                        distance = 79.0,
                        spinRate = 14,
                        clubUsed = "club 1"
                    ),
                    ShotEntity(
                        id = "shot2",
                        playerId = "2",
                        ballSpeed = 70.0,
                        launchAngle = 80.0,
                        distance = 90.0,
                        spinRate = 10,
                        clubUsed = "club 2"
                    )
                )
            )

            val result = awaitItem()

            assertEquals(1, result.size)
            assertEquals("shot1", result.first().id)
            assertEquals("1", result.first().playerId)

            cancelAndIgnoreRemainingEvents()
        }
    }
}