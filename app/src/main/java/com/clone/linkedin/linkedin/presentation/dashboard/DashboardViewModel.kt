package com.clone.linkedin.linkedin.presentation.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.domain.model.BottomSheetData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _postState = mutableStateOf<List<PostType>>(emptyList())
    val postState: State<List<PostType>> = _postState

    init {
        getPost()
    }

    private fun getPost() {
        val caption =
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
        val numberOfPosts = 18
        val post = NormalPost(
            postTop = PostTop(
                "https://picsum.photos/id/64/200/200", "Shubham Bhama", "Senior Software Engineer", "1s"
            ), postCenter = PostCenter(
                caption, "https://picsum.photos/500/600?random=${Math.random()}"
            ), postAction = PostAction(
                Random.nextInt(18, 9999), Random.nextInt(8, 99), Random.nextInt(18, 51)
            ), postHeader = PostHeader("https://picsum.photos/id/91/200/200", "XYZ commented on this")
        )
        val userProfileIds = listOf(64, 65, 453, 91, 209, 334, 338, 342, 375, 447, 494, 513)
        _postState.value += List(numberOfPosts) {
            post.copy(
                postTop = PostTop(
                    "https://picsum.photos/id/${userProfileIds[Random.nextInt(userProfileIds.size)]}/200/200", "Shubham Bhama", "Senior Software Engineer", "1s"
                ),
                postCenter = post.postCenter.copy(
                    postImageUrl = "https://picsum.photos/500/600?random=${Math.random()}"
                ), postAction = PostAction(
                    Random.nextInt(18, 9999), Random.nextInt(8, 99), Random.nextInt(18, 51)
                ), postHeader = if (Random.nextBoolean()) PostHeader("https://picsum.photos/id/91/200/200", "XYZ commented on this") else null
            )
        }
    }

    fun getDataForPostMenu() =
        listOf(
            BottomSheetData(R.drawable.ic_save, "Save"),
            BottomSheetData(R.drawable.ic_share, "Share via"),
            BottomSheetData(R.drawable.ic_hide, "I don't want to see this"),
            BottomSheetData(R.drawable.ic_cancel, "Unfollow"),
            BottomSheetData(R.drawable.ic_unfollow, "Remove connection"),
            BottomSheetData(R.drawable.ic_report_post, "Report post"),
        )
}