package com.clone.linkedin.linkedin.presentation.notification

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.domain.model.NotificationData
import com.clone.linkedin.linkedin.presentation.util.component.ActionButton
import com.clone.linkedin.linkedin.presentation.util.component.ImageViaUrl
import com.clone.linkedin.ui.theme.LightBlue
import com.clone.linkedin.ui.theme.textIconViewColor

@Composable
fun NotificationScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
        NotificationList()
    }
}

@Composable
private fun NotificationList(viewModel: NotificationViewModel = hiltViewModel()) {
    val listData = viewModel.getNotificationData()
    val context = LocalContext.current
    LazyColumn {
        items(listData.size) { position ->
            NotificationItem(context = context, notificationData = listData[position], modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp))
        }
    }
}

@Composable
private fun NotificationItem(context: Context, modifier: Modifier = Modifier, notificationData: NotificationData) {
    Row(modifier = modifier) {
        Row(modifier = Modifier.weight(9f).align(Alignment.CenterVertically)) {
            context.ImageViaUrl(modifier = Modifier.padding(top = 6.dp).size(40.dp), imageUrl = notificationData.imageUrl, shape = notificationData.imageShape)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = notificationData.notificationText,
                    style = TextStyle(fontSize = TextUnit(14F, TextUnitType.Sp))
                )
                notificationData.additionalButtonText?.let {
                    ActionButton(it, modifier = Modifier.padding(top = 16.dp).border(width = 1.dp, color = LightBlue, shape = RoundedCornerShape(24.dp)).padding(horizontal = 8.dp, vertical = 6.dp))
                }
            }
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            Text(text = notificationData.time, textAlign = TextAlign.Start, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(7.dp))
            Image(
                painter = painterResource(R.drawable.ic_menu_vertical),
                contentDescription = "Menu Item",
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(textIconViewColor())
            )
        }
    }
}