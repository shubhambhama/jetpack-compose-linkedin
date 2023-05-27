package com.clone.linkedin.linkedin.presentation.message

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.navigation.NavController
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.domain.model.MessageData
import com.clone.linkedin.linkedin.domain.model.MessageTabItem
import com.clone.linkedin.linkedin.presentation.util.component.ImageViaUrl
import com.clone.linkedin.linkedin.presentation.util.component.SearchBar
import com.clone.linkedin.ui.theme.JetpackComposeLinkedInCloneTheme
import com.clone.linkedin.ui.theme.TabGreen
import com.clone.linkedin.ui.theme.textIconViewColor
import com.clone.linkedin.ui.theme.textIconViewColorReverse
import kotlinx.coroutines.launch

@Composable
fun MessageScreen(navController: NavController) {
    JetpackComposeLinkedInCloneTheme {
        Column {
            TopBar(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().background(MaterialTheme.colorScheme.surface).padding(top = 8.dp, bottom = 6.dp, end = 12.dp, start = 12.dp),
                navController = navController
            )
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
                TabLayout()
            }
        }
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier, navController: NavController) {
    Surface(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_left_arrow_back),
                contentDescription = "Back Button",
                modifier = Modifier.padding(start = 8.dp).width(32.dp).height(24.dp).weight(1f).alpha(0.8f),
                colorFilter = ColorFilter.tint(textIconViewColor())
            )
            Spacer(modifier = Modifier.width(12.dp))
            SearchBar(
                modifier = Modifier.fillMaxWidth().height(36.dp).weight(7f).background(textIconViewColorReverse(), RoundedCornerShape(4.dp)).alpha(0.9f), hint = "Search messages"
            )
            Image(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
                modifier = Modifier.padding(start = 8.dp).width(32.dp).height(24.dp).weight(1f).alpha(0.8f),
                colorFilter = ColorFilter.tint(textIconViewColor())
            )
            Image(
                painter = painterResource(id = R.drawable.ic_menu_vertical),
                contentDescription = "Menu",
                modifier = Modifier.padding(start = 8.dp).width(32.dp).height(24.dp).weight(1f).alpha(0.8f).align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(textIconViewColor())
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout() {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val messageTabStopSpan = listOf(
        MessageTabItem(title = "Focused", screen = { MessageList() }),
        MessageTabItem(title = "Other", screen = { MessageList() }),
    )

    Column(
        modifier = Modifier.padding(0.dp)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 2.dp,
                    color = TabGreen // Change this to your desired line color
                )
            }
        ) {
            messageTabStopSpan.forEachIndexed { index, item ->
                Tab(
                    selected = index == pagerState.currentPage,
                    text = { Text(text = item.title) },
                    selectedContentColor = TabGreen,
                    unselectedContentColor = textIconViewColor(),
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                )
            }
        }
        HorizontalPager(
            pageCount = messageTabStopSpan.size, state = pagerState
        ) {
            messageTabStopSpan[pagerState.currentPage].screen()
        }
    }
}

@Composable
private fun MessageList(modifier: Modifier = Modifier, viewModel: MessageViewModel = hiltViewModel()) {
    val messageData = viewModel.getMessageData()
    val context = LocalContext.current
    LazyColumn(modifier = modifier) {
        items(messageData.size) {
            MessageItem(messageData = messageData[it], context = context, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp))
        }
    }
}

@Composable
private fun MessageItem(modifier: Modifier = Modifier, messageData: MessageData, context: Context) {
    Row(modifier = modifier) {
        Row(modifier = Modifier.weight(9f).align(Alignment.CenterVertically)) {
            context.ImageViaUrl(modifier = Modifier.padding(top = 6.dp).size(44.dp), imageUrl = messageData.imageUrl, shape = messageData.imageShape)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = messageData.userName,
                    style = TextStyle(fontSize = TextUnit(15F, TextUnitType.Sp), fontWeight = FontWeight.SemiBold),
                    color = textIconViewColor(),
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = messageData.metaData, style = TextStyle(fontSize = TextUnit(14.5F, TextUnitType.Sp), color = textIconViewColor())
                )
            }
        }
        Text(text = messageData.time, textAlign = TextAlign.Start, fontSize = 12.sp, color = textIconViewColor())
    }
}