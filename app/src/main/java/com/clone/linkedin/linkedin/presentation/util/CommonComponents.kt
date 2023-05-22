package com.clone.linkedin.linkedin.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clone.linkedin.R

@Composable
fun RoundImage(
    modifier: Modifier = Modifier, imageResId: Int = R.drawable.user_profile, whoseImage: String? = null
) {
    Card(modifier = modifier, shape = CircleShape) {
        Image(painter = painterResource(id = imageResId), contentDescription = whoseImage)
    }
}