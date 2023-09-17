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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.model.SatelliteListItemObject
import com.example.satellitehub.compose.detail.DetailScreen
import com.example.satellitehub.compose.home.SatelliteListScreen
import com.example.satellitehub.style.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BaseScreen()
                }
            }
        }
    }
}

@Composable
fun BaseScreen() {
    val navController = rememberNavController()
    val animateEnter: EnterTransition = fadeIn() + slideInHorizontally(initialOffsetX = { it })
    val animateExit: ExitTransition = fadeOut() + slideOutHorizontally(targetOffsetX = { -it })

    val animatePopEnter: EnterTransition = fadeIn() + slideInHorizontally(initialOffsetX = { -it })
    val animatePopExit: ExitTransition = fadeOut() + slideOutHorizontally(targetOffsetX = { it })

    Scaffold { padding ->
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
            }
        }
    }
}