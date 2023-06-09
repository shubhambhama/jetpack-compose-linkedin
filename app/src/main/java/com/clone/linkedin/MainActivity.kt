package com.clone.linkedin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.clone.linkedin.linkedin.presentation.addpost.AddPost
import com.clone.linkedin.linkedin.presentation.dashboard.component.DashboardScreen
import com.clone.linkedin.linkedin.presentation.message.MessageScreen
import com.clone.linkedin.linkedin.presentation.notification.NotificationScreen
import com.clone.linkedin.linkedin.presentation.util.Screen
import com.clone.linkedin.linkedin.presentation.util.component.RoundImage
import com.clone.linkedin.linkedin.presentation.util.component.SearchBar
import com.clone.linkedin.linkedin.presentation.util.component.currentRoute
import com.clone.linkedin.ui.theme.JetpackComposeLinkedInCloneTheme
import com.clone.linkedin.ui.theme.textIconViewColor
import com.clone.linkedin.ui.theme.textIconViewColorReverse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            JetpackComposeLinkedInCloneTheme {
                Column {
                    TopBar(
                        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(top = 8.dp, bottom = 6.dp, end = 12.dp, start = 12.dp),
                        navController
                    ) {
                        navController.navigate(Screen.MessageScreen.route)
                    }
                    MainContent(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent(viewModel: MainViewModel = hiltViewModel(), navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(bottomBar = { BottomBar(navController = navController, screens = viewModel.listOfScreen(), showDialog = showDialog) }) {
        Box(modifier = Modifier.fillMaxSize().padding(it).clickable(
            interactionSource = interactionSource, indication = null    // this gets rid of the ripple effect
        ) {
            keyboardController?.hide()
            focusManager.clearFocus(true)
        }) {
            LinkedInNavigate(navController, showDialog)
        }
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier, navController: NavHostController, onMessageClick: () -> Unit) {
    if (isBottomBarPages(navController = navController)) {
        androidx.compose.material3.Surface(modifier = modifier) {
            Row(verticalAlignment = CenterVertically) {
                RoundImage(
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                SearchBar(
                    modifier = Modifier.fillMaxWidth().height(36.dp).weight(8f).background(textIconViewColorReverse(), RoundedCornerShape(4.dp)).alpha(0.9f), hint = "Search"
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_message),
                    contentDescription = "Message",
                    modifier = Modifier.padding(start = 8.dp).width(32.dp).height(24.dp).weight(1f).alpha(0.8f).clickable { onMessageClick.invoke() },
                    colorFilter = ColorFilter.tint(textIconViewColor())
                )
            }
        }
    }
}

@Composable
private fun BottomBar(modifier: Modifier = Modifier, navController: NavController, screens: List<Triple<Screen, Int, Int>>, showDialog: MutableState<Boolean>) {
    val context = LocalContext.current
    if (isBottomBarPages(navController = navController)) {
        BottomNavigation(backgroundColor = MaterialTheme.colorScheme.surface, modifier = modifier) {
            val currentRoute = currentRoute(navController = navController)
            screens.forEach { screen ->
                BottomNavigationItem(icon = {
                    BottomBarItem(screen, context, currentRoute)
                }, selected = currentRoute == screen.first.route, alwaysShowLabel = true, onClick = {
                    if (screen.first.route == Screen.DashboardScreen.route) {
                        navController.navigate(Screen.DashboardScreen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    } else if (screen.first.route == Screen.AddPostScreen.route) {
                        showDialog.value = true
                    } else if (currentRoute != screen.first.route) {
                        navController.navigate(screen.first.route)
                    }
                })
            }
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
            painter = painterResource(screen.second), contentDescription = null, colorFilter = ColorFilter.tint(
                selectedUnselectedColor
            ), modifier = Modifier.size(23.dp), contentScale = ContentScale.Fit
        )

        Text(text = context.getString(screen.third), fontSize = TextUnit(10F, TextUnitType.Sp), color = selectedUnselectedColor, modifier = Modifier.padding(2.dp))
    }
}

@Composable
private fun LinkedInNavigate(navController: NavHostController, showDialog: MutableState<Boolean>) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination
    val activity = (LocalContext.current as? Activity)

    BackHandler {
        if (currentDestination?.route != Screen.DashboardScreen.route) {
            navController.navigate(Screen.DashboardScreen.route) {
                popUpTo(Screen.DashboardScreen.route) {
                    saveState = true
                }
                launchSingleTop = true
            }
        } else {
            activity?.finishAffinity()
        }
    }

    NavHost(navController = navController, startDestination = Screen.DashboardScreen.route) {
        composable(Screen.DashboardScreen.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.MyNetworkScreen.route) {
            DevelopmentInProgressScreen()
        }
        composable(Screen.NotificationScreen.route) {
            NotificationScreen()
        }
        composable(Screen.JobsScreen.route) {
            DevelopmentInProgressScreen()
        }
        composable(Screen.MessageScreen.route) {
            MessageScreen(navController)
        }
    }

    if (showDialog.value) {
        AddPost(navController = navController, onDismiss = {
            showDialog.value = false
        })
    }
}

@Composable
fun DevelopmentInProgressScreen() {
    androidx.compose.material3.Surface(
        modifier = Modifier.fillMaxSize().background(androidx.compose.material.MaterialTheme.colors.surface)
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            androidx.compose.material.Text(
                text = "Development In Progress", textAlign = TextAlign.Center, fontSize = 14.sp, color = textIconViewColor(), modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun isBottomBarPages(navController: NavController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    return currentDestination == Screen.DashboardScreen.route || currentDestination == Screen.MyNetworkScreen.route || currentDestination == Screen.NotificationScreen.route || currentDestination == Screen.JobsScreen.route
}