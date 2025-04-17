package com.example.retrofitanimeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitanimeapp.ui.theme.AppPrimaryColor
import com.example.retrofitanimeapp.ui.theme.ButtonColor
import com.example.retrofitanimeapp.ui.theme.ButtonTextColor

@Composable
fun AnimeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Anime Screen",
            color = ButtonTextColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { },
            modifier = Modifier.padding(bottom = 16.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = ButtonColor,
                contentColor = ButtonTextColor
            )
        ) {
            Text(text = "Click Me")
        }

        Text(
            text = "This is a placeholder for the Anime Screen.",
            color = ButtonTextColor,
            fontSize = 16.sp
        )
    }
}

