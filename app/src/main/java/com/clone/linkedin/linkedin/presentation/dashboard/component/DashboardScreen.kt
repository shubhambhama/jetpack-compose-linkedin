package com.clone.linkedin.linkedin.presentation.dashboard.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.presentation.dashboard.DashboardViewModel
import com.clone.linkedin.linkedin.presentation.dashboard.NormalPost
import com.clone.linkedin.linkedin.presentation.dashboard.PostHeader
import com.clone.linkedin.linkedin.presentation.util.ExpandableText
import com.clone.linkedin.linkedin.presentation.util.RoundImage
import com.clone.linkedin.ui.theme.textIconViewColor

@Composable
fun DashboardScreen(navController: NavController) {
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        PostList()
    }
}

@Composable
private fun PostList(viewModel: DashboardViewModel = hiltViewModel()) {
    val posts = viewModel.postState.value

    LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
        items(posts.size) {
            val data = posts[it]
            if (data is NormalPost) {
                NormalPostItem(post = data, modifier = Modifier)
            }
        }
    }
}

@Composable
private fun NormalPostItem(modifier: Modifier = Modifier, post: NormalPost) {
    Surface(modifier.padding(bottom = 8.dp)) {
        Box {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                post.postHeader?.let { PostHeader(postHeader = post.postHeader, modifier = Modifier.padding(start = 8.dp).wrapContentSize()) }
                Row(modifier = Modifier.padding(start = 16.dp)) {
                    RoundImage(imageResId = post.postTop.userProfileImage, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = post.postTop.userName, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = TextUnit(14F, TextUnitType.Sp)))
                        Text(text = post.postTop.information, fontSize = TextUnit(12F, TextUnitType.Sp))
                        Text(text = post.postTop.postTime, fontSize = TextUnit(12F, TextUnitType.Sp))
                    }
                }
                ExpandableText(
                    modifier = Modifier.animateContentSize().padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 4.dp), text = post.postCenter.caption,
                    maxLines = 2
                )
                Image(painter = painterResource(R.drawable.sample_image), modifier = Modifier.fillMaxSize(), contentDescription = null, contentScale = ContentScale.Crop)
            }
        }
    }
}

@Composable
fun PostHeader(modifier: Modifier = Modifier, postHeader: PostHeader) {
    Column(Modifier.fillMaxSize()) {
        Row(modifier) {
            RoundImage(imageResId = postHeader.actionUserImage, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(8.dp))
            Text(text = postHeader.information, textAlign = TextAlign.Start, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterVertically).weight(8f))
            Image(
                painter = painterResource(R.drawable.ic_menu_vertical), contentDescription = "Menu Item", modifier = Modifier.size(20.dp).weight(1f).align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(textIconViewColor())
            )
        }
        Divider(modifier = Modifier.padding(8.dp).height(1.dp))
    }
}