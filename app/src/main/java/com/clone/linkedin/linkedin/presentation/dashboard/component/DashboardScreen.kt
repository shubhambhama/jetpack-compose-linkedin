package com.clone.linkedin.linkedin.presentation.dashboard.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello LinkedIn", textAlign = TextAlign.Center)
    }
}