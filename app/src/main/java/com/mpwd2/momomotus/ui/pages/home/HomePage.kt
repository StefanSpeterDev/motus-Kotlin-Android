package com.mpwd2.momomotus.ui.pages.home

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mpwd2.momomotus.ui.pages.game.GameScreen
import com.mpwd2.momomotus.ui.pages.profile.ProfileScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomePage(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Game,
        NavigationItem.Leaderboard,
        NavigationItem.Profile,
    );
    val navController = rememberNavController()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { (Text(text = "La meilleure app du monde!!!!")) }
            )
        },
        bottomBar = {
            BottomNavigation(backgroundColor = Color.LightGray) {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route
                items.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.route,
                        unselectedContentColor = Color.Gray,
                        selectedContentColor = Color.Blue,
                        icon = {
                            Icon(
                                painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        },
                        label = { Text(text = item.title) },
                        onClick = {
                            navController.navigate(item.route) {

                                // s'occupe de revenir en arrière
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                // empêche de stacker les éclans à chaque clique
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    ) {
        Navigation(navController = navController)
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Game.route) {
        composable(NavigationItem.Game.route) {
            GameScreen()
        }
        composable(NavigationItem.Leaderboard.route) {
            LeaderboardScreen()
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }
    }
}
