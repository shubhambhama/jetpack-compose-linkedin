package com.clone.linkedin.linkedin.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class MessageTabItem(
    val title: String, val screen: @Composable () -> Unit
)