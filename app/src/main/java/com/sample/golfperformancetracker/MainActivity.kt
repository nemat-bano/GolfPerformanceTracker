package com.sample.golfperformancetracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.sample.golfperformancetracker.ui.theme.GolfPerformanceTrackerTheme
import com.sample.ui.compose.GolfNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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

