package com.clone.linkedin.linkedin.presentation.notification

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.ViewModel
import com.clone.linkedin.linkedin.domain.model.NotificationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {

    fun getNotificationData() = listOf(
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Congratulate Shubham Bhama on completing instagram project based on jetpack compose.", "38m", "Say congrats"),
        NotificationData(
            "https://picsum.photos/200?random=${Math.random()}",
            "Discover more opportunities with Jetpack Compose. Be one of the few developers to adapt modern UI toolkit.",
            "1h"
        ),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "AI is far more dangerous than nukes ⚠️.", "1d", "Read news", imageShape = RectangleShape),
        NotificationData(
            "https://picsum.photos/200?random=${Math.random()}",
            "XYZ is promoting a high priority Lead Software Engineer - Mobile role in Pune that matches your job alert.",
            "2w",
            imageShape = RectangleShape
        ),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Shubham Bhama posted: Sharing", "4h"),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Your Job Alert for senior software engineer in US", "4h", "View 30+ Jobs", imageShape = CircleShape),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Tuesday News Wrap: Scientists use AI to discover new antibiotic to treat deadly superbug", "19h"),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Congratulate Shubham Bhama on 5 year at XYZ \uD83C\uDF89", "1d"),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Shubham Bhama posted: Truly agreed \uD83D\uDCAF", "1d"),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "XYZ is hiring. Help him by sharing this job with your network: Lead Mobile Engineer", "4m", "Share job", imageShape = CircleShape),
        NotificationData("https://picsum.photos/200?random=${Math.random()}", "Millar and 2 people viewed your profile", "4m", "Retry Premium Free", imageShape = CircleShape),
    )
}