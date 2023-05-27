package com.clone.linkedin.linkedin.domain.model

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

data class MessageData(val imageUrl: String, val userName: String, val metaData: String,
                       val time: String, val imageShape: Shape = CircleShape)