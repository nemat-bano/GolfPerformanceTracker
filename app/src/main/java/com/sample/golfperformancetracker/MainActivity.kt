package com.sample.golfperformancetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sample.golfperformancetracker.ui.theme.GolfPerformanceTrackerTheme
import com.sample.ui.compose.GolfNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (BuildConfig.USE_COMPOSE) {
            setContent {
                GolfPerformanceTrackerTheme {
                    GolfNavHost()
                }
            }
        } else {
            setContentView(R.layout.activity_main)
        }


    }
}

