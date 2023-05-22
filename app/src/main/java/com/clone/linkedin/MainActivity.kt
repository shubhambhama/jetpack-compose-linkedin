package com.clone.linkedin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clone.linkedin.linkedin.presentation.dashboard.component.DashboardScreen
import com.clone.linkedin.linkedin.presentation.util.RoundImage
import com.clone.linkedin.linkedin.presentation.util.Screen
import com.clone.linkedin.ui.theme.JetpackComposeLinkedInCloneTheme
import com.clone.linkedin.ui.theme.DarkGray30
import com.clone.linkedin.ui.theme.DarkGray60
import com.clone.linkedin.ui.theme.LightGray
import com.clone.linkedin.ui.theme.textIconViewColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeLinkedInCloneTheme {
                Column {
                    TopBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    )
                    LinkedInNavigate()
                }
            }
        }
    }
}


@Composable
private fun TopBar(modifier: Modifier = Modifier) {
    androidx.compose.material3.Surface(modifier = modifier) {
        Row(verticalAlignment = CenterVertically) {
            RoundImage(
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .weight(8f)
                    .background(DarkGray30, RoundedCornerShape(4.dp)), hint = "Search"
            )
            Image(
                painter = painterResource(id = R.drawable.ic_message), contentDescription = "Message", modifier = Modifier
                    .padding(start = 8.dp)
                    .width(32.dp)
                    .height(24.dp)
                    .weight(1f),
                colorFilter = ColorFilter.tint(textIconViewColor())
            )
        }
    }
}

@Composable
private fun SearchBar(modifier: Modifier = Modifier, hint: String = "", onSearch: (String) -> Unit = {}) {
    val text = remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint.isNotEmpty())
    }
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search", modifier = Modifier
                .padding(start = 8.dp)
                .size(17.dp)
                .align(Alignment.CenterStart),
            colorFilter = ColorFilter.tint(textIconViewColor())
        )
        BasicTextField(
            value = text.value, onValueChange = {
                text.value = it
                onSearch.invoke(it)
            }, maxLines = 1, singleLine = true, textStyle = TextStyle(color = textIconViewColor()),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 36.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                }, cursorBrush = SolidColor(textIconViewColor())
        )
        if (isHintDisplayed) {
            Text(text = hint, color = DarkGray60, modifier = Modifier.padding(horizontal = 36.dp, vertical = 6.dp))
        }
    }
}

@Composable
private fun LinkedInNavigate() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.DashboardScreen.route) {
        composable(Screen.DashboardScreen.route) {
            DashboardScreen(navController = navController)
        }
    }
}