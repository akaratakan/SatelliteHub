package com.example.satellitehub.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.model.SatelliteListItemObject
import com.example.satellitehub.R
import com.example.satellitehub.compose.detail.DetailScreen
import com.example.satellitehub.compose.home.SatelliteListScreen
import com.example.satellitehub.style.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var themeFlow by remember { mutableStateOf<Boolean?>(value = null) }
            AppTheme(darkTheme = themeFlow) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val themeResult = !(themeFlow ?: !isSystemInDarkTheme())
                    BaseScreen {
                        themeFlow = themeResult
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun BaseScreen(onThemeChangeButtonClicked: () -> Unit) {
    val navController = rememberNavController()
    var routeState by remember { mutableStateOf(Screen.Home.route) }
    val toolbarTitle =
        if (routeState == Screen.Home.route) stringResource(id = R.string.toolbar_title_home)
        else stringResource(id = R.string.toolbar_title_detail)
    val animateEnter: EnterTransition = fadeIn() + slideInHorizontally(initialOffsetX = { it })
    val animateExit: ExitTransition = fadeOut() + slideOutHorizontally(targetOffsetX = { -it })

    val animatePopEnter: EnterTransition = fadeIn() + slideInHorizontally(initialOffsetX = { -it })
    val animatePopExit: ExitTransition = fadeOut() + slideOutHorizontally(targetOffsetX = { it })

    Scaffold(topBar = {
        TopBar(
            navController = navController,
            toolbarTitle = toolbarTitle,
            routeState = routeState
        ) {
            onThemeChangeButtonClicked()
        }
    }
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable("${Screen.Detail.route}/{id}/{name}",
                enterTransition = { animateEnter },
                exitTransition = { animateExit },
                popEnterTransition = { animatePopEnter },
                popExitTransition = { animatePopExit }) { backStackEntry ->
                val detail = SatelliteListItemObject(
                    id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0,
                    active = true,
                    name = backStackEntry.arguments?.getString("name") ?: ""
                )
                DetailScreen(detail)
                routeState = Screen.Detail.route
            }
            composable(route = Screen.Home.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() }) {
                SatelliteListScreen {
                    navController.navigate(route = "${Screen.Detail.route}/${it.id}/${it.name}") {
                        launchSingleTop = true
                    }
                }
                routeState = Screen.Home.route
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    toolbarTitle: String,
    routeState: String,
    onThemeChangeButtonClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(text = toolbarTitle) },
        navigationIcon = {
            if (routeState == Screen.Detail.route) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onThemeChangeButtonClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Theme Change Icon"
                )
            }
        }
    )
}