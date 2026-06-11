package com.sample.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.ui.compose.details.PlayerDetailScreen
import com.sample.ui.compose.players.PlayerListScreen


@Composable
fun GolfNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "players"
    ) {

        composable("players") {
            PlayerListScreen(
                onPlayerClick = { playerId ->
                    navController.navigate("player/$playerId")
                }
            )
        }

        composable("player/{playerId}") { backStackEntry ->

            val playerId =
                backStackEntry.arguments
                    ?.getString("playerId")
                    .orEmpty()

            PlayerDetailScreen(
                playerId = playerId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
