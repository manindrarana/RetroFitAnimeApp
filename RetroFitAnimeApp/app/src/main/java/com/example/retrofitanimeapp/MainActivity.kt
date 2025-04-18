package com.example.retrofitanimeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitanimeapp.model.MainViewModel
import com.example.retrofitanimeapp.screens.AnimeDetailsScreen
import com.example.retrofitanimeapp.screens.AnimeScreen
import com.example.retrofitanimeapp.ui.theme.RetroFitAnimeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetroFitAnimeAppTheme {
                val navController: NavHostController = rememberNavController()
                val viewModel: MainViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "anime_list"
                ) {
                    composable("anime_list") {
                        AnimeScreen(
                            modifier = Modifier.fillMaxSize(),
                            viewModel = viewModel,
                            onAnimeClick = { animeId ->
                                navController.navigate("anime_details/$animeId")
                            }
                        )
                    }
                    composable("anime_details/{animeId}") { backStackEntry ->
                        val animeId = backStackEntry.arguments?.getString("animeId")?.toIntOrNull() ?: 0
                        val animeList = viewModel.animeState.value.list
                        AnimeDetailsScreen(
                            animeId = animeId,
                            animeList = animeList
                        )
                    }
                }
            }
        }
    }
}