package com.clone.linkedin.linkedin.presentation.addpost

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.ViewModel
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.domain.model.BottomSheetData
import com.clone.linkedin.linkedin.domain.model.NotificationData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor() : ViewModel() {

    fun getDataForPostMenu() = listOf(
        BottomSheetData(R.drawable.ic_camera, "Add a photo"),
        BottomSheetData(R.drawable.ic_cam_recorder, "Take a video"),
        BottomSheetData(R.drawable.ic_template, "Use a template"),
        BottomSheetData(R.drawable.ic_celebrate, "Celebrate an occasion"),
        BottomSheetData(R.drawable.ic_document, "Add a document"),
        BottomSheetData(R.drawable.ic_jobs, "Share that you're hiring"),
        BottomSheetData(R.drawable.ic_find, "Find an expert"),
        BottomSheetData(R.drawable.ic_bar_chart, "Create a poll"),
        BottomSheetData(R.drawable.ic_event, "Create a event"),
    )
}