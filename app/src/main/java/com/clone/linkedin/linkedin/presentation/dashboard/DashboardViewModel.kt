package com.clone.linkedin.linkedin.presentation.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.domain.model.BottomSheetData
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
                    "https://picsum.photos/id/64/200/200", "Shubham Bhama",
                    "Senior Software Engineer", "1s"
                ), postCenter = PostCenter(
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard " +
                            "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    "https://picsum.photos/500/600?random=${Math.random()}"
                ), postAction = PostAction(
                    182, 222, 52
                ),
                postHeader = PostHeader("https://picsum.photos/id/91/200/200", "XYZ commented on this")
            ),
            NormalPost(
                postTop = PostTop(
                    "https://picsum.photos/id/65/200/200", "Shubham Bhama",
                    "Senior Software Engineer", "1s"
                ), postCenter = PostCenter(
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard " +
                            "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    "https://picsum.photos/500/500?random=${Math.random()}"
                ), postAction = PostAction(
                    1, 2, 34
                )
            ),
            NormalPost(
                postTop = PostTop(
                    "https://picsum.photos/id/453/200/200", "Shubham Bhama",
                    "Senior Software Engineer", "1s"
                ), postCenter = PostCenter(
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard " +
                            "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                    "https://picsum.photos/500/300?random=${Math.random()}"
                ), postAction = PostAction(
                    1, 2, 34
                )
            )
        )
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