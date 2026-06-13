package com.sample.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.sample.data.room.AppDatabase
import com.sample.data.room.dao.ShotDao
import com.sample.data.room.entities.ShotEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShotDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var shotDao: ShotDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        shotDao = database.shotDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun observeShots_returnsOnlyMatchingPlayerShots() = runTest {

        shotDao.observeShots("1").test {

            // Initial Room emission
            assertEquals(emptyList<ShotEntity>(), awaitItem())

            shotDao.updateShots(
                listOf(
                    ShotEntity(
                        id = "shot1", playerId = "1", ballSpeed = 67.0,
                        launchAngle = 86.0,
                        distance = 79.0,
                        spinRate = 14,
                        clubUsed = "club 1",
                    )
                )
            )

            val result = awaitItem()

            assertEquals(1, result.size)

            assertTrue(
                result.all { it.playerId == "1" }
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun observeShots_emitsUpdatesWhenShotsChange() = runTest {

        shotDao.updateShots(
            listOf(
                ShotEntity(
                    id = "shot1", playerId = "1", ballSpeed = 67.0,
                    launchAngle = 86.0,
                    distance = 79.0,
                    spinRate = 14,
                    clubUsed = "club 1",
                )
            )
        )

        shotDao.observeShots("1").test {

            val initial = awaitItem()

            assertEquals(1, initial.size)

            shotDao.updateShots(
                listOf(
                    ShotEntity(
                        id = "shot1", playerId = "1", ballSpeed = 67.0,
                        launchAngle = 86.0,
                        distance = 79.0,
                        spinRate = 14,
                        clubUsed = "club 2",
                    )
                )
            )

            val updated = awaitItem()

            assertEquals(1, updated.size)
            assertEquals("club 2", updated.first().clubUsed)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun observeShots_returnsEmptyListWhenNoShotsExist() = runTest {

        shotDao.observeShots("1").test {

            assertEquals(
                emptyList<ShotEntity>(),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}