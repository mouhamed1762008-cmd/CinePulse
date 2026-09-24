package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.CinePulseDatabase
import com.example.data.repository.CinePulseRepository
import com.example.ui.navigation.CinePulseApp
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.CinePulseTheme
import com.example.ui.viewmodel.CinePulseViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = CinePulseDatabase.getDatabase(applicationContext)
        val repository = CinePulseRepository(database.cinePulseDao())

        setContent {
            CinePulseTheme {
                val viewModel: CinePulseViewModel = viewModel(
                    factory = CinePulseViewModel.provideFactory(repository)
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundDark
                ) {
                    CinePulseApp(viewModel = viewModel)
                }
            }
        }
    }
}
