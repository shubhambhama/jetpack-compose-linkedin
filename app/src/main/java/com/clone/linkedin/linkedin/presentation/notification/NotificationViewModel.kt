package com.clone.linkedin.linkedin.presentation.notification

import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.ViewModel
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.domain.model.NotificationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {

    fun getNotificationData() = listOf(
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Congratulate Shubham Bhama on completing instagram project based on jetpack compose.", "38m", "Say congrats"),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Discover more opportunities with Jetpack Compose. Be one of the few developers to adapt modern UI toolkit.", "1h"),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "AI is far more dangerous than nukes.", "1d", "Say congrats", imageShape = RectangleShape),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "XYZ is promoting a high priority Lead Software Engineer - Mobile role in Pune that matches your job alert.", "2w", imageShape = RectangleShape),
    )
}