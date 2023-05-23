package com.clone.linkedin

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.clone.linkedin.linkedin.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    fun listOfScreen(): List<Triple<Screen, Int, Int>> {
        return listOf(
            Triple(Screen.DashboardScreen, R.drawable.ic_home, R.string.home),
            Triple(Screen.MyNetworkScreen, R.drawable.ic_network, R.string.my_network),
            Triple(Screen.AddPostScreen, R.drawable.ic_post, R.string.post),
            Triple(Screen.NotificationScreen, R.drawable.ic_notification, R.string.notification),
            Triple(Screen.JobsScreen, R.drawable.ic_jobs, R.string.jobs)
        )
    }
}