package com.clone.linkedin.linkedin.presentation.message

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.ViewModel
import com.clone.linkedin.linkedin.domain.model.MessageData
import com.clone.linkedin.linkedin.domain.model.MessageTabItem
import com.clone.linkedin.linkedin.domain.model.NotificationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MessageViewModel @Inject constructor() : ViewModel() {

    fun getMessageData(): List<MessageData> {
        val userProfileIds = listOf(64, 65, 453, 91, 209, 334, 338, 342, 375, 447, 494, 513)
        return listOf(
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Rachel C. O'Donnell", "InMail: OPEN FOR OPPORTUNITY?", "Tue", RectangleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Jose S. Hess", "Sponsored: Data Science Job?", "May 12", RectangleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "James N. Eaton", "I'm happy to connect with you", "Apr 25", CircleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Rose E. Thomas", "You're welcome", "Apr 25", CircleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Shirley G. Laird", "You: Thanks for the wishes", "Jan 21", CircleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Kenneth E. Davis", "InMail: Opportunity with LinkedIn in Pune", "Dec 13, 2023", RectangleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Andrew I. Fullerton", "InMail: We are hiring for Lead Mobile Engineer", "Aug 15, 2023", RectangleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Clarence S. Miller", "InMail: Hello from Turing!", "May 17, 2022", RectangleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Casey E. Monico", "I'm happy to connect with you", "Feb 1, 2023", CircleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Latoya K. Albrecht", "Let's Connect for an interesting US Job opportunity", "Oct 19, 2022", CircleShape),
            MessageData("https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200",
            "Andrew J. Knowlton", "Greeting from Google! Job Opportunity.", "Sep 19, 2021", CircleShape),
        ).shuffled()
    }
}