package com.clone.linkedin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clone.linkedin.linkedin.presentation.dashboard.component.DashboardScreen
import com.clone.linkedin.linkedin.presentation.util.RoundImage
import com.clone.linkedin.linkedin.presentation.util.Screen
import com.clone.linkedin.linkedin.presentation.util.currentRoute
import com.clone.linkedin.ui.theme.DarkGray30
import com.clone.linkedin.ui.theme.DarkGray60
import com.clone.linkedin.ui.theme.JetpackComposeLinkedInCloneTheme
import com.clone.linkedin.ui.theme.White70
import com.clone.linkedin.ui.theme.textIconViewColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeLinkedInCloneTheme {
                Column {
                    TopBar(
                        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(top = 8.dp, bottom = 6.dp, end = 12.dp, start = 12.dp)
                    )
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController, screens = viewModel.listOfScreen()) }) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            LinkedInNavigate(navController)
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
                painter = painterResource(id = R.drawable.ic_message),
                contentDescription = "Message",
                modifier = Modifier.padding(start = 8.dp).width(32.dp).height(24.dp).weight(1f),
                colorFilter = ColorFilter.tint(White70)
            )
        }
    }
}

@Composable
private fun BottomBar(modifier: Modifier = Modifier, navController: NavController, screens: List<Triple<Screen, Int, Int>>) {
    val context = LocalContext.current
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.surface) {
        val currentRoute = currentRoute(navController = navController)
        screens.forEach { screen ->
            BottomNavigationItem(icon = {
                BottomBarItem(screen, context, currentRoute)
            }, selected = currentRoute == screen.first.route,
                alwaysShowLabel = true, onClick = {
                if (currentRoute != screen.first.route) {
                    navController.navigate(screen.first.route)
                }
            })
        }
    }
}

@Composable
private fun BottomBarItem(screen: Triple<Screen, Int, Int>, context: Context, currentRoute: String?) {
    val selectedUnselectedColor = textIconViewColor().let {
        if (currentRoute == screen.first.route) it else it.copy(alpha = ContentAlpha.medium)
    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(screen.second), contentDescription = null,
            colorFilter = ColorFilter.tint(
                selectedUnselectedColor
            ), modifier = Modifier.size(20.dp), contentScale = ContentScale.Fit
        )

        Text(text = context.getString(screen.third), fontSize = TextUnit(10F, TextUnitType.Sp), color = selectedUnselectedColor, modifier = Modifier.padding(2.dp))
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
private fun LinkedInNavigate(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.DashboardScreen.route) {
        composable(Screen.DashboardScreen.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.MyNetworkScreen.route) {
            DevelopmentInProgressScreen()
        }
        composable(Screen.AddPostScreen.route) {
            DevelopmentInProgressScreen()
        }
        composable(Screen.NotificationScreen.route) {
            DevelopmentInProgressScreen()
        }
        composable(Screen.JobsScreen.route) {
            DevelopmentInProgressScreen()
        }
    }
}

@Composable
fun DevelopmentInProgressScreen() {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        androidx.compose.material.Text(
            text = "Development In Progress", textAlign = TextAlign.Center, fontSize = 14.sp, color = textIconViewColor(), modifier = Modifier.padding(8.dp)
        )
    }
}