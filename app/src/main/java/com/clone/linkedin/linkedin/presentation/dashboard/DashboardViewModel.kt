package com.clone.linkedin.linkedin.presentation.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.clone.linkedin.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(): ViewModel() {

    private val _postState = mutableStateOf<List<PostType>>(emptyList())
    val postState: State<List<PostType>> = _postState

    init {
        getPost()
    }

    private fun getPost() {
        _postState.value += listOf(
            NormalPost(
                postTop = PostTop(
                    R.drawable.user_profile, "Shubham Bhama",
                    "Senior Software Engineer", "1s"
                ), postCenter = PostCenter(
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard " +
                            "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    R.drawable.ic_launcher_background
                ), postAction = PostAction(
                    1, 2, 34
                ),
                postHeader = PostHeader(R.drawable.user_profile, "XYZ commented on this")
            ),
            NormalPost(
                postTop = PostTop(
                    R.drawable.user_profile, "Shubham Bhama",
                    "Senior Software Engineer", "1s"
                ), postCenter = PostCenter(
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard " +
                            "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    R.drawable.ic_launcher_background
                ), postAction = PostAction(
                    1, 2, 34
                )
            ),
            NormalPost(
                postTop = PostTop(
                    R.drawable.user_profile, "Shubham Bhama",
                    "Senior Software Engineer", "1s"
                ), postCenter = PostCenter(
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard " +
                            "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    R.drawable.ic_launcher_background
                ), postAction = PostAction(
                    1, 2, 34
                )
            )
        )
    }
}