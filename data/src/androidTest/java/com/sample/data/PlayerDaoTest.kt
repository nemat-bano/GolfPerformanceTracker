package com.sample.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.sample.data.room.AppDatabase
import com.sample.data.room.dao.PlayerDao
import com.sample.data.room.entities.PlayerEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PlayerDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: PlayerDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.playerDao()
    }

    @Test
    fun observePlayers_emitsPlayers() = runTest {

        dao.observePlayers().test {

            // Initial emission
            Assert.assertEquals(emptyList<PlayerEntity>(), awaitItem())

            dao.upsertPlayer(
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

            Assert.assertEquals(2, result.size)

            // ORDER BY name
            Assert.assertEquals("name 1", result[0].name)
            Assert.assertEquals("name 2", result[1].name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun observePlayer_emitsMatchingPlayer() = runTest {

        dao.observePlayer("1").test {

            // Initial emission
            assertEquals(null, awaitItem())

            dao.upsertPlayer(
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

            val player = awaitItem()

            assertEquals("1", player?.id)
            assertEquals("name 1", player?.name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun observePlayer_emitsUpdatedPlayer() = runTest {

        dao.upsertPlayer(
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

        dao.observePlayer("1").test {

            assertEquals("club 1", awaitItem()?.club)

            dao.upsertPlayer(
                listOf(
                    PlayerEntity(
                        id = "1",
                        name = "name 1",
                        club = "club 2",
                        averageSpeed = 45.0,
                        averageDistance = 73.0,
                        imageUrl = "https://randomuser.me/api/portraits/men/1.jpg"
                    )
                )
            )

            assertEquals(
                "club 2",
                awaitItem()?.club
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        database.close()
    }
}