package com.example.retrofitanimeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.retrofitanimeapp.screens.AnimeScreen
import com.example.retrofitanimeapp.ui.theme.RetroFitAnimeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetroFitAnimeAppTheme {
                AnimeScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}