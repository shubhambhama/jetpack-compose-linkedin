package com.clone.linkedin.linkedin.domain.model

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

data class NotificationData(val imageUrl: String, val notificationText: String, val time: String, val additionalButtonText: String? = null, val imageShape: Shape = CircleShape)