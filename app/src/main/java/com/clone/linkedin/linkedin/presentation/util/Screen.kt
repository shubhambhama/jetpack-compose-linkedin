package com.clone.linkedin.linkedin.presentation.util

sealed class Screen(val route: String) {
    object DashboardScreen: Screen("linkedin_dashboard_screen")
    object MyNetworkScreen: Screen("linkedin_my_network_screen")
    object AddPostScreen: Screen("linkedin_add_post_screen")
    object NotificationScreen: Screen("linkedin_notification_screen")
    object JobsScreen: Screen("linkedin_jobs_screen")
    object MessageScreen: Screen("linkedin_message_screen")
    object UserProfileScreen: Screen("linkedin_user_profile_screen")
}